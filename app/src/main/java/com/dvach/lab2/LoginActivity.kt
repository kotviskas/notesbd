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
            var validation= InputValidation(this)

            if (validation.isInputEditTextEmail(emailText,emaiInputLayout,"Введите e-mail") && validation.isInputEditTextFilled(passwordText,textInputLayout3,"Введите пароль")) {
                var user = AppDatabase.getDatabase(this).userDao()
                    .getUser(emailText.text.toString(), passwordText.text.toString())
                if (user != null
                ) {
                    saveText()
                    val i = Intent(this, MainActivity::class.java)
                    i.putExtra("user", user)
                    startActivity(i)
                }
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
