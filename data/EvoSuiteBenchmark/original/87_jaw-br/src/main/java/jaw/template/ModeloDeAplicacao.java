package jaw.template;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author flavio
 */
public class ModeloDeAplicacao implements Comparable, Serializable {

    private String nome = new String();
    private String descricao = new String();
    private List<TemplateJaw> templates = new Vector();
    private String autor = new String();
    private Date data = new Date();

    public static List<ModeloDeAplicacao> listar() {
        Vector<ModeloDeAplicacao> vetor = new Vector();
        File lista[] = new File(jaw.Main.configuracao.getCaminhoDosModelos()).listFiles();
        for (int i = 0; i < lista.length; i++) {
            if (!lista[i].getName().endsWith("modelo.xml")) {
                continue;
            }
            try {
                XStream xStream = new XStream();

                xStream.alias("templateJaw", TemplateJaw.class);
                xStream.useAttributeFor(TemplateJaw.class, "nome");
                xStream.useAttributeFor(TemplateJaw.class, "destino");
                xStream.useAttributeFor(TemplateJaw.class, "nomeDoArquivo");
                xStream.useAttributeFor(TemplateJaw.class, "tipo");

                xStream.alias("modelo", ModeloDeAplicacao.class);
                xStream.useAttributeFor(ModeloDeAplicacao.class, "nome");
                xStream.useAttributeFor(ModeloDeAplicacao.class, "autor");
                xStream.useAttributeFor(ModeloDeAplicacao.class, "data");


                ModeloDeAplicacao m = (ModeloDeAplicacao) xStream.fromXML(new FileReader(lista[i]));
                vetor.add(m);
            } catch (Exception e) {
                jaw.Main.janelaPrincipal.log(e.toString());
            }
        }
        java.util.Collections.sort((List) vetor);
        return vetor;
    }

    public void remover() {
        File arquivo = new File(jaw.Main.configuracao.getCaminhoDosModelos() + this.getNome() + ".modelo.xml");
        arquivo.delete();
    }

    public void salvar() {
        try {
            java.io.FileWriter escrevedor = new java.io.FileWriter(jaw.Main.configuracao.getCaminhoDosModelos() + this.getNome() + ".modelo.xml");
            XStream xStream = new XStream();
            xStream.alias("templateJaw", TemplateJaw.class);
            xStream.useAttributeFor(TemplateJaw.class, "nome");
            xStream.useAttributeFor(TemplateJaw.class, "destino");
            xStream.useAttributeFor(TemplateJaw.class, "nomeDoArquivo");
            xStream.useAttributeFor(TemplateJaw.class, "tipo");

            xStream.alias("modelo", ModeloDeAplicacao.class);
            xStream.useAttributeFor(ModeloDeAplicacao.class, "nome");
            xStream.useAttributeFor(ModeloDeAplicacao.class, "autor");
            xStream.useAttributeFor(ModeloDeAplicacao.class, "data");
            xStream.toXML(this, escrevedor);
        } catch (Exception e) {
            jaw.Main.janelaPrincipal.log(e.toString());
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<TemplateJaw> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateJaw> templates) {
        this.templates = templates;
    }

    public int compareTo(Object o) {
        if (o instanceof ModeloDeAplicacao) {
            return this.getNome().compareTo(((ModeloDeAplicacao) o).getNome());
        } else {
            return -1;
        }
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
