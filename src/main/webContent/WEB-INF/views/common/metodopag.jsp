<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Checkout</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/checkout.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/scripts/checkout.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>
	<main>
		<form action="checkout" method="get">
			<fieldset id="metodo_field">
				<label for="metodo_scelto">Metodo di Pagamento:
					<select name="metodo_scelto" id="metodo_scelto" required>
						<option selected disabled value="">-- Metodo di Pagamento --</option>
						<option value="nuovo">Crea nuovo</option>
						<c:forEach var="metodo" items="${metodi}">
							<option value="<c:out value="${metodo.id}"/>"><c:out value="${metodo.metodo}"/></option>
						</c:forEach>
					</select>
				</label>
				<label for="metodo">Modifica metodo:
					<input type="text" name="metodo" id="metodo">
				</label>
			</fieldset>
			<button>Invia</button>
		</form>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>
</html>
