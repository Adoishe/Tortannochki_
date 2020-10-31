package com.adoishe.tortannochki

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

public class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

//    private final Context fContext;
    var fContext : Context = context
//    private static final String DATABASE_NAME = "cat_database.db";

//    public static final String TABLE_NAME = "cattable";

//    DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, 1);
//        fContext = context;
//    }

    init {

        //  Log.d("table", CREATE_TABLE_PRESSURES)
    }

    fun createTables(db: SQLiteDatabase){
        var resCount    : Int = 8
        var resid       : Int = 0
        var resIndex    : Int = 0
        var resou       : Resources  = fContext.getResources()

        fun doTable(tableName : String , resId : Int){

            // Добавляем записи в таблицу
            var values      : ContentValues = ContentValues()
            // Получим массив строк из ресурсов
            val resou       : Resources     = fContext.getResources()
            val arrValues   : Array<String> = resou.getStringArray(resId)
            // проходим через массив и вставляем записи в таблицу
            val  length     : Int = arrValues.count();
            var i           : Int = 0

            db.execSQL("CREATE TABLE "
                            + tableName
                            + " ("
//                            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "_id BLOB PRIMARY KEY DEFAULT (randomblob(16)),"
                            + "title TEXT"
                            + ") WITHOUT ROWID;")

            for ( i in  0 ..length - 1) {

                values.put("title", arrValues[i])
                Log.d("write", arrValues[i])
                db.insert(tableName, null, values);
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
            ,"Tastes"
        )

        for (resId  in 0..resCount-1){

            resIndex = resou.getIdentifier(tables[resId], "array",  "com.adoishe.tortannochki")

            doTable(tables[resId] , resIndex)
        }

        // profile
        db.execSQL("CREATE TABLE "
                + "Profile"
                + " ("
                + "_id BLOB PRIMARY KEY DEFAULT (randomblob(16)),"
                + ",name TEXT"
                + ",PhoneNumber TEXT"
                + ") WITHOUT ROWID;")
        //Addresses
        db.execSQL("CREATE TABLE "
                + "Addresses"
                + " ("
                + "_id BLOB PRIMARY KEY DEFAULT (randomblob(16)),"
                + ",Address TEXT"
                + ",ProfileId BLOB"
                + ",AddressName TEXT"
                + ") WITHOUT ROWID;")
        // Orders
        db.execSQL("CREATE TABLE "
                + "Orders"
                + " ("
                + "_id BLOB PRIMARY KEY DEFAULT (randomblob(16)),"
                + ",AddressId BLOB"
                + ",ProfileId BLOB"
                + ",Timeto TEXT"
                + ",Comment TEXT"
                + ") WITHOUT ROWID;")

    }

    fun deleteTables(db: SQLiteDatabase){
        var resCount    : Int = 8
        var resid       : Int = 0
        var resIndex    : Int = 0

        var tables      : Array<String> = arrayOf(
            "Decor"
            ,"OtherSweets"
            ,"ChocolateFigures"
            ,"CaramelFigures"
            ,"TortTypes"
            ,"OneStoreySizes"
            ,"MultyStoreySizes"
            ,"Tastes"
        )

        for (resId  in 0..resCount - 1){
            db.execSQL("DROP TABLE IF EXISTS " + tables[resId])
        }
    }

    public fun queryToArrContentValues(db: SQLiteDatabase, selectQuery : String): ArrayList<ContentValues>{
        var arrContentValues                = ArrayList<ContentValues>()
        val c                       = db.rawQuery(selectQuery, null)

        if (c.moveToFirst())
            do {
                var contentVal          : ContentValues = ContentValues()
                // запись в формат структуры
                DatabaseUtils.cursorRowToContentValues(c, contentVal)
                // структру в массив структуру
                arrContentValues.add(contentVal)

            } while (c.moveToNext())

        c.close()
        db.close()


        return arrContentValues

    }

    public fun arrContentValuesToArrayListString(arrCV: ArrayList<ContentValues>, name : String): ArrayList<String>{
        val arrStrings: ArrayList<String>   = ArrayList()

        for (componentCV in arrCV ){
            arrStrings.add(componentCV.getAsString(name))
        }


        return arrStrings
    }

    public fun getProfileCV(db: SQLiteDatabase): ContentValues{

        var contentVal          : ContentValues = ContentValues()
        var arrContentValues     = ArrayList<ContentValues>()

        arrContentValues = queryToArrContentValues(db , "Select name , PhoneNumber from Profile;")

        if (arrContentValues.count() > 0)
            return arrContentValues[0]

        return contentVal
    }

    public fun componentsByName(db: SQLiteDatabase, name : String): ArrayList<String>{

        val selectQuery                     = "Select title from $name;"
       // val c                       = db.rawQuery(selectQuery, null)
        var arrContentValues                = ArrayList<ContentValues>()
        var arrStrings: ArrayList<String>   = ArrayList()

        arrContentValues = queryToArrContentValues(db , selectQuery)

        arrStrings = arrContentValuesToArrayListString(arrContentValues , "title")

        return arrStrings
        /*
        for (componentCV in arrContentValues ){
            arrStrings.add(componentCV.getAsString("title"))
        }


         */

        /*
        val selectQuery                     = "Select title from $name;"
        val c                       = db.rawQuery(selectQuery, null)
        var arrContentValues                = ArrayList<ContentValues>()
        val arrStrings: ArrayList<String>   = ArrayList()

        if (c.moveToFirst())
            do {
                var contentVal          : ContentValues = ContentValues()
                // запись в формат структуры
                DatabaseUtils.cursorRowToContentValues(c, contentVal)
                // структру в массив структуру
                arrContentValues.add(contentVal)

            } while (c.moveToNext())

        c.close()
        db.close()
*/






    }

    override fun onCreate(db: SQLiteDatabase){
        createTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        deleteTables(db)
//        // TODO Auto-generated method stub
//        Log.w("TestBase", "Upgrading database from version " + oldVersion
//                + " to " + newVersion + ", which will destroy all old data");
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
        createTables(db)
    }

    companion object {

        var arrTables                   = ArrayList<ContentValues>()
        var DATABASE_NAME               = "tortannochki_db"
        private val DATABASE_VERSION    = 1
        private val TABLE_PRESSURES     = "pressures"
        private val KEY_ID              = "id"
        private val KEY_PRESSURE        = "pressure"
        private val KEY_PERIOD          = "period"

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
                )
    }

}

class MainActivity (private var databaseHelper: DatabaseHelper? = null): AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        databaseHelper  = DatabaseHelper(this)

        val db   = databaseHelper!!.writableDatabase

//        databaseHelper!!.createTables()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
