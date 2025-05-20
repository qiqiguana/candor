/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaw.template;

import java.io.Serializable;
import jaw.util.Conversor;
import jaw.metamodelo.Entidade;

/**
 *
 * @author flavio
 */
public class TemplateJaw implements Comparable, Serializable {

    private String nome;
    private String velocityTemplate;
    private String destino;
    private String nomeDoArquivo;
    private String descricao;
    private int tipo = 0;
    public static final int INDIVIDUAL = 0;
    public static final int CONJUNTA = 1;
    
    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVelocityTemplate() {
        return this.velocityTemplate;
    }

    public void setVelocityTemplate(String velocityTemplate) {
        this.velocityTemplate = velocityTemplate;
    }

    public String getDestino() {
        return destino.replaceAll("\\$gerado\\$", jaw.Main.configuracao.getCaminhoDosGerados());
    }

    public String getDestino(Entidade entidade) {
        return getDestino().replaceAll("\\$Entidade\\$", Conversor.toClasse(entidade.getNome())).replaceAll("\\$entidade\\$", Conversor.toAtributo(entidade.getNome())).replaceAll("\\$pacote\\$", Conversor.toAtributo(entidade.getPacote()).replaceAll("\\.", "/"));
    }

    public void setDestino(String destino) {
        this.destino = destino.replaceAll(jaw.Main.configuracao.getCaminhoDosGerados(), "\\$gerado\\$");
    }

    public String getNomeDoArquivo() {
        return nomeDoArquivo;
    }

    public String getNomeDoArquivo(Entidade entidade) {
        return nomeDoArquivo.replaceAll("\\$Entidade\\$", Conversor.toClasse(entidade.getNome())).replaceAll("\\$entidade\\$", Conversor.toAtributo(entidade.getNome()));
    }

    public void setNomeDoArquivo(String nomeDoArquivo) {
        this.nomeDoArquivo = nomeDoArquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int compareTo(Object at) {
        if (at instanceof TemplateJaw) {
            return this.getNome().compareTo(((TemplateJaw) at).getNome());
        } else {
            return -1;
        }
    }
}
