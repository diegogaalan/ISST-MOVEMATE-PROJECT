package isst.isst_group21.movemate_back.enums;

public enum Categorias {
    FÚTBOL,
    BALONCESTO,
    TENIS,
    PÁDEL,
    GOLF,
    NATACIÓN,
    CICLISMO,
    ATLETISMO,
    FITNESS,
    YOGA,
    PILATES,
    CROSSFIT,
    CALISTENIA,
    ESCALADA,
    SURF,
    SKATE,
    SNOWBOARD,
    ESQUÍ,
    PATINAJE,
    BAILE,
    BOXEO,
    KÁRATE,
    JUDO,
    TAEKWONDO,
    KICKBOXING,
    MMA,
    JIUJITSU,
    FLAUTISTA;

    public String getNombreArchivo() {
        return this.name() + ".jpg";
    }
}
