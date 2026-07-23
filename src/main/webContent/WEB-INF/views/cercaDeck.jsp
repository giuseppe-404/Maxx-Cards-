<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cerca Confezionato</title>
<%@include file="/WEB-INF/views/components/ext.jsp"%>
<script src="${pageContext.request.contextPath}/scripts/cerca_prodotto.js"></script>
<link href="${pageContext.request.contextPath}/styles/cerca_prodotto.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<section id="ricerca_wrapper">
		<details open>
			<summary>Impostazioni di ricerca</summary>
			<form action="">
				<fieldset>
					<input type="hidden" id="search_category" value="8">
					<label for="nome">Nome:
						<input type="text" name="nome" id="nome">
					</label>
					<label for="descrizione">Descrizione:
						<input type="text" name="descrizione" id="descrizione">
					</label>
					<label for="prezzo">Prezzo:
						<input type="number" name="prezzo" id="prezzo">
					</label>
					<label for="lingua">Lingua:
						<select name="lingua" id="lingua">
							<option value="" selected>Tutte</option>
							<option value="it">Italiano</option>
							<option value="en">Inglese</option>
							<option value="jp">Giappone</option>
						</select>
					</label>
					<input type="button" id="cerca_prod" value="Ricerca">
				</fieldset>
			</form>
		</details>
		<main>
			<p class="hidden" id="fail">Nessun prodotto trovato</p>
			<section class="product_selection" id="search_result">
				<a href="getProdottoPage?id=0" class="product_link selezionabile hidden">
					<section class="product_sale hidden">
						<p>Sconto!</p>
						<p>10%</p>
					</section>
					<section class="product_image">
						<img src="" alt="Immagine di x">
					</section>
					<section class="product_description">
						<h3>nome</h3>
						<p>desc</p>
					</section>
				</a>
			</section>
		</main>
	</section>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>
</html>