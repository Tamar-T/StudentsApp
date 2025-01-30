package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        student = intent.getSerializableExtra("student") as Student

        val studentNameTextView = findViewById<TextView>(R.id.studentNameTextView)
        val studentIdTextView = findViewById<TextView>(R.id.studentIdTextView)
        val studentEmailTextView = findViewById<TextView>(R.id.studentEmailTextView)
        val studentCourseTextView = findViewById<TextView>(R.id.studentCourseTextView)
        val studentImageView = findViewById<ImageView>(R.id.studentImageView)
        val studentCheckBox = findViewById<CheckBox>(R.id.studentCheckBox)
        val backButton = findViewById<Button>(R.id.backButton)
        val editButton = findViewById<Button>(R.id.editButton)

        // ✅ עדכון ממשק המשתמש - הפונקציה זמינה גם מחוץ ל-onCreate()
        fun updateUI(updatedStudent: Student) {
            student = updatedStudent
            studentNameTextView.text = student.name
            studentIdTextView.text = student.id
            studentEmailTextView.text = student.email
            studentCourseTextView.text = student.course
            studentImageView.setImageResource(R.drawable.student_default)
            studentCheckBox.isChecked = student.isChecked
        }

        updateUI(student)

        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student", student)
            startActivityForResult(intent, EDIT_STUDENT_REQUEST)
        }

        // ✅ מעדכן את הנתונים החוזרים לרשימת הסטודנטים
        backButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("updatedStudent", student)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    // ✅ קבלת הנתונים החדשים ממסך עריכת סטודנט
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_STUDENT_REQUEST && resultCode == RESULT_OK) {
            val updatedStudent = data?.getSerializableExtra("updatedStudent") as? Student
            val deleteStudentId = data?.getStringExtra("deleteStudentId")

            if (deleteStudentId != null) {
                // ✅ שולח ל-MainActivity למחוק מהרשימה ומיד יוצא מהמסך
                val resultIntent = Intent().apply {
                    putExtra("deleteStudentId", deleteStudentId)
                }
                setResult(RESULT_OK, resultIntent)
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
