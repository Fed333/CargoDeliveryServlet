<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
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
                        <select class="form-select" name="sort.property" id="sortCriterionSelect">
                            <option value="senderCity.name" id="senderCityOption" <c:if test="${sessionScope.sort == \"senderCity.name\"}">selected</c:if>>
                                Sender City
                            </option>
                            <option value="receiverCity.name" id="receiverCityOption" <c:if test="${sessionScope.sort == \"receiverCity.name\"}">selected</c:if>>
                                Receiver City
                            </option>
                            <option value="distance" id="distanceOption" <c:if test="${sessionScope.sort == \"distance\"}">selected</c:if>>
                                Distance
                            </option>
                        </select>
                    </div>
                    <div class="col-5">
                        <select class="form-select" id="sortOrderSelect" name="sort.order">
                            <option value="ASC" id="ascOption" <c:if test="${sessionScope.sortOrder == \"ASC\"}">selected</c:if>>
                                ASC
                            </option>
                            <option value="DESC" id="descOption" <c:if test="${sessionScope.sortOrder == \"DESC\"}">selected</c:if>>
                                DESC
                            </option>
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
                        <button class="btn btn-primary" type="submit" id="submitButton">Filter</button>
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
                <div class="row">
                    <tag:pager url="${requestScope.url}" submitButtonId="submitButton"/>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
</html>