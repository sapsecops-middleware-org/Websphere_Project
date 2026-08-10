<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>DigiStack Bank</title>
</head>
<body>
    <h1>DigiStack Bank</h1>

    <% String configValue = (String) request.getAttribute("configValue"); %>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>

    <% if (configValue != null) { %>
        <p><strong>Live DB Read:</strong> <%= configValue %></p>
    <% } else { %>
        <p style="color:red;"><strong>DB Read Failed:</strong> <%= errorMessage %></p>
    <% } %>

</body>
</html>
