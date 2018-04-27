package com.example.yeye.rchispacarbonapp.interfaces;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yeye.rchispacarbonapp.R;

import fragments.ListaPedidosFragment;

public class PedidoActivity extends AppCompatActivity implements ListaPedidosFragment.OnFragmentInteractionListener{
    ListaPedidosFragment listaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        listaFragment = new ListaPedidosFragment();
        //Cargar el fragment
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.contenedorFragment, listaFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
