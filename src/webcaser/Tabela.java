/*
 * Tabela.java
 *
 * Created on 6 de Março de 2008, 21:05
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package webcaser;

//import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author felipe
 */
public class Tabela {
    
    private String id;
    private String tableName;
    private String label;
    private HashMap<String,Coluna> campos;
    private boolean habilitado;
    
    /** Creates a new instance of tabelas */
    public Tabela() {
        this.id = "";
        this.tableName = "";
        this.label = "";
        campos = new HashMap<String,Coluna>();
        this.habilitado = true;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public HashMap getCampos() {
        return campos;
    }
    public Coluna getCampos( String index ) {
        return campos.get(index);
    }
    public void setCampos(HashMap campos) {
        this.campos = campos;
    }
    public void setCampos( String index, Coluna col) {
        this.campos.put(index, col);
    }

    
    public boolean isHabilitado() {
        return habilitado;
    }
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
}
