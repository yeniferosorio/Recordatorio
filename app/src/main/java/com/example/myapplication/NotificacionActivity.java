package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

    TextView textView=findViewById(R.id.text_View);

        String mensaje=getIntent().getStringExtra("mensaje");
        textView.setText(mensaje);
    }
}