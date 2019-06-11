/*
 * Arquivos.java
 * Autor: Felipe Ramos
 */
package arquivos;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.w3c.dom.*;
import webcaser.*;

/**
 *
 * @author 180000dn8
 */
public class Arquivos {

    //private Element arquivoSubstituicao;
    private PrintStream fileSaida;
    private HashMap<String, Tabela> estrutura;
    private String driver;
    private String url;
    private String usuario;
    private String senha;
    private String diretorioTemplates;
    private String pacoteDao;
    private String pacoteModel;
    private String pacoteController;
    private String pacote1;
    private String pacote2;
    private String pacote3;
    private int sequencial;

    /**
     * Creates a new instance of Arquivos
     */
    public Arquivos(String diretorioTemplate, String driver, String url, String usuario, String senha, String pacoteDao, String pacoteModel, String pacoteController, String pacote1, String pacote2, String pacote3, HashMap<String, Tabela> estrutura) {
        this.driver = driver;
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
        this.pacoteDao = pacoteDao;
        this.pacoteModel = pacoteModel;
        this.pacoteController = pacoteController;
        this.diretorioTemplates = diretorioTemplate;
        this.estrutura = estrutura;
        this.pacote1 = pacote1;
        this.pacote2 = pacote2;
        this.pacote3 = pacote3;

    }

    /*
     * Redireciona o template a ser gerado para:
     * Arquivo UNICO ou um arquivo por tabela
     */
    public void criaArquivos(String diretorioDestino, Template template, String tipo) throws Exception {
        // Se for um arquivo tipo unico
        if (tipo.equalsIgnoreCase("UNICO")) {
            criaArquivos(diretorioDestino, template);
        } else {
            // Percorre todas as tabelas e cria os arquivos
            for (Iterator it2 = this.estrutura.keySet().iterator(); it2.hasNext();) {
                String keytab = (String) it2.next();
                Tabela tab = this.estrutura.get(keytab);

                // Se Tabela Marcada para ser criada 
                if (tab.isHabilitado()) {
                    // Cria todos os arquivos configurados para cada Tabela
                    try {
                        criaArquivos(diretorioDestino, template, tab);
                    } catch (Exception e) {
                        //jlMensagem.setText( "Erro na geração dos arquivos da Tabela: ".concat(tab.getTableName()) );
                        System.out.println("Erro na geração dos arquivos da tabela: ".concat(tab.getTableName()));
                    }

                }
            }

        }
    }

    /*
     * CRIA ARQUIVOS PARA CADA TABELA COM BASE NOS TEMPLATE CONFIGURADOS
     */
    public String criaArquivos(String diretorioDestino, Template template, Tabela tab) throws Exception {
        String erro = new String();

        // SOMENTE GERA EM CASO DO TEMPLATE SE UTILIZADO PARA GERACAO DE 
        // ARQUIVOS POR TABELA
        try {
            // Monta nome do arquivo conforme configurado
            String arquivoSaida = new String("");
            arquivoSaida = arquivoSaida.concat(diretorioDestino);
            arquivoSaida = arquivoSaida.concat("\\".concat(template.getNomeAntes()));
            arquivoSaida = arquivoSaida.concat(tab.getTableName());
            arquivoSaida = arquivoSaida.concat(template.getNomeDepois());
            arquivoSaida = arquivoSaida.concat(".").concat(template.getExtensao());

            // Cria novo arquivo de Saida
            FileOutputStream saida = new FileOutputStream(arquivoSaida);
            fileSaida = new PrintStream(saida);

            // Atualiza o conteudo do arquivo por Tabela
            atualizaConteudo(template.getArquivoTemplate(), tab);

            fileSaida.close();
        } catch (Exception e) {
            erro = e.getMessage();
        }

        return erro;
    }

