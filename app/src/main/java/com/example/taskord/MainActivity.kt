package com.example.taskord

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dontHaveAccount = findViewById<TextView>(R.id.switch_register_button)
        dontHaveAccount.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, RegisterActivity::class.java)
            )
        }

        firebaseAuth = Firebase.auth
//        startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
        login()
    }

    private fun login() {
        val logInButton = findViewById<Button>(R.id.logInButton);
        logInButton.setOnClickListener {

            val username = findViewById<EditText>(R.id.username).text.toString();
            val password = findViewById<EditText>(R.id.password).text.toString();


            if (!username.isEmpty() && !password.isEmpty()) {

                val p =
                    Pattern.compile("^(.+)@(.+)\$");
                val matcher = p.matcher(username);

                Log.d("valid", matcher.matches().toString())

                if (matcher.matches()) {

                    if (password.length >= 8) {

                        firebaseAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val intent =
                                        Intent(this@MainActivity, DashboardActivity::class.java)
                                    intent.putExtra("userid", "Nimalan")
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Something Wrong",
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

}