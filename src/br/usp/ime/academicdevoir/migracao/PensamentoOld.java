package br.usp.ime.academicdevoir.migracao;

import javax.persistence.*;

@Entity
@Table(name = "Pensamentos")
public class PensamentoOld {
	@Id
	@Column(name = "Cod")
	private Integer cod;
	
	@Column(name = "pensamento")
	private String pensamento;
	
	@Column(name = "autor")
	private String autor;

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
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
