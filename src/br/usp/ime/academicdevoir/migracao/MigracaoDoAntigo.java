package br.usp.ime.academicdevoir.migracao;

import java.util.List;

import org.hibernate.*;

import br.usp.ime.academicdevoir.dao.*;
import br.usp.ime.academicdevoir.entidade.*;
import br.usp.ime.academicdevoir.infra.Criptografia;
import br.usp.ime.academicdevoir.infra.Privilegio;

@SuppressWarnings("unchecked")
public class MigracaoDoAntigo {
	private static Session oldSession;
	private static Session newSession;
		
	public static void main(String[] args) {
		abreSessions();
		migraAlunos();
		migraPensamentos();
		fechaSessions();
	}
	
	public static void abreSessions() {
		oldSession = HibernateUtil.getOldSessionFactory().openSession();
		newSession = HibernateUtil.getNewSessionFactory().openSession();
	}
	
	public static void fechaSessions() {
		oldSession.close();
		newSession.close();
	}
	
	private static void migraAlunos() {
		List<AlunoOld> lista = listaAlunosOld();
		int cont = 0;
		for (AlunoOld old: lista) {
			String login = old.getLogin();
			String nome = old.getNome();
			String email = old.getEmail();
			String senha = new Criptografia().geraMd5(old.getSenha());
			Aluno aluno = new Aluno();
			aluno.setPrivilegio(Privilegio.ALUNO);
			aluno.setLogin(login);
			aluno.setNome(nome);
			aluno.setEmail(email);
			aluno.setSenha(senha);
			if (tentaSalvarAluno(aluno))
				cont++;
		}
		System.out.println("Aluno salvos = " + cont);
	}
	
	private static List<AlunoOld> listaAlunosOld() {
		Transaction transaction = oldSession.beginTransaction();
		Criteria filtro = oldSession.createCriteria(AlunoOld.class);
		List<AlunoOld> lista = filtro.list();
		transaction.commit();
		return lista;
	}

	private static boolean tentaSalvarAluno(Aluno aluno) {
		AlunoDao dao = new AlunoDao(newSession);
		try {
			dao.salvaAluno(aluno);
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao salvar aluno: " + e);
			newSession.close();
			newSession = HibernateUtil.getNewSessionFactory().openSession();
			return false;
		}
	}
	
	public static void migraPensamentos() {
		List<PensamentoOld> lista = listaPensamentosOld();
		int cont = 0;
		for (PensamentoOld old: lista) {
			String quote = old.getPensamento().replace("<BR>", "");
			String autor = old.getAutor();
			Pensamento pensamento = new Pensamento();
			pensamento.setQuote(quote);
			pensamento.setAutor(autor);
			if (tentaSalvarPensamento(pensamento))
				cont++;
		}
		System.out.println("Pensamentos salvos = " + cont);
	}
	
	private static List<PensamentoOld> listaPensamentosOld() {
		Transaction transaction = oldSession.beginTransaction();
		Criteria filtro = oldSession.createCriteria(PensamentoOld.class);
		List<PensamentoOld> lista = filtro.list();
		transaction.commit();
		return lista;
	}

	private static boolean tentaSalvarPensamento(Pensamento pensamento) {
		PensamentoDao dao = new PensamentoDao(newSession);
		try {
			dao.salva(pensamento);
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao salvar pensamento: " + e);
			newSession.close();
			newSession = HibernateUtil.getNewSessionFactory().openSession();
			return false;
		}
	}
}
