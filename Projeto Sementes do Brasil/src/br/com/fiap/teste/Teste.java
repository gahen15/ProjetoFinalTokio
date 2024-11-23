package br.com.fiap.teste;

import java.sql.SQLException;

import br.com.fiap.controller.AppController;
import br.com.fiap.models.Apolice;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.TipoSeguro;

public class Teste {
    public static void main(String[] args) throws SQLException {
        // Instanciando o controlador da aplicação
        AppController app = AppController.getInstance();
        
        // Buscando um TipoSeguro pelo ID

        try {
            // Criando um cliente fictício para teste
            Cliente cliente = app.buscarClientePorId(1);
            // ID do cliente existente no banco de dados

            // Criando um tipo de seguro fictício para teste
            TipoSeguro tipoSeguro =app.buscarTipoSeguroId(1); // ID do tipo de seguro existente no banco de dados

            // Criando uma apólice para teste de inserção
            Apolice novaApolice = new Apolice(cliente, tipoSeguro, 500.00);
            novaApolice.setStatus("Ativa");

            // Testando a inserção da apólice
            System.out.println("Inserindo uma nova apólice...");
            app.inserirApolice(novaApolice);
            System.out.println("Apólice inserida com sucesso! ID: " + novaApolice.getId());

            // Testando a exclusão da apólice
            System.out.println("Excluindo a apólice...");
            //app.excluirApolice(novaApolice);
            System.out.println("Apólice excluída com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao executar teste: " + e.getMessage());
            e.printStackTrace();
        }
    }


    }

