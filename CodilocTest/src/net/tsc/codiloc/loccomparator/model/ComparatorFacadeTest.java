package net.tsc.codiloc.loccomparator.model;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import net.tsc.codiloc.loccomparator.exception.ComparatorException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Caso de pruebas para la clase <code>ComparatorFacade</code>.
 * 
 * @author Diego Alejandro Guzmán Trujillo <da.guzman12@uniandes.edu.co>.
 * @version 1.0
 */
public class ComparatorFacadeTest {

	/**
	 * originalLines - Líneas de código originales.
	 */
	private static List<String> originalLines;

	/**
	 * modifiedLines - Líneas de código modificadas.
	 */
	private static List<String> modifiedLines;

	/**
	 * singleton - Instancia singleton de <code>ComparatorFacade</code>.
	 */
	private static ComparatorFacade singleton;

	/**
	 * Pre inicializa el caso de pruebas de <code>ComparatorFacade</code>.
	 * 
	 * @throws Exception
	 *             Si ocurre un error pre inicializando.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		generateOriginalLines();
		generateModifiedLines();
		singleton = ComparatorFacade.getInstance();
	}

	/**
	 * Pre finaliza el caso de pruebas de <code>ComparatorFacade</code>.
	 * 
	 * @throws Exception
	 *             Si ocurre un error pre finalizando.
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		originalLines.clear();
		modifiedLines.clear();
		originalLines = null;
		modifiedLines = null;
		singleton = null;
	}

	/**
	 * Prueba que se devuelva una instancia singleton de
	 * <code>ComparatorFacade</code>.
	 */
	@Test
	public final void testGetInstance() {
		ComparatorFacade facade = ComparatorFacade.getInstance();
		Assert.assertSame(singleton, facade);
	}

	/**
	 * Prueba que se obtengan correctamente las líneas agregadas.
	 */
	@Test
	public final void testGetAddedLOC() {
		List<ComparedLine> comparedLines;
		try {
			comparedLines = ComparatorFacade.getInstance().getAddedLOC(
					originalLines, modifiedLines);
			Assert.assertTrue(comparedLines.size() == 1);
			Assert.assertEquals("3", comparedLines.get(0).getTextLine());
			Assert.assertEquals(2, comparedLines.get(0).getTextLineNumber());
		} catch (ComparatorException e) {
			e.printStackTrace();
			fail("A ComparatorException has occurred");
		}
	}

	/**
	 * Prueba que se obtengan correctamente las líneas elimiandas.
	 */
	@Test
	public final void testGetDeletedLOC() {
		List<ComparedLine> comparedLines;
		try {
			comparedLines = ComparatorFacade.getInstance().getDeletedLOC(
					originalLines, modifiedLines);
			Assert.assertTrue(comparedLines.size() == 2);
			Assert.assertEquals("This is the original file",
					comparedLines.get(0).getTextLine());
			Assert.assertEquals(1, comparedLines.get(0).getTextLineNumber());
			Assert.assertEquals("30", comparedLines.get(1).getTextLine());
			Assert.assertEquals(4, comparedLines.get(0).getTextLineNumber());
		} catch (ComparatorException e) {
			e.printStackTrace();
			fail("A ComparatorException has occurred");
		}
	}

	/**
	 * Construye las líneas de código modificadas.
	 */
	private static void generateModifiedLines() {
		modifiedLines = new ArrayList<String>();
		modifiedLines.add("1");
		modifiedLines.add("3");
		modifiedLines.add("5");
		modifiedLines.add("20");
	}

	/**
	 * Construye las líneas de código originales.
	 */
	private static void generateOriginalLines() {
		originalLines = new ArrayList<String>();
		originalLines.add("This is the original file");
		originalLines.add("1");
		originalLines.add("5");
		originalLines.add("20");
		originalLines.add("30");
	}
}
