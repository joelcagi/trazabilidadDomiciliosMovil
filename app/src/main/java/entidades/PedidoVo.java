package entidades;

/**
 * Clase para almacenar información tomada de la BD de la plataforma web
 */
public class PedidoVo {
    /*
    Url del servicio en amazon
     */
    public static final String URL_AMAZON = "ec2-18-191-11-199.us-east-2.compute.amazonaws.com:8080/api/";
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
    El estado final del pedido a definir por parte del repartidor de domicilio
     */
    private int estadoPedido;
    /*
    El costo del pedido realizado
     */
    private double costoPedido;
    /*
    El nombre del producto a entregar con el pedido
     */
    private String nombreProducto;
    /*
    la fecha en la que se toma el pedido
     */
    private String horaDespacho;
    /*
    fecha en la que se entrega el pedido
     */
    private String horaEntrega;

    public PedidoVo(int idPedido, String nombreCliente, String direccionCliente,
                    String telefonoCliente, int estadoPedido, double costoPedido,
                    String nombreProducto, String horaDespacho, String horaEntrega) {
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.estadoPedido = estadoPedido;
        this.costoPedido = costoPedido;
        this.nombreProducto = nombreProducto;
        this.horaDespacho = horaDespacho;
        this.horaEntrega = horaEntrega;
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

    public int getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(int estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public double getCostoPedido() {
        return costoPedido;
    }

    public void setCostoPedido(double costoPedido) {
        this.costoPedido = costoPedido;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getHoraDespacho() {
        return horaDespacho;
    }

    public void setHoraDespacho(String horaDespacho) {
        this.horaDespacho = horaDespacho;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }
}
