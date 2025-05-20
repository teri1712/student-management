<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="model.Student"%>
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
<script src="./scripts/editstudent.js"></script>
<body>
	<%
	Student s = (Student) request.getAttribute("student");
	%>
	<div class="navigate">
		<a href="coursesession?cc=show%20course%20session">course session</a>
		<a href="studentsession?cc=show%20student%20session">student session</a>
	</div>
	<div class="content">
		<p id="status"></p>
		<label for="id">id</label>
		<br>
		<input type="text" id="id" name="id" readonly value="<%=s.getId()%>">
		<br>
		<label for="fullname">full name</label>
		<br>
		<input type="text" id="fullname" name="fullname" value="<%=s.getName()%>">
		<br>
		<br>
		<label for="birthday">birthday</label>
		<br>
		<input type="text" id="birthday" name="birthday" value="<%=s.getBirdthDay().toString()%>">
		<br>
		<br>
		<label for="grade">grade</label>
		<br>
		<input type="text" id="grade" name="grade" value="<%=s.getGrade()%>">
		<br>
		<br>
		<label for="address">address</label>
		<br>
		<input type="text" id="address" name="address" value="<%=s.getAddress() %>">
		<br>
		<br>
		<label for="notes">notes</label>
		<br>
		<input type="text" id="notes" name="notes" value="<%=s.getNotes() %>">
		<br>
		<button onclick="editStudent()">edit</button>
		<br>
		<label>Enter year:</label>
		<br>
		<input type="text" id="searchYear" name="searchYear">
		<br>
		<button onclick="getCoursesByYear()">search for course</button>
		<table>
			<thead>
				<tr>
					<th>id</th>
					<th>name</th>
					<th>lecture</th>
					<th>year</th>
					<th>notes</th>
					<th>score</th>
				</tr>
			</thead>
			<tbody id="course list">
			</tbody>
		</table>
	</div>
</body>
</html>
