package br.com.fiap.interfacegrafica;

import java.util.HashMap;
import java.util.Map;

public class Usuarios {

	Map<String, String> usuario = new HashMap<String, String>();

	public Usuarios() {
		usuario.put("Admin".toUpperCase(), "admin123");
		usuario.put("Gahen".toUpperCase(), "gahen123");
		usuario.put("Icaro".toUpperCase(), "marino123");
		usuario.put("Jeff".toUpperCase(), "palmeiras");
		usuario.put("Arcangel".toUpperCase(), "fiap123");
		usuario.put("Duda".toUpperCase(), "mariajoaquina");
		usuario.put("Carlos".toUpperCase(), "seilamano");
		// usuario.put("Admin", "admin123");

	}

	 public boolean validarUsuario(String usuario, String senha) {
	        // Verifica se o mapa contém o usuário e a senha associada
	        String senhaAssociada = this.usuario.get(usuario.trim().toUpperCase()); // Obtém a senha associada ao usuário

	        // Verifica se a senha fornecida é igual à senha armazenada
	        return senhaAssociada != null && senhaAssociada.equals(senha);
	    }

}
