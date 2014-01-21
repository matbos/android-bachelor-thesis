<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,java.lang.*, pl.mbos.bachelor_thesis.objs.*,pl.mbos.bachelor_thesis.web.DataFormatter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View data stored on the server</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="./style.css" rel="stylesheet" type="text/css" />
<link href="../style.css" rel="stylesheet" type="text/css" />
</head>
<body>
		<%
			String action = (String) request.getAttribute("action");
			if (action == null) {
		%>
		<div class="formLogin">
		<form class="login" action="data/view" method="post" >
			<input type="text" name="login" /> <input type="password"
				name="password" /> <input type="submit" name="action" value="login" />
		</form>
		</div>		
		<%
			} else if (action.equalsIgnoreCase("chooseUser")) {
		%>
		<div class="formLogin">
		<h1> Preview data</h1>
		<form class="preview" action="view" method="post">
			<select name="chosenUser">
				<%
					@SuppressWarnings("unchecked")
						List<User> users = (List<User>) request.getAttribute("users");
						for (User u : users) {
							out.print("<option value=\"" + u.getId() + "\">" + u.getFirstName() + " " + u.getLastName() + "</option>");
						}
				%>
			</select> <input type="hidden" name="action" value="showUser" /> <input
				type="submit" value="Show data" />
		</form>
		<h1> Add user</h1>
		<form class="addUserForm" action="view" method="post">
			<input type="hidden" name="action" value="addUser" />
		Name:	<input type="text" name="name" value="" /> <br/>
		Surname:	<input type="text" name="surname" value="" /> <br/> 
		Password:	<input type="text" name="newPassword" value="" /> <br/><br/>
			<input type="submit" value="Add user" />
		</form>
		</div>
		<%
			} else if (action.equalsIgnoreCase("display")) {
		%>
		<div class="centered">
		<a href="../view.jsp">Go back</a>
		<%
			out.print(DataFormatter.formatSimpleData("Attention", (List<ISimpleData>) request.getAttribute("attentions")) + "<br/><br/>");
				out.print(DataFormatter.formatSimpleData("Meditation", (List<ISimpleData>) request.getAttribute("meditations")) + "<br/><br/>");
				out.print(DataFormatter.formatSimpleData("Blink", (List<ISimpleData>) request.getAttribute("blinks")) + "<br/><br/>");
				out.print(DataFormatter.formatSimpleData("Poor Signal", (List<ISimpleData>) request.getAttribute("signals")) + "<br/><br/>");
				out.print(DataFormatter.formatComplexData("PowerEEG", (List<PowerEEG>) request.getAttribute("powers")) + "<br/><br/>");
		%>

		<a href="../view.jsp">Go back</a>
		</div>
		<%
			} else if (action.equalsIgnoreCase("wrongCredentials")) {
		%>
		<div class="centered">
		<div class="error">
			<%
				out.print(request.getAttribute("cause"));
			%>
			<br /> Please go back to <a href="../view.jsp">the beginning</a>
		</div>
		</div>
		<%
			} else if (action.equalsIgnoreCase("userAdded")) {
		%>
		<div class="formLogin">
			<%
				out.print("Created user with: ID: "+request.getAttribute("id") + "<br/> Name "+request.getAttribute("name") +"<br/> Surname: " +request.getAttribute("surname") + "<br/> Password "+request.getAttribute("pass"));
			%>
			<br /> Please go back to <a href="../view.jsp">the beginning</a>
		</div>
		<%
			} else {
		%>
		<div class="centered">
		<div class="error">
			Most probably you should not be here, please go back to <a
				href="../view.jsp">the beginning</a>
		</div>
		</div>
		<%
			}
		%>
	</div>
</body>
</html>