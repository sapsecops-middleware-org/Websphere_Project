<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>DigiStack Bank - Login Successful</title>
</head>
<body>
    <h1>DigiStack Bank</h1>
    <p>Login successful. Welcome, <%= request.getAttribute("username") %>!</p>
    <p><em>(Session/last-login tracking coming in Sprint 3.)</em></p>
</body>
</html>