/**
 * Services : Contient des fonctions de tests de services réseau
 */

import java.net.*;
import java.io.*;
//import java.lang.*;
//import java.nio.channels.*;
//import java.nio.*;
//import java.nio.charset.*;

/**
 * @author Gilles
 *
 */
public class Services {
	
	/*
	 * Test de service http (port 80 par defaut)
	 * paramétres : url, n° de port
	 * retour : HTTP_Result, contient tous les résultats
	 */
	public static HTTP_Result testHTTP(String url, int port)
	{
		
		HTTP_Result res = new HTTP_Result();
		URL serv;
		BufferedReader in = null;
				
		long start = System.currentTimeMillis(); //pour calcul du temp de chargement
		try
		{
			serv = new URL("http", url, port, ""); //connection au serveur
			HttpURLConnection conn = (HttpURLConnection)(serv.openConnection()); //pour interprétation du code de page
			conn.setConnectTimeout(3000); //timeout 3s
			in = new BufferedReader(new InputStreamReader(serv.openStream()));
			String ligne; String page = ""; //récupération de la page
			while ((ligne = in.readLine()) != null)
			{	page = page + ligne + "\n"; }
		
			//Resultat :
			res.works = true;
			res.time = System.currentTimeMillis()-start;
			res.page_weight = page.length();
			res.code = conn.getResponseCode();
			res.Error = "";		
		}
		//Gestion des Erreurs
		catch(MalformedURLException e) {res.reset(); res.Error = "**URL Mal Formé** (" + e + ") **";}
		catch(IOException e) {res.reset(); res.Error = "**Erreur d'E/S** (" + e + ") **";}
		catch(Exception e) {res.reset(); res.Error = "**Erreur : " + e + "**";}
		finally
		{
			try {	in.close(); } //cloture du flux
			catch(Exception e) { }
		}
		return res;
	}

	
	/*
	 * Test de service https (port 443 par defaut)
	 * paramétres : url, n° de port
	 * retour : HTTP_Result, contient tous les résultats
	 */
	public static HTTP_Result testHTTPS(String url, int port)
	{
		
		HTTP_Result res = new HTTP_Result();
		URL serv;
		BufferedReader in = null;
				
		long start = System.currentTimeMillis(); //pour calcul du temp de chargement
		try
		{
			serv = new URL("https", url, port, ""); //connection au serveur
			HttpURLConnection conn = (HttpURLConnection)(serv.openConnection()); //pour interprétation du code de page
			conn.setConnectTimeout(3000); //timeout 3s
			in = new BufferedReader(new InputStreamReader(serv.openStream()));
			
			String ligne; String page = ""; //récupération de la page
			while ((ligne = in.readLine()) != null)
			{	page = page + ligne + "\n"; }
		
			in.close(); //cloture du flux
			
			//Resultat :
			res.works = true;
			res.time = System.currentTimeMillis()-start;
			res.page_weight = page.length();
			res.code = conn.getResponseCode();
			res.Error = "";		
		}
		//Gestion des Erreurs
		catch(MalformedURLException e) {res.reset(); res.Error = "**URL Mal Formé** (" + e + ") **";}
		catch(IOException e) {res.reset(); res.Error = "**Erreur d'E/S** (" + e + ") **";}
		catch(Exception e) {res.reset(); res.Error = "**Erreur : " + e + "**";}
		finally
		{
			try {	in.close(); } //cloture du flux
			catch(Exception e) { }
		}
		return res;
	}
	
	
	/*
	 * Test de service pop3 (port 110 par defaut)
	 * paramétres : url, n° de port
	 * retour : SIMPLE_Result, contient tous les résultats
	 */
	public static SIMPLE_Result testPOP3(String url, int port)
	{
		SIMPLE_Result res = new SIMPLE_Result();
		long start = System.currentTimeMillis(); //pour calcul du temp de chargement
		Socket sock = null;
        PrintWriter out = null;
        BufferedReader in = null;
		
		try
		{
            sock = new Socket(url, port);
            sock.setSoTimeout(3000); //timeout 3s
            out = new PrintWriter(sock.getOutputStream(), true); //flux de sortie
            in = new BufferedReader(new InputStreamReader(sock.getInputStream())); //flux d'entrée
            
            //Vérification que le serveur dise bien +OK et on quit
            String ligne = in.readLine(); //récupération l'annonce serveur (1ere ligne)
			out.write("QUIT\r\n"); //quitter proprement
			
			//Resultat :
			if(ligne.indexOf("+OK")>(-1)) //+OK present dans l'annonce
			{
				res.works = true;
				res.time = System.currentTimeMillis()-start;
				res.Error = "";
			}
			else
			{
				res.works = false;
				res.time = System.currentTimeMillis()-start;
				res.Error = "**Réponse du serveur incorrect**";
			}			
		}
		//Gestion des Erreurs
		catch(MalformedURLException e) {res.reset(); res.Error = "**URL Mal Formé** (" + e + ") **";}
		catch(IOException e) {res.reset(); res.Error = "**Erreur d'E/S** (" + e + ") **";}
		catch(Exception e) {res.reset(); res.Error = "**Erreur : " + e + "**";}
		finally
		{
			try {	out.close(); in.close(); sock.close(); } //cloture du flux
			catch(Exception e) { }
		}

		return res;
	}


