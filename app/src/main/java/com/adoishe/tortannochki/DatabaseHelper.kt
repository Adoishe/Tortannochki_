package com.adoishe.tortannochki

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

//public class DatabaseHelper(context: Context, var db: SQLiteDatabase?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
public class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var fContext : Context = context

    init {

        //  Log.d("table", CREATE_TABLE_PRESSURES)
    }

    fun createProfile(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE "
                + "Profiles"
                + " ("
                + "_id BLOB PRIMARY KEY DEFAULT (randomblob(16))"
                + ",name TEXT"
                + ",PhoneNumber TEXT"
                + ",jsonObject TEXT"
            //+ ") WITHOUT ROWID;")
                + ")  ;")

    }

    fun createAddresses(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE "
                + "Addresses"
                + " ("
                + "_id BLOB PRIMARY KEY DEFAULT (randomblob(16))"
                + ",Address TEXT"
                + ",ProfileId BLOB"
                + ",AddressName TEXT"
                ///+ ") WITHOUT ROWID;")
                + ") ;")

    }

    fun createOrders(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE "
                + "Orders"
                + " ("
                + "_id BLOB PRIMARY KEY DEFAULT (randomblob(16))"
                + ",AddressId BLOB"
                + ",ProfileId BLOB"
                + ",Timeto TEXT"
                + ",Comment TEXT"
               // + ") WITHOUT ROWID;")
                + ") ;")

    }

    fun createTables(db: SQLiteDatabase?){

      //  val db = this.writableDatabase

        var resCount    : Int = 8
        var resid       : Int = 0
        var resIndex    : Int = 0
        var resou       : Resources = fContext.resources

        fun doTable(tableName : String , resId : Int){

            // Добавляем записи в таблицу
            var values      : ContentValues = ContentValues()
            // Получим массив строк из ресурсов
            val resou       : Resources = fContext.getResources()
            val arrValues   : Array<String> = resou.getStringArray(resId)
            // проходим через массив и вставляем записи в таблицу
            val  length     : Int = arrValues.count();
            var i           : Int = 0

            db?.execSQL("CREATE TABLE "
                    + tableName
                    + " ("
//                            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "_id BLOB PRIMARY KEY DEFAULT (randomblob(16)),"
                    + "title TEXT"
                   // + ") WITHOUT ROWID;")
                    + ") ;")

            for ( i in  0 ..length - 1) {

                values.put("title", arrValues[i])
                Log.d("write", arrValues[i])
                db?.insert(tableName, null, values);
            }
        }

        var tables : Array<String>

        tables = arrayOf(
            "Decor"
            ,"OtherSweets"
            ,"ChocolateFigures"
            ,"CaramelFigures"
            ,"TortTypes"
            ,"OneStoreySizes"
            ,"MultiStoreySizes"
            //,"Addresses"
            ,"Tastes"
        )

        // создаются тблицы наоснове ресурсов
        for (resId  in 0..resCount-1){

            resIndex = resou.getIdentifier(tables[resId], "array",  "com.adoishe.tortannochki")

            doTable(tables[resId] , resIndex)
        }

        // profile
        createProfile(db)

        //Addresses
        createAddresses(db)

        // Orders
        createOrders(db)

    }

    fun deleteTables(db: SQLiteDatabase?) {

        var resCount    : Int           = 11
        var resid       : Int           = 0
        var resIndex    : Int           = 0
        var tables      : Array<String> = arrayOf(
            "Decor"
            ,"OtherSweets"
            ,"ChocolateFigures"
            ,"CaramelFigures"
            ,"TortTypes"
            ,"OneStoreySizes"
            ,"MultiStoreySizes"
            ,"Tastes"
            ,"Addresses"
            ,"Orders"
            ,"Profiles"
        )

        for (resId  in 0..resCount - 1){
            db?.execSQL("DROP TABLE IF EXISTS " + tables[resId])
        }

    }

    fun queryToArrContentValues(selectQuery : String): ArrayList<ContentValues>{

        val db                      = this.writableDatabase
        var arrContentValues        = ArrayList<ContentValues>()
        var  c : Cursor?            = null

        try {
             c = db.rawQuery(selectQuery, null)
        } catch (e: Exception){

            println(e.message)

            throw e
        }

        if (c.moveToFirst())
            do {
                var contentVal : ContentValues = ContentValues()
                // запись в формат структуры
                DatabaseUtils.cursorRowToContentValues(c, contentVal)
                // структру в массив структуру
                arrContentValues.add(contentVal)

            } while (c.moveToNext())

        c.close()
        db.close()

        return arrContentValues

    }

    fun arrContentValuesToArrayListString(arrCV: ArrayList<ContentValues>, name : String): ArrayList<String>{
        val arrStrings: ArrayList<String>   = ArrayList()

        for (componentCV in arrCV ){
            arrStrings.add(componentCV.getAsString(name))
        }

        return arrStrings
    }

    public fun getProfileCV(): ContentValues {

        var contentVal          : ContentValues = ContentValues()
        var arrContentValues    = ArrayList<ContentValues>()
        val db                  = this.writableDatabase
        arrContentValues        = queryToArrContentValues( "Select hex(_id) as _id, name , PhoneNumber , jsonObject from Profiles;")

        if (arrContentValues.count() > 0)
            return arrContentValues[0]

        db.close()

        return contentVal
    }

    public fun componentsByName( name : String): ArrayList<String>{

        val db                                 = this.writableDatabase
        val selectQuery                     = "Select title from $name;"
        var arrContentValues                = ArrayList<ContentValues>()
        var arrStrings: ArrayList<String>   = ArrayList()
        arrContentValues                    = queryToArrContentValues(selectQuery)
        arrStrings                          = arrContentValuesToArrayListString(arrContentValues , "title")

        db.close()

        return arrStrings
    }

    override fun onCreate(db: SQLiteDatabase?){
        createTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        deleteTables(db)
        createTables(db)
    }

    companion object {

        var arrTables                   = ArrayList<ContentValues>()
        var DATABASE_NAME               = "tortannochki_db"
        private val DATABASE_VERSION    = 3
        private val TABLE_PRESSURES     = "pressures"
        private val KEY_ID              = "id"
        private val KEY_PRESSURE        = "pressure"
        private val KEY_PERIOD          = "period"

            /*
        private val CREATE_TABLE_PRESSURES = ("CREATE TABLE "
                + TABLE_PRESSURES
                + "("
                + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PRESSURE
                + " REAL,"
                + KEY_PERIOD
                + " LONG "
                + ");"
                )
        private val DELETE_TABLE_PRESSURES = ("DELETE FROM "
                + TABLE_PRESSURES
                + ";"
                )
        private val VACUUM_TABLE_PRESSURES = ("VACUUM "
                + ";"
                )
        private val COUNT_TABLE_PRESSURES = ("SELECT COUNT(*)  as COUNT FROM "
                + TABLE_PRESSURES
                + ";"
                )*/
    }

}