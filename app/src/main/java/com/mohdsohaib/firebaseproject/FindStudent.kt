package com.mohdsohaib.firebaseproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mohdsohaib.firebaseproject.databinding.ActivityFindStudentBinding

class FindStudent : AppCompatActivity() {

    private lateinit var binding : ActivityFindStudentBinding
    private lateinit var database : DatabaseReference
    private lateinit var remoteConfig : FirebaseRemoteConfig
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

        //**********************REMOTE CONFIG********************************
        remoteConfig = Firebase.remoteConfig
        val configSetting = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSetting)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate().addOnCompleteListener(this) {
            if (it.isSuccessful){
                val findBtnChange = remoteConfig.getString("GET_DATA_BTN")
                val btnFind = binding.btnFind
                btnFind.text = findBtnChange

                 val colorBackground = remoteConfig.getString("BG_CHANGE")
                val bgColor = binding.llBgColor
                bgColor.setBackgroundColor(Color.parseColor(colorBackground))
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