	/*
	 * Test de service smtp (port 25 par defaut)
	 * paramétres : url, n° de port
	 * retour : SIMPLE_Result, contient tous les résultats
	 */
	public static SIMPLE_Result testSMTP(String url, int port)
	{
		SIMPLE_Result res = new SIMPLE_Result();
		long start = System.currentTimeMillis(); //pour calcul du temp de chargement
		Socket sock = null;
        PrintWriter out = null;
        BufferedReader in = null;
		
		try
		{
            sock = new Socket(url, port);
            sock.setSoTimeout(3000); //timeout 3s
            out = new PrintWriter(sock.getOutputStream(), true); //flux de sortie
            in = new BufferedReader(new InputStreamReader(sock.getInputStream())); //flux d'entrée
            
            //séquence smtp
            //S: 220 smtp.... Ready
            //C: EHLO machine.com
            //S: 250 smtp....
            //C: QUIT
			String ligne = in.readLine();
			out.write("QUIT\r\n"); //quitter proprement
			
			//Resultat :
			if(ligne.indexOf("220")>(-1)) //220 present dans l'annonce
			{
				res.works = true;
				res.time = System.currentTimeMillis()-start;
				res.Error = "";
			}
			else
			{
				res.works = false;
				res.time = System.currentTimeMillis()-start;
				res.Error = "**Réponse incorrect**";
			}
		}
		//Gestion des Erreurs
		catch(MalformedURLException e) {res.reset(); res.Error = "**URL Mal Formé** (" + e + ") **";}
		catch(IOException e) {res.reset(); res.Error = "**Erreur d'E/S** (" + e + ") **";}
		catch(Exception e) {res.reset(); res.Error = "**Erreur : " + e + "**";}
		finally
		{
			try {	out.close(); in.close(); sock.close(); } //cloture du flux
			catch(Exception e) { }
		}

		return res;
	}
	
	
	
/*	public static int TestHTTPc() throws Exception
	{
		//socket par channel avec les nio (+ performant)
		long deb = System.currentTimeMillis(); //test de temps
		
		//1 Def de la socket
		SocketChannel s = SocketChannel.open(); //def d'un channel socket non connecté
		Socket sock = s.socket(); //recup de la socket associé au channel
		sock.setSoTimeout(3000); //def d'un timeout
		
		//2 connection à l'hote
		sock.connect(new InetSocketAddress("www.google.fr", 80));
		
		//3 envoi la requete
		Charset charset = Charset.forName("ISO-8859-1");
		s.write(charset.encode(CharBuffer.wrap("GET / HTTP/1.0\r\n\r\n")));
		
		//4 recup de ce qui arrive
		ByteBuffer buffin = ByteBuffer.allocate(8192); //mémoire tampon
		try {s.read(buffin); }  //lecture du buffer lié a la socket
		catch(Exception e) {System.out.println(e); }
		
		System.out.println("- TEMPS DE CHARGEMENT - : " + (System.currentTimeMillis()-deb) + " ms.");
		
		return 1;
	}
*/
	
/*****************************************
 * Classes internes qui contiennent le résultats des test
 *****************************************/
	
/*
 * Utilisé pour les résultat de HTTP et HTTPS
 */
public static class HTTP_Result
{
	public boolean works;
	public long time;
	public int page_weight;
	public int code;
	public String Error;
	
	public HTTP_Result() {works = false; time = 0; page_weight = 0; code = 0; Error ="";}
	public void reset() {works = false; time = 0; page_weight = 0; code = 0; Error ="";}
	public String toString() //pour affichage en console
	{
		return "**** Resultat : \n" +
		"** Fonctionnement : " + works + "\n" +
		"** Temps : " + time + " ms\n" +
		"** Poid de la page : " + page_weight + " octets\n" +
		"** Code de page : " + code + "\n" +
		"** Erreur générée : " + Error +".\n****";
	}
}

/*
 * Utilisé pour les résultat de HTTP et HTTPS
 */
public static class SIMPLE_Result
{
	public boolean works;
	public long time;
	public String Error;
	
	public SIMPLE_Result() {works = false; time = 0; Error ="";}
	public void reset() {works = false; time = 0; Error ="";}
	public String toString() //pour affichage en console
	{
		return "**** Resultat : \n" +
		"** Fonctionnement : " + works + "\n" +
		"** Temps : " + time + " ms\n" +
		"** Erreur générée : " + Error +".\n****";
	}
}

}