package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Edit extends AppCompatActivity {

    EditText etSystolic,etDiastolic,etHeartRate,etComment;
    Button btnSave;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        String id = getIntent().getStringExtra("Id");

        etSystolic = findViewById(R.id.etSystolic);
        etDiastolic = findViewById(R.id.etDiastolic);
        etHeartRate = findViewById(R.id.etHeartRate);
        etComment = findViewById(R.id.etComment);
        btnSave = findViewById(R.id.btnSave);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        String currentUserUid = firebaseAuth.getCurrentUser().getUid();
        CollectionReference measurementsRef = database.collection("users").document(currentUserUid).collection("measurements");

        measurementsRef.document(id).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Measurement measurement = documentSnapshot.toObject(Measurement.class);

                // Set the retrieved values to the EditText fields
                etSystolic.setText(String.valueOf(measurement.getSystolic()));
                etDiastolic.setText(String.valueOf(measurement.getDiastolic()));
                etHeartRate.setText(String.valueOf(measurement.getHeartRate()));
                etComment.setText(measurement.getComment());

                // Handle the button click event for saving changes
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Update the measurement object with the new values from EditText fields
                        measurement.setSystolic(Integer.parseInt(etSystolic.getText().toString()));
                        measurement.setDiastolic(Integer.parseInt(etDiastolic.getText().toString()));
                        measurement.setHeartRate(Integer.parseInt(etHeartRate.getText().toString()));
                        measurement.setComment(etComment.getText().toString());




                    }
                });
            }
        });



    }
}