package com.dvach.lab2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    lateinit var sPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn.setOnClickListener {
            if (AppDatabase.getDatabase(this).userDao()
                    .getUser(emailText.text.toString(), passwordText.text.toString()) != null
            ) {
                saveText()
                startActivity(Intent(this, MainActivity::class.java))
            }

        }
        goToCreateText.setOnClickListener {
            startActivity(Intent(this, CreateAccActivity::class.java))
        }
    }

    fun saveText() {
        sPref = getSharedPreferences("kek", Context.MODE_PRIVATE)
        val ed: Editor = sPref.edit()
        ed.putString("userEmail", emailText.text.toString())
        ed.putString("userPassword", passwordText.text.toString())
        ed.apply()
        //Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }
}
