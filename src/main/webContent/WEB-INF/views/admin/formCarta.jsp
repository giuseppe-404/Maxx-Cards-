<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>
            Carta
        </title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%@include file="/WEB-INF/views/components/ext.jsp"%>
		<script src="<%=request.getContextPath()%>/scripts/salva_carta.js"></script>
    </head>
    <body>
        <%@include file="/WEB-INF/views/components/header.jsp"%>
		<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
        <main>
            <form action="http://localhost/Max-Cards-/nuovaCarta" method="get" class="insertion_form" enctype="multipart/form-data" autocomplete="off" id="card_form">
                <fieldset id="product_loader">
                	<label for="ricerca_carta" class="autocompletabile">Ricerca:
                		<input type="text" name="ricerca_carta" id="ricerca_carta" placeholder="Maxx &quot C &quot">
                	</label>
                    <input type="button" id="carica_carta" value="Carica">
                    <input type="button" id="cancella_carta" class="risky_button" value="Cancella">
                    <input type="hidden" name="old_id" id="old_id">
                    <input type="hidden" name="action" id="action" value="add" required>
                    <p id="card_retrieve_error" class="hidden">Impossibile trovare la carta ricercata</p>
                    <input type="button" value="Reset" class="reset_button">
                </fieldset>
                <fieldset id="card_data">
                    <label for="classe_carta">Classe della carta:
                        <select name="classe_carta" id="classe_carta" required>
                            <option disabled selected value=""> -- Classe  -- </option>
                            <option value="mostro">mostro</option>
                            <option value="magia">magia</option>
                            <option value="trappola">trappola</option>
                        </select>
                    </label>
                    <label for="id_carta">ID:
                        <input type="number" name="id_carta" id="id_carta" min="0" step="1" placeholder="12345678" required>
                    </label>
                    <label for="nome_it">Nome it:
                        <input type="text" name="nome_it" id="nome_it" placeholder="Massimiliano &quot S &quot" required>
                    </label>
                    <label for="nome_en">Nome en:
                        <input type="text" name="nome_en" id="nome_en" placeholder="Maxx &quot C &quot" required>
                    </label>
                    <label for="nome_jp">Nome jp:
                        <input type="text" name="nome_jp" id="nome_jp" placeholder="Makksu &quot Si &quot" required>
                    </label>
                    <label for="pnt_carta">Punteggio:
                        <input type="number" name="pnt_carta" id="pnt_carta" placeholder="0" min="0" max="100" step="1">
                    </label>
                    <label for="testo">Testo:
                        <textarea name="testo_carta" id="testo_carta" rows="5" placeholder="Testo ed effetti..." required></textarea> <!-- cols="50" rows="5" -->
                    </label>
                    <input type="button" value="Reset" class="reset_button">
                </fieldset>

                <fieldset id="card_image">
                    <label for="image">Immagine:
                        <input type="file" name="image" id="image_upload" accept="image/*">
                    </label>
                    <section>
                        <img src="" alt="" id="image_preview" class="hidden">
                    </section>
                    <input type="button" value="Reset" class="reset_button">
                </fieldset>
                
                <fieldset id="card_tipology" class="hidden">
                    <label for="tipologia_carta">Tipologia:
                        <select name="tipologia_carta" id="tipologia_carta" required>
                            <option disabled selected value=""> -- Tipologia  -- </option>
                        </select>
                    </label>
                    <input type="button" value="Reset" class="reset_button">
                </fieldset>

                <fieldset id="monster_data" class="hidden">
                    <label for="livello_mostro">Livello:
                        <input type="number" min="0" max="13" step="1" name="livello_mostro" id="livello_mostro">
                    </label>
                    <label for="categoria_mostro">Categoria:
                        <select name="categoria_mostro" id="categoria_mostro">
                            <option disabled selected value=""> -- Categoria  -- </option>
                            <option value="nessuna">nessuna</option>
                            <option value="toon">toon</option>
                            <option value="gemello">gemello</option>
                            <option value="spirit">spirit</option>
                            <option value="unione">unione</option>
                        </select>
                    </label>
                    <label for="attributo_mostro">Attributo:
                        <select name="attributo_mostro" id="attributo_mostro">
                            <option disabled selected value=""> -- Attributo  -- </option>
                            <option value="luce">Luce</option>
                            <option value="oscurita">Oscurità</option>
                            <option value="fuoco">Fuoco</option>
                            <option value="acqua">Acqua</option>
                            <option value="vento">Vento</option>
                            <option value="terra">Terra</option>
                            <option value="divino">Divino</option>
                        </select>
                    </label>
                    <label for="attacco_mostro">Attacco:
                        <input type="number" min="-1" step="1" name="attacco_mostro" id="attacco_mostro">
                    </label>
                    <label for="difesa_mostro">Difesa:
                        <input type="number" min="-1" step="1" name="difesa_mostro" id="difesa_mostro">
                    </label>
                    <label for="tuner_mostro">Tuner:
                        <input type="checkbox" name="tuner_mostro" id="tuner_mostro">
                    </label>
                    <label for="scala_mostro">Scala:
                        <input type="number" min="0" max="13" step="1" name="scala_mostro" id="scala_mostro">
                    </label>
                    <input type="button" value="Reset" class="reset_button">
                </fieldset>

                <fieldset id="arrow_data" class="hidden">
                    <label for="frecce_link" id="frecce_label">Frecce:
                        <br>
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
                    </label>
                    <input type="button" value="Reset" class="reset_button">
                </fieldset>

				<fieldset id="monster_type" class="hidden">
                    <label for="tipo_mostro">Tipo:
                        <select name="tipo_mostro" id="tipo_mostro">
                            <option disabled selected value=""> -- Tipo  -- </option>
                            <c:if test="${not empty tipi}">
	                            <c:forEach var="tipo" items="${tipi}">
	                            	<option value="${tipo.tipo}">${tipo.tipo}</option>
	                            </c:forEach>
                            </c:if>
                            <option value="nuovo">Crea nuovo</option>
                        </select>
                    </label>
                    <label for="nuovo_tipo" class="invisible">Nome del nuovo tipo
                        <input type="text" name="nuovo_tipo" id="nuovo_tipo">
                    </label>
                    <input type="button" value="Reset" class="reset_button">
                </fieldset>

                <input type="submit" value="Crea">
            </form>
        </main>
    </body>
</html>