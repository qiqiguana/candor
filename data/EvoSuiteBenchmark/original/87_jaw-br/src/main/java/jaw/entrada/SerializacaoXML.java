package jaw.entrada;

import com.thoughtworks.xstream.XStream;
import java.io.Reader;
import java.util.List;
import jaw.metamodelo.Atributo;
import jaw.metamodelo.Entidade;

public class SerializacaoXML {

    public SerializacaoXML() {

    }

    public String entidades2XML(List<jaw.metamodelo.Entidade> entidades) {
        XStream xStream = new XStream();
        xStream.alias("entidade", Entidade.class);
        xStream.useAttributeFor(Entidade.class, "nome");
        xStream.useAttributeFor(Entidade.class, "pacote");
        
        xStream.alias("atributo", Atributo.class);
        xStream.useAttributeFor(Atributo.class, "nome");
        xStream.useAttributeFor(Atributo.class, "tipo");
        xStream.useAttributeFor(Atributo.class, "tamanho");
        xStream.useAttributeFor(Atributo.class, "chavePrimaria");

        String xml = xStream.toXML(entidades);
        return xml;
    }

    public List<Entidade> xml2Entidades(Reader arquivo) {
        XStream xStream = new XStream();
        
        xStream.alias("entidade", Entidade.class);
        xStream.useAttributeFor(Entidade.class, "nome");
        xStream.useAttributeFor(Entidade.class, "pacote");
        
        xStream.alias("atributo", Atributo.class);
        xStream.useAttributeFor(Atributo.class, "nome");
        xStream.useAttributeFor(Atributo.class, "tipo");
        xStream.useAttributeFor(Atributo.class, "tamanho");
        xStream.useAttributeFor(Atributo.class, "chavePrimaria");

        java.util.Vector<jaw.metamodelo.Entidade> entidades = (java.util.Vector<jaw.metamodelo.Entidade>) xStream.fromXML(arquivo);
        return entidades;
    }
}
