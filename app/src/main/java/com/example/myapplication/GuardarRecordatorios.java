package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.work.WorkManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import butterknife.BindView;
import model.Data;

public class GuardarRecordatorios extends AppCompatActivity {

    EditText titulo;
    EditText ubicacion;
    Switch todoElDia;
    EditText inicio;
    EditText fin;
    Button guardar;

    private Data de;
//    private boolean isHighImportance = false;
//    private NotificationHandler notificationHandler;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_recordatorios);
//        notificationHandler = new NotificationHandler(this);

        getSupportActionBar().hide();
        de = new Data(getApplicationContext());


        final Calendar cal = Calendar.getInstance();


        int day = getIntent().getIntExtra("day", cal.get(Calendar.DAY_OF_MONTH));
        int month = getIntent().getIntExtra("month", cal.get(Calendar.MONTH));
        int year = getIntent().getIntExtra("year", cal.get(Calendar.YEAR));


        titulo = findViewById(R.id.editTextTextTitulo);
        ubicacion = findViewById(R.id.editTextTextUbicacion);
        todoElDia = findViewById(R.id.switchTodoElDia);
        inicio = findViewById(R.id.fecha);
        fin = findViewById(R.id.hora);
        guardar = findViewById(R.id.btnGuardar);


        inicio.setText(day + "/" + (month + 1) + "/" + year);

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirHora(v);

            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                de.insertRec(titulo.getText().toString(),
                        ubicacion.getText().toString(),
                        inicio.getText().toString(),
                        fin.getText().toString());

                Toast.makeText(GuardarRecordatorios.this, "Recordatorio Creado", Toast.LENGTH_LONG).show();
//                sendNotification();
            }
        });

    }

    public void abrirHora(View view) {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        TimePickerDialog Ti = new TimePickerDialog(GuardarRecordatorios.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                fin.setText(hourOfDay + ":" + minute);


            }
        }, hora, min, true);
        Ti.show();


    }

    private void sendNotification() {
//        String title = titulo.getText().toString();
//        String hubicacion = ubicacion.getText().toString();
//        String inic = inicio.getText().toString();
//        String f = fin.getText().toString();
//
//        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(hubicacion)) {
//            Notification.Builder nb = notificationHandler.createNotification(title, hubicacion, inic, f, isHighImportance);
//            notificationHandler.getManager().notify(1, nb.build());
//
//        }
    }


}
