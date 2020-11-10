package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Data;

public class InicioSesion extends AppCompatActivity {

    private EditText editTextTextEmailAddress;
    private EditText editTextTextPassword;
    private Button buttonInicioSesion;
    private TextView textViewRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        getSupportActionBar().hide();
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        buttonInicioSesion = findViewById(R.id.buttonInicioSesion);
        textViewRegistro = findViewById(R.id.textViewRegistro);

        buttonInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextTextEmailAddress.getText().toString();
                String password = editTextTextPassword.getText().toString();
                if (login(email, password)) {
                    if(emailExistsInDB(email,password)){
                        Toast.makeText(InicioSesion.this, "Bienvenido A Personal Reminder",Toast.LENGTH_LONG).show();

                        Menu();
                    }else{

                        Toast.makeText(InicioSesion.this, "Los datos son incorrectos o no existen,\npor favor cree una cuenta o revise los datos", Toast.LENGTH_LONG).show();
                    }


                }

            }
        });
        textViewRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioSesion.this, CrearCuenta.class);
                startActivity(intent);
            }
        });




    }
    private boolean emailExistsInDB(String email, String password) {
        Data d = new Data(getApplicationContext());



        return d.userExists(email,password);//cambiar

    }
    private boolean login(String email, String password) { //aqui manda un mensaje si el e-mail y el password no son correctos
        if (!validarEmail(email)) {
            Toast.makeText(this, "Email no es valido por favor intente de nuevo", Toast.LENGTH_LONG).show();
            return false;
        } else if (!validarPassword(password)) {
            Toast.makeText(this, "contraseña no valida, debe contener 4 caracteres o más. Intente de nuevamente", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validarEmail(String email) { // aqui veo si el email que estoy registrando es valido
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean validarPassword(String password) {
        return password.length() >= 4;
    }
    private void Menu() { // una vez que me haya logeado no volver a donde esta el login
        Intent intent = new Intent(this, menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }





}