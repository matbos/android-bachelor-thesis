package pl.mbos.bachelor_thesis.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import pl.mbos.bachelor_thesis.Container;
import pl.mbos.bachelor_thesis.token.TokenManager;

/**
 * Servlet implementation class DataRequestToken
 */
@WebServlet("/data/token")
public class DataRequestToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static TokenManager tokenManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataRequestToken() {
		super();
		tokenManager = TokenManager.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletInputStream inputStream = request.getInputStream();
		String requestString = IOUtils.toString(inputStream, "UTF-8");
		inputStream.close();
		Long id = -102L;
		String cause = "";
		try {
			JSONObject obj = new JSONObject(requestString);
			id = obj.getLong("userID");
		} catch (JSONException e) {
			cause = e.getMessage();
			e.printStackTrace();
		}
		ServletOutputStream outputStream = response.getOutputStream();
		if (id == -102L) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			outputStream.println(cause);
		} else {
			outputStream.println(tokenManager.generateToken(id));
		}
		outputStream.flush();
		outputStream.close();
	}
}
