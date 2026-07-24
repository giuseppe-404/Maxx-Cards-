<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Storico</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/ordini.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>
	<main>
		<h1>Grazie per l'acquisto!</h1>
		<input type="button" onclick="location.href = 'fattura';" value="Scarica la fattura">
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>
</html>