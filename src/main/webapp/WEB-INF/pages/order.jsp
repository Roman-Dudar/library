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
        <c:when test="${not empty book_instance}">
            <h4>
                <fmt:message key="library.order_book" bundle="${bundle}"/>
                "${book_instance.getBookDescription().getTitle() }"
                <fmt:message key="${book_instance.getBookDescription().getAvailability().getLocaleKey()}" bundle="${bundle}"/>
            </h4>

            <form action="./order" method="POST" role="form">
                <input type="hidden" id="bookid" name="bookid" value="${book_instance.getBookDescription().getId()}">
                <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}"/></button>
            </form>
        </c:when>

        <c:otherwise>
            <div class="alert alert-warning">
                <strong><fmt:message key="library.sorry" bundle="${bundle}"/></strong>,
                <fmt:message key="library.order.not_available" bundle="${bundle}"/>.
            </div>
        </c:otherwise>
    </c:choose>

    <c:if test="${not empty order}">
        <div class="alert alert-warning">
            <fmt:message key="library.order.success" bundle="${bundle}"/> <strong>"${order.getId()}"</strong>
        </div>
    </c:if>
</div>


<%@include file="footer.jsp"%>