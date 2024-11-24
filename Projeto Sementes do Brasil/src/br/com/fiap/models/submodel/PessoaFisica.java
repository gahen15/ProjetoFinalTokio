package br.com.fiap.models.submodel;

import br.com.fiap.models.Cliente;
import br.com.fiap.models.EstadoCivil;

import java.sql.Date; // Para representar a data de nascimento

/**
 * Classe que representa uma Pessoa Física, um tipo específico de Cliente.
 * Herda os atributos da classe Cliente e adiciona informações específicas
 * sobre uma pessoa física, como CPF, profissão, estado civil e data de nascimento.
 */
public class PessoaFisica extends Cliente {

    // Atributos específicos de Pessoa Física
    private String cpf, profissao;
    
    // Tipo de cliente, que é sempre "Pessoa Física"
    private final String tipoCliente = "Pessoa Física";
    
    // Atributos adicionais para estado civil e data de nascimento
    private EstadoCivil estadoCivil;
    private Date dataNascimento;

    /**
     * Construtor padrão da classe PessoaFisica.
     * Inicializa um objeto PessoaFisica sem parâmetros.
     */
    public PessoaFisica() {}

    /**
     * Construtor da classe PessoaFisica. Recebe parâmetros para inicializar a pessoa física
     * e valida o CPF.
     * 
     * @param nome Nome da pessoa.
     * @param email Email de contato.
     * @param telefone Telefone de contato.
     * @param endereco Endereço da pessoa.
     * @param cpf CPF da pessoa. Deve ter exatamente 11 dígitos.
     * @param estadoCivil Estado civil da pessoa, representado por um enum EstadoCivil.
     * @param profissao Profissão da pessoa.
     * @param dataNascimento Data de nascimento da pessoa.
     * @throws IllegalArgumentException Caso o CPF seja inválido (não tenha 11 dígitos).
     */
    public PessoaFisica(String nome, String email, String telefone, String endereco, 
                        String cpf, EstadoCivil estadoCivil, String profissao, Date dataNascimento) {
        super(nome, email, telefone, endereco);

        // Valida o CPF, que deve ter 11 caracteres
        if (cpf != null && cpf.length() == 11) {
            this.cpf = cpf;
        } else {
            throw new IllegalArgumentException("CPF inválido. O CPF deve ter 11 caracteres.");
        }

        this.estadoCivil = estadoCivil;
        this.profissao = profissao;
        this.dataNascimento = dataNascimento; // Inicializando a data de nascimento
    }

    /**
     * Retorna o CPF da pessoa.
     * 
     * @return O CPF da pessoa.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna o documento da pessoa, que neste caso é o CPF.
     * 
     * @return O CPF da pessoa.
     */
    public String getDocumento() {
        return cpf;
    }

    /**
     * Define o CPF da pessoa.
     * 
     * @param cpf O novo CPF da pessoa.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna a profissão da pessoa.
     * 
     * @return A profissão da pessoa.
     */
    public String getProfissao() {
        return profissao;
    }

    /**
     * Define a profissão da pessoa.
     * 
     * @param profissao A nova profissão da pessoa.
     */
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    /**
     * Retorna o estado civil da pessoa.
     * 
     * @return O estado civil da pessoa, representado por um enum EstadoCivil.
     */
    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Define o estado civil da pessoa.
     * 
     * @param estadoCivil O novo estado civil da pessoa.
     * @throws IllegalArgumentException Se o estado civil for nulo.
     */
    public void setEstadoCivil(EstadoCivil estadoCivil) {
        if (estadoCivil == null) {
            throw new IllegalArgumentException("O Estado Civil não pode ser vazio!");
        }
        this.estadoCivil = estadoCivil;
    }

    /**
     * Retorna o tipo de cliente, que é sempre "Pessoa Física" para esta classe.
     * 
     * @return O tipo de cliente ("Pessoa Física").
     */
    public String getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Retorna a data de nascimento da pessoa.
     * 
     * @return A data de nascimento da pessoa.
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Define a data de nascimento da pessoa.
     * 
     * @param dataNascimento A nova data de nascimento da pessoa.
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
