package domain;
import java.util.*;

public class Alocador {
	List<Predio> predios;
	List<Disciplina> disciplinas;
	List<Ficha> fichas;
	
	
	public Alocador(List<Predio> predios, List<Disciplina> disciplinas)
	{
		this.predios = predios;
		this.disciplinas = disciplinas;
		this.fichas = new ArrayList <Ficha>();
	}
	
	public void ordenaPrioridadesSala()
	{
		
		
		for(int k =0; k < predios.size(); k++){
			for( int i=this.predios.get(k).getSalas().size(); i>=1; i--){
				for(int j=1; j<i; j++){
					if(this.predios.get(k).getSalas().get(j-1).getNumeroRecursos() < this.predios.get(k).getSalas().get(j).getNumeroRecursos()){
						Collections.swap(this.predios.get(k).getSalas(), j, j-1);
					}
				}
		       }
        }
	
	}
	

	
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
		
		for(int i = 0; i< disciplinaAloc.size(); i++){

			turmaAloc = disciplinaAloc.get(i).getTurmas();
			for(int j = 0; j < turmaAloc.size();j++){
				horarioAloc = turmaAloc.get(j).getHorarios();
				for(int k = 0; k < horarioAloc.size() ; k++ ){
					novaFicha = new Ficha(turmaAloc.get(j).getProfessor(), 
										  turmaAloc.get(j).getNroAlunos(),
										  turmaAloc.get(j).getID(),
										  horarioAloc.get(k).getRequisitos(),
										  horarioAloc.get(k),
										  disciplinaAloc.get(i).getID());
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
					this.fichas.add(novaFicha);
					if(!horarioAloc.isEmpty())
						horarioAloc.remove(k);
				}	

			}	
		}
	}
	
	public void AlocaSala(){
		
		List<Sala> salasDoPredio;
		List<Sala> pioresSalas;
		List<Integer> referenciaSala;
		int index = 0;
		pioresSalas = new ArrayList<Sala>();
		referenciaSala = new ArrayList<Integer>();
		for(int i = 0; i < this.fichas.size(); i++){
			for(int j = 0; j < this.predios.size(); j++){
				salasDoPredio = this.predios.get(j).getSalas();
				index = 0;
				while(salasDoPredio.get(index).getAgenda().containsKey(this.fichas.get(i).getHorario().toString()) == true){
					index++;

				}
				
				pioresSalas.add(j, salasDoPredio.get(index));
				referenciaSala.add(j,j);
			}
			Sala Primeira = pioresSalas.get(0);
			
			for(int k = 1; k < pioresSalas.size(); k++){
				
				if(pioresSalas.get(k).getNumeroRecursos() < Primeira.getNumeroRecursos()){
					Collections.swap(pioresSalas, 0, k);
					Collections.swap(referenciaSala,0,k);
				}
			}
			int l;
			for( l = 0; l < pioresSalas.size(); l++){
				
				if(this.compareMaps(this.fichas.get(i).getRequisitos(), pioresSalas.get(l).getRecurso()) == true){


					pioresSalas.get(l).agenda.put(this.fichas.get(i).horario.toString(), this.fichas.get(i));
		
					break;
				}
			}
			if(l == pioresSalas.size()){
				for(int ii = 0; ii < this.predios.size(); ii++){
					for(int jj = 0; jj < this.predios.get(ii).getSalas().size(); jj++){
						
						if(this.compareMaps(this.fichas.get(i).getRequisitos(), this.predios.get(ii).getSalas().get(jj).getRecurso() )&&(this.predios.get(ii).getSalas().get(jj).getAgenda().containsKey(this.fichas.get(i).getHorario().toString()) == false)){
							
							this.predios.get(ii).salas.get(jj).agenda.put(this.fichas.get(i).horario.toString(), this.fichas.get(i));
							break;
						}
					}				
				}
	
			}
			else{
				int g = 0;
				while(!(pioresSalas.get(l).getIDSala().equals( this.predios.get(referenciaSala.get(l)).getSalas().get(g).getIDSala()))){
					g++;
	
				}
	
				this.predios.get(referenciaSala.get(l)).salas.set(g, pioresSalas.get(l));
			}
			
		}
	}
	public List <Predio> getPredios(){
		
		 
		
			return predios;
		
	}
	public List<Ficha> getFichas(){
		
		return this.fichas;
		
		
		
	}
	public boolean compareMaps(Map<Integer, Boolean> map1, Map<Integer, Boolean> map2){
		
		Set<Integer> chaves1 = map1.keySet();
		Set<Integer> chaves2 = map2.keySet();
		
		List<Integer> lista1 = new ArrayList<Integer>();
		List<Integer> lista2 = new ArrayList<Integer>();
		
		for(Integer chave1 : chaves1){
			if (map1.get(chave1) == true){
				lista1.add(chave1);
			}
		}
		
		for(Integer chave2 : chaves2){
			if (map2.get(chave2) == true){
				lista2.add(chave2);
			}
		}
		
		for(int i=0; i<lista2.size() && i < lista1.size(); i++){
			if(lista2.contains(lista1.get(i)) == false)
				return false;
		}
		return true;
	}

}
