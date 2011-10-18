package univesp.text2speech.textofala.exceptions;

@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception {
	public String getMessage(){
		return "The password passed to function is invalid (status: -11)";
	}
}
