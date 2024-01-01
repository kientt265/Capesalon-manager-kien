package com.example.capellisalon_app_manager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class booking extends AppCompatActivity {
    ListView listnoti;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking); // Di chuyển lên đầu tiên

        listnoti = findViewById(R.id.lv_booking);
        notificationList = new ArrayList<>();
        notificationList.add("Thông báo 1");
        notificationList.add("Thông báo 2");
        notificationList.add("Thông báo 3");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notificationList);
        listnoti.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userID = user.getUid();
            DatabaseReference userBookingRef = FirebaseDatabase.getInstance().getReference().child("userID").child("NAKXsVLE1XWi3QlQcg6iBLqaHDZ2").child("InfoBooking");
            userBookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.d("Firebase", "DataSnapshot exists: " + dataSnapshot.getValue());
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object data = snapshot.getValue();
                            if (data != null) {
                                String convertedData = data.toString();
                                notificationList.add(convertedData);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("Firebase", "No data found at this path");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase", "onCancelled called: " + databaseError.getMessage());
                }
            });
        }
    }

}