    /* 
     * CRIA ARQUIVOS UNICOS
     */
    public String criaArquivos(String diretorioDestino, Template template) throws Exception {
        String erro = new String();

        try {
            // Monta nome do arquivo conforme configurado
            String arquivoSaida = new String("");
            arquivoSaida = arquivoSaida.concat(diretorioDestino);
            arquivoSaida = arquivoSaida.concat("\\".concat(template.getNomeAntes()));
            arquivoSaida = arquivoSaida.concat(template.getNome());
            arquivoSaida = arquivoSaida.concat(template.getNomeDepois());
            arquivoSaida = arquivoSaida.concat(".").concat(template.getExtensao());

            // Cria novo arquivo de Saida
            FileOutputStream saida = new FileOutputStream(arquivoSaida);
            fileSaida = new PrintStream(saida);
            // Atualiza arquivo 
            atualizaConteudo(template.getArquivoTemplate(), null);
            fileSaida.close();

        } catch (Exception e) {
            erro = e.getMessage();
        }


        return erro;
    }

    /**
     * atualizaArquivo - Gera conteudo do arquivo conforme dados do objeto
     * Vai gerando do meio do arquivo para as pontas
     * TABLE -> FORM -> BODY -> HTML
     */
    private void atualizaConteudo(String arquivoTemplate, Tabela tab) throws Exception {

        // LE ARQUIVO DE TEMPLATE
        ArquivoRead template = new ArquivoRead(this.diretorioTemplates.concat("\\"), arquivoTemplate);
        String linha;
        // Enquanto não for o final do arquivo
        while (!template.isEOF()) {

            // LE A PROXIMA LINHA DO ARQUIVO
            linha = template.readNextRow();

            // Se for a Atualização de arquivos de Tabela
            if (!(tab == null)) {

                /*
                 * INDICAÇÃO DE REPETIÇÃO PARA TODOS OS CAMPOS (HABILITADOS) DA TABELA
                 * OU SOMENTE IMPRESSÃO DE BLOCO PARA PK
                 */
                if (linha.contains("[REPETE_CAMPOS]") ||
                        linha.contains("[SOMENTE_STRING_OBRIGATORIOS]") ||
                        linha.contains("[SOMENTE_FK]") ||
                        linha.contains("[SOMENTE_PK]") ||
                        linha.contains("[REPETE_CAMPOS_NAO_PK]") ||
                        linha.contains("[REPETE_CAMPOS_NAO_PK_FK]")) {

                    // Cria lista para armazenas as linhas que estão dentro do bloco
                    ArrayList<String> linhas_repetidas = new ArrayList<String>();
                    // Indica se imprime PK
                    boolean imprimePk = linha.contains("[SOMENTE_PK]") ||
                            linha.contains("[REPETE_CAMPOS]");
                    // Indica se imprime campos normais
                    boolean imprimeNaoPk = linha.contains("[REPETE_CAMPOS_NAO_PK]") ||
                            linha.contains("[REPETE_CAMPOS]");
                    // Indica se imprime campos normais 
                    boolean imprimeNaoPkFk = linha.contains("[REPETE_CAMPOS_NAO_PK_FK]") ||
                            linha.contains("[REPETE_CAMPOS]");
                    // Indica que imprime FK
                    boolean imprimeFk = linha.contains("[SOMENTE_FK]") ||
                            linha.contains("[REPETE_CAMPOS]");
                    //Indica que imprime somente para campos obrigatorios
                    boolean imprimeObrigatorios = linha.contains("[SOMENTE_STRING_OBRIGATORIOS]");

                    boolean reinicializaSequencial = true;
                    // LE A PROXIMA LINHA DO ARQUIVO
                    linha = template.readNextRow();

                    // Repete enquanto não fo o final de arquivo e não for final da repetição
                    while (!template.isEOF() &&
                            !linha.contains("[/REPETE_CAMPOS]") &&
                            !linha.contains("[/SOMENTE_STRING_OBRIGATORIOS]") &&
                            !linha.contains("[/SOMENTE_PK]") &&
                            !linha.contains("[/SOMENTE_FK]") &&
                            !linha.contains("[/REPETE_CAMPOS_NAO_PK]") &&
                            !linha.contains("[/REPETE_CAMPOS_NAO_PK_FK]")) {
                        linhas_repetidas.add(linha);
                        // Verifica se reinicializa o sequencial
                        if (linha.contains("SEQUENCIAL_CONT")) {
                            reinicializaSequencial = false;
                        }
                        // LE A PROXIMA LINHA DO ARQUIVO
                        linha = template.readNextRow();
                    }

                    // Percorre todas as colunas da Tabela
                    HashMap<String, Coluna> estruturaCampos = tab.getCampos();

                    if (reinicializaSequencial) {
                        this.sequencial = 0;
                    }
                    // Validacoes de todos os campos da Tabela
                    for (Iterator it = estruturaCampos.keySet().iterator(); it.hasNext();) {
                        String key = (String) it.next();
                        Coluna col = estruturaCampos.get(key);
                        // Verifica se imprime dados para Coluna
                        boolean imprime = false;
                        // Somente imprime se PK
                        imprime = ((col.isPrimaryKey() && imprimePk) ||
                                   (col.isNotNull() && imprimeObrigatorios && col.getColTypeResult().equals("String")) ||
                                   (col.isIsForeignKey() && imprimeFk) ||
                                   (!col.isPrimaryKey() && imprimeNaoPk) ||
                                   (!col.isIsForeignKey() && !col.isPrimaryKey() && imprimeNaoPkFk));

                        // Se Coluna definida como habilitada para inclusão
                        // OU Se for somente PK e Coluna for a PK
                        if (imprime) {
                            this.sequencial++;

                            // Percorre todas as linhas a serem repetidas por Coluna
                            for (int pos = 0; pos < linhas_repetidas.size(); pos++) {

                                // Recebe a linha da Coluna
                                String linhaCol = ((String) linhas_repetidas.get(pos));

                                // SE ENCONTRAR PALAVRA RESERVADA QUE NÃO IMPRIME PARA PRIMEIRA COLUNA
                                // E FOR PRIMEIRA COLUNA
                                if (linhaCol.contains("[NAO_PRIMEIRA_COLUNA]") && this.sequencial == 1) {
                                    linhaCol = "";
                                } else {
                                    linhaCol = linhaCol.replace("[NAO_PRIMEIRA_COLUNA]", "");
                                }
                                // Imprime a linha da Coluna
                                fileSaida.println(normalizaLinha(normalizaLinha(linhaCol, col), tab));

                            }
                        }
                    }

                } else {
                    // IMPRIME LINHA NORMAL COM DADOS DE TABELA
                    fileSaida.println(normalizaLinha(linha, tab));

                }
            } else {
                if (linha.contains("[REPETE_TABELAS]")) {

                    // Cria lista para armazenas as linhas que estão dentro do bloco
                    ArrayList<String> linhas_repetidas = new ArrayList<String>();
                    // LE A PROXIMA LINHA DO ARQUIVO
                    linha = template.readNextRow();
                    // Repete enquanto não fo o final de arquivo e não for final da repetição
                    while (!template.isEOF() &&
                            !linha.contains("[/REPETE_TABELAS]")) {
                        linhas_repetidas.add(linha);
                        // LE A PROXIMA LINHA DO ARQUIVO
                        linha = template.readNextRow();
                    }

                    // Validacoes de todos os campos da Tabela
                    for (Iterator it = this.estrutura.keySet().iterator(); it.hasNext();) {
                        String key = (String) it.next();
                        Tabela tabela = estrutura.get(key);

                        // Se Tabela habilitada
                        if (tabela.isHabilitado()) {

                            // Percorre todas as linhas a serem repetidas por Coluna
                            for (int pos = 0; pos < linhas_repetidas.size(); pos++) {

                                // Recebe a linha da Coluna
                                String linhaCol = ((String) linhas_repetidas.get(pos));
                                // Imprime a linha da Coluna
                                fileSaida.println(normalizaLinha(linhaCol, tabela));

                            }
                        }
                    }

                } else {
                    // IMPRIME LINHA NORMAL COM DADOS DE TABELA
                    fileSaida.println(normalizaLinha(linha));
                }
            }

        }

    }

