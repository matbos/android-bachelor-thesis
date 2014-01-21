package pl.mbos.bachelor_thesis.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import pl.mbos.bachelor_thesis.Container;
import pl.mbos.bachelor_thesis.objs.Attention;
import pl.mbos.bachelor_thesis.objs.Blink;
import pl.mbos.bachelor_thesis.objs.Meditation;
import pl.mbos.bachelor_thesis.objs.PoorSignal;
import pl.mbos.bachelor_thesis.objs.PowerEEG;
import pl.mbos.bachelor_thesis.web.helpers.OperationResult;

public class DBWriter {

	private static final String SCHEMA = "application";

	public void persistData(Container dataContainer, OperationResult result) {
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

	public String addUser(String name, String surname, String password) {
		String userID = "";
		Connection connection;
		try {
			connection = DBAccessor.getConnection();
			Statement createStatement = connection.createStatement();
			String sql = "INSERT INTO `" + SCHEMA + "`.`users` (name,surname,password) VALUES('" + name + "','" + surname + "','" + password + "') ";
			int executeUpdate = createStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = createStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				userID = "" + generatedKeys.getBigDecimal(1).toPlainString();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userID;
	}

	private void persistSignals(List<PoorSignal> signals) throws SQLException {
		Connection connection = DBAccessor.getConnection();
		Statement createStatement = connection.createStatement();
		for (PoorSignal psig : signals) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`signals` (value,userID,acquisitionTime) VALUES(" + psig.getValue() + "," + psig.getUserId() + "," + psig.getCollectionDate().getTime() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();
	}

	private void persistPowers(List<PowerEEG> powers) throws SQLException {
		Connection connection = DBAccessor.getConnection();
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
		Connection connection = DBAccessor.getConnection();
		Statement createStatement = connection.createStatement();
		for (Blink blink : blinks) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`blinks` (value,userID,acquisitionTime) VALUES(" + blink.getValue() + "," + blink.getUserId() + "," + blink.getCollectionDate().getTime() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();
	}

	private void persistMeditation(List<Meditation> meditations) throws SQLException {
		Connection connection = DBAccessor.getConnection();
		Statement createStatement = connection.createStatement();
		for (Meditation med : meditations) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`meditations` (value,userID,acquisitionTime) VALUES(" + med.getValue() + "," + med.getUserId() + "," + med.getCollectionDate().getTime() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();
	}

	private void persistAttention(List<Attention> attentions) throws SQLException {
		Connection connection = DBAccessor.getConnection();
		Statement createStatement = connection.createStatement();
		for (Attention att : attentions) {
			String sql = "INSERT INTO `" + SCHEMA + "`.`attentions` (value,userID,acquisitionTime) VALUES(" + att.getValue() + "," + att.getUserId() + "," + att.getCollectionDate().getTime() + ") ";
			createStatement.addBatch(sql);
		}
		createStatement.executeBatch();
		connection.close();

	}

}
