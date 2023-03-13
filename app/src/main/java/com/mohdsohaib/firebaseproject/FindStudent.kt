package com.mohdsohaib.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mohdsohaib.firebaseproject.databinding.ActivityFindStudentBinding

class FindStudent : AppCompatActivity() {

    private lateinit var binding : ActivityFindStudentBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgAddStudent.setOnClickListener {
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnFind.setOnClickListener {

            val findName : String = binding.edtFindStudent.text.toString()
            if(findName.isNotEmpty()){
                readData(findName)
            }else{
                Toast.makeText(this,"Please enter the user name",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(findName: String) {
        database = FirebaseDatabase.getInstance().getReference("Student")

        database.child(findName).get().addOnSuccessListener {

            if(it.exists()){
                val name = it.child("name").value
                val age = it.child("age").value
                val roll_no = it.child("roll_no").value
                val course = it.child("course").value

                Toast.makeText(this,"Successfully Read",Toast.LENGTH_SHORT).show()

                binding.edtFindStudent.text.clear()

                binding.txtName.text = name.toString()
                binding.txtAge.text = age.toString()
                binding.txtRollNo.text = roll_no.toString()
                binding.txtCourse.text = course.toString()

            }else{
                Toast.makeText(this,"Student doesn't exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }
}