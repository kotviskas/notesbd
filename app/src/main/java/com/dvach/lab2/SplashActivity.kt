package com.dvach.lab2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class SplashActivity : AppCompatActivity() {
    lateinit var sPref: SharedPreferences
    var savedEmail: String? = null
    var savedPassword: String? = null
    var kaef: Boolean = false
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadText()
        if (kaef == true) {
            user = AppDatabase.getDatabase(this).userDao()
                .getUser(savedEmail!!, savedPassword!!)
            if (user != null) {
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("user", user)
                startActivity(i)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

    fun loadText() {
        sPref = getSharedPreferences("kek", Context.MODE_PRIVATE)
        if (sPref.contains("userEmail") && sPref.contains("userPassword")) {

            savedEmail = sPref.getString("userEmail", "")
            savedPassword = sPref.getString("userPassword", "")
            kaef = true
            //   Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show()
        }

    }
}
