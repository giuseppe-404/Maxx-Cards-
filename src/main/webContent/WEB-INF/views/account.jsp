<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>
            Account
        </title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%@include file="/WEB-INF/views/components/ext.jsp"%>
		<script src="${pageContext.request.contextPath}/scripts/account.js"></script>
		<link href="${pageContext.request.contextPath}/styles/account.css" rel="stylesheet" type="text/css">
		
    </head>
    <body>
        <%@include file="/WEB-INF/views/components/header.jsp"%>
		<%@include file="/WEB-INF/views/components/optionsAside.jsp"%>
		
		<main>
			<form action="loginAccount" method="post" id="account_form">
				<label for="email">Email:
					<input type="email" name="email" id="email" required>
				</label>
				<label for="pwd">Password:
					<input type="password" name="pwd" id="pwd" required>
				</label>
				<div id="pwd_msg" class="hidden">
					<h3>La password deve contenere:</h3>
					<p id="letter"> Una lettera <b> minuscola</b></p>
					<p id="capital"> Una lettera <b> maiuscola</b></p>
					<p id="number"> Un <b> numero</b></p>
					<p id="length"> Almeno <b> 8 caratteri</b>
				</div>
				<button value="loginAccount" id="login_button">Login</button>
				
				<label for="conf_pwd">Conferma Password: 
					<input type="password" name="conf_pwd" id="conf_pwd">
				</label>
				<button value="registraAccount" id="registra_button">Registrati</button>
				<div id="conf_msg" class="hidden">
					<h3>Le password non corrispondono</h3>
				</div>
				<c:if test="${msg != ''}">
					<p>${msg}</p>
				</c:if>
			</form>
			
		</main>
		
		<%@include file="/WEB-INF/views/components/footer.jsp"%>
    </body>
</html>