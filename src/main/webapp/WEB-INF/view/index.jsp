<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<!DOCTYPE>
<html>
<head>
    <title>Index</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <h2><fmt:message key="lang.greeting"/></h2>
    </div>
    <form action="/CargoDeliveryServlet/" method="get">
        <input name="lang" value="${sessionScope.lang}" id="langInput" hidden>
        <button type="submit" id="submitButton" hidden></button>
    </form>
</div>
<script src="static/js/formSubmit.js"></script>
<script src="static/js/localization.js"></script>
<script>
    function clickSubmitButtonHandler(){
        clickSubmitButton('submitButton')
    }
    addSwitchLanguageWithFormListeners(clickSubmitButtonHandler)
</script>
</body>
</html>
