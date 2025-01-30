package com.example.studentsapp

import StudentsAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var studentsRecyclerView: RecyclerView
    private lateinit var studentsAdapter: StudentsAdapter
    private val studentsList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentsRecyclerView = findViewById(R.id.studentsRecyclerView)
        studentsRecyclerView.layoutManager = LinearLayoutManager(this)

        studentsAdapter = StudentsAdapter(studentsList) { student ->
            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra("student", student)
            startActivityForResult(intent, STUDENT_DETAILS_REQUEST)
        }
        studentsRecyclerView.adapter = studentsAdapter

        findViewById<View>(R.id.addStudentButton).setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivityForResult(intent, ADD_STUDENT_REQUEST)
        }

        loadSampleStudents()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ADD_STUDENT_REQUEST -> {
                    val newStudent = data?.getSerializableExtra("newStudent") as? Student
                    newStudent?.let {
                        studentsList.add(it)
                        studentsAdapter.notifyDataSetChanged()
                    }
                }
                STUDENT_DETAILS_REQUEST -> {
                    val updatedStudent = data?.getSerializableExtra("updatedStudent") as? Student
                    val deleteStudentId = data?.getStringExtra("deleteStudentId")

                    if (deleteStudentId != null) {
                        studentsList.removeAll { it.uniqueId == deleteStudentId }
                        studentsAdapter.notifyDataSetChanged()
                        return
                    }

                    if (updatedStudent != null) {
                        updateStudentInList(updatedStudent)
                    }
                }
            }
        }
    }

    private fun updateStudentInList(updatedStudent: Student) {
        val index = studentsList.indexOfFirst { it.uniqueId == updatedStudent.uniqueId }
        if (index != -1) {
            studentsList[index] = updatedStudent
            studentsAdapter.notifyItemChanged(index)
        }
    }

    private fun loadSampleStudents() {
        studentsList.add(Student(id = "123", name = "John Doe", email = "johndoe@gmail.com", course = "Computer Science", isChecked = false))
        studentsList.add(Student(id = "124", name = "Jane Smith", email = "janesmith@gmail.com", course = "Mathematics", isChecked = false))
        studentsAdapter.notifyDataSetChanged()
    }

    companion object {
        const val ADD_STUDENT_REQUEST = 1
        const val STUDENT_DETAILS_REQUEST = 2
    }
}
