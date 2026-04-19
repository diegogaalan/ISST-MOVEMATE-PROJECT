package isst.isst_group21.movemate_back.models;

import isst.isst_group21.movemate_back.enums.Categorias;
import java.time.LocalDateTime;
import java.util.Random;
import jakarta.persistence.*; 
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "ACTIVIDAD") 
@Getter
@Setter
public class Actividad {
    // Será el DNI puesto por el organizador +  7 numeros aleatorios
    @Id
    @Column(name = "ID_ACTIVIDAD", length = 15)
    private String idActividad;

    @Column(name = "NOMBRE",nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING) 
    @Column(name = "CATEGORIA",nullable = false)
    private Categorias categoria;

    @Column(name = "DESCRIPCION",nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Future
    @Column(name ="FECHAYHORA",nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "PRECIO", nullable = false)
    private double precio;

    @Column(name = "AFORO_MAX", nullable = false)
    private int aforoMaximo;

    @Transient
    private int numReservas;

    @Column(name = "UBICACION", nullable = false)
    private String ubicacion;

    @Column(name = "LATITUD", nullable = false)
    private double lat;

    @Column(name = "LONGITUD", nullable = false)
    private double lon;

    @Lob
    @Column(name = "FOTO", columnDefinition = "BLOB")
    private byte[] foto;

    @Column(name = "CONFIRMADA", nullable = false)
    private boolean confirmada;

    //(Un organizador puede tener varias actividades) solo se carga cuando se accede a la propiedad
    // CascadeType.PERSIST: si se persiste una actividad, se persiste el organizador
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_ORGANIZADOR", nullable = false)
    private Organizador organizador;

    // Constructor vacío
    public Actividad() {
    }

    // Constructor completo
    public Actividad(String idActividad, String nombre, Categorias categoria, String descripcion, LocalDateTime fechaHora, double precio, int aforoMax, int numReservas, String ubicacion, double latitud, double longitud, byte[] foto, boolean confirmada, Organizador organizador) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.aforoMaximo = aforoMax;
        this.ubicacion = ubicacion;
        this.lat = latitud;
        this.lon = longitud;
        this.foto = foto;
        this.confirmada = confirmada;
        this.organizador = organizador;
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

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
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

    public double getLatitud() {
        return lat;
    }

    public void setLatitud(double latitud) {
        this.lat = latitud;
    }

    public double getLongitud() {
        return lon;
    }

    public void setLongitud(double longitud) {
        this.lon = longitud;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public static String generarIdActividad(Organizador organizador) {
        String dniSinLetra = organizador.getDniSinLetra();
        Random random = new Random();
        int numeroAleatorio = 1000000 + random.nextInt(9000000); // 7 cifras
        return dniSinLetra + numeroAleatorio;
    }

    // Método toString
    @Override 
    public String toString() {
        return "Actividad [confirmada=" + confirmada + "categoria=" + categoria + ", descripcion=" + descripcion + ", fechaHora=" + fechaHora
                + ", id_actividad=" + idActividad + ", nombre=" + nombre + ", organizador=" + organizador + ", precio="
                + precio + ", aforo maximo=" + aforoMaximo + ", ubicacion=" + ubicacion + ", latitud=" + lat + ", longitud=" + lon + "]";
    }

}
