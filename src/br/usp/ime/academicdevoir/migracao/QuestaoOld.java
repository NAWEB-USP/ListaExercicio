package br.usp.ime.academicdevoir.migracao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Questao")
public class QuestaoOld {

	@Id
	@Column(name = "Cod")
	private Integer cod;
	
	@ManyToOne
	@JoinColumn(name = "CodDisc")
	private DisciplinaOld disciplina;
	
	@Column(name = "Enunciado")
	private String enunciado;
	
	@Column(name = "Criterios")
	private String criterios;
	
	@Column(name = "Tipo")
	private String tipo;

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public DisciplinaOld getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(DisciplinaOld disciplina) {
		this.disciplina = disciplina;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getCriterios() {
		return criterios;
	}

	public void setCriterios(String criterios) {
		this.criterios = criterios;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
