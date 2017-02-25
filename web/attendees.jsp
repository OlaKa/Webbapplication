<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Nyårsfest</title>
</head>
<body>
<header>
    <h1>Välkommen till nyårsfest 2017!</h1>

</header>

<nav>
    <ul>
        <li><a href="default.html">Hem</a></li>
        <li><a href="news.html">Om</a></li>
        <li><a href="contact.html">Anmäl dig</a></li>
        <li><a href="attendees.html" class="active">Anmälda</a></li>
    </ul>

</nav>
<section>
    <h1>Anmälda deltagare</h1>
    <table>
        <tr>
            <th>Förnamn</th>
            <th>Efternamn</th>
            <th>Email</th>
        </tr>
        <tbody>
        <c:forEach items="${attendeeList}" var="attendee">
            <tr>
                <td><c:out value="${attendee.getFirstname()}"></c:out></td>
                <td><c:out value="${attendee.getLastname()}"></c:out></td>
                <td><c:out value="${attendee.getEmail()}"></c:out></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br><br>
</section>
<footer>
    <center>&copy 2017</center>
</footer>

</body>
</html>