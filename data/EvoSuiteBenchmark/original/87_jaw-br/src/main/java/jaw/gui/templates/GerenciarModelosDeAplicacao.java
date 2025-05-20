/*
 * GerenciarModelosDeAplicacao.java
 *
 * Created on 11 de Maio de 2008, 22:44
 */
package jaw.gui.templates;

import java.util.List;
import javax.swing.JOptionPane;
import jaw.template.ModeloDeAplicacao;

/**
 *
 * @author  flavio
 */
public class GerenciarModelosDeAplicacao extends javax.swing.JDialog {

    List<ModeloDeAplicacao> modelos;

    public GerenciarModelosDeAplicacao(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        atualizar();
    }

    private void atualizar() {
        modelos = ModeloDeAplicacao.listar();
        listarModelos();
    }

    private ModeloDeAplicacao getModeloSelecionado() {
        int colunaSelecionada = jTable1.getSelectedRow();
        ModeloDeAplicacao modelo = modelos.get(colunaSelecionada);
        return modelo;
    }

    private void listarModelos() {
        //listar
        ((javax.swing.table.DefaultTableModel) jTable1.getModel()).setRowCount(0);
        for (ModeloDeAplicacao modelo : modelos) {
            ((javax.swing.table.DefaultTableModel) jTable1.getModel()).addRow(new Object[]{modelo.getNome(), modelo.getDescricao()});
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        novo = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        editar = new javax.swing.JButton();
        remover = new javax.swing.JButton();
        baixar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(jTable1);

        novo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/general/New16.gif"))); // NOI18N
        novo.setText("Novo");
        novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novoActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/general/Stop16.gif"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/general/Open16.gif"))); // NOI18N
        editar.setText("Editar");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });

        remover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/general/Delete16.gif"))); // NOI18N
        remover.setText("Remover");
        remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerActionPerformed(evt);
            }
        });

        baixar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/general/Import16.gif"))); // NOI18N
        baixar.setText("baixar");
        baixar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baixarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(novo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remover, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(baixar, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(novo)
                    .addComponent(editar)
                    .addComponent(remover)
                    .addComponent(baixar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novoActionPerformed
        //Novo
        new CriarModeloDeAplicacao().setVisible(true);
        atualizar();
}//GEN-LAST:event_novoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Cancelar
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void removerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerActionPerformed
        //Remover
        if (JOptionPane.showConfirmDialog(this, "Deseja remover?") != JOptionPane.OK_OPTION) {
            return;
        }
        this.getModeloSelecionado().remover();
        this.atualizar();
    }//GEN-LAST:event_removerActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        // Editar
        new CriarModeloDeAplicacao(this.getModeloSelecionado()).setVisible(true);
        this.atualizar();
    }//GEN-LAST:event_editarActionPerformed

    private void baixarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baixarActionPerformed
        //baixar
        try {
            jaw.web.Update.getModelos();
        } catch (Exception e) {
            jaw.Main.janelaPrincipal.log(e.toString());
        }
        this.atualizar();
}//GEN-LAST:event_baixarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton baixar;
    private javax.swing.JButton editar;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton novo;
    private javax.swing.JButton remover;
    // End of variables declaration//GEN-END:variables
}
