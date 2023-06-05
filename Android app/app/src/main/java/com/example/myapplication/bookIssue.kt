package com.example.myapplication

//import QRCodeScannerActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.util.Date

//..
class BookIssue : AppCompatActivity() {
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookissue)

        database = FirebaseDatabase.getInstance().getReference("BookIssueTable")

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

        val submitButton=findViewById<Button>(R.id.button2)
        submitButton.setOnClickListener{
            val bookNo = ab[0]
            val bookName = ab[1]
            val issueDate = ab[2]
            val retDate = ab[3]
            val data = BookIssueForm(bookNo,bookName,issueDate,retDate)
            database.child(bookNo).setValue(data)
        }
    }
}