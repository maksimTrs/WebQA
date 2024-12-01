package com.webqa.tests.ui

import com.webqa.core.ui.pages.HomePage
import com.webqa.core.ui.pages.LoginPage
import com.webqa.tests.BaseTest
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class ProductTest : BaseTest() {
    private lateinit var homePage: HomePage
    private lateinit var loginPage: LoginPage

    @BeforeMethod
    fun setup() {
        homePage = HomePage(driver)
        loginPage = LoginPage(driver)

        // Login before each test
        driver.get(baseUrl)
        homePage.clickLogin()
        loginPage.login(userEmail, userPass)

        // Wait for successful login by checking the email is displayed
        assertThat(homePage.getLoggedInUserEmail())
            .isEqualTo(userEmail)
    }

    @Test
    @Description("Verify products are displayed for logged in user")
    fun testProductsDisplayed() {
        assertThat(homePage.getProductsCount())
            .isEqualTo(5)
    }

    @Test
    @Description("Verify product can be added to cart")
    fun testAddProductToCart() {
        homePage.addProductToCart(0)

        assertThat(homePage.isChartCartDisplayed()).isTrue
        assertThat(homePage.getChartCartInputValue()).isEqualTo(1)
    }
}
