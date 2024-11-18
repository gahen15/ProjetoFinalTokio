package br.com.fiap.models.implement;

public interface Seguro {
    int getIdTipoSeguro(); // Retorna o ID do tipo de seguro.
    String getDescricao(); // Retorna a descrição do seguro.
    String getCategoria(); // Retorna a categoria do seguro (Pessoa Física ou Jurídica).
    
    void calcularPremio(); // Método para calcular o prêmio do seguro (implementação específica).
}
