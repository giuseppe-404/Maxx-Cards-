<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav>
	<ul>
		<li><a href="${pageContext.request.contextPath}/cercaCartaSingola" class="selezionabile">Carte singole</a></li>
		<li><a href="${pageContext.request.contextPath}/cercaConfezionato" class="selezionabile">Confezionati</a></li>
		<li><a href="${pageContext.request.contextPath}/cercaDeck" class="selezionabile">Deck</a></li>
		<li><a href="${pageContext.request.contextPath}/cercaProdotto" class="selezionabile">Tutti i Prodotti</a></li>
		<li><a href="${pageContext.request.contextPath}/cercaCarta" class="selezionabile">Vedi Carte</a></li>
	</ul>
</nav>