<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<!DOCTYPE>
<html>
<head>
    <title>Fares</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">

        <div class="row">
            <div class="col d-flex justify-content-center">
                <h1><fmt:message key="lang.fares"/></h1>
            </div>
        </div>

        <div class="row mt-2">
            <div class="col ms-4 me-4">
                <div class="row mb-2">
                    <h2 class="d-flex justify-content-center"><fmt:message key="fares-for-distance.head"/></h2>
                </div>
                <div class="row">
                    <table class="table table-bordered">
                        <thead class="table-primary">
                        <tr>
                            <th><fmt:message key="lang.distance"/> (<fmt:message key="lang.not-inclusive"/>)</th>
                            <th><fmt:message key="lang.price"/>, <fmt:message key="lang.UAH"/></th>
                        </tr>
                        </thead>
                        <tbody class="table-light">
                            <c:forEach items="${requestScope.distanceFares}" var="distanceFare" varStatus="loop">
                                <tr>
                                    <c:choose>
                                        <c:when test="${loop.last}">
                                            <td><fmt:message key="lang.over"/> ${distanceFare.distanceTo}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><fmt:message key="lang.up-to"/> ${distanceFare.distanceTo+1}</td>
                                        </c:otherwise>
                                    </c:choose>
                                <td>${distanceFare.price}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col ms-4 me-4">
                <div class="row mb-2">
                    <h2 class="d-flex justify-content-center"><fmt:message key="fares-for-weight.head"/></h2>
                </div>
                <div class="row">
                    <table class="table table-bordered">
                        <thead class="table-primary">
                        <tr>
                            <th><fmt:message key="lang.weight"/> (<fmt:message key="lang.not-inclusive"/>)</th>
                            <th><fmt:message key="lang.price"/>, <fmt:message key="lang.UAH"/></th>
                        </tr>
                        </thead>
                        <tbody class="table-light">
                            <c:forEach items="${requestScope.weightFares}" var="weightFare" varStatus="loop">
                                <tr>
                                    <c:choose>
                                        <c:when test="${loop.last}">
                                            <td><fmt:message key="lang.for-every"/> ${weightFare.weightTo} <fmt:message key="lang.over"/> ${weightFare.weightTo}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><fmt:message key="lang.up-to"/> ${weightFare.weightTo+1}</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>${weightFare.price}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col ms-4 me-4">
                <div class="row mb-2">
                    <h2 class="d-flex justify-content-center"><fmt:message key="fares-for-dimensions.head"/></h2>
                </div>
                <div class="row">
                    <table class="table table-bordered">
                        <thead class="table-primary">
                        <tr>
                            <th><fmt:message key="lang.volume"/>, <fmt:message key="lang.cm"/><sup><small>3</small></sup> (<fmt:message key="lang.not-inclusive"/>)</th>
                            <th><fmt:message key="lang.price"/>, <fmt:message key="lang.UAH"/></th>
                        </tr>
                        </thead>
                        <tbody class="table-light">
                            <c:forEach items="${requestScope.dimensionsFares}" var="dimensionsFare" varStatus="loop">
                                <tr>
                                    <c:choose>
                                        <c:when test="${loop.last}">
                                            <td><fmt:message key="lang.for-every"/> ${dimensionsFare.dimensionsTo} <fmt:message key="lang.over"/> ${dimensionsFare.dimensionsTo}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><fmt:message key="lang.up-to"/> ${dimensionsFare.dimensionsTo+1}</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>${dimensionsFare.price}</td>
                                </tr>

                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<script>
    addSwitchLanguageWithUrlClickListeners('${pageContext.request.contextPath}/fares', [])
</script>
</body>
</html>
