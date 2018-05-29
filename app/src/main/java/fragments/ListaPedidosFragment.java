package fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yeye.rchispacarbonapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import adaptador_pedido.AdaptadorPedido;
import entidades.PedidoVo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaPedidosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaPedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Calderón - Gomez - Guerrero
 */
public class ListaPedidosFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    /*
    Atributo para almacenar el codigo del repartidor
     */
    private int codigoR;

    /*
    ArrayList para guardar los pedidos asignados al respartidor desde la BD
     */
    private ArrayList<PedidoVo> listaPedido;

    /*
    Recycler para mostrar los datos de los pedidos asignados al repartidor
     */
    private RecyclerView recyclerPedido;

    /*
    Progress para representar la espera en el cargue de los datos
     */
    private ProgressDialog progress;

    /**
     * Constructor del fragment
     */
    public ListaPedidosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPedidosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaPedidosFragment newInstance(String param1, String param2) {
        ListaPedidosFragment fragment = new ListaPedidosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Método donde se guarda el código del repartidor en el atributo codigoR
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //Traer codigo repartidor
            codigoR = getArguments().getInt("Codigo_Repartidor", 1);
        }
    }

    /**
     * Método para inicializar la lista y el recyclerview creados
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_lista_pedidos, container, false);

        //Inicializar la lista
        listaPedido = new ArrayList<>();

        //Inicializar el recycler
        recyclerPedido = vista.findViewById(R.id.recyclerId);
        recyclerPedido.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerPedido.setHasFixedSize(true);

        //Se utiliza los datos extraidos de la api rest
        //llenarListaPedidos();


        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        String fecha = dateFormat.format(date);

        listaPedido.add(new PedidoVo(1, "JAVIER  MARTINEZ", "k 20 2 60 las palmas",
                "7467916", 3, 10000, "Pollo",
                fecha, fecha));
        listaPedido.add(new PedidoVo(2, "ILBIA GIRALDO", "c 22 13 26",
                "7400823", 3, 10000, "Almuerzo",
                fecha, fecha));
        listaPedido.add(new PedidoVo(3, "ALEJANDRO MARULANDA", "k 21a 14 33",
                "3113201019", 3, 10000, "Pollo",
                fecha, fecha));
        listaPedido.add(new PedidoVo(4, "YEFERSON GOMEZ", "C 14N 10 05",
                "3146857668", 3, 10000, "Almuerzo",
                fecha, fecha));
        listaPedido.add(new PedidoVo(5, "CRISTINA VALENCIA", "K 15 23 27",
                "7450351", 3, 10000, "Almuerzo",
                fecha, fecha));
        listaPedido.add(new PedidoVo(6, "JONNY CASTELLANOS", "A 19 24N 04",
                "7477464", 3, 10000, "Pollo",
                fecha, fecha));
        listaPedido.add(new PedidoVo(7, "PROPLAT S.A", "K 23 19 38",
                "7427697", 3, 10000, "Almuerzo",
                fecha, fecha));

        //Borrar luego*/

        //Enviar los datos capturados con la petición GET al adaptador para mostrarlos en el Recyclerview
        AdaptadorPedido adapter = new AdaptadorPedido(listaPedido);

        //Enviar el adaptador al recycler creado
        recyclerPedido.setAdapter(adapter);

        //Establecer evento del adaptador (Modo general)
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó el pedido con ID: "
                                + listaPedido.get(
                        recyclerPedido.getChildAdapterPosition(view)).getIdPedido()
                                + " del repartidor: " + codigoR,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return vista;
    }

    /**
     * Método para conectar a la bd y tomar los valores del pedido
     */
    private void llenarListaPedidos() {

        //Proceso para realizar la petición GET

        String registrar_URL = PedidoVo.URL_AMAZON + "asignados";
        final ProgressDialog loading = ProgressDialog.show(this.getContext(),
                "Por favor espere...",
                "Actualizando datos...",
                false,
                false);

        Map<String, String> params = new HashMap<>();
        params.put("state", "" + codigoR);
        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                registrar_URL,
                jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        try {
                            //Lista obtenida del servicio
                            JSONArray lista = response.getJSONArray("window.json");
                            for (int i = 0; i < lista.length(); i++) {
                                JSONObject jsonData = lista.getJSONObject(i);
                                PedidoVo obj = new PedidoVo(jsonData.getInt("id"),
                                        jsonData.getString("cliente.nombre"),
                                        jsonData.getString("cliente.direccion"),
                                        jsonData.getString("cliente.id"),
                                        jsonData.getInt("state"),
                                        jsonData.getDouble("total"),
                                        jsonData.getString("items.nombre"),
                                        jsonData.getString("creado"),
                                        jsonData.getString("entregado"));
                                listaPedido.add(obj);
                            }

                        } catch (Exception ex) {
                            mostrarMensaje("Error cargando lista:" + ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.d("error en peticion:", error.getMessage());
                mostrarMensaje("Error en petición:" + error.getMessage());
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(jsonObjReq);

    }

    /**
     * método para mostrar un mensaje toast
     *
     * @param message, el mensaje a mostrar
     */
    private void mostrarMensaje(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    //Métodos heredados por el fragment (No se utilizan)

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        //No se usa esta interface
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
