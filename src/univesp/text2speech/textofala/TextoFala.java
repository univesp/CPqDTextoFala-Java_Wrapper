package univesp.text2speech.textofala;

import java.util.HashMap;
import java.util.Map;

import univesp.text2speech.Text2Speech;
import univesp.text2speech.textofala.exceptions.*;

import com.sun.jna.Native;
import com.sun.jna.Platform;

public class TextoFala implements Text2Speech{
	
	private ITextoFala CTFKernel = null;
	private Map<Long, String> canais = null;
	private String CTF_HOME = "";
	
	public TextoFala(){
		
		this.CTF_HOME = System.getProperty("user.dir") + System.getProperty("file.separator") + "ext";
		if(Platform.isWindows()){
			this.CTF_HOME += System.getProperty("file.separator") + "win_textofala";
			System.out.println("$HOME="+CTF_HOME);
			System.setProperty("jna.library.path", this.CTF_HOME);
			this.CTFKernel = (ITextoFala) Native.loadLibrary("ctf.dll", ITextoFala.class);
		}
		else{
			System.out.println("$HOME="+CTF_HOME);
			System.setProperty("jna.library.path", this.CTF_HOME);
			this.CTFKernel = (ITextoFala) Native.loadLibrary("cpqdtf", ITextoFala.class);
		}
		this.CTFKernel.tts_inicializa();
		System.out.println("Biblioteca carregada de : " + this.CTF_HOME + ", versão: " + this.versao());
		
		this.canais = new HashMap<Long, String>();
		//this.criarCanais(1);
	}
	
	@Override
	protected void finalize() throws TextoFalaFinalizeException, Throwable{
		// TODO Auto-generated method stub
		if(!this.canais.isEmpty()){ for(long canal : this.canais.keySet()){ this.cfreeChannel(canal); } }
		if(this.CTFKernel != null){ this.CTFKernel.tts_finaliza(); }
		super.finalize();
	}
			
	private long callocChannel(){
		if(this.CTFKernel == null){
			/// TODO: Add Exception
		}
		long canal = this.CTFKernel.tts_alocaCanal();
		if(canal <= 0){
			/// TODO: Add Exception
		}
		return canal;
	}
	
	private void cfreeChannel(long canal){
		this.CTFKernel.tts_desalocaCanal(canal);
	}
	
	public String versao(){
		return this.CTFKernel.tts_versao();
	}
	
	public Map<Long, String> criarCanais( int quantidade ){
		for(int q = 0; q < quantidade; q++){
			this.canais.put(this.callocChannel(), "available");
		}
		return this.canais;
	}
	
	long buscarCanaisDisponiveis(){
		for(long c : this.canais.keySet()){
			if (this.canais.get(c) == "available"){
				return c;
			}
		}
		return 0;
	}
	
	public String converter(String s){
		long canal = this.buscarCanaisDisponiveis();
		String file = "";
		this.CTFKernel.tts_sintetizaTexto_mf(canal, s, file);
		return file;
	}
}