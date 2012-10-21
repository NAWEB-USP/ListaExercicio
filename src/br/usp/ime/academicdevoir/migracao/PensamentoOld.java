package br.usp.ime.academicdevoir.migracao;

import javax.persistence.*;


@Entity
@Table(name = "Pensamentos")
public class PensamentoOld {
	@Id
	@Column(name = "Cod")
	private Integer id;
	
	@Column(name = "pensamento")
	private String pensamento;
	
	@Column(name = "autor")
	private String autor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
