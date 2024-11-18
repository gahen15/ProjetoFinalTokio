package br.com.fiap.models.submodel;

import br.com.fiap.models.Cliente;
import br.com.fiap.models.TipoCliente;

public class Empresa extends Cliente{
	private String cnpj, nomeFantasia, razaoSocial;
	private final TipoCliente tipoCliente = TipoCliente.JURIDICA;
	public Empresa() {}
	public Empresa(String nome, String email, String telefone, String endereco, String cnpj, String nomeFantasia, String razaoSocial) {
	    super(nome, email, telefone, endereco);

	    // Valida o CNPJ
	    if (!validarCnpj(cnpj)) {
	        throw new IllegalArgumentException("CNPJ inválido. Deve ter exatamente 14 dígitos numéricos.");
	    }

	    this.cnpj = cnpj;
	    this.razaoSocial = razaoSocial;
	    this.nomeFantasia = nomeFantasia;
	}

	// Método de validação de CNPJ
	private boolean validarCnpj(String cnpj) {
	    // Remove caracteres não numéricos
	    cnpj = cnpj.replaceAll("[^0-9]", "");

	    // Verifica se o CNPJ tem exatamente 14 dígitos
	    return cnpj.length() == 14;
	}


	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	public TipoCliente getTipoCliente() {
		return  tipoCliente;
	}

}
