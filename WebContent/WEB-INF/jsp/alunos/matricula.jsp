<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<style type="text/css">
<%@ include file="/css/form2.css" %>
</style>
<title>Matricula</title>
</head>

<body>
	<div id="wrapper">
	<div id="header"> <%@ include file="/css/header.jsp" %></div> <br/>
	<div id="left"><fieldset><%@ include file="/css/menu.jsp" %></fieldset></div>
	<div id="right">
	<div id="menu">Matricular-se em uma turma:</div>
	<form action='inscreve'>
	<input type="hidden" name="idAluno" value="${usuarioSession.usuario.id}">
	 Ol�, ${usuarioSession.usuario.nome}, selecione a turma na qual voc� quer se matricular.<br/><br/>
	<select name="idTurma">
    <c:forEach items="${listaDeDisciplinas}" var="disciplina">
          <c:forEach items="${disciplina.turmas}" var="turma">
          <option value="${turma.id }">${disciplina.nome } - ${turma.nome }</option> 
          </c:forEach>
    </c:forEach>
    </select>
        <br /><br />        
    <input type="submit" value="Ok"/>
	</form>
	</div>
	</div>
	</body>
</html>