package univesp.text2speech.textofala;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class Client {
	
	private ITextoFala textofala = null;
	private String CTF_HOME = "";
	private long canal;
	
	public Client(){
		this.CTF_HOME = System.getProperty("user.dir") + System.getProperty("file.separator") + "ext";
		if(Platform.isWindows()){
			this.CTF_HOME += System.getProperty("file.separator") + "win_textofala";
			System.out.println("$HOME="+CTF_HOME);
			System.setProperty("jna.library.path", this.CTF_HOME);
			this.textofala = (ITextoFala) Native.loadLibrary("ctf.dll", ITextoFala.class);
		}
		else{
			System.out.println("$HOME="+CTF_HOME);
			System.setProperty("jna.library.path", this.CTF_HOME);
			this.textofala = (ITextoFala) Native.loadLibrary("cpqdtf", ITextoFala.class);
		}
		this.textofala.tts_inicializa();
		
		this.canal = textofala.tts_alocaCanal().longValue();
		String splash_txt = "Biblioteca carregada de : $HOME=" + this.CTF_HOME + 
				";\nversão: $VERSION=" + 
				this.textofala.tts_versao() + 
				"; \ncanal de conversão @@LOCAL_CHANNEL=" + 
				this.canal;
		
		System.out.println(splash_txt);
		
		if(!this.textofala.tts_mensagemErro().isEmpty()){
			System.err.println(this.textofala.tts_mensagemErro()+"\n");
		}
	}
	
	@Override
	protected void finalize() throws  Throwable{
		// TODO Auto-generated method stub
		if(this.textofala != null){ this.textofala.tts_finaliza(); }
		super.finalize();
	}
	
	public boolean converter(String texto, String nomeDestino) throws FileNotFoundException, IOException{
		PointerByReference dataBuffer = new PointerByReference();
		NativeLong sinteze = this.textofala.tts_sintetizaTexto_mm(new NativeLong(canal), texto, dataBuffer);
		
		/// Tratamento de erro.
		if(sinteze.longValue() < 0){
			System.err.println(this.textofala.tts_mensagemErro()+"\n");
			return false;
		}
		Pointer ponteiro = dataBuffer.getValue();
		
		BufferedOutputStream audioFile = new BufferedOutputStream(new FileOutputStream(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + nomeDestino +".wav")));
		
		for(byte b : ponteiro.getByteArray(0, sinteze.intValue())){
			audioFile.write(b);
		}
		audioFile.close();
		System.out.println("Convertido para " + nomeDestino);
		System.err.println("\t * " + textofala.tts_mensagemErro());
		return true;
	}
	public String getError(){return textofala.tts_mensagemErro();}
	
}
