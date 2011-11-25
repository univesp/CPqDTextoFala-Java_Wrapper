package univesp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import univesp.text2speech.textofala.Client;
import univesp.text2speech.textofala.TextoFala;

public class CarregarArquivos {
	public static void main(String[] params) throws IOException, InterruptedException{
		FileFilter filtro = new FileFilter() {
			public boolean accept(File f){
				return f.getName().endsWith(".txt");
			}
		};
		//TextoFala t2s = new TextoFala();
		Client textofala = new Client();
		File dir = new File("C:\\Users\\paulo\\Desktop\\objetos");
		
		
		

		
		
		StringBuffer sbuffer = null;
		
		for(File arquivo : dir.listFiles(filtro)){
			System.out.println(":file => \t"+arquivo.getName());
			sbuffer = new StringBuffer();
			Reader reader = new InputStreamReader(new FileInputStream(arquivo), "UTF-8");
			BufferedReader bf = new BufferedReader(reader);
			while(bf.ready()){
				sbuffer.append( new String(bf.readLine().getBytes("UTF-8"), "UTF-8") + "\n" );
			}
			
			//System.out.println(sbuffer.toString());
			//t2s.converter(sbuffer.toString(), arquivo.getName());
			if(textofala.converter(sbuffer.toString(), arquivo.getName())){
				System.out.println("Arquivo " + arquivo.getName() + " convertido com sucesso para " + arquivo.getName() + ".wav!");
			}
			else{
				System.err.println("Falha na conversão do arquivo " + arquivo.getName() + " para " + arquivo.getName() + ".wav.");
				System.err.println(textofala.getError());
			}
		}
	}
}
