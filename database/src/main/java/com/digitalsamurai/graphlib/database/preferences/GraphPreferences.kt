package com.digitalsamurai.graphlib.database.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class GraphPreferences(private val context : Context) {

    private val preferences : SharedPreferences
    init {
        preferences = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
    }

    var lastOpenedGraph : String? by preferences.nullableString(preferences,null)






    //-------------DELEGATORS-----------

    //Взял из своего старого кода, а туда добавил из какого-то гайда по делегатам.
    //Когда-нибудь я научусь использовать делегаты и рефлексию в должной мере
    fun SharedPreferences.string(preferences: SharedPreferences,
                                 defaultValue : String = "",
                                 key : (KProperty<*>)-> String = KProperty<*>::name) : ReadWriteProperty<Any,String>
        = object : ReadWriteProperty<Any,String>{
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return preferences.getString(key(property),defaultValue)!!
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            preferences.edit().putString(key(property),value).apply()
        }
    }


    private fun SharedPreferences.nullableString(pref : SharedPreferences,
                                                 defaultValue: String? = null,
                                                 key: (KProperty<*>) -> String = KProperty<*>::name) : ReadWriteProperty<Any,String?> =
        object :ReadWriteProperty<Any,String?>{
            override fun getValue(thisRef: Any, property: KProperty<*>): String? {
                return pref.getString(key(property),null)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
                pref.edit().putString(key(property), value).apply()
            }
        }

    private fun SharedPreferences.boolean(pref : SharedPreferences,
                                          defaultValue: Boolean = false,
                                          key: (KProperty<*>) -> String = KProperty<*>::name) : ReadWriteProperty<Any,Boolean> =
        object :ReadWriteProperty<Any,Boolean>{
            override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
                return pref.getBoolean(key(property),defaultValue)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
                pref.edit().putBoolean(key(property), value).apply()
            }
        }

    companion object{
        const val PREFERENCE_KEY = "GraphLibPreferences"
    }
}