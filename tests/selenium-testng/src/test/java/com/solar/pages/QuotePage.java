
package com.solar.pages;

import com.solar.utils.WebUIUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class QuotePage {
    private final WebDriver driver;

    private final By firstNameField = By.xpath("//label[text()='First Name:']/following-sibling::input");
    private final By lastNameField = By.xpath("//label[text()='Last Name:']/following-sibling::input");
    private final By addressField = By.xpath("//label[text()='Address:']/following-sibling::textarea");
    private final By stateDropdown = By.xpath("//label[text()='State:']/following-sibling::select");
    private final By panelDropdown = By.xpath("//label[text()='Panel Type:']/following-sibling::select");
    private final By panelCountField = By.xpath("//label[text()='Panel Count:']/following-sibling::input");
    private final By solarTypeDropdown = By.xpath("//label[text()='Solar Type:']/following-sibling::select");
    private final By financeTypeDropdown = By.xpath("//label[text()='Finance Type:']/following-sibling::select");
    private final By termDropdown = By.xpath("//label[text()='Term (Years):']/following-sibling::select");
    private final By aprDropdown = By.xpath("//label[text()='APR (%):']/following-sibling::select");
    private final By nextButton = By.xpath("//button[text()='Next']");
    private final By monthlyPayment = By.xpath("//p[strong[text()='Estimated Monthly Rent:']]");


    public QuotePage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillForm(String firstName, String lastName, String address, String state, String panel, int panelCount,String solarType,String financeType,int termInYears,double aprPercentage) {
        WebUIUtils.typeText(driver, firstNameField, firstName, "First Name");
        WebUIUtils.typeText(driver, lastNameField, lastName, "Last Name");
        WebUIUtils.typeText(driver, addressField, address, "Address");
        WebUIUtils.selectDropdown(driver, stateDropdown, state, "State");
        WebUIUtils.selectDropdown(driver, panelDropdown, panel, "Panel Type");
        WebUIUtils.typeText(driver, panelCountField, String.valueOf(panelCount), "Panel Count");
        WebUIUtils.selectDropdown(driver, solarTypeDropdown, solarType, "Solar Type");
        WebUIUtils.selectDropdown(driver, financeTypeDropdown, financeType, "Finance Type");
        WebUIUtils.selectDropdown(driver, termDropdown, String.valueOf(termInYears), "Term(Years)");
        WebUIUtils.selectDropdown(driver, aprDropdown, String.valueOf(aprPercentage), "APR %");
    }

    public void clickNext() {
        WebUIUtils.click(driver, nextButton, "Next Button");
    }

    public String getDealerIdFromLocalStorage() {
        return (String) ((JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('dealerId');");
    }

    public String getDisplayedMonthlyRent() {
        return driver.findElement(monthlyPayment).getText().trim();
    }
}
