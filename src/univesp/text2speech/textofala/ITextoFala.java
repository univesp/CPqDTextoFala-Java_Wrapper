package univesp.text2speech.textofala;

import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.PointerByReference;

/**
 * Interface para acessar  a biblioteca do texto fala (ctf.dll@windows | libcpqdtf.so@linux).
 * TODO Inserir texto de copyright do CPqD e TrendMark do Texto-FalaÂ©
 * TODO Criar o arquivo de configuraÃ§Ã£o ctf.ini
 * TODO Adicionar o apÃªndice I
 * TODO Terminar documentaÃ§Ã£o da LIB
 */
public interface ITextoFala extends Library{
	
	/**
	 * Inicializa a biblioteca Texto-FalaÂ© (ctf.dll | libcpqdtf.so), 
	 * devendo ser acionada antes de qualquer chamada Ã s funÃ§Ãµes de sÃ­ntese e de controle de canais.
	 * 
	 * Essa funÃ§Ã£o retorna um inteiro longo. Se o valor retornado for igual a zero, entÃ£o a inicializaÃ§Ã£o foi bem sucedida. 
	 * Se o valor retornado for diferente de zero, entÃ£o o processo de inicializaÃ§Ã£o falhou. 
	 * Nesse caso, o valor retornado corresponde a um cÃ³digo de erro que indica o problema ocorrido durante o processo de inicializaÃ§Ã£o. 
	 * A lista de possÃ­veis erros estÃ¡ no ApÃªndice I, ao final deste documento.
	 * 
	 * TODO Criar Exception.
	 * TODO Modificar este mÃ©todo para lanÃ§ar excessÃ£o de inicializaÃ§Ã£o.
	 * 
	 * @return long
	 */
	NativeLong tts_inicializa();
	
	/**
	 * Finaliza a biblioteca Texto-FalaÂ©, devendo ser chamada quando a aplicaÃ§Ã£o nÃ£o mais necessitar das funÃ§Ãµes da biblioteca.
	 */
	void tts_finaliza();
	
	/**
	 * Aloca um novo canal de conversÃ£o Texto-FalaÂ©.
	 * Essa funÃ§Ã£o retorna um inteiro longo. 
	 * Se o valor retornado for maior ou igual a zero, entÃ£o a alocaÃ§Ã£o foi bem sucedida e o valor retornado corresponde ao handle do novo canal. 
	 * Esse handle deve ser passado como parÃ¢metro para as outras funÃ§Ãµes de conversÃ£o texto-fala. 
	 * Se o valor retornado for menor que zero, entÃ£o o processo de alocaÃ§Ã£o falhou. 
	 * Nesse caso, o valor retornado corresponde a um cÃ³digo de erro que indica o problema ocorrido durante o processo de alocaÃ§Ã£o. 
	 * A lista de possÃ­veis erros estÃ¡ no ApÃªndice I, ao final deste documento.
	 * 
	 * TODO Criar Exception.
	 * TODO Modificar este mÃ©todo para lanÃ§ar excessÃ£o de inicializaÃ§Ã£o.
	 * 
	 * @return long
	 */
	NativeLong tts_alocaCanal();
	
	/**
	 * Desaloca um canal de conversÃ£o texto-fala previamente alocado atravÃ©s de 
	 * tts_alocaCanal(), devendo ser chamada sempre que um dado canal nÃ£o for mais utilizado pela aplicaÃ§Ã£o.
	 * @param canal 
	 */
	void tts_desalocaCanal(long canal);
	
	/**
	 * Obtem os dados de configuraÃ§Ã£o de sÃ­ntese do Texto-FalaÂ©.
	 */
	void tts_obtemConfiguracaoSintese();
	NativeLong tts_obtemConfiguracaoSintese(
			NativeLong canal,
			byte[] codificacaoAudio,
			byte[] taxaAmostragem,
			byte[] numeroCanaisAudio,
			byte[] volume,
			byte[] ritmo,
			byte[] entonacao,
			byte[] codificacaoTexto,
			byte[] pausaInicial,
			byte[] pausaFinal,
			byte[] cabecalhoArquivo,
			byte[] cabecalhoMemoria
		);
	
