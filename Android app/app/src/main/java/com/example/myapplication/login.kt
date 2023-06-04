package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Login : AppCompatActivity() {
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val loginButton = findViewById<Button>(R.id.button2)


        loginButton.setOnClickListener {

            val username = findViewById<EditText>(R.id.mailId)
            val usernameText = username.text.toString()
            val password = findViewById<EditText>(R.id.password)
            val passwordText = password.text.toString()

            databaseRef = FirebaseDatabase.getInstance().getReference("users")


            databaseRef.orderByChild("username").equalTo(usernameText).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var isLoginSuccessful = false
                    for (snapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null && user.password == passwordText) {
                            isLoginSuccessful = true
                            Toast.makeText(this@Login,"Login Success",Toast.LENGTH_SHORT).show()

                            break
                        }
                    }
                    print(isLoginSuccessful)
                    if (isLoginSuccessful) {
                        val intent = Intent(this@Login, Home::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@Login,"Invalid Credentials",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }
    }

}

