package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class NewStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        val studentNameEditText = findViewById<EditText>(R.id.studentNameEditText)
        val studentIdEditText = findViewById<EditText>(R.id.studentIdEditText)
        val studentEmailEditText = findViewById<EditText>(R.id.studentEmailEditText)
        val studentCourseEditText = findViewById<EditText>(R.id.studentCourseEditText)
        val studentImageView = findViewById<ImageView>(R.id.studentImageView)
        val studentCheckBox = findViewById<CheckBox>(R.id.studentCheckBox)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val backButton = findViewById<Button>(R.id.backButton)

        studentImageView.setImageResource(R.drawable.student_default)

        saveButton.setOnClickListener {
            val name = studentNameEditText.text.toString()
            val id = studentIdEditText.text.toString()
            val email = studentEmailEditText.text.toString()
            val course = studentCourseEditText.text.toString()
            val isChecked = studentCheckBox.isChecked

            if (name.isEmpty() || id.isEmpty() || email.isEmpty() || course.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!id.matches(Regex("\\d+"))) {
                Toast.makeText(this, "ID must contain only numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newStudent = Student(uniqueId = UUID.randomUUID().toString(), id, name, email, course, isChecked)

            val resultIntent = Intent().apply {
                putExtra("newStudent", newStudent)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        cancelButton.setOnClickListener {
            studentNameEditText.text.clear()
            studentIdEditText.text.clear()
            studentEmailEditText.text.clear()
            studentCourseEditText.text.clear()
            studentCheckBox.isChecked = false
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
