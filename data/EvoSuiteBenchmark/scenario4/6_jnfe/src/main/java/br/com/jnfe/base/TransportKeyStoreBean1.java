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

    private final static String DEFAULT_KEYSTORE_URI = "file:#{ systemProperties['user.home'] }/jnfe.pfx";

    private final static String DEFAULT_KEYSTORE_TYPE = "pkcs12";

    private String keyStoreUri;

    private String keyStoreType;

    private String keyStorePassword;

    private String trustStoreType;

    private String trustStore;

    private String trustStorePassword;

    /**
     * A URI para a keystore usada para autenticar o transporte.
     *
     * @param keyStoreUri
     */
    public void setKeyStoreUri(String keyStoreUri);

    /**
     * O tipo de keystore empregado para o transporte.
     *
     * @param keyStoreType
     */
    public void setKeyStoreType(String keyStoreType);

    /**
     * A senha para acesso ao keystore empregado para o transporte.
     *
     * @param keyStorePassword
     */
    public void setKeyStorePassword(String keyStorePassword);

    /**
     * Localiza��o do aramz�m seguro.
     *
     * @param trustStore
     */
    public void setTrustStore(String trustStore);

    /**
     * Tipo do armaz�m do armaz�m seguro (cacerts).
     *
     * @param trustStoreType
     */
    public void setTrustStoreType(String trustStoreType);

    /**
     * Senah do armaz�m seguro (cacerts).
     *
     * @param trustStorePassword
     */
    public void setTrustStorePassword(String trustStorePassword);

    public void afterPropertiesSet() throws Exception;

    /**
     * toString
     * @return String
     */
    public String toString();

    /**
     * Abre o  armaz�m seguro (cacerts).
     *
     * @throws Exception
     */
    public KeyStore openTransportStore() throws Exception;

    /**
     * Abre o gerenciador de chaves do armaz�m de transporte.
     *
     * @throws Exception
     */
    public KeyManagerFactory openTransportKeyManagerFactory() throws Exception;

    private static final Logger logger = LoggerFactory.getLogger(TransportKeyStoreBean.class);
}
