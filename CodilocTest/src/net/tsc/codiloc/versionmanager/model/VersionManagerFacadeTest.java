package net.tsc.codiloc.versionmanager.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

}
