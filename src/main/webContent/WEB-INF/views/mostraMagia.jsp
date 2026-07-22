<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${magia.nomeIt}</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/prodotto.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>

	<main>
		<section id=product_section>
			<section id="img_section">
				<img src="image?action=show&prodottoId=${magia.id}&isProdotto=false" alt="Immagine di ${magia.nomeIt}">
			</section>
			<section id="data_section">
				<section id="txt_section">
					<h1>${magia.nomeIt}</h1>
					<p>Nome inglese: ${magia.nomeEn}</p>
					<p>Nome giapponese: ${magia.nomeJp}</p>
					<p>Punteggio in Genesys: ${magia.punteggio}</p>
					<p>Tipologia: ${magia.tipologia}</p>
					<p>${magia.testo}</p>
				</section>
			</section>
		</section>
		<c:if test="${prodotti != null}">
			<section id="buy_options">
				<h5>Opzioni di acquisto:</h5>
				<table>
					<c:forEach var="prodotto" items="${prodotti}">
						<c:if test="${prodotto.sconto > 0}">
							<c:set target="${prodotto}" property="prezzo" value="${(100 - prodotto.sconto / 100) * prodotto.prezzo / 100}" />
						</c:if>
						<tr class="card_row">
							<td colspan="5"><a href="mostraProdotto?id=${prodotto.id}">${prodotto.nome}</a></td>
							<td>${prodotto.lingua}</td>
							<td>${prodotto.quality}</td>
							<td>${prodotto.prezzo/100}</td>
						</tr>
						<c:set var="qnt_flag" value="${prodotto.qnt < 20}"/>
						<c:set var="sconto_flag" value="${prodotto.sconto > 0}"/>
						<c:choose>
							<c:when test="${prodotto.qnt < 20 && prodotto.sconto > 0}">
								<tr class="half_extra_row">
									<td colspan="4">Solo ${prodotto.qnt} rimanenti!</td>
									<td colspan="4">Ben ${prodotto.sconto/100}% di sconto!</td>
								</tr>
							</c:when>
							<c:when test="${prodotto.qnt < 20}">
								<tr class="full_extra_row">
									<td colspan="8">Solo ${prodotto.qnt} rimanenti!</td>
								</tr>
							</c:when>
							<c:when test="${prodotto.sconto > 0}">
								<tr class="full_extra_row">
									<td colspan="8">Ben ${prodotto.sconto/100}% di sconto!</td>
								</tr>
							</c:when>	
						</c:choose>
						
					</c:forEach>
				</table>
			</section>
		</c:if>	
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>