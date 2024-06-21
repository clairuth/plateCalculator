package com.example.barbellcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean isPounds = true;
    private EditText totalWeightEditText;
    private TextView resultTextView;
    private LinearLayout leftWeightsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalWeightEditText = findViewById(R.id.totalWeightEditText);
        resultTextView = findViewById(R.id.resultTextView);
        leftWeightsLayout = findViewById(R.id.leftWeightsLayout);
        
        Button poundsButton = findViewById(R.id.poundsButton);
        Button kilogramsButton = findViewById(R.id.kilogramsButton);
        Button calculateButton = findViewById(R.id.calculateButton);

        poundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPounds = true;
                totalWeightEditText.setHint("Enter desired total weight (lbs)");
            }
        });

        kilogramsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPounds = false;
                totalWeightEditText.setHint("Enter desired total weight (kg)");
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateWeights();
            }
        });
    }

    private void calculateWeights() {
        double barWeight = isPounds ? 45 : 20; // 45 lbs or 20 kg
        double[] plateWeights = isPounds ? new double[]{45, 35, 25, 10, 5, 2.5} : new double[]{25, 20, 15, 10, 5, 2.5, 1.25};
        
        String totalWeightStr = totalWeightEditText.getText().toString();
        if (totalWeightStr.isEmpty()) {
            resultTextView.setText("Please enter a valid weight.");
            return;
        }
        
        double totalWeight = Double.parseDouble(totalWeightStr);
        if (totalWeight <= barWeight) {
            resultTextView.setText("Please enter a weight greater than the bar weight.");
            return;
        }

        double weightPerSide = (totalWeight - barWeight) / 2;
        StringBuilder weightBreakdown = new StringBuilder();

        weightBreakdown.append("You need the following plates on each side (").append(isPounds ? "lbs" : "kg").append("):\n");

        leftWeightsLayout.removeAllViews();

        for (double plateWeight : plateWeights) {
            int numPlates = (int) (weightPerSide / plateWeight);
            if (numPlates > 0) {
                weightBreakdown.append(numPlates).append(" x ").append(plateWeight).append(" ").append(isPounds ? "lbs" : "kg").append("\n");
                weightPerSide -= numPlates * plateWeight;

                for (int j = 0; j < numPlates; j++) {
                    TextView weightView = new TextView(this);
                    weightView.setText(String.valueOf(plateWeight));
                    weightView.setBackgroundColor(getPlateColor(plateWeight));
                    weightView.setTextColor(getResources().getColor(android.R.color.white));
                    weightView.setPadding(10, 30, 10, 30);

                    leftWeightsLayout.addView(weightView);
                }
            }
        }

        resultTextView.setText(weightBreakdown.toString());
    }

    private int getPlateColor(double plateWeight) {
        if (isPounds) {
            switch ((int) plateWeight) {
                case 45: return getResources().getColor(android.R.color.holo_red_dark);
                case 35: return getResources().getColor(android.R.color.holo_blue_dark);
                case 25: return getResources().getColor(android.R.color.holo_green_dark);
                case 10: return getResources().getColor(android.R.color.holo_orange_light);
                case 5: return getResources().getColor(android.R.color.holo_orange_dark);
                case 2.5: return getResources().getColor(android.R.color.holo_purple);
                default: return getResources().getColor(android.R.color.darker_gray);
            }
        } else {
            switch ((int) plateWeight) {
                case 25: return getResources().getColor(android.R.color.holo_red_dark);
                case 20: return getResources().getColor(android.R.color.holo_blue_dark);
                case 15: return getResources().getColor(android.R.color.holo_green_dark);
                case 10: return getResources().getColor(android.R.color.holo_orange_light);
                case 5: return getResources().getColor(android.R.color.holo_orange_dark);
                case 2.5: return getResources().getColor(android.R.color.holo_purple);
                case 1: return getResources().getColor(android.R.color.darker_gray);
                default: return getResources().getColor(android.R.color.darker_gray);
            }
        }
    }
}
