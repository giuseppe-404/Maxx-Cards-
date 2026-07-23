<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${prodotto.nome}</title>
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
				<img src="image?action=show&prodottoId=${prodotto.id}&isProdotto=true" alt="Immagine di ${prodotto.nome}">
			</section>
			<section id="data_section">
				<section id="txt_section">
					<h1>${prodotto.nome}</h1>
					<p>${prodotto.descrizione}</p>
				</section>
				<section id="price_section">
					<c:if test="${prodotto.qnt < 20}">
						<P>Solo ${prodotto.qnt} rimanenti!</P>
					</c:if>
					<c:if test="${prodotto.sconto > 0}">
						<c:set var="old_price" value="${prodotto.prezzo}"/>
						<c:set target="${prodotto}" property="prezzo" value="${(100 - prodotto.sconto / 100) * prodotto.prezzo / 100}" />
						<P>Ben ${prodotto.sconto / 100}% di sconto! <span>${old_price / 100}€</span> &gt&gt <strong>${prodotto.prezzo / 100}€</strong> </P>
					</c:if>
					<section id="buy_section">
						<p>${prodotto.prezzo / 100}€</p>
						<input type="number" min="0" max="${prodotto.qnt}" value="0" id="buy_qnt">
						<input type="hidden" value="${prodotto.id}" id="buy_id">
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