package br.com.fiap.models;

import java.util.Date;

/**
 * Classe que representa uma apólice de seguro.
 * 
 * Uma apólice de seguro é associada a um cliente e a um tipo de seguro. Ela contém informações como o valor do seguro, 
 * a data de emissão, e o status da apólice (ativa, cancelada, etc.).
 */
public class Apolice {
    
    // Atributos
    private long id; // Identificador único da apólice
    private Cliente cliente; // Cliente associado à apólice (pode ser ClienteEmpresa ou ClientePessoaFisica)
    private TipoSeguro tipoSeguro; // Tipo de seguro associado
    private Date dataEmissao; // Data de emissão da apólice
    private double valor; // Valor da apólice
    private String status; // Status da apólice (ativa, cancelada, etc.)

    /**
     * Construtor vazio para inicializar uma apólice de seguro.
     * Este construtor é utilizado para criar uma apólice sem valores iniciais.
     */
    public Apolice() {}

    /**
     * Construtor para inicializar uma apólice com os dados necessários.
     * 
     * @param cliente O cliente associado à apólice (Pessoa Física ou Jurídica).
     * @param tipoSeguro O tipo de seguro associado à apólice.
     * @param valor O valor da apólice.
     */
    public Apolice(Cliente cliente, TipoSeguro tipoSeguro, double valor) {
        this.cliente = cliente;
        this.tipoSeguro = tipoSeguro;
        this.dataEmissao = new java.sql.Date(System.currentTimeMillis()); // A data de emissão é definida no momento da criação
        this.valor = valor;
    }

    // Getters e Setters

    /**
     * Retorna o identificador único da apólice.
     * 
     * @return O ID da apólice.
     */
    public long getId() {
        return id;
    }

    /**
     * Define o identificador único da apólice.
     * 
     * @param id O ID a ser atribuído à apólice.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna o cliente associado à apólice.
     * 
     * @return O cliente associado à apólice.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente associado à apólice.
     * 
     * @param cliente O cliente a ser associado à apólice.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna o tipo de seguro associado à apólice.
     * 
     * @return O tipo de seguro associado.
     */
    public TipoSeguro getTipoSeguro() {
        return tipoSeguro;
    }

    /**
     * Define o tipo de seguro associado à apólice.
     * 
     * @param tipoSeguro O tipo de seguro a ser associado à apólice.
     */
    public void setTipoSeguro(TipoSeguro tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    /**
     * Retorna a data de emissão da apólice.
     * 
     * @return A data de emissão da apólice.
     */
    public Date getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define a data de emissão da apólice.
     * 
     * @param dataEmissao A data de emissão a ser atribuída à apólice.
     */
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    /**
     * Retorna o valor da apólice.
     * 
     * @return O valor da apólice.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor da apólice.
     * 
     * @param valor O valor a ser atribuído à apólice.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Retorna o status da apólice.
     * 
     * @return O status da apólice (ex: ativa, cancelada, etc.).
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o status da apólice.
     * 
     * @param status O status a ser atribuído à apólice (ex: ativa, cancelada, etc.).
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
