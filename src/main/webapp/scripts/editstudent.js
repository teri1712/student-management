function deleteStudent(contextPath, id) {
    let req = new XMLHttpRequest();
    req.open("DELETE", contextPath + "/management/student/" + id);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send();
    req.onload = () => {
        if (req.status === 200) {
            document.getElementById("status").classList.remove("d-none");
            document.getElementById("status").classList.remove("alert-info");
            document.getElementById("status").classList.add("alert-success");
            document.getElementById("status").innerHTML = "Student deleted successfully";
            window.location.href = contextPath + "/management/student/list";
        } else {
            document.getElementById("status").classList.remove("d-none");
            document.getElementById("status").classList.remove("alert-info");
            document.getElementById("status").classList.add("alert-danger");
            document.getElementById("status").innerHTML = "Error deleting student: " + req.responseText;
        }
    }
}
