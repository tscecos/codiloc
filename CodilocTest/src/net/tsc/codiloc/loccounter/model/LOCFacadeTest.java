package net.tsc.codiloc.loccounter.model;

import static org.junit.Assert.fail;
import net.tsc.codiloc.loccounter.exception.LOCCounterException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Caso de pruebas para la clase <code>LOCFacade</code>.
 * 
 * @author Diego Alejandro Guzmán Trujillo <da.guzman12@uniandes.edu.co>.
 * @version 1.0
 */
public class LOCFacadeTest {

	/**
	 * private static final String SOURCE_CODE_FRAGMENT - Fragmento de código de
	 * pruebas.
	 */
	private static final String SOURCE_CODE_FRAGMENT = "public void setX(int x) {\nint y = x;\n//In-line comment\n/*\n*\n*Multiline comment\n*/\n}";

	/**
	 * singleton - Instancia singleton de <code>FileManagerFacade</code>.
	 */
	private static LOCFacade singleton;

	/**
	 * Pre inicializa el caso de pruebas de <code>LOCFacade</code>.
	 * 
	 * @throws Exception
	 *             Si ocurre un error pre inicializando.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		singleton = LOCFacade.getInstance();
	}

	/**
	 * Pre finaliza el caso de pruebas de <code>LOCFacade</code>.
	 * 
	 * @throws Exception
	 *             Si ocurre un error pre finalizando.
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		singleton = null;
	}

	/**
	 * Prueba que se devuelva una instancia singleton de <code>LOCFacade</code>.
	 */
	@Test
	public final void testGetInstance() {
		LOCFacade facade = LOCFacade.getInstance();
		Assert.assertSame(singleton, facade);
	}

	/**
	 * Prueba que se cuenten líneas lógicas en vez de físicas.
	 */
	@Test
	public final void testCount() {
		try {
			Assert.assertTrue(LOCFacade.getInstance().count(
					SOURCE_CODE_FRAGMENT) == 3);
		} catch (LOCCounterException e) {
			e.printStackTrace();
			fail("A LOCCounterException has occurred");
		}
	}
}
