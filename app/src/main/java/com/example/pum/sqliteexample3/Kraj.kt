package com.example.pum.sqliteexample3

/**
 * Created by Wlodek on 2018-02-08.
 */
class Kraj {
    var id : Int? = null
    var CountryName : String? = null

    constructor() {
    }

    constructor(CountryName: String) {
        this.CountryName = CountryName
    }

    // setters:
    //funkcja setID - wykorzystywana wyłącznie dogenerowania zmiennych lokalnych podczas raportowania
    //w zadnym wypadku nie jest wykorzystywana przy zapisach do DB !
    fun setId(id: Int) {
        this.id = id
    }

    fun ToString() : String  {
        //return "$imie $nazwisko $telefon"
        var cTxt:String
        cTxt = this.id.toString() + " " + this.CountryName.toString()
        //return id.toString()  + " " + imie + " " + nazwisko + " " + kraj_fk_id.toString()
        return cTxt

    }

}