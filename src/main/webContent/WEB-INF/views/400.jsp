<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/views/components/ext.jsp"%>
<link href="${pageContext.request.contextPath}/styles/error.css" rel="stylesheet" type="text/css">
<title>Errore</title>
</head>
<body>
<%@include file="/WEB-INF/views/components/header.jsp"%>
<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
<h1>Errore 400</h1>
<h3>Richiesta errata</h3>
<p>${msg}</p>
<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>
</html>