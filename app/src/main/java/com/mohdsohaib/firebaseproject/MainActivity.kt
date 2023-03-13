package com.mohdsohaib.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mohdsohaib.firebaseproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imgFindStudent.setOnClickListener {
            intent = Intent(this,FindStudent::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnSubmit.setOnClickListener {

            database = FirebaseDatabase.getInstance().getReference("Student")

            val name = binding.edtName.text.toString()
            val age = binding.edtAge.text.toString()
            val roll_no = binding.edtRollNo.text.toString()
            val course = binding.edtCourse.text.toString()

            val student = Student(name,age, roll_no,course)

            if(name.isNotEmpty() && age.isNotEmpty() && roll_no.isNotEmpty() && course.isNotEmpty()){
                database.child(name).setValue(student).addOnSuccessListener {

                    binding.edtName.text.clear()
                    binding.edtAge.text.clear()
                    binding.edtRollNo.text.clear()
                    binding.edtCourse.text.clear()

                    Toast.makeText(this, "Success",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {

                    Toast.makeText(this, "Failure",Toast.LENGTH_SHORT).show()
                }
            }else{
                 Toast.makeText(this,"Oops! Fill all the blanks with data",Toast.LENGTH_SHORT).show()
            }
        }
    }
}