package com.example.pum.sqliteexample3

/**
 * Created by Wlodek on 2018-02-11.
 */
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class StaryProgramJAVA : AppCompatActivity() {


    private var _db: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1:
        _db = openOrCreateDatabase("mydb.db", Context.MODE_PRIVATE, null)
        Log.d("DB", "Baza utworzona")
        _db!!.execSQL("DROP TABLE IF EXISTS OSOBY;")
        _db!!.execSQL("CREATE TABLE OSOBY (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAZWISKO CHAR (20) NOT NULL," +
                "PESEL CHAR (9) NOT NULL," +
                "DATAUR DATE," +
                "WZROST REAL)")
        Log.d("DB", "Tabela utworzona")
        _db!!.execSQL("INSERT INTO OSOBY (NAZWISKO, PESEL, DATAUR, WZROST) VALUES (" + "'Kowalski', '123456789', '2016-01-09', 55.4)")
        _db!!.execSQL("INSERT INTO OSOBY (NAZWISKO, PESEL, DATAUR, WZROST) VALUES (" + "'Nowak', '123456788', '2016-01-08', 56.2)")
        Log.d("DB", "Rekordy dodane")

        //Wypisywanie w logu:
        //tu jest definicja co i jak ma być definiowanie:
        var cursor = _db!!.rawQuery("SELECT * FROM OSOBY", null)
        //A to jest samo wypisanie:
        sendToLog(cursor)

        //2:
        Log.d("DB", "Punkt D")
        _db!!.execSQL("UPDATE OSOBY SET WZROST = 57 WHERE ID = 2")
        cursor = _db!!.rawQuery("SELECT * FROM OSOBY", null)
        sendToLog(cursor)

        Log.d("DB", "Punkt D pkt 4:")
        //Wyswietlenie:
        //_db.execSQL("UPDATE OSOBY SET WZROST = 57 WHERE ID = 2");
        cursor = _db!!.rawQuery("SELECT * FROM OSOBY WHERE WZROST>56", null)
        sendToLog(cursor)


        //Wyswietlenie E 1:
        Log.d("DB", "Punkt E 1:")
        cursor = _db!!.rawQuery("SELECT AVG(WZROST) FROM OSOBY", null)
        sendToLog2(cursor)

        //Wyswietlenie E 1:
        Log.d("DB", "Punkt E 2:")
        cursor = _db!!.rawQuery("SELECT * FROM OSOBY WHERE ID=1", null)
        sendToLog(cursor)

        //Wyswietlenie E 3:
        Log.d("DB", "Punkt E 3:")
        cursor = _db!!.rawQuery("SELECT ID FROM OSOBY", null)
        sendToLog2(cursor)

        //Wyswietlenie F:
        Log.d("DB", "Punkt F:")
        _db!!.execSQL("DELETE FROM OSOBY WHERE WZROST < 56")
        cursor = _db!!.rawQuery("SELECT * FROM OSOBY", null)
        sendToLog(cursor)

        _db!!.close()
    }

    private fun sendToLog(cursor: Cursor) {
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nazwisko = cursor.getString(1)
            val pesel = cursor.getString(cursor.getColumnIndex("PESEL"))
            val dataUr = cursor.getString(cursor.getColumnIndex("DATAUR"))
            val wzrost = cursor.getDouble(4)
            Log.d("DB", id.toString() + "\t" + nazwisko + "\t" + pesel + "\t" + dataUr + "\t" + wzrost)
        }
    }

    private fun sendToLog2(cursor: Cursor) {
        while (cursor.moveToNext()) {
            val id = cursor.getDouble(0)
            Log.d("DB", id.toString() + "")
        }
    }


}
