package com.example.pum.sqliteexample3

/**
 * Created by Wlodek on 2018-02-08.
 */
class User {

    /*
    var id: Int? = 0;


    var imie: String =""
        get() = field
        set(value) {
            field = value
        }

    var nazwisko: String = ""
        get() = field
        set(value) {
            field = value
        }
    var kraj_fk_id: Int? = 0
    */
    var id: Int? = null
    var imie: String? = null
    var nazwisko: String? = null
    var kraj_fk_id: Int? = null

    // constructors
    constructor() {}

    constructor(imie: String, nazwisko: String, kraj_fk_id: Int) {
        this.imie = imie
        this.nazwisko = nazwisko
        this.kraj_fk_id = kraj_fk_id
    }


    // setters:
    //funkcja setID - wykorzystywana wyłącznie dogenerowania zmiennych lokalnych podczas raportowania
    //w zadnym wypadku nie jest wykorzystywana przy zapisach do DB !
    fun setId(id: Int) {
        this.id = id
    }
    /*
    fun setImie(imie: String) {
        this.imie = imie
    }
    */
    /*
    fun setNazwisko(nazwisko: String) {
        this.nazwisko = nazwisko
    }
    */
    fun setKraj_fk_id(kraj_fk_id: Int) {
        this.kraj_fk_id = kraj_fk_id
    }


    fun ToString() : String  {
        //return "$imie $nazwisko $telefon"
        var cTxt:String
        cTxt = this.id.toString() + " " + this.imie.toString() + " " + this.nazwisko + " " +this.kraj_fk_id
        //return id.toString()  + " " + imie + " " + nazwisko + " " + kraj_fk_id.toString()
        return cTxt

    }

}
