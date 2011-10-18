package univesp.text2speech.textofala.exceptions;
@SuppressWarnings("serial")
public class OpenDebugFileException extends Exception { public String getMessage(){ return "Could not open debug file. (status: -8)"; } }