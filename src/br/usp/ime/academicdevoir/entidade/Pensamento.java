package br.usp.ime.academicdevoir.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pensamento {

	@Id @GeneratedValue
	private Long id;
	
	private String pensamento;
	
	private String autor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPensamento() {
		return pensamento;
	}

	public void setPensamento(String pensamento) {
		this.pensamento = pensamento;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

}
