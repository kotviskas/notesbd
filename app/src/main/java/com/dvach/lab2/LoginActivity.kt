package com.dvach.lab2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn.setOnClickListener {
            if (AppDatabase.getDatabase(this).userDao()
                    .getUser(emailText.text.toString(), passwordText.text.toString()) != null
            ) {
                val i: Intent
                i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }

        }
        goToCreateText.setOnClickListener {
            startActivity(Intent(this, CreateAccActivity::class.java))
        }

    }
}
