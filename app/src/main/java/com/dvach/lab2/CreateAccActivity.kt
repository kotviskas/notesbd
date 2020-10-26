package com.dvach.lab2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_acc.*


class CreateAccActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)
        var user=User()


        addUserBtn.setOnClickListener {
            user.email = emailText.text.toString();
            user.name = nameText.text.toString();
            user.password = passwordText.text.toString();
                AppDatabase.getDatabase(this).userDao().insert(user)
            val i: Intent
            i = Intent(this, MainActivity::class.java)
            startActivity(i)
            }
    }
}
