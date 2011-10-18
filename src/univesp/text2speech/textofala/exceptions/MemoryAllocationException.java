package univesp.text2speech.textofala.exceptions;

@SuppressWarnings("serial")
public class MemoryAllocationException extends Exception {
	public String getMessage(){
		return "Could not allocate memory for loading resources from text to speech converter (status: -12)";
	}
}
