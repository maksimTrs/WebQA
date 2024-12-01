package com.webqa.tests

import com.webqa.core.config.Configuration.App
import com.webqa.core.driver.WebDriverFactory
import io.qameta.allure.Step
import org.openqa.selenium.WebDriver
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod

abstract class BaseTest {
    protected lateinit var driver: WebDriver

    protected val baseUrl = App.baseUrl
    protected val userEmail = App.userEmail
    protected val userPass = App.userPass

    @BeforeMethod
    @Step("Initialize WebDriver")
    fun setUp() {
        driver = WebDriverFactory.createDriver()
    }

    @AfterMethod(alwaysRun = true)
    @Step("Close WebDriver")
    fun tearDown() {
        driver.quit()
    }
}
