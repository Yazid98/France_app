package com.example.france_app

//first name c'est prenom
//Lastname c'est le nom de famille

@Suppress("ConvertSecondaryConstructorToPrimary", "RedundantGetter", "RedundantSetter", "unused")
class Users {
    constructor(
        id: String?,
        firstname: String,
        lastname: String,
        age: String
    ) {
        this.id = id.toString()
        this.firstname = firstname
        this.lastname = lastname
        this.age = age
    }

    private var id : String = ""
        get() = field
        set(value) {
            field = value
        }

    private var firstname: String = ""
        get() = field
        set(value) {
            field = value
        }

    var lastname: String = ""
        get() = field
        set(value) {
            field = value
        }

    var age: String = ""
        get() = field
        set(value) {
            field = value
        }
}