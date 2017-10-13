<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/10/17
  Time: 5:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>


<div class="container">
    <h2><fmt:message key="library.sign_up" bundle="${bundle}" /></h2>
    <form action="./signUp" method="POST" role="form">
        <div class="form-group">
            <label for="name"><fmt:message key="library.field.name" bundle="${bundle}" />:</label>
            <input type="name" class="form-control" id="name" placeholder=<fmt:message key="library.field.enter.surname" bundle="${bundle}" /> name="name">
        </div>
        <div class="form-group">
            <label for="surname"><fmt:message key="library.field.surname" bundle="${bundle}" />:</label>
            <input type="surname" class="form-control" id="surname" placeholder=<fmt:message key="library.field.enter.surname" bundle="${bundle}"/> name="surname">
        </div>
        <div class="form-group">
            <label for="phone-number"><fmt:message key="library.field.phone.number" bundle="${bundle}" />:</label>
            <input type="phone-number" class="form-control" id="phone-number" placeholder="+38(012)345-67-89" name="phone-number">
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="library.field.password" bundle="${bundle}" />:</label>
            <input type="password" class="form-control" id="password" placeholder=<fmt:message key="library.field.enter.password" bundle="${bundle}"/> name="password">
        </div>
        <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}" /></button>
    </form>
</div>


<%@include file="footer.jsp"%>
