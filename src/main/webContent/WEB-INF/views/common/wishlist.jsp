<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Carrello</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/carrello.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/styles/wishlist.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/scripts/wishlist.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>
	<main>
		<c:choose>
			<c:when test="${wants.size() <= 0}">
				<p>La wishlist è vuoto</p>
			</c:when>
			<c:otherwise>
				<table id="carrello_table">
					<c:forEach var="prodotto" items="${prodotti}">
						<tr class="prodotto_carrello prodotto_wishlist">
							<td colspan="1">
								<img src="image?action=show&prodottoId=${prodotto.id}&isProdotto=true" alt="Immagine di ${prodotto.nome}">
							</td>
							<td colspan="3">
								<a href="getProdottoPage?id=${prodotto.id}">${prodotto.nome}</a>
							</td>
							<td colspan="1">
								<p>${prodotto.prezzo}€</p>
							</td>
							<td colspan="1">
								<div class="wish_update">
									<input type="hidden" value="${prodotto.id}" class="wish_id">
									<input type="image" src="${pageContext.request.contextPath}/images/wish.png" class="wish_button" alt="Aggiungi alla wishlist">
									<input type="image" src="${pageContext.request.contextPath}/images/wished.png" class="wish_button hidden" alt="Aggiungi alla wishlist">
								</div>
								<p class="output_msg hidden"></p>
							</td>
						</tr>
					</c:forEach>
					<tr id="buttons_row">
						<td colspan="6">
							<button onclick="location.href = 'svuotaWish';">Svuota la wishlist</button>
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>