package com.dvach.lab2

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about_note.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.activity_note.backImg
import kotlinx.android.synthetic.main.dialog_layout.view.*
import java.util.*
import kotlin.collections.ArrayList


class NoteActivity : AppCompatActivity() {
    var note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        lottieAnimationView.playAnimation()


        var kaef: Boolean = false
        var list2 = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.prioritet)))
        var category = Category()
        var categoryList: ArrayList<String> =
            AppDatabase.getDatabase(this).CategoryDao().getAllNames() as ArrayList<String>


        val intent = intent
        if (intent.hasExtra("note")) {
            note = intent.getSerializableExtra("note") as Note
            nameEditText.setText(note.name)
            noteTextEditText.setText(note.text)
            kaef = true
            var flag : Int = 0
            var index:Int = 0
            list2.forEach {
                if(list2[flag] == note.prioritet){
                    index = flag
                }
                flag++
            }
            var str:String = list2[0]
            list2[index] = str
            list2[0] = note.prioritet
           // list2.add(0, note.prioritet)
            flag = 0
            index = 0
            categoryList.forEach {
                if(categoryList[flag] == note.category){
                    index = flag
                }
                flag++
            }
            str = categoryList[0]
            categoryList[index] = str
            categoryList[0] = note.category

            //categoryList.add(0,note.category)
        }


        // categorySpinner.selectedItem = note.category




        var spinner: Spinner = findViewById(R.id.prioritetSpinner)
        var adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.spinnertext, list2
        )
        adapter.setDropDownViewResource(R.layout.spinnertext)
        spinner.adapter = adapter

        var spinner2: Spinner = findViewById(R.id.categorySpinner)

        val adapter2: ArrayAdapter<String> = ArrayAdapter<String>(
            this, R.layout.spinnertext, categoryList
        )

        adapter2.setDropDownViewResource(R.layout.spinnertext)
        spinner2.adapter = adapter2

        if (kaef == true) {
            var list2: ArrayList<String>

        }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        addDate.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                dateEditText.setText("" + dayOfMonth + "." + month + "." + year)

            }, year, month, day)
                dpd.show()


        }

        addCategory.setOnClickListener {
            val dialog = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)

            val builder = AlertDialog.Builder(this)
                .setView(dialog)
            val alertDialog = builder.show()

            dialog.saveTextView.setOnClickListener {
                alertDialog.dismiss()
                category.categoryName = dialog.addCategoryEditText.text.toString()
                category.categoryUser = intent.getSerializableExtra("user") as User
                AppDatabase.getDatabase(this).CategoryDao().insert(category)
                categoryList.add(category.categoryName)
                adapter2.notifyDataSetChanged();
            }

            dialog.cancelTextView.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        backImg.setOnClickListener { finish() }

        saveNoteBtn.setOnClickListener {
            var validation= InputValidation(this)
            if(validation.isEditTextFilled(nameEditText,"Введите заголовок") && validation.isInputEditTextFilled(noteTextEditText,emaiInputLayout,"Добавьте описание")
                && validation.isSpinnerFilled(spinner2, "")) {
                lottieLayout.visibility = View.VISIBLE
                lottieAnimationView.playAnimation()
                note.name = nameEditText.text.toString()
                note.text = noteTextEditText.text.toString()
                note.date = dateEditText.text.toString()
                note.prioritet = spinner.selectedItem.toString()
                note.category = spinner2.selectedItem.toString()
                note.user = intent.getSerializableExtra("user") as User
                if (note.prioritet == "Срочно") {
                    note.color = resources.getColor(R.color.redCard)
                }
                if (note.prioritet == "Важно") {
                    note.color = resources.getColor(R.color.yellowCard)
                }
                if (note.prioritet == "Нужно") {
                    note.color = resources.getColor(R.color.greenCard)
                }
                if (note.prioritet == "Пофиг") {
                    note.color = resources.getColor(R.color.blueCard)
                }

                AppDatabase.getDatabase(this).NoteDao().insert(note)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

}
