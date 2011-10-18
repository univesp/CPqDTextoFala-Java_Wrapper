package univesp.text2speech.textofala.exceptions;

@SuppressWarnings("serial")
public class FileInputIsNullOrNoExistException extends Exception {
	public String getMessage(){
		return "File with the text entry is empty or does not exist. (status: -7)";
	}
}
