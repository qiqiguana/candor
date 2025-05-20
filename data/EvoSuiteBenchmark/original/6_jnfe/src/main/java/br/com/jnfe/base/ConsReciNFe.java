
package br.com.jnfe.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Consulta recibo da NFe.
 * 
 * <p>
 * Esta classe foi adaptada do resultado da compila��o de esquemas com
 * o utilit�rio xjc (JAXB) de um dos pacotes de libera��o e passou 
 * para o pacote base por ser simples presumivelmente "est�vel", ou 
 * seja, n�o deve mudar nas pr�ximas libera��es.
 * </p>
 * 
 * @deprecated ao inv�s de fazer marshalling, passamos a usar diretamente o adaptador 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TConsReciNFe", propOrder = {
    "tpAmb",
    "nRec"
})
@XmlRootElement(name="consReciNFe")
public class ConsReciNFe {

    private String tpAmb;
    private String nRec;
    private String versao;
    
    /**
     * Tipo de ambiente.
     */
    @XmlElement(required = true)
    public String getTpAmb() {
        return tpAmb;
    }
    public void setTpAmb(String value) {
        this.tpAmb = value;
    }

    /**
     * N�mero do Recibo.
     */
    @XmlElement(required = true)
    public String getNRec() {
        return nRec;
    }
    public void setNRec(String value) {
        this.nRec = value;
    }

    /**
     * Vers�o.
     */
    @XmlAttribute(required = true)
    public String getVersao() {
        return versao;
    }
    public void setVersao(String value) {
        this.versao = value;
    }

}
