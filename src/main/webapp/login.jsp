<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Section</title>
</head>
<script src="./scripts/login.js"></script>
<style>
</style>
<body>
	<p id="status"></p>
	<label for="username">username:</label>
	<br>
	<input type="text" id="username" name="username">
	<br>
	<label for="password">password</label>
	<br>
	<input type="password" id="password" name="password">
	<br>
	<button onclick="makeLogin()">log in</button>
	<br>
	<br>
	<form action="signup.jsp" method="get">
		<input type="submit" value="sign up">
	</form>
</body>
</html>