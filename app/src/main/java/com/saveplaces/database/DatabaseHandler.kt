package com.saveplaces.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.saveplaces.models.SavePlaceModel

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1 // Database version
        private const val DATABASE_NAME = "SavePlacesDatabase" // Database name
        private const val TABLE_Save_PLACE = "SavePlacesTable" // Table Name

        //All the Columns names
        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_Save_PLACE_TABLE = ("CREATE TABLE " + TABLE_Save_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)")
        db?.execSQL(CREATE_Save_PLACE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_Save_PLACE")
        onCreate(db)
    }

    /**
     * Function to insert a Save Place details to SQLite Database.
     */
    fun addSavePlace(savePlace: SavePlaceModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, savePlace.title) // SavePlaceModelClass TITLE
        contentValues.put(KEY_IMAGE, savePlace.image) // SavePlaceModelClass IMAGE
        contentValues.put(
            KEY_DESCRIPTION,
            savePlace.description
        ) // SavePlaceModelClass DESCRIPTION
        contentValues.put(KEY_DATE, savePlace.date) // SavePlaceModelClass DATE
        contentValues.put(KEY_LOCATION, savePlace.location) // SavePlaceModelClass LOCATION
        contentValues.put(KEY_LATITUDE, savePlace.latitude) // SavePlaceModelClass LATITUDE
        contentValues.put(KEY_LONGITUDE, savePlace.longitude) // SavePlaceModelClass LONGITUDE

        // Inserting Row
        val result = db.insert(TABLE_Save_PLACE, null, contentValues)
        //2nd argument is String containing nullColumnHack  

        db.close() // Closing database connection
        return result
    }

    /**
     * Function to read all the list of Save Places data which are inserted.
     */
    fun getSavePlacesList(): ArrayList<SavePlaceModel> {

        // A list is initialize using the data model class in which we will add the values from cursor.
        val savePlaceList: ArrayList<SavePlaceModel> = ArrayList()

        val selectQuery = "SELECT  * FROM $TABLE_Save_PLACE" // Database select query

        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val place = SavePlaceModel(
                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                            cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE))
                    )
                    savePlaceList.add(place)

                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return savePlaceList
    }

    /**
     * Function to update record
     */
    fun updateSavePlace(savePlace: SavePlaceModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, savePlace.title) // SavePlaceModelClass TITLE
        contentValues.put(KEY_IMAGE, savePlace.image) // SavePlaceModelClass IMAGE
        contentValues.put(
            KEY_DESCRIPTION,
            savePlace.description
        ) // SavePlaceModelClass DESCRIPTION
        contentValues.put(KEY_DATE, savePlace.date) // SavePlaceModelClass DATE
        contentValues.put(KEY_LOCATION, savePlace.location) // SavePlaceModelClass LOCATION
        contentValues.put(KEY_LATITUDE, savePlace.latitude) // SavePlaceModelClass LATITUDE
        contentValues.put(KEY_LONGITUDE, savePlace.longitude) // SavePlaceModelClass LONGITUDE

        // Updating Row
        val success =
            db.update(TABLE_Save_PLACE, contentValues, KEY_ID + "=" + savePlace.id, null)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    /**
     * Function to delete save place details.
     */
    fun deleteSavePlace(savePlace: SavePlaceModel): Int {
        val db = this.writableDatabase
        // Deleting Row
        val success = db.delete(TABLE_Save_PLACE, KEY_ID + "=" + savePlace.id, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}
