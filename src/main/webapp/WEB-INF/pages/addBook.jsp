<%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/19/17
  Time: 4:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>

<div class="container" align="center">
    <h2><fmt:message key="library.bookDescription.add" bundle="${bundle}" /></h2>
    <form action="./addBook" method="POST" role="form">
        <div class="form-group">
            <label for="book_title"><fmt:message key="library.bookDescription.title" bundle="${bundle}" />:</label>
            <input type="text" class="form-control" id="book_title" placeholder=<fmt:message key="library.bookDescription.title" bundle="${bundle}" /> name="book_title">
        </div>
        <div class="form-group">
            <label for="isbn">ISBN:</label>
            <input type="text" class="form-control" id="isbn" placeholder="978-0-13-148005-6" name="isbn">
        </div>
        <div class="form-group">
            <label for="publisher"><fmt:message key="library.bookDescription.publisher" bundle="${bundle}" />:</label>
            <input type="phone-number" class="form-control" id="publisher" placeholder="<fmt:message key="library.bookDescription.publisher" bundle="${bundle}" />" name="phone-number">
        </div>
        <div class="form-group">
            <label for="genre"><fmt:message key="library.bookDescription.genre" bundle="${bundle}" />:</label>
            <input type="text" class="form-control" id="genre" placeholder=<fmt:message key="library.bookDescription.genre" bundle="${bundle}"/> name="genre">
        </div>
        <div class="form-group">
            <label for="availability"><fmt:message key="library.bookDescription.availability" bundle="${bundle}" />:</label>
            <select class="form-control" id="availability" name="availability">
                <option value="subscription"><fmt:message key="library.availability.subscription" bundle="${bundle}"/></option>
                <option value="reading_room"><fmt:message key="library.availability.reading_room" bundle="${bundle}"/></option>
            </select>
        </div>
        <div class="form-group">
            <label for="authors"><fmt:message key="library.bookDescription.authors" bundle="${bundle}"/>:</label>
            <textarea class="form-control" rows="5" id="authors" name="authors"></textarea>
        </div>
        <button type="submit" class="btn btn-default"><fmt:message key="library.button.submit" bundle="${bundle}" /></button>
    </form>

    <c:if test="${not empty bookId}">
        <div class="alert alert-success">
            <strong><fmt:message key="library.success" bundle="${bundle}"/></strong>
            <fmt:message key="library.bookDescription.add_success" bundle="${bundle}"/> "${user_id}".
        </div>
    </c:if>
</div>



<%@include file="error.jsp"%>

<%@include file="footer.jsp"%>
