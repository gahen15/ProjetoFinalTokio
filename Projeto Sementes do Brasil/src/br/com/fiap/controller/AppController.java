package br.com.fiap.controller;

import java.sql.Connection;
import java.sql.Date; // Importando a classe Date
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.dao.SeguroDAO;
import br.com.fiap.models.Apolice;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.EstadoCivil;
import br.com.fiap.models.TipoSeguro;
import br.com.fiap.models.submodel.Empresa;
import br.com.fiap.models.submodel.PessoaFisica;

public class AppController {
	private static final String PASS = null;
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
	public long verificarIdApolicePorTipoSeguro(long idCliente, long idTipoSeguro) throws SQLException {
	    String query = "SELECT a.id " +
	                   "FROM T_Apolice a " +
	                   "JOIN T_Cliente c ON a.idCliente = c.idCliente " +
	                   "JOIN TipoSeguro t ON a.idTipoSeguro = t.idTipoSeguro " +
	                   "WHERE a.idCliente = ? AND a.idTipoSeguro = ?";

	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setLong(1, idCliente); // Cliente
	        stmt.setLong(2, idTipoSeguro); // Tipo de seguro

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                // Retorna o ID da apólice encontrada
	                return rs.getLong("id");
	            } else {
	                throw new SQLException("Nenhuma apólice encontrada para o cliente com o tipo de seguro fornecido.");
	            }
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Erro ao verificar o ID da apólice: " + e.getMessage());
	    }
	}

	   // Método para alterar o status de uma apólice, agora incluindo o tipo de seguro e cliente
    public void alterarStatusApolice( long idCliente, long idTipoSeguro, String novoStatus) {
        try {
           long idApolice = verificarIdApolicePorTipoSeguro(idCliente, idTipoSeguro);
            seguroDAO.alterarStatusApolice(idApolice, idCliente, idTipoSeguro, novoStatus);
        } catch (SQLException e) {
            // Exibe mensagem de erro ou lida com a exceção conforme necessário
            System.err.println("Erro ao alterar o status da apólice: " + e.getMessage());
        }
    }
	public void inserirApolice(Apolice apolice) throws SQLException {
		seguroDAO.inserir(apolice);
	}
	public void excluirApolice(Apolice apolice) throws SQLException {
		seguroDAO.excluir(apolice.getId());
	}
	

	public List<TipoSeguro> listarSegurosDoCliente(long idCliente) throws SQLException {
		// Consulta SQL para listar os seguros associados ao cliente, incluindo o valor
		String query = "SELECT ts.idTipoSeguro, ts.descricao, ts.categoria, ts.valor " + "FROM TipoSeguro ts "
				+ "JOIN ClienteSeguro cs ON ts.idTipoSeguro = cs.idTipoSeguro " + "WHERE cs.idCliente = ?";

		List<TipoSeguro> seguros = new ArrayList<>();

		try (PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setLong(1, idCliente); // Substitui o '?' pela variável idCliente

			// Executa a consulta
			ResultSet rs = stmt.executeQuery();

			// Processa o resultado e preenche a lista de seguros
			while (rs.next()) {
				TipoSeguro seguro = new TipoSeguro();
				seguro.setIdTipoSeguro(rs.getLong("idTipoSeguro"));
				seguro.setDescricao(rs.getString("descricao"));
				seguro.setCategoria(rs.getString("categoria"));
				seguro.setValor(rs.getDouble("valor")); // Definindo o valor no objeto

				// Adiciona o seguro à lista
				seguros.add(seguro);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar seguros do cliente: " + e.getMessage());
			throw e; // Lança novamente a exceção para o tratamento no controlador
		}

		return seguros;
	}

	public boolean verificarSeguroAssociado(long idCliente, TipoSeguro tipoSeguro) throws SQLException {
		String sql = "SELECT COUNT(*) FROM clienteseguro WHERE idcliente = ? AND idTipoSeguro = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			// Definir os parâmetros da consulta
			stmt.setLong(1, idCliente); // ID do cliente
			stmt.setLong(2, tipoSeguro.getIdTipoSeguro()); // ID do seguro

			// Executar a consulta
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0; // Se contar mais de 0, o seguro está associado
			}
		}
		return false; // Retorna falso se não encontrar associação
	}

	public void removerSeguroDoCliente(long idCliente, TipoSeguro tipoSeguro) throws SQLException {
		String sql = "DELETE FROM ClienteSeguro WHERE idCliente = ? AND idTipoSeguro = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			// Definir os parâmetros da consulta
			stmt.setLong(1, idCliente); // ID do cliente
			stmt.setLong(2, tipoSeguro.getIdTipoSeguro()); // ID do seguro

			// Executar a atualização no banco de dados
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Nenhuma associação encontrada para remover.");
			}
		}
	}

	public Cliente buscarClientePorId(long id) {

		Cliente cliente = clienteDAO.buscarClientePorId(id);

		return cliente;

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

	public void associarSeguroAoCliente(long idCliente, TipoSeguro seguro) throws SQLException {
		try {
			// Validação de entrada
			if (idCliente <= 0 || seguro == null) {
				throw new IllegalArgumentException("Cliente ou seguro inválido.");
			}

			// Buscar o cliente
			Cliente cliente = buscarClientePorId(idCliente); // Método para buscar o cliente no banco de dados ou lista
			if (cliente == null) {
				throw new IllegalArgumentException("Cliente não encontrado.");
			}

			// Adicionando o seguro à lista de seguros do cliente
			cliente.adicionarSeguro(seguro); // Adiciona o seguro à lista de seguros do cliente

			// Chama o DAO para associar o seguro ao cliente no banco de dados (se
			// necessário)
			seguroDAO.associarSeguroAoCliente(idCliente, seguro);

			// Mostra uma mensagem de sucesso
			JOptionPane.showMessageDialog(null, "Seguro associado ao cliente com sucesso!");
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Erro ao associar seguro ao cliente: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public TipoSeguro buscarTipoSeguroId(long id) throws SQLException {
		TipoSeguro tipoSeguro = seguroDAO.buscarTipoSeguroPorId(id);
		return tipoSeguro;
	}

	public void listarTipoSeguro(long id) {
		seguroDAO.listarClientesPorTipoSeguro(id);
	}

	public void cadastrarClienteFisico(String nome, String email, String telefone, String endereco, String cpf,
			EstadoCivil estadoCivil, String profissao, Date dataNascimento) {

		Cliente cliente = new PessoaFisica(nome, email, telefone, endereco, cpf, estadoCivil, profissao,
				dataNascimento);

		inserirCliente(cliente);

	}

	public void cadastrarClienteEmpresa(String nome, String email, String telefone, String endereco, String cnpj,
			String nomeFantasia, String razaoSocial) {

		Cliente cliente = new Empresa(nome, email, telefone, endereco, cnpj, nomeFantasia, razaoSocial);

		inserirCliente(cliente);

	}

	public List<TipoSeguro> listarSeguros() {
		// TODO Auto-generated method stub
		return seguroDAO.listarTodosSeguros();
	}

	public Apolice buscarApolicePorId(long idApolice) {
	    String sql = "SELECT * FROM Apolices WHERE id = ?";
	    Apolice apolice = null;

	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	        preparedStatement.setLong(1, idApolice);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                apolice = new Apolice();
	                apolice.setId(resultSet.getLong("id"));

	                // Buscando o cliente associado
	                long idCliente = resultSet.getLong("id_cliente");
	                Cliente cliente = buscarClientePorId(idCliente); // Método que busca o cliente
	                apolice.setCliente(cliente);

	                // Buscando o tipo de seguro associado
	                int tipoSeguroId = resultSet.getInt("id_tipo_seguro");
	                TipoSeguro tipoSeguro = buscarTipoSeguroId(tipoSeguroId); // Método que busca o tipo de seguro
	                apolice.setTipoSeguro(tipoSeguro);

	                apolice.setDataEmissao(resultSet.getDate("data_emissao"));
	                apolice.setValor(resultSet.getDouble("valor"));
	                apolice.setStatus(resultSet.getString("status"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Use um logger ou trate a exceção de forma adequada
	    }

	    return apolice;
	}

	public List<Apolice> listarApolicesDoCliente(long idCliente) {
	    List<Apolice> apolices = new ArrayList<>();
	    String sql = """
	        SELECT a.id, a.idTipoSeguro, a.dataEmissao, a.valor, a.status,
       c.idCliente, c.tipoCliente, c.nome, c.endereco,
       ts.descricao AS descricaoSeguro,
       pf.cpf, pf.estadoCivil, pf.profissao, pf.dataNascimento, -- Dados Pessoa Física
       e.cnpj, e.nomeFantasia, e.razaoSocial -- Dados Empresa
FROM T_Apolice a
JOIN ClienteApolice ca ON a.id = ca.idApolice
JOIN T_Cliente c ON ca.idCliente = c.idCliente
JOIN TipoSeguro ts ON a.idTipoSeguro = ts.idTipoSeguro
LEFT JOIN T_PessoaFisica pf ON c.idCliente = pf.idCliente -- Join com T_PessoaFisica
LEFT JOIN T_Empresa e ON c.idCliente = e.idCliente -- Join com T_Empresa
WHERE ca.idCliente = ?

	    """;

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setLong(1, idCliente);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                // Instanciar a apólice
	                Apolice apolice = new Apolice();
	                apolice.setId(rs.getLong("id"));

	                // Configurar o cliente associado
	                String tipoCliente = rs.getString("tipoCliente");
	                Cliente cliente;
	                if ("FISICA".equalsIgnoreCase(tipoCliente)) {
	                    cliente = new PessoaFisica();
	                    ((PessoaFisica) cliente).setCpf(rs.getString("cpf"));
	                } else if ("JURIDICA".equalsIgnoreCase(tipoCliente)) {
	                    cliente = new Empresa();
	                    ((Empresa) cliente).setCnpj(rs.getString("cnpj"));
	                } else {
	                    throw new SQLException("Tipo de cliente desconhecido: " + tipoCliente);
	                }
	                cliente.setIdCliente(rs.getLong("idCliente"));
	                cliente.setNome(rs.getString("nome"));
	                cliente.setEndereco(rs.getString("endereco"));
	                apolice.setCliente(cliente);

	                // Configurar o tipo de seguro
	                TipoSeguro tipoSeguro = new TipoSeguro();
	                tipoSeguro.setIdTipoSeguro(rs.getLong("idTipoSeguro"));
	                tipoSeguro.setDescricao(rs.getString("descricaoSeguro"));
	                apolice.setTipoSeguro(tipoSeguro);

	                apolice.setDataEmissao(rs.getDate("dataEmissao"));
	                apolice.setValor(rs.getDouble("valor"));
	                apolice.setStatus(rs.getString("status"));

	                apolices.add(apolice);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return apolices;
	}


	




}
