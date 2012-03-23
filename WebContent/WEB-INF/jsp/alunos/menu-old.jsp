<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java"
import="java.sql.*" errorPage="" %>

<html>
<head>
<style type="text/css">
<%@ include file="../css/formatacao.css" %>
</style>
<title>Academic Devoir</title>
</head>

<body>
    <h1>Academic Devoir</h1>
    <h2>Grupo 1 - Engenharia de Software</h2>
    <form action='cadastro' method="get"><input type="submit"
    value="Adicionar aluno" /></form>
    <form action='lista' method="get"><input type="submit"
    value="Listar aluno" /></form>
    <form action='remocao' method="get"><input type="submit"
    value="Remover aluno" /></form>
    <a href="<c:url value='/login'/>">Sair</a><br/>
        <c:if test ="${usuarioSession.usuario.privilegio == 'ALUNO' || usuarioSession.usuario.privilegio == 'MONITOR'}">
    	<a href="<c:url value='/alunos/home'/>">Página Principal</a><br/>
 	</c:if>
    <c:if test ="${usuarioSession.usuario.privilegio == 'PROFESSOR' || usuarioSession.usuario.privilegio == 'ADMINISTRADOR'}">
    	<a href="<c:url value='/professores/home'/>">Página Principal</a><br/>    		         
 	</c:if>
</body>
</html>