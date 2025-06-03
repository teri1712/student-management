function deleteCourse(contextPath, id, year) {

    const query = "id=" + encodeURIComponent(id)
        + "&year=" + encodeURIComponent(year);
    let req = new XMLHttpRequest();
    req.open("DELETE", contextPath + "/management/course?" + query);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send();
    req.onload = () => {
        document.getElementById("status").classList.remove("d-none");
        document.getElementById("status").classList.remove("alert-info");
        if (req.status === 200) {
            document.getElementById("status").classList.add("alert-success");
            document.getElementById("status").innerHTML = "Course deleted successfully";
            window.location.href = contextPath + "/management/course/list";
        } else {
            document.getElementById("status").classList.add("alert-danger");
            document.getElementById("status").innerHTML = "Error deleting course: " + req.responseText;
        }
    }
}

function deleteStudent(contextPath, id, year, student) {
    let req = new XMLHttpRequest();
    const query = "courseId=" + encodeURIComponent(id)
        + "&year=" + encodeURIComponent(year)
        + "&studentId=" + encodeURIComponent(student);
    req.open("DELETE", contextPath + "/management/course-student?" + query);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send();
    req.onload = () => {
        if (req.status === 200) {
            document.getElementById("status").classList.remove("d-none");
            document.getElementById("status").classList.remove("alert-info");
            document.getElementById("status").classList.add("alert-success");
            document.getElementById("status").innerHTML = "Student deleted successfully";
            window.location.href = contextPath + "/management/course?id=" + encodeURIComponent(id) + "&year=" + encodeURIComponent(year);
        } else {
            document.getElementById("status").classList.remove("d-none");
            document.getElementById("status").classList.remove("alert-info");
            document.getElementById("status").classList.add("alert-danger");
            document.getElementById("status").innerHTML = "Error deleting student from course: " + req.responseText;
        }
    }
}
