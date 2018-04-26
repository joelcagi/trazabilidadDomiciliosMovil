package adaptadorPedido;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yeye.rchispacarbonapp.R;

import java.util.ArrayList;

import entidades.PedidoVo;

public class AdaptadorPedido extends RecyclerView.Adapter<AdaptadorPedido.PedidosViewHolder> implements  View.OnClickListener

{

    ArrayList<PedidoVo> listaPedidos;
    private View.OnClickListener listener;

    public AdaptadorPedido(ArrayList<PedidoVo> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        view.setOnClickListener(this);
        return new PedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PedidosViewHolder holder, int position) {
        holder.txtidPedido.setText(listaPedidos.get(position).getIdPedido());
        holder.txtCliente.setText(listaPedidos.get(position).getNombreCliente());
        holder.txtDireccion.setText(listaPedidos.get(position).getDireccionCliente());
        holder.txtTelefono.setText(listaPedidos.get(position).getTelefonoCliente());
        holder.txtVrDomicilio.setText(""+listaPedidos.get(position).getCostoDomicilioZona());
        holder.txtVrPedido.setText(""+listaPedidos.get(position).getCostoPedido());
        holder.txtFechaInicio.setText(""+listaPedidos.get(position).getFechaDespacho());
        holder.txtFechaFin.setText(""+listaPedidos.get(position).getFechaEntrega());

    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder {

        TextView txtidPedido, txtCliente, txtDireccion, txtTelefono, txtVrDomicilio, txtVrPedido,
                txtFechaInicio, txtFechaFin;
        Spinner comboestado;

        public PedidosViewHolder(View itemView) {
            super(itemView);
            txtidPedido = itemView.findViewById(R.id.lblidpedidos);
            txtCliente = itemView.findViewById(R.id.lblnombrecliente);
            txtDireccion = itemView.findViewById(R.id.lbldireccioncliente);
            txtTelefono = itemView.findViewById(R.id.lbltelefonocliente);
            txtVrDomicilio = itemView.findViewById(R.id.lblvrdomicilio);
            txtVrPedido = itemView.findViewById(R.id.lblvrpedido);
            txtFechaInicio = itemView.findViewById(R.id.lblfechaInicio);
            txtFechaFin = itemView.findViewById(R.id.lblfechaFin);
            comboestado = itemView.findViewById(R.id.spinnerEstadoPedido);

            //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.combo_estados,
            //       android.R.layout.simple_spinner_item);

            //comboestado.setAdapter(adapter);
        }
    }
}
