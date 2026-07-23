<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Cronologia</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/carrello.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>
	<main>
		<c:choose>
			<c:when test="${ricerche.size() <= 0}">
				<p>La cronologia è vuoto</p>
			</c:when>
			<c:otherwise>
				<table id="carrello_table">
					<c:forEach var="ricerca" items="${ricerche}">
						<c:choose>
							<c:when test="${ricerca.prodotto}">
								<c:forEach var="target" items="${prodotti}">
									<c:if test="${ricerca.idTarget == target.id}">
										<tr class="prodotto_carrello">
											<td colspan="1">
												<img src="uploadImmagine?action=show&prodottoId=${target.id}&isProdotto=true" alt="Immagine di ${target.nome}">
											</td>
											<td colspan="3">
												<a href="getProdottoPage?id=${target.id}">${target.nome}</a>
											</td>
											<td colspan="1">
												${ricerca.dataVisita}
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var="target" items="${carte}">
									<c:if test="${ricerca.idTarget == target.id}">
										<tr class="prodotto_carrello">
											<td colspan="1">
												<img src="uploadImmagine?action=show&prodottoId=${target.id}&isProdotto=false" alt="Immagine di ${target.nome}">
											</td>
											<td colspan="3">
												<a href="getCarta?id=${target.id}">${target.nome}</a>
											</td>
											<td colspan="1">
												${ricerca.dataVisita}
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>