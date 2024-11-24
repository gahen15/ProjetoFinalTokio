package br.com.fiap.teste;

import java.sql.SQLException;

import br.com.fiap.controller.AppController;
import br.com.fiap.models.Apolice;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.TipoSeguro;

/**
 * Classe de teste que simula a execução de operações relacionadas a apólices de seguro.
 * 
 * A classe contém um exemplo de uso do controlador da aplicação (AppController) para listar as apólices de um cliente específico.
 */
public class Teste {
    
    /**
     * Método principal da classe Teste. Realiza as operações de teste para a aplicação.
     * 
     * @param args Argumentos de linha de comando (não utilizados neste caso).
     * @throws SQLException Caso ocorra um erro durante a interação com o banco de dados.
     */
    public static void main(String[] args) throws SQLException {
        
        // Instanciando o controlador da aplicação
        AppController app = AppController.getInstance();
        
        // Buscando e listando as apólices de um cliente pelo seu ID
        // O método listarApolicesDoCliente recebe um ID de cliente e exibe as apólices associadas a esse cliente.
        app.listarApolicesDoCliente(1);
    }
}
