package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMeasurement extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText etDiastolic, etSystolic, etHeartRate, etDate, etTime, etComment;
    FirebaseFirestore database;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        String currentUserUid = firebaseAuth.getCurrentUser().getUid();

        etDiastolic = findViewById(R.id.etDiastolic);
        etSystolic = findViewById(R.id.etSystolic);
        etTime = findViewById(R.id.etTime);
        etDate = findViewById(R.id.etDate);
        etComment = findViewById(R.id.etComment);
        etHeartRate = findViewById(R.id.etHeartRate);
        btnSave = findViewById(R.id.btnSave);

        etDate.setEnabled(false);
        etTime.setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());

        etDate.setText(date);
        etTime.setText(time);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diastolic = etDiastolic.getText().toString();
                String systolic = etSystolic.getText().toString();
                String heartRate = etHeartRate.getText().toString();
                String comment = etComment.getText().toString();

                if (TextUtils.isEmpty(diastolic) || TextUtils.isEmpty(systolic) || TextUtils.isEmpty(heartRate) ) {
                    Toast.makeText(AddMeasurement.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int diastolicValue = Integer.parseInt(diastolic);
                        int systolicValue = Integer.parseInt(systolic);
                        int heartRateValue = Integer.parseInt(heartRate);

                        if (diastolicValue < 0 || systolicValue < 0 || heartRateValue < 0) {
                            Toast.makeText(AddMeasurement.this, "Please enter non-negative values", Toast.LENGTH_SHORT).show();
                        } else {
                            if (comment.length() > 20) {
                                Toast.makeText(AddMeasurement.this, "Comment exceeds maximum length of 20 characters", Toast.LENGTH_SHORT).show();
                            } else {
                                saveMeasurement(currentUserUid, systolicValue, diastolicValue, date, time, heartRateValue, comment);
                            }
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(AddMeasurement.this, "Invalid input for systolic, diastolic, or heart rate", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void saveMeasurement(String currentUserUid, int systolic, int diastolic, String date, String time, int heartRate, String comment) {
        Measurement measurement = new Measurement(systolic, diastolic, date, time, heartRate, comment);

        CollectionReference cRef = database.collection("users").document(currentUserUid).collection("measurements");
        DocumentReference docRef = cRef.document();

        String measurementId = docRef.getId();
        measurement.setId(measurementId);

        docRef.set(measurement)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddMeasurement.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddMeasurement.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMeasurement.this, "Data insertion error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}