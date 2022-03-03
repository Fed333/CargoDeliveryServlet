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
        <script src="../../static/js/formSubmit.js"></script>
        <script src="../../static/js/localization.js"></script>
        <div class="row d-flex justify-content-center mt-4">
            <form action="/login" method="post" style="max-width: 480px;">
                <input type="hidden" name="_csrf" value="9bdeb140-e24e-418e-8ed6-d3a9d03365dd">
                <div class="card">
                    <div class="card-body">
                        <div class="form-group row">
                            <div class="col d-flex justify-content-center mb-2">
                                <h2>Please Sign In</h2>
                            </div>
                        </div>
                        <div class="form-group row justify-content-center mb-2">
                            <input class="form-control" type="text" name="username" placeholder="Login" id="input_login">
                        </div>
                        <div class="form-group row justify-content-center mb-2">
                            <input class="form-control" type="password" name="password" placeholder="Password" id="input_password">
                        </div>
                        <div class="form-group row mx-5">
                            <label class="col-form label"><a href="/reset_password">Forgot password</a></label>
                        </div>
                        <div class="form-group row mx-5">
                            <label class="col-form label">Haven't registered yet? <a href="/registration">Register now!</a></label>
                        </div>
                        <div class="form-group row justify-content-center">
                            <button class="btn btn-primary mt-4" type="submit" id="button_sign_in">Sign In</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>


        <form action="/login" method="get">
            <input name="lang" value="en" id="langInput" hidden="">
            <button type="submit" id="submitButton" hidden=""></button>
        </form>

        <script>
            function clickSubmitButtonHandler(){
                clickSubmitButton('submitButton')
            }
            addEventListeners(clickSubmitButtonHandler)
        </script>

    </div>
</div>

</body>

</html>