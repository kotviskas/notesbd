package com.dvach.lab2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_acc.*
import kotlinx.android.synthetic.main.activity_create_acc.emailText
import kotlinx.android.synthetic.main.activity_create_acc.passwordText
import kotlinx.android.synthetic.main.activity_login.*


class CreateAccActivity : AppCompatActivity() {
    lateinit var sPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)
        var user=User()

      //  var kaef: Boolean = 0


        addUserBtn.setOnClickListener {

            user.email = emailText.text.toString();
            user.name = nameText.text.toString();
            user.password = passwordText.text.toString();
                AppDatabase.getDatabase(this).userDao().insert(user)
            saveText()
            val i: Intent
            i = Intent(this, MainActivity::class.java)
            startActivity(i)
            }

        loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    fun saveText() {
        sPref = getSharedPreferences("kek", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putString("userEmail", emailText.text.toString())
        ed.putString("userPassword", passwordText.text.toString())
        ed.apply()
        //Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }
}
