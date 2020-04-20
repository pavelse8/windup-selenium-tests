package org.jboss.windup.web.selenium.tests;

import java.text.ParseException;
import java.util.ArrayList;

//import junit.framework.TestCase;
import org.jboss.windup.web.selenium.pages.EditProject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.After;
//import org.junit.FixMethodOrder;
//import org.junit.runners.MethodSorters;

/*testing of the web console navigation features using
 * Project Selenium02Test created via the methods within class Selenium02Test
 */

public class Selenium03Test {

	private EditProject selenium;

	@BeforeEach
	public void setUp() {
		selenium = new EditProject();
	}

	@Test
	public void test01ProjectList() throws ParseException {
		assertTrue(selenium.navigateProject("Selenium02Test"));
		selenium.waitForProjectLoad("Selenium02Test");
		assertTrue(selenium.checkURL().endsWith("/project-detail"));
		
		selenium.clickProjectsIcon();
		assertEquals(selenium.getRhamtBaseUrl() + "rhamt-web/project-list", selenium.checkURL());

		assertTrue(selenium.navigateProject("Selenium02Test"));
		selenium.waitForProjectLoad("Selenium02Test");
		assertTrue(selenium.checkURL().endsWith("/project-detail"));

		//asserts the project name Selenium02Test is indeed in the dropdown
		assertEquals("Project\nSelenium02Test", selenium.dropDownInfo());
		//will return what active panel is there
		assertEquals("Analysis Results", selenium.activePage());
		assertEquals(2, selenium.analysisResultsShown());

		assertTrue(selenium.sortString(1, "Analysis"));
		assertTrue(selenium.sortStatus());
		assertTrue(selenium.sortDate(3, "Start Date"));
		assertTrue(selenium.sortString(4, "Applications"));

		ArrayList<String> list = selenium.collectTableCol(1);
		String analysis = list.get(0).toString();
		
		selenium.search(analysis.substring(1));
		assertEquals("[" + analysis + "]", selenium.collectTableCol(1).toString());
		
		selenium.cancelSearch();

	}

	@Test
	public void test02MaintainApps() throws ParseException {
		assertTrue(selenium.navigateProject("Selenium02Test"));
		
		selenium.clickApplications();
		assertTrue(selenium.checkURL().endsWith("/applications"));

		assertEquals("Applications", selenium.activePage());

		assertTrue(selenium.sortString(1, "Application"));
		assertTrue(selenium.sortDate(2, "Date Added"));
		
		ArrayList<String> table = new ArrayList<>();
		table.add("arit-ear-0.8.1-SNAPSHOT.ear");
		table.add("AdditionWithSecurity-EAR-0.01.ear");
		table.add("AdministracionEfectivo.ear");
		
		assertEquals(table, selenium.collectTableCol(1));

		selenium.deleteApplication("arit-ear-0.8.1-SNAPSHOT.ear");
		assertEquals(
				"Confirm Application Deletion;Are you sure you want to delete 'arit-ear-0.8.1-SNAPSHOT.ear'?",
				selenium.popupInfo());
		
		selenium.deletePopup();
		assertTrue(selenium.popupRemoved("deleteAppDialog"));
		
		assertEquals(table, selenium.collectTableCol(1));
		

//		selenium.deleteApplication("arit-ear-0.8.1-SNAPSHOT.ear");
//		selenium.acceptPopup();
//		assertTrue(selenium.popupRemoved("deleteAppDialog"));
//		table.remove(0);
//		assertEquals(table, selenium.collectTableCol(1));
//		selenium.collectTableCol(1).toString());

		selenium.search("Admin");
		assertEquals("[AdministracionEfectivo.ear]", selenium.collectTableCol(1).toString());
		
		selenium.cancelSearch();

	}

	@Test
	public void test03MaintainAnalysisRuns() throws InterruptedException {
		assertTrue(selenium.navigateProject("Selenium02Test"));
		selenium.clickAnalysisConfiguration();

		selenium.clickProjDropDown("Selenium01Test");
		assertEquals("Project\nSelenium01Test", selenium.dropDownInfo());

		selenium.clickProjDropDown("Selenium02Test");
		assertEquals("Project\nSelenium02Test", selenium.dropDownInfo());
		
		selenium.deleteAnalysisResults(2);
		String num = selenium.analysisName(2);
		assertEquals("Confirm Analysis Deletion;Are you sure you want to delete analysis " + num + "?", selenium.popupInfo());
		
		selenium.deletePopup();
		assertTrue(selenium.popupRemoved("deleteAppDialog"));
		

		// would be a pain to re-do
		// selenium.deleteAnalysisResults(2);
		// selenium.acceptPopup();
		// assertTrue(selenium.popupRemoved("deleteAppDialog"));
		
		String url = selenium.clickAnalysisReport(1);
		selenium.navigateTo(1);

	}

	@AfterEach
	public void tearDown()
	{
		System.out.println(new Object() {}
				.getClass()
				.getEnclosingMethod()
				.getName() + " complete");

	}
}
