package net.tsc.codiloc.filemanager.model;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.tsc.codiloc.filemanager.exception.FileManagerException;
import net.tsc.codiloc.filemanager.model.FileManagerFacade;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Caso de pruebas para la clase <code>FileManagerFacade</code>.
 * 
 * @author Diego Alejandro Guzmán Trujillo <da.guzman12@uniandes.edu.co>.
 * @version 1.0
 */
public class FileManagerFacadeTest {

	/**
	 * FILE_TEST_PATH_WRITE - Ruta relativa se escribe el archivo.
	 */
	private static final String FILE_TEST_WRITE_PATH = "../files/TestWrite.txt";
	
	/**
	 * FILE_TEST_PATH - Ruta relativa donde se encuentra el archivo de pruebas.
	 */
	private static final String FILE_TEST_PATH = "../files/Test.txt";

	/**
	 * FILE_BAD_TEST_PATH - Ruta relativa de un archivo ficticio(no existe).
	 */
	private static final String FILE_BAD_TEST_PATH = "./badfile.txt";

	/**
	 * TEST_LINE_VALUE - Valor de prueba de una línea del archivo de pruebas.
	 */
	private static final String TEST_LINE_VALUE = "Line3";

	/**
	 * testFile - Archivo de pruebas.
	 */
	private static File testFile = null;

	/**
	 * badFile - Archivo ficticio(no existe).
	 */
	private static File badFile = null;
	
	/**
	 * writeFile - Archivo de pruebas para escribir
	 */
	private static File writeFile = null;
	
	/**
	 * fileLines - Lista de lineas del archivo.
	 */
	private static List<String> fileLines = null;

	/**
	 * singleton - Instancia singleton de <code>FileManagerFacade</code>.
	 */
	private static FileManagerFacade singleton;

	/**
	 * Pre inicializa el caso de pruebas de <code>FileManagerFacade</code>.
	 * 
	 * @throws Exception
	 *             Si ocurre un error pre inicializando.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testFile = new File(FILE_TEST_PATH);
		singleton = FileManagerFacade.getInstance();
		badFile = new File(FILE_BAD_TEST_PATH);
		fileLines = new ArrayList<>();
		writeFile = new File(FILE_TEST_WRITE_PATH);
	}

	/**
	 * Pre finaliza el caso de pruebas de <code>FileManagerFacade</code>.
	 * 
	 * @throws Exception
	 *             Si ocurre un error pre finalizando.
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		testFile = null;
		singleton = null;
		badFile = null;
		fileLines = null;
		writeFile = null;
	}

	/**
	 * Prueba que se devuelva una instancia singleton de
	 * <code>FileManagerFacade</code>.
	 */
	@Test
	public void testGetInstance() {
		FileManagerFacade facade = FileManagerFacade.getInstance();
		Assert.assertSame(singleton, facade);
	}

	/**
	 * Prueba que se obtenga las líneas de un archivo satisfactoriamente.
	 */
	@Test
	public void testGetLinesFromFile00() {
		try {
			List<String> lines = FileManagerFacade.getInstance()
					.getLinesFromFile(testFile);
			Assert.assertTrue(lines.size() == 4);
			Assert.assertTrue(lines.get(2).equals(TEST_LINE_VALUE));
		} catch (FileManagerException e) {
			e.printStackTrace();
			fail("A FileManagerException has occurred");
		}

	}

	/**
	 * Prueba que se lance <code>IllegalArgumentException</code> si el archivo
	 * es <code>null</code>.
	 * 
	 * @throws FileManagerException
	 *             Si ocurre un error leyendo y obteniendo las líneas de texto
	 *             de un archivo.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetLinesFromFile01() throws FileManagerException {
		FileManagerFacade.getInstance().getLinesFromFile(null);
	}

	/**
	 * Prueba que se lance <code>FileManagerException</code> si el archivo
	 * no existe.
	 * 
	 * @throws FileManagerException
	 *             Si ocurre un error leyendo y obteniendo las líneas de texto
	 *             de un archivo.
	 */
	@Test(expected = FileManagerException.class)
	public void testGetLinesFromFile02() throws FileManagerException {
		FileManagerFacade.getInstance().getLinesFromFile(badFile);
	}
	
	/**
	 * Prueba que se lance <code>IllegalArgumentException</code> si el parámetro
	 * archivo es <code>null</code>.
	 * 
	 * @throws FileManagerException
	 *             Cuando ocurre un error escribiendo el archivo
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWriteLinesToFiles00() throws FileManagerException {
		FileManagerFacade.getInstance().writeLinesToFiles(null, fileLines);
	}
	
	/**
	 * Prueba que se lance <code>IllegalArgumentException</code> si el parámetro
	 * lines es <code>null</code>.
	 * 
	 * @throws FileManagerException
	 *             Cuando ocurre un error escribiendo el archivo
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWriteLinesToFiles01() throws FileManagerException {
		FileManagerFacade.getInstance().writeLinesToFiles(testFile, null);
	}
	
	/**
	 * Prueba que se se escriba un archivo.
	 * 
	 * @throws FileManagerException
	 *             Cuando ocurre un error escribiendo el archivo
	 */
	@Test
	public void testWriteLinesToFiles02() throws FileManagerException {
		List<String> linesTest01 = FileManagerFacade.getInstance()
				.getLinesFromFile(testFile);
		FileManagerFacade.getInstance().writeLinesToFiles(writeFile, linesTest01);
		
		List<String> linesTest02 = FileManagerFacade.getInstance()
				.getLinesFromFile(writeFile);
		
		Assert.assertTrue(linesTest02.size() == 4);
		Assert.assertTrue(linesTest02.get(2).equals(TEST_LINE_VALUE));
	}
}
