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
		<c:choose>
			<c:when test="${prodCarrello.size() <= 0}">
				<p>Il carrello è vuoto</p>
			</c:when>
			<c:otherwise>
				<form action="checkout" method="post">
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
					<table id="carrello_table">
						<c:set var="totale" value="0"/>
						<c:forEach var="ordinato" items="${prodCarrello}">
							<c:forEach var="prodotto" items="${prodotti}">
								<c:if test="${prodotto.id == ordinato.idOriginale}">
									<c:set target="${prodotto}" property="prezzo" value="${(100 - prodotto.sconto / 100) * prodotto.prezzo / 100}"/>
									<c:set var="totale" value="${totale + (prodotto.prezzo * ordinato.qnt)}"/>
									<tr>
										<td colspan="1">
											<img src="image?action=show&prodottoId=${prodotto.id}&isProdotto=true" alt="Immagine di ${prodotto.nome}">
										</td>
										<td colspan="4">
											<a href="getProdottoPage?id=${prodotto.id}">${prodotto.nome}</a>
										</td>
										<td colspan="1">
											<p>${prodotto.prezzo/100}€</p>
										</td>
										<td colspan="1">
											×${ordinato.qnt}
										</td>
									</tr>
									<tr>
										<td colspan="7">
											Costo totale: ${prodotto.prezzo * ordinato.qnt /100}€
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</c:forEach>
						<tr id="buttons_row">
							<td colspan="3">
								Totale: ${totale/100}€
							</td>
							<td colspan="2">
								<button>Vai al checkout!</button>
<!-- 								<input type="button" id="checkout_button" value="Vai al checkout!"> -->
							</td>
							<td colspan="2">
								<input type="button" onclick="location.href = 'svuotaCarrello';" value="Svuota il carrello">
							</td>
						</tr>
					</table>
				</form>
			</c:otherwise>
		</c:choose>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>