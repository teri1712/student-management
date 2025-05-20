<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="model.Course"%>
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
<script src="./scripts/editcourse.js"></script>
<body>
	<%
	Course c = (Course) request.getAttribute("course");
	%>
	<div class="navigate">
		<a href="coursesession?cc=show%20course%20session">course session</a>
		<a href="studentsession?cc=show%20student%20session">student session</a>
	</div>
	<div class="content">
		<p id="status"></p>
		<label for="id">Id:</label>
		<br>
		<input type="text" id="id" name="id" value="<%=c.getId()%>" readonly>
		<br>
		<label for="name">name</label>
		<br>
		<input type="text" id="name" name="name" value="<%=c.getName()%>">
		<br>
		<br>
		<label for="lecture">lecture</label>
		<br>
		<input type="text" id="lecture" name="lecture" value="<%=c.getLecture()%>">
		<br>
		<br>
		<label for="year">year</label>
		<br>
		<input type="text" id="year" name="year" value="<%=c.getYear()%>" readonly>
		<br>
		<br>
		<label for="notes">Notes</label>
		<br>
		<input type="text" id="notes" name="notes" value="<%=c.getNote()%>">
		<br>
		<button onclick="editCourse()">edit</button>
		<br>
		<br>
		<label for="idStudent">id student</label>
		<br>
		<input type="text" id="idStudent" name="idStudent">
		<br>
		<button onclick="addStudent()">add student by id</button>
		<br>
		<br>
		<button onclick="findStudentsFromCourse()">get student list</button>
		<table>
			<thead>
				<tr>
					<th>id</th>
					<th>name</th>
					<th>grade</th>
					<th>birthday</th>
					<th>address</th>
					<th>notes</th>
					<th>score</th>
				</tr>
			</thead>
			<tbody id="student list">
			</tbody>
		</table>
	</div>
</body>
</html>
