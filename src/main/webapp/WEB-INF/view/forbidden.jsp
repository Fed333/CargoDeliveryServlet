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
    <script src="/static/js/localization.js"></script>
    <div class="row">
        <div class="col d-flex justify-content-center">
            <div class="row">
                <h1 class="d-flex justify-content-center mb-4"><fmt:message key="lang.forbidden"/></h1>
                <div class="alert alert-danger">
                    <fmt:message key="forbidden.no-permission"/>
                </div>
            </div>
        </div>
    </div>
    <script>addSwitchLanguageWithUrlClickListeners('${url}', [])</script>
</div>
</body>
</html>


