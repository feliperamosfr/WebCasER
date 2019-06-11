/*
 * Main.java
 *
 * Created on 6 de Março de 2008, 20:49
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */ 

package webcaser;

import janelas.principal;

/**
 * 
 * @author Felipe Ramos
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // TODO code application logic here
        // webCaseReader arquivo = new webCaseReader( "U:\\Exemplo.xml" );
        
        // Lê informações do XML, extraindo dados de tabelas e seus campos
        //arquivo.readAll();
        
        principal tela = new principal();
        //tela.Executar();

    }
    
}
