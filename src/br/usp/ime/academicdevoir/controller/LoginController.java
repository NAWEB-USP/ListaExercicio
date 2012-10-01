package br.usp.ime.academicdevoir.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.academicdevoir.dao.PensamentoDao;
import br.usp.ime.academicdevoir.dao.UsuarioDao;
import br.usp.ime.academicdevoir.entidade.Usuario;
import br.usp.ime.academicdevoir.infra.Privilegio;
import br.usp.ime.academicdevoir.infra.Public;
import br.usp.ime.academicdevoir.infra.UsuarioSession;
import br.usp.ime.academicdevoir.sessao.CriadorDeSessao;
import br.usp.ime.academicdevoir.sessao.CriadorDeSessionFactory;

@Public
@Resource
/**
 * Controlador de login.
 */
public class LoginController{

	private final Result result;

	private UsuarioSession usuarioSession;

	private UsuarioDao usuarioDao;

	private PensamentoDao pensamentoDao;

	private final HttpSession session;

	private final HttpServletResponse response;
	
	private final HttpServletRequest request;
	
	public LoginController(Result result, UsuarioDao usuarioDao, 
			PensamentoDao pensamentoDao, UsuarioSession alunodao, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		this.result = result;
		this.usuarioDao = usuarioDao;
		this.pensamentoDao = pensamentoDao;
		this.usuarioSession = alunodao;
		this.session = session;
		this.response = response;
		this.request = request;
	}

	@Path("/")
	public void index() {
		result.redirectTo(LoginController.class).login();
	}

	@Get
	@Path("/login")
	public void login() {
		checalogin();
		result.include("pensamento", pensamentoDao.buscaAleatorio());
	}

	@Post("/autenticar")
	public void login(Usuario usuario) {
		Usuario user = usuarioDao.fazLogin(usuario.getLogin(),
				usuario.getSenha());
		if (user != null) {
			usuarioSession.setUsuario(user);
			Cookie cookie = new Cookie("academicdevoir", user.getId().toString());
			cookie.setMaxAge(24*60*60);
	        response.addCookie(cookie);
			Privilegio pr = user.getPrivilegio();
			if (pr.equals(Privilegio.PROFESSOR)) {
				result.redirectTo(ProfessoresController.class).home();
			} else if (pr.equals(Privilegio.ADMINISTRADOR)) {
				result.redirectTo(AdministradorController.class).home();
			} else {
				result.redirectTo(AlunosController.class).home();
			}
		} else {
			result.include("error", "Login ou senha incorreta!")
			.redirectTo(this).login();
		}

	}

	@Get("/logout")
	public void logout() {
		session.invalidate();
		usuarioSession.logout();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("academicdevoir")) {
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}
		}
		result.redirectTo(this).login();
	}

	public void acessoNegado() {

	}

	public void checalogin() {
		String useridst = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("academicdevoir")) {
					useridst = cookies[i].getValue();
					Long userid = Long.parseLong(useridst.trim());
					Usuario usuario = usuarioDao.fazLoginCookie(userid);
					usuarioSession.setUsuario(usuario);
					Privilegio pr = usuario.getPrivilegio();
					if (pr.equals(Privilegio.PROFESSOR)) {
						result.redirectTo(ProfessoresController.class).home();
					} else if (pr.equals(Privilegio.ADMINISTRADOR)) {
						result.redirectTo(AdministradorController.class).home();
					} else {
						result.redirectTo(AlunosController.class).home();
					}
					break;
				}
			}
		}
	}
}
