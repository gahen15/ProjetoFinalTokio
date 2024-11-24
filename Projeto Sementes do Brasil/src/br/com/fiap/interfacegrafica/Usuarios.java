package br.com.fiap.interfacegrafica;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por gerenciar os usuários do sistema. Contém um mapa com
 * os nomes de usuário e suas respectivas senhas, além de um método para validar
 * as credenciais fornecidas.
 */
public class Usuarios {

    // Mapa que armazena o nome de usuário (em letras maiúsculas) como chave e a senha como valor.
    private Map<String, String> usuario = new HashMap<String, String>();

    /**
     * Construtor da classe. Inicializa o mapa com alguns usuários e suas senhas.
     * Os nomes de usuário são armazenados em letras maiúsculas.
     */
    public Usuarios() {
        usuario.put("Admin".toUpperCase(), "admin123");
        usuario.put("Gahen".toUpperCase(), "gahen123");
        usuario.put("Icaro".toUpperCase(), "marino123");
        usuario.put("Jeff".toUpperCase(), "palmeiras");
        usuario.put("Arcangel".toUpperCase(), "fiap123");
        usuario.put("Duda".toUpperCase(), "mariajoaquina");
        usuario.put("Carlos".toUpperCase(), "seilamano");
        // Comentado: usuario.put("Admin", "admin123");
    }

    /**
     * Valida as credenciais fornecidas para o login do usuário.
     * 
     * @param usuario O nome de usuário fornecido.
     * @param senha A senha fornecida.
     * @return Verdadeiro se o nome de usuário e a senha forem válidos, falso caso contrário.
     */
    public boolean validarUsuario(String usuario, String senha) {
        // Verifica se o mapa contém o usuário e a senha associada
        String senhaAssociada = this.usuario.get(usuario.trim().toUpperCase()); // Obtém a senha associada ao usuário

        // Verifica se a senha fornecida é igual à senha armazenada
        return senhaAssociada != null && senhaAssociada.equals(senha);
    }
}
