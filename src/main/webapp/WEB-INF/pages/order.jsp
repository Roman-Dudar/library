<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/8/17
  Time: 5:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>


<div class="container" align="center">
    <h2><fmt:message key="library.order" bundle="${bundle}"/></h2>
    <c:choose>
        <c:when test="${not bookInstance.isPresent()}">
            <h4>
                <fmt:message key="library.order_book" bundle="${bundle}" /> "${book_instance.get().getBookDescription().getTitle() }"
            </h4>
            <form action="./order" method="POST" role="form">
                <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}"/></button>
            </form>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info">
                <strong>Sorry</strong>, book is not available right now. Try again later.
            </div>
        </c:otherwise>
    </c:choose>
</div>


<%@include file="footer.jsp"%>