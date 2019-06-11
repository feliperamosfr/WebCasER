/*
 * FileWrite.java
 *
 * Created on 7 de Maio de 2008, 20:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package arquivos;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 *
 * @author 180000dn8
 */
public class FileWrite {
    private PrintStream arquivo;
    private String erro;
    private String nomeArquivo;
    
    /**
     * Creates a new instance of ArquivoRead
     */
    public FileWrite( String nomeArquivo ) {
        this.nomeArquivo = nomeArquivo;
        
        try {
            // Cria novo arquivo
            this.arquivo = new PrintStream( new FileOutputStream( this.nomeArquivo ) );

        } catch (Exception e) {
            this.erro = e.getMessage();
        }
    }
    
    public String writeRow( String linha ){
        this.arquivo.println(linha);
        return linha;
    }

    public void closeFile(){
        this.arquivo.close();
    }
}
