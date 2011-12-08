package univesp.text2speech.textofala;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * Uma classe mais elegante para a manipulação da biblioteca.
 * Deve ser considerada que ela deve ser mono thread e outra classe deve manipular uma concorrencia de uma coleção
 * de instancias.
 * 
 * @author "@paulopatto"<paulopatto@gmail.com>
 * @version 2.0
 * @since 02/DEZ/2011
 */
public class TextoFala2 {
	
	///Constantes
	private String $HOME = System.getProperty("user.dir") + System.getProperty("file.separator") + "ext";
	private String $OS_PATH = "";
	private String $LIBNAME;
	private String $SAVE_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "output";
	
	private long canal;
	private ITextoFala core = null;
	
	/// O Brâman
	public TextoFala2(){
		this.carregarBiblioteca();
		core.tts_inicializa();
		this.canal = this.alocarCanal().longValue();
	}
	
	/// Os Vishnu
	public boolean converter(String textoAConverter, String caminhoDoArquivoDeSaida, String nomeDoArquivoDeSaida){
		PointerByReference memory = new PointerByReference();
		NativeLong info;
		info = this.core.tts_sintetizaTexto_mm(new NativeLong(this.canal), textoAConverter, memory);
		
		/// Análise de resultados da sinteze
		/// TODO: Tratar erros
		if(info.longValue() < 0){
			log(ERROR, core.tts_mensagemErro());
			return false;
		}
		
		Pointer memoryStream = memory.getValue();
		try {
			BufferedOutputStream outputDataFile = 
					new BufferedOutputStream(
							new FileOutputStream(
									new File(
										caminhoDoArquivoDeSaida + 
										System.getProperty("file.separator") + 
										nomeDoArquivoDeSaida + ".wav"
									)
							)
					);
			/// FIM DO OUTPUTDATAFILE
			
			for(byte data : memoryStream.getByteArray(0, info.intValue())){
				outputDataFile.write(data);
			}
			outputDataFile.close();
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public boolean converter(String texto){
		return converter(texto, $SAVE_PATH, texto.substring(0, 20));
	}
	public boolean converter(String texto, String filename){
		return converter(texto, $SAVE_PATH, filename);
	}
	public boolean converter(File arquivo){
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));
			while(reader.ready()){
				buffer.append( new String(reader.readLine().getBytes("UTF-8"), "UTF-8") + "\n" );
			}
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return converter(buffer.toString(), $SAVE_PATH, arquivo.getName());
	}
	public boolean converter(File arquivo, String salvarComo){
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));
			while(reader.ready()){
				buffer.append( new String(reader.readLine().getBytes("UTF-8"), "UTF-8") + "\n" );
			}
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return converter(buffer.toString(), $SAVE_PATH, salvarComo);
	}
	
	public String getVersion(){
		return this.core.tts_versao();
	}
	
	/// O Shiva
 	protected void finalize() throws  Throwable{
		this.core.tts_desalocaCanal(this.canal);
		this.core.tts_finaliza();
	}
	
	/// HELPERS
	private NativeLong alocarCanal(){
		NativeLong c = this.core.tts_alocaCanal();
		if(c.longValue() < 0){
			/// TODO: Aqui lançar uma excessão de erro de alocação de canal.
			log(ERROR, "Erro na alocação de canal.");
			log(ERROR, core.tts_mensagemErro());
			return c;
		}
		else{
			log(LOG, "Alocado canal " + c.longValue() + " para a biblioteca.");
			return c;
		}
	}
	private void carregarBiblioteca(){
		/// Configure a plataforma
		if(Platform.isWindows()){
			this.$OS_PATH = System.getProperty("file.separator") + "windows";
			this.$LIBNAME = "ctf";
		}
		else if(Platform.isLinux()){
			this.$OS_PATH = System.getProperty("file.separator") + "linux";
			this.$LIBNAME = "cpqdtf";
		}
		else if(Platform.isMac()){
			/// TODO: Ainda não implementado o suporte a plataforma Mac OS/X (Darwin)
		}
		else{
			/// TODO: Ainda não implementado o suporte a outras plataformas.
		}
		
		/// Carrega a CPqD Texto-Fala
		System.setProperty("jna.library.path", this.$HOME+this.$OS_PATH);
		this.core = (ITextoFala) Native.loadLibrary(this.$LIBNAME, ITextoFala.class);
		/// TODO: Incluir uma verificação de sucesso no carregamento da biblioteca do texto fala.
	}
	
	/**
	 * Classe ficticia apenas para substituir uma classe de logs
	 * @param tipo
	 * @param mensagem
	 */
	private final int ERROR = 0;
	private final int LOG = 1;
	private void log(int tipo, String m){
		if(tipo == ERROR){
			System.err.println("[ERROR] :"+ m);
		}
		else{
			System.out.println("[LOG] " + m);
		}
	}
}
