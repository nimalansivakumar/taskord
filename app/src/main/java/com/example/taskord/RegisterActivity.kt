package com.example.taskord

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val haveAccount = findViewById<TextView>(R.id.switch_log_in)
        haveAccount.setOnClickListener {
            startActivity(
                Intent(this@RegisterActivity, MainActivity::class.java)
            )
        }

        firebaseAuth = Firebase.auth
        register()
    }

    private fun register() {
        val signUpButton = findViewById<Button>(R.id.register_button);

        signUpButton.setOnClickListener {

            val username = findViewById<EditText>(R.id.username).text.toString();
            val password = findViewById<EditText>(R.id.password).text.toString();


            if (!username.isEmpty() && !password.isEmpty()) {

                val p =
                    Pattern.compile("^(.+)@(.+)\$");
                val matcher = p.matcher(username);
                Log.d("valid", matcher.matches().toString())

                if (matcher.matches()) {

                    if (password.length >= 8) {


                        firebaseAuth
                            .createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    Log.d("task", task.toString());
                                    saveToDb(username)
                                } else {
                                    Toast.makeText(
                                        this,
                                        "User Already Exists",
                                        Toast.LENGTH_SHORT
                                    ).show();

                                }
                            }

                    } else {
                        Toast.makeText(
                            this,
                            "Password must be 8 characters in length",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(this, "Empty fields not allowed", Toast.LENGTH_SHORT).show();
            }

        };
    }

    private fun saveToDb(currentUser: String?) {

        Log.d("currentUser", currentUser.toString());

        val docData = hashMapOf(
            "email" to currentUser
        )
        db = Firebase.firestore
        db.collection("users").add(currentUser.toString())
            .addOnSuccessListener {
                db.collection("users").document(currentUser.toString()).set(docData)
                intent.putExtra("userid", "nimalan")
                startActivity(Intent(this, DashboardActivity::class.java))
                Log.d(TAG, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


    }


}