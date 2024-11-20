package br.com.fiap.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.dao.SeguroDAO;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.EstadoCivil;
import br.com.fiap.models.implement.Seguro;
import br.com.fiap.models.submodel.Empresa;
import br.com.fiap.models.submodel.PessoaFisica;
import java.sql.Date; // Importando a classe Date

public class AppController {
    private static AppController instance;
    private Connection connection;
    private ClienteDAO clienteDAO;
    private SeguroDAO seguroDAO;

    private AppController() throws SQLException {
        this.connection = new ConnectionFactory().conectar();
        this.clienteDAO = new ClienteDAO(connection);
        this.seguroDAO = new SeguroDAO(connection);
    }

    public static AppController getInstance() throws SQLException {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void inserirCliente(Cliente cliente) {
        try {
            // Caso o cliente seja uma Pessoa Física, podemos passar a data de nascimento
            if (cliente instanceof PessoaFisica) {
                PessoaFisica pf = (PessoaFisica) cliente;
                Date dataNascimento = pf.getDataNascimento();
                // Pode-se validar ou processar a data antes de passar para o DAO
                clienteDAO.InserirCliente(cliente);
            } else {
                clienteDAO.InserirCliente(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Tratar a exceção adequadamente
        }
    }

    public void deletarCliente(long id) {
        try {
            clienteDAO.deletarCliente(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os clientes com seus atributos
    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

    // Método adicional para exibir os clientes (caso precise)
    public void exibirClientes() {
        List<Cliente> clientes = listarClientes();

        for (Cliente cliente : clientes) {
            // Exibe os dados comuns de todos os clientes
            System.out.println("ID Cliente: " + cliente.getIdCliente());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("Endereço: " + cliente.getEndereco());
            System.out.println("Tipo Cliente: " + (cliente instanceof PessoaFisica ? "Pessoa Física"
                    : cliente instanceof Empresa ? "Empresa" : "Desconhecido"));

            System.out.println("Data Cadastro: " + cliente.getDataCadastro());

            // Exibindo a data de nascimento se for Pessoa Física
            if (cliente instanceof PessoaFisica) {
                PessoaFisica pf = (PessoaFisica) cliente;
                System.out.println("CPF: " + pf.getCpf());
                System.out.println("Estado Civil: " + pf.getEstadoCivil());
                System.out.println("Profissão: " + pf.getProfissao());
                System.out.println("Data de Nascimento: " + pf.getDataNascimento());
            }
            // Exibindo dados da Empresa
            else if (cliente instanceof Empresa) {
                Empresa emp = (Empresa) cliente;
                System.out.println("CNPJ: " + emp.getCnpj());
                System.out.println("Nome Fantasia: " + emp.getNomeFantasia());
                System.out.println("Razão Social: " + emp.getRazaoSocial());
            }
            System.out.println("--------------------------------------");
        }
    }

    public void associarSeguroCliente(long idCliente, Seguro seguro) {
        seguroDAO.associarSeguroAoCliente(idCliente, seguro);
    }

    public void listarTipoSeguro(long id) {
        seguroDAO.listarClientesPorTipoSeguro(id);
    }

    public void listarSegurosPorCliente(long id) {
        seguroDAO.listarSegurosPorCliente(id);
    }
    public void cadastrarClienteFisico(String nome, String email, String telefone, String endereco, 
            String cpf, EstadoCivil estadoCivil, String profissao, Date dataNascimento) {
    	
    	
    	Cliente cliente = new PessoaFisica(nome, email, telefone, endereco, 
                 cpf, estadoCivil, profissao, dataNascimento);
    	
    	inserirCliente(cliente);
    	
    }
    public void cadastrarClienteEmpresa(String nome, String email, String telefone, String endereco, String cnpj, String nomeFantasia, String razaoSocial) {
    	
    	
    	Cliente cliente = new Empresa(nome, email, telefone, endereco, 
    			cnpj, nomeFantasia, razaoSocial);
    	
    	inserirCliente(cliente);
    	
    }


}
