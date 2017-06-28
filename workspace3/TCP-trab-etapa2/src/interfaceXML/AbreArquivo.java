package interfaceXML;
import java.util.*;
import dominio.*;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

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
	
	public Map<Integer, Boolean> montaRecursos(String recursos)
	{
		int nroRecurso;
		String aux;
		Map<Integer, Boolean> mapaRecursos;
		
		mapaRecursos = new HashMap<Integer, Boolean>();
		
		for(int i=0; i<recursos.length(); i+=3)
		{
			aux = recursos.substring(i, i+1);
			nroRecurso = Integer.parseInt(aux);
			
			mapaRecursos.put(nroRecurso, true);
		}
		
		return mapaRecursos;
	}
	public Hora montaHora(String entrada){
		int minutos;
		int horas;
		horas = Integer.valueOf(entrada.substring(0,2));
		minutos = Integer.valueOf(entrada.substring(3));
		Hora novaHora = new Hora(horas,minutos);
		
		return novaHora;
		
	}
	
	public List<Disciplina> montaListaDisciplinas(String nomeArquivo){
		  Disciplina novaDisciplina;
		  List<Disciplina> novaListaDisciplinas = new ArrayList <Disciplina>();
	      try {	
		         File inputFile = new File("nomeArquivo.xml");
		         DocumentBuilderFactory dbFactory 
		            = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         Document doc = dBuilder.parse(inputFile);
		         doc.getDocumentElement().normalize();
		         NodeList disciplinaXMLList = doc.getElementsByTagName("course");
		         for (int i = 0; i < disciplinaXMLList.getLength(); i++) {
		            Node nodoXMLDisciplina = disciplinaXMLList.item(i);
		            if (nodoXMLDisciplina.getNodeType() == Node.ELEMENT_NODE) {
		               Element elementoDisciplina = (Element) nodoXMLDisciplina;

		               novaDisciplina = new Disciplina(elementoDisciplina.getAttribute("name"), elementoDisciplina.getAttribute("id"));
		             
		              List<Turma> novaListaTurmas = new ArrayList <Turma>();
		              Turma novaTurma;
		  	         NodeList turmasXMLList = doc.getElementsByTagName("group");
			         for (int j = 0; j < turmasXMLList.getLength(); j++) {
			            Node nodoXMLTurma = turmasXMLList.item(j);
			            if (nodoXMLTurma.getNodeType() == Node.ELEMENT_NODE) {
			               Element elementoTurma = (Element) nodoXMLTurma;
			              
			               novaTurma = new Turma(elementoTurma.getAttribute("tecaher"),Integer.valueOf(elementoTurma.getAttribute("number_of_students")),elementoTurma.getAttribute("id"));
			               //
				              List<Horario> novaListaHorarios = new ArrayList <Horario>();
				              Horario novoHorario;
				  	         NodeList horariosXMLList = doc.getElementsByTagName("group");
					         for (int k = 0; k < horariosXMLList.getLength(); k++) {
					            Node nodoXMLHorario = horariosXMLList.item(j);
					            if (nodoXMLHorario.getNodeType() == Node.ELEMENT_NODE) {
					               Element elementoHorario = (Element) nodoXMLHorario;					               
					               novoHorario = new Horario(elementoHorario.getAttribute("weekday"), Integer.parseInt(elementoHorario.getAttribute("duration")),this.montaHora(elementoHorario.getAttribute("start_time")),montaRecursos(elementoHorario.getAttribute("feeature_ids")));
					               
					               novaListaHorarios.add(novoHorario);
					            }
					            
					        
					         }
					         //
			               novaListaTurmas.add(novaTurma);
			            }
			            
			        
			         }
			         novaDisciplina.setTurmas(novaListaTurmas);
			         novaListaDisciplinas.add(novaDisciplina);
		               
		            }
		         }
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		return novaListaDisciplinas;
	}
	public List<Predio> montaListaPredios(){
		  Predio novoPredio;
		  List<Predio> novaListaPredios = new ArrayList <Predio>();
	      try {	
		         File inputFile = new File("nomeArquivo.xml");
		         DocumentBuilderFactory dbFactory 
		            = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         Document doc = dBuilder.parse(inputFile);
		         doc.getDocumentElement().normalize();
		         NodeList predioXMLList = doc.getElementsByTagName("building");
		         for (int i = 0; i < predioXMLList.getLength(); i++) {
		            Node nodoXMLPredio = predioXMLList.item(i);
		            if (nodoXMLPredio.getNodeType() == Node.ELEMENT_NODE) {
		               Element elementoPredio = (Element) nodoXMLPredio;

		               novoPredio = new Predio(elementoPredio.getAttribute("id"));
		              List<Sala> novaListaSalas = new ArrayList <Sala>();
		              Sala novaSala;
		  	         NodeList salasXMLList = doc.getElementsByTagName("room");
			         for (int j = 0; j < salasXMLList.getLength(); j++) {
			            Node nodoXMLSala = salasXMLList.item(j);
			            if (nodoXMLSala.getNodeType() == Node.ELEMENT_NODE) {
			               Element elementoSala = (Element) nodoXMLSala;
			               novaSala = new Sala(Integer.valueOf(elementoSala.getAttribute("number_of_places")),
			            		   Boolean.parseBoolean(elementoSala.getAttribute("available_for_allocation")),
			            		   elementoSala.getAttribute("id"),elementoSala.getAttribute("note"),this.montaRecursos(elementoSala.getAttribute("feature_idsfeature_ids")));
			               
			               novaListaSalas.add(novaSala);
			            }
			            
			        
			         }
			         novoPredio.setPredios(novaListaSalas);
			         novaListaPredios.add(novoPredio);
		               
		            }
		         }
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		return novaListaPredios;
		
		
		
	}
}
