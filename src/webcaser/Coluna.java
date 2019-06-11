/*
 * Coluna.java
 *
 * Created on 10 de Março de 2008, 20:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webcaser;

/**
 *
 * @author 180000dn8
 */
public class Coluna {
    private String id;
    private String ColName;
    private String idDataType;
    private String dataTypeParams;
    private boolean primaryKey;
    private boolean notNull;
    private boolean autoInc;
    private boolean isForeignKey;
    private String defaultValue;
    private String label;
    private boolean habilitado;
    private boolean detalhe;
    // Quando Foreign Key
    private String tabelaOrigem;
    private String nomeCampoOrigem;
    private String nomeDetalheOrigem;
    
    
    /**
     * Creates a new instance of Coluna
     */
    public Coluna() {
        this.id = "" ;
        this.ColName = "" ;
        this.idDataType = "" ;
        this.dataTypeParams = "" ;
        this.primaryKey = false ;
        this.notNull = false ;
        this.autoInc = false ;
        this.isForeignKey = false ;
        this.defaultValue = "" ;
        this.label = "" ;
        this.habilitado = true ;
        this.tabelaOrigem = "" ;
        this.nomeCampoOrigem = "" ;
        this.nomeDetalheOrigem = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColName() {
        return ColName;
    }

    public void setColName(String ColName) {
        this.ColName = ColName;
    }

    public String getIdDataType() {
        return idDataType;
    }

    public void setIdDataType(String idDataType) {
        this.idDataType = idDataType;
    }

    public String getDataTypeParams() {
        return dataTypeParams;
    }

    public void setDataTypeParams(String dataTypeParams) {
        this.dataTypeParams = dataTypeParams;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isAutoInc() {
        return autoInc;
    }

    public void setAutoInc(boolean autoInc) {
        this.autoInc = autoInc;
    }

    public boolean isIsForeignKey() {
        return isForeignKey;
    }

    public void setIsForeignKey(boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    public String getSize() {
        String size = new String("20");
        if( this.dataTypeParams.length() > 0 ){
           size = dataTypeParams.replace("(","").replace(")","");
        }
        return size;
    }

    public String getTabelaOrigem() {
        return tabelaOrigem;
    }

    public void setTabelaOrigem(String tabelaOrigem) {
        this.tabelaOrigem = tabelaOrigem;
    }

    public String getNomeCampoOrigem() {
        return nomeCampoOrigem;
    }

    public void setNomeCampoOrigem(String nomeCampoOrigem) {
        this.nomeCampoOrigem = nomeCampoOrigem;
    }
    
    public String getDataType() {
        return this.idDataType;
    }
    public String getColType() {
        String colType = new String("");
        if ( this.idDataType.equals("5") ){
            colType = "int";
        } else if ( this.idDataType.equals("20") ){
            colType = "String";
        } else {
            colType = "String";
        } 
        return colType;
    }
    public String getColTypeResult() {
        String colType = new String("");
        if ( this.idDataType.equals("5") ){
            colType = "Int";
        } else if ( this.idDataType.equals("20") ){
            colType = "String";
        } else {
            colType = "String";
        } 
        return colType;
    }

    public String getNomeDetalheOrigem() {
        return nomeDetalheOrigem;
    }

    public void setNomeDetalheOrigem(String nomeDetalheOrigem) {
        this.nomeDetalheOrigem = nomeDetalheOrigem;
    }

    public boolean isDetalhe() {
        return detalhe;
    }

    public void setDetalhe(boolean detalhe) {
        this.detalhe = detalhe;
    }
    
}
