<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="request" var="name" value="${requestScope.userRequest.name}"/>
<c:set scope="request" var="surname" value="${requestScope.userRequest.surname}"/>
<c:set scope="request" var="email" value="${requestScope.userRequest.email}"/>
<c:set scope="request" var="phone" value="${requestScope.userRequest.phone}"/>
<c:set scope="request" var="login" value="${requestScope.userRequest.login}"/>

<c:set scope="request" var="address" value="${requestScope.userRequest.address}"/>
<c:set scope="request" var="selectedCityId" value="${requestScope.userRequest.address.cityId}"/>
<c:set scope="request" var="streetName" value="${requestScope.userRequest.address.streetName}"/>
<c:set scope="request" var="houseNumber" value="${requestScope.userRequest.address.houseNumber}"/>

<!DOCTYPE>
<html>
<head>
    <title>Registration</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">

        <script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/validationError.js"></script>

        <div class="row d-flex justify-content-center mt-4">
            <form action="${pageContext.request.contextPath}/registration" method="post" style="max-width: 860px;">
                <div class="card">
                    <div class="card-body">
                        <div class="form-group row">
                            <div class="col d-flex justify-content-center">
                                <h2><fmt:message key="auth.registration.head"/></h2>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center mb-2">
                            <h3><fmt:message key="auth.registration.personal-data.head"/></h3>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center">
                            <div class="col-4 me-4">
                                <div class="validation-container" <c:if test="${requestScope.nameErrorMessage != null}">data-error="${requestScope.nameErrorMessage}"</c:if>>
                                    <input class="form-control mt-2" type="text" name="name" id="nameInput" value="${name}" placeholder="<fmt:message key="lang.name"/>">
                                </div>
                                <div class="validation-container" <c:if test="${requestScope.surnameErrorMessage != null}">data-error="${requestScope.surnameErrorMessage}"</c:if>>
                                    <input class="form-control mt-2" type="text" name="surname" id="surnameInput" value="${surname}" placeholder="<fmt:message key="lang.surname"/>">
                                </div>
                            </div>
                            <div class="col-4 validation-container">
                                <div class="validation-container">
                                    <input class="form-control mt-2" type="email" name="email" id="emailInput" value="${email}" placeholder="<fmt:message key="auth.email.placeholder"/>">
                                </div>
                                <div class="validation-container" <c:if test="${requestScope.phoneErrorMessage != null}">data-error="${requestScope.phoneErrorMessage}" </c:if>>
                                    <input class="form-control mt-2" type="text" maxlength="13" name="phone" value="${phone}" id="phoneInput" placeholder="<fmt:message key="lang.phone"/>">
                                </div>
                            </div>
                        </div>

                    </div>
                    <hr>
                    <div class="form-group row">
                        <div  class="d-flex justify-content-center mb-2">
                            <h3><fmt:message key="auth.registration.address-data.head"/></h3>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-4">
                            <select class="form-select" name="address.cityId" id="cityInput">
                                <c:forEach items="${requestScope.cities}" var="city">
                                    <c:set scope="request" var="cityId" value="${city.id}"/>
                                    <option value="${city.id}" id="city${cityId}">${city.name}</option>
                                    <script>selectOption('city${cityId}', '${cityId}'==='${selectedCityId}')</script>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <input class="form-control" type="text" name="address.streetName" value="${streetName}" id="streetInput" placeholder="<fmt:message key="auth.registration.street-data"/>">
                        </div>
                        <div class="col-4">
                            <input class="form-control" type="text" name="address.houseNumber" value="${houseNumber}" id="houseNumberInput" placeholder="<fmt:message key="auth.registration.house-number-data"/>">
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center mb-2">
                            <h3><fmt:message key="auth.registration.credentials-data.head"/></h3>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-4 validation-container" <c:if test="${requestScope.loginErrorMessage != null}">data-error="${requestScope.loginErrorMessage}"</c:if>>
                            <input class="form-control" type="text" name="login" value="${login}" id="loginInput" placeholder="<fmt:message key="auth.credentials-login"/>">
                        </div>
                        <div class="col-4 validation-container" <c:if test="${requestScope.passwordErrorMessage != null}">data-error="${requestScope.passwordErrorMessage}"</c:if>>
                            <input class="form-control" type="password" name="password" id="passwordInput" placeholder="<fmt:message key="auth.credentials-password"/>">
                        </div>
                        <div class="col-4 validation-container" <c:if test="${requestScope.duplicatePasswordErrorMessage != null}">data-error="${requestScope.duplicatePasswordErrorMessage}"</c:if>>
                            <input class="form-control" type="password" name="duplicatePassword" id="duplicatePasswordInput" placeholder="<fmt:message key="auth.credentials-duplicate-password"/>">
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="col d-flex justify-content-center">
                            <button class="btn btn-primary" id="button_register" type="submit"><fmt:message key="auth.registration.button"/></button>
                        </div>
                    </div>
                </div>
                <input name="lang" value="${sessionScope.lang}" id="langInput" hidden>
            </form>
        </div>
        <script>addRemoveErrorAttributeListener()</script>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<script>
    let inputs = ['nameInput', 'surnameInput', 'emailInput', 'phoneInput', 'streetInput', 'houseNumberInput', 'loginInput', 'cityInput']
    let url = '${pageContext.request.contextPath}/registration'
    addSwitchLanguageWithUrlClickListeners(url, inputs)
</script>

</body>
</html>