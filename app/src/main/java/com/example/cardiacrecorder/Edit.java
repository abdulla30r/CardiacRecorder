package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
                        String systolic = etSystolic.getText().toString();
                        String diastolic = etDiastolic.getText().toString();
                        String heartRate = etHeartRate.getText().toString();
                        String comment = etComment.getText().toString();

                        if (TextUtils.isEmpty(systolic) || TextUtils.isEmpty(diastolic) || TextUtils.isEmpty(heartRate) ) {
                            Toast.makeText(Edit.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                int diastolicValue = Integer.parseInt(diastolic);
                                int systolicValue = Integer.parseInt(systolic);
                                int heartRateValue = Integer.parseInt(heartRate);

                                if (diastolicValue < 0 || systolicValue < 0 || heartRateValue < 0) {
                                    Toast.makeText(Edit.this, "Please enter non-negative values", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (comment.length() > 20) {
                                        Toast.makeText(Edit.this, "Comment exceeds maximum length of 20 characters", Toast.LENGTH_SHORT).show();
                                    } else {
                                        measurement.setDiastolic(diastolicValue);
                                        measurement.setComment(comment);
                                        measurement.setHeartRate(heartRateValue);
                                        measurement.setSystolic(systolicValue);

                                        measurementsRef.document(id).set(measurement)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(Edit.this, "Item Edited", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Edit.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line
                                                    startActivity(intent);
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(Edit.this, "Edit Failed", Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                }
                            } catch (NumberFormatException e) {
                                Toast.makeText(Edit.this, "Invalid input for systolic, diastolic, or heart rate", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // Save the updated measurement object back to the database


                    }
                });
            }
        });



    }
}