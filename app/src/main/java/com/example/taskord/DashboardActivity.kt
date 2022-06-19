package com.example.taskord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DashboardActivity : AppCompatActivity() {

    private lateinit var firebase: FirebaseAuth
    private lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val displayText = findViewById<TextView>(R.id.newtext);

//        firebase = Firebase.auth
//        if (firebase.currentUser != null) {
//            currentUser = firebase.currentUser!!.getIdToken(true).toString();
//            displayText.setText("Welcome, " + currentUser);
//        }

        val user = Firebase.auth.currentUser
        if (user != null) {
            displayText.setText("Welcome, " + user.email)
        };

        val contactNumber = findViewById<TextView>(R.id.contactnumber);
        val contactbutton = findViewById<Button>(R.id.create_group);

        contactbutton.setOnClickListener(View.OnClickListener {
            val dialog = CustomDialogModal();

            dialog.show(supportFragmentManager, "customDialog")
        })
    }


    private fun pickContact() {
        var builder = StringBuilder()

    }


}

