package com.example.testactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        myDb = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Executor executor = Executors.newSingleThreadExecutor();

        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setSubtitle("U cannot ByPass it")
                .setDescription("I dont want anyone to Write something in my life unwanted")
                .setNegativeButton("Cancel", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                }).build();

        biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                MainActivity.this.finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                MainActivity.this.finish();
            }
        });


    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat time = new SimpleDateFormat("h:mm a");

    final String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    final String currentTime = time.format(calendar.getTime());

    TextView displayDate = findViewById(R.id.text_view_date);
        displayDate.setText(currentDate);

    TextView displayTime = findViewById(R.id.text_view_time);
        displayTime.setText(currentTime);

    String fields[];
    fields = currentDate.split(",");

    final String day = fields[0].trim();
    final String month = fields[1].trim().split(" ")[1];
    final String date = fields[1].trim().split(" ")[0];
    final String year = fields[2];

        Log.i("day",day);
        Log.i("month",month);
        Log.i("date",date);

    final Button writeDatabase = findViewById(R.id.make_query);
    Button viewDatabase = findViewById(R.id.view_database);

    final EditText editText = findViewById(R.id.edittext);

        editText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().length() > 0) {
                writeDatabase.setEnabled(true);
            } else {
                writeDatabase.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });

        writeDatabase.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean is_inserted = myDb.insertData(year,month,date,day,currentTime,editText.getText().toString());
            if(is_inserted)
                Toast.makeText(MainActivity.this,"Inserted",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();

            editText.setText("");

        }
    });

        viewDatabase.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent viewInformation = new Intent(MainActivity.this,ViewInformation.class);
            startActivity(viewInformation);

        }
    });

}




}
