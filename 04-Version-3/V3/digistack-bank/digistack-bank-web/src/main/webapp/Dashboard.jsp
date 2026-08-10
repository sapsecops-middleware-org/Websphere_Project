<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>DigiStack Bank - Dashboard</title>
</head>
<body>
    <h1>DigiStack Bank</h1>

    <%
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        String lastLogin = (session != null) ? (String) session.getAttribute("lastLogin") : null;

        if (username == null || lastLogin == null) {
            response.sendRedirect("Login");
            return;
        }
    %>

    <p>Welcome, <%= username %>!</p>
    <p><strong>Last login:</strong> <%= lastLogin %></p>

    <form action="Logout" method="post">
        <button type="submit">Logout</button>
    </form>

</body>
</html>