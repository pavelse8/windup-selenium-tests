package org.jboss.windup.web.selenium.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

/**
 * this code is intended for a RHAMT web application that does not have any
 * pre-made projects
 * 
 * @author elise
 *
 */
public class CreateProject extends CommonProject {

	//locators:
	SelenideElement checkbox;
	SelenideElement projButton = $(By.cssSelector("button.btn.btn-primary"));
	SelenideElement newProjButton = $(By.className("blank-slate-pf-main-action"));
	SelenideElement runButton = $(By.xpath("//button[@class=\"btn btn-primary\" and contains(.,\"Save & Run\")]"));
	SelenideElement nameInputSelect = $(By.cssSelector("input#idProjectTitle.form-control.ng-pristine.ng-invalid.ng-untouched"));
	SelenideElement cancel = $(By.cssSelector("button.btn.btn-default"));
	SelenideElement next = $(By.cssSelector("button.btn.btn-primary"));
	SelenideElement nameInput =$(By.cssSelector("input#idProjectTitle"));
	SelenideElement descInput = $(By.cssSelector("textarea#idDescription"));
	SelenideElement activePanel = $(By.cssSelector("li[class^='active']"));
	SelenideElement chooseFiles = $(By.cssSelector("label.btn.btn-primary.upload-button"));
	SelenideElement fileBar = $(By.className("col-md-12"));
	SelenideElement fileUpload = $(By.id("fileUpload"));
	SelenideElement clickOut = $(By.className("form-group"));
	SelenideElement modalBackdrop = $(By.cssSelector(".modal-backdrop"));
	SelenideElement modalTitle = $(By.cssSelector("h1.modal-title"));
	SelenideElement modalBody = $(By.cssSelector("div.modal-body"));
	SelenideElement modalNo = $(By.cssSelector("button.cancel-button.btn.btn-lg.btn-default"));
	SelenideElement modalDel = $(By.cssSelector("button.confirm-button.btn.btn-lg.btn-danger"));
	SelenideElement itemInList = $(By.cssSelector("li.jstree-node"));
	SelenideElement packageList = $(By.cssSelector("ul.jstree-container-ul.jstree-children"));
	SelenideElement progBar = $(By.cssSelector("wu-progress-bar"));
	SelenideElement selectedApplications =  $(By.cssSelector("ul.chosen-choices"));
	SelenideElement sort = $(By.cssSelector("wu-sort"));
	SelenideElement  order = $(By.cssSelector("span.sort-direction.fa.fa-sort-alpha-asc"));
	SelenideElement projList = $(By.cssSelector("div.list-group"));
	SelenideElement container = $(By.cssSelector("wu-analysis-context-advanced-options"));
	SelenideElement addOption = container.$(By.cssSelector("button"));
	SelenideElement dropdownOption = container.$(By.cssSelector("select.form-control"));
	SelenideElement containerGroup = container.$(By.className("input-group"));
	SelenideElement analysisConfig = activePageList.$(By.cssSelector("li:nth-child(3)"));
	SelenideElement deleteProject = $(By.cssSelector("button.confirm-button.btn.btn-lg.btn-danger"));
	SelenideElement footer = $(By.cssSelector("div.modal-footer"));
	SelenideElement clearFilter = $(By.cssSelector("a#clear-filters"));
	SelenideElement search = searchContainer.$(By.cssSelector("input.form-control"));
	SelenideElement clear = searchContainer.$(By.cssSelector("button.clear"));


	public CreateProject()
	{
		this(true);
	}

	public CreateProject(boolean otherProjectsAlreadyCreated)
	{
		if (otherProjectsAlreadyCreated) waitForProjectList();
		else waitForNoProjectWelcomePage();
	}

	/**
	 * clicks the new project button This is when there are no other projects in the
	 * project list should re-direct to
	 * http://127.0.0.1:8080/rhamt-web/wizard/create-project
	 */
	public void clickNewProjButton() {
		newProjButton.shouldBe(Condition.visible);
		newProjButton.click();
	}

	/**
	 * clicks the new project button This is for when there are already other
	 * projects in the project list should re-direct to
	 * http://127.0.0.1:8080/rhamt-web/wizard/create-project
	 */
	public void clickProjButton() {
		projButton.waitUntil(visible, 10000);
		projButton.click();
	}

	/**
	 * returns the current URL of the page May have to wait a few seconds for it to
	 * properly load
	 * 
	 * @return the full URL
	 */

	/**
	 * clicks cancel (should work on every page) PRECONDITION: must call
	 * cancelEnabled() first should redirect to the previous page (can be checked by
	 * checkURL())
	 */
	public void clickCancel() {
		cancel.click();
	}

