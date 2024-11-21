package br.com.fiap.teste;


import java.sql.SQLException;

import br.com.fiap.controller.AppController;
import br.com.fiap.models.TipoSeguro;



public class Teste {
public static void main(String[] args) throws SQLException {
	AppController app = AppController.getInstance();
	 
	//ClienteDAO clienteDao;
	//Cliente Gabriel = new PessoaFisica("Jucenil", "gahen.2007@gmail.com", "119395481946","Rua123", "11231452628", EstadoCivil.DIVORCIADO,"Nomesla");
	//app.deletarCliente(3);
	//app.inserirCliente(Gabriel);
	//Seguro seguro = new SeguroEmpresaEscolar();
	TipoSeguro tipoSeguro = app.buscarTipoSeguroId(3);
	app.associarSeguroAoCliente(2, tipoSeguro );
	//app.exibirClientes();
	//app.listarTipoSeguro(5);
	//app.listarSegurosPorCliente(3);
	
}

	

	
	
	
	
	
	
}