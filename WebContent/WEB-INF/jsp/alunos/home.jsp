<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	language="java" import="java.sql.*" errorPage=""%>

<html>
<head>
<link rel="stylesheet" type="text/css" charset="utf-8" media="screen"
	href="<c:url value="/css/form2.css"/>" />
</head>
<body>
	<div id="menu">Menu</div>

	<form action='alteracao' method="get">
		<input type="hidden" value="${usuarioSession.usuario.id}" name="id" />
		<a
			href="<c:url value='/alunos/alteracao?id=${usuarioSession.usuario.id}'/>">Alterar
			dados pessoais</a>
		<br />
	</form>

	<form action='matricula' method="get">
		<a href="<c:url value='/alunos/matricula'/>">Fazer matricula numa
			turma</a>
		<br />
	</form>

	<form action='listaTurmas' method="get">
		<input type="hidden" name="idAluno"
			value="${usuarioSession.usuario.id}" />
		<a
			href="<c:url value='/alunos/listaTurmas?idAluno=${usuarioSession.usuario.id}'/>">Meus
			cursos</a>
		<br />
	</form>
	<a href="<c:url value='/login'/>">Sair</a>
</body>
</html>