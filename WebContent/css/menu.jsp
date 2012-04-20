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
	<div id="menu"> Meus Cursos </div>
	<br/>
	<table>
        <c:forEach items="${usuarioSession.usuario.turmas}" var="t">
            <tr>
                <td>
                    <a href="<c:url value="/turmas/home/${t.id}"/>">
                        ${t.disciplina.nome} - ${t.nome} </a>
                </td>
            </tr>
        </c:forEach>
	</table>
	<br/>
	<br/>
	<div id="menu">Menu</div>
	<br/>
	<c:if test ="${usuarioSession.usuario.privilegio == 'ADMINISTRADOR'}">
		<a href="<c:url value='/disciplinas/cadastro'/>">Cadastrar disciplina</a><br/><br/>
	</c:if>
	<c:if test ="${usuarioSession.usuario.privilegio == 'PROFESSOR'}">
		<a href="<c:url value='/professores/alteracao?id=${usuarioSession.usuario.id}'/>">Alterar meus dados</a><br/><br/>
		<a href="<c:url value='/professores/listaTurmas?idProfessor=${usuarioSession.usuario.id}'/>">Turmas ministradas</a><br/><br/>
		<a href="<c:url value='/turmas/nova'/>">Criar Turma</a><br/><br/>
		<a href="<c:url value='/disciplinas/lista'/>">Listar disciplinas</a><br/><br/>
		<a href="<c:url value='/questoes/cadastro'/>">Cadastrar quest&atilde;o</a><br/><br/>
		<br/><br/>
		Links temporarios:<br/><br/>
		<a href="<c:url value='/listasDeExercicios/cadastro'/>">Cadastrar Lista</a><br/><br/>
		<br/><br/>		

		<a href="<c:url value='/login'/>">Sair</a>
	</c:if>
	<c:if test ="${usuarioSession.usuario.privilegio == 'ALUNO' || usuarioSession.usuario.privilegio == 'MONITOR'}">
		<a href="<c:url value='/alunos/alteracao?id=${usuarioSession.usuario.id}'/>">Alterar meus dados</a><br/><br/>
		<a href="<c:url value='/alunos/listaTurmas?idAluno=${usuarioSession.usuario.id}'/>">Meus cursos</a><br/><br/>
		<a href="<c:url value='/alunos/matricula'/>">Matrícula</a><br/><br/>
		<a href="<c:url value='/login'/>">Sair</a>
	</c:if>        
</body>
</html>
