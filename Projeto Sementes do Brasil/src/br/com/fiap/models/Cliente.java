package br.com.fiap.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.models.implement.Seguro;

public abstract class Cliente {
	
	
	//Atributos
	private Long IdCliente;
	
	private String nome, email, telefone, endereco;
	private Date dataCadastro;
	private List<Seguro> seguro;
	
	public Cliente() {}
	
	//Construtor
	public Cliente(String nome, String email, String telefone, String endereco) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
		this.dataCadastro = new java.sql.Date(System.currentTimeMillis());
		this.seguro = new ArrayList<Seguro>();
	}
	
	
	public void adicionarSeguro(Seguro seguro) {
		this.seguro.add(seguro);
	}
	
	public List<Seguro> getSeguros(){
		return seguro;
	}
	
	
	// getter & setters
	public void setIdCliente(Long idCliente) {
		this.IdCliente = idCliente;
	}
	
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Long getIdCliente() {
		return IdCliente;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	
	
	
	
	
	
	
	
}
