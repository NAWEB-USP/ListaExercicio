package br.usp.ime.academicdevoir.migracao;

import javax.persistence.*;

@Entity
@Table(name = "Aluno")
public class AlunoOld {

	@Id
	@Column(name = "Cod")
	private Integer cod;
	
	@Column(name = "Nome")
	private String nome;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "NumeroMatricula")
	private String numeroDaMatricula;
	
	@Column(name = "Curso")
	private String curso;
	
	@Column(name = "DataNascimento")
	private String dataNascimento;
	
	@Column(name = "Sexo")
	private String sexo;
	
	@Column(name = "Login")
	private String login;
	
	@Column(name = "Senha")
	private String senha;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumeroDaMatricula() {
		return numeroDaMatricula;
	}

	public void setNumeroDaMatricula(String numeroDaMatricula) {
		this.numeroDaMatricula = numeroDaMatricula;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
