<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="request" var="filterRequest" value="${requestScope.deliveryApplicationsReviewFilterRequest}"/>
<c:set scope="request" var="selectedSenderCityId" value="${requestScope.filterRequest.cityFromId}"/>
<c:set scope="request" var="selectedReceiverCityId" value="${requestScope.filterRequest.cityToId}"/>
<c:set scope="request" var="sendingDate" value="${requestScope.filterRequest.sendingDate}"/>
<c:set scope="request" var="receivingDate" value="${requestScope.filterRequest.receivingDate}"/>
<c:set scope="request" var="selectedState" value="${requestScope.filterRequest.applicationState}"/>
<c:set scope="request" var="selectedType" value="${requestScope.filterRequest.baggageType}"/>

<!DOCTYPE>
<html>
<head>
    <title>Applications Review</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>

    <script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>

    <div class="container mt-4">
        <div class="row">
            <div class="col d-flex justify-content-center">
                <h1><fmt:message key="delivery-applications.review.head"/></h1>
            </div>
        </div>
        <hr>
        <form action="${pageContext.request.contextPath}/applications/review" method="get">
            <input name="lang" value="${sessionScope.lang}" id="langInput" hidden>
            <div class="row">
                <div class="col" style="max-width: 380px;">
                    <div class="row">
                        <h2 class="d-flex justify-content-center"><fmt:message key="filter.head"/></h2>
                    </div>
                    <div class="row">
                        <div class="col filter-element" filter-name="<fmt:message key="filter.element.sender-city"/>">
                            <select class="form-select" name="cityFromId" id="senderCitySelect">
                                <option value=""><fmt:message key="filter.select.option.city.any"/></option>
                                <c:forEach items="${requestScope.cities}" var="city">
                                    <option value="${city.id}" id="senderCity${city.id}">${city.name}</option>
                                    <script>selectOption('senderCity${city.id}', '${city.id}'==='${selectedSenderCityId}')</script>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col filter-element" filter-name="<fmt:message key="filter.element.receiver-city"/>">
                            <select class="form-select "  name="cityToId" id="receivingCitySelect">
                                <option value=""><fmt:message key="filter.select.option.city.any"/></option>
                                <c:forEach items="${requestScope.cities}" var="city">
                                    <option value="${city.id}" id="receiverCity${city.id}">${city.name}</option>
                                    <script>selectOption('receiverCity${city.id}', '${city.id}'==='${selectedReceiverCityId}')</script>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col filter-element" filter-name="<fmt:message key="filter.element.application-state"/>">
                            <select class="form-select" name="applicationState">
                                <option value=""><fmt:message key="filter.select.option.ALL"/></option>
                                <c:forEach items="${requestScope.states}" var="state">
                                    <option value="${state}" <c:if test="${state == selectedState}">selected</c:if>><fmt:message key="delivery-application.state.${state}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col filter-element" filter-name="<fmt:message key="filter.element.baggage-type"/>">
                            <select name="baggageType" id="baggageTypeSelect" class="form-select">
                                <option value=""><fmt:message key="filter.select.option.ALL"/></option>
                                <c:forEach items="${requestScope.types}" var="type">
                                    <option value="${type}" <c:if test="${type == selectedType}">selected</c:if>><fmt:message key="baggage.type.${type}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6 filter-element" filter-name="<fmt:message key="filter.element.sending-date"/>">
                            <input class="form-control" type="date" name="sendingDate" value="${sendingDate}" id="sendingDateInput">
                        </div>
                        <div class="col-6 filter-element" filter-name="<fmt:message key="filter.element.receiving-date"/>">
                            <input class="form-control" type="date" name="receivingDate" value="${receivingDate}" id="receivingDateInput">
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col d-flex justify-content-center">
                            <button class="btn btn-primary" type="submit" id="filterButton"><fmt:message key="filter.button"/></button>
                        </div>
                    </div>
                </div>
                <div class="col ms-4">
                    <c:choose>
                        <c:when test="${requestScope.applicationsPage != null && requestScope.applicationsPage.content.size() > 0 }">

                            <div class="row alert alert-dark mb-2">
                                <div class="col-1">
                                    #id
                                </div>
                                <div class="col-5">
                                    <fmt:message key="lang.direction"/>
                                </div>
                                <div class="col-3">
                                    <fmt:message key="delivery-applications.review.dates"/>
                                </div>
                                <div class="col-1" style="min-width: 110px;">
                                    <fmt:message key="lang.price"/>
                                </div>
                                <div class="col-1">
                                    <fmt:message key="delivery-application.state"/>
                                </div>
                            </div>

                            <c:forEach items="${requestScope.applicationsPage.content}" var="application">
                                <div class="row alert alert-primary mb-2">
                                    <div class="col-1">
                                        <a class="link" href="${pageContext.request.contextPath}/application?id=${application.id}">#${application.id}</a>
                                    </div>
                                    <div class="col-5">
                                        ${application.senderAddress.city.name} - ${application.receiverAddress.city.name}
                                    </div>
                                    <div class="col-3">
                                            ${application.sendingDate} - ${application.receivingDate}
                                    </div>
                                    <div class="col" style="max-width: 110px;">
                                            ${application.price} <fmt:message key="lang.UAH"/>
                                    </div>
                                    <div class="col">
                                        <fmt:message key="delivery-application.state.${application.state}"/>
                                    </div>
                                </div>
                            </c:forEach>
                            <tag:pager prefix="" page="${requestScope.applicationsPage}" submitButtonId="filterButton"/>
                        </c:when>
                        <c:otherwise>
                            <div class="row alert alert-secondary" role="alert">
                                <fmt:message key="delivery-applications.review.no-applications-found"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    function clickSubmitButtonHandler(){
        clickSubmitButton('filterButton')
    }
    addSwitchLanguageWithFormListeners(clickSubmitButtonHandler)
</script>

</body>
</html>



