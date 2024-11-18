package br.com.fiap.models.implement;

public class SeguroEmpresaColetivo implements Seguro {
	 private long idTipoSeguro;
	    private String descricao;
	    private String categoria;
	    
		public SeguroEmpresaColetivo() {

			this.idTipoSeguro = 6;
			this.descricao = "";
			this.categoria = "Pessoa Jurídica";
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