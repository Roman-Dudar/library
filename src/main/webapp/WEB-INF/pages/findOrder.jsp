<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/14/17
  Time: 4:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>


<div class="container" align="center">
    <form action="./findOrder" method="POST" role="form">
        <div class="form-group">
            <label for="order_id"><fmt:message key="library.order.enter_id" bundle="${bundle}"/> </label>
            <input type="text" class="form-control" id="order_id">
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}"/></button>
        </div>
    </form>
</div>


<%@include file="footer.jsp"%>
