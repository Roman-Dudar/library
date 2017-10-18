<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/18/17
  Time: 1:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>


<div class="container" align="center">
    <div class="form-group">
        <label for="confirm"><fmt:message key="library.order.confirm" bundle="${bundle}"/></label>
        <select class="form-control" id="confirm" name="confirm">
            <c:if test="${empty order.getActualReturnDate()}">
                <c:if test="${empty order.getPickupDate()}">
                    <option value="pick-up"><fmt:message key="library.order.pick_up" bundle="${bundle}"/></option>
                </c:if>
                <option value="return"><fmt:message key="library.order.return" bundle="${bundle}"/></option>
            </c:if>
        </select>
        <input type="hidden" id="order_id" name="order_id" value="${order.getId()}">
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}"/></button>
    </div>
</div>


<%@include file="error.jsp"%>

<%@include file="footer.jsp"%>