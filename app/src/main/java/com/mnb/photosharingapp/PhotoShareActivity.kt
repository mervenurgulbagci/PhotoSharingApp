package com.mnb.photosharingapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_photo_share.*
import java.util.*

class PhotoShareActivity : AppCompatActivity() {

    var choosedImage: Uri? = null
    var choosedBitmap: Bitmap? = null
    private lateinit var storage : FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var database :FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_share)

        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
    }

    fun share(view: View) {
        //storage
        //UUID : Universal Unique id
        val uuid = UUID.randomUUID()
        val imageName = "${uuid}.jpg"
        val reference = storage.reference
        val imageReference = reference.child("images").child(imageName)
        if (choosedImage != null){
            imageReference.putFile(choosedImage!!).addOnSuccessListener { taskSnapshot ->
               val loadedImageReference = FirebaseStorage.getInstance().reference.child("images").child(imageName)
                loadedImageReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    
                }
            }
        }


    }

    fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,

                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                val galleryIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            choosedImage = data.data

            if (choosedImage != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, choosedImage!!)
                    choosedBitmap = ImageDecoder.decodeBitmap(source)
                    imageView3.setImageBitmap(choosedBitmap)

                } else {
                    choosedBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, choosedImage)
                    imageView3.setImageBitmap(choosedBitmap)
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}