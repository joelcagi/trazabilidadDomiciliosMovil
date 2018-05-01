package adaptadorPedido;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.yeye.rchispacarbonapp.R;

import java.util.ArrayList;
import java.util.Date;

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

    /**
     * Constructor de la clase AdaptadorPedido
     *
     * @param listaPedidos, la lista de los pedidos asignados al repartidor
     */
    public AdaptadorPedido(ArrayList<PedidoVo> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    /**
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

    @Override
    public void onBindViewHolder(PedidosViewHolder holder, final int position) {

        holder.txtidPedido.setText("Id Pedido: " + listaPedidos.get(position).getIdPedido());
        holder.txtCliente.setText("Cliente: " + listaPedidos.get(position).getNombreCliente());
        holder.txtDireccion.setText("Dirección: " + listaPedidos.get(position).getDireccionCliente());

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

        holder.txtTelefono.setText("Telefono: " + listaPedidos.get(position).getTelefonoCliente());
        holder.txtVrDomicilio.setText("Costo domicilio: " + listaPedidos.get(position).getCostoDomicilioZona());
        holder.txtVrPedido.setText("Costo Pedido: " + listaPedidos.get(position).getCostoPedido());
        holder.txtFechaInicio.setText("Fecha Despacho: " + listaPedidos.get(position).getFechaDespacho());
        holder.txtFechaFin.setText("Fecha Entrega: " + listaPedidos.get(position).getFechaEntrega());

        /*
      Capturar el estado del pedido
         */
        final int posRV = position;
        holder.comboestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listaPedidos.get(posRV).setEstadoPedido(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Evento para cambiar el estado del pedido y subirlo a la BD
        holder.btnCambiarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //Implementación del AlertDialog
                final Context viewD = view.getContext();
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Estado Pedido")
                        .setMessage("¿Desea cambiar el estado del pedido "
                                + codigoP + " a '"
                                + listaPedidos.get(posRV).getEstadoPedido() + "'?")
                        .setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {

                                        //se pasan los parametros a la BD del nuevo estado y la hora
                                        // y fecha que se cambió (Petición PATH)

                                        listaPedidos.get(posRV).setFechaEntrega(new Date());

                                        Toast.makeText(viewD, "Cambio estado pedido a '" +
                                                        listaPedidos.get(posRV).getEstadoPedido() +
                                                "' el " + listaPedidos.get(posRV).getFechaEntrega(),
                                                Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();

            }
        });

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
     * método para establecer direccion para enviar a google maps
     * @param direccionI, la cadena que posee la dirección
     * @param aux, la nueva cadena con la dirección
     * @param pos, la posición a iniciar el cargue de datos en la variable aux
     * @return
     */
    private String construirDireccion(String direccionI, String aux, int pos) {
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

        for (int i = pos; i < direccionI.trim().length(); i++) {
            if (direccionI.charAt(i) == ' ') {
                aux = aux + "+";
            } else {
                aux = aux + direccionI.charAt(i);
            }
        }
        return aux;
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }


    public class PedidosViewHolder extends RecyclerView.ViewHolder {

        TextView txtidPedido, txtCliente, txtDireccion, txtTelefono, txtVrDomicilio, txtVrPedido,
                txtFechaInicio, txtFechaFin;
        Spinner comboestado;
        Button btnCambiarEstado, btnVerRuta;
        Context context;

        public PedidosViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtidPedido = itemView.findViewById(R.id.lblidpedidos);
            txtCliente = itemView.findViewById(R.id.lblnombrecliente);
            txtDireccion = itemView.findViewById(R.id.lbldireccioncliente);
            txtTelefono = itemView.findViewById(R.id.lbltelefonocliente);
            txtVrDomicilio = itemView.findViewById(R.id.lblvrdomicilio);
            txtVrPedido = itemView.findViewById(R.id.lblvrpedido);
            txtFechaInicio = itemView.findViewById(R.id.lblfechaInicio);
            txtFechaFin = itemView.findViewById(R.id.lblfechaFin);
            comboestado = itemView.findViewById(R.id.spinnerEstadoPedido);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.combo_estados,
                    android.R.layout.simple_spinner_dropdown_item);

            comboestado.setAdapter(adapter);

            btnCambiarEstado = itemView.findViewById(R.id.btnCambioEstado);
            btnVerRuta = itemView.findViewById(R.id.btnVerRuta);

        }
    }
}
