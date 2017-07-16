package file;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import dominio.*;
import org.raapi.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class LeArquivoExcel {
String nome;
	
	public LeArquivoExcel(String nome)
	{	
		this.nome = nome;
		System.out.println(nome);
	}
	
	
	
	public List<Feature> montaRecursos()
	{
		List<Feature> recursos = new ArrayList<Feature>();
		try {
			FileInputStream fs = new FileInputStream(nome);
			XSSFWorkbook wb = new XSSFWorkbook (fs);
		    
		    //pega dados da segunda planilha a que possui informacoes dos recursos
		    XSSFSheet planilha = wb.getSheetAt(1);
		    
		    XSSFRow linha;
		    XSSFCell celula;
		    String NomeRecurso, IDRecurso;
		    int linhas = planilha.getPhysicalNumberOfRows(); // Numero de linhas 
		    //System.out.println(linhas);
		    	    
		    int c=0;
		    for(int r=1; r<linhas; r++) {
		        linha = planilha.getRow(r);
		        if(linha != null) {
	                celula = linha.getCell((short)c);
	                if(celula != null)
	                {
	                	NomeRecurso = celula.getStringCellValue(); c++;
		                //System.out.print(NomeRecurso + "\t"); 
		                celula = linha.getCell((short)c);
		                if(celula != null)
		                {
		                	double id = celula.getNumericCellValue();
			                IDRecurso = Double.toString(id); c++;
			                //System.out.print(IDRecurso + "\t");
			                Identifier IdRec = new ID_Name(IDRecurso, NomeRecurso);
			                celula = linha.getCell((short)c);
			                if(celula == null)
			                {
				                String RecursoHidden = "";
				                Boolean hidden;
				                hidden = !(RecursoHidden.equals(""));
				                //System.out.print(hidden);
				                Feature novoRecurso = new Feature(IdRec, hidden);   
				                recursos.add(novoRecurso); c=0;
			                }
			                else
			                {
			                	boolean hide = celula.getBooleanCellValue();
				                String RecursoHidden = Boolean.toString(hide);
				                Boolean hidden;
				                hidden = !(RecursoHidden.equals(""));
				                //System.out.print(hidden);
				                Feature novoRecurso = new Feature(IdRec, hidden);   
				                recursos.add(novoRecurso); c=0;
			                }
			                //System.out.println("");
		                }
	                }                 	
		        }
		    }
		    wb.close();
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
		return recursos;
	}
	
	
	public List<Building> montaPredios()
	{
		List<Building> predios = new ArrayList<Building>();
		Map<String,Feature> featureMap = new HashMap<>();
	    List<Feature> recursos = this.montaRecursos();
	    
	    for(Feature f : recursos){
            String featID = f.getIdentifier().getId();
            featureMap.put(featID, f);
        }
		
		try {
			FileInputStream fs = new FileInputStream(nome);
		    @SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook (fs);
		    
		    //pega dados da segunda planilha a que possui informacoes dos predios
		    XSSFSheet planilha = wb.getSheetAt(2);
		    
		    XSSFRow linha, linhaInicial;
		    XSSFCell celula, celulaInicial;
		    int linhas = planilha.getPhysicalNumberOfRows(); // Numero de linhas
		    System.out.println(linhas);
		    boolean disponivelBool;
		    int c=0, i=1, ident=0, r=1; //flags
		    
		    while(r<linhas)
		    {
		    	linhaInicial 		= planilha.getRow(i);
		    	if(linhaInicial != null)
		    	{
		    		celulaInicial 		= linhaInicial.getCell((short)ident);
				    celula 				= linhaInicial.getCell((short)ident);
				    if(celula != null)
				    {
				    	String IDPredio 	= celulaInicial.getStringCellValue();		    
					    Identifier bid 		= new ID(IDPredio);
			            Building novoPredio = new Building(bid);
			            System.out.println(IDPredio + "\t\t");
				    		    
					    while(celulaInicial.getStringCellValue().equals(celula.getStringCellValue())) {
					    	
					        linha = planilha.getRow(r);
				        	if(linha != null)
				        	{
				        		c=1; celula = linha.getCell((short)c);
					            String IDSala = celula.getStringCellValue(); c=4;
					            Identifier bidSala = new ID(IDSala);
					            System.out.print(IDSala + "\t\t");
					            
					            celula = linha.getCell((short)c);
					            if(celula == null)
					            {
					            	disponivelBool = true; c=3;
					            	System.out.print("    true");
					            }
					            else
					            {
					            	disponivelBool = false; c=3;
					            	System.out.print("    false");
					            }
					          
				        		celula = linha.getCell((short)c);
					            double nroLugares = celula.getNumericCellValue(); c=2;
					            int aux = (int)nroLugares;
					            String numeroDeLugares = Integer.toString(aux);
					            Room novaSala = new Room(bidSala, Integer.parseInt(numeroDeLugares), disponivelBool);
					            System.out.print("\t" + numeroDeLugares);
					            
					            celula = linha.getCell((short)c);
					            String IDRecursos = celula.getStringCellValue(); c=0;
					            List<String> RecursosSala = Arrays.asList(IDRecursos.split(","));
					            for(String f : RecursosSala)
					            {
					            	Feature recursoSala = featureMap.get(f);
					            	novaSala.addFeature(recursoSala);
					            	System.out.print("    " + f);
					            }
					            System.out.println("");
					            novoPredio.addRoom(novaSala); r++;
					            linha = planilha.getRow(r);
					            if(linha != null)
					            	celula = linha.getCell((short)c);
				        	}
				        	
					    } i=r; //atribuicao para setar para linha certa na planilha
					    predios.add(novoPredio);
				    }
				    
		    	}
			     
		    }
	    } catch(Exception ioe) {
	    		ioe.printStackTrace();
	    	}
		return predios;
	}
	
	public List<Course> montaDisciplinas()
	{
		Map<String, Feature> featureMap = new HashMap<>();
		List<Feature> recursos = this.montaRecursos();
		
		for(Feature f : recursos) {
            String featID = f.getIdentifier().getId();
            featureMap.put(featID, f);
        }
		
		List<Course> disciplinas = new ArrayList<Course>();
		
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(nome));
		    @SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook(fs);
		    
		    //pega dados da segunda planilha a que possui informacoes das disciplinas
		    HSSFSheet planilha = wb.getSheetAt(0);
		    
		    HSSFRow linha, linhaInicial, linhaInicial2;
		    HSSFCell celula, celulaInicial, celulaInicial2, celulaInicial3;
		    int linhas = planilha.getPhysicalNumberOfRows(); // Numero de linhas
		    int r=1, r0=0, i=1, ident=0, ident1=1, ident2=4, c=0, d=0, flag=0, acomp=0; //flags
		    
		    while(r<linhas)
		    {
		    	linhaInicial 			= planilha.getRow(i);
			    celulaInicial 			= linhaInicial.getCell((short)ident);
			    celulaInicial2			= linhaInicial.getCell((short)ident1);
			    celula 					= linhaInicial.getCell((short)ident);
			    String NomeDisciplina 	= celulaInicial.getStringCellValue();
			    String IDDisciplina		= celulaInicial2.getStringCellValue();
			    Identifier idDisciplina = new ID_Name(IDDisciplina, NomeDisciplina);
	            Course novaDisciplina 	= new Course(idDisciplina);
		    		    
			    while(celulaInicial.getStringCellValue().equals(celula.getStringCellValue()))
			    {	
			    	linha = planilha.getRow(r);
		     
		        	c=2; celula = linha.getCell((short)c);
		        	String numeroDeAlunos = celula.getStringCellValue(); c=3;
		        	int NroAlunos = Integer.parseInt(numeroDeAlunos);
		        	
		        	celula = linha.getCell((short)c); c=4;
		        	String professor = celula.getStringCellValue();
		        	List<String> professores = Arrays.asList(professor.split(""));
		        	
		        	celula = linha.getCell((short)c); 
		        	String IDTurma = celula.getStringCellValue();
		        	
		        	Identifier turmaID = new ID(IDTurma);
		        	Group novaTurma = new Group(NroAlunos, professores, turmaID);
		        	novaDisciplina.addGroup(novaTurma);
		        	
		        	d=r+1; linhaInicial2 = planilha.getRow(d);
		        	celulaInicial3 = linhaInicial2.getCell((short)ident2);
		        	
		        	while(IDTurma.equals(celulaInicial3.getStringCellValue()))
		        	{
		        		linha = planilha.getRow(r);
		        		
		        		c=9; celula = linha.getCell((short)c);
			        	String horaInicio = celula.getStringCellValue(); c=6;
			        	
			        	celula = linha.getCell((short)c);
			        	String duracaoString = celula.getStringCellValue(); c=8;
			        	int duracao;
			        	if(duracaoString.equals(""))
			        		duracao = 120;
			        	else
			        		duracao = Integer.parseInt(duracaoString);
			        	
			        	celula = linha.getCell((short)c);
			        	String diaDaSemanaString = celula.getStringCellValue(); c=7;
			        	int diaDaSemana = Integer.parseInt(diaDaSemanaString);
			        	
			        	celula = linha.getCell((short)c);
			        	String recursoDoPredio = celula.getStringCellValue(); c=5;
			        	Identifier recursoPredioID;
			        	if(recursoDoPredio.equals(""))
			        	{
			        		recursoPredioID = null;
			        	}
			        	else
			        	{
			        		recursoPredioID = new ID(recursoDoPredio);
			        	} 
			        	
			        	celula = linha.getCell((short)c);
			        	String recursoDaSala = celula.getStringCellValue();
			        	Identifier recursoSalaID;
			        	if(recursoDaSala.equals(""))
			        	{
			        		recursoSalaID = null;
			        	}
			        	else
			        	{
			        		recursoSalaID = new ID(recursoDoPredio);
			        	}
		        		
			        	Session novaSessao = new Session(novaTurma, horaInicio, duracao, diaDaSemana, recursoPredioID, recursoSalaID);
			        	
			        	c=13; String recursos1 = celula.getStringCellValue();
			        	c=14; String recursos2 = celula.getStringCellValue();
			        	String recursosTotais = recursos1 + "," + recursos2;
			        	
			        	if(recursosTotais != null)
			        	{
			        		List<String> recursosSala = Arrays.asList(recursosTotais.split(","));
			        		for(String f : recursosSala)
			        		{
			        			Feature requisitoSessao = featureMap.get(f);
			        			novaSessao.addRequirement(requisitoSessao);
			        		}
			        		
			        	}
			        	novaDisciplina.getGroups().get(acomp).addSession(novaSessao);acomp++;
			        	r0 = r++; linhaInicial2 = planilha.getRow(r);
			        	celulaInicial3 = linha.getCell((short)ident2); 
			        	flag = 1;
		        	}
		        	if(flag == 0)
		        		r++;
		        	else
		        		r = r0;
		            linha = planilha.getRow(r);
		            celula = linha.getCell((short)c);
			    } i=r;
		    	disciplinas.add(novaDisciplina);
		    }
		    	
		
		} catch(Exception ioe) {
    			ioe.printStackTrace();
    		}
		
		return disciplinas;
	}

}
