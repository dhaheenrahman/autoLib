package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.core.view.View

class Login : AppCompatActivity() {
    private lateinit var databaseRef: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            // User is already logged in, navigate to Home activity
            val intent = Intent(this@Login, Home::class.java)
            startActivity(intent)
            finish()
            return
        }

        val stButton = findViewById<ImageButton>(R.id.imageButton2)
        stButton.setOnClickListener{
            val username = findViewById<EditText>(R.id.mailId)
            username.setHint("Enter your Admission No")

            val loginButton = findViewById<Button>(R.id.button2)

            loginButton.setOnClickListener {
                val username = findViewById<EditText>(R.id.mailId)
                val usernameText = username.text.toString()
                val password = findViewById<EditText>(R.id.password)
                val passwordText = password.text.toString()

                databaseRef = FirebaseDatabase.getInstance().getReference("users")

                databaseRef.orderByChild("username").equalTo(usernameText)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var isLoginSuccessful = false
                            for (snapshot in dataSnapshot.children) {
                                val user = snapshot.getValue(User::class.java)
                                if (user != null && user.password == passwordText) {
                                    isLoginSuccessful = true
                                    Toast.makeText(this@Login, "Login Success", Toast.LENGTH_SHORT).show()

                                    // Save login status in SharedPreferences
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("isLoggedIn", true)
                                    editor.putString("admno", usernameText)
                                    editor.apply()
                                    break
                                }
                            }
                            if (isLoginSuccessful) {
                                val intent = Intent(this@Login, Home::class.java)
                                startActivity(intent)
//                                finish()
                            } else {
                                Toast.makeText(this@Login, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle cancellation
                        }
                    })
            }
        }
//
        val libButton = findViewById<ImageButton>(R.id.imageButton4)
        libButton.setOnClickListener{
            val username = findViewById<EditText>(R.id.mailId)
            username.setHint("Enter your login Id")

            val loginButton = findViewById<Button>(R.id.button2)

            loginButton.setOnClickListener {
                val username = findViewById<EditText>(R.id.mailId)
                val usernameText = username.text.toString()
                val password = findViewById<EditText>(R.id.password)
                val passwordText = password.text.toString()

                databaseRef = FirebaseDatabase.getInstance().getReference("users")

                databaseRef.orderByChild("username").equalTo(usernameText)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var isLoginSuccessful = false
                            for (snapshot in dataSnapshot.children) {
                                val user = snapshot.getValue(User::class.java)
                                if (user != null && user.password == passwordText) {
                                    isLoginSuccessful = true
                                    Toast.makeText(this@Login, "Login Success", Toast.LENGTH_SHORT).show()

                                    // Save login status in SharedPreferences
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("isLoggedIn", true)
                                    editor.apply()

                                    break
                                }
                            }
                            if (isLoginSuccessful) {
                                val intent = Intent(this@Login, LibrarianHome::class.java)
                                startActivity(intent)
//                                finish()
                            } else {
                                Toast.makeText(this@Login, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle cancellation
                        }
                    })
            }
        }






    }

}
