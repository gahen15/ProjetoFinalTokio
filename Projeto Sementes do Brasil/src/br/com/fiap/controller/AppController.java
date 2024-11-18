package br.com.fiap.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.submodel.Empresa;
import br.com.fiap.models.submodel.PessoaFisica;


public class AppController {
	private static AppController instance;
	private Connection connection;
	private ClienteDAO clienteDAO;

	private AppController() throws SQLException {
		this.connection = new ConnectionFactory().conectar();
		this.clienteDAO = new ClienteDAO(connection);

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
            clienteDAO.InserirCliente(cliente); // Delegando a inserção para o ClienteDAO
        } catch (Exception e) {
            e.printStackTrace(); // Tratar a exceção adequadamente
        }
    }

	public void deletarCliente(long id) {
		try {
			clienteDAO.deletarCliente(id);		}catch(Exception e) { e.printStackTrace();}
	}
	
	
	 // Método para listar todos os clientes com seus atributos
    public List<Cliente> listarClientes() {
        // Chama o DAO para obter a lista de clientes
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
            System.out.println("Tipo Cliente: " + (cliente instanceof PessoaFisica ? "Pessoa Física" : 
                cliente instanceof Empresa ? "Empresa" : "Desconhecido"));

            System.out.println("Data Cadastro: " + cliente.getDataCadastro());

            // Verifica se o cliente é Pessoa Física e exibe seus atributos
            if (cliente instanceof PessoaFisica) {
                PessoaFisica pf = (PessoaFisica) cliente;
                System.out.println("CPF: " + pf.getCpf());
                System.out.println("Estado Civil: " + pf.getEstadoCivil());
                System.out.println("Profissão: " + pf.getProfissao());
            }
            // Verifica se o cliente é uma Empresa e exibe seus atributos
            else if (cliente instanceof Empresa) {
                Empresa emp = (Empresa) cliente;
                System.out.println("CNPJ: " + emp.getCnpj());
                System.out.println("Nome Fantasia: " + emp.getNomeFantasia());
                System.out.println("Razão Social: " + emp.getRazaoSocial());
            }
            System.out.println("--------------------------------------");
        }
    }
	
}
