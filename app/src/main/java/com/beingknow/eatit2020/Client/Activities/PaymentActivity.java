package com.beingknow.eatit2020.Client.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;


import com.beingknow.eatit2020.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.confirm_order));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        IntentIntegrator integrator = new IntentIntegrator(PaymentActivity.this);

        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan QR code");
        integrator.setBeepEnabled(false);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setCameraId(0);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();



        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(PaymentActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PaymentActivity.this, "Scanned : " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
    }
}