    /*
     * Atualiza linhas normais
     */
    private String normalizaLinha(String linha) {
        String retorno = linha;
        // SUBSTITUI PALAVRAS RESERVADAS PELOS DADOS EXTRAIDOS DO MODELO ER
        retorno = retorno.replace("DRIVER_DB", this.driver);
        retorno = retorno.replace("URL_DB", this.url);
        retorno = retorno.replace("USUARIO_DB", this.usuario);
        retorno = retorno.replace("SENHA_DB", this.senha);
        retorno = retorno.replace("PACOTE_DAO", this.pacoteDao);
        retorno = retorno.replace("PACOTE_MODEL", this.pacoteModel);
        retorno = retorno.replace("PACOTE_CONTROLLER", this.pacoteController);
        retorno = retorno.replace("PACOTE_GERAL_1", this.pacote1);
        retorno = retorno.replace("PACOTE_GERAL_2", this.pacote2);
        retorno = retorno.replace("PACOTE_GERAL_3", this.pacote3);

        retorno = retorno.replace("SEQUENCIAL_INI", (new Integer(this.sequencial).toString()));
        retorno = retorno.replace("SEQUENCIAL_CONT", (new Integer(this.sequencial).toString()));

        return retorno;
    }

    /*
     * Atualiza linha de Tabela 
     */
    private String normalizaLinha(String linha, Tabela tab) {
        String retorno = normalizaLinha(linha);
        // SUBSTITUI PALAVRAS RESERVADAS PELOS DADOS EXTRAIDOS DO MODELO ER
        retorno = retorno.replace("NOME_TABELA", tab.getTableName());
        retorno = retorno.replace("LABEL_TABELA", tab.getLabel());
        return retorno;
    }

