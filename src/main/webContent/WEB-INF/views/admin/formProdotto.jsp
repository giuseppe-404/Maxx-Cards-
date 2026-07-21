<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Salva Prodotto</title>
<%@include file="/WEB-INF/views/components/ext.jsp"%>
<script src="${pageContext.request.contextPath}/scripts/salva_prodotto.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/components/header.jsp"%>
	<main>
		<form action="nuovoProdotto" method="post" class="insertion_form" enctype="multipart/form-data" autocomplete="off" id="prod_form">
			<fieldset id="product_loader">
				<label for="ricerca_prodotto" class="autocompletabile">Ricerca:
					<input type="text" name="ricerca_prodotto" id="ricerca_prodotto" placeholder="Maxx &quot C &quot">
				</label> 
				<input type="button" id="carica_prodotto" value="Carica"> 
				<input type="button" id="cancella_prodotto" class="risky_button" value="Cancella"> 
				<input type="hidden" name="old_id" id="old_id"> 
				<input type="hidden" name="action" id="action" value="add" required>
				<p id="prod_retrieve_error" class="hidden">Impossibile trovare il prodotto ricercato</p>
				<input type="button" value="Reset" class="reset_button">
			</fieldset>
			<fieldset id="product_data">
				<label for="tipo_prodotto">Tipo di prodotto: 
					<select name="tipo_prodotto" id="tipo_prodotto" required>
						<option disabled selected value>-- Tipo --</option>
						<option value="prodotto">Prodotto semplice</option>
						<option value="carta">Carta Singola</option>
						<option value="box">Box</option>
						<option value="tin">Tin</option>
						<option value="pacchetto">Pacchetto</option>
						<option value="structure">Structure Deck</option>
						<option value="deck">Deck</option>
					</select>
				</label> 
				<label for="nome_prodotto">Nome: 
					<input type="text" name="nome" id="nome_prodotto" placeholder="Maxx &quot C &quot" required>
				</label> 
				<label for="qnt_prodotto">Quantità: 
					<input type="number" name="qnt" id="qnt_prodotto" placeholder="0" min="0" step="1" required>
				</label> 
				<label for="descr_prodotto">Descrizione: 
					<textarea name="descr" id="descr_prodotto" rows="5" placeholder="Descrizione..." required></textarea>
				</label> 
				<input type="button" value="Reset" class="reset_button">
			</fieldset>

			<fieldset id="product_price">
				<label for="prezzo_prodotto">Prezzo: 
					<input type="number" min="0.01" step="0.01" name="prezzo" id="prezzo_prodotto" placeholder="0.00" required> €
				</label> 
				<label for="sconto_prodotto">Sconto: 
					<input type="number" min="0" step="0.01" max="100" name="sconto" id="sconto_prodotto" placeholder="0"> %
				</label>
				<p id="prezzo_finale"></p>
				<input type="button" value="Reset" class="reset_button">
			</fieldset>

			<fieldset id="product_image">
				<label for="image">Immagine:
                	<input type="file" name="image" id="image_upload" accept="image/*">
                </label>
                <section>
                	<img src="" alt="" id="image_preview" class="hidden">
                </section>
                <input type="button" value="Reset" class="reset_button">
			</fieldset>

			<fieldset id="product_language" class="hidden">
				<label for="lingua_prodotto">Lingua: 
					<select name="lingua_prodotto" id="lingua_prodotto">
						<option disabled selected value="">-- Lingua --</option>
						<option value="italiano">Italiano</option>
						<option value="inglese">Inglese</option>
						<option value="giapponese">Giappone</option>
					</select>
				</label>
				<input type="button" value="Reset" class="reset_button">
			</fieldset>

			<fieldset id="product_set" class="hidden">
				<label for="set_prodotto">Set: 
					<select name="set" id="set_prodotto">
						<option disabled selected value>-- Set --</option>
						<c:if test="${not empty sets}">
							<c:forEach var="tipo" items="${set}">
								<option value="${set.nome}">${set.nome}</option>
							</c:forEach>
						</c:if>
						<option value="nuovo">Crea nuovo</option>
					</select>
				</label> 
				<label for="nuovo_set" class="invisible">Nome del nuovo set 
					<input type="text" name="nuovo_set" id="nuovo_set">
				</label> 
				<label for="data_set" class="invisible">Data di rilascio 
					<input type="date" name="data_set" id="data_set">
				</label> 
				<input type="button" value="Reset" class="reset_button">
			</fieldset>

			<fieldset id="product_quality" class="hidden">
				<label for="qlt_prodotto">Qualità carta: 
					<select name="qlt" id="qlt_prodotto"><!-- Controllare come i parametri con spazio vengono passati -->
						<option value="mint">Mint</option>
						<option value="near mint">Near Mint</option>
						<option value="excellent">Excellent</option>
						<option value="good">Good</option>
						<option value="light played">Light Played</option>
						<option value="played">Played</option>
						<option value="poor">Poor</option>
					</select>
				</label> 
				<input type="button" value="Reset" class="reset_button">
			</fieldset>
			
			<fieldset id="deck_composition" class="hidden">
				<section class="carte_contenute">
					<label for="carta" class="autocompletabile">Carta: 
						<input type="text" name="carta" class="nome_carta">
					</label> 
					<label for="qnt_carta">Quantità: 
						<input type="number" name="qnt_carta" min="1" max="3" step="1" class="qnt_carta">
					</label> 
					<input type="button" value="+" class="aggiungi_carta"> 
					<input type="button" value="-" class="rimuovi_carta">
				</section>
				<input type="button" value="Reset" class="reset_button">
			</fieldset>

			<input type="submit" value="Crea">
		</form>
	</main>
</body>
</html>