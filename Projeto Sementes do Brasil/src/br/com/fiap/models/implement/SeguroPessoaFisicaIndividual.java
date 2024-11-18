package br.com.fiap.models.implement;

public class SeguroPessoaFisicaIndividual implements Seguro {
    private int idTipoSeguro;
    private String descricao;
    private String categoria;

    public SeguroPessoaFisicaIndividual(int idTipoSeguro, String descricao, String categoria) {
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
        // Implementação específica do cálculo para Seguro Individual
        System.out.println("Calculando prêmio para Seguro Individual...");
    }
}
