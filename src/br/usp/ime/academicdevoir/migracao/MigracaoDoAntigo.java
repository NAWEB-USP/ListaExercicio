package br.usp.ime.academicdevoir.migracao;

import java.util.ArrayList;
import java.util.Collection;
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
	
	private static Professor professor;
		
	public static void main(String[] args) {
		abreSessions();
		carregaProfessor();
		migraPensamentos();
		migraAlunos();
		migraDisciplinas();
		migraTurmas();
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
	
	// Professor //////////////////////////////////////////
	
	public static void carregaProfessor() {
		ProfessorDao dao = new ProfessorDao(newSession);
		professor = dao.buscaPorLogin("professor");
		if (professor == null) {
			professor = new Professor();
			professor.setNome("Professor");
			professor.setLogin("professor");
			professor.setSenha(new Criptografia().geraMd5("professor"));
			professor.setLogin("professor");
			professor.setEmail("professor@gmail.com");
			professor.setPrivilegio(Privilegio.PROFESSOR);
			dao.salvaProfessor(professor);
		}
	}
	
	// Pensamentos ///////////////////////////////////////
	
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
		System.out.println("Pensamentos antigos = " + lista.size());
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
	
	// Alunos ///////////////////////////////////////
	
	public static void migraAlunos() {
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
		System.out.println("Alunos antigos = " + lista.size());
		System.out.println("Alunos salvos = " + cont);
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
	
	// Disciplinas ///////////////////////////////////////
	
	public static void migraDisciplinas() {
		List<DisciplinaOld> lista = listaDisciplinasOld();
		int cont = 0;
		for (DisciplinaOld old: lista) {
			String nome = old.getNome();
			boolean status = !old.isDesativada();
			Disciplina disciplina = new Disciplina();
			disciplina.setNome(nome);
			disciplina.setStatus(status);
			if (tentaSalvarDisciplina(disciplina))
				cont++;
		}
		System.out.println("Disciplinas antigas = " + lista.size());
		System.out.println("Disciplinas salvas = " + cont);
	}
	
	private static List<DisciplinaOld> listaDisciplinasOld() {
		Transaction transaction = oldSession.beginTransaction();
		Criteria filtro = oldSession.createCriteria(DisciplinaOld.class);
		List<DisciplinaOld> lista = filtro.list();
		transaction.commit();
		return lista;
	}

	private static boolean tentaSalvarDisciplina(Disciplina disciplina) {
		DisciplinaDao dao = new DisciplinaDao(newSession, null);
		try {
			dao.salvaDisciplina(disciplina);
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao salvar disciplina: " + e);
			newSession.close();
			newSession = HibernateUtil.getNewSessionFactory().openSession();
			return false;
		}
	}
	
	// Turmas ///////////////////////////////////////
	
	public static void migraTurmas() {
		List<TurmaOld> lista = listaTurmasOld();
		int cont = 0;
		for (TurmaOld turmaOld: lista) {
			DisciplinaDao disciplinaDao = new DisciplinaDao(newSession, null);
			Disciplina disciplina = disciplinaDao.buscaPorNome(turmaOld.getDisciplina().getNome());
			String nome = turmaOld.getNome();
			Turma turma = new Turma();
			turma.setDisciplina(disciplina);
			turma.setNome(nome);
			turma.setProfessor(professor);
			turma.setTemPrazo("nao");
			Collection<Aluno> alunos = new ArrayList<Aluno>();
			for (AlunoOld alunoOld: turmaOld.getAlunos()) {
				UsuarioDao usuarioDao = new UsuarioDao(newSession);
				Aluno aluno = (Aluno) usuarioDao.buscarPorLogin(alunoOld.getLogin());
				if (aluno != null)
					alunos.add(aluno);
			}
			turma.setAlunos(alunos);
			if (tentaSalvarTurma(turma))
				cont++;
		}
		System.out.println("Turmas antigas = " + lista.size());
		System.out.println("Turmas salvas = " + cont);
	}

	private static List<TurmaOld> listaTurmasOld() {
		Transaction transaction = oldSession.beginTransaction();
		Criteria filtro = oldSession.createCriteria(TurmaOld.class);
		List<TurmaOld> lista = filtro.list();
		transaction.commit();
		return lista;
	}
	
	private static boolean tentaSalvarTurma(Turma turma) {
		TurmaDao dao = new TurmaDao(newSession);
		try {
			dao.salvaTurma(turma);
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao salvar turma: " + e);
			newSession.close();
			newSession = HibernateUtil.getNewSessionFactory().openSession();
			return false;
		}
	}
	
}
