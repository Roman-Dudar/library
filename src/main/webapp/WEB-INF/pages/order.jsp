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
    <h4>
        <fmt:message key="library.order_book" bundle="${bundle}" /> «${book_instance.getBookDescription().getTitle() }»
    </h4>
    <form action="./order" method="POST" role="form">
        <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}"/></button>
    </form>
</div>


<%@include file="footer.jsp"%>