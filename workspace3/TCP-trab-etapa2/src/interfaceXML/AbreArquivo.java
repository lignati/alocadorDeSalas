package interfaceXML;
import java.util.*;
import dominio.*;
public class AbreArquivo {
	String              nome;
	List<Predio>        predios;
	List<Disciplina>    disciplinas;
	Map<Integer,String> idRecursos;
	
	public AbreArquivo(String nome){
		
		this.predios     =  new ArrayList <Predio>();
		this.disciplinas =  new ArrayList <Disciplina>();
		this.idRecursos  =  new HashMap <Integer,String>(); 
		
		this.nome = nome;
	}
	
	public void montaListaPredios(){
		
		return;
	}
}
