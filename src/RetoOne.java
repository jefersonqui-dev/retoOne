import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class RetoOne {
    static List<String> planetas = List.of(
            "Mercurio", "Venus", "Marte",
            "Jupiter", "Saturno", "Urano",
            "Neptuno", "Venus");

    private static String planetaElegido;
    static Scanner request = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        introduccionMision();
        configuracionInicialDeLaMision();
        menuPrincipal();
    }

    public static void menuPrincipal() {
        String nombre = introduccionMision();
        System.out.println("**¿Estás listo para comenzar, " + nombre + "?");

        // Si, Despliega menu
        // No, Vuelve pronto, en una nueva mision.

        int option;

        boolean exit = false;
        do {
            System.out.println("""
                    1. Seleccionar Destino
                    2. Ajustar Recursos
                    3. Iniciar Mision
                    4. Informacion De Da Flota
                    0. Salir
                        """);
            System.out.print("Elige una Opcion: ");
            option = request.nextInt();
            switch (option) {
                case 1:
                    elegirDestino();
                    if (planetaElegido != null) {
                        calcularDistanciaTiempoDeViaje(planetaElegido);
                    }
                    break;
                case 2:
                    ajustarRecursos();
                    break;
                case 3:
                    iniciarMision();
                    break;
                case 4:
                    informacionDeLaFlota();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Opcion no valida, Intente de Nuevo");
                    break;
            }
        } while (!exit);

    }

    private static void informacionDeLaFlota() {
        int option;

        System.out.println("""
                1. Solaris - Explorer
                2. Orion - Supply
                3. Artemis - fixer
                4. Aegis - Protector
                    """);
        System.out.print("De que Nave deseas obtener Informacion: ");
        option = request.nextInt();
        switch (option) {
            case 1:
                System.out.println("""
                        - Nave principal: [Nave de Exploración]
                        - Velocidad inicial: [150,000 km/h]
                        - Realiza exploraciones rápidas y detecta rutas alternativas
                        - Transporte de tripulación, gestión de recursos, navegación a larga distancia.
                            """);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            default:
                break;
        }
    }

    private static void calcularDistanciaTiempoDeViaje(String planeta) {
        // Cuando se elije el destino, se mostrara en consola una barra de progreso,
        // inferiendo calculo de Distancia y tiempo estimado de Viaje
        // Tanto la velocidad como la distancia se toman globales y no cambian para el
        // calculo de la estimacion del tiempo, cambiaria para los eventos aleatorios en
        // donde cada nave tiene una velocidad predefinida
        // y la distancia como referencia seria la nave principal

        System.out.println("Calculando Distancia y Tiempo Estimado de Viaje al planeta  " + planeta);
        switch (planeta) {
            case "Mercurio":
                System.out.println("Distancia a Mercurio: 2222222");
                break;
            case "Venus":

                break;
            case "Marte":

                break;
            case "Jupiter":

                break;

            case "Saturno":

                break;

            case "Urano":

                break;
            default:
                break;
        }
    }

    private static void iniciarMision() {
        // Recibe parametros como, planeta destino, distancia, recursos y las
        // configuraciones
        // establecidas
        // Aqui se introducen eventos aleatorios y utiliza los funciones de las naves
        // alternativas
        // para completar la mision.
        // Los eventos aleatorios, deben ser random, ("""vertuto""")
    }

    private static void ajustarRecursos() {

    }

    public static void elegirDestino() {
        int option;

        System.out.println("Lista de Planetas");

        for (int i = 0; i < planetas.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, planetas.get(i));
        }

        System.out.print(" Elija el planeta al cual desearia Viajar: ");
        option = request.nextInt();
        if (option >= 1 && option <= planetas.size()) {
            planetaElegido = planetas.get(option - 1);
            System.out.println("Usted ha elegido viaja a: " + planetaElegido);
        } else {
            System.out.println("Opcion No valida. Por favor Intente de Nuevo");
            planetaElegido = null;
        }

    }

    public static void configuracionInicialDeLaMision() {
        // La nave se ha configurado con los siguientes parametros, si desea ajustarlos
        // Se debe Mostrar lo siguiente en consola, Luego de la confirmacion(S/N
        // - Nave principal: [Nave de Exploración]
        // - Velocidad: [100,000 km/h]
        // - Combustible: [80%]
        // - Oxígeno: [100%]
        // Para ser mas interactivo se debe ajustar la variable Combustible y oxigeno
        // con valores Random, esto permitira
        // al usuario hacer posteriores ajustes desde el menu principal
        // N: La nave no esta totalmente preparada, por favor realiza los cambios,
        // (S/N(Salir)(Continuar dependera de las otras naves
    }

    public static String introduccionMision() {
        System.out.println(System.getProperty("file.encoding"));
        System.out.print("Tu nombre: ");
        String nombre = request.nextLine();
        System.out.println("""
                                =====================================================
                                |              SIMULADOR INTERPLANETARIO            |
                                |               MISION DE EXPLORACION               |
                                =====================================================
                ¡Bienvenido, ! """
                + nombre +
                """

                        Tu flota incluye:
                        * Nave de Exploración: Tu vehículo principal para cruzar el espacio.
                        * Nave de Recursos: Siempre lista para abastecer combustible y oxígeno.
                        * Nave de Reparación: Vital para superar fallas técnicas en el camino.
                        * Nave de Defensa: Preparada para enfrentar cualquier amenaza externa.

                        **Tu misión principal**
                        Lidera a tu equipo con precisión, toma decisiones estratégicas y llega al planeta elegido.
                        El futuro de la humanidad podría depender de este viaje.
                                                                """);
        return nombre;

    }

}
