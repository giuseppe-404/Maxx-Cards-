<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ordini</title>
<%@include file="/WEB-INF/views/components/ext.jsp"%>
<link href="${pageContext.request.contextPath}/styles/ordini.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/scripts/ordini.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<main>
		<form action="">
			<fieldset id="email_field">
				<label>Email utente: 
					<input type="email" id="email_input">
				</label>
				<input type="button" id="email_button" value="Carica gli ordini">
				<p id="output" class="hidden"></p>
			</fieldset>
			<fieldset id="orders_field">
				<section class="order_section hidden">
					<section class="order_data">
						<label>Mostra:
							<input type="checkbox" class="show_check" checked>
						</label>
						<label>
							<input type="number" class="id" readonly>
						</label>
						<label>
							<input type="date" class="data_acquisto" readonly>
						</label>
						<label>
							<input type="date" class="data_consegna">
						</label>
						<label>
							<select class="stato">
								<option value="acquistato">Acquistato</option>
								<option value="spedita">Spedito</option>
								<option value="consegnato">Consegnato</option>
								<option value="rimborsato">Rimborsato</option>
							</select>
						</label>
						<label>
							<input type="button" class="update_button" value="Aggiorna">
						</label>
					</section>
					<section class="order_product">
						<table>
							<tr class="product_header">
								<th colspan="3">Nome</th>
								<th>Prezzo</th>
								<th>Quantità</th>
							</tr>
							<tr class="product_data">
								<td class="product_nome" colspan="3">
									
								</td>
								<td class="product_prezzo">
									
								</td>
								<td class="product_qnt">
									
								</td>
							</tr>
							<tr class="product_desc">
								<td class="product_info" colspan="5">
								
								</td>
							</tr>
						</table>
					</section>
				</section>
			</fieldset>
		</form>
	</main>
</body>
</html>