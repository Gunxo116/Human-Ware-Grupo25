package human.ware;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   HUMAN-Ware - Division 2 - Grupo 25  ");
        System.out.println("========================================\n");

        // ================================================================
        // 1. CATALOGOS (en el proyecto real los provee Division 1)
        // ================================================================

        Skill java      = new Skill(1, "Java",    "Programacion Java");
        Skill sql       = new Skill(2, "SQL",     "Bases de datos");
        Skill python    = new Skill(3, "Python",  "Programacion Python");
        Skill ingles    = new Skill(4, "Ingles",  "Idioma ingles");
        Skill redes     = new Skill(5, "Redes",   "Administracion de redes");
        Skill testing   = new Skill(6, "Testing", "Pruebas de software");

        Degree tds      = new Degree(1, "Tecnicatura en Desarrollo de Software", "");
        Degree ingSis   = new Degree(2, "Ingeniero en Sistemas", "");
        Degree tester   = new Degree(3, "Tester", "");

        // ================================================================
        // 2. USUARIOS
        // ================================================================

        Usuario uYamil     = new Usuario("ybarbeito",  "pass123", "yamilbarbeito5@gmail.com");
        Usuario uEsteban   = new Usuario("eredon",     "pass456", "esteban.redon@gmail.com");
        Usuario uCristian  = new Usuario("csoria",     "pass789", "cristian.soria@gmail.com");
        Usuario uAtahualpa = new Usuario("ablanco",    "pass000", "atahualpa.blanco@gmail.com");

        // ================================================================
        // 3. PERFILES PERSONALES
        // ================================================================

        PerfilDatos perfilYamil = new PerfilDatos(
            "20-45563425-9", "M", LocalDate.of(2004, 5, 1),
            "Barbeito Yamil Emmanuel", "yamilbarbeito5@gmail.com", "2664-45563425"
        );
        PerfilDatos perfilEsteban = new PerfilDatos(
            "20-48112233-4", "M", LocalDate.of(2005, 7, 12),
            "Redon Esteban", "esteban.redon@gmail.com", "2664-38112233"
        );
        PerfilDatos perfilCristian = new PerfilDatos(
            "20-41556677-1", "M", LocalDate.of(2003, 7, 15),
            "Soria Cristian", "cristian.soria@gmail.com", "2664-41556677"
        );
        PerfilDatos perfilAtahualpa = new PerfilDatos(
            "20-43998811-6", "M", LocalDate.of(2004, 11, 30),
            "Blanco Atahualpa", "atahualpa.blanco@gmail.com", "2664-43998811"
        );

        // ================================================================
        // 4. POSTULANTES
        // ================================================================

        Postulante yamil = new Postulante(
            600_000.0, TipoJornada.AMBAS, true, "Moto", tds, perfilYamil, uYamil
        );
        yamil.agregarSkill(new PostulanteSkill(java,   5));
        yamil.agregarSkill(new PostulanteSkill(sql,    4));
        yamil.agregarSkill(new PostulanteSkill(ingles, 3));

        Postulante esteban = new Postulante(
            550_000.0, TipoJornada.COMPLETA, false, "Auto", tds, perfilEsteban, uEsteban
        );
        esteban.agregarSkill(new PostulanteSkill(java,   4));
        esteban.agregarSkill(new PostulanteSkill(python, 5));
        esteban.agregarSkill(new PostulanteSkill(sql,    3));

        Postulante cristian = new Postulante(
            500_000.0, TipoJornada.PARCIAL, true, null, tester, perfilCristian, uCristian
        );
        cristian.agregarSkill(new PostulanteSkill(testing, 5));
        cristian.agregarSkill(new PostulanteSkill(ingles,  4));
        cristian.agregarSkill(new PostulanteSkill(sql,     2));

        Postulante atahualpa = new Postulante(
            650_000.0, TipoJornada.COMPLETA, true, "Auto", ingSis, perfilAtahualpa, uAtahualpa
        );
        atahualpa.agregarSkill(new PostulanteSkill(redes,  5));
        atahualpa.agregarSkill(new PostulanteSkill(java,   3));
        atahualpa.agregarSkill(new PostulanteSkill(ingles, 4));

        // ================================================================
        // 5. OFERTAS LABORALES (publicadas por Division 1)
        // ================================================================

        Oferta ofertaJava = new Oferta("OF-001", "Desarrollador Java Jr", tds,
            List.of(new OfertaSkill(java, 4), new OfertaSkill(sql, 3))
        );
        Oferta ofertaTester = new Oferta("OF-002", "Tester QA", tester,
            List.of(new OfertaSkill(testing, 4), new OfertaSkill(ingles, 3))
        );
        Oferta ofertaPython = new Oferta("OF-003", "Desarrollador Python", tds,
            List.of(new OfertaSkill(python, 4), new OfertaSkill(sql, 2))
        );

        // ================================================================
        // 6. RANKING DE CANDIDATOS POR OFERTA
        // ================================================================

        System.out.println("--- Ranking: " + ofertaJava.getTitulo() + " ---");
        mostrarRanking(ofertaJava, yamil, esteban, cristian, atahualpa);

        System.out.println("\n--- Ranking: " + ofertaTester.getTitulo() + " ---");
        mostrarRanking(ofertaTester, yamil, esteban, cristian, atahualpa);

        System.out.println("\n--- Ranking: " + ofertaPython.getTitulo() + " ---");
        mostrarRanking(ofertaPython, yamil, esteban, cristian, atahualpa);

        // ================================================================
        // 7. POSTULACIONES
        // ================================================================

        System.out.println("\n--- Postulaciones ---");

        Application app1 = yamil.aplicarOferta(ofertaJava);
        Application app2 = yamil.aplicarOferta(ofertaPython);
        System.out.println(yamil.getNombre() + " aplico a: " + app1.getOferta().getTitulo());
        System.out.println(yamil.getNombre() + " aplico a: " + app2.getOferta().getTitulo());
        System.out.println("Activas de " + yamil.getNombre() + ": " + yamil.getSolicitudesActivas().size());

        Application app3 = esteban.aplicarOferta(ofertaJava);
        Application app4 = esteban.aplicarOferta(ofertaPython);
        System.out.println(esteban.getNombre() + " aplico a: " + app3.getOferta().getTitulo());
        System.out.println(esteban.getNombre() + " aplico a: " + app4.getOferta().getTitulo());

        Application app5 = cristian.aplicarOferta(ofertaTester);
        System.out.println(cristian.getNombre() + " aplico a: " + app5.getOferta().getTitulo());

        // Yamil cancela una solicitud
        yamil.cancelarSolicitud(app2);
        System.out.println("\n" + yamil.getNombre() + " cancelo: " + app2.getOferta().getTitulo());
        System.out.println("Activas de " + yamil.getNombre() + ": " + yamil.getSolicitudesActivas().size());

        // Empresa cubre el puesto con Yamil
        app1.asignarFechaContrato(LocalDate.now());
        System.out.println("\nPuesto cubierto! " + yamil.getNombre()
            + " contratado para: " + app1.getOferta().getTitulo());
        System.out.println("Estado:         " + app1.getEstado());
        System.out.println("Fecha contrato: " + app1.getFechaContrato());

        // Historial de Yamil
        System.out.println("\n--- Historial de " + yamil.getNombre() + " ---");
        for (Application a : yamil.getHistorialSolicitudes()) {
            System.out.println("  " + a.getOferta().getTitulo()
                + " | " + a.getEstado()
                + " | creada: " + a.getFechaCreacion());
        }

        // ================================================================
        // 8. PERFILES DEL GRUPO
        // ================================================================

        System.out.println("\n--- Perfiles del Grupo 25 ---");
        mostrarPerfil(yamil);
        mostrarPerfil(esteban);
        mostrarPerfil(cristian);
        mostrarPerfil(atahualpa);

        System.out.println("========================================");
        System.out.println("   Prueba completada con exito          ");
        System.out.println("========================================");
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    static void mostrarRanking(Oferta oferta, Postulante... postulantes) {
        System.out.println("  Titulo requerido: " + oferta.getTitulacionRequerida().getNombre());
        for (Postulante p : postulantes) {
            boolean cumple = p.cumpleRequisitos(oferta);
            int puntaje    = p.calcularPuntaje(oferta);
            System.out.println("  " + p.getNombre()
                + " -> cumple: " + cumple
                + (cumple ? " | puntaje: " + puntaje : ""));
        }
    }

    static void mostrarPerfil(Postulante p) {
        PerfilDatos d = p.getPerfilDatos();
        System.out.println("  Nombre:   " + d.getNombre());
        System.out.println("  CUIL:     " + d.getCuil());
        System.out.println("  Email:    " + d.getEmail());
        System.out.println("  Telefono: " + d.getTelefono());
        System.out.println("  Edad:     " + d.getEdad() + " anios");
        System.out.println("  Titulo:   " + p.getTitulacion().getNombre());
        System.out.println("  Skills:   " + p.getSkills());
        System.out.println("  Jornada:  " + p.getTipoJornada());
        System.out.println("  Viaje:    " + (p.isDisponibilidadViaje() ? "Si" : "No"));
        System.out.println("  Vehiculo: " + (p.getVehiculo() != null ? p.getVehiculo() : "No tiene"));
        System.out.println();
    }
}