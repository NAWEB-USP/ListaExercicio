<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<!-- Website template by freewebsitetemplates.com -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java"
import="java.sql.*" errorPage="" %>

<html>
<head>
	<title>Academic Devoir - IME USP</title>
	<link rel="stylesheet" type="text/css" charset="utf-8" media="screen" href="<c:url value="/css/style.css"/>"/>
	<link rel="stylesheet" type="text/css" charset="utf-8" media="screen" href="<c:url value="/css/menu.css"/>"/>
	<!--[if IE 9]>
		<link rel="stylesheet" type="text/css" charset="utf-8" media="screen" href="<c:url value="/css/ie9.css"/>"/>
	<![endif]-->
	<!--[if IE 8]>
		<link rel="stylesheet" type="text/css" charset="utf-8" media="screen" href="<c:url value="/css/ie8.css"/>"/>
	<![endif]-->
	<!--[if IE 7]>
		<link rel="stylesheet" type="text/css" charset="utf-8" media="screen" href="<c:url value="/css/ie7.css"/>"/>
	<![endif]-->
</head>

<body>
<%@ include file="/layout/header.jsp" %>
<div id="content">
	<div id="body">
  	<table border="0">
    	<tr>
      		<td width="200" align="center">	
				<%@ include file="/layout/menu.jsp" %>
			</td>
			<td width="750" align="left" valign="top">    


				<div class="welcome">Você acessou como ${usuarioSession.usuario.nome } (<a href="<c:url value='/logout'/>">Sair</a>)</div>
				<h1><a href="index.html">Lista de Disciplinas</a></h1>


	<table>
    <tbody>
        <c:forEach items="${lista }" var="disciplina">
            <tr>
                <td>
                	<a href="<c:url value="/disciplinas/home/${disciplina.id}"/>">
                		${disciplina.nome}
                	</a>
                </td>
                <td>
                <a style="margin-left: 4.5em;" href="<c:url value='/disciplinas/alteracao/${disciplina.id}'/>"> Alterar</a> 
                <a class="excluir" href="./remove?id=${disciplina.id}">  Excluir</a>
                </td>
            </tr>
        </c:forEach>             
    </tbody>
	</table>
	<br/><br/>  
    <a href="<c:url value='/disciplinas/cadastro'/>">Cadastrar nova disciplina</a>
	
			</td>
		</tr>
	</table>    			
	<!-- body -->
	</div>
<!-- content -->
</div>
<%@ include file="/layout/footer.jsp" %>





	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	
	
	<script src="<c:url value="/javascript/common.js"/>" type="text/javascript"></script>




</body>
</html>