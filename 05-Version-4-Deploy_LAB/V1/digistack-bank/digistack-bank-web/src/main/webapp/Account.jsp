<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>DigiStack Bank - My Account</title>
</head>
<body>
    <h1>DigiStack Bank</h1>
    <h2>My Account</h2>

    <%
        String accountNumber = (String) request.getAttribute("accountNumber");
        Object balance = request.getAttribute("balance");
        String successMessage = (String) request.getAttribute("successMessage");
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>

    <% if (successMessage != null) { %>
        <p style="color:green;"><%= successMessage %></p>
    <% } %>

    <% if (errorMessage != null) { %>
        <p style="color:red;"><%= errorMessage %></p>
    <% } %>

    <% if (accountNumber != null) { %>
        <p><strong>Account Number:</strong> <%= accountNumber %></p>
        <p><strong>Current Balance:</strong> <%= balance %></p>
    <% } %>

    <h3>Deposit</h3>
    <form action="Account" method="post">
        <input type="hidden" name="action" value="deposit" />
        <label for="depositAmount">Amount:</label>
        <input type="text" id="depositAmount" name="amount" required="required" />
        <button type="submit">Deposit</button>
    </form>

    <h3>Withdraw</h3>
    <form action="Account" method="post">
        <input type="hidden" name="action" value="withdraw" />
        <label for="withdrawAmount">Amount:</label>
        <input type="text" id="withdrawAmount" name="amount" required="required" />
        <button type="submit">Withdraw</button>
    </form>

    <p><a href="Account">Refresh Balance</a> | <a href="Dashboard.jsp">Back to Dashboard</a></p>

</body>
</html>