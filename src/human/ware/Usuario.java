package human.ware;

/**
 * Cuenta de acceso al sistema.
 * IMPLEMENTACIÓN COMPLETA A CARGO DE DIVISIÓN 1.
 * Stub para compilación independiente de División 2.
 */

public class Usuario {
    private String username;
    private String password;
    private String email;
    private boolean activo;

    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email    = email;
        this.activo   = true;
    }

    public String  getUsername()  { return username; }
    public String  getEmail()     { return email; }
    public boolean isActivo()     { return activo; }
    public void    desactivar()   { this.activo = false; }

    public boolean login(String u, String p) {
        return username.equals(u) && password.equals(p) && activo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Usuario other)) return false;
        return username.equals(other.username);
    }

    @Override
    public int hashCode() { return username.hashCode(); }

    @Override
    public String toString() { return "Usuario{" + username + "}"; }
}
