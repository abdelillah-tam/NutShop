package com.example.nutshop.domain.models

data class Customer(
    var uid : String,
    var email: String,
    var firstName: String,
    var lastName: String,
    var address: String
){

    constructor() : this(uid = "", email = "", firstName = "",lastName = "",address = "")
}