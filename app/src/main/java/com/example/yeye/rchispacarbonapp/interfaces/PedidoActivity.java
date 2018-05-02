package com.example.yeye.rchispacarbonapp.interfaces;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.yeye.rchispacarbonapp.R;
import fragments.ListaPedidosFragment;

/**
 * Activity que muestra los pedidos asignados a un repartidor despues de iniciar sesión en la aplicación
 *
 * @author Calderón - Gomez - Guerrero
 */
public class PedidoActivity extends AppCompatActivity implements ListaPedidosFragment.OnFragmentInteractionListener{
    /*
    fragment para crear el RecyclerView
     */
    private ListaPedidosFragment listaFragment;

    /*
    Bundle para traer el código del repartidor desde loginActivity
     */
    private Bundle codigoR;

    /**
     * Método para cargar el fragment creado
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        //Almacenar el código del pedido
        codigoR = getIntent().getExtras();

        //Crear el fragment
        listaFragment = new ListaPedidosFragment();
        //Pasar el código del empleado
        listaFragment.setArguments(codigoR);
        //Iniciar el fragment
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.contenedorFragment, listaFragment).commit();
    }

    /**
     * No se implementa
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
