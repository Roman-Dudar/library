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
            <option value="pick-up"><fmt:message key="library.order.pick_up" bundle="${bundle}"/></option>
            <option value="return"><fmt:message key="library.order.return" bundle="${bundle}"/></option>
        </select>
        <input type="hidden" id="orderid" name="orderid" value="${order.getId()}">
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}"/></button>
    </div>

</div>


<%@include file="footer.jsp"%>