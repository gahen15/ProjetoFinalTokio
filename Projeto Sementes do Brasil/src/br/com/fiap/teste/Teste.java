package br.com.fiap.teste;


import java.sql.SQLException;

import br.com.fiap.controller.AppController;
import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.models.Cliente;
import br.com.fiap.models.EstadoCivil;
import br.com.fiap.models.submodel.Empresa;


public class Teste {
public static void main(String[] args) throws SQLException {
	AppController app = AppController.getInstance();
	 
	ClienteDAO clienteDao;
	Cliente Gabriel = new Empresa("NOMESLA", "gahen.2007@gmail.com", "119395481946","Rua123", "11231452627", "NomeSLA","Nomesla");
	//app.deletarCliente(3);
	app.inserirCliente(Gabriel);
	
	
	app.exibirClientes();
}

	

	
	
	
	
	
	
}
