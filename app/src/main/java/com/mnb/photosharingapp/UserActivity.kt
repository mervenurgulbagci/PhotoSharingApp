package com.mnb.photosharingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class UserActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val loginUser = auth.currentUser
        if (loginUser != null) {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun login(view: View){
        auth.signInWithEmailAndPassword(emailText.text.toString(),passwordText.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val loginUser = auth.currentUser?.email.toString()
                Toast.makeText(this, "Hoşgeldin: ${loginUser}", Toast.LENGTH_LONG).show()

                val intent = Intent(this,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
    fun register(view : View){

        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            //asenkron
            if (task.isSuccessful){
                //go to other activity
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
}