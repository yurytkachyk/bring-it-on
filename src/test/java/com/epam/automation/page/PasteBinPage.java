package com.epam.automation.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PasteBinPage {

    public static final String PASTEBIN_URL = "https://pastebin.com/";

    private final WebDriver driver;

    @FindBy(xpath = "//textarea[@id='postform-text']")
    private WebElement newPasteTextArea;

    @FindBy(css = "#select2-postform-format-container")
    private WebElement syntaxHighlightingSpan;

    @FindBy(xpath = "(//li[@class='select2-results__option' and text()='Bash'])[1]")
    private WebElement syntaxHighlightingLi;

    @FindBy(xpath = "//span[@id='select2-postform-expiration-container']")
    private WebElement pasteExpirationSpan;

    @FindBy(xpath = "//li[@class='select2-results__option' and text()='10 Minutes']")
    private WebElement pasteExpirationLi;

    @FindBy(css = "#postform-name")
    private WebElement pasteNameInput;

    @FindBy(css = ".btn.-big")
    private WebElement createNewPasteButton;

    public PasteBinPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public PasteBinPage openPage() {
        driver.get(PASTEBIN_URL);
        return this;
    }

    public PasteBinPage writeCode(String code) {
        newPasteTextArea.sendKeys(code);
        return this;
    }

    public PasteBinPage showSyntaxHighlightingList() {
        syntaxHighlightingSpan.click();
        return this;
    }

    public PasteBinPage selectSyntaxHighlighting() {
        syntaxHighlightingLi.click();
        return this;
    }

    public PasteBinPage showPasteExpirationList() {
        pasteExpirationSpan.click();
        return this;
    }

    public PasteBinPage selectPasteExpiration() {
        pasteExpirationLi.click();
        return this;
    }

    public PasteBinPage enterPasteName(String name) {
        pasteNameInput.sendKeys(name);
        return this;
    }

    public PostedPasteBinPage createNewPaste() {
        createNewPasteButton.click();
        return new PostedPasteBinPage(driver);
    }

}