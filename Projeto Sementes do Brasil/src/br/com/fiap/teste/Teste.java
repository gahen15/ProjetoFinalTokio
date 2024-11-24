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
app.listarApolicesDoCliente(1);
    }

    }

