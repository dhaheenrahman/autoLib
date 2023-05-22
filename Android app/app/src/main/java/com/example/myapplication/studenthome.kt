package com.example.myapplication

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
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val bookIssueButton: LinearLayout = findViewById(R.id.linearLayout3)
        bookIssueButton.setOnClickListener {
            val intent = Intent(this, QrScanner::class.java)
            startActivity(intent)
        }
        val bookReturnButton: LinearLayout = findViewById(R.id.linearLayout4)
        bookReturnButton.setOnClickListener {
            val intent = Intent(this, QrScanner::class.java)
            startActivity(intent)
        }
        val bookReadButton: LinearLayout = findViewById(R.id.linearLayout5)
        bookReadButton.setOnClickListener {
            val intent = Intent(this, QrScanner::class.java)
            startActivity(intent)
        }
    }
}