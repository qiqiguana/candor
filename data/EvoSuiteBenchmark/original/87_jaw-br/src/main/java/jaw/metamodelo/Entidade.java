package jaw.metamodelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jaw.util.Conversor;

public class Entidade implements Comparable, Serializable {

    private String nome = "";
    private java.util.List<Atributo> atributos = new java.util.Vector();
    private String pacote = "";
    private List<Atributo> chavesPrimarias = new ArrayList();
    
    public Entidade(String nome) {
        this.nome = nome;
    }

    public Entidade() {
    }

    public int compareTo(Object ent) {
        if (ent instanceof Entidade) {
            String comp = ((Entidade) ent).getPacote() + "." + ent.toString();
            return (this.pacote + "." + this.nome).compareTo(comp);
        } else {
            return this.nome.compareTo(ent.toString());
        }
    }

    public String getNome() {
        return this.nome;
    }

    public String getNomeAsClasse() {
        return Conversor.toClasse(this.nome);
    }

    public String getNomeAsTabela() {
        return Conversor.toTabela(this.nome);
    }

    public String getNomeAsAtributo() {
        return Conversor.toAtributo(this.nome);
    }

    public String getNomeAsTexto() {
        return Conversor.toTexto(this.nome);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public java.util.List<Atributo> getAtributos() {
        return this.atributos;
    }

    public void setAtributos(java.util.List<Atributo> atributos) {
        this.atributos = atributos;
    }

    public String toString() {
        return this.nome;
    }

    public String getPacote() {
        return pacote;
    }

    public void setPacote(String pacote) {
        this.pacote = pacote;
    }

    public List<Atributo> getChavesPrimarias() {
        return chavesPrimarias;
    }

    public void setChavesPrimarias(List<Atributo> chavesPrimarias) {
        this.chavesPrimarias = chavesPrimarias;
    }
}
