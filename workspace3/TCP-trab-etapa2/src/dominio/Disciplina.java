package dominio;

import java.util.*;

public class Disciplina {
	
	String id;
	String nome;
	List<Turma> turmas;
	List<Integer> requisitos;
	
	
	public Disciplina(String nome, String id,List<Turma> turmas, List<Integer> requisitos){
		
		this.nome   = nome;
		this.id     = id;
		this.turmas = new ArrayList<Turma>();
		this.requisitos = new ArrayList<Integer>();
	};
	
	public Map<Horario,Turma> getTurmasMesmoHorario(List<Turma> turmas){
		Map<Horario,Turma> mapaHorarios =  new HashMap<Horario,Turma>();
		for (int elemento = 0;elemento < turmas.size();elemento++  )	{
			for(int j = 0; j < turmas.get(elemento).getHorarios().size();j++){
				mapaHorarios.put(turmas.get(elemento).getHorarios().get(j),elemento);
			
			}
		}
			
		
		
	}
	
	public int getCapacidadeMesmoHorario(){}
	
	public List<int> getRequisitos(){}
	
	

}
