package com.example.myapplication

//import QRCodeScannerActivity
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.util.Date

class BookIssue : AppCompatActivity() {
    private lateinit var database:DatabaseReference
    private lateinit var handler: Handler
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookissue)

        database = FirebaseDatabase.getInstance().getReference("BookIssueTable")
        sharedPrefs = getSharedPreferences("BookIssuePrefs", Context.MODE_PRIVATE)

        val bookNoField : TextInputEditText? = findViewById<TextInputEditText>(R.id.bookNoField)
        val bookNameField : TextInputEditText? = findViewById<TextInputEditText>(R.id.bookNameField)
        val issueDateField : TextInputEditText? = findViewById<TextInputEditText>(R.id.issueDateField)
        val retDateField : TextInputEditText? = findViewById<TextInputEditText>(R.id.retDateField)

        val str:String?=intent.getStringExtra("barcodevalue")
        var ab = str!!.split(",")
        val datee = org.threeten.bp.LocalDate.now()
        val ret = datee.plusDays(30)
        val datestr = datee.toString()
        val retstr = ret.toString()
        ab += datestr
        ab += retstr
        bookNoField?.setText(ab[0])
        bookNameField?.setText(ab[1])
        issueDateField?.setText(ab[2])
        retDateField?.setText(ab[3])

        bookNoField?.isEnabled = false
        bookNameField?.isEnabled = false
        issueDateField?.isEnabled = false
        retDateField?.isEnabled = false

        var uniqueId = sharedPrefs.getInt("lastUniqueId",0) // Get the generated unique ID

        val submitButton=findViewById<Button>(R.id.button2)
        submitButton.setOnClickListener{
            uniqueId+=1

//            val newChildRef = database.push() // Generate a new unique key

            val bookNo = ab[0]
            val bookName = ab[1]
            val issueDate = ab[2]
            val retDate = ab[3]
            val data = BookIssueForm(uniqueId,bookNo,bookName,issueDate,retDate)
            database.child(uniqueId.toString()).setValue(data)

            val editor = sharedPrefs.edit()
            editor.putInt("lastUniqueId", uniqueId)
            editor.apply()

            val intent = Intent(this@BookIssue,Approval::class.java)
            startActivity(intent)

            val visibilityDuration = 2000L
            handler = Handler()
            handler.postDelayed({

                val returnIntent = Intent(this@BookIssue, Home::class.java)
                startActivity(returnIntent)
                finish()
            },visibilityDuration)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the handler to prevent memory leaks
        handler.removeCallbacksAndMessages(null)
    }
}