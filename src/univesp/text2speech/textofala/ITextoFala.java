package univesp.text2speech.textofala;

import univesp.text2speech.textofala.exceptions.*;

import com.sun.jna.Library;

/**
 * Interface para acessar  a biblioteca do texto fala (ctf.dll@windows | libcpqdtf.so@linux).
 * TODO Inserir texto de copyright do CPqD e TrendMark do Texto-Fala©
 * TODO Criar o arquivo de configuração ctf.ini
 * TODO Adicionar o apêndice I
 * TODO Terminar documentação da LIB
 */
public interface ITextoFala extends Library{
	
	/**
	 * Inicializa a biblioteca Texto-Fala© (ctf.dll | libcpqdtf.so), 
	 * devendo ser acionada antes de qualquer chamada às funções de síntese e de controle de canais.
	 * 
	 * Essa função retorna um inteiro longo. Se o valor retornado for igual a zero, então a inicialização foi bem sucedida. 
	 * Se o valor retornado for diferente de zero, então o processo de inicialização falhou. 
	 * Nesse caso, o valor retornado corresponde a um código de erro que indica o problema ocorrido durante o processo de inicialização. 
	 * A lista de possíveis erros está no Apêndice I, ao final deste documento.
	 * 
	 * TODO Criar Exception.
	 * TODO Modificar este método para lançar excessão de inicialização.
	 * 
	 * @return long
	 */
	long tts_inicializa();
	
	/**
	 * Finaliza a biblioteca Texto-Fala©, devendo ser chamada quando a aplicação não mais necessitar das funções da biblioteca.
	 */
	void tts_finaliza() throws TextoFalaFinalizeException;
	
	/**
	 * Aloca um novo canal de conversão Texto-Fala©.
	 * Essa função retorna um inteiro longo. 
	 * Se o valor retornado for maior ou igual a zero, então a alocação foi bem sucedida e o valor retornado corresponde ao handle do novo canal. 
	 * Esse handle deve ser passado como parâmetro para as outras funções de conversão texto-fala. 
	 * Se o valor retornado for menor que zero, então o processo de alocação falhou. 
	 * Nesse caso, o valor retornado corresponde a um código de erro que indica o problema ocorrido durante o processo de alocação. 
	 * A lista de possíveis erros está no Apêndice I, ao final deste documento.
	 * 
	 * TODO Criar Exception.
	 * TODO Modificar este método para lançar excessão de inicialização.
	 * 
	 * @return long
	 */
	long tts_alocaCanal();
	
	/**
	 * Desaloca um canal de conversão texto-fala previamente alocado através de 
	 * tts_alocaCanal(), devendo ser chamada sempre que um dado canal não for mais utilizado pela aplicação.
	 * @param canal 
	 */
	void tts_desalocaCanal(long canal);
	
	/**
	 * Obtem os dados de configuração de síntese do Texto-Fala©.
	 */
	void tts_obtemConfiguracaoSintese();
	
	/**
	 * Ajusta a configuração de síntese do Texto-Fala©.
	 * O arquivo de configuração da biblioteca Texto-Fala©, a configuração default de síntese do Texto Fala pode ser alterada através do arquivo de inicialização (“ctf.ini”).
	 * A configuração de síntese pode também ser alterada de forma dinâmica, podendo-se inclusive especificar configurações diferentes para cada canal de síntese. 
	 * Todos os parâmetros definidos no arquivo de configuração podem ser alterados, com exceção dos diretórios pathTTS, pathDat e pathReport.
	 * 
	 * @param canal : É o canal de síntese ao qual se aplica a configuração.
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
	 * Recebe um arquivo texto padrão em formato ASCII como entrada e gera como saída um arquivo contendo sinal de fala. 
	 * Dependendo da versão da biblioteca utilizada, o arquivo gerado estará no formato PCM linear, lei-A ou lei-m  (com ou sem cabeçalho WAV).
	 */
	void tts_sintetizaTexto_ff();
	
	/**
	 * Recebe uma cadeia de caracteres como entrada e gera como saída um arquivo contendo sinal de fala. 
	 * Dependendo da versão da biblioteca utilizada, o arquivo gerado estará no formato PCM linear, lei-A ou lei-m  (com ou sem cabeçalho WAV).
	 */
	void tts_sintetizaTexto_mf(long canal, String texto, String outputFile);
	
