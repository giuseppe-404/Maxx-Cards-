<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${carta.nome}</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/prodotto.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/scripts/prodotto.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>
	<main>
		<section id=product_section>
			<section id="img_section">
				<img src="image?action=show&prodottoId=${carta.id}&isProdotto=true" alt="Immagine di ${carta.nome}">
			</section>
			<section id="data_section">
				<section id="txt_section">
					<h1>${carta.nome}</h1>
					<p>${carta.descrizione}</p>
					<p>Qualità: ${carta.quality}</p>
					<p>Lingua: ${carta.lingua}</p>
					<p>Set: ${carta.idSet}</p>
					<a href="getCartaPage?id=${carta.idCarta}">Visita la pagina della carta!</a>
				</section>
				<section id="price_section">
					<c:if test="${carta.qnt < 20}">
						<P>Solo ${carta.qnt} rimanenti!</P>
					</c:if>
					<c:if test="${carta.sconto > 0}">
						<c:set var="old_price" value="${carta.prezzo}"/>
						<c:set target="${carta}" property="prezzo" value="${(100 - carta.sconto / 100) * carta.prezzo / 100}" />
						<P>Ben ${carta.sconto / 100}% di sconto! <span>${old_price / 100}€</span> &gt&gt <strong>${carta.prezzo / 100}€</strong> </P>
					</c:if>
					<section id="buy_section">
						<p>${carta.prezzo / 100}€</p>
						<input type="number" min="0" max="${carta.qnt}" value="0" id="buy_qnt">
						<input type="hidden" value="${carta.id}" id="buy_id">
						<input type="image" src="${pageContext.request.contextPath}/images/carrello.png" id="buy_button" alt="Aggiungi al carrello">
						<input type="image" src="${pageContext.request.contextPath}/images/wish.png" class="wish_button" alt="Aggiungi al carrello">
						<input type="image" src="${pageContext.request.contextPath}/images/wished.png" class="wish_button hidden" alt="Aggiungi al carrello">
					</section>
					<p id="carrello_output" class="hidden"></p>
				</section>
			</section>
		</section>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>