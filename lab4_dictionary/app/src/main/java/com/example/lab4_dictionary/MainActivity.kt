package com.example.lab4_dictionary

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DictDatabase
    private lateinit var queryInput: TextInputEditText
    private lateinit var resultDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the database helper
        dbHelper = DictDatabase(this)

        // Bind views from the XML
        queryInput = findViewById(R.id.query_input)
        resultDisplay = findViewById(R.id.result_display)
        val searchButton = findViewById<Button>(R.id.search_action)

        // Set initial text for the result display
        resultDisplay.text = "Enter a word to search the dictionary."

        // Set up the search button click listener
        searchButton.setOnClickListener {
            val word = queryInput.text.toString().trim().lowercase()
            if (word.isEmpty()) {
                resultDisplay.text = "Please enter a word."
                return@setOnClickListener
            }

            // Search for an exact match
            val exactMatch = dbHelper.searchWords(word)
            if (exactMatch != null) {
                val (matchedWord, definition) = exactMatch
                resultDisplay.text = "$matchedWord: $definition"
            } else {
                // If no exact match, search for substring matches
                val substringMatches = dbHelper.searchSubStrings(word)
                if (substringMatches.isNotEmpty()) {
                    val result = substringMatches.joinToString("\n") { (matchedWord, definition) ->
                        "$matchedWord: $definition"
                    }
                    resultDisplay.text = "No exact match found. Related words:\n$result"
                } else {
                    resultDisplay.text = "No matches found for '$word'."
                }
            }
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}