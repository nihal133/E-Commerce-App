package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.R;

public class PaymentActivity extends AppCompatActivity {
    EditText CardNumber, ExpireDateMonth, ExpireDateYear, CVV;
    Button addCreditCard;
    String userId;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Database db = new Database(this);

        Intent ii = getIntent();
        userId = ii.getStringExtra("id");

        CardNumber = findViewById(R.id.CardNumber);
        ExpireDateMonth = findViewById(R.id.ExpireDateMonth);
        ExpireDateYear = findViewById(R.id.ExpireDateYear);
        CVV = findViewById(R.id.CVV);
        addCreditCard = findViewById(R.id.AddPayment);

        addCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get credit card information from the input fields
                String cardNumber = CardNumber.getText().toString();
                String expireDateMonth = ExpireDateMonth.getText().toString();
                String expireDateYear = ExpireDateYear.getText().toString();
                String cvv = CVV.getText().toString();

                // Simple validation: Check if any of the fields are empty
                if (cardNumber.isEmpty() || expireDateMonth.isEmpty() || expireDateYear.isEmpty() || cvv.isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Additional validation: Check if the credit card number is valid using the Luhn algorithm
                    if (isLuhnValid(cardNumber)) {
                        // Continue with your logic for adding credit card and transitioning to OrderActivity
                        Intent paymentIntent = new Intent(PaymentActivity.this, OrderActivity.class);
                        // Pass any necessary data using Intent extras
                        flag = 1;
                        paymentIntent.putExtra("paymentFlag", flag);
                        // Start the OrderActivity
                        startActivity(paymentIntent);

                        // Finish the current activity
                        finish();
                    } else {
                        Toast.makeText(PaymentActivity.this, "Invalid credit card number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
   // Luhn algorithm validation method
    private boolean isLuhnValid(String number) {
        // Remove any whitespace or hyphens from the number
        number = number.replaceAll("\\s+", "").replaceAll("-", "");

        // Check if the number is composed of digits only
        if (!number.matches("\\d+")) {
            return false;
        }

        // Reverse the number and convert it to an array of integers
        int[] digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = Character.getNumericValue(number.charAt(number.length() - 1 - i));
        }

        // Apply the Luhn algorithm
        int total = 0;
        for (int i = 0; i < digits.length; i++) {
            if (i % 2 == 0) {
                total += digits[i];
            } else {
                int doubledDigit = digits[i] * 2;
                total += doubledDigit > 9 ? doubledDigit - 9 : doubledDigit;
            }
        }

        // If the total is divisible by 10, the card number is valid
        return total % 10 == 0;
    }
}
