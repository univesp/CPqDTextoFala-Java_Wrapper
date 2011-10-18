package univesp.text2speech.textofala.exceptions;
@SuppressWarnings("serial")
public class TextoFalaFinalizeException extends Exception{ public String getMessage(){ return "Attempt to finalize text to speech converter nonexistent (Status: -9)"; } }