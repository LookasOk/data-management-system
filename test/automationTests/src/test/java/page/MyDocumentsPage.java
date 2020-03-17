package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyDocumentsPage extends AbstractPage {

	public MyDocumentsPage(WebDriver driver) {
		super(driver);
	}

	/* FIELDS */

	@FindBy(xpath = "//*[@aria-label='Search']")
	private WebElement searchDocumentField;

	/* BUTTONS */

	@FindBy(id = "all")
	private WebElement buttonAllDocuments;

	@FindBy(id = "created")
	private WebElement buttonCreated;

	@FindBy(id = "submitted")
	private WebElement buttonSubmitted;

	@FindBy(id = "rejected")
	private WebElement buttonRejected;

	@FindBy(id = "accepted")
	private WebElement buttonAccepted;

	/* CLICK BUTTONS */

	public void clickButtonAll() {
		this.buttonAllDocuments.click();
	}

	public void clickButtonCreated() {
		this.buttonCreated.click();
	}

	public void clickButtonSubmitted() {
		this.buttonSubmitted.click();
	}

	public void clickButtonRejected() {
		this.buttonRejected.click();
	}

	public void clickButtonAccepted() {
		this.buttonAccepted.click();
	}
    //driver.findElement(By.xpath("//td[7]//button")).click();
	public void clickEditViewDocument(String documentName) {
		driver.findElement(By.xpath("//td[8]//button")).click();
	}

	/* SEND KEYS */

	public void sendKeysSearchDocument(String documentInformation) {
		this.searchDocumentField.sendKeys(documentInformation);
	}

	/* GET TEXT METHODS */
    //return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[1]")).getText();
	public String getIDbyDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[3][text()='" + documentName + "']/..//td[2]")).getText();
	}
	//return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[3]")).getText();
	public String getTypeByDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[3][text()='" + documentName + "']/..//td[4]")).getText();
	}
	//return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[4]")).getText();
	public String getStatusByDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[3][text()='" + documentName + "']/..//td[5]")).getText();
	}
	//return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']/..//td[5]")).getText();
	public String getCreationDatebyDocumentName(String documentName) {
		return driver.findElement(By.xpath("//td[3][text()='" + documentName + "']/..//td[6]")).getText();
	}

	/* IS DISPLAYED METHOD */
	//return driver.findElement(By.xpath("//td[2][text()='" + documentName + "']")).isDisplayed();
	public boolean isDocumentNameDisplayed(String documentName) {
		return driver.findElement(By.xpath("//td[3][text()='" + documentName + "']")).isDisplayed();
	}
}