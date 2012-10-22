package br.usp.ime.academicdevoir.migracao;

import javax.persistence.*;

@Entity
@Table(name = "Turma")
public class TurmaOld {

	@Id
	@Column(name = "Cod")
	private Integer cod;
	
	@Column(name = "Nome")
	private String nome;
	
	@Column(name = "Descricao")
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "CodDisc")
	private DisciplinaOld disciplina;

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

	public DisciplinaOld getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(DisciplinaOld disciplina) {
		this.disciplina = disciplina;
	}
	
}
