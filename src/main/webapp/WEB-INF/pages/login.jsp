<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/5/17
  Time: 1:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>


<div class="container" align="center">
    <h2><fmt:message key="library.login" bundle="${bundle}"/></h2>
    <form action="./login" method="POST" role="form">
        <div class="form-group">
            <label for=><fmt:message key="library.field.phone.number" bundle="${bundle}" />:</label>
            <input class="form-control" id="phone-number" placeholder="+38(012)345-67-89" name="phone-number">
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="library.field.password" bundle="${bundle}" />:</label>
            <input type="password" class="form-control" id="password"
                   placeholder=<fmt:message key="library.field.enter.password" bundle="${bundle}"/>
                           name="password">
        </div>
        <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}" /></button>
    </form>
</div>


<%@include file="error.jsp"%>

<%@include file="footer.jsp"%>
