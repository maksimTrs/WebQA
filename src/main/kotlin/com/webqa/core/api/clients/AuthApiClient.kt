package com.webqa.core.api.clients

import com.webqa.core.api.ApiClient
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When

class AuthApiClient : ApiClient() {
    fun login(email: String, password: String): String {
        return Given {
            spec(requestSpec)
            body(mapOf(
                "email" to email,
                "password" to password
            ))
        } When {
            post("/user/login")
        } Then {
            statusCode(200)
        } Extract {
            path<String>("authToken")
        }
    }

    fun signup(email: String, password: String) {
        Given {
            spec(requestSpec)
            body(mapOf(
                "email" to email,
                "password" to password,
                "passwordConfirm" to password
            ))
        } When {
            post("/user/signup")
        } Then {
            statusCode(200)
        }
    }
}
