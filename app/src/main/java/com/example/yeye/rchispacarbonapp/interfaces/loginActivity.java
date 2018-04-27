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
			   Intent i = new Intent(getApplicationContext(), PedidoActivity.class);
			   i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
			          Intent.FLAG_ACTIVITY_NEW_TASK);
			   startActivity(i);

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
