package pl.mbos.bachelor_thesis.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.management.InvalidAttributeValueException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.taglibs.standard.lang.jstl.ELException;
import org.apache.taglibs.standard.lang.jstl.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.mbos.bachelor_thesis.Container;
import pl.mbos.bachelor_thesis.db.DBAccessor;
import pl.mbos.bachelor_thesis.objs.Attention;
import pl.mbos.bachelor_thesis.objs.Blink;
import pl.mbos.bachelor_thesis.objs.Meditation;
import pl.mbos.bachelor_thesis.objs.PoorSignal;
import pl.mbos.bachelor_thesis.objs.PowerEEG;

/**
 * Servlet implementation class DataServlet
 */
@WebServlet(urlPatterns = {"/data"})
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SCHEMA = "application";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.println("This feature is not yet supported. Please come back later");
		outputStream.flush();
		outputStream.close();
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OperationResult operationResult = new OperationResult(HttpServletResponse.SC_OK, "Thank you for yet another successfull upload of your data");

		ServletInputStream inputStream = request.getInputStream();
		String requestString = IOUtils.toString(inputStream, "UTF-8");
		inputStream.close();

		Container dataContainer = parseRequest(requestString, operationResult);
		if (operationResult.code == HttpServletResponse.SC_OK) {
			persistData(dataContainer, operationResult);
		}
		response.setStatus(operationResult.code);
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.println(operationResult.outcome);
		outputStream.flush();
		outputStream.close();
	}

	private void persistData(Container dataContainer, OperationResult result) {
		try {
			persistAttention(dataContainer.getAttentions());
			persistMeditation(dataContainer.getMeditations());
			persistBlink(dataContainer.getBlinks());
			persistPowers(dataContainer.getPowers());
			persistSignals(dataContainer.getSignals());
		} catch (SQLException e) {
			result.outcome = e.getMessage();
			result.code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
	}

	private void persistSignals(List<PoorSignal> signals) throws SQLException {
		DBAccessor dba = new DBAccessor();
		Connection connection = dba.getConnection();
		Statement createStatement = connection.createStatement();
		for (PoorSignal psig : signals) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`signals` (value,userID,acquisitionTime) VALUES(" + psig.getValue() + "," + psig.getUserId() + "," + psig.getCollectionDate().getTime() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();
	}

	private void persistPowers(List<PowerEEG> powers) throws SQLException {
		DBAccessor dba = new DBAccessor();
		Connection connection = dba.getConnection();
		Statement createStatement = connection.createStatement();
		for (PowerEEG power : powers) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`powers` (acquisitionTime, userID, lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, midGamma, theta, delta) " + "VALUES("
					+ power.getCollectionDate().getTime() + "," + power.getUserId() + "," + power.getLowAlpha() + "," + power.getHighAlpha() + "," + power.getLowBeta() + "," + power.getHighBeta()
					+ "," + power.getLowGamma() + "," + power.getMidGamma() + "," + power.getTheta() + "," + power.getDelta() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();
	}

	private void persistBlink(List<Blink> blinks) throws SQLException {
		DBAccessor dba = new DBAccessor();
		Connection connection = dba.getConnection();
		Statement createStatement = connection.createStatement();
		for (Blink blink : blinks) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`blinks` (value,userID,acquisitionTime) VALUES(" + blink.getValue() + "," + blink.getUserId() + "," + blink.getCollectionDate().getTime() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();
	}

	private void persistMeditation(List<Meditation> meditations) throws SQLException {
		DBAccessor dba = new DBAccessor();
		Connection connection = dba.getConnection();
		Statement createStatement = connection.createStatement();
		for (Meditation med : meditations) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`meditations` (value,userID,acquisitionTime) VALUES(" + med.getValue() + "," + med.getUserId() + "," + med.getCollectionDate().getTime() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();
	}

	private void persistAttention(List<Attention> attentions) throws SQLException {
		DBAccessor dba = new DBAccessor();
		Connection connection = dba.getConnection();
		Statement createStatement = connection.createStatement();
		for (Attention att : attentions) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`attentions` (value,userID,acquisitionTime) VALUES(" + att.getValue() + "," + att.getUserId() + "," + att.getCollectionDate().getTime() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();

	}

	private Container parseRequest(String requestString, OperationResult operationResult) {
		ParseResult result = new ParseResult(true, operationResult.outcome);
		Container container = DataRequestParser.parseRequest(requestString, result);
		if (!result.success) {
			operationResult.code = HttpServletResponse.SC_BAD_REQUEST;
			operationResult.outcome = result.outcome;
		}
		return container;
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
}
