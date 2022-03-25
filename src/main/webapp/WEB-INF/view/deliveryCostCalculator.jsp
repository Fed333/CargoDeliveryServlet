<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="application" var="url" value="${pageContext.request.contextPath}/delivery_cost_calculator"/>

<c:choose>
    <c:when test="${requestScope.distance != null}">
        <c:set scope="request" var="selectedCitySenderId" value="${requestScope.distance.cityFrom.id}"/>
        <c:set scope="request" var="selectedCityReceiverId" value="${requestScope.distance.cityTo.id}"/>
    </c:when>
    <c:when test="${requestScope.calculatorRequest != null}">
        <c:set scope="request" var="selectedCitySenderId" value="${requestScope.calculatorRequest.cityFromId}"/>
        <c:set scope="request" var="selectedCityReceiverId" value="${requestScope.calculatorRequest.cityToId}"/>
    </c:when>
</c:choose>

<c:set scope="request" var="weight" value="${requestScope.calculatorRequest.weight}"/>
<c:set scope="request" var="length" value="${requestScope.calculatorRequest.dimensions.length}"/>
<c:set scope="request" var="width" value="${requestScope.calculatorRequest.dimensions.width}"/>
<c:set scope="request" var="height" value="${requestScope.calculatorRequest.dimensions.height}"/>


<!DOCTYPE>
<html>
<head>
    <title>Cost calculator</title>
    <%@include file="jspf/head.jspf"%>
  </head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>

    <script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/validationError.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/deliveryCostCalculatorValidation.js"></script>


    <div class="container mt-4">

        <div class="row mb-2">
            <div class="col d-flex justify-content-center">
                <h1><fmt:message key="delivery-cost-calculator.head"/></h1>
            </div>
        </div>

        <form action="${url}" method="post">

            <div class="form-group row d-flex justify-content-center mt-4">
                <div class="col-2">
                    <label class="col-form-label"><fmt:message key="lang.route"/></label>
                </div>

                <div class="col-5 validation-container" id="routeSelectCol" <c:if test="${requestScope.invalidCityDirectionErrorMessage != null}">data-error="${requestScope.invalidCityDirectionErrorMessage}"</c:if>>
                    <div class="form-group row">
                        <div class="col">
                            <select class="form-select" name="cityFromId" id="cityFromSelect">
                                <c:forEach items="${requestScope.cities}" var="citySender">
                                    <c:set scope="application" var="citySenderId" value="${citySender.id}"/>
                                    <option value="${citySender.id}" id="citySender${citySenderId}">${citySender.name}</option>
                                    <script>selectOption('citySender${citySenderId}', '${citySenderId}'==='${selectedCitySenderId}')</script>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col">
                            <select class="form-select" name="cityToId" id="cityToSelect">
                                <c:forEach items="${requestScope.cities}" var="cityReceiver">
                                    <c:set var="cityReceiverId" value="${cityReceiver.id}"/>
                                    <option value="${cityReceiver.id}" id="cityReceiver${cityReceiverId}">${cityReceiver.name}</option>
                                    <script>selectOption('cityReceiver${cityReceiverId}', '${cityReceiverId}' === '${selectedCityReceiverId}')</script>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

            </div>

            <div class="form-group row d-flex justify-content-center mt-4">
                <div class="col-2">
                    <label class="col-form-label"><fmt:message key="lang.dimensions"/></label>
                </div>

                <div class="col-5">
                    <div class="form-group row">
                        <div class="col validation-container" <c:if test="${requestScope.lengthErrorMessage != null}">data-error="${requestScope.lengthErrorMessage}!"</c:if>>
                            <input class="form-control" type="text" id="lengthInput" name="dimensions.length" value="${length}" placeholder="<fmt:message key="placeholder.length"/>">
                        </div>

                        <div class="col validation-container" <c:if test="${requestScope.widthErrorMessage != null}">data-error="${requestScope.widthErrorMessage}!"</c:if>>
                            <input class="form-control" type="text" id="widthInput" name="dimensions.width" value="${width}" placeholder="<fmt:message key="placeholder.width"/>">
                        </div>

                        <div class="col validation-container" <c:if test="${requestScope.heightErrorMessage != null}">data-error="${requestScope.heightErrorMessage}!"</c:if>>
                            <input class="form-control" type="text" id="heightInput" name="dimensions.height" value="${height}" placeholder="<fmt:message key="placeholder.height"/>">
                        </div>

                        <div class="col">
                            <label class="col-form-label"><fmt:message key="lang.cm"/></label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group row d-flex justify-content-center mt-4">
                <div class="col-2">
                    <label class="col-form-label"><fmt:message key="lang.weight"/></label>
                </div>

                <div class="col-5">
                    <div class="form-group row">
                        <div class="col-3 validation-container" <c:if test="${requestScope.weightErrorMessage != null}">data-error="${requestScope.weightErrorMessage}!"</c:if>>
                            <input class="form-control" type="text" id="weightInput" name="weight" value="${weight}" placeholder="<fmt:message key="placeholder.weight"/>">
                        </div>

                        <div class="col">
                            <label class="col-form-label"><fmt:message key="lang.kg"/></label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group row mt-4">
                <div class="d-flex justify-content-center">
                    <button class="btn btn-primary" type="submit"><fmt:message key="delivery-cost-calculator.calculate-button"/></button>
                </div>
            </div>

        </form>

        <c:if test="${requestScope.cost != null}">
            <div class="row" id="costDeliveryRow">
                <div class="alert alert-success col-auto" role="alert" id="costDeliveryContent">
                    <fmt:message key="lang.delivery-cost"/> ${cost} <fmt:message key="lang.UAH"/>
                </div>
            </div>
            <a class="btn btn-primary" data-bs-toggle="collapse" href="#details" role="button" aria-expanded="false" aria-controls="details">
                <fmt:message key="lang.show-me-more"/>
            </a>
        </c:if>

        <div class="mt-4 collapse" id="details">

            <c:if test="${requestScope.distance != null}">
                <div class="row" id="routeRow">
                    <div class="alert alert-primary col-auto" role="alert" id="routeAlert">
                        <fmt:message key="lang.route"/>:
                        <c:forEach items="${requestScope.distance.route}" var="city" varStatus="loop">
                            ${city.name} <c:if test="${!loop.last}"> - </c:if>
                        </c:forEach>
                    </div>
                </div>

                <div class="row" id="distanceRow">
                    <div class="alert alert-primary col-auto" role="alert" id="distanceAlert">
                        <fmt:message key="lang.distance"/> ${requestScope.distance.distance} <fmt:message key="lang.km"/>
                    </div>
                </div>
            </c:if>

        </div>

    </div>

</div>
<script>
    let inputs = ['lengthInput', 'widthInput', 'heightInput', 'weightInput', 'cityFromSelect', 'cityToSelect']
    let url = '${url}'
    addSwitchLanguageWithUrlClickListeners(url, inputs)
</script>

<script>addRemoveErrorAttributeListener()</script>
<script>

    addRemoveDataErrorTagSelectCityListeners('cityFromSelect', 'cityToSelect', ()=>removeDataErrorTag('routeSelectCol'))
</script>
</body>
</html>
