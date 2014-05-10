package net.tsc.codiloc.versionmanager.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsc.codiloc.filemanager.exception.FileManagerException;
import net.tsc.codiloc.filemanager.model.FileManagerFacade;
import net.tsc.codiloc.loccomparator.exception.ComparatorException;
import net.tsc.codiloc.loccomparator.model.ComparatorFacade;
import net.tsc.codiloc.loccomparator.model.ComparedLine;
import net.tsc.codiloc.versionmanager.exception.VersionManagerException;

import org.junit.Test;

public class VersionManagerFacadeTest {
	
	private final File original = new File("..\\CodilocTest\\testFiles\\original\\ComparedLine.java");
	 
    private final File modified = new File("..\\CodilocTest\\testFiles\\modified\\ComparedLine.java");
    
	@Test
	public void compareVersion() throws VersionManagerException {

		int addedLines = 0;
		int deletedLines = 0;
		int totalLines = 0;
		
		List<String> originalLines = null;
		List<String> modifiedLines = null;
		
		List<ComparedLine> addedLinesList = new ArrayList<>();
		List<ComparedLine> deletedLinesList = new ArrayList<>();

		VersionManagerFacade versionManager = VersionManagerFacade.getInstance();
		ComparatorFacade comparator = ComparatorFacade.getInstance();
		FileManagerFacade fileManager = FileManagerFacade.getInstance();

		try {
			originalLines = fileManager.getLinesFromFile(original);
			modifiedLines = fileManager.getLinesFromFile(modified);
		} catch (FileManagerException e) {
			e.printStackTrace();
		}

		try {
			addedLinesList = comparator.getAddedLOC(originalLines, modifiedLines);
			assertEquals("	private String addLine;", addedLinesList.get(0).getTextLine());
    		assertEquals("	private int addLineNumber;", addedLinesList.get(1).getTextLine());
    		
			deletedLinesList = comparator.getDeletedLOC(originalLines, modifiedLines);
			assertEquals("	public void setTextLineNumber(int textLineNumber) {", deletedLinesList.get(0).getTextLine());
    		assertEquals("		this.textLineNumber = textLineNumber;", deletedLinesList.get(1).getTextLine());
    		assertEquals("	}", deletedLinesList.get(2).getTextLine());

			addedLines = versionManager.countComparedLOC(addedLinesList);
			assertEquals(2, addedLines);
			
			deletedLines = versionManager.countComparedLOC(deletedLinesList);
			assertEquals(3, deletedLines);
			
			for(String line : modifiedLines){
				totalLines += versionManager.countLOC(line);
			}
						
			assertEquals(20, totalLines); 
			
		} catch (ComparatorException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void loadBase() throws VersionManagerException {
		
		String fileName = "../files/ComparedLine.java";
		VersionManagerFacade versionManager = VersionManagerFacade.getInstance();
		
		List<String> baseLines = versionManager.loadBase(fileName);
		assertEquals(61, baseLines.size());
		assertEquals("package net.tsc.codiloc.loccomparator.model;", baseLines.get(0));
		assertEquals("}", baseLines.get(60));
		
	}
	
	@Test
	public void loadHistory() throws VersionManagerException {
		
		String fileName = "../files/ComparedLine.java";
		VersionManagerFacade versionManager = VersionManagerFacade.getInstance();
		
		List<String> historyLines = versionManager.loadHistory(fileName);
		assertEquals(null, historyLines);
		
	}
	
	@Test
	public void addHeaderVer0() throws VersionManagerException {
		Map<String, String> header = null;
		List<String> historyLines = null;
		List<String> hResult = new ArrayList<String>();

		VersionManagerFacade versionManager = VersionManagerFacade
				.getInstance();
		try {
			versionManager.addHeader(header, historyLines);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		historyLines = new ArrayList<String>();
		historyLines.add("int edad = 22;");
		historyLines.add("if(comparedText.equal(\"hola mundo\")){");
		historyLines.add("int String = \"mi nombre es\";");
		historyLines.add("int total = 1233;");
		historyLines.add("bool flag = false;");
		historyLines.add("int cost = 345;");
		
		header = new HashMap<String, String>();
		header.put("Autor", "Mauricio Torres");
		header.put("Observaciones", "nueva clase que hace algo");

		hResult.add("/*<<---HEADER--->>");
		hResult.add("*<<VER:0>>");
		hResult.add("*<<DATETIME:");
		hResult.add("*<<Autor:Mauricio Torres>>");
		hResult.add("*<<Observaciones:nueva clase que hace algo>>");
		hResult.add("*<<---END HEADER--->>");
		hResult.add("*/");
		try {
			versionManager.addHeader(header, historyLines);
			List<String> hRes = versionManager.getHistoryLines();

			for (Map.Entry<String, String> entry : header.entrySet()) {
				String line = "*<<" + entry.getKey() + ":" + entry.getValue()
						+ ">>";
				boolean res = hRes.contains(line);
				assertEquals(res, true);
			}
			for (String hLine : historyLines) {
				boolean res = hRes.contains(hLine);
				assertEquals(res, true);
			}

		} catch (VersionManagerException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void addHeaderVerX() throws VersionManagerException {
		Map<String, String> header = null;
		List<String> historyLines = null;
		List<String> hResult = new ArrayList<String>();

		VersionManagerFacade versionManager = VersionManagerFacade
				.getInstance();
		try {
			versionManager.addHeader(header, historyLines);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		historyLines = new ArrayList<String>();
		
		historyLines.add("/*<<---HEADER--->>");
		historyLines.add("*<<VER:0>>");
		historyLines.add("*<<DATETIME:10/05/14 10:32 AM>>");
		historyLines.add("*<<Observaciones:nueva clase que hace algo>>");
		historyLines.add("*<<Autor:Mauricio Torres>>");
		historyLines.add("*<<---END HEADER--->>");
		historyLines.add("*/");
		historyLines.add("int edad = 22;");
		historyLines.add("if(comparedText.equal(\"hola mundo\")){");
		historyLines.add("int String = \"mi nombre es\";");
		historyLines.add("int total = 1233;");
		historyLines.add("bool flag = false;");
		historyLines.add("int cost = 345;");
		
		header = new HashMap<String, String>();
		header.put("Autor", "Juan Perez");
		header.put("Observaciones", "La misma clase que hace algo, ahora hace otra cosa");
		
		hResult.add("/*<<---HEADER--->>");
		hResult.add("*<<VER:1>>");
		hResult.add("*<<DATETIME:");
		hResult.add("*<<Autor:Juan Perez>>");
		hResult.add("*<<Observaciones:La misma clase que hace algo, ahora hace otra cosa>>");
		hResult.add("*<<---END HEADER--->>");
		hResult.add("*/");

		try {
			versionManager.addHeader(header, historyLines);
			
			List<String> hRes = versionManager.getHistoryLines();

			for (Map.Entry<String, String> entry : header.entrySet()) {
				String line = "*<<" + entry.getKey() + ":" + entry.getValue()
						+ ">>";
				boolean res = hRes.contains(line);
				assertEquals(res, true);
			}
			for (String hLine : historyLines) {
				boolean res = hRes.contains(hLine);
				assertEquals(res, true);
			}

		} catch (VersionManagerException e) {
			e.printStackTrace();
		}
	}

}
