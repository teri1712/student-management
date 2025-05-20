<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="model.Course"%>
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
<script type="text/javascript" src="./scripts/coursesession.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Session</title>
</head>
<body>
	<%
	ArrayList<Course> l = (ArrayList<Course>) request.getAttribute("course list");
	%>
	<div class="navigate">
		<a href="coursesession?cc=show%20course%20session"> course session</a>
		<a href="studentsession?cc=show%20student%20session">student session</a>
	</div>
	<div class="content">
		<p id="status"></p>
		<br>
		<a href="addcourse.jsp">add new course</a>
		<br>
		<label for="name">find by name: </label>
		<input type="text" id="name" name="name">
		<button onclick="findByName()">search</button>
		<br>
		<br>
		<button onclick="sortBy('name')">sort by name</button>
		<br>
		<table>
			<thead>
				<tr>
					<th>id</th>
					<th>name</th>
					<th>year</th>
					<th>lecture</th>
					<th>notes</th>
				</tr>
			</thead>
			<tbody id="course list">
				<%
				if (l != null)
					for (Course i : l) {
				%>
				<tr>
					<td><%=i.getId()%></td>
					<td><%=i.getName()%></td>
					<td><%=i.getYear()%></td>
					<td><%=i.getLecture()%></td>
					<td><%=i.getNote()%></td>
					<td>
						<a href="coursesession?cc=show%20edit%20course%20session&id=<%=i.getId()%>&year=<%=i.getYear()%>" class="edit-button">edit</a>
						<button onclick="deleteCourse('<%=i.getId()%>','<%=i.getYear()%>')">delete</button>
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
