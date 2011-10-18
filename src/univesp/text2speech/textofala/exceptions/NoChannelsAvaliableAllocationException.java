package univesp.text2speech.textofala.exceptions;
@SuppressWarnings("serial")
public class NoChannelsAvaliableAllocationException extends Exception { public String getMessage(){ return "Error in channel allocation (there are no channels available) (Status: -10)"; } }