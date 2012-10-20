package br.usp.ime.academicdevoir.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.academicdevoir.dao.PensamentoDao;
import br.usp.ime.academicdevoir.dao.UsuarioDao;
import br.usp.ime.academicdevoir.entidade.Pensamento;
import br.usp.ime.academicdevoir.entidade.Usuario;
import br.usp.ime.academicdevoir.infra.UsuarioSession;
import br.usp.ime.academicdevoir.util.Given;

public class LoginControllerTeste {
	
	private LoginController loginController;
	private Usuario usuario;
	
	@Spy 
	private Result result = new MockResult();

	@Mock
	private UsuarioDao usuarioDao;
	
	@Mock
	private PensamentoDao pensamentoDao;
	
	private UsuarioSession usuarioSession;
	//private HttpSession httpSession;
	
	@Mock
	private HttpSession session;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private HttpServletRequest request;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		usuario = Given.novoUsuario();
		usuarioSession = new UsuarioSession(); 
		usuarioSession.setUsuario(usuario);
		loginController = new LoginController(result, usuarioDao, pensamentoDao, usuarioSession, session, response, request);
	}
	
	@Test
	public void deveFazerLogin() {
		Pensamento p = new Pensamento();
		Mockito.when(pensamentoDao.buscaAleatorio()).thenReturn(p);		
		loginController.login();
		Mockito.verify(result).include("pensamento", p);
	}
	
	@Test
	public void deveAutenticarLogin(){
		Mockito.when(usuarioDao.fazLogin(usuario.getLogin(), usuario.getSenha())).thenReturn(usuario);
		loginController.login(usuario);
	}
	
	@Test
	public void naoDeveAutenticar() {
		Mockito.when(usuarioDao.fazLogin(usuario.getLogin(), usuario.getSenha())).thenReturn(null);
		loginController.login(usuario);	
		Mockito.verify(result).include("error", "Login ou senha incorreta!");
	}

}
