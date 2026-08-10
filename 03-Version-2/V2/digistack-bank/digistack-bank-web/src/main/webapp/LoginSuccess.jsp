<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>DigiStack Bank - Login Successful</title>
</head>
<body>
    <h1>DigiStack Bank</h1>

    <%
        String username = (String) session.getAttribute("username");
        String lastLogin = (String) session.getAttribute("lastLogin");
    %>

    <p>Login successful. Welcome, <%= username %>!</p>
    <p><strong>Last login:</strong> <%= lastLogin %></p>
    <p><strong>Session ID:</strong> <%= session.getId() %></p>

    <p><em>(Logout coming in Sprint 4.)</em></p>
</body>
</html>