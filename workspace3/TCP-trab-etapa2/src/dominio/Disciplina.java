package dominio;

import java.util.List;

public class Disciplina {
	
	String id;
	String nome;
	List<Turma> turmas;
	List<Integer> requisitos;
	
	
	public Disciplina(){};
	
	public void getTurmasMesmoHorario(){}
	
	public int getCapacidadeMesmoHorario(){}
	
	public List<int> getRequisitos(){}

}
