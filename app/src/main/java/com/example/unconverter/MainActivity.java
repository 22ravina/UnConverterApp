
package com.example.unconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText frombox, tobox;
    private Spinner fromSpinner, toSpinner;
    private String[] units = {"Meter", "Kilometer", "Centimeter", "Millimeter"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        frombox = findViewById(R.id.frombox);
        tobox = findViewById(R.id.tobox);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);

        // Set up Spinner adapters
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        // Add TextWatcher to frombox
        frombox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                performConversion();
            }
        });

        // Add OnItemSelectedListener to fromSpinner
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                performConversion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Add OnItemSelectedListener to toSpinner
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                performConversion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // Method to perform unit conversion
    private void performConversion() {
        String inputText = frombox.getText().toString();
        if (inputText.isEmpty()) {
            tobox.setText("");
            return;
        }

        double inputValue = Double.parseDouble(inputText);
        String fromUnit = fromSpinner.getSelectedItem().toString();
        String toUnit = toSpinner.getSelectedItem().toString();
        double result = convertUnits(inputValue, fromUnit, toUnit);
        tobox.setText(String.valueOf(result));
    }

    // Conversion logic (example for length units)
    private double convertUnits(double value, String fromUnit, String toUnit) {
        double meterValue = 0;

        // Convert from the source unit to meters
        switch (fromUnit) {
            case "Meter": meterValue = value; break;
            case "Kilometer": meterValue = value * 1000; break;
            case "Centimeter": meterValue = value / 100; break;
            case "Millimeter": meterValue = value / 1000; break;
        }

        // Convert from meters to the target unit
        switch (toUnit) {
            case "Meter": return meterValue;
            case "Kilometer": return meterValue / 1000;
            case "Centimeter": return meterValue * 100;
            case "Millimeter": return meterValue * 1000;
        }

        return value;
    }
}