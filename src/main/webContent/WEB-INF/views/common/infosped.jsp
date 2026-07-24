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
		<form action="modificaInfoSped" method="post">
			<fieldset id="sped_field">
				<label for="info_scelto">Informazioni di spedizione:
					<select name="info_scelte" id="info_scelte" required>
						<option selected disabled value="">-- Info Spedizione --</option>
						<option value="nuovo">Crea nuovo</option>
						<c:forEach var="info" items="${infos}">
							<option value="<c:out value="${info.id}"/>">[<c:out value="${info.cap}"/>] <c:out value="${info.via}"/>, <c:out value="${info.civico}"/></option>
						</c:forEach>
					</select>
				</label>
				<c:forEach var="info" items="${infos}">
					<section id="info_${info.id}" class="info_section hidden">
						<input type="hidden" name="info_id" id="id_${info.id}" value="${info.id}" disabled required>
						<label>Nome:
							<input type="text" name="info_nome" id="nome_${info.id}" value="<c:out value="${info.nome}"/>" disabled required>
						</label>
						<label>Cognome:
							<input type="text" name="info_cognome" id="cognome_${info.id}" value="<c:out value="${info.cognome}"/>" disabled required>
						</label>
						<label>Cap:
							<input type="text" name="info_cap" id="cap_${info.id}" pattern="[\d]{5}" value="<c:out value="${info.cap}"/>"  title="Numero di 5 cifre" disabled required>
						</label>
						<label>Via:
							<input type="text" name="info_via" id="via_${info.id}" value="<c:out value="${info.via}"/>" disabled required>
						</label>
						<label>N. Civico:
							<input type="number" name="info_civico" id="civico_${info.id}" value="<c:out value="${info.civico}"/>" disabled required>
						</label>
					</section>
				</c:forEach>
				<section id="info_nuovo" class="info_section hidden">
					<input type="hidden" name="info_id" id="id_nuovo" value="nuovo" disabled required>
					<label>Nome:
						<input type="text" name="info_nome" id="nome_nuovo" value="" disabled required>
					</label>
					<label>Cognome:
						<input type="text" name="info_cognome" id="cognome_nuovo" value="" disabled required>
					</label>
					<label>Cap:
						<input type="text" name="info_cap" id="cap_nuovo" pattern="[\d]{5}" value="" title="Numero di 5 cifre" disabled required>
					</label>
					<label>Via:
						<input type="text" name="info_via" id="via_nuovo" value="" disabled required>
					</label>
					<label>N. Civico:
						<input type="number" name="info_civico" id="civico_nuovo" value="" disabled required>
					</label>
				</section>
			</fieldset>
			<button>Invia</button>
		</form>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>
</html>
