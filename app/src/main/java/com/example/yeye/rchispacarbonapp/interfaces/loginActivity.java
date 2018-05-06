package com.example.yeye.rchispacarbonapp.interfaces;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yeye.rchispacarbonapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entidades.PedidoVo;

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
    /*
    progressDialog para el logueo
     */
    private ProgressDialog pDialog;
    /*
    Objeto para traer del servicio creado el codigo del usuario
     */
    private JsonObjectRequest jsonObjectRequest;
    /*
    la request respectiva
     */
    private RequestQueue request;
    /*
    Código del usuario
     */
    private int idUsuario = 0;

    /**
     * Método para iniciar la Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializar la request
        request = Volley.newRequestQueue(this);

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



                //consultarUsuario(usuario, contrasenia);

                Intent i = new Intent(getApplicationContext(), PedidoActivity.class);

                //PENDIENTE PASAR PARAMETRO DE CODIGO DE REPARTIDOR
                i.putExtra("Codigo_Repartidor", idUsuario);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);

                //Iniciar la actividad
                startActivity(i);

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

    /**
     * Método para cargar los datos de la BD (PDTE REVISIÖN)
     */
    private void consultarUsuario(String usuario, String contrasena) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando...");
        pDialog.show();

        String url = PedidoVo.URL_AMAZON + "asignados?correo(REVISAR NOMBRE)=" + usuario.trim()
                + "&contrasena(REVISAR NOMBRE)=" + contrasena.trim();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();

                        JSONArray json = response.optJSONArray("codigoUsuario (REVISAR BD)");
                        JSONObject jsonObject = null;

                        try {
                            jsonObject = json.getJSONObject(0);
                            idUsuario = jsonObject.optInt("id_usuario(REVISAR BD)");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarMensaje("Usuario no registrado " + error.toString());
                pDialog.hide();
            }
        });

        request.add(jsonObjectRequest);
    }
}
