package com.jnpablos.practica_1

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jnpablos.practica_1.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

private const val EMPTY = ""
private const val SPACE = " "

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var users: MutableList<User> = mutableListOf()
    //Comentario de prueba
    private var fechaNacimiento: String = ""
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val format = "MM/dd/yyyy"
                    val sdf = SimpleDateFormat(format, Locale.US)
                    fechaNacimiento = sdf.format(cal.time).toString()
                    mainBinding.dateTextView.setText(fechaNacimiento)

                }

        mainBinding.dateTextView.setOnClickListener {
            DatePickerDialog(
                    this,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        mainBinding.saveButton.setOnClickListener {

            val namee = mainBinding.nameEditText.text.toString()
            val email = mainBinding.emailEditText.text.toString()
            val password = mainBinding.passwordEditText.text.toString()
            val repPassword = mainBinding.repPaswordEditText.text.toString()
            val city = mainBinding.spciudad.selectedItem.toString()
            val genre = if (mainBinding.femaleRadioButton.isChecked) getString(R.string.female) else getString(R.string.male)
            var hobbies = if (mainBinding.danceCheckBox.isChecked) getString(R.string.dance) + SPACE else EMPTY
            hobbies += if (mainBinding.eatCheckBox.isChecked) getString(R.string.eat) + SPACE else EMPTY
            hobbies += if (mainBinding.readCheckBox.isChecked) getString(R.string.read) + SPACE else EMPTY
            hobbies += if (mainBinding.sportCheckBox.isChecked) getString(R.string.sport) + SPACE else EMPTY

            if (email.isNotEmpty() && namee.isNotEmpty() && fechaNacimiento.isNotEmpty() && password.isNotEmpty() && repPassword.isNotEmpty() && city.isNotEmpty()) {
                if (password == repPassword) {

                    saveUser(namee, email, password, genre, hobbies, city, fechaNacimiento)
                    mainBinding.repPasswordTextLayout.error = null
                    cleanViews()

                } else {
                    mainBinding.repPasswordTextLayout.error = getString(R.string.password_error)
                }
            } else
                Toast.makeText(this, getString(R.string.data_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun cleanViews() {
        with(mainBinding) {
            nameEditText.setText(EMPTY)
            emailEditText.setText(EMPTY)
            passwordEditText.setText(EMPTY)
            repPaswordEditText.setText(EMPTY)
            femaleRadioButton.isChecked = true
            danceCheckBox.isChecked = false
            sportCheckBox.isChecked = false
            eatCheckBox.isChecked = false
            readCheckBox.isChecked = false
            spciudad.setSelection(0)
            dateTextView.setText(EMPTY)
        }
    }

    private fun saveUser(name: String, email: String, password: String, genre: String, hobbies: String, city: String, fechaNacimiento: String) {
        val newUser = User(name, email, password, genre, hobbies, city, fechaNacimiento)
        users.add(newUser)
        printUserData()
    }

    private fun printUserData() {
        var info = getString(R.string.info)
        for (user in users)
            info = info + "\n\n"+ getString(R.string.name) + ": " + user.name + "\nEmail: " + user.email + "\n" + getString(R.string.genre) + user.genre + "\n" + getString(R.string.hobbies) + user.hobbies + "\n" + getString(R.string.city) + user.city + "\n" + getString(R.string.date_of_bird) + user.fechaNacimiento + "\n-------------------"
        mainBinding.infoTextView.text = info
    }
}