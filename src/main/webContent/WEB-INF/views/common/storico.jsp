<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${deck.nome}</title>
	<%@include file="/WEB-INF/views/components/ext.jsp"%>
	<link href="${pageContext.request.contextPath}/styles/ordini.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<%@include file="/WEB-INF/views/components/nav.jsp"%>
	<main>
		<c:choose>
			<c:when test="${ordini.size() == 0}">
				<p>Non sono stati fatti ordini.</p>
			</c:when>
			<c:otherwise>
				<c:forEach var="ordine" items="${ordini}">
					<section class="order_section">
						<section class="order_data">
							<p>
								<span>Stato: ${ordine.stato}</span>
								<span>ID ${ordine.idOrdine}</span>
								<span>Acquistato il ${ordine.dataAcquisto}</span>
								<c:if test="${ordine.dataConsegna != null}">
									<span>Consegnato il: ${ordine.dataConsegna}</span>
								</c:if>
							</p>
							<c:forEach var="metodo" items="${metodi}">
								<c:if test="${metodo.id == ordine.idMetodo}">
									<p>
										Metodo di pagamento: <c:out value="${metodo.metodo}"/>
									</p>
								</c:if>
							</c:forEach>
							<c:forEach var="info" items="${infos}">
								<c:if test="${info.id == ordine.idInfoSped}">
									<p>
										Informazioni di spedizione:
										<span>Nome: <c:out value="${info.cognome}"/> <c:out value="${info.nome}"/></span>
										<span>Indirizzo: [<c:out value="${info.cap}"/>] <c:out value="${info.via}"/>, <c:out value="${info.civico}"/></span>
									</p>
								</c:if>
							</c:forEach>
						</section>
						<section class="order_product">
							<table>
								<tr class="product_header">
									<th colspan="3">Nome</th>
									<th>Prezzo</th>
									<th>Quantità</th>
								</tr>
								<c:forEach var="prodotto" items="${prodotti}">
									<c:if test="${prodotto.idOrdine == ordine.idOrdine}">
										<tr class="product_data">
											<td class="product_nome" colspan="3">
												${prodotto.nome}
											</td>
											<td class="product_prezzo">
												${prodotto.prezzo}
											</td>
											<td class="product_qnt">
												${prodotto.qnt}
											</td>
										</tr>
										<tr class="product_desc">
											<td class="product_info" colspan="5">
												${prodotto.info}
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</section>
					</section>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</main>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>