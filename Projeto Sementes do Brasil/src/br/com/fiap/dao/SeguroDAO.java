package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.models.implement.Seguro;

public class SeguroDAO {

    private Connection connection;

    public SeguroDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserirTipoSeguro(Seguro seguro) {
        String sql = "INSERT INTO TipoSeguro (descricao, categoria) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, seguro.getDescricao());  
            stmt.setString(2, seguro.getCategoria()); 

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tipo de seguro", e);
        }
    }


    public void associarSeguroAoCliente(long idCliente, Seguro seguro) {
        String sql = "INSERT INTO ClienteSeguro (idCliente, idTipoSeguro) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);
            stmt.setLong(2, seguro.getIdTipoSeguro());  

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao associar seguro ao cliente", e);
        }
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
    }

  
    public void removerSeguroDeCliente(long idCliente, Seguro seguro) {
        String sql = "DELETE FROM ClienteSeguro WHERE idCliente = ? AND idTipoSeguro = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);
            stmt.setLong(2, seguro.getIdTipoSeguro());  

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover seguro de cliente", e);
        }
    }
}
