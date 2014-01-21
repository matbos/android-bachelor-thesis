package pl.mbos.bachelor_thesis.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.mbos.bachelor_thesis.db.DBAccessor;

/**
 * Servlet implementation class DataLastUpdate
 */
@WebServlet(urlPatterns = {"/data/last"})
public class DataLastUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Date attentionDate;
	private Date mediationDate;
	private Date blinkDate;
	private Date powerDate;
	private Date poorSignalDate;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataLastUpdate() {
		super();
		attentionDate = mediationDate = blinkDate = powerDate = poorSignalDate = new Date();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OperationResult opResult = new OperationResult(HttpServletResponse.SC_OK, "All good");
		String userIDString = request.getParameter("userID");
		try {			
			if(userIDString != null ) {
				Long uid = Long.parseLong(userIDString);
				getLastSynchornizationDate(uid, opResult);
			} else {
				opResult.code = HttpServletResponse.SC_BAD_REQUEST;
				opResult.outcome = "Request was missing a parameter userID";
			}	
		} catch (  NumberFormatException e) {
			opResult.code = HttpServletResponse.SC_BAD_REQUEST;
			opResult.outcome = "Parameter userID was malformed ("+e.getMessage()+")";
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.println(opResult.outcome);
		outputStream.flush();
		outputStream.close();
		response.setStatus(opResult.code);
	}

	private void getLastSynchornizationDate(Long userID, OperationResult operationResult) {
		try {
		long attentionTime = fetchMaxTime("attentions", userID);
		long mediationTime = fetchMaxTime("meditations", userID);
		long blinkTime = fetchMaxTime("blinks", userID);
		long powerTime = fetchMaxTime("powers", userID);
		long poorSignalTime = fetchMaxTime("signals", userID);
		String jsonString = "{ " + "\"attention\" : " + attentionTime + "," + "\"meditation\" : " + mediationTime + "," + "\"blink\" : " + blinkTime + "," + "\"power\" : "
				+ powerTime + "," + "\"poorSignal\" : " + poorSignalTime + "" + " }";
		operationResult.outcome = jsonString;
		} catch( SQLException e) {
			operationResult.code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			operationResult.outcome = e.getMessage() + "    \n\n " + e.getSQLState();
		}
	}

	private long fetchMaxTime(String tableName, Long userID) throws SQLException {
		String columnName = "acquisitionTime";
		DBAccessor dba = new DBAccessor();
		Connection connection = dba.getConnection();
		Statement statement = connection.createStatement();
		String sql = "SELECT max(" + columnName + ") as "+columnName+" FROM application." + tableName + " WHERE userID=" + userID;
		statement.execute(sql);
		ResultSet resultSet = statement.getResultSet();
		if (resultSet.first()) {
			return resultSet.getLong(columnName);
		} else {
			return Long.MIN_VALUE;
		}
	}

	private JsonObject readRequest(BufferedReader reader) {
		JsonReader jsonReader = Json.createReader(reader);
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		return object;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doTrace(HttpServletRequest, HttpServletResponse)
	 */
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
