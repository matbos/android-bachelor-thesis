package pl.mbos.bachelor_thesis.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Servlet that handles user login requests.
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject jsonObject = readRequest(request.getReader());
		OperationResult operationResult = new OperationResult(HttpServletResponse.SC_OK, "Ok, you're allowed in.");
		authorizeUser(jsonObject, operationResult);
		response.setStatus(operationResult.code);
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.println(operationResult.outcome);
		outputStream.flush();
		outputStream.close();		
	}

	private void authorizeUser(JsonObject jsonObject, OperationResult operationResult) {
		if (jsonObject.containsKey("id") && jsonObject.containsKey("password")) {
			String pass =  jsonObject.getString("password");
			Long uid = Long.parseLong(jsonObject.getString("id"));
			String dbPass = getPassForUser(uid);			
			if (dbPass != null) { 
				if(dbPass.equals(pass)) {
					operationResult.code = HttpServletResponse.SC_OK;					
				} else {
					operationResult.code = HttpServletResponse.SC_UNAUTHORIZED;
					operationResult.outcome = "Incorrect password, try again";
				}
			} else {
				operationResult.code = HttpServletResponse.SC_UNAUTHORIZED;
				operationResult.outcome = "User with given id does not exist "+uid;
			}
		} else {
			operationResult.code = HttpServletResponse.SC_BAD_REQUEST;
			operationResult.outcome = "Incorrect structure of the query : "+jsonObject.toString();
		}

	}

	private String getPassForUser(Long userID) {
		try {
			DBAccessor dba = new DBAccessor();
			Connection connection = dba.getConnection();
			Statement createStatement = connection.createStatement();
			String sql = "SELECT `password` FROM `application`.`users` WHERE id=" + userID;
			createStatement.execute(sql);
			ResultSet resultSet = createStatement.getResultSet();
			resultSet.first();
			resultSet.findColumn("password");
			String dbPass = resultSet.getString("password");
			connection.close();
			return dbPass;
		} catch (SQLException e) {
			String msg = e.getMessage();
			return null;
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

	private JsonObject readRequest(BufferedReader reader) {
		JsonReader jsonReader = Json.createReader(reader);
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		return object;
	}

}
