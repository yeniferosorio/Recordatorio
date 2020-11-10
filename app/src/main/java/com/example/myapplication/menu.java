package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class menu extends AppCompatActivity {

    private CalendarView calendarView;
    private ImageView imageButton;
    int dia, mes, an;
    int hora,min,seg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        calendarView = findViewById(R.id.calendarV);
        imageButton = findViewById(R.id.botonEvento);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        dia = dayOfMonth;
                        mes = month;
                        an = year;
                    }
                });


                Intent intent = new Intent(menu.this, GuardarRecordatorios.class);
                intent.putExtra("day", dia);
                intent.putExtra("month", mes);
                intent.putExtra("year", an);
                startActivity(intent);

            }
        });





    }
}