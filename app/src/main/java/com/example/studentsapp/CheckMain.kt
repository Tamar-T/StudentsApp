package com.example.studentsapp

import StudentsAdapter
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class dfgMainActivity : AppCompatActivity() {
    private lateinit var studentsRecyclerView: RecyclerView
    private lateinit var studentsAdapter:  StudentsAdapter
    private val studentsList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentsRecyclerView = findViewById(R.id.studentsRecyclerView)
        studentsRecyclerView.layoutManager = LinearLayoutManager(this)

        studentsAdapter = StudentsAdapter(studentsList) { student ->
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student", student)
            startActivityForResult(intent, EDIT_STUDENT_REQUEST)
        }
        studentsRecyclerView.adapter = studentsAdapter

        findViewById<View>(R.id.addStudentButton).setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivityForResult(intent, ADD_STUDENT_REQUEST)
        }

        loadSampleStudents()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // ✅ בדיקה אם מדובר בהוספת סטודנט חדש
        if (requestCode == ADD_STUDENT_REQUEST && resultCode == RESULT_OK) {
            val newStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getSerializableExtra("newStudent", Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getSerializableExtra("newStudent") as? Student
            }

            if (newStudent != null) {
                studentsList.add(newStudent) // הוספת סטודנט חדש לרשימה
                studentsAdapter.notifyDataSetChanged() // רענון רשימת הסטודנטים
            }
        }

        // ✅ בדיקה אם מדובר בעריכת סטודנט קיים
        else if (requestCode == EDIT_STUDENT_REQUEST) {
            val updatedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getSerializableExtra("updatedStudent", Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getSerializableExtra("updatedStudent") as? Student
            }

            val uniqueId = data?.getStringExtra("studentUniqueId") // קבלת ה-UUID
            val deleteStudentId = data?.getStringExtra("deleteStudentId") // קבלת מזהה סטודנט למחיקה

            // ✅ מחיקת סטודנט מהרשימה
            if (deleteStudentId != null) {
                studentsList.removeAll { it.uniqueId == deleteStudentId }
                studentsAdapter.notifyDataSetChanged()
                return
            }

            // ✅ עדכון סטודנט קיים ברשימה
            if (updatedStudent != null && uniqueId != null) {
                val index = studentsList.indexOfFirst { it.uniqueId == uniqueId }
                if (index != -1) {
                    studentsList[index] = updatedStudent // עדכון הנתונים ברשימה
                    studentsAdapter.notifyItemChanged(index) // עדכון ה-RecyclerView
                }
            }
        }
    }




    private fun loadSampleStudents() {
        studentsList.add(Student(id = "123", name = "John Doe", email = "johndoe@gmail.com", course = "Computer Science", isChecked = false))
        studentsList.add(Student(id = "124", name = "Jane Smith", email = "janesmith@gmail.com", course = "Mathematics", isChecked = false))
        studentsAdapter.notifyDataSetChanged()
    }


    companion object {
        const val ADD_STUDENT_REQUEST = 1
        const val EDIT_STUDENT_REQUEST = 2
    }
}
