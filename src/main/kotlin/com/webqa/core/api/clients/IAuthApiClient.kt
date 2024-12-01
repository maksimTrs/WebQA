package com.webqa.core.api.clients

interface IAuthApiClient<L, S> {
    fun login(email: String, password: String): L
    fun signup(email: String, password: String): S
}