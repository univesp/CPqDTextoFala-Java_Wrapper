package univesp.text2speech.textofala.exceptions;
@SuppressWarnings("serial")
public class MemoryAllocationForTextException extends Exception {
	public String getMessage(){return "Could not allocate memory for text input (status: -13)";}
}