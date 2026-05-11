package human.ware;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        System.out.println("=== HUMAN-Ware -- Prueba Division 2 ===\n");

        // ── 1. Catalogos (provistos por Division 1) ────────────────────────
        Skill ingles  = new Skill(1, "Ingles",  "Idioma ingles");
        Skill java    = new Skill(2, "Java",    "Programacion Java");
        Skill python  = new Skill(3, "Python",  "Programacion Python");

        Degree ingSistemas = new Degree(1, "Ingeniero en Sistemas", "");
        Degree tecnico     = new Degree(2, "Técnico en PC",         "");

        // ── 2. Crear usuario y postulante ──────────────────────────────────
        Usuario u1 = new Usuario("jperez", "pass123", "jperez@mail.com");

        PerfilDatos perfil1 = new PerfilDatos
        ("20-12345678-9", "M", LocalDate.of(1998, 5, 14), "Juan Perez", "jperez@mail.com", "351-1234567");

        Postulante p1 = new Postulante
        (900_000.0, TipoJornada.AMBAS, true, "Auto", ingSistemas, perfil1, u1);

        // Agregar skills
        p1.agregarSkill(new PostulanteSkill(ingles, 4));
        p1.agregarSkill(new PostulanteSkill(java,   5));
        p1.agregarSkill(new PostulanteSkill(python,  3));

        System.out.println("Postulante creado: " + p1);
        System.out.println("Skills: " + p1.getSkills());
        System.out.println("Edad: " + p1.getPerfilDatos().getEdad() + " anios\n");

        // ── 3. Segundo postulante para comparar ranking ────────────────────
        Usuario u2 = new Usuario("mgarcia", "pass456", "mgarcia@mail.com");

        PerfilDatos perfil2 = new PerfilDatos
        ("27-87654321-3", "F", LocalDate.of(1995, 8, 22), "Maria Garcia", "mgarcia@mail.com", "351-7654321");

        Postulante p2 = new Postulante
        (850_000.0, TipoJornada.COMPLETA, false, null, ingSistemas, perfil2, u2);

        p2.agregarSkill(new PostulanteSkill(ingles, 5));
        p2.agregarSkill(new PostulanteSkill(java,   4));

        System.out.println("Postulante 2 creado: " + p2 + "\n");

        // ── 4. Oferta laboral (stub, provista por División 1) ──────────────
        List<OfertaSkill> reqSkills = List.of(
            new OfertaSkill(ingles, 3),   // mínimo nivel 3 en Inglés
            new OfertaSkill(java,   4)    // mínimo nivel 4 en Java
        );

        Oferta oferta1 = new Oferta ("OF-001", "Desarrollador Java Senior",ingSistemas, reqSkills);

        // ── 5. Verificar requisitos ────────────────────────────────────────
        System.out.println("--- Verificacion de requisitos ---");
        System.out.println(p1.getNombre() + " cumple requisitos: " + p1.cumpleRequisitos(oferta1));
        System.out.println(p2.getNombre() + " cumple requisitos: " + p2.cumpleRequisitos(oferta1));

        // ── 6. Calcular puntaje ────────────────────────────────────────────
        System.out.println("\n--- Puntajes para OF-001 ---");
        int puntajeP1 = p1.calcularPuntaje(oferta1);
        int puntajeP2 = p2.calcularPuntaje(oferta1);
        System.out.println(p1.getNombre() + ": " + puntajeP1 + " pts  (Ingles=4 + Java=5)");
        System.out.println(p2.getNombre() + ": " + puntajeP2 + " pts  (Ingles=5 + Java=4)");

        String ganador = puntajeP1 >= puntajeP2
            ? p1.getNombre() : p2.getNombre();
        System.out.println("Mejor candidato: " + ganador + "\n");

        // ── 7. Postulaciones ───────────────────────────────────────────────
        System.out.println("--- Postulaciones ---");
        Application app1 = p1.aplicarOferta(oferta1);
        System.out.println("Solicitud creada: " + app1);
        System.out.println("Solicitudes activas de " + p1.getNombre() + ": "
                           + p1.getSolicitudesActivas().size());

        // Cancelar solicitud
        p1.cancelarSolicitud(app1);
        System.out.println("Solicitud cancelada. Activas: "
                           + p1.getSolicitudesActivas().size());

        // Re-postular
        Application app2 = p1.aplicarOferta(oferta1);
        System.out.println("Nueva solicitud: " + app2);

        // Simular contratación
        app2.asignarFechaContrato(LocalDate.now());
        System.out.println("Estado tras cobertura: " + app2.getEstado());
        System.out.println("Fecha contrato: " + app2.getFechaContrato());

        System.out.println("\n=== Prueba completada con Exito ===");
        
    }
    
}