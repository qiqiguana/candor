package jaw.saida;

import jaw.metamodelo.Entidade;
import org.apache.velocity.*;
import org.apache.velocity.app.*;
import java.io.StringWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;
import jaw.template.ModeloDeAplicacao;
import jaw.template.TemplateJaw;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;

public class Gerador {

    List<jaw.metamodelo.Entidade> entidades;
    VelocityContext context = new VelocityContext();
    ModeloDeAplicacao modelo;

    public Gerador(List<jaw.metamodelo.Entidade> entidades, ModeloDeAplicacao modelo) {
        this.entidades = entidades;
        this.modelo = modelo;

        context = new VelocityContext();
        Properties propriedades = jaw.Main.configuracao;
        propriedades.setProperty("resource.loader", "string");
        propriedades.setProperty("string.resource.loader.description", "Velocity StringResource loader");
        propriedades.setProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");

        for (TemplateJaw template : modelo.getTemplates()) {
            try {
                Velocity.init(propriedades);
                StringResourceLoader srl = new StringResourceLoader();
                StringResourceLoader.getRepository().putStringResource("template", template.getVelocityTemplate());
            } catch (Exception e) {
                jaw.Main.janelaPrincipal.log(e.toString());
            }

            if (template.getTipo() == TemplateJaw.INDIVIDUAL) {
                for (Entidade entidade : entidades) {
                    gerarIndividual(entidade, template);
                }
            } else {
                gerarUnico(entidades, template);
            }
        }
    }

    public void gerarIndividual(Entidade entidade, TemplateJaw template) {
        try {

            Template t = Velocity.getTemplate("template");
            context.put("entidade", entidade);
            StringWriter writer = new StringWriter();
            // mistura o contexto com o template
            t.merge(context, writer);

            // Replace
            String caminho = template.getDestino(entidade);

            // se nao houver os diretorios, cria
            try {
                if (!new File(caminho).exists()) {
                    new File(caminho).mkdirs();
                }
            } catch (Exception e) {
                jaw.Main.janelaPrincipal.log(e.toString());
            }

            try {
                File arquivoDestino =
                        new File(caminho + "/" +
                        template.getNomeDoArquivo(entidade));
                FileWriter fw = new FileWriter(arquivoDestino);
                fw.write(writer.toString());
                fw.flush();
                fw.close();
            } catch (Exception e) {
                jaw.Main.janelaPrincipal.log(e.toString());
            }
        } catch (Exception ex) {
            jaw.Main.janelaPrincipal.log(ex.toString());
        }
    }

    public void gerarUnico(List<Entidade> entidades, TemplateJaw template) {
        try {
            Template t = Velocity.getTemplate("template");
            context.put("entidades", entidades);
            StringWriter writer = new StringWriter();
            // mistura o contexto com o template
            t.merge(context, writer);

            // se nao houver os diretorios, cria
            String caminho = template.getDestino();

            try {
                if (!new File(caminho).exists()) {
                    new File(caminho).mkdirs();
                }
            } catch (Exception e) {
                jaw.Main.janelaPrincipal.log(e.toString());
            }
            File arquivoDestino = new File(caminho + "/" + template.getNomeDoArquivo());
            FileWriter fw = new FileWriter(arquivoDestino);
            fw.write(writer.toString());
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            jaw.Main.janelaPrincipal.log(ex.toString());
        }
    }
}