	/**
	 * checks if the cancel button on the page is enabled in all situations this
	 * should return true
	 * 
	 * @return true if it is enabled
	 */
	public boolean cancelEnabled() {
		return cancel.isEnabled();
	}

	/**
	 * clicks next (should work on every page) PRECONDITION: must call nextEnabled()
	 * first should redirect to the next page (can be checked by checkURL())
	 */
	public void clickNext() {
		modalBackdrop.shouldNotBe(visible);
		next.waitUntil(visible,10000).click();
	}

	/**
	 * checks if the cancel button on the page is enabled in all situations this
	 * should return true
	 * 
	 * @return true if it is enabled
	 */
	public boolean nextEnabled() {
		return next.isEnabled();

	}

	/**
	 * Selenium will not recognise the input project name slot as being selected
	 * Therefore this only checks that when the page is loaded, a untouched version
	 * of the input box is shown, if it is not there in that form then it returns
	 * false
	 * 
	 * @return
	 */
	public boolean nameInputSelected() {
		if (nameInputSelect.isDisplayed()){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * sends a string to the input project name box
	 * 
	 * @param s
	 */
	public void inputProjName(String s) {
		nameInput.setValue(s);
	}

	/**
	 * clears any characters in the input box
	 */
	public void clearProjName() {
		nameInput.clear();
		nameInput.sendKeys(" ");
		nameInput.clear();
		clickOut.click();
	}

	/**
	 * inputs a project description
	 * 
	 * @param s
	 *            is the description
	 */
	public void inputProjDesc(String s) {
		descInput.sendKeys(s);
	}

	/**
	 * clears any characters in the project description box
	 */
	public void clearProjDesc() {
		descInput.clear();
		descInput.sendKeys("");
	}

	/**
	 * on the Add Applications page, this returns what the active panel is Should
	 * return "Upload"
	 * 
	 * @return the name of the current active pannel
	 */
	public String activePanel() {
		activePanel.waitUntil(exist, 100000);
		return activePanel.getText();
	}

	/**
	 * clicks on the "choose files" button, will invoke the "upload files" popup
	 */
	public void clickChooseFiles() {
		chooseFiles.click();
	}

	/**
	 * given that the "upload files" popup is not part of the html, a robot has to
	 * close it.
	 * 
	 * @throws AWTException
	 */
	public void robotCancel() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ESCAPE);
		r.keyRelease(KeyEvent.VK_ESCAPE);
	}

	/**
	 * the robot inputs the path/to/file string into the "upload files" popup then
	 * closes the upload files popup. The file should have loaded
	 * 
	 * @param s
	 *            the path/to/file
	 * @throws AWTException
	 */
	public void robotSelectFile(String s) throws AWTException {
		fileUpload.shouldBe(Condition.exist);
		fileUpload.sendKeys(s);
	}

	/**
	 * checks if there are any files loaded onto the screen
	 * 
	 * @return true if there are no files shown
	 */
	public boolean voidFile() {
		return fileBar.getText().equals("");

	}

	/**
	 * depending on the index, it returns the file information and colour
	 * 
	 * @param index
	 *            starts at 1
	 * @return the file information, a colon, then the rgb colour
	 */
	public String checkFileInfo(int index) {
		SelenideElement file = $(By.xpath("(//*[@class='progress-bar success'])"));
		SelenideElement fileInfo = $(By.xpath("(//*[@class='file-info'])[" + index + "]"));
		// have to wait a bit for the file to upload
		file.waitUntil(exist, 35000);
		fileInfo.waitUntil(Condition.not(have(text("%"))),10000);
		return fileInfo.getText() + ":" + file.getCssValue("background-color");
	}

	/**
	 * checks if the file at the given index is not there
	 * 
	 * @param index
	 *            starts at 1
	 * @return true if the file is not there
	 */
	public boolean checkForEmptyFile(int index) {
		SelenideElement file = $("(//*[@class='progress-bar success'])[" + index + "]");
		try {
			file.waitUntil(exist, 15000);
		} catch (NoSuchElementException e) {
			return true;
		}
		return false;
	}

	/**
	 * depending on the index, it finds that file's delete button and clicks it
	 * 
	 * @param index
	 *            starts at 1
	 */
	public void deleteFile(int index) {
		SelenideElement delete = $(By.xpath("(//span[@class='pointer'])[" + index + "]"));
		delete.shouldBe(enabled);
		modalBackdrop.shouldNotBe(visible);
		delete.click();
	}

	/**
	 * whenever a popup is shown (such as deleteing a file or submitting before
	 * packages load) it returns the title, then text shown
	 * 
	 * @return
	 */
	public String popupInfo() {
		modalTitle.shouldBe(visible);
		modalBody.shouldBe(visible);

		return modalTitle.getText() + ";" + modalBody.getText();
	}

