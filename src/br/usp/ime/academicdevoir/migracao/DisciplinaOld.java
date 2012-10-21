package br.usp.ime.academicdevoir.migracao;
import javax.persistence.*;

@Entity
@Table(name = "Disciplina")
public class DisciplinaOld {

	@Id
	@Column(name = "Cod")
	private Integer cod;
	
	@Column(name = "Nome")
	private String nome;
	
	@Column(name = "Descricao")
	private String descricao;
	
	@Column(name = "Desativada")
	private boolean desativada;

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isDesativada() {
		return desativada;
	}

	public void setDesativada(boolean desativada) {
		this.desativada = desativada;
	}
	
}
