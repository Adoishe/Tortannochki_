package com.adoishe.tortannochki

import org.json.JSONObject

class Order {

    var bodyJson : JSONObject = JSONObject()
    lateinit var profile: Profile

    constructor(profile: Profile){

        this.profile = profile

    }


    fun save(){

    }

    fun load(){

    }

    fun fillFromHashMap(){

    }
}