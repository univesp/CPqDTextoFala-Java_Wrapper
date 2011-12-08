package univesp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import univesp.text2speech.textofala.TextoFala2;

public class SequencialClient {
	public void main(String[] params) throws IOException{
		
		FileFilter filtro = new FileFilter() {
			public boolean accept(File f){
				return f.getName().endsWith(".txt");
			}
		};
		
		System.out.println("Carregando arquivos...");
		
		File dir = new File("");
		StringBuffer sbuffer;
		
		System.out.println("convertendo...");
		TextoFala2 tf = new TextoFala2();
		for(File arquivo : dir.listFiles(filtro)){
			System.out.println(":file => \t"+arquivo.getName());
			sbuffer = new StringBuffer();
			Reader reader = new InputStreamReader(new FileInputStream(arquivo), "UTF-8");
			BufferedReader bf = new BufferedReader(reader);
			while(bf.ready()){
				sbuffer.append( new String(bf.readLine().getBytes("UTF-8"), "UTF-8") + "\n" );
			}
			if( tf.converter(sbuffer.toString(), arquivo.getName())){
				System.out.println("Arquivo " + arquivo.getName() + " convertido com sucesso para " + arquivo.getName() + ".wav!");
			}
			else{
				System.err.println("Falha na conversão do arquivo " + arquivo.getName() + " para " + arquivo.getName() + ".wav.");
			}
			System.gc();
		}
		
	}
}
