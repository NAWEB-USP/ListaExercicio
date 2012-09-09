package br.usp.ime.academicdevoir.dao;

import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.usp.ime.academicdevoir.entidade.Pensamento;

@Component
public class PensamentoDao {

	private final Session session;
	
	public PensamentoDao(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pensamento> listaTudo() {
		return this.session.createCriteria(Pensamento.class).list();
	}
	
	public void salva(Pensamento pensamento) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(pensamento);
		tx.commit();
	}
	
	public Pensamento carrega(Long id) {
		return (Pensamento) session.load(Pensamento.class, id);
	}
	
	public void atualiza(Pensamento pensamento) {
		Transaction tx = session.beginTransaction();
		this.session.update(pensamento);
		tx.commit();
	}

	public void remove(Pensamento pensamento) {
		Transaction tx = session.beginTransaction();
		this.session.delete(pensamento);
		tx.commit();
	}

	public void recarrega(Pensamento pensamento) {
		session.refresh(pensamento);
	}

	public Pensamento buscaPeloAutor(String autor) {
		return (Pensamento) session.createCriteria(Pensamento.class)
				.add(Restrictions.eq("autor", autor)).uniqueResult();
	}
	
	public Pensamento buscaAleatorio() {
		List<Pensamento> todos = this.listaTudo();
		if (todos.size() > 0) {
			int randomIndex = new Random().nextInt(todos.size());
			return todos.get(randomIndex);
		}
		return null;
	}

}
