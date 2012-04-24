<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java"
import="java.sql.*" errorPage="" %>

<html>

<head>
<style type="text/css">
<%@ include file="/css/form2.css" %>
</style>
<title>Academic Devoir</title>
</head>

<body>
	<div id="wrapper"> 
	<div id="header"> <%@ include file="/css/header.jsp" %></div> <br/>
	<div id="left"><fieldset><%@ include file="/css/menu.jsp" %></fieldset></div>
	<div id="right">
	<div id="menu">Minhas Turmas</div>
	<br/><br/>
	<table>
        <c:forEach items="${professor.turmas}" var="turma">
            <tr>
                <td>
                    <a href="<c:url value="/turmas/home/${turma.id}"/>">
                        ${turma.disciplina.nome} - ${turma.nome} </a>
                </td>
                <td>
			<a style="margin-left: 4.5em;" href="../turmas/alteracao?id=${turma.id}">    Alterar</a> 
			<a href="../turmas/remove?id=${turma.id}">    Excluir</a>
		</td>
            </tr>
        </c:forEach>
	</table>
	<br/><br/>
	<form action='../turmas/cadastro'>
            <!-- input type="submit" value="Cadastrar nova turma"/-->
            <a href="<c:url value='../disciplinas/lista'/>">Cadastrar nova turma</a>
	</form>
    
    </div>
    </div>
</body>
</html>
