	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<!-- Website template by freewebsitetemplates.com -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java"
import="java.sql.*" errorPage="" %>

	
	<div id="header">
		<div>
			<div id="logo">
				<a href="/academic-devoir/"><img src="/academic-devoir/images/logo.png" alt="Logo" height="100" /></a>
			</div>
      <div id="title">
      	Academic Devoir
      </div>
			<div id="navigation">
				<div>
					<ul>
					<c:choose>
						<c:when test="${usuarioSession.logged}">
						<c:if test ="${usuarioSession.usuario.privilegio == 'ADMINISTRADOR'}">
						<li><a href="<c:url value='/disciplinas/cadastro'/>">Home</a></li>
						</c:if>
						<c:if test ="${usuarioSession.usuario.privilegio == 'PROFESSOR'}">
							<li><a href="<c:url value='/professores/home/${usuarioSession.usuario.id}'/>">Home</a></li>
						</c:if>
						<c:if test ="${usuarioSession.usuario.privilegio == 'ALUNO'}">
							<li><a href="<c:url value='/aluno/${usuarioSession.usuario.id}/turmas'/>">Home</a></li>
						</c:if>    
					</c:when>
											

						<c:otherwise><li><a href="<c:url value='/logout'/>">Home</a></li></c:otherwise>	
			
					</c:choose>	
					<li><a href="about.html">About us</a></li>
					<li class="current"><a href="blog.html">Report a Bug</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!--header -->