	/**
	 * Ajusta a configuraÃ§Ã£o de sÃ­ntese do Texto-FalaÂ©.
	 * O arquivo de configuraÃ§Ã£o da biblioteca Texto-FalaÂ©, a configuraÃ§Ã£o default de sÃ­ntese do Texto Fala pode ser alterada atravÃ©s do arquivo de inicializaÃ§Ã£o (â€œctf.iniâ€�).
	 * A configuraÃ§Ã£o de sÃ­ntese pode tambÃ©m ser alterada de forma dinÃ¢mica, podendo-se inclusive especificar configuraÃ§Ãµes diferentes para cada canal de sÃ­ntese. 
	 * Todos os parÃ¢metros definidos no arquivo de configuraÃ§Ã£o podem ser alterados, com exceÃ§Ã£o dos diretÃ³rios pathTTS, pathDat e pathReport.
	 * 
	 * @param canal : Ã‰ o canal de sÃ­ntese ao qual se aplica a configuraÃ§Ã£o.
	 * @param codificacaoAudio
	 * @param taxaAmostragem
	 * @param numeroCanaisAudio
	 * @param volume
	 * @param ritmo
	 * @param entonacao
	 * @param codificacaoTexto
	 * @param pausaInicial
	 * @param cabecalhoArquivo
	 * @param cabecalhoMemoria
	 * 
	 * @return long
	 */
	long tts_ajustaConfiguracaoSintese(
			long canal, 
			long codificacaoAudio, 
			long taxaAmostragem, 
			long numeroCanaisAudio, 
			long volume, 
			long ritmo,	
			long entonacao, 
			long codificacaoTexto, 
			long pausaInicial, 
			long pausaFinal, 
			long cabecalhoArquivo, 
			long cabecalhoMemoria
		);
	
	/**
	 * Recebe um arquivo texto padrÃ£o em formato ASCII como entrada e gera como saÃ­da um arquivo contendo sinal de fala. 
	 * Dependendo da versÃ£o da biblioteca utilizada, o arquivo gerado estarÃ¡ no formato PCM linear, lei-A ou lei-m  (com ou sem cabeÃ§alho WAV).
	 */
	void tts_sintetizaTexto_ff();
	
	/**
	 * Recebe uma cadeia de caracteres como entrada e gera como saÃ­da um arquivo contendo sinal de fala. 
	 * Dependendo da versÃ£o da biblioteca utilizada, o arquivo gerado estarÃ¡ no formato PCM linear, lei-A ou lei-m  (com ou sem cabeÃ§alho WAV).
	 */
	NativeLong tts_sintetizaTexto_mf(NativeLong canal, String texto, byte[] outputFile);
	NativeLong tts_sintetizaTexto_mf(NativeLong canal, String texto, String[] outputFile);
	NativeLong tts_sintetizaTexto_mf(NativeLong canal, String texto, String outputFile);
	
	/**
	 * Recebe um arquivo texto padrÃ£o em formato ASCII como entrada e produz amostras de fala como saÃ­da. 
	 * Essas amostras serÃ£o escritas em uma Ã¡rea de memÃ³ria alocada pela prÃ³pria biblioteca Texto-FalaÂ©. 
	 * Dependendo da versÃ£o da biblioteca utilizada, o sinal de fala produzido estarÃ¡ no formato PCM linear, lei-A ou lei-m. (com ou sem cabeÃ§alho WAV).
	 * A Ã¡rea de memÃ³ria alocada para o sinal pode ser liberada atravÃ©s da funÃ§Ã£o tts_libera_m().
	 */
	void tts_sintetizaTexto_fm();
	
	/**
	 * Recebe uma cadeia de caracteres como entrada e produz amostras de fala como saÃ­da. 
	 * Essas amostras serÃ£o escritas em uma Ã¡rea de memÃ³ria alocada pela prÃ³pria biblioteca Texto-FalaÂ©.
	 * Dependendo da versÃ£o da biblioteca utilizada, o sinal de fala produzido estarÃ¡ no formato PCM linear, lei-A ou lei-m. (com ou sem cabeÃ§alho WAV).
	 * A Ã¡rea de memÃ³ria alocada para o sinal pode ser liberada atravÃ©s da funÃ§Ã£o tts_libera_m().
	 */
	NativeLong tts_sintetizaTexto_mm(NativeLong ch, String texto, PointerByReference buffVoz);
	NativeLong tts_sintetizaTexto_mm(NativeLong ch, String texto, String[] buffVoz);
	
