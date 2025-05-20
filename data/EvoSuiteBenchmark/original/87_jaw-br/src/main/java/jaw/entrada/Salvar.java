package jaw.entrada;

import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JOptionPane;
import jaw.metamodelo.Entidade;

public class Salvar {

    private boolean vela = false;
    private String arquivo = "";
    public String xml = "";
    public java.util.List<Entidade> entidade = new java.util.Vector();
    public static final int SALVAR_XML = 10;
    public static final int SALVAR = 20;
    public static final int SALVAR_COMO = 30;

    public Salvar(jaw.gui.ProcessarEntidades form, int tipoSalvar) {
        try {
        this.entidade = form.getEntidades();
        } catch (Exception ex) {
            jaw.Main.janelaPrincipal.log(ex.toString());
            return;
        }
        JFileChooser escolhedor = new JFileChooser();
        escolhedor.setCurrentDirectory(new File(jaw.Main.configuracao.getCaminhoDosProjetos()));
        if (tipoSalvar == Salvar.SALVAR_XML) {
            escolhedor.setFileFilter(new Filtro2());
        }
        if (tipoSalvar == Salvar.SALVAR || tipoSalvar == Salvar.SALVAR_COMO) {
            escolhedor.setFileFilter(new Filtro());
        }
        if (tipoSalvar == Salvar.SALVAR_XML || tipoSalvar == Salvar.SALVAR_COMO || (tipoSalvar == Salvar.SALVAR && form.getNomeArquivoSalvo().equals("Novo Projeto"))) {
            escolhedor.showSaveDialog(form);
            vela = true;
        }

        if (escolhedor.getSelectedFile() == null && form.getName().equals("")) {
            return;
        } else {
            if (vela) {
                if (escolhedor.getSelectedFile() == null) {
                    return;
                }
                arquivo = escolhedor.getSelectedFile().getAbsolutePath();
            } else {
                arquivo = form.getNomeArquivoSalvo();
            }
            if (tipoSalvar == Salvar.SALVAR || tipoSalvar == Salvar.SALVAR_COMO) {
                if (!arquivo.endsWith(".jaw") && vela) {
                    arquivo += ".jaw";
                    form.setNomeArquivoSalvo(arquivo);
                    vela = false;
                } else {
                    arquivo = form.getNomeArquivoSalvo();
                }
                System.out.println(form.getNomeArquivoSalvo());
                Serializacao salvar = new Serializacao();
                try {
                    if (salvar.salvar(this.entidade, arquivo)) {
                        javax.swing.JOptionPane.showMessageDialog(form, "Arquivo salvo com sucesso!", "Jaw - Salvar", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Não foi possível salvar esse arquivo\n Verifique o formato das entidades !", "Salvando Arquivo", JOptionPane.ERROR_MESSAGE);
                    jaw.Main.janelaPrincipal.log(ex.toString());
                }

            }

            if (tipoSalvar == Salvar.SALVAR_XML) {
                if (!arquivo.endsWith(".jaw.xml")) {
                    arquivo += ".jaw.xml";
                }
                jaw.entrada.SerializacaoXML serializaXML = new jaw.entrada.SerializacaoXML();
                this.xml = serializaXML.entidades2XML(this.entidade);
                try {
                    java.io.FileWriter escrevedor = new java.io.FileWriter(arquivo);
                    escrevedor.write(xml);
                    escrevedor.flush();
                    escrevedor.close();
                    javax.swing.JOptionPane.showMessageDialog(form, "Arquivo Exportado com sucesso!", "Jaw - Salvar", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(form, "Não foi possível salvar esse arquivo\n Verifique o formato das entidades !", "Salvando Arquivo", JOptionPane.ERROR_MESSAGE);
                    jaw.Main.janelaPrincipal.log(e.toString());
                }
            }
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
