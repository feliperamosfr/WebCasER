/*
 * To change this Template, choose Tools | Templates
 * and open the Template in the editor.
 */
package webcaser;

/**
 *
 * @author felipe_ramos
 */
public class Template {

    private String nome;
    private String arquivoTemplate;
    private boolean habilitado;
    private String tipo;
    private String nomeAntes;
    private String nomeDepois;
    private String extensao;


    public Template() {
    }

//    public Template( String nome, String arquivoTemplate, String arquivoSubstituicao, String tipo, String nomeAntes, String nomeDepois, String extensao) {
    public Template( String nome, String arquivoTemplate, String tipo, String nomeAntes, String nomeDepois, String extensao, boolean habilitado ) {
       this.nome = nome;
       this.arquivoTemplate = arquivoTemplate;
       this.tipo = tipo;
       this.nomeAntes = nomeAntes;
       this.nomeDepois = nomeDepois;
       this.extensao = extensao;
       this.habilitado = habilitado;
    }

    public String getArquivoTemplate() {
        return arquivoTemplate;
    }

    public void setArquivoTemplate(String arquivoTemplate) {
        this.arquivoTemplate = arquivoTemplate;
    }

/*    public String getArquivoSubstituicao() {
        return arquivoSubstituicao;
    }

    public void setArquivoSubstituicao(String arquivoSubstituicao) {
        this.arquivoSubstituicao = arquivoSubstituicao;
    }
*/
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeAntes() {
        return nomeAntes;
    }

    public void setNomeAntes(String nomeAntes) {
        this.nomeAntes = nomeAntes;
    }

    public String getNomeDepois() {
        return nomeDepois;
    }

    public void setNomeDepois(String nomeDepois) {
        this.nomeDepois = nomeDepois;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }



}
