package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;

import model.Data;

public class InicioApp extends AppCompatActivity {
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Toast toast1 = Toast.makeText(getApplicationContext(), "Bienvenido! Gracias por utilizar la app!", Toast.LENGTH_SHORT);
                toast1.show();
                Intent intent = new Intent(InicioApp.this, InicioSesion.class);
                startActivity(intent);
                mCountDownTimer.start();
                finish();

            }
        }, 2000);
    }

    CountDownTimer mCountDownTimer = new CountDownTimer(60000, 60000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            isCounterRunning = false;
            yourOperation();
        }
    };


    boolean isCounterRunning = false;

    private void yourOperation() {
        if (!isCounterRunning) {
            isCounterRunning = true;
            mCountDownTimer.start();

            boolean fechaExiste = false;

            //rescatar la fecha y la hora(actual): guardarlo en una variable c/u (fecha y hora)

            Data d = new Data(getApplicationContext());

            String fecha = Dates.obtenerFechaActual("GMT-3");
            String hora = Dates.obtenerHoraActual("GMT-3");

            System.out.println("A-----------------------");
            List<String[]> lista = d.consultarFechaExistente(fecha, hora);


            if (lista != null) {
                fechaExiste = true;
            }
            if (fechaExiste) {
                notificationHandler = new NotificationHandler(this);
                String title = null;
                String hubicacion= null;
                String inic= null;
                String f= null;
                for (String[] var : lista) {

                    title = var[0];
                    hubicacion = var[1];
                    inic = var[2];
                    f = var[3];

                    System.out.println(title + " - " + hubicacion+ " - "+inic +" - "+ f+ " - ");

                }

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(hubicacion)) {
                    Notification.Builder nb = notificationHandler.createNotification(title, hubicacion, inic, f, false);
                    notificationHandler.getManager().notify(1, nb.build());

                }
            }

        } else {
            mCountDownTimer.cancel(); // cancel
            mCountDownTimer.start();  // then restart
        }

    }


}

class Dates {

    public static String obtenerHoraActual(String zonaHoraria) {
        String formato = "HH:mm";
        return Dates.obtenerFechaConFormato(formato, zonaHoraria);
    }

    public static String obtenerFechaActual(String zonaHoraria) {
        String formato = "dd/mm/yyyy";
        return Dates.obtenerFechaConFormato(formato, zonaHoraria);
    }

    @SuppressLint("SimpleDateFormat")
    public static String obtenerFechaConFormato(String formato, String zonaHoraria) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getTimeZone(zonaHoraria));
        return sdf.format(date);
    }
}

