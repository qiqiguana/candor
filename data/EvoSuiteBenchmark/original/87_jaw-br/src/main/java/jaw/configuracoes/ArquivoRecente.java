/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaw.configuracoes;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author flavio
 */
public class ArquivoRecente {

    private static Properties arquivos = new Properties();

    public static List<String> getArquivos() {
        Vector<String> lista = new Vector();
        String caminho = jaw.Main.configuracao.getCaminho();
        arquivos = new Properties();
        File arquivo = new java.io.File(caminho + "/conf/arquivosRecentes.properties");
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException ex) {
                jaw.Main.janelaPrincipal.log(ex.toString());
            }
        }
        try {
            java.io.FileInputStream in = new java.io.FileInputStream(arquivo);
            arquivos.load(in);
        } catch (Exception e) {
            jaw.Main.janelaPrincipal.log(e.toString());
        }
        Enumeration nomes = arquivos.propertyNames();
        while (nomes.hasMoreElements()) {
            lista.add(nomes.nextElement().toString());
        }
        return lista;
    }

    public static void adicionarArquivo(String arquivo) {
        arquivos.setProperty(arquivo, new Date().toString());
        salvarArquivos();
    }

    public static void salvarArquivos() {
        String caminho = jaw.Main.configuracao.getCaminho();
        File arquivo = new java.io.File(caminho + "/conf/arquivosRecentes.properties");
        try {
            arquivo.createNewFile();
            java.io.OutputStream os = new java.io.FileOutputStream(arquivo);
            arquivos.store(os, "" + new Date());
        } catch (Exception e) {
            jaw.Main.janelaPrincipal.log(e.toString());
        }
    }

    public static void limpar() {
        arquivos.clear();
    }
}
