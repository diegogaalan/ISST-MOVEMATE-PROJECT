package isst.isst_group21.movemate_back.DTO;

import isst.isst_group21.movemate_back.models.Actividad;
import jakarta.validation.constraints.NotBlank;

public class ActividadDTO {
    @NotBlank private String nombre;
    @NotBlank private String descripcion;
    @NotBlank private String fechaHora;
    @NotBlank private double precio;
    @NotBlank private int aforoMaximo;
    @NotBlank private String ubicacion;
    private int numReservas;
    private double lat;
    private double lon;
    private String categoria;
    private String idActividad;

    //Nuevo campo para enviar la imagen como Base64 al frontend
    private String fotoBase64;


    public ActividadDTO() {
    }

    // Constructor completo (sin imagen)
    public ActividadDTO(String idActividad, String nombre, String descripcion, String fechaHora,
                        double precio, int aforoMaximo, int numReservas, String ubicacion,
                        double lat, double lon, String categoria) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.aforoMaximo = aforoMaximo;
        this.numReservas = numReservas;
        this.ubicacion = ubicacion;
        this.lat = lat;
        this.lon = lon;
        this.categoria = categoria;
    }

  
    public ActividadDTO(Actividad actividad) {
        this.idActividad = actividad.getIdActividad();
        this.nombre = actividad.getNombre();
        this.descripcion = actividad.getDescripcion();
        this.fechaHora = actividad.getFechaHora().toString();
        this.precio = actividad.getPrecio();
        this.aforoMaximo = actividad.getAforoMaximo();
        this.numReservas = actividad.getNumReservas();
        this.ubicacion = actividad.getUbicacion();
        this.lat = actividad.getLatitud();
        this.lon = actividad.getLongitud();
        this.categoria = actividad.getCategoria().name();

        // ✅ Codifica la imagen en Base64 si está disponible
        if (actividad.getFoto() != null) {
            this.fotoBase64 = java.util.Base64.getEncoder().encodeToString(actividad.getFoto());
        }
    }

    // Getters y Setters

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public int getNumReservas() {
        return numReservas;
    }

    public void setNumReservas(int numReservas) {
        this.numReservas = numReservas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double latitud) {
        this.lat = latitud;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double longitud) {
        this.lon = longitud;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }


    @Override
    public String toString() {
        return "ActividadDTO{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaHora='" + fechaHora + '\'' +
                ", precio=" + precio +
                ", aforoMaximo=" + aforoMaximo +
                ", ubicacion='" + ubicacion + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