	/**
	 * Recebe um arquivo texto padrÃ£o em formatoASCII como entrada e gera como saÃ­da um arquivo contendo sinal de fala.
	 * Diferentemente da funÃ§Ã£o tts_sintetizaTexto_ff(), tts_sintetizaSentenca_ff() processa uma Ãºnica sentenÃ§a do texto de entrada. 
	 * Dependendo da versÃ£o da biblioteca utilizada, o arquivo gerado estarÃ¡ no formato PCM linear, lei-A ou lei-m  (com ou sem cabeÃ§alho WAV).
	 */
	void tts_sintetizaSentenca_ff();
	
	/**
	 * Recebe uma cadeia de caracteres como entrada e gera como saÃ­da um arquivo contendo sinal de fala. 
	 * Diferentemente da funÃ§Ã£o tts_sintetizaTexto_mf(), tts_sintetizaSentenca_mf() processa uma Ãºnica sentenÃ§a do texto de entrada. 
	 * Dependendo da versÃ£o da biblioteca utilizada, o arquivo gerado estarÃ¡ no formato PCM linear, lei-A ou lei-m (com ou sem cabeÃ§alho WAV).
	 */
	String tts_sintetizaSentenca_mf();
	
	/**
	 * Recebe um arquivo texto padrÃ£o em formato ASCII como entrada e produz amostras de fala como saÃ­da. 
	 * Essas amostras serÃ£o escritas em uma Ã¡rea de memÃ³ria alocada pela prÃ³pria biblioteca Texto-FalaÂ©. 
	 * Diferentemente da funÃ§Ã£o tts_sintetizaTexto_fm(), tts_sintetizaSentenca_fm() processa uma Ãºnica sentenÃ§a do texto de entrada. 
	 * Dependendo da versÃ£o da biblioteca utilizada, o sinal de fala produzido estarÃ¡ no formato PCM linear, lei-A ou lei-m. (com ou sem cabeÃ§alho WAV). 
	 * A Ã¡rea de memÃ³ria alocada para o sinal pode ser liberada atravÃ©s da funÃ§Ã£o tts_libera_m().
	 */
	void tts_sintetizaSentenca_fm();
	
	/**
	 * Recebe uma cadeia de caracteres como entrada e produz amostras de fala como saÃ­da. 
	 * Essas amostras serÃ£o escritas em uma Ã¡rea de memÃ³ria alocada pela prÃ³pria biblioteca Texto-FalaÂ©. 
	 * Diferentemente da funÃ§Ã£o tts_sintetizaTexto_mm(), tts_sintetizaSentenca_mm() processa uma Ãºnica sentenÃ§a do texto de entrada. 
	 * Dependendo da versÃ£o da biblioteca utilizada, o sinal de fala produzido estarÃ¡ no formato PCM linear, lei-A ou lei-m. (com ou sem cabeÃ§alho WAV). 
	 * A Ã¡rea de memÃ³ria alocada para o sinal pode ser liberada atravÃ©s da funÃ§Ã£o tts_libera_m().
	 */
	void tts_sintetizaSentenca_mm();
	
	/**
	 * Retorna um ponteiro para uma cadeia de caracteres contendo a mensagem de erro correspondente ao Ãºltimo erro ocorrido em uma das funÃ§Ãµes da biblioteca Texto-FalaÂ©. 
	 */
	String tts_mensagemErro();
	
	/**
	 * Retorna um ponteiro para uma cadeia de caracteres contendo a versÃ£o da biblioteca Texto-FalaÂ©.
	 */
	String  tts_versao();
	
	/**
	 * Libera uma Ã¡rea de memÃ³ria alocada internamente Ã  biblioteca Texto-FalaÂ©.
	 */
	void tts_libera_m();
	
	/**
	 * Apaga um arquivo gerado pela biblioteca Texto-FalaÂ©.
	 */
	void tts_libera_f();
}
