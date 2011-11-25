package univesp;

import java.io.File;
import java.io.IOException;

import univesp.text2speech.Text2Speech;
import univesp.text2speech.textofala.TextoFala;
@SuppressWarnings("unused")
public class Text2SpeechTeste {
	private TextoFala t2s = new TextoFala();
	private void converter(String texto, String nome) throws IOException, InterruptedException{
		System.out.println("[FILE]: " + t2s.converter(texto, nome));
	}
	
	
	public static void main(String[] params) throws IOException, InterruptedException{
		TextoFala app = new TextoFala();
		System.out.println(app.versao());
		System.out.println(app.converter("Sucesso, isso é um teste mostrando que o software esté ok! \\pause{500} \\pitch{160} Nossa que legal! \\pitch{100} \\pause{50}", "sucesso"));
		System.out.println("FIM");
	}
}
