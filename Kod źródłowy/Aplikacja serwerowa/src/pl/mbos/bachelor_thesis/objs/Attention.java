package pl.mbos.bachelor_thesis.objs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Entity mapped to table ATTENTION.
 */
public class Attention implements ISimpleData{

	private long userId;
	private int value;
	/**
	 * Not-null value.
	 */
	private java.util.Date collectionDate;

	public Attention() {
	}

	public Attention(long userId, int value, java.util.Date collectionDate) {
		this.userId = userId;
		this.value = value;
		this.collectionDate = collectionDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Not-null value.
	 */
	public java.util.Date getCollectionDate() {
		return collectionDate;
	}

	/**
	 * Not-null value; ensure this value is available before it is saved to the
	 * database.
	 */
	public void setCollectionDate(java.util.Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public static Attention parseJSON(JSONObject object) {
		Attention attention = null;
		try {
			attention = new Attention(object.getLong("user"), object.getInt("value"), new Date(object.getLong("date")));
		} catch (JSONException e) {
			throw new RuntimeException("Passed JSON was invalid! " + e.getMessage());
		}
		return attention;
	}

	public String toJSON() {
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{ ");
		jsonBuilder.append("\"user\" : " + userId + ",");
		jsonBuilder.append("\"value\" : " + value + ",");
		jsonBuilder.append("\"date\" : " + collectionDate.getTime());
		jsonBuilder.append(" }");
		return jsonBuilder.toString();
	}

	public Attention(Attention a) {
		this.userId = a.getUserId();
		this.value = a.getValue();
		this.collectionDate = a.getCollectionDate();
	}

	public static Attention[] convertToArray(List<Attention> data) {
		Attention[] array = new Attention[data.size()];
		int i = 0;
		for (Attention a : data) {
			array[i++] = new Attention(a);
		}
		return array;
	}
}
