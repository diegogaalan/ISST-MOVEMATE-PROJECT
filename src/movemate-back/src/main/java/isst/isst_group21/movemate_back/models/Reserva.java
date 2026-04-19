package isst.isst_group21.movemate_back.models;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "RESERVA")
@Getter
@Setter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @Column(name = "CONFIRMADA", nullable = false)
    private boolean confirmada;

    @Column(name = "CANCELADA",nullable = false)
    private boolean cancelada;

    @Column(name = "PROCESANDO",nullable = false)
    private boolean enProceso;

/*@Column(name = "FECHA_RESERVA", nullable = false)
    private LocalDateTime fechaReserva;*/


    @JsonIgnoreProperties({"reservas", "organizador"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACTIVIDAD", nullable = false)
    private Actividad actividad;

    @JsonIgnoreProperties({"reservas"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;
    /*// Relación con Usuario (Un usuario puede tener varias reservas)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    // Relación con Actividad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACTIVIDAD", nullable = false)
    private Actividad actividad; */

    // Constructor vacío
    public Reserva() {
    }

    // Constructor completo
    public Reserva(boolean confirmada, boolean cancelada, boolean enProceso,Usuario usuario, Actividad actividad) {
        this.confirmada = confirmada;
        this.cancelada = cancelada;
        this.enProceso = enProceso;
        this.usuario = usuario;
        this.actividad = actividad;
    }

    // Getters y Setters

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    public boolean isEnProceso() {
        return enProceso;
    }

    public void setEnProceso(boolean enProceso) {
        this.enProceso = enProceso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    /*public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }
    
    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }*/

    // Método toString
    @Override
    public String toString() {
        return "Reserva [id_reserva=" + idReserva + ", confirmada=" + confirmada + ", cancelada=" + cancelada
                + ", enProceso=" + enProceso + ", usuario=" + usuario + ", actividad=" + actividad + "]";
    }
}
