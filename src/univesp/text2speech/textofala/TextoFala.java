package univesp.text2speech.textofala;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import univesp.text2speech.Text2Speech;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class TextoFala implements Text2Speech{
	
	private ITextoFala textofala = null;
	private Map<Long, String> canais = null;
	private String CTF_HOME = "";
	
	public TextoFala(){
		
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
		System.out.println("Biblioteca carregada de : " + this.CTF_HOME + ", versão: " + this.versao());
		
		this.canais = new HashMap<Long, String>();
	}
	
	public void __init__(){
		String EXT ; 
		
	}
	
	@Override
	protected void finalize() throws  Throwable{
		// TODO Auto-generated method stub
		if(!this.canais.isEmpty()){ for(long canal : this.canais.keySet()){ this.cfreeChannel(canal); } }
		if(this.textofala != null){ this.textofala.tts_finaliza(); }
		super.finalize();
	}
			
	private long callocChannel(){
		if(this.textofala == null){
			/// TODO: Add Exception
		}
		NativeLong canal = this.textofala.tts_alocaCanal();
		if(canal.longValue() <= 0){
			/// TODO: Add Exception
			System.err.println(this.textofala.tts_mensagemErro()+"\n");
		}
		System.out.println("[WARN]: alocado canal " + canal.longValue());
		return canal.longValue();
	}
	
	private void cfreeChannel(long canal){
		this.textofala.tts_desalocaCanal(canal);
	}
	
	public String versao(){
		return this.textofala.tts_versao();
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
			ret += new Byte(x).longValue();
		}
		
		return ret;
	}
	public String configuracao(long ch) throws UnsupportedEncodingException{
		if(ch < 0){
			String s = "Erro no canal: " + this.textofala.tts_mensagemErro();
			System.out.println(s);
			return  s;
		}
		System.out.println("Verificando a configura��o do canal " + ch);
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
		
		NativeLong codigoConfiguracao = this.textofala.tts_obtemConfiguracaoSintese(
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
		String m = "var Configuração_do_texto_fala: {";
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
	@SuppressWarnings({ "unused", "static-access" })
	public String converter(String s) throws IOException, InterruptedException{
		NativeLong canal = this.textofala.tts_alocaCanal();
		PointerByReference buff = new PointerByReference();
		try {
			this.configuracao(canal.longValue());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(this.textofala.tts_mensagemErro()+"\n");
		NativeLong sinteze;
		sinteze = this.textofala.tts_sintetizaTexto_mm(canal, s, buff);
		
		if(sinteze.intValue() < 0){
			System.err.println(this.textofala.tts_mensagemErro()+"\n");
		}
		Pointer ptr = buff.getValue();
		String fullfilename = System.getProperty("user.dir") + System.getProperty("file.separator") + "Sucesso.wav";
		System.out.println("SALVANDO EM " + fullfilename);
		File f = new File(fullfilename);
		FileOutputStream out = new FileOutputStream(f);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		
		for(byte b : ptr.getByteArray(0, sinteze.intValue())){
			bout.write(b);
		}
		
		bout.close();
		System.out.println("\nconvertendo com os seguintes parametros: --channel="+canal+" --text="+s+" --file="+buff);
			
		System.out.println("Sinteze is " + sinteze.longValue() + ":file => " + buff);
		return "";
	}
}