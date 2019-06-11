/*
 * WebCaseReader.java
 *
 * Created on 6 de Mar�o de 2008, 20:58
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package webcaser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 *
 * @author felipe
 */
public class WebCaseReader {
    
    // caminho (path) do arquivo XML
    private String xmlPathname;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;
    private Element elem;
    
    private String version;
    private HashMap<String,Tabela> estrutura;
    private ArrayList validacoes;
    
    /**
     * Creates a new instance of WebCaseReader
     */
    public WebCaseReader() {
        // Inicia hash de estrutura e atributo de validade
        this.estrutura = new HashMap<String,Tabela>();
//        this.relacionamentos = new HashMap<String,Relacionamento>();
        this.validacoes = new ArrayList();
        this.version = "";
        this.validacoes = new ArrayList();
    }
    
    /**
     * Objeto construtor onde ir� criar um novo arquivo com toda estrutura do XML
     * Ser� passado por par�metro o path do arquivo
     */
    public WebCaseReader( String path ) {
        // Inicia hash de estrutura e atributo de validade
        this.estrutura = new HashMap<String,Tabela>();
//        this.relacionamentos = new HashMap<String,Relacionamento>();
        this.validacoes = new ArrayList();
        this.version = "";

        // Inicia path+arquivo
        this.xmlPathname = path;
        
        // tenta abrir arquivo passado
        try{
            this.dbf = DocumentBuilderFactory.newInstance();
            this.db =  this.dbf.newDocumentBuilder();
            //Este documento possuir� toda a estrutura do arquivo
            this.doc = this.db.parse( this.xmlPathname );
            // Pegamos o this.elemento principal do documento, e depois explodimos em uma lista de n�s
            this.elem = this.doc.getDocumentElement();

            
            
        	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
        	NodeList nList = doc.getElementsByTagName("template");
        			
        	System.out.println("----------------------------");

        	for (int temp = 0; temp < nList.getLength(); temp++) {

        		Node nNode = nList.item(temp);
        				
        		System.out.println("\nCurrent Element :" + nNode.getNodeName());
        				
        		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

        			Element eElement = (Element) nNode;

        			System.out.println("nome : " + eElement.getAttribute("nome"));
        			System.out.println("descricao : " + eElement.getAttribute("descricao"));

        		}
        	}
             
            
            
            
            
            
            
            // Cria uma lista para armazenar as informa��es das pastas
            //this.setListaBusinessFolder(new HashMap<String, BusinessFolder>());
            
        } catch( Exception e ) {
            System.out.println("Problemas na abertura do arquivo!");
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * L� as informa��es sobre as tabelas e campos
     */
    public void readAll( ) {
        try{
            // L� dados do XML
            this.readVersion();
            // L� dados das tabelas
            this.readTabelas();
            // L� dados das tabelas
            this.readRelacionamentos();

        } catch ( Exception e ){
            System.out.println( e.getMessage() );
        }
    }

    /**
     * L� dados espec�ficos do XML
     **/
    private void readVersion() throws Exception{
        setVersion( XmlReader.readTag( this.elem, "", "version" ) );
    }
    

    
    /**
     * L� as informa��es das tabelas e seus campos
     */
    private void readRelacionamentos( ) throws Exception {

        // L� o Nodo de Relacionamentos (retornar� 1 elemento para cada Tabela)
        NodeList nlRelacionamentos = XmlReader.readNode( this.elem, "RELATION" );
        // Percorre todas as tabelas dentro do nodo
        for( int pos = 0; pos < nlRelacionamentos.getLength(); pos++ ) {
            
            // L� cada Tabela
            Element tagrel = (Element) nlRelacionamentos.item(pos);
            String idTabelaOrigem = XmlReader.readTag( tagrel, "", "SrcTable" );
            String colunas = XmlReader.readTag( tagrel, "", "FKFields" );
            String colunaOrigem = colunas.substring(0, colunas.indexOf("="));
            String colunaDestino = colunas.substring(colunas.indexOf("=")+1, colunas.indexOf("\\") );
            String idTabelaDestino = XmlReader.readTag( tagrel, "", "DestTable" );
            
            // Adiciona na coluna FK o campo Origem e o nome da tabela Origem
            this.getEstrutura( ).get( idTabelaDestino ).getCampos( colunaDestino ).setNomeCampoOrigem( colunaOrigem );
            this.getEstrutura( ).get( idTabelaDestino ).getCampos( colunaDestino ).setNomeDetalheOrigem( colunaOrigem );
            this.getEstrutura( ).get( idTabelaDestino ).getCampos( colunaDestino ).setTabelaOrigem(  
                                        this.getEstrutura( ).get( idTabelaOrigem ).getTableName() );
            
        }
    }

    
    /**
     * L� as informa��es das tabelas e seus campos
     */
    private void readTabelas( ) throws Exception {
        Tabela table;
        
        // L� o Nodo de tabelas (retornar� 1 elemento para cada Tabela)
        NodeList nlTabelas = XmlReader.readNode( this.elem, "TABLE" );
        // Percorre todas as tabelas dentro do nodo
        for( int pos = 0; pos < nlTabelas.getLength(); pos++ ) {
            // Inst�ncia um objeto de Tabela para ser adicionado no array da estrutura
            table = new Tabela();
            
            // L� cada Tabela
            Element tagtab = (Element) nlTabelas.item(pos);
            table.setId( XmlReader.readTag( tagtab, "", "ID" ) );
            table.setTableName( XmlReader.readTag( tagtab, "", "Tablename" ) );
            table.setLabel( XmlReader.readTag( tagtab, "", "Comments" ) );
            
            // L� o Nodo de Colunas da Tabela (retornar� 1 elemento para cada Coluna)
            NodeList nlColunas = XmlReader.readNode( tagtab, "COLUMN" );
            // Chama m�todo para ler colunas a partir do nodo de colunas
            // Insere no objeto de Tabela o HashMap j� pronto contendo objeto de Coluna
            table.setCampos( readColunas( nlColunas ) );
            
            // Adiciona na lista da estrutura a Tabela
            this.getEstrutura().put( table.getId(), table );
            
        }
    }
    
    /**
     * Le as colunas de cada Tabela
     **/
    private HashMap readColunas( NodeList nlColunas ) throws Exception {
        HashMap<String,Coluna> campos = new HashMap<String,Coluna>();
        
        // Percorre todas as tabelas dentro do nodo
        for( int poscol = 0; poscol < nlColunas.getLength(); poscol++ ) {
            
            // L� cada Coluna
            Element tagcol = (Element) nlColunas.item(poscol);
            
            // Insere nos atributos do objeto os dados de cada tag
            // inst�ncia um objeto de Coluna para ser adicionado na lista de colunas da Tabela
            Coluna field = new Coluna();
            field.setId(XmlReader.readTag( tagcol, "", "ID" ));
            field.setColName(XmlReader.readTag( tagcol, "", "ColName" ));
            field.setIdDataType(XmlReader.readTag( tagcol, "", "idDatatype" ));
            field.setDataTypeParams(XmlReader.readTag( tagcol, "", "DatatypeParams" ));
            field.setPrimaryKey(XmlReader.readTag( tagcol, "", "PrimaryKey" ).equalsIgnoreCase("1") );
            field.setNotNull(XmlReader.readTag( tagcol, "", "NotNull" ).equalsIgnoreCase("1") );
            field.setAutoInc(XmlReader.readTag( tagcol, "", "AutoInc" ).equalsIgnoreCase("1") );
            field.setIsForeignKey(XmlReader.readTag( tagcol, "", "IsForeignKey" ).equalsIgnoreCase("1") );
            field.setDefaultValue(XmlReader.readTag( tagcol, "", "DefaultValue" ));
            field.setLabel(XmlReader.readTag( tagcol, "", "Comments" ));
            field.setDetalhe( field.isPrimaryKey() );
            // Adiciona no HasMap cada Coluna lida
            campos.put( field.getColName(), field);
        }
        return campos;
    }
    
    
    /**
     *
     **/
    public void validaXml(){
        
        // Valida vers�o
//        if ( ! this.version.equals( "4.0") ) {
//            setErro( "Vers�o inv�lida. XML deve possuir vers�o igual a 4.0");
//        }
        
        // Validacoes de Tabela
        for (Iterator it = getEstrutura().keySet().iterator(); it.hasNext();) {   
            String key = (String)it.next();   
            Tabela item = getEstrutura().get(key);  
            // Chama m�todo para validar tabelas
            validaDadosTabelas( item );
        }     

    }

    /**
     * Valida dados de cada Tabela
     */
    private void validaDadosTabelas( Tabela tab ){
        
        // Verifica se informado campo de comments usado como label
        if ( tab.getLabel().trim().equals("") ){
            setErro( "" );
            setErro( "Tabela ".concat( tab.getTableName() ).concat( " n�o possui informa��o de 'comments' informado." )  );
        }
        
        HashMap<String,Coluna> estruturaCampos = tab.getCampos();
        // Validacoes de todos os campos da Tabela
        for (Iterator it = estruturaCampos.keySet().iterator(); it.hasNext();) {   
            String key = (String)it.next();   
            Coluna col = estruturaCampos.get(key);   
            validaDadosCampos( tab, col );
        }     

    }
    
    /**
     * Valida dados de cada Tabela
     */
    private void validaDadosCampos( Tabela tab, Coluna col ){
        
        if ( col.getLabel().trim().equals("") ){
            setErro( "Tabela ".concat( tab.getTableName() ).concat( ", Coluna " ).concat( col.getColName() ).concat( " n�o possui informa��o de 'comments' informado." )  );
        }
        
        // FALTA FAZER MAIS VALIDACOES ESPECIFICAS

    }

    
    /**
     * Insere erro em lista de erros
     */
    private void setErro( String erro ){
        getValidacoes().add( erro.concat("\n") );
    }
    
    private void setVersion( String version ){
        this.version = version;
    }

    /**
     * Retorna ArrayList de erros/inconsist�ncias do XML
     */
    public ArrayList getValidacoes() {
        return validacoes;
    }

    public HashMap<String, Tabela> getEstrutura() {
        return this.estrutura;
    }

}
