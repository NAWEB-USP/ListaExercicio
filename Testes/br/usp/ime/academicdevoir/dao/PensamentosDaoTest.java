package br.usp.ime.academicdevoir.dao;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.academicdevoir.entidade.Pensamento;

public class PensamentosDaoTest {
	PensamentoDao pensametoDao; 
	Pensamento pensamento;
	
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;
	private @Mock PensamentoDao pensamentoDao;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
		when(session.beginTransaction()).thenReturn(tx);
		
		pensamentoDao = new PensamentoDao(session);

		pensamento = new Pensamento();
		pensamento.setId(1L);
		pensamento.setAutor("autor");
		pensamento.setQuote("quote");
	}
		
	@Test
	public void deveListarTudo(){
		List<Pensamento> pensamentos = new ArrayList<Pensamento>();
		pensamentos.add(pensamento);
		
		when(session.createCriteria(Pensamento.class)).thenReturn(criteria);
		when(criteria.list()).thenReturn(pensamentos);
		
		Assert.assertEquals(pensamentoDao.listaTudo(), pensamentos);
		verify(criteria).list();
	}
	
	@Test
	public void deveSalvar() {
		pensamentoDao.salva(pensamento);
		
		verify(session).beginTransaction();
		verify(session).saveOrUpdate(pensamento);
		verify(tx).commit();
	}
	
	@Test
	public void deveCarreagr() {
		when(session.load(Pensamento.class, 1L)).thenReturn(pensamento);
		
		Assert.assertEquals(pensamentoDao.carrega(1L), pensamento);
		verify(session).load(Pensamento.class, 1L);
	}
	
	@Test
	public void deveAtualizar() {
		pensamentoDao.atualiza(pensamento);
		
		verify(session).beginTransaction();
		verify(session).update(pensamento);
		verify(tx).commit();
	}
	
	@Test
	public void deveRemover() {
		pensamentoDao.remove(pensamento);
		
		verify(session).beginTransaction();
		verify(session).delete(pensamento);
		verify(tx).commit();
	}

	@Test
	public void deveRecarregar() {
		pensamentoDao.recarrega(pensamento);
		
		verify(session).refresh(pensamento);
	}

}
