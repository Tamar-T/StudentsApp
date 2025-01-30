package com.example.studentsapp

import java.util.UUID

data class Student(
    val uniqueId: String = UUID.randomUUID().toString(),  // יצירת מזהה ייחודי לכל סטודנט
    var id: String,
    var name: String,
    var email: String,
    var course: String,
    var isChecked: Boolean
) : java.io.Serializable
