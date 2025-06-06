package com.example.electricitybills_alyabalqis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Spinner monthSpinner;
    EditText unitsInput, rebateInput;
    TextView resultText;
    Button calculateBtn, clearBtn, viewListBtn, aboutBtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthSpinner = findViewById(R.id.spinnerMonth);
        unitsInput = findViewById(R.id.inputUnits);
        rebateInput = findViewById(R.id.inputRebate);
        resultText = findViewById(R.id.resultText);
        calculateBtn = findViewById(R.id.btnCalculate);
        clearBtn = findViewById(R.id.btnClear);
        viewListBtn = findViewById(R.id.btnViewList);
        aboutBtn = findViewById(R.id.btnAbout);

        databaseReference = FirebaseDatabase.getInstance().getReference("bills");

        calculateBtn.setOnClickListener(v -> calculateAndSave());
        clearBtn.setOnClickListener(v -> clearInputs());
        viewListBtn.setOnClickListener(v -> startActivity(new Intent(this, BillListActivity.class)));
        aboutBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));

    }

    private void calculateAndSave() {
        String month = monthSpinner.getSelectedItem().toString();
        String unitsStr = unitsInput.getText().toString();
        String rebateStr = rebateInput.getText().toString();

        if (unitsStr.isEmpty() || rebateStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int units = Integer.parseInt(unitsStr);
        double rebate = Double.parseDouble(rebateStr);

        double totalCharge = calculateCharge(units);
        double finalCost = totalCharge - (totalCharge * rebate / 100);

        resultText.setText(String.format("Final Cost: RM %.2f", finalCost));

        String id = databaseReference.push().getKey();
        Bill bill = new Bill(month, units, rebate, totalCharge, finalCost);
        databaseReference.child(id).setValue(bill);

        Toast.makeText(this, "Bill saved to Firebase", Toast.LENGTH_SHORT).show();
    }

    private double calculateCharge(int units) {
        double total = 0;
        if (units <= 200) total = units * 0.218;
        else if (units <= 300) total = 200 * 0.218 + (units - 200) * 0.334;
        else if (units <= 600) total = 200 * 0.218 + 100 * 0.334 + (units - 300) * 0.516;
        else total = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (units - 600) * 0.546;
        return total;
    }

    private void clearInputs() {
        unitsInput.setText("");
        rebateInput.setText("");
        resultText.setText("Final Cost: ");
        monthSpinner.setSelection(0);
        Toast.makeText(this, "Inputs cleared", Toast.LENGTH_SHORT).show();
    }
}
