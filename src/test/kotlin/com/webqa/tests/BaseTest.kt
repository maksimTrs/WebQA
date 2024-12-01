package com.webqa.tests

import WebDriverFactory
import com.webqa.core.config.Configuration.App
import io.qameta.allure.Attachment
import io.qameta.allure.Step
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod

abstract class BaseTest {
    protected val baseUrl = App.baseUrl
    protected val userEmail = App.userEmail
    protected val userPass = App.userPass

    @BeforeMethod
    @Step("Initialize WebDriver")
    fun setUp() {
        WebDriverFactory.createDriver()
    }

    @AfterMethod(alwaysRun = true)
    @Step("Close WebDriver")
    fun tearDown(testResult: ITestResult) {
        if (testResult.status == ITestResult.FAILURE) {
            saveScreenshotPNG()
        }
        WebDriverFactory.quitDriver()
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    fun saveScreenshotPNG(): ByteArray {
        return (getDriver() as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
    }

    protected fun getDriver(): WebDriver = WebDriverFactory.getDriver()
}
