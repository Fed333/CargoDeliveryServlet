<html lang="ru"><head>
    <meta charset="UTF-8">
    <title>Login</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

    <!-- Bootstrap CSS -->
    <!--    підключаємо стилі Bootstrap-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- Optional JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="static/css/style.css">
    <link rel="stylesheet" href="static/css/style_auth.css">
    <link rel="stylesheet" href="static/css/style_navbar.css">
    <link rel="stylesheet" href="static/css/style_input_error.css">

</head>
<body>

<div class="content">



<nav class="navbar navbar-expand-lg navbar-dark bg-green-ireland">
    <div class="container-fluid">

        <a class="navbar-brand" href="/main">Cargo Delivery</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/">Directions</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/make_app">Create Application</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/profile">Profile</a>
                </li>
            </ul>
            <div class="dropdown navbar-nav me-2" id="switchLanguageDropdown">
                <a class="nav-link active dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" id="selectLanguageDropdown">
                   <img src="static/images/i18n/language_icon.png" width="24" height="24" class="d-inline-block align-text-top">
                    Language
                </a>
                <ul class="dropdown-menu me-auto" aria-labelledby="selectLanguageDropdown">
                    <li>
                        <a class="dropdown-item" href="#" role="button" id="langEnglish">
                            <img src="static/images/i18n/flags/english_flag.png" width="48" height="24" class="d-inline-block me-4">
                            English
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item" href="#" role="button" id="langUkrainian">
                            <img src="static/images/i18n/flags/ukraine_flag.png" width="48" height="24" class="d-inline-block me-4">
                            Ukrainian
                        </a>
                    </li>
                </ul>
            </div>
                <a class="btn btn-light me-2" href="/login">Sign In</a>
        </div>
    </div>
</nav>    <div class="container mt-4">
   <script src="static/js/formSubmit.js"></script>
   <script src="static/js/localization.js"></script>
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