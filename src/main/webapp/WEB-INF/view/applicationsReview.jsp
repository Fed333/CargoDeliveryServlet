<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<!DOCTYPE>
<html>
<head>
    <title>Applications Review</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>

    <h1>Applications Review</h1>

    <form action="/CargoDeliveryServlet${requestScope.url}" method="get">
        <input name="lang" value="${sessionScope.lang}" id="langInput" hidden>
        <button type="submit" id="submitButton" hidden></button>
    </form>
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



