package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class BookDatabase : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var databaseReference: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookdb)

        val button=findViewById<ImageButton>(R.id.imageButton8)
        button.setOnClickListener{
            val intent= Intent(this,Home::class.java)
            startActivity(intent)
        }

        tableLayout = findViewById(R.id.tableLayout)
        databaseReference = FirebaseDatabase.getInstance().getReference("BookDetails")

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing table rows
//                tableLayout.removeAllViews()

                // Iterate through the database snapshot to retrieve each book entry
                for (bookSnapshot in snapshot.children) {
                    // Get the values of BookNo, BookName, and Author
                    val bookNo = bookSnapshot.child("BookNo").getValue(String::class.java)
                    val bookName = bookSnapshot.child("BookName").getValue(String::class.java)
                    val author = bookSnapshot.child("Author").getValue(String::class.java)

                    // Create a new table row
                    val tableRow = TableRow(this@BookDatabase)

                    // Create TextViews for each column and set the text
                    val bookNoTextView = TextView(this@BookDatabase)
                    bookNoTextView.text = bookNo
                    val bookNameTextView = TextView(this@BookDatabase)
                    bookNameTextView.text = bookName
                    val authorTextView = TextView(this@BookDatabase)
                    authorTextView.text = author

                    // Add TextViews to the table row
                    tableRow.addView(bookNoTextView)
                    tableRow.addView(bookNameTextView)
                    tableRow.addView(authorTextView)

                    // Add the table row to the table layout
                    tableLayout.addView(tableRow)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during data retrieval
                Log.e("Firebase", "Error retrieving data: ${error.message}")
            }
        }

        // Retrieve the data from Firebase when the activity starts
        retrieveDataFromFirebase()
    }

    private fun retrieveDataFromFirebase() {
        // Add the ValueEventListener to the database reference
        databaseReference.addValueEventListener(valueEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the ValueEventListener when the activity is destroyed
        databaseReference.removeEventListener(valueEventListener)
    }
}
