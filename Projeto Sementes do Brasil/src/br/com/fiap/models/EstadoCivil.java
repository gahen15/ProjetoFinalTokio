package br.com.fiap.models;

/**
 * Enum que representa o estado civil de uma pessoa.
 * 
 * Este enum é usado para categorizar o estado civil de um indivíduo, facilitando o armazenamento e a manipulação
 * dessa informação no sistema.
 * 
 * Os valores possíveis são:
 * <ul>
 *     <li>SOLTEIRO</li>
 *     <li>CASADO</li>
 *     <li>DIVORCIADO</li>
 *     <li>VIUVO</li>
 *     <li>SEPARADO_JUDICIALMENTE</li>
 *     <li>UNIAO_ESTAVEL</li>
 * </ul>
 */
public enum EstadoCivil {

    /** Representa o estado civil de uma pessoa solteira. */
    SOLTEIRO, 
    
    /** Representa o estado civil de uma pessoa casada. */
    CASADO, 
    
    /** Representa o estado civil de uma pessoa divorciada. */
    DIVORCIADO, 
    
    /** Representa o estado civil de uma pessoa viúva. */
    VIUVO, 
    
    /** Representa o estado civil de uma pessoa separada judicialmente. */
    SEPARADO_JUDICIALMENTE, 
    
    /** Representa o estado civil de uma pessoa em união estável. */
    UNIAO_ESTAVEL;
}
