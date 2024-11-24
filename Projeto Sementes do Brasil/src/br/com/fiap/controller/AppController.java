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

/**
 * Controlador principal do aplicativo, responsável pela gestão das operações de
 * cadastro, atualização e remoção de clientes e apólices. Realiza a interação
 * entre as classes DAO e o banco de dados, além de fornecer métodos para
 * manipulação de dados de clientes e seguros.
 */
public class AppController {
	private static AppController instance;
	private Connection connection;
	private ClienteDAO clienteDAO;
	private SeguroDAO seguroDAO;

	/**
	 * Construtor privado para inicializar a conexão com o banco de dados e os DAOs.
	 *
	 * @throws SQLException Caso ocorra um erro ao estabelecer a conexão com o banco
	 *                      de dados.
	 */
	private AppController() throws SQLException {
		this.connection = new ConnectionFactory().conectar();
		this.clienteDAO = new ClienteDAO(connection);
		this.seguroDAO = new SeguroDAO(connection);
	}

	/**
	 * Obtém a instância única (singleton) do controlador.
	 *
	 * @return A instância do controlador.
	 * @throws SQLException Caso ocorra um erro ao obter a conexão.
	 */
	public static AppController getInstance() throws SQLException {
		if (instance == null) {
			instance = new AppController();
		}
		return instance;
	}

