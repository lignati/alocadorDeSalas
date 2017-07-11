package dominio;

import java.util.*;
import java.util.Iterator;
import java.util.Set;

public class Sala {
	
	int capacidade;
	boolean disponivel;
	
	String tipo;
	String id;
	Map<String, Ficha> agenda;
	Map<Integer, Boolean> recurso;
	String note;
	
	
	public Sala(int capacidade, boolean disponivel, String tipo, String id, Map<Integer, Boolean> recurso)
	{
		this.capacidade = capacidade;
		this.disponivel = disponivel;
		this.tipo = tipo;
		this.id = id;
		this.agenda = new HashMap <String,  Ficha>();
		this.recurso = recurso;
		
	}
	
	public boolean getIntegerRecurso(int tipoRecurso)
	{
		if (recurso.get(tipoRecurso) == true)
			return true;
		
		return false;
	}
	
	public int getNumeroRecursos()
	{
		int cont = 0;
		Set<Integer> chaves = this.recurso.keySet();
		
		for(Iterator<Integer> iterator = chaves.iterator(); iterator.hasNext();)
		{
			Integer chave = iterator.next();
			Boolean validade = (Boolean)this.recurso.get(chave);
			if(validade == true)
				cont++;
		}
		return cont;
	}
	
	public String getIDSala()
	{
		return this.id;
	}
	
	public Map<Integer, Boolean> getRecurso()
	{
		return this.recurso;
	}
	
	public Map<String, Ficha> getAgenda()
	{
		return agenda;
	}

	public void imprimeAgenda(){
		Set<String> chaves = this.agenda.keySet();
		for (Iterator<String> iterator = chaves.iterator(); iterator.hasNext();)
		{
			String chave = iterator.next();
			if(chave != null)
				System.out.println(agenda.get(chave).toString() + this.id);
		}
		for (String key: agenda.keySet()){
			System.out.println(agenda.get(key).toString() + "\n");


		} 
		
	}
	

}


