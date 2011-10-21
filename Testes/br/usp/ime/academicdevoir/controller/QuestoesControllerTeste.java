package br.usp.ime.academicdevoir.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import static org.junit.Assert.*;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.academicdevoir.dao.QuestaoDao;
import br.usp.ime.academicdevoir.entidade.Questao;
import br.usp.ime.academicdevoir.entidade.QuestaoDeMultiplaEscolha;
import br.usp.ime.academicdevoir.entidade.QuestaoDeSubmissaoDeArquivo;
import br.usp.ime.academicdevoir.entidade.QuestaoDeTexto;
import br.usp.ime.academicdevoir.entidade.QuestaoDeVouF;
import br.usp.ime.academicdevoir.infra.TipoDeQuestao;

public class QuestoesControllerTeste {

	private QuestoesController questoesController;
	private QuestaoDao dao;
	private MockResult result;
	private QuestaoDeMultiplaEscolha questaoDeMultiplaEscolha;
	private QuestaoDeSubmissaoDeArquivo questaoDeSubmissaoDeArquivo;
	private QuestaoDeTexto questaoDeTexto;
	private QuestaoDeVouF questaoDeVouF;

	@Before
	public void SetUp() {
		result = spy(new MockResult());
		dao = mock(QuestaoDao.class);

		questoesController = new QuestoesController(dao, result);
		questaoDeMultiplaEscolha = new QuestaoDeMultiplaEscolha();
		questaoDeSubmissaoDeArquivo = new QuestaoDeSubmissaoDeArquivo();
		questaoDeTexto = new QuestaoDeTexto();
		questaoDeVouF = new QuestaoDeVouF();

		questaoDeMultiplaEscolha.setId(0L);
		questaoDeSubmissaoDeArquivo.setId(1L);
		questaoDeTexto.setId(2L);
		questaoDeVouF.setId(3L);

		when(dao.listaTudo()).thenReturn(new ArrayList<Questao>());
		
		when(dao.carrega(questaoDeMultiplaEscolha.getId())).thenReturn(questaoDeMultiplaEscolha);
		when(dao.carrega(questaoDeSubmissaoDeArquivo.getId())).thenReturn(questaoDeSubmissaoDeArquivo);
		when(dao.carrega(questaoDeTexto.getId())).thenReturn(questaoDeTexto);
		when(dao.carrega(questaoDeVouF.getId())).thenReturn(questaoDeVouF);		
	}

	@Test
	public void testeAlteracaoQuestaoDeMultiplaEscolha() {
		questoesController.alteracao(questaoDeMultiplaEscolha.getId());
		verify(result).redirectTo(QuestoesDeMultiplaEscolhaController.class);
	}

	@Test
	public void testeAlteracaoQuestaoDeSubmissaoDeArquivo() {
		questoesController.alteracao(questaoDeSubmissaoDeArquivo.getId());
		verify(result).redirectTo(QuestoesDeSubmissaoDeArquivoController.class);
	}

	@Test
	public void testeAlteracaoQuestaoDeTexto() {
		questoesController.alteracao(questaoDeTexto.getId());
		verify(result).redirectTo(QuestoesDeTextoController.class);
	}

	@Test
	public void testeAlteracaoQuestaoDeVouF() {
		questoesController.alteracao(questaoDeVouF.getId());
		verify(result).redirectTo(QuestoesDeVouFController.class);
	}

	@Test
	public void testeRemove() {
		questoesController.remove(new Random().nextLong() % 4);
		verify(result).redirectTo(QuestoesController.class);
	}

	@Test
	public void testeLista() {
		questoesController.lista();
		List<Questao> questoes = result.included("lista");
		assertNotNull(questoes);
	}
}
