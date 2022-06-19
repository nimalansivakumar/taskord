package com.example.taskord

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import com.example.taskord.DashboardActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CustomDialogModal : DialogFragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_dialog, container, false)
        val group_button: Button = rootView.findViewById<Button>(R.id.create_group_button);

        group_button.setOnClickListener {

            Log.d("output", "Called");
            val group_name: String =
                rootView.findViewById<EditText>(R.id.group_name).text.toString();

            if (group_name.isNotEmpty() && group_name.length > 5) {
                db = Firebase.firestore;
                val currentUser = Firebase.auth.currentUser

                if (currentUser != null) {
                    db.collection("admin").document(currentUser.email.toString()).get()
                        .addOnCompleteListener { documents ->
                            if (documents.isSuccessful) {

                                val document = documents.result.data;
                                Log.d(
                                    "doc", document.toString()
                                );
                            } else {
                                val group_name_array = arrayOf(group_name)
                                val docData = hashMapOf("group_names" to group_name_array)


                                db.collection("admin").document(currentUser.email.toString())
                                    .set(docData)
                                    .addOnSuccessListener { success ->
                                        Log.w(TAG, "Success$ success");
                                    }
                            }
                        }


                }

            } else {
                Toast.makeText(
                    activity,
                    "Group name should be atleast 6 characters in length",
                    Toast.LENGTH_SHORT
                ).show();
            }
            dialog?.dismiss();
        }


        return rootView

    }

}
