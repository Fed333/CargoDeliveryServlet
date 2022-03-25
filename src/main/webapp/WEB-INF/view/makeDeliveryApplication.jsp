<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="request" var="url" value="${pageContext.request.contextPath}/make_app"/>

<c:set scope="request" var="deliveredBaggage" value="${requestScope.deliveryApplicationRequest.deliveredBaggageRequest}"/>
<c:set scope="request" var="volume" value="${requestScope.deliveredBaggage.volume}"/>
<c:set scope="request" var="weight" value="${requestScope.deliveredBaggage.weight}"/>
<c:set scope="request" var="selectedType" value="${requestScope.type}"/>
<c:set scope="request" var="description" value="${requestScope.description}"/>

<c:set scope="request" var="senderAddress" value="${requestScope.deliveryApplicationRequest.senderAddress}"/>
<c:set scope="request" var="senderCityId" value="${requestScope.senderAddress.cityId}"/>
<c:set scope="request" var="senderStreetName" value="${requestScope.senderAddress.streetName}"/>
<c:set scope="request" var="senderHouseNumber" value="${requestScope.senderAddress.houseNumber}"/>

<c:set scope="request" var="receiverAddress" value="${requestScope.deliveryApplicationRequest.receiverAddress}"/>
<c:set scope="request" var="receiverCityId" value="${requestScope.receiverAddress.cityId}"/>
<c:set scope="request" var="receiverStreetName" value="${requestScope.receiverAddress.streetName}"/>
<c:set scope="request" var="receiverHouseNumber" value="${requestScope.receiverAddress.houseNumber}"/>

<c:set scope="request" var="sendingDate" value="${requestScope.deliveryApplicationRequest.sendingDate}"/>
<c:set scope="request" var="receivingDate" value="${requestScope.deliveryApplicationRequest.receivingDate}"/>

