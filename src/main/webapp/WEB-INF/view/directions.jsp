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
        <h2 class="d-flex justify-content-center">Directions</h2>
        <div class="d-flex justify-content-center">
            <form class="col-6" action="${requestScope.url}" method="get">
                <div class="form-group row mt-2">
                    <div class="col-2">
                        <label class="col-form-label">Sorting</label>
                    </div>
                    <div class="col-5">
                        <select class="form-select" name="sort" id="sortCriterionSelect">
                            <option value="senderCity.name" id="senderCityOption">Sender City</option>
                            <option value="receiverCity.name" id="receiverCityOption">Receiver City</option>
                            <option value="distance" id="distanceOption">Distance</option>
                        </select>
                    </div>
                    <div class="col-5">
                        <select class="form-select" id="sortOrderSelect">
                            <option value="asc" id="ascOption">ASC</option>
                            <option value="desc" id="descOption">DESC</option>
                        </select>
                    </div>
                </div>
                <div class="form-group row mt-2">

                    <div class="col-2">
                        <label class="col-form-label">Direction</label>
                    </div>

                    <div class="col-4">
                        <input type="text" class="form-control" name="senderCityName" id="senderCityNameId" value="${sessionScope.senderCity != null ? sessionScope.senderCity : ""}" placeholder="Sender city"/>
                    </div>

                    <div class="col-4">
                        <input type="text" class="form-control" name="receiverCityName" id="receiverCityNameId" value="${sessionScope.receiverCity != null ? sessionScope.receiverCity : ""}" placeholder="Receiver city"/>
                    </div>

                    <div class="col-2">
                        <button class="btn btn-primary" type="submit">Filter</button>
                    </div>

                </div>
                <div class="row mt-4">
                    <table class="table table-bordered">
                        <thead class="table-primary">
                        <tr>
                            <th>Directions</th>
                            <th>Distance, km</th>
                        </tr>
                        </thead>
                        <tbody class="table-light">
                        <c:forEach items="${requestScope.directions}" var="direction">
                            <tr>
                                <td>${direction.senderCity.name} - ${direction.receiverCity.name}</td>
                                <td>${direction.distance}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

            </form>
        </div>
    </div>

</div>
</body>
</html>