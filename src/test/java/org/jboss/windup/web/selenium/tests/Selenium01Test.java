package org.jboss.windup.web.selenium.tests;

import java.awt.AWTException;
import java.io.File;

//import junit.framework.TestCase;
import org.jboss.windup.web.selenium.pages.CreateProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.After;
//import org.junit.FixMethodOrder;
//import org.junit.runners.MethodSorters;

/**
 * Runs the first test from the Web UI Test Script V0.1
 * Initial Analysis Run using new Project test
 * @author elise
 */

public class Selenium01Test  {

	private CreateProject selenium;

	@BeforeEach
	public void setUp() {
		selenium = new CreateProject(false);

		System.out.println(new Object() {}
				.getClass()
				.getEnclosingMethod()
				.getName() + " complete");

	}

		@Test
		public void test01ProjectList() {
		/*
		 * Navigate to Project List
		 */

		assertEquals(selenium.getRhamtBaseUrl() + "rhamt-web/project-list", selenium.checkURL());
		
		selenium.clickNewProjButton();
		assertEquals(selenium.getRhamtBaseUrl() + "rhamt-web/wizard/create-project", selenium.checkURL());
		
		//checks that the project name field has focus, then the cancel/next buttons are enabled/disabled
		//.nameInputSelected does not work
		assertTrue(selenium.nameInputSelected());
		assertTrue(selenium.cancelEnabled());
		assertFalse(selenium.nextEnabled());
		
		selenium.clickCancel();
		selenium.clickCancel();
		
		assertEquals(selenium.getRhamtBaseUrl() + "rhamt-web/project-list", selenium.checkURL());

		System.out.println(new Object() {}
				.getClass()
				.getEnclosingMethod()
				.getName() + " complete");

	}

	/**
	 * New Project Project Name validation and upload applications enabled
	 */
	@Test
	public void test02CreateProject() {

		assertEquals(selenium.getRhamtBaseUrl() + "rhamt-web/project-list", selenium.checkURL());
		selenium.clickNewProjButton();
		assertEquals(selenium.getRhamtBaseUrl() + "rhamt-web/wizard/create-project", selenium.checkURL());

		//checks for next being enabled after entering in 3 characters
		selenium.inputProjName("abc");
		assertTrue(selenium.nextEnabled());
		selenium.clearProjName();
		assertFalse(selenium.nextEnabled());

		//properly inputs the project name & description
		selenium.inputProjName("Selenium01Test");
		assertTrue(selenium.nextEnabled());
		selenium.inputProjDesc("Selenium Test Project containing a single Application");
		
		//checks that it redirects to the correct page
		selenium.clickNext();
		//assertEquals("/add-applications", selenium.checkURL().substring(51));
		
		//checks that the upload panel is active & the next button is enabled
		assertEquals("Upload", selenium.activePanel());
		assertFalse(selenium.nextEnabled());


		System.out.println(new Object() {}
				.getClass()
				.getEnclosingMethod()
				.getName() + " complete");
	}


	@Test
	public void test03AddApps() throws AWTException {

		test02CreateProject();

		assertFalse(selenium.nextEnabled());
		selenium.clickChooseFiles();

		selenium.robotCancel();
		//checks that the user has been returned to the correct page
		assertTrue(selenium.checkURL().endsWith("/add-applications"));
		//checks that there are no files pulled up
		assertTrue(selenium.voidFile());

		selenium.clickChooseFiles();
		//AdministracionEfectivo.ear
		File file = new File("src/test/resources/test-archives/AdministracionEfectivo.ear");
		selenium.robotSelectFile(file.getAbsolutePath());
		//checks that the uploaded file is green and has the correct information.
		assertEquals("AdministracionEfectivo.ear (60.161 MB):rgba(63, 156, 53, 1)", selenium.checkFileInfo(1));

		// skips the dragging and dropping because I currently do not have a solution for it
		// uploads AdditionWithSecurity-EAR-0.01.ear
		File file2 = new File("src/test/resources/test-archives/AdditionWithSecurity-EAR-0.01.ear");
		selenium.robotSelectFile(file2.getAbsolutePath());
		//checks that the uploaded file is green and has the correct information.
		assertEquals("AdditionWithSecurity-EAR-0.01.ear (36.11 MB):rgba(63, 156, 53, 1)", selenium.checkFileInfo(2));

		selenium.robotCancel();

		System.out.println(new Object() {}
				.getClass()
				.getEnclosingMethod()
				.getName() + " complete");
	}

	@Test
	public void test04MaintainApps() throws AWTException, InterruptedException {

		test03AddApps();
		
		selenium.deleteFile(2);
		//lets the pop-up load
		assertEquals(
				"Confirm application deletion;Do you really want to delete application AdditionWithSecurity-EAR-0.01.ear?",
				selenium.popupInfo());
		
		selenium.deletePopup();
		assertTrue(selenium.popupRemoved("deleteAppDialog"));
		assertEquals("AdministracionEfectivo.ear (60.161 MB):rgba(63, 156, 53, 1)", selenium.checkFileInfo(1));
		assertEquals("AdditionWithSecurity-EAR-0.01.ear (36.11 MB):rgba(63, 156, 53, 1)", selenium.checkFileInfo(2));
		//need to check that it goes back to the add applications screen
		//also check that there are no changes to the files
		

		selenium.deleteFile(2);
		selenium.acceptPopup();
		assertTrue(selenium.popupRemoved("deleteAppDialog"));
		assertEquals("AdministracionEfectivo.ear (60.161 MB):rgba(63, 156, 53, 1)", selenium.checkFileInfo(1));
		//checks that AdditionWithSecurity-EAR-0.01.ear is deleted
		assertTrue(selenium.checkForEmptyFile(2));

		System.out.println(new Object() {}
				.getClass()
				.getEnclosingMethod()
				.getName() + " complete");
	}

	@Test
	public void test05RunAnalysis() throws AWTException, InterruptedException {
		test04MaintainApps();
		
		selenium.clickNext();

		assertEquals("Migration to JBoss EAP 7", selenium.transformationPath());
		

/*
		//has the user click save and run before letting the packages load
		selenium.saveAndRun();
		//checks that the pop-up has the correct information

		TODO: fix this test because with the latest changes the package retrieval in the 3rd step of the new project wizard is too fast
		and the package tree appears before the "save & run" button has been pushed by the test (the command above)
		so the pop-up never appears
		assertEquals(
				"Package identification is not complete;Do you want to save the analysis without selecting packages?",
				selenium.popupInfo());

		selenium.deletePopup();
		assertTrue(selenium.popupRemoved("confirmDialog"));
		*/

		//waits a few seconds then checks that the packages are shown
		//need to check that it is all tier 1
		assertEquals("antlr\ncom\njavassist\njavax\nmx\nnet\noracle\norg", selenium.findPackages());
		//checks that the three more detailed dialogue are compressed
		assertTrue(selenium.collapesdInfo());
		
		selenium.saveAndRun();
		//Waits for the project to load then checks that the progress bar is visible
		assertTrue(selenium.checkProgressBar());

		
		//waits approx 1.3 minutes for the file to fully load, then deletes the project and closes the driver
//		Thread.sleep(100000);
//		selenium.deleteProject("test");

		System.out.println(new Object() {}
				.getClass()
				.getEnclosingMethod()
				.getName() + " complete");
	}


	public void tearDown()
	{

		System.out.println(new Object() {}
				.getClass()
				.getEnclosingMethod()
				.getName() + " complete");

	}


}