	/**
	 * this finds the no or cancel button of the popup and clicks it
	 */
	public void deletePopup() {
		modalNo.click();
	}

	/**
	 * this finds the yes or confirm button of the popup and clicks it
	 */
	public void acceptPopup() {
		modalDel.click();
	}

	/**
	 * can type in deleteAppDialog for the delete box and confirmDialog for save and
	 * running before the packages are loaded
	 * 
	 * @param s
	 *            the string for the type of dialog box
	 * @return true if the popup is removed
	 */
	public boolean popupRemoved(String s) {
		SelenideElement dialog = $(By.cssSelector("div#" + s + ".modal.fade.in"));
		if (dialog.isDisplayed()){return false;}
		else{return true;}
	}

	/**
	 * this goes through the three possible paths to migrate to. When the page is
	 * new, "JBoss EPA 7" should be selected, and if another option is selected and
	 * this method is run again then it should return the string value of that
	 * option. "None are selected" should never be returned
	 * 
	 * @return the chosen migration path
	 */
	public String transformationPath() {
		for (int i = 1; i < 4; i++) {
			SelenideElement radioButton = $(By.xpath("(//*[@id='migrationPath'])[" + i + "]"));
			radioButton.shouldBe(visible);
			if (radioButton.isSelected()) {
				SelenideElement path = $(By.xpath("(//*[@class='radio-inline control-label'])[" + i + "]"));
				return path.getText();
			}
		}
		return "None are selected";
	}

	/**
	 * with a given index, this will click on the transformation path
	 * @param index starts at 1
	 */
	public void chooseTransformationPath(int index) {
		SelenideElement radioButton = $(By.xpath("(//*[@id='migrationPath'])[" + index + "]"));
		radioButton.click();
	}

