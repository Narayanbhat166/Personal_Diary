package com.example.testactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;


public class ViewInformation extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_info_layout);

        TextView display = findViewById(R.id.display_info_text_view);


        myDb = new DatabaseHelper(this);

                Cursor res = myDb.getAllData();
                StringBuilder buffer = new StringBuilder();


                while(res.moveToNext()){
                    buffer.append("Time:"+res.getString(5)+"\n");
                    buffer.append(res.getString(6)+"\n\n");
                }


                if(buffer.length()<=0)
                    display.setText("Nothing to show");
                else{
                    display.setText(buffer);
                    display.setTextColor(Color.parseColor("#110133"));
                    display.setMovementMethod(new ScrollingMovementMethod());


    }
}
}
