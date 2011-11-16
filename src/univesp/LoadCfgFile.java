package univesp;

import java.io.FileNotFoundException;
import java.util.Map;

import org.ho.yaml.Yaml;

public class LoadCfgFile {
	
	private Map<String, Map> CFG;
	
	public LoadCfgFile(){
		try {
			this.CFG = (Map<String, Map>) Yaml.load(new java.io.File(System.getProperty("user.dir") + System.getProperty("file.separator") + "config.yml"));
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Arquivo de configuração não encontrado. Ele era esperado em "+System.getProperty("user.dir") + System.getProperty("file.separator") + "config.yml");
		}
		
	}
	
	public Map getGroup(String key){
		return this.CFG.get(key); 
	}
	public String getProperty(String group, String key){
		return (String) getGroup(group).get(key);
	}
	
	
	
	
	public static void main(String [] params){
		LoadCfgFile cfg = new LoadCfgFile();
		System.out.println(cfg.getProperty("teste", "nome"));
	}
}