	/**
	 * Verifica o ID da apólice de um cliente com base no tipo de seguro.
	 *
	 * @param idCliente    O ID do cliente.
	 * @param idTipoSeguro O ID do tipo de seguro.
	 * @return O ID da apólice associada ao cliente e tipo de seguro.
	 * @throws SQLException Caso ocorra um erro ao consultar o banco de dados.
	 */
	public long verificarIdApolicePorTipoSeguro(long idCliente, long idTipoSeguro) throws SQLException {
		String query = "SELECT a.id " + "FROM T_Apolice a " + "JOIN T_Cliente c ON a.idCliente = c.idCliente "
				+ "JOIN TipoSeguro t ON a.idTipoSeguro = t.idTipoSeguro "
				+ "WHERE a.idCliente = ? AND a.idTipoSeguro = ?";

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

	/**
	 * Altera o status de uma apólice com base no ID do cliente e tipo de seguro.
	 *
	 * @param idCliente    O ID do cliente.
	 * @param idTipoSeguro O ID do tipo de seguro.
	 * @param novoStatus   O novo status da apólice.
	 */

	public void alterarStatusApolice(long idCliente, long idTipoSeguro, String novoStatus) {
		try {
			long idApolice = verificarIdApolicePorTipoSeguro(idCliente, idTipoSeguro);
			seguroDAO.alterarStatusApolice(idApolice, idCliente, idTipoSeguro, novoStatus);
		} catch (SQLException e) {
			// Exibe mensagem de erro ou lida com a exceção conforme necessário
			System.err.println("Erro ao alterar o status da apólice: " + e.getMessage());
		}
	}

	/**
	 * Insere uma nova apólice no banco de dados.
	 *
	 * @param apolice A apólice a ser inserida.
	 * @throws SQLException Caso ocorra um erro ao inserir a apólice.
	 */
	public void inserirApolice(Apolice apolice) throws SQLException {
		seguroDAO.inserir(apolice);
	}

	/**
	 * Exclui uma apólice do banco de dados.
	 *
	 * @param apolice A apólice a ser excluída.
	 * @throws SQLException Caso ocorra um erro ao excluir a apólice.
	 */
	public void excluirApolice(Apolice apolice) throws SQLException {
		seguroDAO.excluir(apolice.getId());
	}

	/**
	 * Lista todos os seguros associados a um cliente.
	 *
	 * @param idCliente O ID do cliente.
	 * @return Uma lista de seguros associados ao cliente.
	 * @throws SQLException Caso ocorra um erro ao consultar o banco de dados.
	 */
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
	/**
	 * Verifica se um seguro está associado a um cliente.
	 *
	 * @param idCliente O ID do cliente.
	 * @param tipoSeguro O tipo de seguro.
	 * @return True se o seguro estiver associado ao cliente, false caso contrário.
	 * @throws SQLException Caso ocorra um erro ao consultar o banco de dados.
	 */
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
	/**
	 * Remove um seguro associado a um cliente.
	 *
	 * @param idCliente O ID do cliente.
	 * @param tipoSeguro O tipo de seguro a ser removido.
	 * @throws SQLException Caso ocorra um erro ao excluir a associação.
	 */
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
	/**
	 * Busca um cliente no banco de dados pelo seu ID.
	 *
	 * @param id O ID do cliente.
	 * @return O cliente encontrado ou null se não encontrado.
	 */
	public Cliente buscarClientePorId(long id) {

		Cliente cliente = clienteDAO.buscarClientePorId(id);

		return cliente;

	}
	/**
	 * Fecha a conexão com o banco de dados.
	 *
	 * @throws SQLException Caso ocorra um erro ao fechar a conexão.
	 */
	public void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
	 
	/**
	 * Insere um novo cliente no banco de dados, podendo ser Pessoa Física ou Jurídica.
	 *
	 * @param cliente O cliente a ser inserido.
	 */
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
	/**
	 * Deleta um cliente do sistema com base no ID fornecido.
	 * 
	 * @param id O ID do cliente a ser deletado.
	 */
	public void deletarCliente(long id) {
		try {
			clienteDAO.deletarCliente(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todos os clientes cadastrados no sistema.
	 * 
	 * @return Uma lista de todos os clientes.
	 */
	public List<Cliente> listarClientes() {
		return clienteDAO.listarClientes();
	}
	/**
	 * Associa um seguro a um cliente, validando os parâmetros e atualizando a base de dados.
	 * 
	 * @param idCliente O ID do cliente a ser associado.
	 * @param seguro O tipo de seguro a ser associado.
	 * @throws SQLException Se ocorrer um erro ao realizar a operação no banco de dados.
	 */
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
	/**
	 * Busca um tipo de seguro no banco de dados com base no ID fornecido.
	 * 
	 * @param id O ID do tipo de seguro.
	 * @return O tipo de seguro correspondente ao ID fornecido.
	 * @throws SQLException Se ocorrer um erro ao realizar a operação no banco de dados.
	 */
	public TipoSeguro buscarTipoSeguroId(long id) throws SQLException {
		TipoSeguro tipoSeguro = seguroDAO.buscarTipoSeguroPorId(id);
		return tipoSeguro;
	}
	/**
	 * Lista todos os clientes associados a um tipo de seguro.
	 * 
	 * @param id O ID do tipo de seguro.
	 */
	public void listarTipoSeguro(long id) {
		seguroDAO.listarClientesPorTipoSeguro(id);
	}
	/**
	 * Cadastra um novo cliente do tipo Pessoa Física no sistema.
	 * 
	 * @param nome O nome do cliente.
	 * @param email O email do cliente.
	 * @param telefone O telefone do cliente.
	 * @param endereco O endereço do cliente.
	 * @param cpf O CPF do cliente.
	 * @param estadoCivil O estado civil do cliente.
	 * @param profissao A profissão do cliente.
	 * @param dataNascimento A data de nascimento do cliente.
	 */
	public void cadastrarClienteFisico(String nome, String email, String telefone, String endereco, String cpf,
			EstadoCivil estadoCivil, String profissao, Date dataNascimento) {

		Cliente cliente = new PessoaFisica(nome, email, telefone, endereco, cpf, estadoCivil, profissao,
				dataNascimento);

		inserirCliente(cliente);

	}
	/**
	 * Cadastra um novo cliente do tipo Empresa no sistema.
	 * 
	 * @param nome O nome da empresa.
	 * @param email O email da empresa.
	 * @param telefone O telefone da empresa.
	 * @param endereco O endereço da empresa.
	 * @param cnpj O CNPJ da empresa.
	 * @param nomeFantasia O nome fantasia da empresa.
	 * @param razaoSocial A razão social da empresa.
	 */
	public void cadastrarClienteEmpresa(String nome, String email, String telefone, String endereco, String cnpj,
			String nomeFantasia, String razaoSocial) {

		Cliente cliente = new Empresa(nome, email, telefone, endereco, cnpj, nomeFantasia, razaoSocial);

		inserirCliente(cliente);

	}
	/**
	 * Lista todos os tipos de seguro disponíveis no sistema.
	 * 
	 * @return Uma lista de todos os tipos de seguros.
	 */
	public List<TipoSeguro> listarSeguros() {
		// TODO Auto-generated method stub
		return seguroDAO.listarTodosSeguros();
	}
	/**
	 * Busca uma apólice no banco de dados com base no ID da apólice fornecido.
	 * 
	 * @param idApolice O ID da apólice.
	 * @return A apólice correspondente ao ID fornecido, ou null se não for encontrada.
	 * @throws SQLException Se ocorrer um erro ao realizar a operação no banco de dados.
	 */
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
	/**
	 * Lista todas as apólices associadas a um cliente específico com base no ID do cliente.
	 * 
	 * @param idCliente O ID do cliente.
	 * @return Uma lista de apólices associadas ao cliente.
	 * @throws SQLException Se ocorrer um erro ao realizar a operação no banco de dados.
	 */
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
