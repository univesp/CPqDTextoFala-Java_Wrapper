package univesp;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import univesp.text2speech.textofala.TextoFala2;

public class ClienteClasseMT {
	
	private final int THEAD_POOL = 4;
	private ArrayList<File> arquivosCarregados;
	
	public ClienteClasseMT(){
		this.arquivosCarregados = new ArrayList<File>();
	}	
	public void carregarArquivos( String diretorioBase ){
		FileFilter filtro = new FileFilter() {
			public boolean accept(File f){
				return f.getName().endsWith(".txt");
			}
		};
		for(File arquivo : new File(diretorioBase).listFiles(filtro)){
			carregarArquivo(arquivo);
		}
		
	}
	
	public void carregarArquivo( String caminho ){
		carregarArquivo(new File(caminho));
	}
	
	public void carregarArquivo(File arquivo){
		/// TODO Adicionar verificações se o arquivo realmente existe.
		/// TODO Adicionar verificações se é um arquivo válido.
		this.arquivosCarregados.add(arquivo);
	}
	/// public void carregarArquivos(File[] arquivos){} 
	
	public ArrayList<File> arquivos(){
		return this.arquivosCarregados;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClienteClasseMT mt = new ClienteClasseMT();
		mt.carregarArquivos("C:\\Users\\paulo.UNIVESP\\Desktop\\objetos");
		TextoFala2 ctf = new TextoFala2();
		
		for(File f : mt.arquivos()){
			System.out.println("Convertendo " + f.getName() + " ...");
			ctf.converter(f, f.getPath() + f.getName());
			System.out.println(f.getName() + " convertido com sucesso (espera-se) em " + f.getPath());
		}
		
		System.out.println("Ok!");
	}

}
