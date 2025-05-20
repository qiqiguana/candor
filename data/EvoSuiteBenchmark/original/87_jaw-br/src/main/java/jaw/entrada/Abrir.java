package jaw.entrada;

import java.io.FileReader;
import java.io.Reader;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JOptionPane;

public class Abrir {

    public String xml = "";
    public jaw.gui.ProcessarEntidades form;
    public static final int ABRIR_XML = 10;
    public static final int ABRIR = 20;

    public Abrir(jaw.gui.ProcessarEntidades form, String arquivo) {
        this.form = form;
        if (arquivo.endsWith(".jaw.xml")) {
            abrirXML(arquivo);
        } else {
            abrirSerializado(arquivo);
        }
    }

    public Abrir(jaw.gui.ProcessarEntidades form, int tipoAbrir) {
        this.form = form;
        JFileChooser escolhedor = new JFileChooser();
        escolhedor.setCurrentDirectory(new File(jaw.Main.configuracao.getCaminhoDosProjetos()));

        if (tipoAbrir == this.ABRIR_XML) {
            escolhedor.setFileFilter(new Filtro2());
        }
        if (tipoAbrir == this.ABRIR) {
            escolhedor.setFileFilter(new Filtro());
        }
        escolhedor.showOpenDialog(null);

        if (escolhedor.getSelectedFile() == null) {
            return;
        } else {

            String arquivo = escolhedor.getSelectedFile().getAbsolutePath();
            if (tipoAbrir == this.ABRIR) {
                abrirSerializado(arquivo);

            }

            if (tipoAbrir == this.ABRIR_XML) {
                abrirXML(arquivo);
            }
        }
    }

    private void abrirSerializado(String arquivo) {
        if (arquivo.endsWith(".jaw")) {
            try {
                form.setNomeArquivoSalvo(arquivo);
                this.form.setEntidades(new Serializacao().abrir(arquivo));
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Arquivo jaw corrompido ou incompatível com aplicação!",
                        "Abrir Arquivo jaw", JOptionPane.ERROR_MESSAGE);
                jaw.Main.janelaPrincipal.log(e.toString());
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Arquivo jaw corrompido ou incompatível com aplicação!",
                    "Abrir Arquivo jaw", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirXML(String arquivo) {
        if (arquivo.endsWith(".jaw.xml")) {
            try {
                form.setNomeArquivoSalvo(arquivo);
                Reader leitor = new FileReader(arquivo);
                this.form.setEntidades(new SerializacaoXML().xml2Entidades(leitor));
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Arquivo xml corrompido ou incompatível com aplicação!",
                        "Importação XML", JOptionPane.ERROR_MESSAGE);
                jaw.Main.janelaPrincipal.log(e.toString());
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Arquivo xml corrompido ou incompatível com aplicação!",
                    "Importação XML", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class Filtro2 extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {

            return f.isDirectory() || f.getName().toLowerCase().endsWith(".jaw.xml");
        }

        public String getDescription() {
            return "XML JAW files";
        }
    }

    private class Filtro extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".jaw");
        }

        public String getDescription() {
            return "JAW files";
        }
    }
}
