<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<style>
div.navigate {
	height: 100vh;
	width: 25%;
	border: 1px solid #4CAF50;
	float: left;
	overflow: hidden;
}

div.content {
	height: 100vh;
	width: 74%;
	border: 1px solid #af4caf;
	float: left;
	overflow: hidden;
}

a {
	border: 1px solid #af4caf;
	display: block;
	text-align: center;
}

a.add-button {
	display: inline-block;
	margin-top: 5px;
}

a.edit-button {
	float: left;
	margin-left: 10px;
}

table {
	display: block;
	border: 1px solid #af4caf;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Session</title>
</head>
<script src="./scripts/addstudents.js"></script>
<body>
	<div class="navigate">
		<a href="coursesession?cc=show%20course%20session">course session</a>
		<a href="studentsession?cc=show%20student%20session">student session</a>
	</div>
	<div class="content">
		<p id="status"></p>
		<label for="id">Id:</label>
		<br>
		<input type="text" id="id" name="id">
		<br>
		<label for="fullname">Full name</label>
		<br>
		<input type="text" id="fullname" name="fullname">
		<br>
		<br>
		<label for="birthday">birthday</label>
		<br>
		<input type="text" id="birthday" name="birthday">
		<br>
		<br>
		<label for="grade">grade</label>
		<br>
		<input type="text" id="grade" name="grade">
		<br>
		<br>
		<label for="address">Address</label>
		<br>
		<input type="text" id="address" name="address">
		<br>
		<br>
		<label for="notes">Notes</label>
		<br>
		<input type="text" id="notes" name="notes">
		<br>
		<br>
		<button onclick="addStudent()">add</button>
	</div>
</body>
</html>
