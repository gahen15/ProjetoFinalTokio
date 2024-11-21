package br.com.fiap.models;

public class TipoSeguro {
    private long idTipoSeguro;
    private String descricao;
    private String categoria;

    public TipoSeguro() {}
    public TipoSeguro(long idTipoSeguro, String descricao, String categoria) {
        this.idTipoSeguro = idTipoSeguro;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public long getIdTipoSeguro() {
        return idTipoSeguro;
    }

    public void setIdTipoSeguro(int idTipoSeguro) {
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

    @Override
    public String toString() {
        return "ID: " + idTipoSeguro + ", Tipo: " + descricao + ", Categoria: " + categoria;
    }
  
	public void setId(long long1) {
		this.idTipoSeguro = long1;
		
	}
}
