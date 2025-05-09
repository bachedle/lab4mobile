package com.example.lab4_dictionary

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DictDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dictionary.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "words"
        private const val COLUMN_WORD = "word"
        private const val COLUMN_DEFINITION = "definition"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_WORD TEXT PRIMARY KEY, $COLUMN_DEFINITION TEXT)"
        db.execSQL(createTable)

        // Insert sample data
        insertSampleData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun insertSampleData(db: SQLiteDatabase) {
        val sampleData = listOf(
            "apple" to "A fruit that is typically red or green.",
            "banana" to "A long, curved fruit that is yellow.",
            "cat" to "A small domesticated animal.",
            "dog" to "A loyal pet that barks.",
            "elephant" to "A large animal with a trunk."
        )

        sampleData.forEach { (word, definition) ->
            db.execSQL("INSERT INTO $TABLE_NAME ($COLUMN_WORD, $COLUMN_DEFINITION) VALUES ('$word', '$definition')")
        }
    }

    fun searchWords(word: String): Pair<String, String>? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_WORD = ?", arrayOf(word))
        return if (cursor.moveToFirst()) {
            val definition = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEFINITION))
            cursor.close()
            word to definition
        } else {
            cursor.close()
            null
        }
    }

    fun searchSubStrings(word: String): List<Pair<String, String>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_WORD LIKE ?", arrayOf("%$word%"))
        val results = mutableListOf<Pair<String, String>>()
        while (cursor.moveToNext()) {
            val foundWord = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORD))
            val definition = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEFINITION))
            results.add(foundWord to definition)
        }
        cursor.close()
        return results
    }
}