package pl.mbos.bachelor_thesis.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.mbos.bachelor_thesis.db.DBReader;
import pl.mbos.bachelor_thesis.db.DBWriter;
import pl.mbos.bachelor_thesis.db.DataContainer;

/**
 * Servlet implementation class DataViewServlet
 */
@WebServlet(urlPatterns = { "/data/view" })
public class DataViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBReader reader;
	private final DBWriter writer;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataViewServlet() {
		super();
		reader = new DBReader();
		writer = new DBWriter();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		if (action.equalsIgnoreCase("login")) {
			handleLoginAction(request, response);
		} else if (action.equalsIgnoreCase("addUser")) {
			handleAddUserAction(request, response);
		} else if (action.equalsIgnoreCase("showUser")) {
			handleShowUserAction(request, response);
		} else {
			response.sendRedirect("/view.jsp");
		}
		request.getRequestDispatcher("/view.jsp").forward(request, response);
	}

	private void handleAddUserAction(HttpServletRequest request, HttpServletResponse response) {
		String pass = (String) request.getParameter("newPassword");
		String name = (String) request.getParameter("name");
		String surname = (String) request.getParameter("surname");
		if (pass != null && name != null && surname != null) {
			pass = pass.trim();
			name = name.trim();
			surname = surname.trim();
			if (!pass.equals("") && !name.equals("") && !surname.equals("")) {
				String id = writer.addUser(name, surname, pass);
				request.setAttribute("action", "userAdded");
				request.setAttribute("id", id);
				request.setAttribute("name", name);
				request.setAttribute("surname", surname);
				request.setAttribute("pass", pass);	
			} else {
				request.setAttribute("action", "wrongCredentials");
				request.setAttribute("cause", "Passed values were invalid!");
			}
		} else {
			request.setAttribute("action", "wrongCredentials");
			request.setAttribute("cause", "Given fields values were invalid!");
		}
	}

	private void handleLoginAction(HttpServletRequest request, HttpServletResponse response) {
		String username = (String) request.getParameter("login");
		String pass = (String) request.getParameter("password");
		if (username.equals("admin") && pass.equals("concept")) {
			request.setAttribute("users", reader.getAllUsers());
			request.setAttribute("action", "chooseUser");
		} else {
			request.setAttribute("action", "wrongCredentials");
			request.setAttribute("cause", "Credentials provided do not match any of privilegded accounts");
		}
	}

	private void handleShowUserAction(HttpServletRequest request, HttpServletResponse response) {
		Long userId = Long.parseLong((String) request.getParameter("chosenUser"));
		if (reader.isUserInDB(userId)) {
			DataContainer dc = reader.getAllDataForUser(userId);
			request.setAttribute("attentions", dc.getAttentions());
			request.setAttribute("meditations", dc.getMeditations());
			request.setAttribute("blinks", dc.getBlinks());
			request.setAttribute("powers", dc.getPowers());
			request.setAttribute("signals", dc.getSignals());
			request.setAttribute("action", "display");
		}
	}
}
