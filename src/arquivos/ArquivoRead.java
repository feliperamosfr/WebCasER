/*
 * ArquivoRead.java
 *
 * Created on 7 de Maio de 2008, 20:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package arquivos;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author 180000dn8
 */
public class ArquivoRead {
    private BufferedReader arquivo;
    private String erro;
    private String caminho;
    private String nomeArquivo;
    
    public ArquivoRead(){
        
    }
    /**
     * Creates a new instance of ArquivoRead
     */
    public ArquivoRead( String caminho, String nomeArquivo ) {
        this.caminho = caminho;
        this.nomeArquivo = nomeArquivo;
        
        try {
            // Cria novo arquivo
            this.arquivo = new BufferedReader(new FileReader(this.caminho.concat(this.nomeArquivo)));

        } catch (Exception e) {
            this.erro = e.getMessage();
        }
    }
    
    public String readNextRow( ){
        String linha = new String("EOF");
        try{
            if( this.arquivo.ready() ){
                linha = this.arquivo.readLine();
            }
        } catch (Exception e) {
            this.erro = e.getMessage();
        }
        return linha;
    }
    
    public boolean isEOF(){
        boolean isEof = false;
        try{
            isEof = ! this.arquivo.ready();
        }catch (Exception e) {
            this.erro = e.getMessage();
        }
        return ( isEof  );
    }
    
}
