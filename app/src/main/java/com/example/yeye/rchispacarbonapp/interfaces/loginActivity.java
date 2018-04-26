package com.example.yeye.rchispacarbonapp.interfaces;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yeye.rchispacarbonapp.R;

public class loginActivity extends AppCompatActivity {
	/*
	Botón para realizar la validación de logueo a la aplicación móvil
	 */
	private Button Entrar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Entrar = (Button) findViewById(R.id.btnEntrar);
		Entrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			   // Intent i = new Intent(getApplicationContext(), MainActivity.class);
			   // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
			  //          Intent.FLAG_ACTIVITY_NEW_TASK);
			  //  startActivity(i);


				// se captura la direccion de envio de pedido de la BD y se le hace casting a
				// URI para establecer la ruta mas corta para llegar alli

				Uri gmmIntentUri = Uri.parse("google.navigation:q=universidad+del+quindio,+Armenia+Colombia");
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
				mapIntent.setPackage("com.google.android.apps.maps");
				if (mapIntent.resolveActivity(getPackageManager()) != null) {
				startActivity(mapIntent);
				}
				else{
					mostrarMensaje("El servicio de ubicación no funciona en el momento");
				}
			}
		});

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
