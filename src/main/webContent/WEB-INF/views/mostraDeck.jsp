<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${deck.nome}</title>
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
				<img src="image?action=show&prodottoId=${deck.id}&isProdotto=true" alt="Immagine di ${deck.nome}">
			</section>
			<section id="data_section">
				<section id="txt_section">
					<h1>${deck.nome}</h1>
					<p>${deck.descrizione}</p>
					<p>Lingua: ${deck.lingua}</p>
				</section>
				<section id="deck_section">
					<label for="mostra_carte">Mostra Carte:
						<input type="checkbox" id="mostra_carte" checked>
					</label>
					<c:forEach var="carta" items="${carte}">
						<c:forEach var="contiene" items="${contieni}">
							<c:if test="${carta.id eq contiene.idCarta}">
								<div class="carta_div">
									<a href="mostraCarta?id=${carta.id}">${carta.nomeIt}</a>
									<p>× ${contiene.qnt}</p>
									<img alt="" src="https://m.media-amazon.com/images/I/71aorzt910L._AC_UF1000,1000_QL80_.jpg">
								</div>
							</c:if>
						</c:forEach>
					</c:forEach>
				</section>
				<section id="price_section">
					<c:if test="${deck.qnt < 20}">
						<P>Solo ${deck.qnt} rimanenti!</P>
					</c:if>
					<c:if test="${deck.sconto > 0}">
						<c:set var="old_price" value="${deck.prezzo}"/>
						<c:set target="${deck}" property="prezzo" value="${(100 - deck.sconto / 100) * deck.prezzo / 100}" />
						<P>Ben ${deck.sconto / 100}% di sconto! <span>${old_price / 100}€</span> &gt&gt <strong>${deck.prezzo / 100}€</strong> </P>
					</c:if>
					<section id="buy_section">
						<p>${deck.prezzo / 100}€</p>
						<input type="number" min="0" max="${deck.qnt}" value="0" id="buy_qnt">
						<input type="hidden" value="${deck.id}" id="buy_id">
						<input type="image" src="${pageContext.request.contextPath}/images/carrello.png" id="buy_button" alt="Aggiungi al carrello">
					</section>
					<p id="carrello_output" class="hidden"></p>
				</section>
			</section>
		</section>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>