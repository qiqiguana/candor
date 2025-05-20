package jaw.util;

public class Conversor {

    /** Creates a new instance of Conversor */
    public Conversor() {
    }

    public static String toClasse(String nome) {
        for (int i = 0; i < nome.length(); i++) {
            if (i == 0) {
                nome = nome.substring(0, i + 1).toUpperCase() + nome.substring(1, nome.length());
            }
            if (nome.charAt(i) == '_') {
                if (i == nome.length() - 1) {
                    nome = nome.substring(0, i);
                } else {
                    nome = nome.substring(0, i) +
                            nome.substring(i + 1, i + 2).toUpperCase() +
                            nome.substring(i + 2, nome.length());
                    i--;
                }
            }
        }
        return nome;
    }

    public static String toAtributo(String nome) {
        for (int i = 0; i < nome.length(); i++) {
            if (nome.charAt(i) == '_') {
                if (i == nome.length() - 1) {
                    nome = nome.substring(0, i);
                } else {
                    nome = nome.substring(0, i) +
                            nome.substring(i + 1, i + 2).toUpperCase() +
                            nome.substring(i + 2, nome.length());
                    i--;
                }
            }
        }
        return nome;
    }

    public static String toTabela(String nome) {
        for (int i = 0; i < nome.length(); i++) {
            if (!nome.substring(i, i + 1).equals(nome.substring(i, i + 1).toUpperCase())) {
                continue;
            }
            if (nome.substring(i, i + 1).equals("_")) {
                continue;
            }
            nome = nome.substring(0, i) +
                    "_" +
                    nome.substring(i, i + 1).toLowerCase() +
                    nome.substring(i + 1, nome.length());
            i++;
        }
        return nome;
    }

    public static String toTexto(String nome) {
        for (int i = 0; i < nome.length(); i++) {
            if (!nome.substring(i, i + 1).equals("_")) {
                continue;
            }
            nome = nome.substring(0, i) +
                    " " +
                    nome.substring(i+1,nome.length()).toLowerCase();
            i++;
        }
        return nome;
    }

    public static void main(String[] args) {
        String texto = "Texto_para_teste_de_conversao_a_toa";
        System.out.println(texto + " - " + Conversor.toTexto(texto));
    }
}