    /*
     * Atualiza linha com dados de Coluna
     */
    private String normalizaLinha(String linha, Coluna col) {
        String retorno = normalizaLinha(linha);
        // Se tamanho do campo for maior que 50, fixa em 50
        String sizeCol = col.getSize();
        if ( ( new Integer( col.getSize() ) ) > 50 ){
            sizeCol = "50";
        }
        // SUBSTITUI PALAVRAS RESERVADAS PELOS DADOS EXTRAIDOS DO MODELO ER
        retorno = retorno.replace("NOME_COLUNA", col.getColName());
        retorno = retorno.replace("LABEL_COLUNA", col.getLabel());
        retorno = retorno.replace("TAMANHO_COLUNA", sizeCol );
        retorno = retorno.replace("TAMANHO_MAX_COLUNA", col.getSize());
        retorno = retorno.replace("TIPO_COLUNA", col.getColType());
        retorno = retorno.replace("TIPO_RESULT_COLUNA", col.getColTypeResult());
        retorno = retorno.replace("DEFAULT_COLUNA", col.getDefaultValue());
        retorno = retorno.replace("NOME_TABELA_RELATION", col.getTabelaOrigem());
        retorno = retorno.replace("COLUNA_ORIGEM", col.getNomeCampoOrigem());
        retorno = retorno.replace("COLUNA_DETALHE_ORIGEM", col.getNomeDetalheOrigem());
        // Conversor de valor
        if (retorno.contains("CONVERSOR_VALOR")) {
            // Conversor para Int
            if (col.getIdDataType().equals("5")) {
                retorno = retorno.replace("CONVERSOR_VALOR", "Integer.valueOf");
            } else {
                retorno = retorno.replace("CONVERSOR_VALOR", "");
            }
        }
        // Inicializa "coluna" 
        if (retorno.contains("INICIALIZA_COLUNA")) {
            // Conversor para Int
            if (col.getIdDataType().equals("5")) {
                retorno = retorno.replace("INICIALIZA_COLUNA", "0");
            } else {
                retorno = retorno.replace("INICIALIZA_COLUNA", "\"\"");
            }
        }

        
        return retorno;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
