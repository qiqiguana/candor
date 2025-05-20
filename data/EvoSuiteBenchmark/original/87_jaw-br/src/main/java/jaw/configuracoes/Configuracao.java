/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaw.configuracoes;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author flavio
 */
public class Configuracao extends java.util.Properties {

    private static Configuracao instance = null;
    private String caminhoDosModelos = new String();
    private String caminhoDosProjetos = new String();
    private String caminhoDosGerados = new String();
    private List<Mapeamento> mapeamentos = new Vector();
    private String caminho = "";

   
    protected Configuracao() {
    }

    public static Configuracao getInstance() {
        if (instance == null) {
            instance = new Configuracao();
        }
        instance.carregar();
        return instance;
    }

    private void carregarCaminho() {
        setCaminho(new File("").getAbsolutePath() + "..");
        if (!new File(caminho).exists()) {
            setCaminho(System.getProperty("user.home") + "/jaw");
        }
    }

    public void carregar() {
        // se nao houver o diteorio, cria tudo
        carregarCaminho();
        try {
            java.io.FileInputStream in = new java.io.FileInputStream(new java.io.File(getCaminho() + "/conf/config"));
            this.load(in);
            if (this.getProperty("caminhoDosGerados") != null) {
                this.setCaminhoDosGerados(this.getProperty("caminhoDosGerados"));
            }
            if (this.getProperty("caminhoDosProjetos") != null) {
                this.setCaminhoDosProjetos(this.getProperty("caminhoDosProjetos"));
            }
            if (this.getProperty("caminhoDosModelos") != null) {
                this.setCaminhoDosModelos(this.getProperty("caminhoDosModelos"));
            }
        } catch (Exception e) {
            jaw.Main.janelaPrincipal.log(e.toString());
        }
    }

    public void salvar() {
        this.setProperty("caminhoDosProjetos", this.getCaminhoDosProjetos());
        this.setProperty("caminhoDosGerados", this.getCaminhoDosGerados());
        this.setProperty("caminhoDosModelos", this.getCaminhoDosModelos());

        try {
            java.io.OutputStream os = new java.io.FileOutputStream(new java.io.File(System.getProperty("user.home") + "/jaw/conf/config"));
            this.store(os, "" + new Date());
        } catch (Exception e) {
            jaw.Main.janelaPrincipal.log(e.toString());
        }
    }

    public String getCaminhoDosModelos() {
        if (!this.caminhoDosModelos.endsWith("/")) {
            this.caminhoDosModelos += "/";
        }
        return caminhoDosModelos;
    }

    public void setCaminhoDosModelos(String caminhoDosModelos) {
        this.caminhoDosModelos = caminhoDosModelos;
    }

    public String getCaminhoDosProjetos() {
        if (!this.caminhoDosProjetos.endsWith("/")) {
            this.caminhoDosProjetos += "/";
        }
        return caminhoDosProjetos;
    }

    public void setCaminhoDosProjetos(String caminhoDosProjetos) {
        this.caminhoDosProjetos = caminhoDosProjetos;
    }

    public String getCaminhoDosGerados() {
        if (!this.caminhoDosGerados.endsWith("/")) {
            this.caminhoDosGerados += "/";
        }
        return caminhoDosGerados;
    }

    public void setCaminhoDosGerados(String caminhoDosGerados) {
        this.caminhoDosGerados = caminhoDosGerados;
    }

    public List<Mapeamento> getMapeamentos() {
        return mapeamentos;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
