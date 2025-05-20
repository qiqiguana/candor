
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
 * Esta classe foi adaptada do resultado da compilação de esquemas com
 * o utilitário xjc (JAXB) de um dos pacotes de liberação e passou 
 * para o pacote base por ser simples presumivelmente "estável", ou 
 * seja, não deve mudar nas próximas liberações.
 * </p>
 * 
 * @deprecated ao invés de fazer marshalling, passamos a usar diretamente o adaptador 
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
     * Número do Recibo.
     */
    @XmlElement(required = true)
    public String getNRec() {
        return nRec;
    }
    public void setNRec(String value) {
        this.nRec = value;
    }

    /**
     * Versão.
     */
    @XmlAttribute(required = true)
    public String getVersao() {
        return versao;
    }
    public void setVersao(String value) {
        this.versao = value;
    }

}
