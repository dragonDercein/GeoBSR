package com.example.geobsr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";
    private RadioButton rb1, rb2;
    private Button bt1, bt2;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rb1 = (RadioButton) findViewById(R.id.rb_1);
        rb2 = (RadioButton) findViewById(R.id.rb_2);
        bt1 = (Button) findViewById(R.id.bt_1);
        bt2 = (Button) findViewById(R.id.bt_2);
        rg = (RadioGroup) findViewById(R.id.rg_opc);
    }

    //Método que permite logearse como usuario o crear un nueva Intitución
    public void continuar(View view){
        int select = rg.getCheckedRadioButtonId();
        Log.i(TAG,"RECUPERA VALOR" + select);
        if(select == -1 ){
            Toast.makeText(this, "Seleccione una Opción", Toast.LENGTH_LONG).show();
            Log.i(TAG,"No selecciono algún Item");
        }else{
            Intent intent;
            if(select == R.id.rb_1){
                intent = new Intent(MainActivity.this, loguser.class);
                startActivity(intent);
            }else{
                if(select == R.id.rb_2){
                    intent = new Intent(MainActivity.this, crearInstitucion.class);
                    startActivity(intent);
                }
            }
        }

    }

    //Método que saldra de la aplicación
    public void salir (View view){
        finish();
    }
}