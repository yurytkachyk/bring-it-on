package com.epam.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class PostedPasteBinPage {

    private final WebDriver driver;

    private final By bashLinkLocator = By.xpath("//a[@class='btn -small h_800' and text()='Bash']");
    private final By liLocator = By.xpath("//li[@class='li1']");

    public PostedPasteBinPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String checkTitleMatch(String name) {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.refreshed(ExpectedConditions.titleContains(name)));

        return driver.getTitle();
    }

    public String checkBushPresence() {
        final WebElement bashLink = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(bashLinkLocator));

        return bashLink.getAccessibleName();
    }

    public List<String> checkCodeMatch() {
        final List<WebElement> codeLines = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(liLocator));

        return codeLines
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

}