<!DOCTYPE>
<html>
<head>
    <title>Make Application</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <script src="/static/js/localization.js"></script>
        <script src="/static/js/validationError.js"></script>

        <div class="row">
            <div class="col d-flex justify-content-center">
                <h1><fmt:message key="make-delivery-application.head"/></h1>
            </div>
        </div>

        <div class="row d-flex justify-content-center mt-4" >
            <form action="${url}" method="post" style="max-width: 920px;">
                <div class="card">
                    <div class="card-body">
                        <div class="form-group row">
                            <div class="col d-flex justify-content-center mb-2">
                                <h2><fmt:message key="lang.baggage"/></h2>
                            </div>
                        </div>
                        <div class="form-group row justify-content-center mt-4 mb-2">
                            <div class="col-5 me-4">
                                <div class="row">
                                    <div class="col-4">
                                        <label class="col-form-label"><fmt:message key="lang.volume"/></label>
                                    </div>

                                    <div class="col validation-container" <c:if test="${requestScope.volumeErrorMessage != null}">data-error="${requestScope.volumeErrorMessage}"</c:if>>
                                        <input class="form-control" name="deliveredBaggageRequest.volume" id="volumeInput" type="text" value="${volume}">
                                    </div>
                                    <div class="col-2">
                                        <label class="col-form-label"><fmt:message key="lang.cm"/><sup><small>3</small></sup></label>
                                    </div>
                                </div>

                                <div class="row mt-2">
                                    <div class="col-4">
                                        <label class="col-form-label"><fmt:message key="lang.weight"/></label>
                                    </div>
                                    <div class="col validation-container" <c:if test="${requestScope.weightErrorMessage != null}">data-error="${requestScope.weightErrorMessage}"</c:if>>
                                        <input class="form-control" name="deliveredBaggageRequest.weight" id="weightInput" type="text" value="${weight}">
                                    </div>
                                    <div class="col-2">
                                        <label class="col-form-label"><fmt:message key="lang.kg"/></label>
                                    </div>
                                </div>

                            </div>

                            <div class="col-5">
                                <div class="row">
                                    <div class="col-4">
                                        <label class="col-form-label"><fmt:message key="lang.type"/></label>
                                    </div>
                                    <div class="col">
                                        <select class="form-select" name="deliveredBaggageRequest.type" id="baggageTypeSelect" >
                                            <c:forEach items="${requestScope.types}" var="type" varStatus="loop">
                                                <option value="${type}" id="type${loop.index}" <c:if test="${type == selectedType}">selected</c:if>><fmt:message key="baggage.type.${type}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row mt-2">
                                    <div class="col-4">
                                        <label class="col-form-label"><fmt:message key="lang.description"/></label>
                                    </div>
                                    <div class="col">
                                        <input class="form-control" name="deliveredBaggageRequest.description" id="descriptionInput" type="text" value="${description}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="form-group row">
                            <div class="d-flex justify-content-center mb-2">
                                <h2><fmt:message key="lang.address"/></h2>
                            </div>
                        </div>

                        <div class="form-group row justify-content-center mt-2 mb-2">
                            <div class="col me-4">
                                <div class="row mb-4">
                                    <h3 class="d-flex justify-content-center"><fmt:message key="lang.sending"/></h3>
                                </div>
                                <div class="row">
                                    <div class="col-5">
                                        <label class="col-form-label"><fmt:message key="lang.city"/></label>
                                    </div>
                                    <div class="col">
                                        <select class="form-select" name="senderAddress.cityId" id="senderAddressSelect">
                                            <c:forEach items="${requestScope.cities}" var="city">
                                                <option value="${city.id}" <c:if test="${city.id == senderCityId}">selected</c:if>>${city.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row mt-2">
                                    <div class="col-5">
                                        <label class="col-form-label"><fmt:message key="lang.street"/></label>
                                    </div>
                                    <div class="col validation-container" <c:if test="${requestScope.senderStreetErrorMessage != null}" >data-error=${requestScope.senderStreetErrorMessage}</c:if>>
                                        <input class="form-control" name="senderAddress.streetName" id="senderStreetInput" value="${senderStreetName}">
                                    </div>
                                </div>
                                <div class="row mt-2">
                                    <div class="col-5">
                                        <label class="col-form-label"><fmt:message key="lang.house-number"/></label>
                                    </div>
                                    <div class="col validation-container" <c:if test="${requestScope.senderHouseNumberErrorMessage != null}">data-error=${requestScope.senderHouseNumberErrorMessage}</c:if>>
                                        <input class="form-control" name="senderAddress.houseNumber" id="senderHouseNumberInput" value="${senderHouseNumber}">
                                    </div>
                                </div>
                            </div>

                            <div class="col ms-4">
                                <div class="row mb-4">
                                    <h3 class="d-flex justify-content-center"><fmt:message key="lang.receiving"/></h3>
                                </div>
                                <div class="row">
                                    <div class="col-5">
                                        <label class="col-form-label"><fmt:message key="lang.city"/></label>
                                    </div>
                                    <div class="col">
                                        <select class="form-select" name="receiverAddress.cityId" id="receiverAddressSelect">
                                            <c:forEach items="${requestScope.cities}" var="city">
                                                <option value="${city.id}" <c:if test="${city.id == receiverCityId}">selected</c:if>>${city.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row mt-2">
                                    <div class="col-5">
                                        <label class="col-form-label"><fmt:message key="lang.street"/></label>
                                    </div>
                                    <div class="col validation-container" <c:if test="${requestScope.receiverStreetErrorMessage != null}" >data-error=${requestScope.receiverStreetErrorMessage}</c:if>>
                                        <input class="form-control" name="receiverAddress.streetName" id="senderStreetInput" value="${receiverStreetName}">
                                    </div>
                                </div>
                                <div class="row mt-2">
                                    <div class="col-5">
                                        <label class="col-form-label"><fmt:message key="lang.house-number"/></label>
                                    </div>
                                    <div class="col validation-container" <c:if test="${requestScope.receiverHouseNumberErrorMessage != null}">data-error=${requestScope.receiverHouseNumberErrorMessage}</c:if>>
                                        <input class="form-control" name="receiverAddress.houseNumber" id="receiverHouseNumberInput" value="${receiverHouseNumber}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="form-group row">
                            <div class="d-flex justify-content-center mb-2">
                                <h2><fmt:message key="lang.date"/></h2>
                            </div>
                        </div>
                        <div class="form-group row justify-content-center mt-2 mb-2">
                            <div class="col-4 me-4">
                                <div class="row mb-2">
                                    <h3 class="d-flex justify-content-center mb-4"><fmt:message key="lang.sending"/></h3>
                                    <div class="validation-container" <c:if test="${requestScope.sendingDateErrorMessage != null}">data-error="${requestScope.sendingDateErrorMessage}"</c:if>>
                                        <input class="form-control" type="date" name="sendingDate" id="sendingDateInput" value="${sendingDate}">
                                    </div>
                                </div>
                            </div>

                            <div class="col-4 ms-4">
                                <div class="row mb-2">
                                    <h3 class="d-flex justify-content-center mb-4"><fmt:message key="lang.receiving"/></h3>
                                    <div class="validation-container" <c:if test="${requestScope.receivingDateErrorMessage != null}">data-error="${requestScope.receivingDateErrorMessage}"</c:if>>
                                        <input class="form-control" type="date" name="receivingDate" id="receivingDateInput" value="${receivingDate}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="form-group row">
                            <div class="d-flex justify-content-center">
                                <button class="btn btn-primary" type="submit"><fmt:message key="make-delivery-application.apply-application"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <script>
        let inputs = [
            'volumeInput', 'weightInput', 'descriptionInput', 'sendingDateInput', 'receivingDateInput',
            'baggageTypeSelect', 'senderAddressSelect', 'senderStreetInput', 'senderHouseNumberInput',
            'receiverAddressSelect', 'receiverStreetInput', 'receiverHouseNumberInput'
        ]

        let url = '${url}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>

    <script>addRemoveErrorAttributeListener()</script>

</div>
</body>
</html>
