package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txtView;
    private FirebaseAuth firebaseAuth;
    private Button btnLogout;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Measurement> measurementList;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        String currentUserUid = firebaseAuth.getCurrentUser().getUid();

        // Initialize views
        txtView = findViewById(R.id.txt);
        btnLogout = findViewById(R.id.btnLogout);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        // Set the current user's display name
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            txtView.setText(name);
        }

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        measurementList = new ArrayList<>();
        adapter = new MyAdapter(measurementList);
        recyclerView.setAdapter(adapter);

        // Retrieve measurement data from Firestore
        CollectionReference measurementsRef = database.collection("users").document(currentUserUid).collection("measurements");

        Query query = measurementsRef
                .orderBy("date", Query.Direction.ASCENDING);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                measurementList.clear();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Measurement measurement = documentSnapshot.toObject(Measurement.class);
                    measurementList.add(measurement);
                }

                Collections.sort(measurementList, new Comparator<Measurement>() {
                    @Override
                    public int compare(Measurement o1, Measurement o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });

                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to retrieve measurements", Toast.LENGTH_SHORT).show();
            }
        });


        // Set up click listeners

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemDeleteClick(int position) {
                measurementsRef.document(measurementList.get(position).getId())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                measurementList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
            }

            @Override
            public void onItemEditClick(int position) {
                String id = measurementList.get(position).getId();
                /*Intent intent = new Intent(MainActivity.this, Edit.class);
                intent.putExtra("Id",id);
                startActivity(intent);*/
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeasurement.class);
                startActivity(intent);
            }
        });
    }
}
