package br.com.fiap.models;

public class TipoSeguro {
    private long idTipoSeguro;
    private String descricao;
    private String categoria;
    private double valor;  // Novo atributo para armazenar o valor do seguro

    // Construtor atualizado para incluir o valor
    public TipoSeguro(long idTipoSeguro, String descricao, String categoria, double valor) {
        this.idTipoSeguro = idTipoSeguro;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;  // Atribuindo o valor
    }

    // Construtor sem valor (caso necessário)
    public TipoSeguro() {}

    public long getIdTipoSeguro() {
        return idTipoSeguro;
    }

    public void setIdTipoSeguro(long idTipoSeguro) {
        this.idTipoSeguro = idTipoSeguro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Getter e setter para o valor
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    // Atualizando o método toString para incluir o valor
    @Override
    public String toString() {
        return "ID: " + idTipoSeguro + ", Tipo: " + descricao + ", Categoria: " + categoria + ", Valor: R$ " + valor;
    }
}
