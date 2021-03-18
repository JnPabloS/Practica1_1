package com.jnpablos.practica_1

data class User(
        var name: String,
        var email: String? = null,
        var password: String? = null,
        var genre: String? = null,
        var hobbies: String? = null,
        var city: String,
        var fechaNacimiento: String
)