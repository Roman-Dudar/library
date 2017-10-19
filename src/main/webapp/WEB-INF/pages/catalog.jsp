<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/6/17
  Time: 6:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>
<%@ taglib prefix="bookTable" uri="bookTable"%>



<div class="container" align="center">
    <bookTable:books-table books="${books}" contextPath="${pageContext.request.contextPath}"></bookTable:books-table>
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