<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cerca Carte</title>
<%@include file="/WEB-INF/views/components/ext.jsp"%>
<script src="${pageContext.request.contextPath}/scripts/cerca_carta.js"></script>
<link href="${pageContext.request.contextPath}/styles/cerca_carta.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="/WEB-INF/views/components/header.jsp"%>
	<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
	<section id="ricerca_wrapper">
		<details open>
			<summary>Impostazioni di ricerca</summary>
			<form action="">
				<fieldset>
					<label for="classe">Classe:
						<select name="classe" id="classe">
	                        <option selected value=""> -- Tutte  -- </option>
	                        <option value="mostro">mostro</option>
	                        <option value="magia">magia</option>
	                        <option value="trappola">trappola</option>
	                    </select>
					</label>
					<label for="nome">Nome:
						<input type="text" name="nome" id="nome">
					</label>
					<label for="pnt">Punteggio:
                        <input type="number" name="pnt" id="pnt" min="0" max="100" step="1">
                    </label>
					<label for="testo">Testo:
						<input type="text" name="testo" id="testo">
                    </label>
                    <label for="tipologia">Tipologia
						<select name="tipologia" id="tipologia">
	                        <option selected value=""> -- Tutte  -- </option>
	                    </select>
					</label>
				</fieldset>
				<fieldset id="monster_fieldset" class="hidden">
					<label for="livello">Livello/Rango/LinkRating:
                        <input type="number" min="0" max="13" step="1" name="livello" id="livello">
                    </label>
                    <label for="categoria">Categoria:
                        <select name="categoria" id="categoria">
                            <option selected value=""> -- Tutte  -- </option>
                            <option value="nessuna">nessuna</option>
                            <option value="toon">toon</option>
                            <option value="gemello">gemello</option>
                            <option value="spirit">spirit</option>
                            <option value="unione">unione</option>
                        </select>
                    </label>
                    <label for="attributo">Attributo:
                        <select name="attributo" id="attributo">
                            <option selected value=""> -- Tutti  -- </option>
                            <option value="luce">Luce</option>
                            <option value="oscurita">Oscurità</option>
                            <option value="fuoco">Fuoco</option>
                            <option value="acqua">Acqua</option>
                            <option value="vento">Vento</option>
                            <option value="terra">Terra</option>
                            <option value="divino">Divino</option>
                        </select>
                    </label>
                    <label for="attacco">Attacco (-1 = ?):
                        <input type="text" pattern="^[\?0-9][0-9]*$" name="attacco" id="attacco">
                    </label>
                    <label for="difesa">Difesa (-1 = ?):
                        <input type="number" pattern="^[\?0-9][0-9]*$" name="difesa" id="difesa">
                    </label>
                    <label for="tuner">Tuner:
                        <select name="tuner" id="tuner">
                        	<option selected value="-1"> -- Qualsiasi  -- </option>
                            <option value="1">Tuner</option>
                            <option value="0">Non-Tuner</option>
                        </select>
                    </label>
                    <label for="scala">Scala Pendulum:
                        <input type="number" min="0" max="13" step="1" name="scala" id="scala">
                    </label>
                    <label for="frecce_link" id="frecce_label">Frecce:</label>
                    <div>
                   		<input type="checkbox" name="freccia1" id="freccia1">
                        <input type="checkbox" name="freccia2" id="freccia2">
                        <input type="checkbox" name="freccia3" id="freccia3">
                        <br>
                        <input type="checkbox" name="freccia4" id="freccia4">
                        <input type="checkbox" name="frecce_link" id="" class="invisible" disabled>
                        <input type="checkbox" name="freccia5" id="freccia5">
                        <br>
                        <input type="checkbox" name="freccia6" id="freccia6">
                        <input type="checkbox" name="freccia7" id="freccia7">
                        <input type="checkbox" name="freccia8" id="freccia8">
                   	</div>
                    <label for="tipo">Tipo:
                        <select name="tipo" id="tipo">
                            <option selected value=""> -- Qualsiasi  -- </option>
                            <c:if test="${not empty tipi}">
	                            <c:forEach var="tipo" items="${tipi}">
	                            	<option value="${tipo.tipo}">${tipo.tipo}</option>
	                            </c:forEach>
                            </c:if>
                        </select>
                    </label>
				</fieldset>
				<input type="button" id="cerca_prod" value="Ricerca">
				<div>
<input type="hidden" value="20" id="limit">
<input type="hidden" value="0" id="page">
<input type="button" id="prec_btn" value="&lt" disabled>
<input type="button" id="next_btn" value="&gt" disabled>
</div>
			</form>
		</details>
		<main>
			<p class="hidden" id="fail">Nessuna carta trovato</p>
			<section class="product_selection" id="search_result">
				<a href="getCartaPage?id=0" class="product_link selezionabile hidden">
					<section class="product_description">
						<h3>Nome</h3>
					</section>
					<section class="product_image">
						<img src="" alt="Immagine di x">
					</section>
				</a>
			</section>
		</main>
	</section>
	<%@include file="/WEB-INF/views/components/footer.jsp"%>
</body>
</html>