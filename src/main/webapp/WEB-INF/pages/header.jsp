<%--
  русский
  Created by IntelliJ IDEA.
  User: Dima
  Date: 10/4/17
  Time: 10:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="lang" scope="session" value="${empty sessionScope.locale ? 'en_GB' : sessionScope.locale}" />
<fmt:setLocale value="${lang}" scope="session" />
<fmt:setBundle basename="/i18n/messages" var="bundle" scope="session" />


<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="library.title" bundle="${bundle}" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>


<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">
                <fmt:message key="library.button.library" bundle="${bundle}"/>
            </a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/main/">
                <fmt:message key="library.button.home" bundle="${bundle}"/>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/main/catalog?page=1">
                <fmt:message key="library.button.catalog" bundle="${bundle}"/>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/main/searchBook">
                <fmt:message key="library.button.search" bundle="${bundle}"/>
            </a></li>
            <c:if test="${user.getRole().name() == 'LIBRARIAN'}">
                <li><a href="${pageContext.request.contextPath}/main/signUp">
                    <fmt:message key="library.sign_up" bundle="${bundle}"/>
                </a></li>

                <li><a href="${pageContext.request.contextPath}/main/findOrder">
                    <fmt:message key="library.order.find" bundle="${bundle}"/>
                </a></li>

                <li><a href="${pageContext.request.contextPath}/main/addBook">
                    <fmt:message key="library.bookDescription.add" bundle="${bundle}"/>
                </a></li>
            </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <span class="glyphicon glyphicon-globe"></span> <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${pageContext.request.contextPath}/main/locale?lang=EN">EN</a></li>
                    <li><a href="${pageContext.request.contextPath}/main/locale?lang=RU">RU</a></li>
                </ul>
            </li>
            <c:choose>
                <c:when test="${empty user}">
                    <li><a href="${pageContext.request.contextPath}/main/login">
                        <span class="glyphicon glyphicon-log-in"></span>
                        <fmt:message key="library.login" bundle="${bundle}" />
                    </a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/main/logout">
                        <span class="glyphicon glyphicon-log-out"></span>
                        <fmt:message key="library.logout" bundle="${bundle}" /></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>