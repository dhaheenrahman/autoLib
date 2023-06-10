package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.studenthome)

        val logoutButton = findViewById<ImageButton>(R.id.imageButton8)
        logoutButton.setOnClickListener {
            // Clear the login status in SharedPreferences
            val sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            // Navigate to the Login activity
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        val bookIssueButton: LinearLayout = findViewById(R.id.linearLayout3)
        bookIssueButton.setOnClickListener {
            val intent = Intent(this, IssueQrScanner::class.java)
            startActivity(intent)
        }
        val bookReturnButton: LinearLayout = findViewById(R.id.linearLayout4)
        bookReturnButton.setOnClickListener {
            val intent = Intent(this, ReturnQrScanner::class.java)
            startActivity(intent)
        }
        val bookReadButton: LinearLayout = findViewById(R.id.linearLayout5)
        bookReadButton.setOnClickListener {
            val intent = Intent(this, ReadQrScanner::class.java)
            startActivity(intent)
        }
        val bookDatabase: LinearLayout = findViewById(R.id.linearLayout1)
        bookDatabase.setOnClickListener {
            val intent = Intent(this, BookDatabase::class.java)
            startActivity(intent)
        }

    }
}