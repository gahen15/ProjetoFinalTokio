package br.com.fiap.models.submodel;

import br.com.fiap.models.Cliente;

/**
 * Classe que representa uma Empresa, que é um tipo de Cliente (Pessoa Jurídica).
 * Herda os atributos e métodos da classe Cliente e adiciona atributos específicos
 * para a Empresa, como CNPJ, nome fantasia e razão social.
 */
public class Empresa extends Cliente {

    // Atributos específicos da classe Empresa
    private String cnpj, nomeFantasia, razaoSocial;
    
    // Tipo de cliente que será sempre "Pessoa Jurídica"
    private final String tipoCliente = "Pessoa Jurídica";
    
    /**
     * Construtor padrão da classe Empresa.
     * Inicializa um objeto Empresa sem parâmetros.
     */
    public Empresa() {}

    /**
     * Construtor da classe Empresa. Recebe parâmetros para inicializar a empresa
     * e valida o CNPJ.
     * 
     * @param nome Nome da empresa.
     * @param email Email da empresa.
     * @param telefone Telefone de contato da empresa.
     * @param endereco Endereço da empresa.
     * @param cnpj CNPJ da empresa. Deve ser válido (14 dígitos numéricos).
     * @param nomeFantasia Nome fantasia da empresa.
     * @param razaoSocial Razão social da empresa.
     * @throws IllegalArgumentException Caso o CNPJ seja inválido (não tenha 14 dígitos numéricos).
     */
    public Empresa(String nome, String email, String telefone, String endereco, String cnpj, String nomeFantasia, String razaoSocial) {
        super(nome, email, telefone, endereco);

        // Valida o CNPJ
        if (!validarCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido. Deve ter exatamente 14 dígitos numéricos.");
        }

        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
    }

    /**
     * Valida o CNPJ verificando se ele possui exatamente 14 dígitos numéricos.
     * 
     * @param cnpj O CNPJ a ser validado.
     * @return true se o CNPJ for válido (14 dígitos numéricos), false caso contrário.
     */
    private boolean validarCnpj(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Verifica se o CNPJ tem exatamente 14 dígitos
        return cnpj.length() == 14;
    }

    /**
     * Retorna o CNPJ da empresa.
     * 
     * @return O CNPJ da empresa.
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Define o CNPJ da empresa.
     * 
     * @param cnpj O novo CNPJ da empresa.
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * Retorna o nome fantasia da empresa.
     * 
     * @return O nome fantasia da empresa.
     */
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * Define o nome fantasia da empresa.
     * 
     * @param nomeFantasia O novo nome fantasia da empresa.
     */
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    /**
     * Retorna a razão social da empresa.
     * 
     * @return A razão social da empresa.
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * Define a razão social da empresa.
     * 
     * @param razaoSocial A nova razão social da empresa.
     */
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    /**
     * Retorna o tipo de cliente, que para esta classe é sempre "Pessoa Jurídica".
     * 
     * @return O tipo de cliente ("Pessoa Jurídica").
     */
    public String getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Retorna o documento da empresa, que neste caso é o CNPJ.
     * 
     * @return O CNPJ da empresa.
     */
    public String getDocumento() {
        return cnpj;
    }
}
