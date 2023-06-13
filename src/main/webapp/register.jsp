<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registration Page</title>
</head>
<body>
<h1>Registration Form</h1>
<form method="POST" action="HotelServlet">
	<input type="hidden" name="command" value="REGISTER">
	<label>Username:</label>
	<input type="text" name="username" required>
	<br><br>
	<label>Password:</label>
	<input type="password" name="password" required>
	<br><br>
	<label>Full name:</label>
	<input type="text" name="fullname" required>
	<br><br>
	<label>Email:</label>
	<input type="text" name="email" required>
	<br><br>
	<label>Phone:</label>
	<input type="text" name="phone" required>
	<br><br>
	<input type="submit" value="Register">
</form>
<br>
<a href="login.jsp">Already have an account? Login here</a>
</body>
</html>