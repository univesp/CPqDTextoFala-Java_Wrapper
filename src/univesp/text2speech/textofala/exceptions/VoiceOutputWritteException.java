package univesp.text2speech.textofala.exceptions;
@SuppressWarnings("serial")
public class VoiceOutputWritteException extends Exception {
	public String getMessage(){
		return "Unable to write to output file voice (status: -15)";
	}
}