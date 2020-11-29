package com.example.geobsr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class crearInstitucion extends AppCompatActivity {

    private static final String TAG = "CREAR_INSTITUCION";
    private EditText nombreI,direccionI,gerenteI,telefonoI,nombreEOI,emailI,pass1,pass2;
    private TextView informacion;
    private String mensajeError;
    private Button crearCuenta, regresar;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private String p1;
    private String p2;
    private String corr;
    private String tel;
    private String dir;
    private String nom;
    private String ape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_institucion);
        nombreI = (EditText)findViewById(R.id.idNI);
        direccionI = (EditText)findViewById(R.id.idDI);
        gerenteI = (EditText)findViewById(R.id.idGI);
        telefonoI = (EditText)findViewById(R.id.idtelefono);
        nombreEOI = (EditText)findViewById(R.id.idNEOI);
        emailI = (EditText)findViewById(R.id.idCE);
        pass1 = (EditText)findViewById(R.id.idpass);
        pass2 = (EditText)findViewById(R.id.idconfirmarpass);
        informacion = (TextView)findViewById(R.id.idTv2);
        crearCuenta = (Button)findViewById(R.id.idCrearCuenta);
        regresar = (Button)findViewById(R.id.idRegresar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

    }

    public void crearC(View view){}

    public void regresar(View view){}

}