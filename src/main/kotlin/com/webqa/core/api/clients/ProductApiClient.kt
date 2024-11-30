package com.webqa.core.api.clients

import com.webqa.core.api.ApiClient
import com.webqa.core.api.models.ApiResponse
import com.webqa.core.api.models.ProductResponse
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When

class ProductApiClient : ApiClient() {
    fun getProducts(authToken: String): ProductResponse {
        return Given {
            spec(requestSpec)
            header("Authorization", authToken)
        } When {
            get("/product/")
        } Then {
            statusCode(200)
        } Extract {
            `as`(ProductResponse::class.java)
        }
    }

    fun createOrder(authToken: String, productId: String, quantity: Int, cardDetails: Map<String, Any>): ApiResponse {
      return  Given {
            spec(requestSpec)
            header("Authorization", authToken)
            body(
                mapOf(
                    "card" to cardDetails,
                    "products" to listOf(
                        mapOf(
                            "id" to productId,
                            "quantity" to quantity
                        )
                    )
                )
            )
        } When {
            post("/order/createAndPay")
        } Then {
            statusCode(200)
        } Extract {
            `as`(ApiResponse::class.java)
        }
    }
}
