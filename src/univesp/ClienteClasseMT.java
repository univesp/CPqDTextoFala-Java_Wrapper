package univesp;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class ClienteClasseMT {
	
	private final int THEAD_POOL = 4;
	private ArrayList<File> arquivosCarregados;
	
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
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
