package adaptadorPedido;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yeye.rchispacarbonapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entidades.PedidoVo;

/**
 * Clase adaptadora para llenar el recyclerview con los pedidos asignados al repartidor
 *
 * @author Calderón - Gomez - Guerrero
 */
public class AdaptadorPedido extends RecyclerView.Adapter<AdaptadorPedido.PedidosViewHolder> implements View.OnClickListener

{
    /*
    lista para almacenar los datos de un pedido
     */
    ArrayList<PedidoVo> listaPedidos;

    /*
    oyente del evento de clic sobre un objeto del recyclerview
     */
    private View.OnClickListener listener;

    /*
     * Botón para bloquear despues de cambiar estado pedido
     */
    private Button boton = null;

    /*
    Spinner para bloquear despues de cambiar estado pedido
     */
    private Spinner spinner = null;

    /**
     * Constructor de la clase AdaptadorPedido
     *
     * @param listaPedidos, la lista de los pedidos asignados al repartidor
     */
    public AdaptadorPedido(ArrayList<PedidoVo> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    /**
     * Método para cargar los elementos del recyclerview en PedidosViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(this);
        return new PedidosViewHolder(view);
    }

    /**
     * Método para cargar los datos de la lista en cada uno de los elementos del recyclerview
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PedidosViewHolder holder, final int position) {

        //Cargar datos de la lista en los componentes del RecyclerView
        holder.txtidPedido.setText("Id Pedido: " + listaPedidos.get(position).getIdPedido());
        holder.txtCliente.setText("Cliente: " + listaPedidos.get(position).getNombreCliente());
        holder.txtDireccion.setText("Dirección: " + listaPedidos.get(position).getDireccionCliente());
        holder.txtTelefono.setText("Telefono: " + listaPedidos.get(position).getTelefonoCliente());
        holder.txtProducto.setText("Producto: " + listaPedidos.get(position).getNombreProducto());
        holder.txtVrPedido.setText("Costo Pedido: " + listaPedidos.get(position).getCostoPedido());
        holder.txtFechaInicio.setText("Fecha Despacho: " + listaPedidos.get(position).getHoraDespacho());
        holder.txtFechaFin.setText("Fecha Entrega: " + listaPedidos.get(position).getHoraEntrega());

        /*
        Armar dirección parametro Google Maps
         */
        String direccionI = "" + listaPedidos.get(position).getDireccionCliente();
        String aux = "";
        int pos = 0;
        aux = construirDireccion(direccionI, aux, pos);

        /*
        Enviar parametro de direccion de busqueda a googlemaps
         */
        final String cadena = aux;

        /*
        Definir estado pedido
         */
        final String estadoP = "";

        /*
        variable para establecer el idpedido en el alertdialog
         */
        final int codigoP = listaPedidos.get(position).getIdPedido();


        /*
        Capturar el estado del pedido
         */
        final int posRV = position;
        CapturarEstadoPedido(holder, posRV);

        //Evento para cambiar el estado del pedido y subirlo a la BD
        eventoCambioPedido(holder, codigoP, posRV);

        //Evento para generar la ruta del pedido de un cliente
        eventoGenerarRuta(holder, cadena);

    }

