package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import model.Data;

public class CrearCuenta extends AppCompatActivity {
    private EditText TextNombre;
    private EditText TextRut;
    private Spinner sprSexo;
    private EditText TextEdad;
    private EditText TextFechaN;
    private EditText TextNumTel;
    private EditText TextCorreo;
    private EditText TextPass;
    private EditText TextConfirmPass;
    private Button RegistrarCuenta;
    private TextView txtIrAinicioSesion;
    private Data d;
    private ArrayAdapter<String> sexoArrayAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        getSupportActionBar().hide();

        d = new Data(getApplicationContext());
        String[] listSexo = {"Masculino", "Femenino"};

        TextNombre = findViewById(R.id.editTextNombre);
        TextRut= findViewById(R.id.editTextRut);
        TextFechaN = findViewById(R.id.editTextFechaN);
        TextNumTel = findViewById(R.id.editTextNumTel);
        TextCorreo = findViewById(R.id.editTextCorreo);
        TextPass = findViewById(R.id.editTextContraseña);
        TextConfirmPass = findViewById(R.id.editTextConfirmPass);
        RegistrarCuenta = findViewById(R.id.buttonRegistrarCuenta);
        sprSexo = findViewById(R.id.spinnerSexo);
        TextEdad = findViewById(R.id.editTextEdad);
        txtIrAinicioSesion=findViewById(R.id.txtIrAinicioSesion);


        sexoArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, listSexo);


        sprSexo.setAdapter(sexoArrayAdapter);


        RegistrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( TextPass.getText().toString().equals( TextConfirmPass.getText().toString())) {
                    //validacion futura de formulario completo (solo se validaron las contraseñas)
                    d.insertNewUser( TextNombre.getText().toString(),
                            TextRut.getText().toString(),
                            TextFechaN.getText().toString(),TextNumTel.getText().toString(),
                            TextCorreo.getText().toString(), TextPass.getText().toString(),
                            sprSexo.getSelectedItem().toString(),
                            Integer.valueOf(TextEdad.getText().toString()));
                    Toast.makeText(CrearCuenta.this, "Usuario Creado", Toast.LENGTH_LONG).show();

                }


            }



        });

        txtIrAinicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CrearCuenta.this,InicioSesion.class);
                startActivity(intent);
            }
        });



    }
}