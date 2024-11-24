package br.com.fiap.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {
    
    // Atributos
    private Long idCliente;
    private String nome, email, telefone, endereco;
    private Date dataCadastro;
    private List<TipoSeguro> seguros;  // Lista para armazenar os tipos de seguros associados ao cliente
    private List<Apolice> apolices;    // Lista para armazenar as apólices associadas ao cliente

    // Construtor vazio
    public Cliente() {
        this.seguros = new ArrayList<>();  // Inicializa a lista de seguros
        this.apolices = new ArrayList<>(); // Inicializa a lista de apólices
    }

    // Construtor com parâmetros
    public Cliente(String nome, String email, String telefone, String endereco) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataCadastro = new java.sql.Date(System.currentTimeMillis());
        this.seguros = new ArrayList<>();  // Inicializa a lista de seguros
        this.apolices = new ArrayList<>(); // Inicializa a lista de apólices
    }

    // Getters e Setters
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    // Getter para a lista de seguros
    public List<TipoSeguro> getSeguros() {
        return seguros;
    }

    // Método para adicionar um seguro à lista de seguros
    public void adicionarSeguro(TipoSeguro seguro) {
        if (seguro != null) {
            this.seguros.add(seguro);
        }
    }

    // Método para remover um seguro da lista
    public void removerSeguro(TipoSeguro seguro) {
        if (seguro != null) {
            this.seguros.remove(seguro);
        }
    }

    // Getter para a lista de apólices
    public List<Apolice> getApolices() {
        return apolices;
    }

    // Método para adicionar uma apólice à lista de apólices
    public void adicionarApolice(Apolice apolice) {
        if (apolice != null) {
            this.apolices.add(apolice);
        }
    }

    // Método para remover uma apólice da lista de apólices
    public void removerApolice(Apolice apolice) {
        if (apolice != null) {
            this.apolices.remove(apolice);
        }
    }
    public abstract String getDocumento();
    // Método abstrato para retornar o tipo de cliente
    public abstract String getTipoCliente();

    @Override
    public String toString() {
        // Formatar a string para exibir as informações do cliente de forma legível
        return "Id: " + idCliente + ", Nome: " + nome + ", Endereço: " + endereco + ", Telefone: " + telefone;
    }
}
