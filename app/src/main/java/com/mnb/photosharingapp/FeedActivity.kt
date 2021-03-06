package com.mnb.photosharingapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class FeedActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.photo_share){
            val intent = Intent(this,PhotoShareActivity::class.java)
            startActivity(intent)
        }else if(item.itemId == R.id.logout){
            auth.signOut()
            val intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)

    }

}