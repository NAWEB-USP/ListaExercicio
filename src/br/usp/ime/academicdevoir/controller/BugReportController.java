package br.usp.ime.academicdevoir.controller;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.usp.ime.academicdevoir.dao.UsuarioDao;
import br.usp.ime.academicdevoir.infra.Public;
import br.usp.ime.academicdevoir.infra.UsuarioSession;
import br.usp.ime.academicdevoir.mail.EmailThreadPoolExecutor;
import br.usp.ime.academicdevoir.mail.Mailer;


@Resource @Public
public class BugReportController {
	
	private UsuarioDao usuarioDao;
	private final Mailer mailer;
	private final Result result;
	private final Validator validator;
	private EmailThreadPoolExecutor emailThreadPoolExecutor;
	private UsuarioSession usuarioSession;
	
	public BugReportController(UsuarioDao usuarioDao, Result result, Validator validator, Mailer mailer, 
			EmailThreadPoolExecutor emailThreadPoolExecutor, UsuarioSession usuarioSession) {
		this.result = result;
		this.validator = validator;
		this.mailer = mailer;
		this.emailThreadPoolExecutor = emailThreadPoolExecutor;
		this.usuarioDao = usuarioDao;
		this.usuarioSession = usuarioSession;
	}

	@Get("/report")
	public void bugReport(Long id) {
		if(!usuarioSession.isLogged()){
			criarValidacao("Você não está logado. Por favor, faça o login para reportar erros.", "login.fail");
		}
		result.include("usuario", usuarioDao.carrega(id));
	}

	private void criarValidacao(String mensagem, String categoria) {
		validator.add(new ValidationMessage(mensagem, categoria));
		validator.onErrorUse(Results.logic()).redirectTo(LoginController.class).login();
	}
	
	@Post("/senderror")
	public void enviarErro(String login, String bug) {
			//Usuario usuario = usuarioDao.carrega(id);
			sendEmailThreadPoolExecutor(login, bug);
	}

	private void sendEmailThreadPoolExecutor(final String user,final  String bug) {
		emailThreadPoolExecutor.runTask(new Runnable() {
		  public void run() {
				try {
					mailer.send(emailTo(user, bug));
				} catch (EmailException e) {
					criarValidacao("Problemas ao enviar e-mail.", "email.problem");
				}
		  }
		 });
		emailThreadPoolExecutor.shutDown();
	}

	private Email emailTo(String user, String bug) throws EmailException {
		Email emailParaResetarSenha = new SimpleEmail();
		emailParaResetarSenha.setSubject("Erro no sistema");
		emailParaResetarSenha.setMsg(mensagem(user, bug));
		emailParaResetarSenha.addTo("academic.devoir@gmail.com");
		return emailParaResetarSenha;
	}

	private String mensagem(String user, String bug) {
		StringBuilder mensagemEmail = new StringBuilder();
		mensagemEmail.append("Erro reportado por: " + user + "\n\n")
								.append(bug + " \n\n")
								.append("+++++.");
		return mensagemEmail.toString();
	}


}