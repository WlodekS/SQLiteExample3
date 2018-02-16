package com.example.pum.sqliteexample3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //testowe wypisanie do LOG-CAT-a:
        Log.d("DB", "1 - Uruchomiono program.")

        val context = this
        var zb = DataBaseHandler(context = context)
        Log.d("DB", "2 - Uruchomiono Zarzadce Bazy - zb.")

        //Wypelnienie bazy danymi testowymi:
        //DodajDaneTestowe(zb)
        //wykorzystano 2018.02.12 godz. 00:24


        //Wypisanie do LOG-CAT-a listy userów w bazie USERS:
        var cTxt: String = ""
        var data = zb.allUsers
        for (usr in data) {
            cTxt = usr.ToString()
            Log.d("DB", usr.ToString())
            //Log.v("DB", usr.ToString())
        }

        //Wypisanie do LOG-CAT-a listy krajów w bazie COUNTRY:
        var cTxt2: String = ""
        var data2 = zb.allCountries
        for (kraj in data2) {
            cTxt = kraj.ToString()
            Log.d("DB", kraj.ToString())
         }

        //###########################################################################
        zb.SelFromTwoTables()

        //###########################################################################

        var a: String = "AA"

        /*
        tvResult.text = ""
        for (i in 0..(data.size-1)) {
            tvResult.append(data.get(i).id.toString() + " " + data.get(i).name + " " + data.get(i).age + "\n")
        }
        */

        // Don't forget to close database connection
        //db.closeDB()
        //zb.close db...###
    }

    /*
     * Dane testowe:
     */
    fun DodajDaneTestowe(zb: DataBaseHandler) {
        //Dodajemy do bazy ze 3 kraje:

        var kraj_1 = Kraj("Polska")
        var kraj_2 = Kraj("Bulgaria")
        var kraj_3 = Kraj("Grecja")
        //wpisanie do bazy Kraj:
        zb.insertData_COUNTRY(kraj_1)
        zb.insertData_COUNTRY(kraj_2)
        zb.insertData_COUNTRY(kraj_3)
        //testowe wypisanie do LOG-CAT-a:
        Log.d("DB", "Dodano kraje do bazy Kraj.")


        //Dodajemy do bazy 5 użytkownikow:
        var user_1 = User("Janek", "Janowski", 1)
        var user_2 = User("Piotr", "Piotrowski", 2)
        var user_3 = User("Agata", "Agacinska", 3)
        var user_4 = User("Baska", "Baskowska", 1)
        var user_5 = User("Kaska", "Kaskowska", 2)
        //wpisanie do bazy Users:
        zb.insertData_USER(user_1)
        zb.insertData_USER(user_2)
        zb.insertData_USER(user_3)
        zb.insertData_USER(user_4)
        zb.insertData_USER(user_5)
        //testowe wypisanie do LOG-CAT-a:
        Log.d("DB", "Dodano uzytkownikow do bazy Users.")


    }
}