	/**
	 * This waits a few moment for it takes a while for the packages to load, then
	 * finds the container for the package links, and returns a string of a list of
	 * the tier 1 packages
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String findPackages() throws InterruptedException {
//		String xpath = "//*[@class='jstree-container-ul jstree-children']";
//		WebElement packageList = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(
//				By.cssSelector("ul.jstree-container-ul.jstree-children")));
//		try {
//			WebElement leaf = packageList.findElement(By.cssSelector("li:nth-child(3)"));
//			
//		}
//		catch (NoSuchElementException e) {
//			Thread.sleep(1000);
//			findPackages();
//		}
//		/**
//		 * TODO Fix the issue in a better way
//		 */
//		catch (StaleElementReferenceException sere)
//		{
//			findPackages();
//		}
		itemInList.waitUntil(exist, LONGTIMEOUT);
		return packageList.getText();
	}
	
	/**
	 * within a package, this method will collect the first main branch of a tree of packages
	 * @param index is for either 1 or 2, 1 being the included packages tree hierarchy, and two being excluded packages
	 * @return a ul of further packages
	 */
	public SelenideElement getMainBranch(int index) {
		SelenideElement packageTable = $(By.cssSelector("wu-js-tree-wrapper.jstree.jstree-"+ index + ".jstree-default"));
		packageTable.waitUntil(exist, TIMEOUT);
		SelenideElement firstPackage = packageTable.$(By.cssSelector("li:nth-child(1)"));
		
		SelenideElement branch = $(By.cssSelector("ul.jstree-children"));
		
		if (branch.isDisplayed())
		{return branch;}
		else
		{SelenideElement firstCarrot = $(By.cssSelector("i.jstree-icon.jstree-ocl"));
		firstCarrot.click();
		return branch;
		}
	}
	
	/**
	 * This method will get the first packages branch, then go to the bottom of the hierarchy, click
	 * on the last package there, and then check if that package, and all other parent packages are selected
	 * @param index
	 * @return
	 */
	public boolean testPackages(int index) {
		SelenideElement branch = getMainBranch(index);
		innerPackages(branch);
		return packageSelected(branch);
	}
	
	/**
	 * this method collects the first package's branch, then clicks on the package at the bottom of the
	 * hierarchy. If this is the include packages tree, then the method checks that that package and
	 * all it's parents packages have been deselected
	 * @param index
	 * @return
	 */
	public boolean testEmptyPackages(int index) {
		SelenideElement branch = getMainBranch(index);
		checkbox.click();
		
		if (index == 1) {
			return packageSelected(branch);
		}
		return false;
	}
	
	/**
	 * This method will go down the tree, and slowly open it until it finds the last package,
	 * which it then clicks.
	 * @param ul is the branch of packages to be opened 
	 */
	public void innerPackages(SelenideElement ul) {
		SelenideElement innerPackages = null;
		SelenideElement carrot;
		SelenideElement branch = null; //TODO Упростить метод
		boolean work = false;
		int previousX = 0;
		int x = 1;
		while (true) {
			try {
				previousX = x;
				innerPackages = ul.$(By.cssSelector("li:nth-child(" + x + ")"));
				x++;
				carrot = innerPackages.$(By.cssSelector("i.jstree-icon.jstree-ocl"));
				carrot.waitUntil(visible,TIMEOUT);
				carrot.click();
				
				branch = innerPackages.$(By.cssSelector("ul.jstree-children"));
				work = true;
			}
			catch (Exception e) {
				if (work == true) {
					innerPackages(branch);
					break;
				}
				if (previousX == x) {
					SelenideElement a = innerPackages.$(By.cssSelector("a"));
					checkbox = a.$(By.cssSelector("i:nth-child(1)"));
					checkbox.click();
					break;
				}
			}
		}
	}
	
	/**
	 * this method will go through the packages and check whether or not any packages have been selected
	 * @param ul is the branch of packages to be looked through
	 * @return true if there are packages selected
	 */
	public boolean packageSelected(SelenideElement ul) {
		SelenideElement innerPackages = null;
		SelenideElement branch;
		int previousX = 0;
		int x = 1;
		while (true) {
			try {
				previousX = x;
				innerPackages = ul.$(By.cssSelector("li:nth-child(" + x + ")"));
				x++;
				
				branch = innerPackages.$(By.cssSelector("ul.jstree-children"));
				SelenideElement a = innerPackages.$(By.cssSelector("a"));
				SelenideElement checkbox = a.$(By.cssSelector("i:nth-child(1)"));
				checkbox.waitUntil(exist, TIMEOUT);
				String c = checkbox.getAttribute("class");
				if (c.equals("jstree-icon jstree-checkbox jstree-undetermined")) {
					return packageSelected(branch);
				}
			}
			catch (Exception e) {
				if (previousX == x) {
					return innerPackages.getAttribute("aria-selected").equals("true");
				}
			}
		}
	}
	
	/**
	 * this clicks on the add options button in the advanced options section of the analysis configuration 
	 */
	public void addOptions() {
		addOption.waitUntil(enabled, TIMEOUT);
		addOption.click();
	}
	
	/**
	 * In the advanced option section of tha analysis configuration page, after the "add option" button
	 * has been clicked, this will go through all the options in the dropdown and click on an option
	 * if it matches the paramater
	 * @param optionName is a string of the options
	 */
	public void optionsDropdown(String optionName) {
		int x = 1;
		while (true) {
			SelenideElement option = dropdownOption.$(By.cssSelector("option:nth-child("+ x + ")"));
			x++;
			if(option.exists()){
				if (option.getAttribute("value").equals(optionName)) {
					option.click();
				}
			} else {break;}
		}
	}
	
	/**
	 * once an option has been chosen, then this button actually ads it to the analysis configuration under
	 * the advanced options table
	 * @param num is the index of the options (starts at 1)
	 */
	public void addOption(int num) {
		SelenideElement buttons = container.$(By.cssSelector("tr:nth-child(" + num + ") > td:nth-child(3)"));
		SelenideElement cancel = buttons.$(By.cssSelector("button:nth-child(1)"));
		cancel.click();
	}
	
	/**
	 * once an option has been chosen, then this button cancels it, removing any progress from
	 * the advanced options table
	 * @param num is the index of the options (starts at 1)
	 */
	public void cancelOption(int num) {

		SelenideElement buttons = container.$(By.cssSelector("tr:nth-child(" + num + ") > td:nth-child(3)"));
		SelenideElement add = buttons.$(By.cssSelector("button:nth-child(2)"));
		add.click();
	}
	
	/**
	 * in the advanced options table, while adding an option, this can toggle the value checkbox
	 * @param num is the index of the options (starts at 1)
	 */
	public void toggleValue(int num) {
		SelenideElement value = container.$(By.cssSelector("tr:nth-child(" + num + ") > td:nth-child(2)"));
		value.waitUntil(visible, TIMEOUT);
		SelenideElement checkbox = value.$(By.cssSelector("input"));
		checkbox.click();
	}
	
	/**
	 * Once an option has been added to the advanced options table, this will return whether or not
	 * the value column is true or false
	 * @param num is the index of the options (starts at 1)
	 * @return true if the given options value is true, false otherwise
	 */
	public boolean value(int num) {

		containerGroup.waitUntil(Condition.not(visible), TIMEOUT);
		SelenideElement value = container.$(By.cssSelector("tr:nth-child(" + num + ") > td:nth-child(2)"));
		if (value.getText().equals("true")) {
			return true;
		}
		return false;
	}
	
	/**
	 * based on its name, in the Analysis Configuration page this will click on the sections
	 * carrot, either to open or close it.
	 * @param name
	 */
	public void clickCollapsed(String name) {
		for (int i = 1; i < 4; i++) {
			SelenideElement collapsedDialogues = $(By.xpath("(//*[@class='fields-section-header-pf'])[" + i + "]"));
			if (name.equals(collapsedDialogues.getText())) {
				SelenideElement link = collapsedDialogues.$(By.cssSelector("span"));
				link.click();
			}
		}
	}
	
	/**
	 * In the Analysis Configuration page, if the specified section is collapsed, then true is returned.
	 * @param name is the name of the section, either "Exclude packages", "Use custom rules", or "Advanced options".
	 * @return true if the specified section is collapsed
	 */
	public boolean isCollapsed(String name) {
		for (int i = 1; i < 4; i++) {
			SelenideElement collapsedDialogues = $(By.xpath("(//*[@class='fields-section-header-pf'])[" + i + "]"));
			if (name.equals(collapsedDialogues.getText())) {
				SelenideElement collapse = collapsedDialogues.$(By.cssSelector("span[class$='fa-angle-right']"));
				if(collapse.exists()){return true;}
				else{return false;}
			}
		}
		return false;
	}

	/**
	 * This goes through the three collapsed dialogues at the bottom of the analysis
	 * configuration screen, confirms they are collapsed by ending in
	 * "fa-angle-right" (if "-angle-down" is added to the back of that then it is
	 * expanded) It then checks the text in each dialogue to the string array
	 * created first to check that they are all in order.
	 * 
	 * @return true if the dialogues are in order and collapsed
	 */
	public boolean collapesdInfo() {
		boolean b = false;
		String[] collapsedList = { "Exclude packages", "Use custom rules", "Advanced options" };
		for (int i = 1; i < 4; i++) {
			SelenideElement collapsedDialogues = $(By.xpath("(//*[@class='fields-section-header-pf'])[" + i + "]"));
			SelenideElement collapse = $(By.cssSelector("span[class$='fa-angle-right']"));
			if(collapse.isDisplayed()) {
				if (collapsedList[i - 1].equals(collapsedDialogues.getText())) {
					b = true;
				}
			} else  {return false;}
		}
		return b;
	}

	/**
	 * this clicks the "save and run" button that will save the project information
	 * and redirect the user to the reports/analysis page
	 */
	public void saveAndRun() {
		runButton.click();
	}
	
	/**
	 * 
	 */


	/**
	 * This method should be run right after redirecting to the reports/analysis
	 * page because it gives about 30 seconds for the progress bar to load. It looks
	 * for the div that holds the changing information for the progress bar If the 
	 * method has found the div, then true is returned.
	 *
	 * @return true if the progress bar div is found
	 * @throws InterruptedException
	 */
	public boolean checkProgressBar() throws InterruptedException {
		progBar.waitUntil(visible,30000);
		if(progBar.isDisplayed()){return true;}
		else {return false;}
	}

	/**
	 * When the reports are run (must be after it has sucessfully ran) 
	 * this will find the Analysis Configuration link and click it. 
	 * Should redirect to an Analysis Configuration page
	 */
	public void clickAnalysisConfiguration() {
		analysisConfig.click();
	}

	/**
	 * will delete an application in the Analysis Configuration page.
	 * Note: they are now ordered in alphabetical order, check what order
	 * the applications are in first to determine the proper index
	 * @param index starts at 1
	 */
	public void deleteSelectedApplication(int index) {
		SelenideElement application = $(By.xpath("(//*[@class='search-choice'])[" + index + "]"));
		SelenideElement delete = application.$(By.className("search-choice-close"));
		delete.click();
	}

	/**
	 * This will go through the applications selected for the report and
	 * prints them out in string form.
	 * @return a string version of the applications. 
	 */
	public String printSelectedApplications() {
		selectedApplications.shouldBe(exist);
		return selectedApplications.getText();
	}
	
	/**
	 * This will go down the Analysis Results section, select each analysis and check that they have a 
	 * "show reports" and "delete" button available. If they are both present, then true is returned
	 * 
	 * @param numOfAnalysis denotes how many results there are to go through
	 * @return true if all results have the "show reports" and "delete" actions
	 */
	public boolean analysisResultsComplete(int numOfAnalysis) {
		for (int x = 1; x <= numOfAnalysis; x++) {
			SelenideElement result = $(By.xpath("(//*[@class='success'])[" + numOfAnalysis + "]"));
			try {
                result.waitUntil(exist, LONGTIMEOUT);
                try {
                        SelenideElement report = $(By.xpath("(//*[@class='pointer link'])[1]"));
                        report.waitUntil(exist, TIMEOUT);
                        SelenideElement delete = $(By.xpath("(//*[@class='pointer link'])[2]"));
                        delete.waitUntil(exist, TIMEOUT);
                        return true;
                    }
                catch (Exception e)
                    {
                        return false;
                    }
                }
            catch (TimeoutException e)
                {
                    System.out.println ("Timeout exception" +
                            new Object() {}
                                    .getClass()
                                    .getName() + ":" +
                            new Object() {}
                                    .getClass()
                                    .getEnclosingMethod()
                                    .getName());
                    return false;
                }

		}
		return false;
	}

	/**
	 * from the project list screen this will navigate to whichever project is given by the name
	 * @param projName the exact string form of the project name
	 * @return true if the project is found
	 */
	public boolean navigateProject(String projName) {
		int x = 1;
		while (true) {
			SelenideElement proj = $(By.xpath("(//*[@class='list-group-item  project-info  tile-click'])[" + x + "]"));
			SelenideElement title = proj.$(By.cssSelector("div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)"));

			if(title.exists()){
				if (title.getText().equals(projName)) {
					title.click();
					return true;
				}
				x++;
			}
			else {break;}
		}
		return false;
	}

	/**
	 * This method will navigate the user to the project-list page, locate the
	 * delete button for the first project in the list, click it, then deal with the
	 * popup dialogue box.
	 */
	public boolean deleteProject(String projName) {
		SelenideElement project = null;
		boolean working = false;
		int x = 1;
		while (true) {
			project = $(By.xpath("(//*[@class='list-group-item  project-info  tile-click'])[" + x + "]"));
			SelenideElement title = project.$(By.cssSelector("h2.project-title"));
			if(title.exists()){
				if (title.getText().equals(projName)) {
					SelenideElement trash = project.$(By.cssSelector("a.action-button.action-delete-project"));
					JavascriptExecutor jse2 = (JavascriptExecutor)WebDriverRunner.getWebDriver();
					jse2.executeScript("arguments[0].click()", trash);
					working = true;
					break;
				}
				x++;
			}
			else {break;}
		}
		if (working) {
			modalNo.waitUntil(enabled,TIMEOUT);
			deleteProject.waitUntil(enabled,TIMEOUT);
			if (modalNo.isEnabled() && !deleteProject.isEnabled()) {
				SelenideElement input = $(By.cssSelector("input#resource-to-delete"));
				input.waitUntil(visible, TIMEOUT);
				input.sendKeys(projName);
				return deleteProject.isEnabled();
			}
		}
		return false;
	}
	
	/**
	 * when the dialog box asks if the user would like to delete the project, this will cancel
	 * the box, returning the user to the project list page.
	 * @return true if the dialog has been removed
	 */
	public boolean cancelDeleteProject() {
		modalNo.click();
		return popupRemoved("deleteProjectDialog");
	}
	
	/**
	 * when the dialog box asks if the user would like to delete the project, this will confirm
	 *and delete the project, returning the user to the project list page.
	 * @return true if the dialog has been removed
	 */
	public boolean clickDeleteProject() {
		deleteProject.click();
		return popupRemoved("deleteProjectDialog");
	}
	
	
	/*
	 * ***************
	 * SORTING METHODS
	 * ***************
	 */
	
	/**
	 * this method will collect each of the names of each project,
	 * then sorts the collect list in descending order, toggling the table in descending order
	 * and compare the two arrayLists, if that passes then it does the same with ascending
	 * order.
	 * @return true if the names sort
	 */
	public boolean sortNames() {
		ArrayList<String> names = collectNames();
		
		//sort descending
		Collections.sort(names, Collections.reverseOrder());

		sortProjectList("Name", false);
		ArrayList<String> sorted = collectNames();
		
		if (names.equals(sorted)) {
			//sort ascending
			Collections.sort(names);

			sortProjectList("Name", true);
			sorted = collectNames();
			return names.equals(sorted);
		}
		return false;
	}
	
	/**
	 * this method will collect each of the last modified dates of each project,
	 * then sorts the collected list in descending order, toggling the table in descending
	 * order and collecting that list. It will then compare the two lists and if that
	 * passes then it does the same with ascending order.
	 * @return true if the table sorts properly by last modified date.
	 */
	public boolean sortLastDate() {
		ArrayList<Calendar> dates = collectDates();
		
		//sort descending
		Collections.sort(dates, Collections.reverseOrder());
		
		sortProjectList("Last modified date", false);
		ArrayList<Calendar> sorted = collectDates();
		
		if (dates.equals(sorted)) {
			//sort ascending
			Collections.sort(dates);

			sortProjectList("Last modified date", true);
			sorted = collectDates();
			return dates.equals(sorted);
		}
		//checks if the method was run between minutes (ex between 4:12:59 and 4:13:00)
		int min1 = dates.get(0).get(Calendar.MINUTE);
		int min2 = sorted.get(0).get(Calendar.MINUTE);
		//if was run at the wrong time, throws and exception and tells the user to run again
		if (min2 - min1 == 1) {
			throw new RuntimeException("One second off, run again.");
		}
		return false;
	}
	
	/**
	 * this method will collect each of the application for each project,
	 * then sorts the collected list in descending order, toggling the table in descending
	 * order and collecting that list. It will then compare the two lists and if that
	 * passes then it does the same with ascending order.
	 * @return true if the table sorts properly by number of applications.
	 */
	public boolean sortApplications() {
		ArrayList<Integer> apps = collectApplications();
		
		//sort descending
		Collections.sort(apps, Collections.reverseOrder());

		sortProjectList("Name", false);
		ArrayList<Integer> sorted = collectApplications();
		
		if (apps.equals(sorted)) {
			//sort ascending
			Collections.sort(apps);

			sortProjectList("Name", true);
			sorted = collectApplications();
			return apps.equals(sorted);
		}
		return false;
	}
	
	/**
	 * This method takes in a string parameter, and re-formats it into a Calendar object.
	 * The only information to go off of is how many days, hours, or minutes away from
	 * the current date this project has been last modified. This information then creates 
	 * a calendar object, and returns it.
	 * @param date is the string representing the time elapsed
	 * @return a calendar date representing whent he project has last been modified
	 */
	public Calendar getCalendarDate(String date) {
		Calendar cal = Calendar.getInstance();
		date = date.substring(13);
		int num;
		if (date.indexOf("minute") != -1) {
			date = date.substring(0, date.indexOf("minute") - 1);
			num = parseNum(date);
			cal.add(Calendar.MINUTE, -num);
		}
		else if (date.indexOf("hour") != -1) {
			date = date.substring(0, date.indexOf("hour") - 1);
			num = parseNum(date);
			cal.add(Calendar.HOUR, -num);
		}
		else if (date.indexOf("day") != -1) {
			date = date.substring(0, date.indexOf("day") - 1);
			num = parseNum(date);
			cal.add(Calendar.DATE, -num);
		}
		return cal;
	}
	
	/**
	 * This method will take the string left from the last updated date, and convert it into
	 * and integer, if the string left is "a" them the integer returned is 1.
	 * @param date is the string left after parsing out the day/minute/second information
	 * @return and integer of units left.
	 */
	private int parseNum(String date) {
		if (date.startsWith("a")) 
			return 1;
		else 
			return Integer.parseInt(date);
	}
		
	
	/**
	 * this will collect an arraylist of application objects that will in turn
	 * collect the name and story point count of each application
	 * @return the arraylist of application objects
	 */
	public ArrayList<Project> listProjects() {
		projList.waitUntil(exist, TIMEOUT);
		ArrayList<Project> list = new ArrayList<>();
		
		int x = 1;
		while (true) {
			try {
				SelenideElement proj = projList.$(By.xpath("(//*[@class='list-group-item  project-info  tile-click'])[" + x + "]"));
				SelenideElement desc = proj.$(By.cssSelector("div.list-group-item-heading"));
				SelenideElement name = desc.$(By.cssSelector("a"));
				SelenideElement application = desc.$(By.cssSelector("small.count-applications"));
				SelenideElement lastU = desc.$(By.cssSelector("small.last-updated"));
				
				String app = application.getText();
				app = app.substring(0, app.indexOf("application") - 1);
				
				String updated = lastU.getText();
				Calendar cal = getCalendarDate(updated);

				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				
				Project p = new Project(name.getText(), cal, app);
				list.add(p);
				x++;
			} 
			catch (Exception e) {
				return list;
			}
		}
	}
	
	
	
	/**
	 * The Status class has a type and output, the type can be warning, success
	 * danger, and info, which are found from the output's class name.
	 * @author edixon
	 *
	 */
	class Project {
		
	    String name;
	    Calendar lastModified;
	    int applications;
	 
	    // Constructor
	    public Project(String name, Calendar lastModified, String applications){
	        this.name = name;
	        this.lastModified = lastModified;
	        this.applications = Integer.parseInt(applications);
	    }
	 
	    public String toString()
	    {
	        return this.name;
	    }
	    
	    public boolean equals(Object o) {
	    	if (o == this) 
	    		return true;
	    	if (o == null || o.getClass() != AnalyzeProject.Application.class) {
	    		return false;
	    	}
	    	Project other = (Project)o;
	    	return (this.name.equals(other.name)) && (this.applications == other.applications);
	    }
	}
	
	/**
	 * with a given project arraylist, this will go through the projects
	 * and put the project names into a string arraylist
	 * @return a string arraylist of names
	 */
	private ArrayList<String> collectNames() {
		ArrayList<Project> projList = listProjects();
		ArrayList<String> list = new ArrayList<>();
		
		for (Project p: projList) {
			list.add(p.name);
		}
		return list;
	}
	
	
	/**
	 * with a given project arraylist, this will go through the projects
	 * and put the dates into a string arraylist
	 * @return a string arraylist of dates
	 */
	private ArrayList<Calendar> collectDates() {
		ArrayList<Project> projList = listProjects();
		ArrayList<Calendar> list = new ArrayList<>();
		
		for (Project p: projList) {
			Calendar cal = p.lastModified;
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			list.add(cal);
		}
		return list;
	}
	
	/**
	 * with a given project arraylist, this will go through the projects
	 * and put the applications into an integer arraylist
	 * @return an integer arraylist of applications
	 */
	private ArrayList<Integer> collectApplications() {
		ArrayList<Project> projList = listProjects();
		ArrayList<Integer> list = new ArrayList<>();
		
		for (Project p: projList) {
			list.add(p.applications);
		}
		return list;
	}
	
	/**
	 * this method is used to actually interact with the page and have it
	 * sort the project list by name/created date/last modified date/number of applications
	 * and toggle the ascending/descending order.
	 * @param sortOrder is the name of the sort (should be "Name", "Created date", "Last modified date", or "Number of applications")
	 * @param ascending is true for ascending order, false for descending
	 * @return true if these params are properly found.
	 */
	public boolean sortProjectList(String sortOrder, boolean ascending) {
		dropDown(sort, sortOrder);
		try {
			order.shouldBe(exist);
			order.click();
			return true;
		}
		catch (Exception e) {
			return false;
			}


	}



	/**
	 * this will clear all filters added on the application list page
	 */
	public void clearFilters() {
		clearFilter.click();
	}

	/**
	 * helper method that interacts with the various drop-downs on whichever page
	 * the driver is on
	 * @param f is the web element holding the dropdown
	 * @param name is the name to be selected in the dropdown
	 */
	private void dropDown(SelenideElement f, String name) {
		SelenideElement dropDown = f.$(By.cssSelector("button.btn.btn-default.dropdown-toggle"));
		dropDown.click();
		SelenideElement menu = f.$(By.className("dropdown-menu"));
		int x = 1;
		while (true) {
			try {
				SelenideElement option = menu.$(By.cssSelector("li:nth-child(" + x + ")"));
				if (option.exists()){if (option.getText().equals(name)) {
					option.click();
				}
					x++;}
				else {break;}

			} catch (Exception e) {
				break;
			}
		}
	}


