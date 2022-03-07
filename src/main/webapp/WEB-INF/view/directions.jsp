<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <title>Directions</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <h2>Directions</h2>

        <table class="table table-bordered">
            <thead class="table-primary">
            <tr>
                <th>Directions</th>
                <th>Distance, km</th>
            </tr>
            </thead>
            <tbody class="table-light">
            <c:forEach items="${directions}" var="direction">
                <tr>
                    <td>${direction.senderCity.name} - ${direction.receiverCity.name}</td>
                    <td>${direction.distance}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
    <div class="row">
        <h3>Your Response: ${response}</h3>
    </div>
</div>
</body>
</html>