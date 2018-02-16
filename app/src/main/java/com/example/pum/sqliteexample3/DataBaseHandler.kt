package com.example.pum.sqliteexample3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.security.AccessControlContext

/**
 * Created by Wlodek on 2018-02-08.
 */

//-------------
// Nazwa Bazy :
//-------------
val DATABASE_NAME = "MyDB"

// ---------------------
// Tabela nr 1 - USERS :
// ---------------------
val TABLE_NAME_USERS = "Users"
val USERS_COL_ID = "id"
val USERS_COL_IMIE = "imie"
val USERS_COL_NAZWISKO = "nazwisko"
val USERS_COL_KRAJ_FK_ID = "kraj_fk_id"

// -----------------------
// Tabela nr 2 - COUNTRY :
// -----------------------
val TABLE_NAME_COUNTRY = "Country"
val COUNTRY_COL_ID = "id"
val COUNTRY_COL_COUNTRY_NAME = "CountryName"

// Logcat tag
private val LOG = "DatabaseHelper"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {

        //włączenie aktywności dla Fereign Key:
        val setFK = "PRAGMA foreign_keys = ON"
        db?.execSQL(setFK)


        // utworzenie tabeli COUNTRY (robię ją pierwszą bo do niej później referencja):
        val createTable2 = "CREATE TABLE if not exists " + TABLE_NAME_COUNTRY +  " (" +
                COUNTRY_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COUNTRY_COL_COUNTRY_NAME + " VARCHAR(30)" + ")"
        db?.execSQL(createTable2)
        Log.d("DB", "DataBaseHandler - tabela COUNTRY-OK!")

        // utworzenie tabeli USER (z referencją do tabeli Country):
        val createTable1 = "CREATE TABLE if not exists " + TABLE_NAME_USERS +  " (" +
                USERS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERS_COL_IMIE + " VARCHAR(30)," +
                USERS_COL_NAZWISKO + " VARCHAR(30)," +
                USERS_COL_KRAJ_FK_ID + " INTEGER NOT NULL," +
                " FOREIGN KEY (" + USERS_COL_KRAJ_FK_ID + ") REFERENCES " +
                TABLE_NAME_COUNTRY + "("+ COUNTRY_COL_ID + ")" + ")"
        db?.execSQL(createTable1)
        Log.d("DB", "DataBaseHandler - tabela USERS-OK!")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData_USER(user : User){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(USERS_COL_IMIE, user.imie)
        cv.put(USERS_COL_NAZWISKO, user.nazwisko)
        cv.put(USERS_COL_KRAJ_FK_ID, user.kraj_fk_id)
        var result = db.insert(TABLE_NAME_USERS, null, cv)
        if(result == -1.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            Log.d("DB", "DataBaseHandler-tabela USERS-Dodanie uzytkownika-Failed!")
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            Log.d("DB", "DataBaseHandler-tabela USERS-Dodanie uzytkownika-OK")
        }
    }

    fun insertData_COUNTRY(kraj : Kraj){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COUNTRY_COL_COUNTRY_NAME, kraj.CountryName)
        var result = db.insert(TABLE_NAME_COUNTRY, null, cv)
        if(result == -1.toLong()) {
            Toast.makeText(context, "Insert country Failed", Toast.LENGTH_SHORT).show()
            Log.d("DB", "DataBaseHandler-tabela COUNTRY-Dodanie uzytkownika-Failed!")
        }
        else {
            Toast.makeText(context, "Insert country Success", Toast.LENGTH_SHORT).show()
            Log.d("DB", "DataBaseHandler-tabela COUNTRY-Dodanie uzytkownika-OK")
        }
    }


    // closing database
    fun closeDB() {
        val db = this.getReadableDatabase()
        if (db != null && db!!.isOpen())
            db!!.close()
    }

    /**
     * getting all users
     */
    // looping through all rows and adding to list
    // adding to todo list
    val allUsers: List<User>
        get() {
            val users = ArrayList<User>()
            val selectQuery = "SELECT  * FROM " + TABLE_NAME_USERS

            Log.d(LOG, selectQuery)

            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)  //c-kursor
            if (c.moveToFirst()) {
                do {
                    val usr = User()
                    usr.setId(c.getInt(c.getColumnIndex(USERS_COL_ID)))
                    //usr.setImie(c.getString(c.getColumnIndex(USERS_COL_IMIE)))
                    usr.imie = c.getString(c.getColumnIndex(USERS_COL_IMIE))
                    //usr.setNazwisko(c.getString(c.getColumnIndex(USERS_COL_NAZWISKO)))
                    usr.nazwisko = c.getString(c.getColumnIndex(USERS_COL_NAZWISKO))
                    usr.setKraj_fk_id(c.getInt(c.getColumnIndex(USERS_COL_KRAJ_FK_ID)))
                    users.add(usr)
                } while (c.moveToNext())
            }
            return users
        }


    /**
     * getting all countries
     */
    // looping through all rows and adding to list
    // adding to todo list
    val allCountries: List<Kraj>
        get() {
            val countries = ArrayList<Kraj>()
            val selectQuery = "SELECT  * FROM " + TABLE_NAME_COUNTRY

            Log.d(LOG, selectQuery)

            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)  //c-kursor
            if (c.moveToFirst()) {
                do {
                    val kraj = Kraj()
                    kraj.setId(c.getInt(c.getColumnIndex(COUNTRY_COL_ID)))
                    kraj.CountryName = c.getString(c.getColumnIndex(COUNTRY_COL_COUNTRY_NAME))
                    countries.add(kraj)
                } while (c.moveToNext())
            }
            return countries
        }

    /*
    fun readData() : MutableList<User> {
        var list : MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var user = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                list.add(user)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
    */

    fun SelFromTwoTables() {
        //Wybór danych z dwóch tabel.
        //Koncepcja przedstawiona jest tutaj:
        //androidopentutorials.com/android-sqlite-join-multiple-tables-example/
        //P.S. doczytać o aapterach.Tutaj:
        //https://code.tutsplus.com/pl/tutorials/android-from-scratch-understanding-adapters-and-adapter-views--cms-26646
        val querySQL = "SELECT " +
                TABLE_NAME_USERS + "." + USERS_COL_ID + ", " +
                TABLE_NAME_USERS + "." + USERS_COL_IMIE + ", " +
                TABLE_NAME_USERS + "." + USERS_COL_NAZWISKO + ", " +
                TABLE_NAME_COUNTRY + "." + COUNTRY_COL_COUNTRY_NAME + " " +
                "FROM " + TABLE_NAME_USERS + " " +
                "INNER JOIN " + TABLE_NAME_COUNTRY + " " +
                "ON " + TABLE_NAME_USERS + "." + USERS_COL_KRAJ_FK_ID + " = " +
                TABLE_NAME_COUNTRY + "." + COUNTRY_COL_ID
        Log.d("DB", "------------------------------------------------")
        Log.d("DB", "SELECT z dwóch tabel polaczonych przez INER JOIN")
        Log.d("DB", "------------------------------------------------")
        Log.d("DB", querySQL)
        Log.d("DB", "------------------------------------------------")

        val db = this.getReadableDatabase()
        var myCursor = db.rawQuery( querySQL, null)
        while (myCursor.moveToNext()) {
            val id = myCursor.getInt(0)
            val imie = myCursor.getString(1)
            val nazwisko = myCursor.getString(2)
            val kraj = myCursor.getString(3)
            Log.d("DB", id.toString() +" "+ imie + " " + nazwisko + " " + kraj)
        }
        myCursor.close()
        Log.d("DB", "------------------------------------------------")
    }


    companion object {
        //...
    }
}
