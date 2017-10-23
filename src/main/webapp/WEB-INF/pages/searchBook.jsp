<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/19/17
  Time: 3:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="header.jsp"%>
<%@ taglib prefix="bookTable" uri="bookTable"%>

<div class="container" align="center">
    <h2><fmt:message key="library.book.findByTitle" bundle="${bundle}"/></h2>
    <br/>

    <c:if test="${not empty search_by}">
        <h4><fmt:message key="library.search_by" bundle="${bundle}"/> "<c:out value="${search_by}" escapeXml="true"/>"</h4>
    </c:if>

    <form action="./searchBook" method="POST">
        <div class="form-group">
            <label for="book_title"><fmt:message key="library.bookDescription.title" bundle="${bundle}"/> </label>
            <input class="form-control" id="book_title" type="text" name="book_title">
        </div>

        <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}"/></button>
    </form>
</div>


<c:if test="${not empty books}">
    <div class="container" align="center">
        <bookTable:books-table books="${books}" contextPath="${pageContext.request.contextPath}"></bookTable:books-table>
    </div>
</c:if>



<%@include file="error.jsp"%>

<%@ include file="footer.jsp"%>
