<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Area Utente</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/areaUtente.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>
	<main>
		<c:if test="${utente.admin}">
			<section id="admin_section">
				<button onclick="location.href = 'gestioneProdotti';">Prodotti</button>
				<button onclick="location.href = 'gestioneCarte';">Carte</button>
				<button onclick="location.href = 'gestioneOrdini';">Ordini Altrui</button>
			</section>
		</c:if>
		<section id="common_section">
			<button onclick="location.href = 'mostraCarrello';">Carrello</button>
			<button onclick="location.href = 'mostraStorico';">Storico</button>
			<button onclick="location.href = 'gestioneInfoSped';">Info Spedizione</button>
			<button onclick="location.href = 'gestioneMetodoPagamento';">Metodi di pagamento</button>
			<button onclick="location.href = 'mostraCronologia';">Cronologia</button>
			<button onclick="location.href = 'gestioneWishlist';">Wishlist</button>
			<button onclick="location.href = 'logout';">Logout</button>
			<button onclick="location.href = 'eliminaAccount';">Eliminazione Account</button>
		</section>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>