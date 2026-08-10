<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>DigiStack Bank - Login</title>
</head>
<body>
    <h1>DigiStack Bank</h1>
    <h2>Login</h2>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <p style="color:red;"><%= errorMessage %></p>
    <% } %>

    <form action="Login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required="required" /><br /><br />

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required="required" /><br /><br />

        <button type="submit">Login</button>
    </form>

</body>
</html>