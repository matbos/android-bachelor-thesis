package pl.mbos.bachelor_thesis.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.mbos.bachelor_thesis.objs.*;

public class DBReader {

	private String dbSchema = "application.";

	public DBReader() {
	}

	public List<Attention> getAttentionForUser(User user) {
		return getAttentionForUser(user.getId());
	}

	public List<Attention> getAttentionForUser(Long userID) {
		List<Attention> attentions = null;
		try {
			Connection connection = DBAccessor.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM " + dbSchema + "attentions WHERE userID=" + userID.toString());
			if (result.isBeforeFirst()) {
				attentions = new ArrayList<Attention>();
				while (result.next()) {
					Attention na = new Attention(result.getLong("userID"), result.getInt("value"), new Date(result.getLong("acquisitionTime")));
					attentions.add(na);
				}
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return attentions;
	}

	public List<Meditation> getMeditationForUser(User user) {
		return getMeditationForUser(user.getId());
	}

	public List<Meditation> getMeditationForUser(Long userID) {
		List<Meditation> meditations = null;
		try {
			Connection connection = DBAccessor.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM " + dbSchema + "meditations WHERE userID = " + userID.toString());
			if (result.isBeforeFirst()) {
				meditations = new ArrayList<Meditation>();
				while (result.next()) {
					Meditation na = new Meditation(result.getLong("userID"), result.getInt("value"), new Date(result.getLong("acquisitionTime")));
					meditations.add(na);
				}
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return meditations;
	}

	public List<Blink> getBlinkForUser(User user) {
		return getBlinkForUser(user.getId());
	}

	public List<Blink> getBlinkForUser(Long userID) {
		List<Blink> blinks = null;
		try {
			Connection connection = DBAccessor.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM " + dbSchema + "blinks WHERE userID = " + userID.toString());
			if (result.isBeforeFirst()) {
				blinks = new ArrayList<Blink>();
				while (result.next()) {
					Blink na = new Blink(result.getLong("userID"), result.getInt("value"), new Date(result.getLong("acquisitionTime")));
					blinks.add(na);
				}
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return blinks;
	}

	public List<PoorSignal> getPoorSignalForUser(User user) {
		return getPoorSignalForUser(user.getId());
	}

	public List<PoorSignal> getPoorSignalForUser(Long userID) {
		List<PoorSignal> signals = null;
		try {
			Connection connection = DBAccessor.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM " + dbSchema + "signals WHERE userID = " + userID.toString());
			if (result.isBeforeFirst()) {
				signals = new ArrayList<PoorSignal>();
				while (result.next()) {
					PoorSignal na = new PoorSignal(result.getLong("userID"), result.getInt("value"), new Date(result.getLong("acquisitionTime")));
					signals.add(na);
				}
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return signals;
	}

	public List<PowerEEG> getPowerEEGForUser(User user) {
		return getPowerEEGForUser(user.getId());
	}

	public List<PowerEEG> getPowerEEGForUser(Long userID) {
		List<PowerEEG> powers = null;
		try {
			Connection connection = DBAccessor.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM " + dbSchema + "powers WHERE userID=" + userID.toString());
			if (result.isBeforeFirst()) {
				powers = new ArrayList<PowerEEG>();
				while (result.next()) {
					PowerEEG.Builder builder = new PowerEEG.Builder();
					builder.addUserId(result.getLong("userID"));
					builder.addLowAlpha(result.getInt("lowAlpha"));
					builder.addHighAlpha(result.getInt("highAlpha"));
					builder.addLowBeta(result.getInt("lowBeta"));
					builder.addHighBeta(result.getInt("highBeta"));
					builder.addLowGamma(result.getInt("lowGamma"));
					builder.addMidGamma(result.getInt("midGamma"));
					builder.addTheta(result.getInt("theta"));
					builder.addDelta(result.getInt("delta"));
					builder.addCollectionDate(new Date(result.getLong("acquisitionTime")));
					powers.add(builder.build());
				}
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return powers;
	}

	public List<User> getAllUsers() {
		List<User> users = null;
		try {
			Connection connection = DBAccessor.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(" SELECT * FROM " + dbSchema + "users");
			if (result.isBeforeFirst()) {
				users = new ArrayList<User>();
				while (result.next()) {
					User na = new User(result.getLong("id"), result.getString("name"), result.getString("surname"));
					users.add(na);
				}
			}
			connection.close();                               
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

	public DataContainer getAllDataForUser(User user) {
		return getAllDataForUser(user.getId());
	}

	public DataContainer getAllDataForUser(Long userID) {
		DataContainer dc = new DataContainer();
		dc.setAttentions(getAttentionForUser(userID));
		dc.setMeditations(getMeditationForUser(userID));
		dc.setBlinks(getBlinkForUser(userID));
		dc.setPowers(getPowerEEGForUser(userID));
		dc.setSignals(getPoorSignalForUser(userID));
		return dc;
	}

	public boolean isUserInDB(Long userID) {
		try {
			Connection connection = DBAccessor.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM " + dbSchema + "users");
			if (result.isBeforeFirst()) {
				return true;
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
