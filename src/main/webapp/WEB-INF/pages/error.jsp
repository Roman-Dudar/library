<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/18/17
  Time: 7:30 PM
  To change this template use File | Settings | File Templates.
--%>
<c:if test="${not empty error}">

    <div class="alert alert-danger" align="center">
        <strong>
            <fmt:message key="${error}" bundle="${bundle}" />
        </strong>
    </div>
</c:if>