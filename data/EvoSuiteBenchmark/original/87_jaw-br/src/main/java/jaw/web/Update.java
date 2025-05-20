/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaw.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author flavio
 */
public class Update {

    public static void getModelos() throws Exception{
        Update.getModelo("http://jaw-br.sourceforge.net/modelos/Sistema%20Web%20I.modelo.xml");
        Update.getModelo("http://jaw-br.sourceforge.net/modelos/Documentacao.modelo.xml");
    }
    private static void getModelo(String modelo) throws Exception {
        URL url = new URL(modelo);
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        urlcon.setDoOutput(true);
        urlcon.setRequestMethod("GET");
        urlcon.setDoInput(true);
        urlcon.setUseCaches(false);

        // Get the response

        BufferedReader rd = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
        String line;

        java.io.FileWriter escrevedor = new java.io.FileWriter(jaw.Main.configuracao.getCaminhoDosModelos() + "Sistema Web I.modelo.xml");

        while ((line = rd.readLine()) != null) {
            escrevedor.write(line + "\n");
        }

        escrevedor.flush();
        escrevedor.close();
        rd.close();
    }
}