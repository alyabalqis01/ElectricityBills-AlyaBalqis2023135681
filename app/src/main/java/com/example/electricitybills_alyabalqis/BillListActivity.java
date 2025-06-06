package com.example.electricitybills_alyabalqis;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class BillListActivity extends AppCompatActivity {
    ListView billListView;
    ArrayList<String> billList;
    ArrayAdapter<String> adapter;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);

        billListView = findViewById(R.id.listViewBills);
        billList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, billList);
        billListView.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance().getReference("bills");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                billList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Bill b = ds.getValue(Bill.class);
                    if (b != null) {
                        String display = "Month: " + b.month +
                                "\nUnits: " + b.units +
                                "\nRebate: " + b.rebate + "%" +
                                "\nTotal Charges: RM " + String.format("%.2f", b.totalCharge) +
                                "\nFinal Cost: RM " + String.format("%.2f", b.finalCost);
                        billList.add(display);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BillListActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        billListView.setOnItemClickListener((parent, view, position, id) -> {
            String details = billList.get(position);
            Toast.makeText(BillListActivity.this, details, Toast.LENGTH_LONG).show();
        });
    }
}
