package univesp;

import java.io.File;
import java.io.IOException;

import univesp.text2speech.Text2Speech;
import univesp.text2speech.textofala.TextoFala;
@SuppressWarnings("unused")
public class Text2SpeechTeste {
	private TextoFala t2s = new TextoFala();
	private void converter(String texto) throws IOException{
		System.out.println("[FILE]: " + t2s.converter(texto));
	}
	
	
	public static void main(String[] params) throws IOException{
		TextoFala app = new TextoFala();
		System.out.println(app.versao());
		System.out.println(app.converter("Sucesso"));
		System.out.println("FIM");
	}
}
