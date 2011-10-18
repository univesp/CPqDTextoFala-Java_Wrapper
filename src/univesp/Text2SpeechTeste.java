package univesp;

import java.io.File;

import univesp.text2speech.Text2Speech;
import univesp.text2speech.textofala.TextoFala;
@SuppressWarnings("unused")
public class Text2SpeechTeste {
	private TextoFala t2s = new TextoFala();
	private void converter(String texto){
		System.out.println("[FILE]: " + t2s.converter(texto));
	}
	
	public static void main(String[] params){
		new Text2SpeechTeste().converter("Teste Bem sucedido!");
	}
}
