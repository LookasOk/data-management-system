package page;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditDocTypePage extends AbstractPage {

	public EditDocTypePage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(id = "groupNameInput")
	private WebElement docTypeNameField;

	@FindBy(xpath = "//div[@id='newUserGroups']//input[@placeholder='Search']")
	private WebElement searchField;

	/* BUTTONS */

	@FindBy(xpath = "//button[contains(text(),'Update')]")
	private WebElement buttonUpdate;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	/* SEND KEYS */

	public void sendKeysDocName(String docTypeName) {
		this.docTypeNameField.sendKeys(docTypeName);
	}

	public void sendKeysSearchField(String groupName) {
		this.searchField.sendKeys(groupName);
	}

	/* CLICK BUTTONS */

	public void clickUpdateButton() {
		this.buttonUpdate.click();
	}

	public void clickCancelButton() {
		this.buttonCancel.click();
	}

	public void clickSpecificGroupCreateCheckBox(String groupName) {
		driver.findElement(
				By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + groupName + "')]/..//td[2]//input"))
				.click();
	}

	public void clickSpecificGroupSignCheckBox(String groupName) {
		driver.findElement(
				By.xpath("//div[@id='newUserGroups']//td[contains(text(), '" + groupName + "')]/..//td[3]//input"))
				.click();
	}

	/* WAITS */

	public void waitForCancelButton() {
		new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(buttonCancel));
	}

	/* OTHER METHODS */

	public void clearDocNameField() {
		this.docTypeNameField.clear();
	}

	public void clearSearchField() {
		this.searchField.clear();
	}
	
	public void createDocType(String groupsThatApprove, String groupsThatCreate, String docTypeName)
			throws IOException {
		//API.createDocType(groupsThatApprove, groupsThatCreate, docTypeName);
	}

	public void createGroup(String description, String docTypesToCreate, String docTypesToSign, String groupName,
			String userList) throws IOException {
		//API.createGroup(description, docTypesToCreate, docTypesToSign, groupName, userList);
	}

}
