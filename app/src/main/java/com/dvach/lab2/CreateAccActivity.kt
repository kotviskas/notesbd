package com.dvach.lab2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_acc.*
import kotlinx.android.synthetic.main.activity_create_acc.emailText
import kotlinx.android.synthetic.main.activity_create_acc.passwordText



class CreateAccActivity : AppCompatActivity() {
    lateinit var sPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)
        var user=User()




        addUserBtn.setOnClickListener {
            if (AppDatabase.getDatabase(this).userDao().getUserByEmail(emailText.text.toString()) == null) {
                var validation = InputValidation(this)
                if (validation.isInputEditTextEmail(
                        emailText,
                        emaiInputLayout,
                        "Неправильный e-mail"
                    ) && validation.isInputEditTextFilled(
                        nameText,
                        textInputLayout2,
                        "Введите свое имя"
                    )
                    && validation.isInputEditTextFilled(
                        passwordText,
                        textInputLayout3,
                        "Введите пароль"
                    ) && validation.isInputEditTextFilled(
                        repasswordEditText,
                        textInputLayout4,
                        "Повторите пароль"
                    )
                    && validation.isInputEditTextMatches(
                        passwordText,
                        repasswordEditText,
                        textInputLayout4,
                        "Пароли не совпадают"
                    )
                ) {

                    user.email = emailText.text.toString();
                    user.userName = nameText.text.toString();
                    user.password = passwordText.text.toString();
                    AppDatabase.getDatabase(this).userDao().insert(user)
                    saveText()
                    val i = Intent(this, MainActivity::class.java)
                    i.putExtra("user", user)
                    startActivity(i)
                }
            }
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
