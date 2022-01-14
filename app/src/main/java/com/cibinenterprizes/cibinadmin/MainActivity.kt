package com.cibinenterprizes.cibinadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        welcome_login.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
        }
        welcome_register.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
    override fun onStart() {
        super.onStart()
        val user = auth.currentUser

        if(user != null){
            if(auth.currentUser?.isEmailVerified!!){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }
}