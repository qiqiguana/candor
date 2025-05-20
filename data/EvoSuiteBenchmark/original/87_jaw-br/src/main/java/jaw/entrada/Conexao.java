package jaw.entrada;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import jaw.configuracoes.Mapeamento;
import jaw.metamodelo.Atributo;
import jaw.metamodelo.Entidade;

public class Conexao {

    private Connection dbConn = null;
    private String driver = "";
    private String url = "";
    private String banco = "";
    private String usuario = "";
    private String senha = "";
    private boolean alteracao;
    private Collection<String> tiposDeAtributos = new HashSet();
    private List<Entidade> entidades = new Vector();

    public Conexao(String driver, String url, String banco, String usuario, String senha) {
        this.driver = driver;
        this.url = url;
        this.banco = banco;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Conexao() {
    }

    public void output(String mensagem) {
        final String msg = mensagem;
        Thread t = new Thread() {

            @Override
            public void run() {
                jaw.Main.janelaPrincipal.log(msg);
                System.out.println(msg);
            }
        };
        t.start();
    }

    public void buscarChavesPrimarias() {
        output("Buscando chaves primárias...");
        for (Entidade entidade : entidades) {
            try {
                ResultSet rr = dbConn.getMetaData().getPrimaryKeys(null, null, entidade.getNome());
                while (rr.next()) {
                    /* 1 = Catalogo
                     * 2 = Esquema
                     * 3 = Tabela
                     * 4 = Chave Primaria
                     * 5 = indice
                     * 6 = Nome da chave primaria
                     */
                    String nomeDaChavePrimaria = rr.getString(4);
                    for (Atributo atributo : entidade.getAtributos()) {
                        if (atributo.getNome().equals(nomeDaChavePrimaria)) {
                            atributo.setChavePrimaria(true);
                            entidade.getChavesPrimarias().add(atributo);
                        }
                    }
                }
            } catch (SQLException ex) {
                jaw.Main.janelaPrincipal.log(ex.toString());
            }
        }
    }

    public void buscarChavesImportadas() {
        this.output("Buscando Chaves importadas...");
        Atributo atributo = new Atributo();

        for (Entidade entidade : entidades) {
            try {
                ResultSet rr = dbConn.getMetaData().getImportedKeys(null, null, entidade.getNome());
                while (rr.next()) {

                    // Primeiramente, encontrar o atributo que possui a referencia
                    String nomeDoAtributoQueReferencia = rr.getString(8);
                    for (Atributo attr : entidade.getAtributos()) {
                        if (nomeDoAtributoQueReferencia.equals(attr.getNome())) {
                            atributo = attr;
                        }
                    }

                    // Agora encontrar a entidade referenciada e seu atributo referenciado
                    String nomeDaEntidadeReferenciada = rr.getString(3);
                    String nomeDoAtributoReferenciado = rr.getString(4);
                    for (Entidade ent : entidades) {
                        if (nomeDaEntidadeReferenciada.equals(ent.getNome())) {
                            for (Atributo attr : ent.getAtributos()) {
                                if (nomeDoAtributoReferenciado.equals(attr.getNome())) {
                                    atributo.setAtributoImportado(attr);
                                    attr.getAtributosExportados().add(atributo);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                jaw.Main.janelaPrincipal.log(ex.toString());
            }
        }
    }
    private final int ESTAGIO_0 = 0;
    private final int ESTAGIO_1 = 1;
    private final int ESTAGIO_2 = 2;
    private final int ESTAGIO_3 = 3;
    private final int ESTAGIO_4 = 4;
    private final int ESTAGIO_5 = 5;
    private int estagio = 0;

    public List<Entidade> listarEntidades() {
        this.popular();
        return entidades;
    }

    public void popular() {
        switch (estagio) {
            case ESTAGIO_0:
                estagio = -1;
                this.buscarEntidades();
                estagio = 1;
                break;
            case ESTAGIO_1:
                estagio = -1;
                this.buscarAtributos();
                estagio = 2;
                break;
            case ESTAGIO_2:
                estagio = -1;
                this.buscarChavesPrimarias();
                estagio = 3;
                break;
            case ESTAGIO_3:
                estagio = -1;
                this.buscarChavesImportadas();
                estagio = 4;
                break;
            case ESTAGIO_4:
                estagio = -1;
                this.buscarMapeamento();
                estagio = 5;
                break;
            case ESTAGIO_5:
                estagio = -1;
                this.desconectar();
                estagio = 6;
                break;
            default:
                break;
        }
    }

    public void buscarMapeamento() {
        alteracao = Mapeamento.adicionarChaves(tiposDeAtributos);
        Mapeamento.mapear(entidades);
    }

    public void buscarEntidades() {
        this.output("Buscando Entidades...");
        try {
            ResultSet rs1;
            String[] types = {"TABLE", "VIEW"};
            rs1 = dbConn.getMetaData().getTables(null, null, "%", types);
            while (rs1.next()) {
                jaw.metamodelo.Entidade entidade = new jaw.metamodelo.Entidade(rs1.getString(3));
                entidades.add(entidade);
            }
        } catch (Exception ex) {
            jaw.Main.janelaPrincipal.log(ex.toString());
        }
    }

    public void buscarAtributos() {
        this.output("Buscando Atributos...");
        for (Entidade entidade : entidades) {
            int colCount = 0;
            try {
                Statement stmt = dbConn.createStatement();
                ResultSet rset = stmt.executeQuery("SELECT * from " + entidade.getNome() + " WHERE false");
                ResultSetMetaData rsmd = rset.getMetaData();
                colCount = rsmd.getColumnCount();
                int i = 0;

                java.util.Vector<jaw.metamodelo.Atributo> vetor = new java.util.Vector();
                for (i = 0; i < colCount; i++) {
                    Atributo atributo = new Atributo();
                    atributo.setEntidade(entidade);
                    atributo.setNome(rsmd.getColumnName(i + 1));
                    //jaw.Main.janelaPrincipal.log("          " + atributo.toString());
                    atributo.setTamanho(rsmd.getColumnDisplaySize(i + 1));
                    String tipo = rsmd.getColumnTypeName(i + 1);
                    atributo.setTipo(tipo);
                    // Todos os tipos são relacionados para
                    // que possam ser adicionados aos mapeamentos
                    tiposDeAtributos.add(tipo);
                    vetor.add(atributo);
                }
                entidade.setAtributos(vetor);

            } catch (Exception e) {
                jaw.Main.janelaPrincipal.log(e.toString());
            }
        }
    }

    public void conectar() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException cnfe) {
            javax.swing.JOptionPane.showMessageDialog(null, "Driver não encontrado!  " + this.driver);
            jaw.Main.janelaPrincipal.log(cnfe.toString());
        }

        try {
            dbConn = DriverManager.getConnection(this.url + this.banco, this.usuario, this.senha);
            javax.swing.JOptionPane.showMessageDialog(null, "Conexão Realizada com Sucesso!");

        } catch (SQLException se) {
            javax.swing.JOptionPane.showMessageDialog(null, "Não foi possivel conectar ao Banco especificado !  " + this.url + "," + this.banco + "," + this.usuario + "," + this.senha);
            jaw.Main.janelaPrincipal.log(se.toString());
        }
    }

    public void desconectar() {
        try {
            dbConn.close();
        } catch (SQLException ex) {
            jaw.Main.janelaPrincipal.log(ex.toString());
        }
    }

    public boolean getAlteracao() {
        return this.alteracao;
    }

    public boolean isConectado() {
        try {
            if (dbConn != null) {
                return !dbConn.isClosed();
            } else {
                return false;
            }
        } catch (SQLException se) {
            jaw.Main.janelaPrincipal.log(se.toString());
        }
        return false;
    }
}
