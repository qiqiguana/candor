/**
 * Lecture du fichier de config contenant la liste des urls
 */
import java.io.*;
import java.lang.String;
import java.util.Vector;




/**
 * @author mnouzille
 *
 */
public class Fichier {

	String file;
	Vector <String> tabFile;

public Fichier(String fileName)
{
		file = fileName;
		tabFile = new Vector<String>();
}
	
public Vector <String> readFile()
{

	try {
	Reader r = new FileReader(file);
	BufferedReader br=new BufferedReader(r);
	String ligne;
	int i=0;
	while ( (ligne = br.readLine()) != null)
	{
		tabFile.add(ligne);
		i++;
	}
	}
	catch(FileNotFoundException e)
	{
		
	}
	catch(IOException e)
	{
		
	}
	finally
	{
		
	}
return tabFile;
}
	
}
