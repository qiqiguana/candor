package net.sf.xisemele.api;

import java.io.Serializable;

import net.sf.xisemele.exception.ElementNotFoundException;

/**
 * Definição de interface de leitura de XML.
 * 
 * @author Carlos Eduardo Coral.
 */
public interface Reader extends Serializable {

   /**
    * Retorna a versão do XML.
    * 
    * @return
    *       <code>java.lang.String</code> contendo a versão do XML.
    */
   String version();
   
   /**
    * Retorna o <i>encoding</i> do XML.
    * 
    * @return
    *       <code>java.lang.String</code> contendo o <i>encoding</i> do XML.
    */
   String encoding();
   
   /**
    * Retorna o elemento raiz do XML.
    * 
    * @return
    *       instância de {@link Element} correspondente ao elemento raiz do XML.
    */
   Element root();
   
   /**
    * Verifica se há um elemento correspondente ao <code>path</code> especificado por
    * parâmetro.
    * 
    * <p>
    * Veja a sintaxe para <i>path</i> no método {@link #find(String)}.
    * </p>
    * 
    * @param path
    *       <code>java.lang.String</code> correspondente ao elemento que será verificado.
    *       
    * @return
    *       <code>true</code> se existir um elemento para o <code>path</code> especificado.
    */
   boolean containsElement(String path);
   
   /**
    * Retorna o elemento correspondente ao <code>path</code> especificado por parâmetro.
    * 
    * <p>
    * O valor de <code>path</code> indica o caminho para se chegar até o elemento desejado
    * a partir do elemento raiz do documento XML. Cada elemento do caminho deve ser separado pelo
    * caractere '/'. 
    * </p>
    * 
    * <p>
    * Exemplo: </br>
    * XML: 
    * <pre>
    * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
    * &lt;root&gt;
    *   &lt;level1&gt;
    *     &lt;level2 /&gt;
    *   &lt;/level1&gt;
    * &lt;/root&gt;
    * </pre> 
    * </p>
    * 
    * Tabela contendo o elemento e o <i>path</i> correspondente.
    * <table border="1">
    *    <tr>
    *       <td align="center">Elemento</td>
    *       <td align="center"><i>path</td>
    *    </tr>
    *    <tr>
    *       <td>root</td>
    *       <td>root</td>
    *    </tr>
    *    <tr>
    *       <td>level1</td>
    *       <td>root/level1</td>
    *    </tr>
    *    <tr>
    *       <td>level2</td>
    *       <td>root/level1/level2</td>
    *    </tr>
    * </table>
    * 
    * <p>
    * A exceção {@link ElementNotFoundException} será disparada se o método não localizar um elemento
    * para o <code>path</code> especificado. Portando, para evitar uma possível exceção, verifique se o elemento
    * existe para o <code>path</code> consultando o método {@link #containsElement(String)}.
    * </p>
    * 
    * @param path
    *       <code>java.lang.String</code> contendo o <i>path</i> para o elemento que deverá ser retornado.
    *       
    * @return
    *       instância de {@link Element} correspondente ao <code>path</code> especificado
    *       ou o valor <code>null</code> caso não encontre.
    *       
    * @throws ElementNotFoundException
    *       exceção disparada se não for localizado um elemento para o <code>path</code> especificado. Para previnir isso, 
    *       verifique antes se a instância de {@link Reader} {@link #containsElement(String) contém o elemento} desejado.
    */
   Element find(String path) throws ElementNotFoundException;
}
