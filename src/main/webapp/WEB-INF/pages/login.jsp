<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/5/17
  Time: 1:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>


<div class="container">
    <h2>Login</h2>
    <form action="./login" method="POST" role="form">
        <div class="form-group">
            <label for=>Phone number:</label>
            <input type="phone-number" class="form-control" id="phone-number" placeholder="+38(012)345-67-89" name="phone-number">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>



<%@include file="footer.jsp"%>
