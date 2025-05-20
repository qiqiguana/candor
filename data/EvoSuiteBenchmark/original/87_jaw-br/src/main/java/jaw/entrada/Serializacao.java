package jaw.entrada;

/**
 *
 * @author robson
 */
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Vector;
import jaw.configuracoes.ConexaoBD;
import jaw.metamodelo.Entidade;

public class Serializacao {

    private FileInputStream fis = null;
    private ObjectInputStream in = null;
    public FileOutputStream fos = null;
    public ObjectOutputStream out = null;

    public boolean salvar(List<jaw.metamodelo.Entidade> entidades, String arquivo)
            throws Exception {
        fos = new FileOutputStream(arquivo);
        out = new ObjectOutputStream(fos);
        out.writeObject(entidades);
        out.close();
        return true;
    }

    public boolean salvarConexoes(List<ConexaoBD> entidades, String arquivo)
            throws Exception {
        fos = new FileOutputStream(arquivo);
        out = new ObjectOutputStream(fos);
        out.writeObject(entidades);
        out.close();
        return true;
    }

    public List<jaw.metamodelo.Entidade> abrir(String caminho)
            throws Exception {
        List<Entidade> entidades = new Vector();
        fis = new FileInputStream(caminho);
        in = new ObjectInputStream(fis);
        entidades = (Vector<Entidade>) in.readObject();
        in.close();
        return entidades;
    }

    public List<ConexaoBD> abrirConexoes(String caminho)
            throws Exception {
        List<ConexaoBD> entidades = new Vector();
        fis = new FileInputStream(caminho);
        in = new ObjectInputStream(fis);
        entidades = (Vector<ConexaoBD>) in.readObject();
        in.close();
        return entidades;
    }
}