    /**
     * Evento para generar ruta del pedido en GoogleMaps
     *
     * @param holder el boton del pedido en especifico
     * @param cadena el texto con la dirección en el formato para GoogleMaps
     */
    public void eventoGenerarRuta(PedidosViewHolder holder, final String cadena) {
        holder.btnVerRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // se captura la direccion de envio de pedido de la BD y se le hace casting a
                // URI para establecer la ruta mas corta para llegar alli

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + cadena + ",+Armenia+Colombia");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
                    view.getContext().startActivity(mapIntent);
                } else {
                    Toast.makeText(view.getContext(), "Servicio no disponible ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Evento para capturar el estado del pedido en un spinner
     *
     * @param holder el spinner del pedido en especifico
     * @param posRV  la posición en la que se encuentra en el Recyclerview
     */
    public void CapturarEstadoPedido(final PedidosViewHolder holder, final int posRV) {
        holder.comboestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner = holder.comboestado.findViewById(R.id.spinnerEstadoPedido);
                if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Entregado")) {
                    listaPedidos.get(posRV).setEstadoPedido(4);
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Incidencia")) {
                    listaPedidos.get(posRV).setEstadoPedido(5);
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Cancelado")) {
                    listaPedidos.get(posRV).setEstadoPedido(6);
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Erroneo")) {
                    listaPedidos.get(posRV).setEstadoPedido(7);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Evento para cambiar el estado de un pedido y acutalizar la BD
     *
     * @param holder  el del botón
     * @param codigoP el código del pedido
     * @param posRV   la posición en el recyclerview
     */
    public void eventoCambioPedido(final PedidosViewHolder holder, final int codigoP, final int posRV) {

        holder.btnCambiarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                boton = holder.btnCambiarEstado.findViewById(R.id.btnCambioEstado);

                generarAlertDialog(holder, view.getContext(), codigoP, posRV, boton);

            }
        });


    }

    /**
     * Método para generar una alerta antes de cambiar estado de pedido
     *
     * @param viewD   el contexto de la vista
     * @param codigoP el código del pedido
     * @param posRV   la posición del recyclerview
     */
    public void generarAlertDialog(final PedidosViewHolder holder, final Context viewD,
                                   int codigoP, final int posRV, final Button boton) {
        new AlertDialog.Builder(viewD)
                .setTitle("Estado Pedido")
                .setMessage("¿Desea cambiar el estado del pedido "
                        + codigoP + " a '"
                        + listaPedidos.get(posRV).getEstadoPedido() + "'?")
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                //se pasan los parametros a la BD del nuevo estado y la hora
                                // y fecha que se cambió

                                listaPedidos.get(posRV).setHoraEntrega(obtenerHoraActual());

                                holder.txtFechaFin.setText("Fecha Entrega: " + listaPedidos.get(posRV).getHoraEntrega());

                                //Hacer petición POST


                                Toast.makeText(viewD, "Cambio estado pedido"
                                                + listaPedidos.get(posRV).getIdPedido()
                                                + " a '" + listaPedidos.get(posRV).getEstadoPedido()
                                                + "' a las " + listaPedidos.get(posRV).getHoraEntrega(),
                                        Toast.LENGTH_SHORT).show();

                                boton.setEnabled(false);
                                boton.setTextColor(Color.LTGRAY);
                                boton.setBackgroundColor(Color.GRAY);
                                spinner.setEnabled(false);

                                dialog.cancel();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }

    /**
     * método para establecer direccion para enviar a google maps
     *
     * @param direccionI, la cadena que posee la dirección
     * @param aux,        la nueva cadena con la dirección
     * @param pos,        la posición a iniciar el cargue de datos en la variable aux
     * @return
     */
    public String construirDireccion(String direccionI, String aux, int pos) {
        if (direccionI.charAt(0) == 'K' && direccionI.charAt(1) == ' ' ||
                direccionI.charAt(0) == 'k' && direccionI.charAt(1) == ' ') {
            aux = "carrera+";
            pos = 2;
        } else if (direccionI.charAt(0) == 'C' && direccionI.charAt(1) == ' ' ||
                direccionI.charAt(0) == 'c' && direccionI.charAt(1) == ' ') {
            aux = "calle+";
            pos = 2;
        } else if (direccionI.charAt(0) == 'A' && direccionI.charAt(1) == ' ' ||
                direccionI.charAt(0) == 'a' && direccionI.charAt(1) == ' ') {
            aux = "avenida+";
            pos = 2;
        }

        StringBuilder auxBuilder = new StringBuilder(aux);
        for (int i = pos; i < direccionI.trim().length(); i++) {
            if (direccionI.charAt(i) == ' ') {
                auxBuilder.append("+");
            } else {
                auxBuilder.append(direccionI.charAt(i));
            }
        }
        aux = auxBuilder.toString();
        return aux;
    }

    /**
     * Método para obtener la fecha actual del sistema
     *
     * @return
     */
    public String obtenerHoraActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void actualizarServicio(final Context context, final int posRV) {
        final ProgressDialog loading = ProgressDialog.show(context, "Por favor espere...",
                "Actualizando datos...", false, false);

        String REGISTER_URL = PedidoVo.URL_AMAZON + "asignados?id=" + listaPedidos.get(posRV).getIdPedido()
                + "&state=" + listaPedidos.get(posRV).getEstadoPedido() + "&entregado=" + listaPedidos.get(posRV).getHoraEntrega();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("state", "" + listaPedidos.get(posRV).getEstadoPedido());
                params.put("entregado", listaPedidos.get(posRV).getHoraEntrega());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    /**
     * Método para obtener el tamaño total de la lista de pedidos generada
     *
     * @return el tamaño de la lista
     */
    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    /**
     * Evento para la selección del recycler en general
     *
     * @param listener
     */
    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Evento de selección del recycler
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    /**
     * viewholder para cada uno de los elementos del recycler
     */
    public class PedidosViewHolder extends RecyclerView.ViewHolder {

        TextView txtidPedido, txtCliente, txtDireccion, txtTelefono, txtProducto, txtVrPedido,
                txtFechaInicio, txtFechaFin;
        Spinner comboestado;
        Button btnCambiarEstado, btnVerRuta;

        /**
         * Constructor de la clase PedidosViewHolder
         *
         * @param itemView
         */
        public PedidosViewHolder(View itemView) {
            super(itemView);

            txtidPedido = itemView.findViewById(R.id.lblidpedidos);
            txtCliente = itemView.findViewById(R.id.lblnombrecliente);
            txtDireccion = itemView.findViewById(R.id.lbldireccioncliente);
            txtTelefono = itemView.findViewById(R.id.lbltelefonocliente);
            txtProducto = itemView.findViewById(R.id.lblProducto);
            txtVrPedido = itemView.findViewById(R.id.lblvrpedido);
            txtFechaInicio = itemView.findViewById(R.id.lblfechaInicio);
            txtFechaFin = itemView.findViewById(R.id.lblfechaFin);
            comboestado = itemView.findViewById(R.id.spinnerEstadoPedido);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(),
                    R.array.combo_estados,
                    android.R.layout.simple_spinner_dropdown_item);
            comboestado.setAdapter(adapter);

            btnCambiarEstado = itemView.findViewById(R.id.btnCambioEstado);
            btnVerRuta = itemView.findViewById(R.id.btnVerRuta);

        }
    }
}
