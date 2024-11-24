package br.com.fiap.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa um cliente.
 * 
 * Esta classe é utilizada como base para representar tanto clientes pessoas físicas quanto jurídicas. 
 * Contém atributos e métodos comuns para o gerenciamento de informações de um cliente, como nome, 
 * e-mail, telefone, endereço, data de cadastro, seguros e apólices associadas.
 */
public abstract class Cliente {

    // Atributos
    private Long idCliente;
    private String nome, email, telefone, endereco;
    private Date dataCadastro;
    private List<TipoSeguro> seguros;  // Lista para armazenar os tipos de seguros associados ao cliente
    private List<Apolice> apolices;    // Lista para armazenar as apólices associadas ao cliente

    /**
     * Construtor vazio para inicializar um objeto Cliente.
     * Inicializa as listas de seguros e apólices como listas vazias.
     */
    public Cliente() {
        this.seguros = new ArrayList<>();  // Inicializa a lista de seguros
        this.apolices = new ArrayList<>(); // Inicializa a lista de apólices
    }

    /**
     * Construtor com parâmetros para inicializar um objeto Cliente com informações fornecidas.
     * 
     * @param nome O nome do cliente.
     * @param email O e-mail do cliente.
     * @param telefone O telefone do cliente.
     * @param endereco O endereço do cliente.
     */
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

    /**
     * Retorna a lista de seguros associados ao cliente.
     * 
     * @return Lista de objetos TipoSeguro.
     */
    public List<TipoSeguro> getSeguros() {
        return seguros;
    }

    /**
     * Adiciona um seguro à lista de seguros do cliente.
     * 
     * @param seguro O objeto TipoSeguro a ser adicionado.
     */
    public void adicionarSeguro(TipoSeguro seguro) {
        if (seguro != null) {
            this.seguros.add(seguro);
        }
    }

    /**
     * Remove um seguro da lista de seguros do cliente.
     * 
     * @param seguro O objeto TipoSeguro a ser removido.
     */
    public void removerSeguro(TipoSeguro seguro) {
        if (seguro != null) {
            this.seguros.remove(seguro);
        }
    }

    /**
     * Retorna a lista de apólices associadas ao cliente.
     * 
     * @return Lista de objetos Apolice.
     */
    public List<Apolice> getApolices() {
        return apolices;
    }

    /**
     * Adiciona uma apólice à lista de apólices do cliente.
     * 
     * @param apolice O objeto Apolice a ser adicionado.
     */
    public void adicionarApolice(Apolice apolice) {
        if (apolice != null) {
            this.apolices.add(apolice);
        }
    }

    /**
     * Remove uma apólice da lista de apólices do cliente.
     * 
     * @param apolice O objeto Apolice a ser removido.
     */
    public void removerApolice(Apolice apolice) {
        if (apolice != null) {
            this.apolices.remove(apolice);
        }
    }

    /**
     * Método abstrato para retornar o documento (CNPJ ou CPF) do cliente.
     * Este método deve ser implementado pelas subclasses.
     * 
     * @return O documento do cliente (CNPJ ou CPF).
     */
    public abstract String getDocumento();

    /**
     * Método abstrato para retornar o tipo de cliente (Pessoa Física ou Jurídica).
     * Este método deve ser implementado pelas subclasses.
     * 
     * @return O tipo de cliente (ex: "Pessoa Física", "Pessoa Jurídica").
     */
    public abstract String getTipoCliente();

    /**
     * Retorna uma string representando as informações básicas do cliente, incluindo ID, nome, endereço e telefone.
     * 
     * @return Uma string com informações básicas sobre o cliente.
     */
    @Override
    public String toString() {
        return "Id: " + idCliente + ", Nome: " + nome + ", Endereço: " + endereco + ", Telefone: " + telefone;
    }
}
