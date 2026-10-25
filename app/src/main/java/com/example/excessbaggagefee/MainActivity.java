package com.example.excessbaggagefee;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar myToolbar;
    EditText etWeight;
    EditText etRate;
    RadioButton rbEconomy;
    RadioButton rbBusiness;
    Button btnCalculate;
    Button btnClear;
    TextView tvExcessWeight;
    TextView tvCharge;
    TextView tvTotal;

    // Tukar ke URL GitHub
    String appUrl = "https://github.com/nicystecu/ExcessBaggageFee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        etWeight = (EditText) findViewById(R.id.etWeight);
        etRate = (EditText) findViewById(R.id.etRate);
        rbEconomy = (RadioButton) findViewById(R.id.rbEconomy);
        rbBusiness = (RadioButton) findViewById(R.id.rbBusiness);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnClear = (Button) findViewById(R.id.btnClear);
        tvExcessWeight = (TextView) findViewById(R.id.tvExcessWeight);
        tvCharge = (TextView) findViewById(R.id.tvCharge);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        btnCalculate.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnCalculate) {
            calculateFee();
        } else if (view == btnClear) {
            clearForm();
        }
    }

    // Function kira fee
    private void calculateFee() {

        // Letak 0 dulu supaya tak null
        double weight = 0;
        double rate = 0;
        double limit = 20;
        double excessWeight = 0;
        double charge = 0;
        double total = 0;

        try {
            // Ni yang boleh jadi NumberFormatException kalau field kosong
            weight = Double.parseDouble(etWeight.getText().toString());
            rate = Double.parseDouble(etRate.getText().toString());

            // Check nombor negatif
            if (weight < 0 || rate < 0) {
                Toast.makeText(this, "Weight and rate cannot be negative", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tentukan had berat (X) ikut class
            if (rbBusiness.isChecked()) {
                limit = 40;
            } else {
                limit = 20;
            }

            // Excess weight, tak boleh negatif
            excessWeight = weight - limit;
            if (excessWeight < 0) {
                excessWeight = 0;
            }

            // Kira charge + total (tax 8%)
            charge = excessWeight * rate;
            total = charge + (0.08 * charge);

            // Papar output, 2 decimal places
            tvExcessWeight.setText("Excess weight: " + String.format("%.2f", excessWeight) + " kg");
            tvCharge.setText("Excess baggage charge: RM " + String.format("%.2f", charge));
            tvTotal.setText("Total payable: RM " + String.format("%.2f", total));

        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Please enter valid number in the text field", Toast.LENGTH_SHORT).show();
        }
    }

    // Function clear semua
    private void clearForm() {
        etWeight.setText("");
        etRate.setText("");
        rbEconomy.setChecked(true);
        tvExcessWeight.setText("Excess weight: -");
        tvCharge.setText("Excess baggage charge: -");
        tvTotal.setText("Total payable: -");
    }

    // Inflate menu ke app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Bila menu item ditekan
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_share) {
            // Share intent (ikut lab Sharing Intent)
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Please use my Baggage Fee Calculator app: " + appUrl);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;

        } else if (item.getItemId() == R.id.item_about) {
            // Buka About activity (ikut lab Start New Activity)
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}