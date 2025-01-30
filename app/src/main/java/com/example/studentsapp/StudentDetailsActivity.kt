package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var student: Student
    private lateinit var studentNameTextView: TextView
    private lateinit var studentIdTextView: TextView
    private lateinit var studentEmailTextView: TextView
    private lateinit var studentCourseTextView: TextView
    private lateinit var studentImageView: ImageView
    private lateinit var studentCheckBox: CheckBox
    private lateinit var backButton: Button
    private lateinit var editButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        student = intent.getSerializableExtra("student") as Student

        studentNameTextView = findViewById(R.id.studentNameTextView)
        studentIdTextView = findViewById(R.id.studentIdTextView)
        studentEmailTextView = findViewById(R.id.studentEmailTextView)
        studentCourseTextView = findViewById(R.id.studentCourseTextView)
        studentImageView = findViewById(R.id.studentImageView)
        studentCheckBox = findViewById(R.id.studentCheckBox)
        backButton = findViewById(R.id.backButton)
        editButton = findViewById(R.id.editButton)

        updateUI(student)

        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student", student)
            startActivityForResult(intent, EDIT_STUDENT_REQUEST)
        }

        backButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("updatedStudent", student)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun updateUI(updatedStudent: Student) {
        student = updatedStudent
        studentNameTextView.text = student.name
        studentIdTextView.text = student.id
        studentEmailTextView.text = student.email
        studentCourseTextView.text = student.course
        studentImageView.setImageResource(R.drawable.student_default)
        studentCheckBox.isChecked = student.isChecked
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_STUDENT_REQUEST && resultCode == Activity.RESULT_OK) {
            val updatedStudent = data?.getSerializableExtra("updatedStudent") as? Student
            val deleteStudentId = data?.getStringExtra("deleteStudentId")

            if (deleteStudentId != null) {
                val resultIntent = Intent().apply {
                    putExtra("deleteStudentId", deleteStudentId)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                return
            }

            if (updatedStudent != null) {
                updateUI(updatedStudent)
            }
        }
    }

    companion object {
        const val EDIT_STUDENT_REQUEST = 2
    }
}
