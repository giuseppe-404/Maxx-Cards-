<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Carrello</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/carrello.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/scripts/carrello.js"></script>
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
				<table id="carrello_table">
					<c:forEach var="ordinato" items="${prodCarrello}">
						<c:forEach var="prodotto" items="${prodotti}">
							<c:if test="${prodotto.id == ordinato.idOriginale}">
								<tr class="prodotto_carrello">
									<td colspan="1">
										<img src="uploadImmagine?action=show&prodottoId=${prodotto.id}&isProdotto=true" alt="Immagine di ${prodotto.nome}">
									</td>
									<td colspan="6">
										<a href="getProdottoPage?id=${prodotto.id}">${prodotto.nome}</a>
									</td>
									<td colspan="3">
										<p>${prodotto.prezzo/100}€</p>
									</td>
									<td colspan="3">
										<div>
											<label>×
												<input type="number" value="${ordinato.qnt}" min="0" max="${prodotto.qnt}" class="ord_qnt">
											</label>
											<c:set target="${prodotto}" property="prezzo" value="${(100 - prodotto.sconto / 100) * prodotto.prezzo / 100}"/>
											<p class="final_price">${prodotto.prezzo * ordinato.qnt / 100}€</p>
										</div>
									</td>
									<td colspan="1">
										<div class="cart_update">
											<input type="hidden" value="${prodotto.id}" class="ord_id">
											<input type="hidden" value="${prodotto.prezzo}" class="ord_price">
											<input type="image" src="${pageContext.request.contextPath}/images/carrello.png" class="update_button" alt="Aggiorna il carrello" disabled>
										</div>
										<p class="output_msg hidden"></p>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
					<tr id="buttons_row">
						<td colspan="7">
							<button onclick="location.href = 'svuotaCarrello';">Svuota il carrello</button>
						</td>
						<td colspan="7">
							<button onclick="location.href = 'mostraCheckout';">Vai al checkout!</button>
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>