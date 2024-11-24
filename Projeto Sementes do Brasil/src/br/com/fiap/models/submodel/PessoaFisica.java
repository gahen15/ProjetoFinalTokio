package br.com.fiap.models.submodel;

import br.com.fiap.models.Cliente;
import br.com.fiap.models.EstadoCivil;

import java.sql.Date; // Para representar a data de nascimento

public class PessoaFisica extends Cliente {

    private String cpf, profissao;
    private final String tipoCliente = "Pessoa Física";
    private EstadoCivil estadoCivil;
    private Date dataNascimento; // Novo campo para data de nascimento

    public PessoaFisica() {}

    public PessoaFisica(String nome, String email, String telefone, String endereco, 
                        String cpf, EstadoCivil estadoCivil, String profissao, Date dataNascimento) {
        super(nome, email, telefone, endereco);

        // Validação do tamanho do CPF
        if (cpf != null && cpf.length() == 11) {
            this.cpf = cpf;
        } else {
            throw new IllegalArgumentException("CPF inválido. O CPF deve ter 11 caracteres.");
        }

        this.estadoCivil = estadoCivil;
        this.profissao = profissao;
        this.dataNascimento = dataNascimento; // Inicializando dataNascimento
    }

    public String getCpf() {
        return cpf;
    }
    public String getDocumento() {
    	return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        if (estadoCivil == null) {
            throw new IllegalArgumentException("O Estado Civil não pode ser vazio!");
        }
        this.estadoCivil = estadoCivil;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
}
