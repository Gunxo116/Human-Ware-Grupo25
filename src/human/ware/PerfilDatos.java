package human.ware;

import java.time.LocalDate;
import java.time.Period;

// Datos personales del postulante. Composicion 1-1 con Postulante.
public class PerfilDatos {

    private String cuil;
    private String sexo;
    private LocalDate fechaNacimiento;
    private String nombre;
    private String email;
    private String telefono;

    public PerfilDatos(String cuil, String sexo, LocalDate fechaNacimiento,
                       String nombre, String email, String telefono) {
        this.cuil = cuil;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // Calcula la edad actual
    public int getEdad() {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    // Getters
    public String getCuil()               { return cuil; }
    public String getSexo()               { return sexo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getNombre()             { return nombre; }
    public String getEmail()              { return email; }
    public String getTelefono()           { return telefono; }

    // Setters para actualizar perfil
    public void setNombre(String nombre)     { this.nombre = nombre; }
    public void setEmail(String email)       { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setSexo(String sexo)         { this.sexo = sexo; }

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