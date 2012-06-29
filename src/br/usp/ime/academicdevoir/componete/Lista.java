package br.usp.ime.academicdevoir.componete;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.usp.ime.academicdevoir.dao.ListaGeradaDao;
import br.usp.ime.academicdevoir.dao.ListaQuestaoDao;
import br.usp.ime.academicdevoir.dao.QuestaoDao;
import br.usp.ime.academicdevoir.entidade.Aluno;
import br.usp.ime.academicdevoir.entidade.ListaDeExercicios;
import br.usp.ime.academicdevoir.entidade.ListaGerada;
import br.usp.ime.academicdevoir.entidade.ListaQuestao;
import br.usp.ime.academicdevoir.entidade.Questao;
import br.usp.ime.academicdevoir.entidade.TagsDaLista;

@Component
public class Lista {

	private final ListaGeradaDao listaGeradaDao;
	
	private final QuestaoDao questaoDao;

	private final ListaQuestaoDao listaQuestaoDao; 
	
	public Lista(ListaGeradaDao listaGeradaDao, QuestaoDao questaoDao, ListaQuestaoDao listaQuestaoDao) {
		this.listaGeradaDao = listaGeradaDao;
		this.questaoDao = questaoDao;
		this.listaQuestaoDao = listaQuestaoDao;
	}

	public ListaGerada gerar(ListaDeExercicios lista, Aluno aluno) {
		ListaGerada listaGerada  = listaGeradaDao.buscar(lista, aluno);
		if (listaGerada == null) {
			listaGerada = new ListaGerada(lista, aluno);
			listaGeradaDao.salvar(listaGerada);
			List<TagsDaLista> tags = lista.getTags();
			
			for (TagsDaLista tagsDaLista : tags) {
				questaoDao.buscarQuestoesPor(tagsDaLista.getTag(), tagsDaLista.getQuantidade());
			}
			
			for (Questao questao : questaoDao.listaFiltradas()) {
				ListaQuestao listaQuestao = new ListaQuestao(listaGerada, questao);
				listaQuestaoDao.salvar(listaQuestao);
			}
		}
		return listaGerada;
	}
}
