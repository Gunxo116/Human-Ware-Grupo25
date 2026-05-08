package human.ware;

import java.time.LocalDate;
import java.time.Period;

/**
 * Perfil personal de un Postulante.
 * Composición 1-1 con Postulante: no existe sin él.
 */
public class PerfilDatos {

    // ── Atributos ──────────────────────────────────────────────────────────
    private String cuil;           // único, formato XX-XXXXXXXX-X
    private String sexo;           // según documento
    private LocalDate fechaNacimiento;
    private String nombre;
    private String email;
    private String telefono;

    // ── Constructor ────────────────────────────────────────────────────────
    public PerfilDatos(String cuil, String sexo, LocalDate fechaNacimiento,
                       String nombre, String email, String telefono) {
        if (cuil == null || cuil.isBlank())
            throw new IllegalArgumentException("CUIL no puede estar vacío.");
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre no puede estar vacío.");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido.");

        this.cuil            = cuil;
        this.sexo            = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre          = nombre;
        this.email           = email;
        this.telefono        = telefono;
    }

    // ── Getters ────────────────────────────────────────────────────────────
    public String getCuil()                { return cuil; }
    public String getSexo()                { return sexo; }
    public LocalDate getFechaNacimiento()  { return fechaNacimiento; }
    public String getNombre()              { return nombre; }
    public String getEmail()               { return email; }
    public String getTelefono()            { return telefono; }

    // ── Métodos de negocio ────────────────────────────────────────────────
    /**
     * Calcula la edad actual del postulante en años.
     */
    public int getEdad() {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    // ── Setters (para actualizar perfil) ───────────────────────────────────
    public void setEmail(String email) {
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido.");
        this.email = email;
    }

    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setSexo(String sexo)         { this.sexo = sexo; }
    public void setNombre(String nombre)     { this.nombre = nombre; }

    // ── Object overrides ───────────────────────────────────────────────────
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PerfilDatos other)) return false;
        return cuil.equals(other.cuil);
    }

    @Override
    public int hashCode() { return cuil.hashCode(); }

    @Override
    public String toString() {
        return "PerfilDatos{cuil='" + cuil + "', nombre='" + nombre +
               "', email='" + email + "', edad=" + getEdad() + "}";
    }
}
