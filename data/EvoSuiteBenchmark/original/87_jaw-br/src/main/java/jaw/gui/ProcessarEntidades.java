/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaw.gui;

import java.util.List;
import jaw.metamodelo.Atributo;
import jaw.metamodelo.Entidade;

/**
 *
 * @author flavio
 */
public abstract class ProcessarEntidades extends javax.swing.JPanel{

    public abstract List<Entidade> selecionarEntidades();
    public abstract void removerEntidades();
    public abstract void carregarEntidades(List<jaw.metamodelo.Entidade> entidades);
    public abstract void atualizarEntidades();
    public abstract void listarAtributos();
    public abstract void resetAtributos();
    public abstract List<Entidade> getEntidades();
    public abstract void setEntidades(List<Entidade> entidades);
    public abstract int getEntidadeSelecionada();
    public abstract void setEntidadeSelecionada(int entidadeSelecionada);
    public abstract int getAtributoSelecionado();
    public abstract void setAtributoSelecionado(int atributoSelecionado);
    public abstract String getNomeArquivoSalvo();
    public abstract void setNomeArquivoSalvo(String nomeArquivoSalvo);
    public abstract Atributo getAtributo();
    public abstract void setAtributo(Atributo atributo);
}
