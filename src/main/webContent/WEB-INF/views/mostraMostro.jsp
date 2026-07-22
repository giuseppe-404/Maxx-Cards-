<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${mostro.nomeIt}</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/prodotto.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/styles/mostro.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>

	<main>
		<section id=product_section>
			<section id="img_section">
				<img src="image?action=show&prodottoId=${mostro.id}&isProdotto=false" alt="Immagine di ${mostro.nomeIt}">
			</section>
			<section id="data_section">
				<section id="txt_section">
					<h1>${mostro.nomeIt}</h1>
					<p>Nome inglese: ${mostro.nomeEn}</p>
					<p>Nome giapponese: ${mostro.nomeJp}</p>
					<c:if test="${mostro.punteggio <= 100 && mostro.punteggio >= 0}">
						<p>Punteggio in Genesys: ${mostro.punteggio}</p>
					</c:if>
					<p>Attributo: ${mostro.attributo.toUpperCase()}</p>
					<c:set target="${mostro}" property="tipologia" value="${mostro.tipologia.toUpperCase()}" />
					<c:choose>
						<c:when test="${mostro.tipologia == 'LINK'}">
							<p>Link Rating: ${mostro.livello}</p>
							<section id="link_display">
								<div>
									<div id="tl_arrow" <c:if test="${mostro.frecceLink.get(0)}">class="arrow"</c:if>>&lt</div>
									<div id="t_arrow" <c:if test="${mostro.frecceLink.get(1)}">class="arrow"</c:if>>&lt</div>
									<div id="tr_arrow" <c:if test="${mostro.frecceLink.get(2)}">class="arrow"</c:if>>&gt</div>
								</div>
								<div>
									<div id="l_arrow" <c:if test="${mostro.frecceLink.get(3)}">class="arrow"</c:if>>&lt</div>
									<div id="no_arrow">o</div>
									<div id="r_arrow" <c:if test="${mostro.frecceLink.get(4)}">class="arrow"</c:if>>&gt</div>
								</div>
								<div>
									<div id="bl_arrow" <c:if test="${mostro.frecceLink.get(5)}">class="arrow"</c:if>>&lt</div>
									<div id="b_arrow" <c:if test="${mostro.frecceLink.get(6)}">class="arrow"</c:if>>&gt</div>
									<div id="br_arrow" <c:if test="${mostro.frecceLink.get(7)}">class="arrow"</c:if>>&gt</div>
								</div>
							</section>
						</c:when>
						<c:when test="${mostro.tipologia == 'XYZ'}">
							<p>Rango: ${mostro.livello}</p>
						</c:when>
						<c:otherwise>
							<p>Livello: ${mostro.livello}</p> 
						</c:otherwise>
					</c:choose>
					
					<c:set var="monster_string" value="[${mostro.tipo.toUpperCase()}"/>
					<c:if test="${mostro.categoria.toUpperCase() != 'NONE'}">
						<c:set var="monster_string" value="${monster_string}/${mostro.categoria.toUpperCase()}"/>
					</c:if>
					<c:if test="${mostro.tuner > 0}">
						<c:set var="monster_string" value="${monster_string}/TUNER"/>
					</c:if>
					<c:if test="${mostro.tipologia.toUpperCase() != 'NORMALE'}">
						<c:set var="monster_string" value="${monster_string}/${mostro.tipologia.toUpperCase()}"/>
					</c:if>
					<c:if test="${mostro.scalaPendulum >= 0}">
						<c:set var="monster_string" value="${monster_string}/PENDULUM"/>
					</c:if>
					<c:set var="monster_string" value="${monster_string}]"/>
					<p>
						${monster_string}
					</p>
					
					<c:if test="${mostro.scalaPendulum >= 0}">
						<p>Scala pendulum: ${mostro.scalaPendulum}</p>
					</c:if>
					
					<p>${mostro.testo}</p>
					<p>
						<c:choose>
							<c:when test="${mostro.atk < 0}">
								<span>Attacco: ?</span>
							</c:when>
							<c:otherwise>
								<span>Attacco: ${mostro.atk}</span>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${mostro.tipologia == 'LINK'}"/>
							<c:when test="${mostro.def < 0}">
								<span>Difesa: ?</span>
							</c:when>
							<c:otherwise>
								<span>Difesa: ${mostro.def}</span>
							</c:otherwise>
						</c:choose>
					</p>
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
							<td colspan="5"><a href="getProdottoPage?id=${prodotto.id}">${prodotto.nome}</a></td>
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