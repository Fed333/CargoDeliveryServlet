<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="application" var="cityName" value="${requestScope.user.address.city.name}"/>
<c:set scope="application" var="street" value="${requestScope.user.address.street}"/>
<c:set scope="application" var="houseNumber" value="${requestScope.user.address.houseNumber}"/>

<!DOCTYPE>
<html>
<head>
    <title>Profile</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <div class="row">
            <div class="col-3">
                <h1 class="d-flex justify-content-center mb-4"><fmt:message key="lang.profile"/></h1>

                <ul class="list-group">
                    <li class="list-group-item">
                        <fmt:message key="lang.login"/>: ${requestScope.user.login}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.name"/>: ${requestScope.user.name != null ? requestScope.user.name : "-"}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.surname"/>: ${requestScope.user.surname != null ? requestScope.user.surname : "-"}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.phone"/>: ${requestScope.user.phone != null ? requestScope.user.phone : "-"}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.email"/>: ${requestScope.user.email != null ? requestScope.user.email : "-"}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.address"/>: ${cityName} <fmt:message key="address.street"/> ${street}, <fmt:message key="address.house"/> ${houseNumber}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.balance"/>: ${requestScope.user.cash != null ? requestScope.user.cash : "0.0"} <fmt:message key="lang.UAH"/>
                    </li>
                </ul>
            </div>
        </div>


        <form action="/CargoDeliveryServlet/profile" method="get">
            <input name="lang" value="${sessionScope.lang}" id="langInput" hidden>
            <button type="submit" id="submitButton" hidden></button>
        </form>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<script>
    function clickSubmitButtonHandler(){
        clickSubmitButton('submitButton')
    }
    addSwitchLanguageWithFormListeners(clickSubmitButtonHandler)
</script>
</body>
</html>
