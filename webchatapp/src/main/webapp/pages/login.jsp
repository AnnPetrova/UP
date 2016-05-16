<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
    <link type="text/css" rel="stylesheet" href="css/logStyle.css"/>
    <script src="js/scripts.js"></script>
</head>
<body onload="run();">
<div class="main">
    <form action="/chat" method="post">
        <div class="inputName">
            <p>Login</p>
            <input type="text" name="login" id="name" size="20" placeholder="Login"/>
        </div>
        <div class="inputPass">
            <p>Password</p>
            <input type="password" name="pass" id="password" size="20" placeholder="Password"/>
        </div>
        <p>
            <button type="submit" id="logBut" class="logBut">Log in</button>
        </p>
    </form>
    <c class="error">
        <c:choose>
            <c:when test="${requestScope.errorMsg!=null}"><c:out value="${requestScope.errorMsg}"></c:out></c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
</div>


</body>
</html>