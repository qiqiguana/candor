package jaw;

import jaw.configuracoes.Configuracao;
import jaw.configuracoes.Mapeamento;
import jaw.gui.JanelaPrincipal;

public class Main {

    public static Configuracao configuracao;
    public static jaw.gui.JanelaPrincipal janelaPrincipal = null;

    public Main() {
        // Carrega os caminhos
        configuracao = Configuracao.getInstance();
        // Carrega os mapeamentos
        Mapeamento.carregarMapeamentos();
        janelaPrincipal = JanelaPrincipal.getInstance();
        janelaPrincipal.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception ex) {
            Main.janelaPrincipal.log(ex.toString());
        }
    }
}