/*
 * **********************
 * END OF SORTING METHODS
 * **********************
 */
	
	
	/**
	 * With a given index of which project to edit, this will click on the edit button, redirecting 
	 * the user to a different page. It will then look for the name input box and the description input
	 * box. If these are found then the new name is inputed. If neither the name nor the description inputs
	 * are found then false is returned, and after that it will check if the name box is enabled and
	 * that the description is enabled, and returns the outcome of that
	 * @param index starts at 1 and denotes which test to use
	 * @param s is the new name for the project
	 * @return true if the name and description input boxes are present and enabled.
	 */
	public boolean editProject(int index, String s) {
		SelenideElement project = $(By.xpath("(//*[@class='list-group-item  project-info  tile-click'])[" + index + "]"));
		SelenideElement edit = project.$(By.cssSelector("a.action-button.action-edit-project"));
		edit.click();
		try {
			nameInput.waitUntil(exist, TIMEOUT);
			nameInput.clear();
			nameInput.sendKeys(s);
			if (!descInput.exists()) {
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return nameInput.isEnabled() && descInput.isEnabled();
	}

	/**
	 * on the edit project page, once the project has been edited, this will click on the update
	 * button, redirecting to the project list page and showing the new changed project name/description
	 */
	public void updateProject() {
		next.click();
	}
	
	/**
	 * With a given index for which project, and a new project name, this will check that
	 * the project name has been changed
	 * @param index starts at 1 and is the number of the project that was changed
	 * @param projName is the changed project name
	 * @return true if the name has been changed
	 */
	public boolean checkUpdateProject(int index, String projName) {
		SelenideElement project = $(By.xpath("(//*[@class='list-group-item  project-info  tile-click'])[" + index + "]"));
		SelenideElement title = project.$(By.cssSelector("h2.project-title"));
		return title.getText().equals(projName);
	}
	
	/**
	 * on the project list page, this will look up the given search parameter into the search
	 * bar, changing the project list.
	 * @param s
	 */
	public void projectSearch(String s) {
		search.sendKeys(s);
	}
	
	/**
	 * if the project search has been changed 
	 */
	public void clearProjectSearch() {
		clear.click();
	}
}
