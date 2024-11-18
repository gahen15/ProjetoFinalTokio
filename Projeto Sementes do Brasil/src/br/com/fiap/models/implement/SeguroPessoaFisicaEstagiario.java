package br.com.fiap.models.implement;

public class SeguroPessoaFisicaEstagiario implements Seguro {
    private int idTipoSeguro;
    private String descricao;
    private String categoria;

    public SeguroPessoaFisicaEstagiario(int idTipoSeguro, String descricao, String categoria) {
        this.idTipoSeguro = idTipoSeguro;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    @Override
    public int getIdTipoSeguro() {
        return idTipoSeguro;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getCategoria() {
        return categoria;
    }

    @Override
    public void calcularPremio() {
        // Implementação específica do cálculo para Seguro Estagiário
        System.out.println("Calculando prêmio para Seguro Estagiário...");
    }
}
