<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="ru">
<head>
    <title>Login</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>

<div class="content">

    <%@include file="jspf/navbar.jspf"%>

    <div class="container mt-4">
        <div class="row d-flex justify-content-center mt-4">
            <form action="${pageContext.request.contextPath}/login" method="post" style="max-width: 480px;">
                <div class="card">
                    <div class="card-body">
                        <div class="form-group row">
                            <div class="col d-flex justify-content-center mb-2">
                                <h2><fmt:message key="auth.login.head"/></h2>
                            </div>
                        </div>
                        <div class="form-group row justify-content-center mb-2">
                            <input class="form-control" type="text" name="login" placeholder="<fmt:message key="auth.credentials-login"/>" id="input_login">
                        </div>
                        <div class="form-group row justify-content-center mb-2">
                            <input class="form-control" type="password" name="password" placeholder="<fmt:message key="auth.credentials-password"/>" id="input_password">
                        </div>
                        <div class="form-group row mx-5">
                            <label class="col-form label"><a href="/reset_password"><fmt:message key="auth.login.forgot-password"/></a></label>
                        </div>
                        <div class="form-group row mx-5">
                            <label class="col-form label"><fmt:message key="auth.login.have-not-registered"/> <a href="/registration"><fmt:message key="auth.login.register-now"/></a></label>
                        </div>
                        <div class="form-group row justify-content-center">
                            <button class="btn btn-primary mt-4" type="submit" id="button_sign_in"><fmt:message key="auth.login"/></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>


        <form action="/CargoDeliveryServlet/login" method="get">
            <input name="lang" value="en" id="langInput" hidden="">
            <button type="submit" id="submitButton" hidden=""></button>
        </form>
        <script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
        <script>
            function clickSubmitButtonHandler(){
                clickSubmitButton('submitButton')
            }
            addSwitchLanguageWithFormListeners(clickSubmitButtonHandler)
        </script>

    </div>
</div>

</body>

</html>