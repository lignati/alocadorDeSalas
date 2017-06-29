package dominio;
import java.util.*;
import dominio.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Alocador {
	
	List<Predio> predios;
	List<Disciplina> disciplinas;
	Map<Integer, String> recursos;
	List<Ficha> fichas;
	
	
	public Alocador(List<Predio> predios, List<Disciplina> disciplinas, Map<Integer, String> recursos)
	{
		this.predios = predios;
		this.disciplinas = disciplinas;
		this.recursos = recursos;
		this.fichas = new ArrayList <Ficha>();
	}
	
	public void ordenaPrioridadesSala()
	{
		
		//M�TODO BOLHA UHUL
		for(int k =0; k < predios.size(); k++){
			for( int i=this.predios.get(k).getSalas().size(); i>=1; i--){
				for(int j=1; j<i; j++){
					if(this.predios.get(k).getSalas().get(j-1).getNumeroRecursos() > this.predios.get(k).getSalas().get(j).getNumeroRecursos()){
						Collections.swap(this.predios.get(k).getSalas(), j, j-1);
					}
				}
		      }
        }
	
	}
	
	
	/*public boolean alocaSala()
	{
		
		return;
	}*/
	
	public void verificaMesmoProfessor(String professor)
	{
		
		
	}
	
	public boolean combinaTurmaSala(Sala sala, String professor, Horario horario, Disciplina disciplina)
	{	
		if(disciplina.getCapacidadeMesmoHorario(horario, professor) < sala.capacidade)
			return true;
		else
			return false;
	}
	public void montaFichas(){
		List<Disciplina> disciplinaAloc;
		List<Turma> turmaAloc;
		List<Horario> horarioAloc;
		disciplinaAloc = disciplinas;
		Ficha novaFicha;
		
		
		for(int i =0; i< disciplinaAloc.size(); i++){
			
			turmaAloc = disciplinaAloc.get(i).getTurmas();
			for(int j = 0; j < turmaAloc.size();j++){
				horarioAloc = turmaAloc.get(j).getHorarios();
				for(int k = 0; k < horarioAloc.size() ; k++ ){
					
					novaFicha = new Ficha(turmaAloc.get(j).getProfessor(), 
										  turmaAloc.get(j).getNroAlunos(),
										  turmaAloc.get(j).getID(),
										  horarioAloc.get(j).getRequisitos(),
										  horarioAloc.get(k),disciplinaAloc.get(i).getID());
					//:'(
					
					for(int f = 0; f < turmaAloc.size(); f++){
						for(int g = 0; g < horarioAloc.size();g ++){
							if(
							(turmaAloc.get(f).getProfessor().equals(turmaAloc.get(j).getProfessor())) &&      
							(horarioAloc.get(g).getStringHorario().equals(horarioAloc.get(k).getStringHorario())) &&
							!(turmaAloc.get(f).getID().equals(turmaAloc.get(j).getID()))){
								
								novaFicha.addTurma(turmaAloc.get(f).getID(), turmaAloc.get(f).getNroAlunos());
								
							}
							if (turmaAloc.get(f).getHorarios().isEmpty() == true){
								turmaAloc.remove(f);
							}
							else{
								turmaAloc.get(f).getHorarios().remove(g);
							}
						}
					}
					horarioAloc.remove(k);
				}
				
			}
			
			
			
			
		}
		
		
		
	}
}
