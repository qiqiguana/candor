package jaw.metamodelo;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import jaw.util.Conversor;

public class Atributo implements Comparable, Serializable {

    private String nome = "";
    private String tipo = "";
    private int tamanho = 0;
    private Properties tipos = new Properties();
    private boolean chavePrimaria = false;
    private Entidade entidade = new Entidade();
    private Atributo atributoImportado = null;
    private List<Atributo> atributosExportados = new Vector();

    public Atributo() {
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public int compareTo(Object at) {
        if (at instanceof Atributo) {
            return this.nome.compareTo(((Atributo) at).getNome());
        } else {
            return -1;
        }
    }

    public Entidade getEntidadeImportada() {
        if (this.atributoImportado != null) {
            return atributoImportado.getEntidade();
        } else {
            return null;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeAsClasse() {
        return Conversor.toClasse(this.nome);
    }

    public String getNomeAsAtributo() {
        return Conversor.toAtributo(this.nome);
    }

    public String getNomeAsTabela() {
        return Conversor.toTabela(this.nome);
    }
    
    public String getNomeAsTexto() {
        return Conversor.toTexto(this.nome);
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Properties getTipos() {
        return tipos;
    }

    public void setTipos(Properties tipos) {
        this.tipos = tipos;
    }

    public boolean isChavePrimaria() {
        return chavePrimaria;
    }

    public boolean getChavePrimaria() {
        return chavePrimaria;
    }

    public void setChavePrimaria(boolean chavePrimaria) {
        this.chavePrimaria = chavePrimaria;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public Atributo getAtributoImportado() {
        return atributoImportado;
    }

    public void setAtributoImportado(Atributo atributoImportado) {
        this.atributoImportado = atributoImportado;
    }

    public List<Atributo> getAtributosExportados() {
        return atributosExportados;
    }

    public void setAtributosExportados(List<Atributo> atributosExportados) {
        this.atributosExportados = atributosExportados;
    }
}
