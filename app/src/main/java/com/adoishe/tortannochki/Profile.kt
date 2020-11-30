package com.adoishe.tortannochki

import android.content.ContentValues
import android.content.Context
import org.json.JSONObject

class Profile {

    private lateinit var name       : String
    private  var phone              :  Int = 0
    private lateinit var jsonObject : JSONObject
    private lateinit var _id        : String

    fun  getName () : String {

        return this.name
    }

    fun  getphone () : Int {

        return this.phone
    }

    fun  getJSONObject () : JSONObject {

        return this.jsonObject
    }

    fun  setName(name: String)  {

        this.name = name
    }

    fun  setPhone(phone: Int)  {

         this.phone = phone
    }

    fun  setJSONObject(jsonObject: JSONObject)  {

         this.jsonObject = jsonObject
    }

    fun fillProfile(contentValue: ContentValues) {

        try {            _id        = contentValue.getAsString("_id")        }catch (e: Exception)
        {            _id        = ""        }

        try {            name        = contentValue.getAsString("name")        }catch (e: Exception)
        {            name        = ""        }

        try {            phone       = contentValue.getAsInteger("phone")        }catch (e: Exception)
        {            phone       = 0        }

        try {
            val jsonString  = contentValue.getAsString("jsonObject")
            jsonObject = JSONObject(jsonString)
        }catch (e: Exception)
        {            this.jsonObject = JSONObject()        }

    }

    fun cvFromProfile () : ContentValues{

        var cv = ContentValues()

        if  (_id != "")
            cv.put("_id", _id)

        cv.put("name", name)
        cv.put("PhoneNumber", phone)
        cv.put("jsonObject", jsonObject.toString())

        return cv
    }

    fun writeProfile(context: Context) {

       //var cv               = cvFromProfile ()
        val databaseHelper  = DatabaseHelper(context)

        var query : String

        var jsonString = jsonObject.toString().replace("\"", "\"\"")
        //var jsonString = jsonObject.toString()
        name = "\"" + name + "\""

            //name = name.replace("\"", "\\\"")

        if  (_id == "") {

            query = "insert into Profiles (name, PhoneNumber , jsonObject) values($name, $phone , \"$jsonString\"); "

            //databaseHelper.writableDatabase.insert("Profiles", null, cv)
        } else{


            query = "update Profiles  set name = $name , PhoneNumber = $phone , jsonObject = \"$jsonString\" where hex(_id) = \"$_id\"; "
            //val where: String = "_id = $_id"

                // databaseHelper.writableDatabase.update("Profiles", cv , where , null)
        }

        databaseHelper.writableDatabase.execSQL(query)

    }

    fun readProfile(context: Context) {
        val databaseHelper  = DatabaseHelper(context)
        val profileCV       = databaseHelper.getProfileCV()

        fillProfile(profileCV)
    }

    fun readAddresses(context: Context) : ArrayList<ContentValues> {

        val databaseHelper  = DatabaseHelper(context)

        val query = "select Address, AddressName from Addresses where hex(ProfileId) =  \"$_id\";"

        return databaseHelper.queryToArrContentValues(query)

    }


}