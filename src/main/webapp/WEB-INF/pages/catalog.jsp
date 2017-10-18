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
            <tr>
                <td>${book.getId()}</td>
                <td><a href="${pageContext.request.contextPath}/main/order?bookid=${book.getId()}">
                        ${book.getTitle()}
                </a></td>
                <td>
                    <c:forEach items="${book.getAuthors()}" var="author">
                        <a href="${pageContext.request.contextPath}/main/search?author=${author.getId()}">
                                ${author.getName()} ${author.getSurname()}
                        </a><br/>
                    </c:forEach>
                </td>
                <td>${book.getPublisher()}</td>
                <td><a href="${pageContext.request.contextPath}/main/search?genre=${book.getGenre()}">
                        ${book.getGenre()}
                </a></td>
                <td><fmt:message key="${book.getAvailability().getLocaleKey()}" bundle="${bundle}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<ul class="pager">
    <li><a href="${pageContext.request.contextPath}/main/catalog?page=${page - 1}">
        <fmt:message key="library.previous_page" bundle="${bundle}" />
    </a></li>
    <li><a href="${pageContext.request.contextPath}/main/catalog?page=${page + 1}">
        <fmt:message key="library.next_page" bundle="${bundle}" />
    </a></li>
</ul>


<%@include file="footer.jsp"%>