package org.jboss.windup.web.selenium.tests;

import java.awt.AWTException;

//import junit.framework.TestCase;
import org.jboss.windup.web.selenium.pages.CreateProject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.After;
//import org.junit.FixMethodOrder;
//import org.junit.runners.MethodSorters;

/*
 * Test the Project List and Maintenance
 */

public class Selenium07Test  {

	private CreateProject selenium;

	@BeforeEach
	public void setUp() {
		selenium = new CreateProject();
	}

	@Test
	public void test01ProjectListAndEdit() throws AWTException, InterruptedException {

		System.out.println (new Object() {}.getClass().getName() + ":" +
				new Object() {}.getClass().getEnclosingMethod().getName());

		assertEquals(selenium.getRhamtBaseUrl() + "rhamt-web/project-list", selenium.checkURL());
		
		selenium.sortProjectList("Created date", true);

		assertTrue(selenium.sortApplications());
		assertTrue(selenium.sortNames());

		assertTrue(selenium.sortLastDate());
		

		assertTrue(selenium.editProject(3, "Selenium06Test"));
		

		selenium.updateProject();
		Thread.sleep(4000);
		assertTrue(selenium.checkUpdateProject(3, "Selenium06Test"));
		

		assertTrue(selenium.sortLastDate());
		

		selenium.projectSearch("2");
		String list = selenium.listProjects().toString();
		assertEquals(list, "[Selenium02Test]");
		
		selenium.clearProjectSearch();
		list = selenium.listProjects().toString();
		System.out.println(list);
		assertEquals("[Selenium01Test, Selenium02Test, Selenium06Test]", list);
		
		assertTrue(selenium.deleteProject("Selenium01Test"));
		
		assertTrue(selenium.cancelDeleteProject());
		
		assertTrue(selenium.deleteProject("Selenium06Test"));

		assertTrue(selenium.clickDeleteProject());
		Thread.sleep(8000);
		list = selenium.listProjects().toString();
		assertEquals("[Selenium01Test, Selenium02Test]", list);
		
	}

	@AfterEach
	public void tearDown()
	{

	}

}
