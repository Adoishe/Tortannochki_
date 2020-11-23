package com.adoishe.tortannochki

import android.content.ContentValues
import android.content.Context
import org.json.JSONObject

class Profile {

    private lateinit var name: String
    private  var phone: Int = 0
    private lateinit var jsonObject: JSONObject

    fun  getName () : String {

        return this.name
    }

    fun  getphone () : Int {

        return this.phone
    }

    fun  getJSONObject () : JSONObject {

        return this.jsonObject
    }

    fun  setName (name : String)  {

        this.name = name
    }

    fun  setPhone (phone:Int)  {

         this.phone = phone
    }

    fun  setJSONObject (jsonObject:JSONObject)  {

         this.jsonObject = jsonObject
    }

    fun fillProfile(contentValue: ContentValues) {


        try {
            name        = contentValue.getAsString("name")
        }catch (e : Exception)
        {
            name        = ""
        }

        try {
            phone       = contentValue.getAsInteger("phone")
        }catch (e : Exception)
        {
            phone       = 0
        }

        try {

            val jsonString  = contentValue.getAsString("jsonObject")
            jsonObject = JSONObject(jsonString)

        }catch (e : Exception)
        {
            this.jsonObject = JSONObject()
        }





    }

    fun readProfile (context : Context) {
        val databaseHelper  = DatabaseHelper(context)
        val profileCV       = databaseHelper.getProfileCV()

        fillProfile(profileCV)
    }


}