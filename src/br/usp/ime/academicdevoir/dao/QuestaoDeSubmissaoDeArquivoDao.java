package br.usp.ime.academicdevoir.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.usp.ime.academicdevoir.entidade.QuestaoDeSubmissaoDeArquivo;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class QuestaoDeSubmissaoDeArquivoDao {
	
	private final Session session;

	public QuestaoDeSubmissaoDeArquivoDao(Session session) {
		this.session = session;
	}

	/**
	 * Retorna uma lista com todas as questões de submissão de arquivo cadastradas no banco de dados.
	 * @return List<QuestaoDeSubmissaoDeArquivo>
	 */
	public List<QuestaoDeSubmissaoDeArquivo> listaTudo() {
		return this.session.createCriteria(QuestaoDeSubmissaoDeArquivo.class)
				.list();
	}

	/**
	 * Cadastra a questão fornecida no banco de dados.
	 * @param questao
	 */
	public void salva(QuestaoDeSubmissaoDeArquivo questao) {
		Transaction tx = session.beginTransaction();
		session.save(questao);
		tx.commit();
	}

	/**
	 * Retorna uma questão de submissão de arquivo com o id fornecido.
	 * @param id
	 * @return QuestaoDeSubmissaoDeArquivo
	 */
	public QuestaoDeSubmissaoDeArquivo carrega(Long id) {
		return (QuestaoDeSubmissaoDeArquivo) this.session.load(
				QuestaoDeSubmissaoDeArquivo.class, id);
	}

	/**
	 * Atualiza a questão fornecida no banco de dados. 
	 * @param questao
	 */
	public void atualiza(QuestaoDeSubmissaoDeArquivo questao) {
		Transaction tx = session.beginTransaction();
		this.session.update(questao);
		tx.commit();
	}

	/**
	 * Remove a questão fornecida do banco de dados.
	 * @param questao
	 */
	public void remove(QuestaoDeSubmissaoDeArquivo questao) {
		Transaction tx = session.beginTransaction();
		this.session.delete(questao);
		tx.commit();
	}

	public List<QuestaoDeSubmissaoDeArquivo> busca(String title) {
		return session.createCriteria(QuestaoDeSubmissaoDeArquivo.class)
				.add(Restrictions.ilike("title", title, MatchMode.ANYWHERE))
				.list();
	}

	public void recarrega(QuestaoDeSubmissaoDeArquivo questao) {
		session.refresh(questao);
	}
}