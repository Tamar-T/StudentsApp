package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    private lateinit var student: Student

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        student = intent.getSerializableExtra("student", Student::class.java)!!

        val studentNameEditText = findViewById<EditText>(R.id.studentNameEditText)
        val studentIdEditText = findViewById<EditText>(R.id.studentIdEditText)
        val studentEmailEditText = findViewById<EditText>(R.id.studentEmailEditText)
        val studentCourseEditText = findViewById<EditText>(R.id.studentCourseEditText)
        val studentImageView = findViewById<ImageView>(R.id.studentImageView)
        val studentCheckBox = findViewById<CheckBox>(R.id.studentCheckBox)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        studentNameEditText.setText(student.name)
        studentIdEditText.setText(student.id)
        studentEmailEditText.setText(student.email)
        studentCourseEditText.setText(student.course)
        studentCheckBox.isChecked = student.isChecked
        studentImageView.setImageResource(R.drawable.student_default)

        saveButton.setOnClickListener {
            val updatedName = studentNameEditText.text.toString()
            val updatedId = studentIdEditText.text.toString()
            val updatedEmail = studentEmailEditText.text.toString()
            val updatedCourse = studentCourseEditText.text.toString()
            val isChecked = studentCheckBox.isChecked


            if (updatedName.isEmpty() || updatedId.isEmpty() || updatedEmail.isEmpty() || updatedCourse.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            student.name = updatedName
            student.id = updatedId
            student.email = updatedEmail
            student.course = updatedCourse
            student.isChecked = isChecked


            val resultIntent = Intent().apply {
                putExtra("updatedStudent", student)
                putExtra("studentUniqueId", student.uniqueId)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        deleteButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("deleteStudentId", student?.uniqueId)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
