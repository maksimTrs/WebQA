package com.webqa.tests.ui

import com.webqa.core.config.Configuration
import com.webqa.core.config.Configuration.userEmail
import com.webqa.core.config.Configuration.userPass
import com.webqa.core.ui.pages.HomePage
import com.webqa.core.ui.pages.LoginPage
import com.webqa.tests.BaseTest
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class LoginTest : BaseTest() {
    private lateinit var homePage: HomePage
    private lateinit var loginPage: LoginPage

    @BeforeMethod
    fun setup() {
        homePage = HomePage(driver)
        loginPage = LoginPage(driver)
        driver.get(Configuration.baseUrl)
    }

    @Test
    @Description("Verify successful login")
    fun testSuccessfulLogin() {
        driver.get(Configuration.baseUrl)
        homePage.clickLogin()
        loginPage.login(userEmail, userPass)
        
        // Wait for login to complete and email to be visible
        homePage.waitForLoginComplete(userEmail)
        
        assertThat(homePage.getLoggedInUserEmail())
            .isEqualTo(userEmail)
    }
}
