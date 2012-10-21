package br.usp.ime.academicdevoir.migracao;

import java.util.List;

import org.hibernate.*;

import br.usp.ime.academicdevoir.dao.*;
import br.usp.ime.academicdevoir.entidade.*;

@SuppressWarnings("unchecked")
public class MigracaoDoAntigo {
	private static Session oldSession;
	private static Session newSession;
		
	public static void main(String[] args) {
		abreSessions();

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


	private static void migraPensamentos() {
		PensamentoDao pensamentoDao = new PensamentoDao(newSession);
		List<PensamentoOld> pensamentosOld = listaPensamentosOld();
		for (PensamentoOld old: pensamentosOld) {
			String quote = old.getPensamento().replace("<BR>", "");
			String autor = old.getAutor();
			Pensamento pensamento = new Pensamento();
			pensamento.setQuote(quote);
			pensamento.setAutor(autor);
			pensamentoDao.salva(pensamento);
		}
	}
		
	public static List<PensamentoOld> listaPensamentosOld() {
		Transaction transaction = oldSession.beginTransaction();
		Criteria filtro = oldSession.createCriteria(PensamentoOld.class);
		List<PensamentoOld> pensamentos = filtro.list();
		transaction.commit();
		return pensamentos;
	}
}
