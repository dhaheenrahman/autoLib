package com.example.myapplication

//import QRCodeScannerActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started)

        val getStartedButton=findViewById<Button>(R.id.button)
        getStartedButton.setOnClickListener{
            val intent=Intent(this,Login::class.java)
            startActivity(intent)
        }

//        val sharedPreferences = getSharedPreferences("my_app", Context.MODE_PRIVATE)
//        val isLoggedin =  sharedPreferences.getBoolean("is_logged_in",false)
//        if(isLoggedin){
//            val intent = Intent(this, QRCodeScannerActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//
//        var email = findViewById<EditText>(R.id.mailId)
//        var pass = findViewById<EditText>(R.id.password)
//
//        findViewById<Button>(R.id.button2).setOnClickListener {
//            if(email.text.isEmpty() || pass.text.isEmpty()){
//                Toast.makeText(this,"Email and password cannot be empty",Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }
//
//            val url = "http://192.168.31.195:8000/dhaheen/login"
//
//            val jsonBody = JSONObject()
//            jsonBody.put("admno",  email.text)
//            jsonBody.put("password", pass.text )
//
//            val jsonObjectRequest = object : JsonObjectRequest(
//                Method.POST, url, jsonBody,
//                Response.Listener<JSONObject> { response ->
//                    if(response.getString("status").equals("success")){
//                        val sharedPreferences = getSharedPreferences("my_app", Context.MODE_PRIVATE)
//                        val editor = sharedPreferences.edit()
//                        editor.putString("token", response.getString("token"))
//                        editor.putBoolean("is_logged_in", true)
//                        editor.apply()
//                        val intent = Intent(this, Home::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    else{
//                        Toast.makeText(this,"Invalid credentials please try again",Toast.LENGTH_LONG).show()
//                    }
//                },
//                Response.ErrorListener { error ->
//                    Toast.makeText(this,"Unable to reach server",Toast.LENGTH_LONG).show()
//                }
//            ) {
//                override fun getHeaders(): Map<String, String> {
//                    val headers = HashMap<String, String>()
//                    headers["Content-Type"] = "application/json"
//                    return headers
//                }
//            }
//
//            val requestQueue = Volley.newRequestQueue(this)
//            requestQueue.add(jsonObjectRequest)
//        }

    }




}