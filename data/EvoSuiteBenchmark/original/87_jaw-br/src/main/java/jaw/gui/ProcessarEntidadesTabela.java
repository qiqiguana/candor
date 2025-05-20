/*
 * ProcessarEntidadesTabela.java
 *
 * Created on 13 de Maio de 2007, 12:59
 */
package jaw.gui;

import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import jaw.metamodelo.Atributo;
import jaw.metamodelo.Entidade;

/**
 *
 * @author  robson
 */
public class ProcessarEntidadesTabela extends ProcessarEntidades {

    /** Creates new form ProcessarEntidadesTabela */
    private List<Entidade> entidades = new Vector();
    private Atributo atributo = new Atributo();
    private int entidadeSelecionada = -1;
    private int atributoSelecionado = -1;
    private String nomeArquivoSalvo = "Novo Projeto";

    public ProcessarEntidadesTabela() {
        this.setLocation(420, 200);
        initComponents();
    }

    public List<Entidade> selecionarEntidades() {
        int[] linhas = jTable1.getSelectedRows();
        Vector<Entidade> lista = new Vector();
        for (int i = 0; i < linhas.length; i++) {
            lista.add((Entidade) jTable1.getValueAt(linhas[i], 0));
        }
        return lista;
    }

    public void removerEntidades() {
        int[] linhas = jTable1.getSelectedRows();
        for (int i = 0; i < linhas.length; i++) {
            this.getEntidades().remove(linhas[i]);
        }
        this.atualizarEntidades();
    }

    public void carregarEntidades(List<Entidade> entidades) {
        this.setEntidades(entidades);
        ((javax.swing.table.DefaultTableModel) jTable1.getModel()).setRowCount(0);
        java.util.Collections.sort(this.getEntidades());
        for (int i = 0; i < entidades.size(); i++) {
            ((javax.swing.table.DefaultTableModel) jTable1.getModel()).addRow(new Object[]{this.getEntidades().get(i), this.getEntidades().get(i).getPacote()});
        }
    }

    public void atualizarEntidades() {
        ((javax.swing.table.DefaultTableModel) jTable1.getModel()).setRowCount(0);
        java.util.Collections.sort(this.getEntidades());
        for (int i = 0; i < this.getEntidades().size(); i++) {
            ((javax.swing.table.DefaultTableModel) jTable1.getModel()).addRow(new Object[]{this.getEntidades().get(i), this.getEntidades().get(i).getPacote()});
        }

    }

    public void listarAtributos() {
        ((javax.swing.table.DefaultTableModel) jTable2.getModel()).setRowCount(0);
        int linhaSelecionada = this.jTable1.getSelectedRow();
        java.util.Collections.sort(this.getEntidades().get(linhaSelecionada).getAtributos());
        for (int i = 0; i < this.getEntidades().get(linhaSelecionada).getAtributos().size(); i++) {
            ((javax.swing.table.DefaultTableModel) jTable2.getModel()).addRow(new Object[]{this.getEntidades().get(linhaSelecionada).getAtributos().get(i).getNome(), this.getEntidades().get(linhaSelecionada).getAtributos().get(i).getTipo(), Boolean.FALSE});
        }

    }

    public void resetAtributos() {
        ((javax.swing.table.DefaultTableModel) jTable2.getModel()).setRowCount(0);

    }

    private void popularEntidades() {
        if (jTable1.getSelectedRow() == -1) {
            javax.swing.JOptionPane.showMessageDialog(null, "Por Favor Selecione Uma Entidade!", "Jaw - Entidades", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.listarAtributos();
    }

    public List<Entidade> getEntidades() {
        return this.entidades;
    }

    public void setEntidades(List<Entidade> entidades) {
        this.entidades = entidades;
        this.atualizarEntidades();
    }

    public int getEntidadeSelecionada() {
        return entidadeSelecionada;
    }

    public void setEntidadeSelecionada(int entidadeSelecionada) {
        this.entidadeSelecionada = entidadeSelecionada;
    }

    public int getAtributoSelecionado() {
        return atributoSelecionado;
    }

    public void setAtributoSelecionado(int atributoSelecionado) {
        this.atributoSelecionado = atributoSelecionado;
    }

    public String getNomeArquivoSalvo() {
        return nomeArquivoSalvo;
    }

    public void setNomeArquivoSalvo(String nomeArquivoSalvo) {
        this.nomeArquivoSalvo = nomeArquivoSalvo;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.entidades.get(this.entidadeSelecionada).getAtributos().add(atributo);
        this.listarAtributos();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setName("FF"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entidades", "Pacote"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);

        jSeparator2.setPreferredSize(new java.awt.Dimension(100, 5));
        jPanel1.add(jSeparator2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Tipo", "Selecione"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Extração dos Dados");
        jPanel2.add(jLabel1, new java.awt.GridBagConstraints());

        add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel4.setPreferredSize(new java.awt.Dimension(2, 100));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );

        add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel5.setPreferredSize(new java.awt.Dimension(2, 100));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );

        add(jPanel5, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        this.setEntidadeSelecionada(this.jTable1.getSelectedRow());
        if (evt.getClickCount() == 2) {
            this.popularEntidades();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        
        
    }//GEN-LAST:event_formComponentShown
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
