<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="model.Student"%>
<%@page import="java.util.ArrayList"%>
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
<script src="./scripts/studentsession.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Session</title>
</head>
<body>
	<%
	ArrayList<Student> l = (ArrayList<Student>) request.getAttribute("student list");
	%>
	<div class="navigate">
		<a href="coursesession?cc=show%20course%20session">course session</a>
		<a href="studentsession?cc=show%20student%20session">student session</a>
	</div>
	<div class="content">
		<p id="status"></p>
		<br>
		<a href="addstudent.jsp">add new student</a>
		<br>
		<label for="name">find by name:</label>
		<input type="text" id="name" name="name">
		<button onclick="findByName()">search</button>
		<br>
		<br>
		<button onclick="sortBy('name')">sort by name</button>
		<button onclick="sortBy('grade')">sort by grade</button>
		<br>
		<table>
			<thead>
				<tr>
					<th>id</th>
					<th>fullname</th>
					<th>birthday</th>
					<th>grade</th>
					<th>address</th>
					<th>notes</th>
				</tr>
			</thead>
			<tbody id="student list">
				<%
				if (l != null)
					for (Student i : l) {
				%>
				<tr>
					<td><%=i.getId()%></td>
					<td><%=i.getName()%></td>
					<td><%=i.getBirdthDay()%></td>
					<td><%=i.getGrade()%></td>
					<td><%=i.getAddress()%></td>
					<td><%=i.getNotes()%></td>
					<td>
						<a href="studentsession?cc=show%20edit%20student%20session&id=<%=i.getId()%>" class="edit-button">edit</a>
						<button onclick="deleteStudent('<%=i.getId()%>')">delete</button>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>
