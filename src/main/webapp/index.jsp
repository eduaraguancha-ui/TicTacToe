<jsp:useBean id="gameBean" scope="session" class="game.GameBean" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tic Tac Toe</title>
</head>
<body>
    <h1>Tres en raya</h1>

    <form action="EntryServlet" method="post">
        <input type="submit" name="User" value="Tú inicias"><br/>
        <input type="submit" name="Computer" value="El ordenador inicia">
    </form>
</body>
</html>