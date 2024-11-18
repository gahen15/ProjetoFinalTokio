package br.com.fiap.models.implement;

public class SeguroPessoaFisicaIndividual implements Seguro {
	private long idTipoSeguro;
	private String descricao;
	private String categoria;

	public SeguroPessoaFisicaIndividual() {
		this.idTipoSeguro = 1;
		this.descricao = "";
		this.categoria = "Pessoa Física";
	}

	@Override
	public long getIdTipoSeguro() {
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