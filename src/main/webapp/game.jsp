<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:useBean id="gameBean" scope="session" class="game.GameBean" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tres en raya</title>
</head>
<body>

<h1>Tres en raya</h1>

<table border="10">
    <c:forEach var="line" items="${gameBean.gridLines}">
        <tr>
            <c:forEach var="cell" items="${gameBean.getGridStatus(line)}">
                <td>
                    <c:choose>
                        <c:when test="${cell.state == 'X'}">
                            <img src="img/state_x.png" alt="X"/>
                        </c:when>

                        <c:when test="${cell.state == 'O'}">
                            <img src="img/state_o.png" alt="O"/>
                        </c:when>

                        <c:otherwise>
                            <c:if test="${winner == null}">
                                <a href="GameServlet?Line=${cell.line}&Col=${cell.col}">
                            </c:if>

                            <img src="img/state_null.png" alt="empty"/>

                            <c:if test="${winner == null}">
                                </a>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>

<c:if test="${winner != null}">
    <h2>${winner} !</h2>
    <form action="index.jsp" method="get">
        <input type="submit" value="JUGAR DE NUEVO">
    </form>
</c:if>

</body>
</html>