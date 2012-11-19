package br.usp.ime.academicdevoir.migracao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import com.healthmarketscience.jackcess.*;

import br.usp.ime.academicdevoir.dao.*;
import br.usp.ime.academicdevoir.entidade.*;
import br.usp.ime.academicdevoir.infra.Criptografia;
import br.usp.ime.academicdevoir.infra.Privilegio;

@SuppressWarnings("unchecked")
public class MigracaoDoAntigo {
	// Atualizar de acordo com a localização de 'aulas.mdb' na máquina
	private static final String oldDBFile = "/Users/dacortez/Desktop/aulas.mdb";
	
	private static Session oldSession;
	private static Session newSession;
	
	private static Professor professor;
	
	private static DisciplinaOld disciplinaZero;
		
	public static void main(String[] args) {
		abreSessions();
		carregaProfessor();
		migraPensamentos();
		migraAlunos();
		migraDisciplinas();
		migraTurmas();
		migraQuestoes();
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
	
	// Questoes ///////////////////////////////////////
	
	// Nesta migração não foi possível utilizar o mapeamento objeto-relacional na listagem
	// das questões por limitação da biblioteca HXTT. Optou-se por utilizar a biblioteca 
	// Jackcess que prove acesso ilimitado as tabelas do banco de dados Access.
	
	public static void migraQuestoes() {
		insereDisciplinaComCodigoZeroNoBDOld();
		insereDisciplinaComCodigoZeroNoBDNovo();
		List<QuestaoOld> lista = listaQuestoesOld();
		int cont = 0; int inexistentes = 0;
		for (QuestaoOld questaoOld: lista) {
			String tipo = questaoOld.getTipo().trim();
			Questao questao = null;
			if (tipo.contentEquals("QuestaoTexto")) {
				questao = new QuestaoDeTexto();
			} else if (tipo.contentEquals("V ou F")) {
				questao = new QuestaoDeVouF();
			} else if (tipo.contentEquals("QuestaoJava")) {
				questao = new QuestaoDeCodigo();
				((QuestaoDeCodigo) questao).setLinguagem("java");
				((QuestaoDeCodigo) questao).setCodigoDeTeste(" ");
			} else if (tipo.contentEquals("Submissão de Arquivo")) {
				questao = new QuestaoDeSubmissaoDeArquivo();
			} else if (tipo.contentEquals("Múltipla Escolha")) {
				questao = new QuestaoDeMultiplaEscolha();
			} else {
				System.err.println("Tipo de questão inexistente no sistema novo.");
				System.err.println(tipo);
				inexistentes++;
			}
			if (questao != null) {
				DisciplinaDao disciplinaDao = new DisciplinaDao(newSession, null);
				Disciplina disciplina = disciplinaDao.buscaPorNome(questaoOld.getDisciplina().getNome());
				questao.setDisciplina(disciplina);
				questao.setEnunciado(questaoOld.getEnunciado());
				if (tentaSalvarQuestao(questao))
					cont++;
			}
		}
		System.out.println("Questões antigas = " + lista.size());
		System.out.println("Questões salvas = " + cont);
		System.out.println("Questões de tipo inexistente no sistema novo = " + inexistentes);
	}
	
	// Necessita-se inserir uma disciplina com código = 0 no BD antigo
	// devido problemas de integridade referencial: a tabela Questao apresenta linhas
	// que fazer referência a uma disciplina de código = 0 que náo está cadastrada 
	// na tabela Disciplina.
	private static void insereDisciplinaComCodigoZeroNoBDOld() {
		disciplinaZero = (DisciplinaOld) oldSession.createCriteria(DisciplinaOld.class)
				.add(Restrictions.eq("cod", 0)).uniqueResult();
		if (disciplinaZero == null) {
			disciplinaZero = new DisciplinaOld();
			disciplinaZero.setCod(0);
			disciplinaZero.setNome("Teste");
			disciplinaZero.setDescricao("Teste");
			disciplinaZero.setDesativada(false);
			Transaction transaction = oldSession.beginTransaction();
			oldSession.save(disciplinaZero);
			transaction.commit();
			System.out.println("Criada disciplina com código zero: " + disciplinaZero.getNome());
		}
		else {
			System.out.println("Encontrada disciplina com código zero: " + disciplinaZero.getNome());
		}	
	}
	
	// Inserimos a disciplina de código = 0 no BD novo para garantir a integridade referencial.
	private static void insereDisciplinaComCodigoZeroNoBDNovo() {
		DisciplinaDao dao = new DisciplinaDao(newSession, null);
		Disciplina disciplina = dao.buscaPorNome(disciplinaZero.getNome());
		if (disciplina == null) {
			disciplina = new Disciplina();
			disciplina.setNome(disciplinaZero.getNome());
			disciplina.setStatus(true);
			dao.salvaDisciplina(disciplina);
			System.out.println("Disciplina com código zero migrada para novo BD: " + disciplina.getNome());
		}
		else {
			System.out.println("Disciplina com código zero já migradada no novo BD: " + disciplina.getNome());
		}
	}

	private static List<QuestaoOld> listaQuestoesOld() {
		List<QuestaoOld> lista = new ArrayList<QuestaoOld>();
		Table questao, disciplina;
		try {
			questao = Database.open(new File(oldDBFile)).getTable("Questao");
			disciplina = Database.open(new File(oldDBFile)).getTable("Disciplina");
			for(Map<String, Object> questaoLinha: questao) {
				Integer cod = (Integer) questaoLinha.get("Cod");
				Integer codDisc = (Integer) questaoLinha.get("CodDisc");
				String enunciado = (String) questaoLinha.get("Enunciado");
				String tipo = (String) questaoLinha.get("Tipo");
				DisciplinaOld disciplinaOld = getDisciplinaOld(disciplina, codDisc);
				QuestaoOld questaoOld = new QuestaoOld();
				questaoOld.setCod(cod);
				questaoOld.setDisciplina(disciplinaOld);
				questaoOld.setEnunciado(enunciado);
				questaoOld.setTipo(tipo);
				lista.add(questaoOld);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	private static DisciplinaOld getDisciplinaOld(Table disciplina, Integer cod) {
		DisciplinaOld disciplinaOld;
		try {
			Map<String, Object> linha = Cursor.findRow(disciplina, Collections.singletonMap("Cod", cod));
			disciplinaOld = new DisciplinaOld();
			disciplinaOld.setCod((Integer) linha.get("Cod"));
			disciplinaOld.setNome((String) linha.get("Nome"));
			disciplinaOld.setDescricao((String) linha.get("Descricao"));
			disciplinaOld.setDesativada((Boolean) linha.get("Desativada"));
		} catch (IOException e) {
			disciplinaOld = null;
			e.printStackTrace();
		}
		return disciplinaOld;
	}
	
	private static boolean tentaSalvarQuestao(Questao questao) {
		QuestaoDao dao = new QuestaoDao(newSession, null);
		try {
			dao.salva(questao);
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao salvar questão: " + e);
			newSession.close();
			newSession = HibernateUtil.getNewSessionFactory().openSession();
			return false;
		}
	}
		
}
