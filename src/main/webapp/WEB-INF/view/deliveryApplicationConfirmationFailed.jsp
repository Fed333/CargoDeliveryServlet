<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<!DOCTYPE>
<html>
<head>
    <title>Confirmation Failed</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <input name="applicationId" id="applicationIdHiddenInput" value="${requestScope.applicationId}" hidden>

        <div class="row">
            <div class="col d-flex justify-content-center">
                <div class="card" style="min-width: 480px;">
                    <div class="card-body">
                        <h2 class="d-flex justify-content-center"><fmt:message key="delivery.application.confirmation.failed.head"/></h2>
                        <hr>
                        <c:if test="${requestScope.applicationId != null}">
                            <div class="row mb-2">
                                <div class="col">
                                    <label><fmt:message key="delivery.application.confirmation.failed.the-application"/> </label>
                                    <a href="${pageContext.request.contextPath}/application?id=${requestScope.applicationId}">#${requestScope.applicationId}</a>
                                    <label><fmt:message key="delivery.application.confirmation.failed.could-not-been-confirmed"/> </label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <label class="fw-bolder me-2"><fmt:message key="delivery.application.confirmation.failed.application-state"/>:</label>
                                    <label><fmt:message key="delivery-application.state.${requestScope.application.state}"/></label>
                                </div>
                            </div>
                            <hr>
                        </c:if>
                        <div class="row">
                            <div class="col d-flex justify-content-center">
                                <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/applications/review"><fmt:message key="lang.OK"/> </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<script>
</script>
</body>
</html>
