<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/6/17
  Time: 6:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>


<div class="container" align="center">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>#</th>
            <th><fmt:message key="library.bookDescription.title" bundle="${bundle}" /></th>
            <th><fmt:message key="library.bookDescription.authors" bundle="${bundle}" /></th>
            <th><fmt:message key="library.bookDescription.publisher" bundle="${bundle}" /></th>
            <th><fmt:message key="library.bookDescription.genre" bundle="${bundle}" /></th>
            <th><fmt:message key="library.bookDescription.availability" bundle="${bundle}" /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${books}" var="book" varStatus="status">
            <tr onclick="window.location.href = 'google.com';" style="cursor: pointer">
                <td>${book.getId()}</td>
                <td>${book.getTitle()}</td>
                <td>
                    <c:forEach items="${book.getAuthors()}" var="author">
                        ${author.getName()} ${author.getSurname()}<br />
                    </c:forEach>
                </td>
                <td>${book.getPublisher()}</td>
                <td>${book.getGenre()}</td>
                <td><fmt:message key="${book.getAvailability().getLocaleKey()}" bundle="${bundle}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<ul class="pager">
    <li><a href="#">Previous</a></li>
    <li><a href="#">Next</a></li>
</ul>
<%@include file="footer.jsp"%>