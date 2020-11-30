package com.example.geobsr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class crearInstitucion extends AppCompatActivity {

    private static final String TAG = "CREAR_INSTITUCION";
    private EditText nombreI,direccionI,gerenteI,telefonoI,nombreEOI,emailI,pass1,pass2;
    private TextView informacion;
    private String mensajeError;
    private Button crearCuenta;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private String nIns, dirIns,gerIns, telIns,nEOIns,emailIns, p1Ins,p2Ins;

    private String listaErrores;

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

        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

    }

    public void crearC(View view){
        if(verificarCorreo()){
            verificarDatos();
        }
        else {
            crearCuenta.setText("VERIFICAR");
            Toast.makeText(this, "VERIFIQUE LOS SIGUIEBNTES DATOS: " + listaErrores, LENGTH_SHORT).show();
            informacion.setText(listaErrores);
        }
    }

    public void regresar(View view){
        finish();
    }

    private boolean verificarCorreo() {
        emailIns = emailI.getText().toString();
        if (emailIns.isEmpty()) {
            listaErrores = listaErrores + " DEBE INGRESAR UN EMAIL VALIDO";
            Log.i(TAG, "EMAIL ERRONEO.");
            return false;
        } else {
            return true;
        }
    }
    private void verificarDatos(){
        listaErrores = "";
        gerIns=gerenteI.getText().toString();
        nEOIns=nombreEOI.getText().toString();
        if(verificarNomIns() && verificarDirIns() && verificarNombre(gerIns) && verificarNombre(nEOIns) && verificarTelefono()){
            verificarPassword();
        }else{
            Log.i(TAG,"REVISAR DATOS");
            informacion.setText(listaErrores);
            crearCuenta.setText("VERIFICAR");
        }
    }

    private boolean verificarNomIns() {
        nIns=nombreI.getText().toString();
        if(nIns.isEmpty()){
            listaErrores = listaErrores + " NOMBRE DE LA INSTITUCIÓN";
            Log.i(TAG,"NOMBRE DE INSTITUCION NO INGRESADO.");
            return false;
        }else{
            return true;
        }
    }

    private boolean verificarTelefono() {
        telIns=telefonoI.getText().toString();
        if(telIns.isEmpty()){
            listaErrores = listaErrores + "NUMERO DE TELEFONO.";
            Log.i(TAG,"TELEFONO NO INGREASDO. ");
            return false;
        }else{
            return true;
        }
    }

    private boolean verificarDirIns() {
        dirIns=direccionI.getText().toString();
        if(dirIns.isEmpty()){
            mensajeError = mensajeError + " DIRECCION DE LA INSTITUCION. ";
            Log.i(TAG,"DIRECCION DE INSTITUCION NO INGRESADO..");
            return false;
        }else{
            return true;
        }
    }

    private boolean verificarNombre(String nom) {
        if(nom.isEmpty() ){
            listaErrores = listaErrores + "GERENTE O ENCARGADO DE OPERACIONES.";
            Log.i(TAG,"GERENTE O ENCARGADO DE OPERACIONES NO INGRESADOS");
            return false;
        }else{
            return true;
        }
    }

    private void verificarPassword(){
        p1Ins=pass1.getText().toString();
        p2Ins=pass2.getText().toString();
        if(p1Ins.length()<3){
            listaErrores = "SU CONTRASEÑA DEBE TENER MAS DE 3 DIGITOS";
            Log.i(TAG,"CONTRASEÑA NO SEGURA");
            informacion.setText(listaErrores);
            crearCuenta.setText("VERIFICAR");
        }else{
            if(p2Ins.equals(p1Ins)){
                actualizarRTDB();
            }else{
                listaErrores = "SU CONTRASEÑA NO COINCIDE.";
                Log.i(TAG,"CONTRASEÑA NO COINCIDE");
                informacion.setText(listaErrores);
                crearCuenta.setText("VERIFICAR");
            }
        }
    }

    private void actualizarRTDB() {
        //implementar agregar datos

        mAuth.createUserWithEmailAndPassword(emailIns,p1Ins).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(TAG,"Se creo cuenta exitosamente - RTDB");
                    informacion.setText("FELICIDADES UD. CREO LA CUENTA INSTITUCIONAL EXITOSAMENTE.");
                    Map<String,Object> map = new HashMap<>();
                    map.put("nameIns",nIns);
                    map.put("addIns",dirIns);
                    map.put("gerIns",gerIns);
                    map.put("telfIns",telIns);
                    map.put("nameEOI",nEOIns);
                    map.put("emailIns",emailIns);
                    map.put("passIns",p1Ins);
                    FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI(user);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Inst").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()) Log.i(TAG,"Se Agrego datos - RTDB");
                            else {
                                Toast.makeText(crearInstitucion.this, "No se pudo crear cuenta en RTDB", LENGTH_SHORT).show();
                                Log.i(TAG,"no se pudo agregar - RTDB");
                            }
                        }
                    });
                    finish();
                }else{
                    Log.e(TAG,"NO SE PUDO AÑADIR CORREO Y PASSWORD - RTDB");
                    informacion.setText("CORREO ELECTRONICO YA EXISTE.");
                }
            }
        });
    }
}