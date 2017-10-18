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
                <fmt:message key="library.order_book" bundle="${bundle}"/> "${book_instance.get().getBookDescription().getTitle() }"
            </h4>

            <form action="./order" method="POST" role="form">
                <label for="order_type"><fmt:message key="library.order.order_for" bundle="${bundle}"/></label>
                <select class="form-control" id="order_type" name="order_type">
                    <c:if test="${book_instance.get().getBookDescription().getAvailability().equals('subscription')}">
                    <option value="subscription"><fmt:message key="library.availability.subscription" bundle="${bundle}"/></option>
                    </c:if>
                    <option value="reading_room"><fmt:message key="library.availability.reading_room" bundle="${bundle}"/></option>
                </select></br>

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