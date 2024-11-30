package com.webqa.tests.api

import com.webqa.core.api.clients.AuthApiClient
import com.webqa.core.api.clients.ProductApiClient
import com.webqa.core.config.Configuration.userEmail
import com.webqa.core.config.Configuration.userPass
import com.webqa.core.utils.TestDataGenerator
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class ProductApiTest {
    private lateinit var authApiClient: AuthApiClient
    private lateinit var productApiClient: ProductApiClient
    private lateinit var authToken: String

    @BeforeClass
    fun setup() {
        authApiClient = AuthApiClient()
        productApiClient = ProductApiClient()

        // Login to get auth token
        authToken = authApiClient.login(userEmail, userPass)
    }

    @Test
    @Description("Verify products can be retrieved")
    fun testGetProducts() {
        val response = productApiClient.getProducts(authToken)

        assertThat(response.products)
            .isNotEmpty
            .hasSize(5)

        assertThat(response.sale)
            .isEqualTo(0.1)
    }

    @Test
    @Description("Verify order can be created")
    fun testCreateOrder() {
        val productIdVal = "1"
        val productQty = 1
        val cardDetails = TestDataGenerator.generateCardDetails()

        val createOrderResponse = productApiClient.createOrder(
            authToken = authToken,
            productId = productIdVal,
            quantity = productQty,
            cardDetails = cardDetails
        )

        SoftAssertions().apply {
            assertThat(createOrderResponse.transaction.id)
                .`as`("Transaction ID")
                .isNotNull()

            assertThat(createOrderResponse.transaction.order.totalQuantity)
                .`as`("Order Quantity")
                .isEqualTo(productQty)

            assertThat(createOrderResponse.transaction.order.id)
                .`as`("Order ID")
                .isNotNull()

            assertThat(createOrderResponse.transaction.order.totalSum)
                .`as`("Total Sum")
                .isEqualTo(100)

            assertThat(createOrderResponse.message)
                .`as`("Order Message")
                .isEqualTo("The 100usd purchase is successfully completed")

            assertThat(createOrderResponse.transaction.order.products[0].product.id)
                .`as`("Product ID")
                .isEqualTo(productIdVal)

        }.assertAll()
    }
}
