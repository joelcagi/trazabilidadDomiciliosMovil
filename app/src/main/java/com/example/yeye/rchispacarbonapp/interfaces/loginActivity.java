package com.example.yeye.rchispacarbonapp.interfaces;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yeye.rchispacarbonapp.R;

/**
 * Activity que permite a un repartidor iniciar sesión en la aplicación
 *
 * @author Calderón - Gomez - Guerrero
 */
public class loginActivity extends AppCompatActivity {

    /*
    Botón para realizar la validación de logueo a la aplicación móvil
     */
    private Button Entrar;

    /*
    Campo para almacenar el usuario
     */
    private TextInputLayout txtUsuario;
    /*
    Campo para almacenar la contraseña
     */
    private TextInputLayout txtContrasenia;


    /**
     * Método para iniciar la Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Definición del evento de botón para acceder a los pedidos del repartidor
        Entrar = findViewById(R.id.btnEntrar);
        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Traer datos del campo usuario
                txtUsuario = findViewById(R.id.usuario);
                final String usuario = txtUsuario.getEditText().getText().toString();

                //Traer datos del campo contraseña
                txtContrasenia = findViewById(R.id.contrasena);
                final String contrasenia = txtContrasenia.getEditText().getText().toString();

                //proceso de la api rest GET



                //en caso de que el usuaria exista en la BD (IF)

                //Se autentica el repartidor de domicilios mediante usuario y contraseña,
                // retornando en caso afirmativo el código de este, el cual permite filtrar
                // los pedidos asignados para el por parte del administrador
                Intent i = new Intent(getApplicationContext(), PedidoActivity.class);

                //PENDIENTE PASAR PARAMETRO DE CODIGO DE REPARTIDOR
                i.putExtra("Codigo_Repartidor", 1);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);

                //Iniciar la actividad
                startActivity(i);

                //En caso de que no este en la BD (ELSE)

                mostrarMensaje("El usuario no se encuentra registrado en el sistema");

            }
        });

    }

    /**
     * Método para eliminar la actividad cuando se accede a la cuenta.
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * Método para mostrar un mensaje en un evento de un component
     *
     * @param message
     */
    private void mostrarMensaje(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
