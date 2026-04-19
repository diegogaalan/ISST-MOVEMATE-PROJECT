package isst.isst_group21.movemate_back.DTO;

import isst.isst_group21.movemate_back.models.Reserva;

import java.util.Base64;

public class ReservaDTO {
    private String actividadNombre;
    private String fechaActividad;
    //private String fechaReserva;
    private String estado;
    private int numPersonas; //aforo de la actividad
    private String fotoBase64;
    private int idReserva;

    public ReservaDTO(Reserva reserva) {
        this.actividadNombre = reserva.getActividad().getNombre();
        this.fechaActividad = reserva.getActividad().getFechaHora().toString();
        this.estado = reserva.isConfirmada() ? "Aprobada"
                    : (reserva.isCancelada() ? "Rechazada" : "Pendiente");
        this.numPersonas = reserva.getActividad().getAforoMaximo();
        this.idReserva = reserva.getIdReserva().intValue();

        if (reserva.getActividad().getFoto() != null) {
            this.fotoBase64 = "data:image/jpeg;base64," +
                    Base64.getEncoder().encodeToString(reserva.getActividad().getFoto());
        } else {
            this.fotoBase64 = null;
        }
    }

    // Getters y setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    public String getActividadNombre() {
        return actividadNombre;
    }

    public void setActividadNombre(String actividadNombre) {
        this.actividadNombre = actividadNombre;
    }

    public String getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(String fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    /*public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }*/

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
}