	/**
	 * Recebe um arquivo texto padrão em formato ASCII como entrada e produz amostras de fala como saída. 
	 * Essas amostras serão escritas em uma área de memória alocada pela própria biblioteca Texto-Fala©. 
	 * Dependendo da versão da biblioteca utilizada, o sinal de fala produzido estará no formato PCM linear, lei-A ou lei-m. (com ou sem cabeçalho WAV).
	 * A área de memória alocada para o sinal pode ser liberada através da função tts_libera_m().
	 */
	void tts_sintetizaTexto_fm();
	
	/**
	 * Recebe uma cadeia de caracteres como entrada e produz amostras de fala como saída. 
	 * Essas amostras serão escritas em uma área de memória alocada pela própria biblioteca Texto-Fala©.
	 * Dependendo da versão da biblioteca utilizada, o sinal de fala produzido estará no formato PCM linear, lei-A ou lei-m. (com ou sem cabeçalho WAV).
	 * A área de memória alocada para o sinal pode ser liberada através da função tts_libera_m().
	 */
	void tts_sintetizaTexto_mm();
	
	/**
	 * Recebe um arquivo texto padrão em formatoASCII como entrada e gera como saída um arquivo contendo sinal de fala.
	 * Diferentemente da função tts_sintetizaTexto_ff(), tts_sintetizaSentenca_ff() processa uma única sentença do texto de entrada. 
	 * Dependendo da versão da biblioteca utilizada, o arquivo gerado estará no formato PCM linear, lei-A ou lei-m  (com ou sem cabeçalho WAV).
	 */
	void tts_sintetizaSentenca_ff();
	
	/**
	 * Recebe uma cadeia de caracteres como entrada e gera como saída um arquivo contendo sinal de fala. 
	 * Diferentemente da função tts_sintetizaTexto_mf(), tts_sintetizaSentenca_mf() processa uma única sentença do texto de entrada. 
	 * Dependendo da versão da biblioteca utilizada, o arquivo gerado estará no formato PCM linear, lei-A ou lei-m (com ou sem cabeçalho WAV).
	 */
	String tts_sintetizaSentenca_mf();
	
	/**
	 * Recebe um arquivo texto padrão em formato ASCII como entrada e produz amostras de fala como saída. 
	 * Essas amostras serão escritas em uma área de memória alocada pela própria biblioteca Texto-Fala©. 
	 * Diferentemente da função tts_sintetizaTexto_fm(), tts_sintetizaSentenca_fm() processa uma única sentença do texto de entrada. 
	 * Dependendo da versão da biblioteca utilizada, o sinal de fala produzido estará no formato PCM linear, lei-A ou lei-m. (com ou sem cabeçalho WAV). 
	 * A área de memória alocada para o sinal pode ser liberada através da função tts_libera_m().
	 */
	void tts_sintetizaSentenca_fm();
	
	/**
	 * Recebe uma cadeia de caracteres como entrada e produz amostras de fala como saída. 
	 * Essas amostras serão escritas em uma área de memória alocada pela própria biblioteca Texto-Fala©. 
	 * Diferentemente da função tts_sintetizaTexto_mm(), tts_sintetizaSentenca_mm() processa uma única sentença do texto de entrada. 
	 * Dependendo da versão da biblioteca utilizada, o sinal de fala produzido estará no formato PCM linear, lei-A ou lei-m. (com ou sem cabeçalho WAV). 
	 * A área de memória alocada para o sinal pode ser liberada através da função tts_libera_m().
	 */
	void tts_sintetizaSentenca_mm();
	
	/**
	 * Retorna um ponteiro para uma cadeia de caracteres contendo a mensagem de erro correspondente ao último erro ocorrido em uma das funções da biblioteca Texto-Fala©. 
	 */
	void tts_mensagemErro();
	
	/**
	 * Retorna um ponteiro para uma cadeia de caracteres contendo a versão da biblioteca Texto-Fala©.
	 */
	String  tts_versao();
	
	/**
	 * Libera uma área de memória alocada internamente à biblioteca Texto-Fala©.
	 */
	void tts_libera_m();
	
	/**
	 * Apaga um arquivo gerado pela biblioteca Texto-Fala©.
	 */
	void tts_libera_f();
}
