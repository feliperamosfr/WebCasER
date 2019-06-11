/*
 * XmlReader.java
 *
 * Created on 6 de Março de 2008, 20:51
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package webcaser;
import org.w3c.dom.*;
import java.util.ArrayList;

/**
 * @author Felipe Ramos
 */
public abstract class XmlReader {
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Efetua a leitura do conteudo de elementos das tags
     * Pode ser passada uma estrutura de Hierarquica de tags para serem lidas conforme exemplo:
     * <SubTag1><SubTag2>
     * Quando for buscar informações dentro de subtags, pegará a "n" ocorrência encontrada
     * aonde "n" é passado por parâmetro
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static String readTag( Element tag, String tagName, String attribute ) throws Exception {
        String contentTag = new String();
        contentTag = "";
        
        // Se for um atributo da tag
        if ( tagName.length() == 0 ){
            contentTag = tag.getAttribute( attribute );
            
        } else {
            
            String tagPart = new String();
            // Identifica o nome da tag
            tagPart = tagName.substring( 4, tagName.indexOf(">") ) ; //tagPart = tagName.substring( 1, tagName.indexOf(">") ) ;
            int ocorrencia = new Integer( tagName.substring( 2, tagName.indexOf(")") ) );
            ocorrencia = ocorrencia - 1;
            //
            tagName = tagName.substring( tagName.indexOf(">")+1 , tagName.length() ); //tagName = tagName.replace( "<".concat(tagPart).concat(">") ,"");
            //
            NodeList nl = tag.getElementsByTagName( tagPart ); 
            
            tag = (Element) nl.item( ocorrencia );    //tag = (Element) nl.item( 0 );
            
            contentTag = readTag( tag, tagName, attribute );
            
        }
        
        return contentTag;
    }
  
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Efetua a leitura dos nodos
     * Element elem -> Elemnto a ser usado para leitura de seus nodos
     * ArrayList tags -> Array com lista de nodos a serem lidos em forma crescente, seguindo
     *                   a estrutura do XML
     * int posicao -> posicao do nodo a ser lido pois um elemento pode aparecer mais do que
     *                uma vez dentro de um nodo. No caso do DBDesigner, iremos ler apenas a
     *                posicao 0 (primeira ocorrencia)
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR 
    // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR 
    // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR // EXCLUIR 
    public static NodeList readNode( Element elem, ArrayList tags, int posicao ) throws Exception {
        NodeList nlReturn;
        
        nlReturn = elem.getElementsByTagName( ( (String)tags.get(0)) );

        // Se apenas um item, retorna ele direto
        if ( tags.size() > 0 ){
           // Percorre a "arvore" solicitada para retornar ultimo node
           for( int pos = 1; pos < tags.size() ; pos++ ) {
               Element tagBusiness = (Element) nlReturn.item(posicao);
               nlReturn = tagBusiness.getElementsByTagName( ((String)tags.get(pos)) );
            }
        }
        
        return nlReturn;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Efetua a leitura dos nodos
     * Element elem -> Elemnto a ser usado para leitura de seus nodos
     * String tag -> Nome da tag a ser localizada. Retorna no elemento todas as tags encontradas, 
                     mesmo que estas possam estar dentro de outras tags
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static NodeList readNode( Element elem, String tag ) throws Exception {
        NodeList nlReturn;
        nlReturn = elem.getElementsByTagName( tag );
        return nlReturn;
    }
    
}
