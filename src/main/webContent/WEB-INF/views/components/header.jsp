<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header>
	<section id="personal_area">
		<a href="personalPage">
			<img src="${pageContext.request.contextPath}/images/iconDark.png" alt="Area Personale" class="dark ">
			<img src="${pageContext.request.contextPath}/images/iconLight.png" alt="Area Personale" class="light">
		</a>
		<a href="mostraCarrello">
			<img src="${pageContext.request.contextPath}/images/carrelloDark.png" alt="Carrello" class="dark">
			<img src="${pageContext.request.contextPath}/images/carrelloLight.png" alt="Carrello" class="light">
		</a>
		<a id="options_button">
			<img src="${pageContext.request.contextPath}/images/menuDark.png" alt="Opzioni" class="dark">
			<img src="${pageContext.request.contextPath}/images/menuLight.png" alt="Opzioni" class="light">
		</a>
	</section>
	<section id="logo">
		<a href="index">
			<img src="${pageContext.request.contextPath}/images/logo.jpg" alt="Logo">
		</a>
	</section>
	<search id="search_bar">
		<section>
			<img src="${pageContext.request.contextPath}/images/lente.png" alt="zoom_lens">                    
		</section>
		<section >
			<input type="text" id="ricerca" placeholder="Ricerca...">
		</section>
	</search>
</header>