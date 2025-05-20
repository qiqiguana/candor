/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaw.configuracoes;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import jaw.entrada.Serializacao;

/**
 *
 * @author flavio
 */
public class ConexaoBD implements Serializable, Comparable {

    private String nome = new String();
    private String usuario = new String();
    private String senha = new String();
    private String url = new String();
    private String driver = new String();
    private String base = new String();
    private static List<ConexaoBD> lista = new Vector();
    private static String caminho = jaw.Main.configuracao.getCaminho() + "/conf/conexaoBD.ser.jaw";

    @Override
    public String toString(){
        return this.nome;
    }
    
    public static List<ConexaoBD> listar() {
        try {
            Serializacao ser = new Serializacao();
            lista = ser.abrirConexoes(caminho);
        } catch (Exception ex) {
            jaw.Main.janelaPrincipal.log("Erro ao listar conexões BD " + ex.toString());
        }
        Collections.sort(lista);
        return lista;
    }

    public static void adicionar(ConexaoBD conexao) {
        if (lista.contains(conexao)) {
            return;
        }
        lista.add(conexao);
        ConexaoBD.salvar(lista);
    }

    public void adicionar() {
        ConexaoBD.adicionar(this);
    }

    public static void remover(ConexaoBD conexao) {
        lista.remove(conexao);
        ConexaoBD.salvar(lista);
    }

    public void remover() {
        ConexaoBD.remover(this);
    }

    private static void salvar(List<ConexaoBD> lista) {
        try {
            Serializacao ser = new Serializacao();
            ser.salvarConexoes(lista, caminho);
        } catch (Exception ex) {
            jaw.Main.janelaPrincipal.log(ex.toString());
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int compareTo(Object o) {
        return ((ConexaoBD) o).getNome().compareTo(this.getNome());
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
