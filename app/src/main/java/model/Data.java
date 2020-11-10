package model;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Data extends SQLiteOpenHelper {
    private static final String NOMBRE_BD = "RECORDATORIO";
     private static final String RECORDATORIO = "CREATE TABLE recordatorio(_id INTEGER PRIMARY KEY AUTOINCREMENT, _titulo TEXT,_ubicacion TEXT,_fecha TEXT,_hora TEXT)";
    private static final String SECCION = "CREATE TABLE seccion (_id INTEGER PRIMARY KEY AUTOINCREMENT, _tipoRecordatorio INTEGER)";
    private static final String AGENDA = "CREATE TABLE agenda (_id INTEGER PRIMARY KEY AUTOINCREMENT,_seccion_fk INTEGER, _recordatorio_fk INTEGER, FOREIGN KEY (seccion_fk) references seccion(id), FOREIGN KEY (recordatorio_fk) references recordatorio(id) )";
    private static final String HISTORIAL = "CREATE TABLE historial (_id INTEGER PRIMARY KEY AUTOINCREMENT,_agenda_fk INTEGER,FOREIGN KEY (agenda_fk)references agenda(id))";
    private static final String LOGIN = " CREATE TABLE login(_id INTEGER PRIMARY KEY AUTOINCREMENT,_correo TEXT,_contrasenia TEXT)";
    private static final String SEXO = "CREATE TABLE sexo (_id INTEGER PRIMARY KEY AUTOINCREMENT,_genero TEXT )";
    private static final String USUARIO = "CREATE TABLE usuario (_id INTEGER PRIMARY KEY AUTOINCREMENT,_nombre TEXT,_rut TEXT,_edad INTEGER,_sexo_fk INTEGER,_fecha_nacimiento TEXT,_telefono INTEGER, _login_fk INTEGER UNIQUE, FOREIGN KEY(_login_fk) references login(_id), FOREIGN KEY (_sexo_fk)references sexo(_id))";

    // INSERT PARA IMPLEMENTACIÓN FUTURA COMPLETA
    //"CREATE TABLE usuario (_id INTEGER PRIMARY KEY AUTOINCREMENT,_nombre TEXT,_rut TEXT,_edad INTEGER,_sexo_fk INTEGER,_login_fk INTEGER UNIQUE,_agenda_fk INTEGER,_fecha_nacimiento TEXT,_telefono INTEGER,FOREIGN KEY(agenda_fk)references agenda(id),FOREIGN KEY(login_fk)references login(id),FOREIGN KEY (sexo_fk)references sexo(id) )";


    public Data(Context context) {
        super(context, NOMBRE_BD, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


//        db.execSQL(SECCION);
//        db.execSQL(AGENDA);
//        db.execSQL(HISTORIAL);
        db.execSQL(RECORDATORIO);
        db.execSQL(LOGIN);
        db.execSQL(SEXO);
        db.execSQL(USUARIO);

//
//       db.execSQL("INSERT INTO recordatorio VALUES(NULL,'')");
//        db.execSQL("INSERT INTO seccion VALUES(NULL,'')");
//        db.execSQL("INSERT INTO agenda VALUES(NULL,'')");
//        db.execSQL("INSERT INTO historial VALUES(NULL,'')");

//        db.execSQL("INSERT INTO login VALUES(NULL,'')");
        db.execSQL("INSERT INTO sexo VALUES(NULL,'Masculino');");
        db.execSQL("INSERT INTO sexo VALUES(NULL,'Femenino');");
//        db.execSQL("INSERT INTO usuario VALUES(NULL,'')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertRec(String titulo, String ubicacion, String fecha, String hora ){
    SQLiteDatabase bd=getReadableDatabase();
    bd.execSQL("INSERT INTO recordatorio VALUES(NULL,'" + titulo + "','" +  ubicacion + "','"+ fecha+ "','" +hora + "')");
        System.out.println("INSERT INTO recordatorio VALUES(NULL,'" + titulo + "','" +  ubicacion + "','"+ fecha+ "','" +hora + "')");
    bd.close();
    }

    public void insertNewUser(String name, String rut, String fNacimiento, String telefono, String correo, String contrasenia, String sexo, int edad) {
        SQLiteDatabase bd = getReadableDatabase();
        bd.execSQL("INSERT INTO login VALUES(NULL,'" + correo + "','" + contrasenia + "')");

        int idLastLoginInsert = 0;

        Cursor read = bd.rawQuery("SELECT _id FROM login ORDER BY _id DESC LIMIT 1;  ", null);
        if (read.moveToFirst()) {
            do {
                idLastLoginInsert = read.getInt(0);

            } while (read.moveToNext());
        }


        int idSexo = 0; // 0 Masculino & 1 Femenino

        if (sexo.equalsIgnoreCase("Femenino")) {
            idSexo = 1;
        }

        bd.execSQL("INSERT INTO usuario VALUES(NULL,'" + name + "','" + rut + "'," + edad + "," + idSexo + ",'" + fNacimiento + "'," + telefono + "," + idLastLoginInsert + ");");
        bd.close();
    }

    public boolean userExists(String correo, String contrasenia) {

        boolean exists = false;
        SQLiteDatabase bd = getReadableDatabase();


        Cursor read = bd.rawQuery("SELECT _correo ,_contrasenia FROM login WHERE _correo == '" + correo + "' AND '" + contrasenia + "' == _contrasenia;  ", null);
        if (read.moveToNext()) {

            do {
                exists = true;
            } while (read.moveToNext());
        }
        bd.close();

        return exists;
    }


    //Hacer metodo que retorne una lista si la fecha existe en la base de datos.

    public List<String[]> consultarFechaExistente(String fecha, String hora) {

        boolean fechaExists = false;
        SQLiteDatabase bd = getReadableDatabase();

        List<String[]> list = new ArrayList<>();

        String query = "SELECT * FROM recordatorio WHERE _fecha LIKE '"+fecha+"' AND _hora LIKE '" + hora +"';";

        Cursor read = bd.rawQuery(query, null);

        System.out.println("B-----------------------");

         while(read.isFirst()){

             String[] data = new String[4];
             data[0] = read.getString(2);
             data[1] = read.getString(3);
             data[2] = read.getString(4);
             data[3] = read.getString(5);

             System.out.println(data.toString());

             list.add(data);
             fechaExists = true;
         }

        bd.close();


        if(fechaExists){
            System.out.println("C1-----------------------");
            return list;

        }else{
            System.out.println("C2-----------------------");
            return null;
        }
    }

}
