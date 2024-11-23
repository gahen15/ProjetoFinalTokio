package br.com.fiap.models;

import java.util.Date;

public class Apolice {
    private long id; // Identificador único da apólice
    private Cliente cliente; // Cliente associado à apólice (pode ser ClienteEmpresa ou ClientePessoaFisica)
    private TipoSeguro tipoSeguro; // Tipo de seguro associado
    private Date dataEmissao; // Data de emissão da apólice
    private double valor; // Valor da apólice
    private String status; // Status da apólice (ativa, cancelada, etc.)

    public Apolice() {}
    // Construtor
    public Apolice( Cliente cliente, TipoSeguro tipoSeguro, double valor) {
      
        this.cliente = cliente;
        this.tipoSeguro = tipoSeguro;
        this.dataEmissao =new java.sql.Date(System.currentTimeMillis());
        this.valor = valor;
       
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoSeguro getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(TipoSeguro tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
