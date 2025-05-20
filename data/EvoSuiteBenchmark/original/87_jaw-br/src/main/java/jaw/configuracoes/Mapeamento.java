/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaw.configuracoes;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jaw.metamodelo.Atributo;
import jaw.metamodelo.Entidade;

/**
 *
 * @author flavio
 */
public class Mapeamento {

    private String nome = "";
    private Properties mapeamento = new Properties();
    private static final TreeSet<String> chaves = new TreeSet();

    public Mapeamento() {
    }

    public String toString() {
        return this.nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Properties getMapeamento() {
        return mapeamento;
    }

    public void setMapeamento(Properties mapeamento) {
        this.mapeamento = mapeamento;
    }

    public static TreeSet<String> getChaves() {
        return chaves;
    }

    public void salvar() {
        salvar(this);
    }

    public void remover() {
        File arquivo = new File(jaw.Main.configuracao.getCaminho() + "/mapeamento/" + this.getNome() + ".properties");
        arquivo.delete();
    }

    public static void carregarMapeamentos() {
        jaw.Main.configuracao.getMapeamentos().clear();
        File[] arquivosDeMapeamento = new File(jaw.Main.configuracao.getCaminho() + "/mapeamento").listFiles();
        for (int i = 0; i < arquivosDeMapeamento.length; i++) {
            try {
                Properties propriedades = new Properties();
                java.io.FileInputStream in = null;
                in = new java.io.FileInputStream(arquivosDeMapeamento[i]);
                propriedades.load(in);
                Mapeamento mapeamento = new Mapeamento();
                mapeamento.setMapeamento(propriedades);
                mapeamento.setNome(arquivosDeMapeamento[i].getName().replace(".properties", ""));
                jaw.Main.configuracao.getMapeamentos().add(mapeamento);
            } catch (IOException ex) {
                jaw.Main.janelaPrincipal.log(ex.toString());
            }
        }
        carregarChaves();
    }

    public static void salvar(List<Mapeamento> mapeamentos) {
        for (Mapeamento mapeamento : mapeamentos) {
            salvarIndividual(mapeamento);
        }
    }

    private static void salvarIndividual(Mapeamento mapeamento) {
        java.io.OutputStream os = null;
        try {
            File arquivo = new File(jaw.Main.configuracao.getCaminho() + "/mapeamento/" + mapeamento.getNome() + ".properties");
            os = new java.io.FileOutputStream(arquivo);
            mapeamento.getMapeamento().store(os, new java.util.Date() + "");
        } catch (IOException ex) {
            jaw.Main.janelaPrincipal.log(ex.toString());
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                jaw.Main.janelaPrincipal.log(ex.toString());
            }
        }

    }

    public static void salvar(Mapeamento mapeamento) {
        salvarIndividual(mapeamento);
        carregarMapeamentos();
    }

    public static boolean removerChave(String chave) {
        System.out.println(chaves.size());
        chaves.remove(chave);
        System.out.println(chaves.size());

        List<Mapeamento> lista = new Vector();
        for (Mapeamento mapeamento : jaw.Main.configuracao.getMapeamentos()) {
            mapeamento.getMapeamento().remove(chave);
            lista.add(mapeamento);
        }
        salvar(lista);
        return true;
    }

    public static boolean adicionarChave(String chave) {
        boolean alteracao = false;
        chaves.add(chave);
        List<Mapeamento> lista = new Vector();
        for (Mapeamento mapeamento : jaw.Main.configuracao.getMapeamentos()) {
            if (!mapeamento.getMapeamento().containsKey(chave)) {
                mapeamento.getMapeamento().setProperty(chave, chave);
                alteracao = true;
            } else {
                alteracao = false;
            }

            if (alteracao) {
                lista.add(mapeamento);
            }
        }
        salvar(lista);
        return alteracao;
    }

    public static boolean adicionarChaves(Collection<String> chaves) {
        boolean alteracao = false;
        for (String chave : chaves) {
            alteracao = alteracao && adicionarChave(chave);
        }
        return alteracao;
    }

    public static Mapeamento createMapeamento(String nome) {
        Mapeamento mapeamento = new Mapeamento();
        mapeamento.setNome(nome);
        Properties propriedades = mapeamento.getMapeamento();
        java.util.Enumeration lista = propriedades.propertyNames();
        while (lista.hasMoreElements()) {
            String prop = lista.nextElement().toString();
            propriedades.setProperty(prop, "");
        }

        return mapeamento;
    }

    public static void mapear(Atributo atributo) {
        String chave = atributo.getTipo();
        Properties propriedades = atributo.getTipos();
        for (Mapeamento mapeamento : jaw.Main.configuracao.getMapeamentos()) {
            String valor = (mapeamento.getMapeamento().getProperty(chave) == null) ? chave : mapeamento.getMapeamento().getProperty(chave);
            propriedades.setProperty(mapeamento.getNome(), valor);
        }

        atributo.setTipos(propriedades);
    }

    public static void mapear(Entidade entidade) {
        for (Atributo atributo : entidade.getAtributos()) {
            mapear(atributo);
        }

    }

    public static void mapear(Collection<Entidade> entidades) {
        for (Entidade entidade : entidades) {
            mapear(entidade);
        }

    }

    public static void carregarChaves() {
        for (Mapeamento mapeamento : jaw.Main.configuracao.getMapeamentos()) {
            java.util.Enumeration lista = mapeamento.getMapeamento().propertyNames();
            while (lista.hasMoreElements()) {
                chaves.add(lista.nextElement().toString());
            }

        }
        normalizarMapeamentos();
    }

    public static void normalizarMapeamentos() {
        boolean alteracao = false;
        for (Mapeamento mapeamento : jaw.Main.configuracao.getMapeamentos()) {
            Properties propriedades = mapeamento.getMapeamento();
            for (String chave : chaves) {
                if (!propriedades.containsKey(chave)) {
                    propriedades.setProperty(chave, chave);
                    alteracao =
                            true;
                }

            }
            if (alteracao) {
                salvar(mapeamento);
                break;
            }
        }
    }
}
