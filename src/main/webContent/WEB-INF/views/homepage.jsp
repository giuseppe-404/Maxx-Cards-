<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Homepage</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/homepage.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/scripts/homepage.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>
	<main>
		<c:if test="${prodotti != null}">
			<section id="product_selection">
				<c:forEach var="prodotto" items="${prodotti}">
					<a href="getProdottoPage?id=${prodotto.id}" class="product_link selezionabile">
						<c:if test="${prodotto.sconto > 0}">
							<section class="product_sale">
								<p>Sconto!</p>
								<c:if test="${prodotto.sconto >= 2000}">
									<p>${prodotto.sconto/100}%</p>
								</c:if>
							</section>
						</c:if>
						<section class="product_image">
							<img src="uploadImmagine?action=show&prodottoId=${prodotto.id}&isProdotto=true" alt="Immagine di ${prodotto.nome}">
						</section>
						<section class="product_description">
							<h3>${prodotto.nome}</h3>
							<p>${prodotto.descrizione}</p>
						</section>
					</a>
				</c:forEach>
			</section>
		</c:if>
		<c:if test="${notizie != null}">
			<section id="news_section">
				<c:forEach var="notizia" items="${notizie}">
					<h3>${notizia.corpo}</h3>
					<p>${notizia.corpo}</p>
				</c:forEach>
			</section>
		</c:if>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>