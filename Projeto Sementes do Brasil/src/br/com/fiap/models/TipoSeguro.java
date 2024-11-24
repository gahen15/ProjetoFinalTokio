package br.com.fiap.models;

/**
 * Representa um tipo de seguro no sistema, contendo informações sobre o tipo de seguro,
 * sua descrição, categoria e valor.
 * 
 * Esta classe é usada para armazenar e manipular os dados relacionados aos tipos de seguros
 * disponíveis, incluindo o valor associado a cada tipo de seguro.
 */
public class TipoSeguro {
    
    // Atributos da classe
    private long idTipoSeguro;  // Identificador único do tipo de seguro
    private String descricao;    // Descrição do tipo de seguro
    private String categoria;    // Categoria do tipo de seguro (ex: Saúde, Automóvel, etc.)
    private double valor;        // Valor do seguro

    /**
     * Construtor da classe TipoSeguro.
     * 
     * Inicializa um objeto TipoSeguro com os valores fornecidos.
     * 
     * @param idTipoSeguro O identificador único do tipo de seguro.
     * @param descricao A descrição do tipo de seguro.
     * @param categoria A categoria do tipo de seguro.
     * @param valor O valor associado ao tipo de seguro.
     */
    public TipoSeguro(long idTipoSeguro, String descricao, String categoria, double valor) {
        this.idTipoSeguro = idTipoSeguro;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;  // Atribui o valor do seguro
    }

    /**
     * Construtor padrão da classe TipoSeguro.
     * 
     * Inicializa um objeto TipoSeguro sem valores predefinidos.
     */
    public TipoSeguro() {}

    /**
     * Retorna o identificador único do tipo de seguro.
     * 
     * @return O id do tipo de seguro.
     */
    public long getIdTipoSeguro() {
        return idTipoSeguro;
    }

    /**
     * Define o identificador único do tipo de seguro.
     * 
     * @param idTipoSeguro O identificador único do tipo de seguro.
     */
    public void setIdTipoSeguro(long idTipoSeguro) {
        this.idTipoSeguro = idTipoSeguro;
    }

    /**
     * Retorna a descrição do tipo de seguro.
     * 
     * @return A descrição do tipo de seguro.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do tipo de seguro.
     * 
     * @param descricao A descrição do tipo de seguro.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a categoria do tipo de seguro.
     * 
     * @return A categoria do tipo de seguro.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do tipo de seguro.
     * 
     * @param categoria A categoria do tipo de seguro.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Retorna o valor do tipo de seguro.
     * 
     * @return O valor do seguro.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor do tipo de seguro.
     * 
     * @param valor O valor do seguro.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Retorna uma representação em formato de string do objeto TipoSeguro.
     * Inclui o id, descrição, categoria e valor do seguro.
     * 
     * @return Uma string representando os detalhes do tipo de seguro.
     */
    @Override
    public String toString() {
        return "ID: " + idTipoSeguro + ", Tipo: " + descricao + ", Categoria: " + categoria + ", Valor: R$ " + valor;
    }
}
