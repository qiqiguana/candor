package br.com.jnfe.base;

import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import br.com.jnfe.base.util.SecurityUtils;

/**
 * Atualiza as propriedades do sistema para establecer a "keyStore"
 * usada para o transmiss�o das mensagens dos web services NFe.
 *
 * @author Mauricio Fernandes de Castro
 */
public class TransportKeyStoreBean implements InitializingBean {

    /**
     * Abre o gerenciador de chaves do armaz�m de transporte.
     *
     * @throws Exception
     */
    public KeyManagerFactory openTransportKeyManagerFactory() throws Exception {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(openTransportStore(), keyStorePassword.toCharArray());
        return kmf;
    }
}
