package entidades;

import java.util.Date;

/**
 * Clase para almacenar información tomada de la BD de la plataforma web
 */
public class PedidoVo {
    /*
    El codigo del pedido
     */
    private int idPedido;
    /*
    El nombre del cliente
     */
    private String nombreCliente;
    /*
    La direccion del cliente
     */
    private String direccionCliente;
    /*
    El telefono del cliente
     */
    private String telefonoCliente;
    /*
    El vslor adicional por domicilio a una zona
     */
    private double costoDomicilioZona;
    /*
    El costo del pedido realizado
     */
    private double costoPedido;
    /*
    El estado final del pedido a definir por parte del repartidor de domicilio
     */
    private String estadoPedido;
    /*
    la fecha en la que se toma el pedido
     */
    private Date fechaDespacho;
    /*
    fecha en la que se entrega el pedido
     */
    private Date fechaEntrega;

    public PedidoVo(int idPedido, String nombreCliente, String direccionCliente,
                    String telefonoCliente, double costoDomicilioZona, double costoPedido,
                    String estadoPedido, Date fechaDespacho, Date fechaEntrega) {
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.costoDomicilioZona = costoDomicilioZona;
        this.costoPedido = costoPedido;
        this.estadoPedido = estadoPedido;
        this.fechaDespacho = fechaDespacho;
        this.fechaEntrega = fechaEntrega;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public double getCostoDomicilioZona() {
        return costoDomicilioZona;
    }

    public void setCostoDomicilioZona(double costoDomicilioZona) {
        this.costoDomicilioZona = costoDomicilioZona;
    }

    public double getCostoPedido() {
        return costoPedido;
    }

    public void setCostoPedido(double costoPedido) {
        this.costoPedido = costoPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Date getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(Date fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
