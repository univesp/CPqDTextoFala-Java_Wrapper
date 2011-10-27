package univesp.text2speech.textofala;

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import univesp.text2speech.Text2Speech;
import univesp.text2speech.textofala.exceptions.TextoFalaFinalizeException;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
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
		System.out.println("Biblioteca carregada de : " + this.CTF_HOME + ", vers√£o: " + this.versao());
		
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
		NativeLong canal = this.CTFKernel.tts_alocaCanal();
		if(canal.longValue() <= 0){
			/// TODO: Add Exception
			System.err.println(this.CTFKernel.tts_mensagemErro()+"\n");
		}
		System.out.println("[WARN]: alocado canal " + canal.longValue());
		return canal.longValue();
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
	private long byte2long(byte[] b){
		long ret = 0;
		
		for(byte x : b){
			//System.out.println(":Byte => " + new Byte(x).longValue());
			ret += new Byte(x).longValue();
		}
		
		return ret;
	}
	public String configuracao(long ch) throws UnsupportedEncodingException{
		if(ch < 0){
			String s = "Erro no canal: " + this.CTFKernel.tts_mensagemErro();
			System.out.println(s);
			return  s;
		}
		System.out.println("Verificando a configuraÁ„o do canal " + ch);
		NativeLong canal = new NativeLong(ch); // Canal a ser analizado
		
		byte[] codificacaoAudio = 			new byte[1];
		byte[] taxaAmostragem = 			new byte[1];
		byte[] numeroCanaisAudio = 			new byte[1];
		byte[] volume = 					new byte[1];
		byte[] ritmo = 						new byte[1];
		byte[] entonacao = 					new byte[1];
		byte[] codificacaoTexto = 			new byte[1];
		byte[] pausaInicial = 				new byte[1];
		byte[] pausaFinal = 				new byte[1];
		byte[] cabecalhoArquivo = 			new byte[1];
		byte[] cabecalhoMemoria = 			new byte[1];
		
		NativeLong codigoConfiguracao = this.CTFKernel.tts_obtemConfiguracaoSintese(
			canal,
			codificacaoAudio,
			taxaAmostragem,
			numeroCanaisAudio,
			volume,
			ritmo,
			entonacao,
			codificacaoTexto,
			pausaInicial,
			pausaFinal,
			cabecalhoArquivo,
			cabecalhoMemoria
		);
		
		byte[] teste = {100, 0, 0};
		String m = "var ConfiguraÁ„o_do_texto_fala: {";
		m += "\n\tcanal : " + canal;
		m += "\n\tcodificacao: " + byte2long(codificacaoAudio);
		m += "\n\ttaxa:" + byte2long(taxaAmostragem);
		m += "\n\tnumeroCanais: " + byte2long(numeroCanaisAudio);
		m += "\n\tvolume: " + byte2long(volume);
		m += "\n\tritmo: " + byte2long(ritmo);
		m += "\n\tentonacao: " + byte2long(entonacao);
		m += "\n\tcodificacaoTexto: " + byte2long(codificacaoTexto);
		m += "\n\tPausaInicial: " + byte2long(pausaInicial);
		m += "\n\tpausaFinal: " + byte2long(pausaFinal);
		m += "\n\tcabecalhoArquivo: " + byte2long(cabecalhoArquivo);
		m += "\n\tcabecalhoMemoria: " + byte2long(cabecalhoMemoria);
		m += "\n\tcodigo: " + codigoConfiguracao;
		m += "\n};";
		System.out.println(m);
		System.out.println(":teste => " +byte2long(teste));
		return m;
		
	}
	@SuppressWarnings("unused")
	public String converter(String s) throws IOException{
		NativeLong canal = this.CTFKernel.tts_alocaCanal();
		//ByteByReference file = new ByteByReference();
		//byte[] buff = new byte[50];
		String[] buff = new String[50];
		//String buff = new String();
		try {
			this.configuracao(canal.longValue());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(this.CTFKernel.tts_mensagemErro()+"\n");
		NativeLong sinteze;
		sinteze = this.CTFKernel.tts_sintetizaTexto_mf(canal, s, buff);
		//sinteze = this.CTFKernel.tts_sintetizaTexto_mm(canal, s, buff);
		
		//PointerByReference buff = new PointerByReference();
		if(sinteze.intValue() < 0){
			System.err.println(this.CTFKernel.tts_mensagemErro()+"\n");
		}
		//Pointer ponteiro = buff.getPointer();
		
		//Pointer ponteiro_idx = buff.getValue();
		//System.out.println("TYPE => " + Pointer.nativeValue(ponteiro));
		//@SuppressWarnings("static-access")
		//byte [] buffer = ponteiro.getByteArray(0, ponteiro.SIZE);
		//System.out.println("FILE:");
		//for(byte b : buffer){
		//	System.out.println("\t"+new Byte(b).longValue());
		//}
		String fullfilename = System.getProperty("user.dir") + System.getProperty("file.separator") + "Sucesso.wav";
		System.out.println("SALVANDO EM " + fullfilename);
		File f = new File(fullfilename);
		FileOutputStream out = new FileOutputStream(f);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		/*
		for(String x : buff){
			if(x == null) {continue;}
			
			for(byte bite : x.getBytes("ASCII")){
				System.out.println(x + " : " + bite);
				bout.write(bite);
			}
		}
		*/
		bout.close();
		System.out.println("convertendo com os seguintes parametros: --channel="+canal+" --text="+s+" --file="+buff);
			
		//String f = buff;
		System.out.println("Sinteze is " + sinteze.longValue() + ":file => " + buff);
		return "";
	}
}