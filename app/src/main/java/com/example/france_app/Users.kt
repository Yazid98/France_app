package com.example.france_app

//first name c'est prenom
//Lastname c'est le nom de famille

@Suppress("ConvertSecondaryConstructorToPrimary", "RedundantGetter", "RedundantSetter", "unused")
class Users {



    var email : String =""
        get() = field
        set(value) {
            field = value
        }

    var password : String =""
        get() = field
        set(value) {
            field = value
        }

    var firstname : String = ""
        get() = field
        set(value) {
            field = value
        }

    var lastname : String = ""
        get() = field
        set(value) {
            field = value
        }

    var age : String = ""
        get() = field
        set(value) {
            field = value
        }

    constructor(email: String, password: String, firstname: String, lastname: String, age: String) {
        this.email = email
        this.password = password
        this.firstname = firstname
        this.lastname = lastname
        this.age = age
    }


}