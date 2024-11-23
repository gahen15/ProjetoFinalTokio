package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.models.Apolice;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.TipoSeguro;


public class SeguroDAO {

    private Connection connection;

    public SeguroDAO(Connection connection) {
        this.connection = connection;
    }
 
 // Método para inserir uma apólice e associá-la ao cliente
    public void inserir(Apolice apolice) throws SQLException {
        String sqlApolice = "INSERT INTO T_Apolice (idCliente, idTipoSeguro, dataEmissao, valor, status) VALUES (?, ?, ?, ?, ?)";
        String sqlClienteApolice = "INSERT INTO ClienteApolice (idCliente, idApolice, dataInicio) VALUES (?, ?, ?)";
        
        try (
             PreparedStatement stmtApolice = connection.prepareStatement(sqlApolice, new String[]{"id"});
             PreparedStatement stmtClienteApolice = connection.prepareStatement(sqlClienteApolice)) {

            connection.setAutoCommit(false); // Iniciar transação

            // Inserir apólice
            stmtApolice.setLong(1, apolice.getCliente().getIdCliente());
            stmtApolice.setLong(2, apolice.getTipoSeguro().getIdTipoSeguro());
            stmtApolice.setDate(3, new Date(apolice.getDataEmissao().getTime()));
            stmtApolice.setDouble(4, apolice.getValor());
            stmtApolice.setString(5, apolice.getStatus());
            stmtApolice.executeUpdate();

            // Obter o ID gerado da apólice
            var rs = stmtApolice.getGeneratedKeys();
            if (rs.next()) {
                long apoliceId = rs.getLong(1);
                apolice.setId(apoliceId);

                // Inserir associação na tabela ClienteApolice
                stmtClienteApolice.setLong(1, apolice.getCliente().getIdCliente());
                stmtClienteApolice.setLong(2, apoliceId);
                stmtClienteApolice.setDate(3, new Date(System.currentTimeMillis()));
                stmtClienteApolice.executeUpdate();
            }

            connection.commit(); // Confirmar transação
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir apólice: " + e.getMessage(), e);
        }
    }

    // Método para excluir uma apólice
    public void excluir(long idApolice) throws SQLException {
        String sqlClienteApolice = "DELETE FROM ClienteApolice WHERE idApolice = ?";
        String sqlApolice = "DELETE FROM T_Apolice WHERE id = ?";

        try (PreparedStatement stmtClienteApolice = connection.prepareStatement(sqlClienteApolice);
             PreparedStatement stmtApolice = connection.prepareStatement(sqlApolice)) {

            connection.setAutoCommit(false); // Iniciar transação

            // Remover da tabela ClienteApolice
            stmtClienteApolice.setLong(1, idApolice);
            stmtClienteApolice.executeUpdate();

            // Remover da tabela T_Apolice
            stmtApolice.setLong(1, idApolice);
            stmtApolice.executeUpdate();

            connection.commit(); // Confirmar transação
        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir apólice: " + e.getMessage(), e);
        }
    }

    public List<TipoSeguro> listarTodosSeguros() {
        String sql = "SELECT idTipoSeguro, descricao, categoria, valor FROM TipoSeguro";  // Atualizada a consulta para incluir o valor

        List<TipoSeguro> listaSeguros = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long idTipoSeguro = rs.getLong("idTipoSeguro");  // Usando long para o id
                String descricao = rs.getString("descricao");
                String categoria = rs.getString("categoria");
                double valor = rs.getDouble("valor");  // Recuperando o valor do banco de dados

                TipoSeguro tipoSeguro = new TipoSeguro(idTipoSeguro, descricao, categoria, valor);  // Passando o valor ao criar o objeto
                listaSeguros.add(tipoSeguro);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os seguros", e);
        }

        return listaSeguros;
    }


    

    public void associarSeguroAoCliente(long idCliente, TipoSeguro seguro) {
        String sql = "INSERT INTO ClienteSeguro (idCliente, idTipoSeguro) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);
            stmt.setLong(2, seguro.getIdTipoSeguro());  // Aqui você usa o método do TipoSeguro, não do Seguro

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao associar seguro ao cliente", e);
        }
    }


    // Método para buscar um tipo de seguro pelo ID
    public TipoSeguro buscarTipoSeguroPorId(Long idTipoSeguro) throws SQLException {
        TipoSeguro tipoSeguro = null;
        String query = "SELECT idTipoSeguro, descricao, categoria, valor FROM TipoSeguro WHERE idTipoSeguro = ?";  // Atualizada para incluir o valor
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, idTipoSeguro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String descricao = rs.getString("descricao");
                    String categoria = rs.getString("categoria");
                    double valor = rs.getDouble("valor");  // Recuperando o valor
                    
                    tipoSeguro = new TipoSeguro(idTipoSeguro, descricao, categoria, valor);  // Passando o valor ao criar o objeto
                }
            }
        }
        
        return tipoSeguro;  // Retorna o tipo de seguro ou null se não encontrado
    }


    public void listarSegurosPorCliente(long idCliente) {
        String sql = "SELECT ts.idTipoSeguro, ts.descricao, ts.categoria " +
                     "FROM TipoSeguro ts " +
                     "INNER JOIN ClienteSeguro cs ON ts.idTipoSeguro = cs.idTipoSeguro " +
                     "WHERE cs.idCliente = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idTipoSeguro = rs.getInt("idTipoSeguro");
                    String descricao = rs.getString("descricao");
                    String categoria = rs.getString("categoria");

                  
                    System.out.println("ID Tipo Seguro: " + idTipoSeguro);
                    System.out.println("Descrição: " + descricao);
                    System.out.println("Categoria: " + categoria);
                    System.out.println("----------------------------");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar seguros por cliente", e);
        }
    }

   
    public void listarClientesPorTipoSeguro(long idTipoSeguro) {
        String sql = "SELECT c.idCliente, c.nome, c.email " +
                     "FROM T_Cliente c " +
                     "INNER JOIN ClienteSeguro cs ON c.idCliente = cs.idCliente " +
                     "WHERE cs.idTipoSeguro = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idTipoSeguro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long idCliente = rs.getLong("idCliente");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");

                    System.out.println("ID Cliente: " + idCliente);
                    System.out.println("Nome: " + nome);
                    System.out.println("Email: " + email);
                    System.out.println("----------------------------");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes por tipo de seguro", e);
        }
    } // Método para inserir uma apólice
 

    public void alterarStatusApolice(long idApolice, long idCliente, long idTipoSeguro, String novoStatus) throws SQLException {
        String query = "UPDATE T_Apolice SET status = ? " +
                       "WHERE id = ? AND idCliente = ? AND idTipoSeguro = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, novoStatus);
            stmt.setLong(2, idApolice);
            stmt.setLong(3, idCliente);  // Certificando que estamos alterando a apólice do cliente correto
            stmt.setLong(4, idTipoSeguro);  // Certificando que estamos alterando a apólice do tipo de seguro correto

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Nenhuma apólice encontrada com os parâmetros fornecidos.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar o status da apólice: " + e.getMessage());
        }
    }


   
}
  
   