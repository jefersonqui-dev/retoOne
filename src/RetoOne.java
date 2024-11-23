import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class RetoOne {

    private static Map<String, Long> planetsAndDistance = Map.of(
            "Mercurio", 77_000L,
            "Venus", 41_000L,
            "Marte", 225_000L,
            "Jupiter", 628_000_000L,
            "Saturno", 1_275_000_000L,
            "Urano", 2_725_000_000L,
            "Neptuno", 4_351_000_000L);

    private static Map<String, Double> shipSpeed = Map.of(
            "SolarisExplorer", 50000.0,
            "OrionSupply", 75_000.0,
            "Aegis", 100_000.0);

    private static String planetaElegido;
    private static String shipSelected;
    private static String nombre;
    static Scanner request = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        introduccionMision();
        configuracionInicialDeLaMision();
        menuPrincipal();

    }

    public static void menuPrincipal() {

        System.out.println("**¿Estás listo para comenzar, " + nombre + "?");
        String decide;
        boolean ready = false;
        // Solicitar si esta preparado para comenzar
        do {
            System.out.println("(S/N)");
            decide = request.nextLine();
            if (decide.equalsIgnoreCase("S")) {
                ready = true;
            } else if (decide.equalsIgnoreCase("N")) {
                System.out.println("Vuelve Pronto, En una Nueva Mision");
                return;
            } else {
                System.out.println("Opcion No valida, Intenta de Nuevo");
            }
        } while (!ready);

        int option;

        boolean exit = false;
        do {
            System.out.println("""
                    1. Seleccionar Destino
                    2. Ajustar Recursos
                    3. Iniciar Mision
                    4. Informacion De La Flota
                    0. Salir
                        """);
            System.out.print("Elige una Opcion: ");
            option = request.nextInt();
            switch (option) {
                case 1:
                    elegirDestino();
                    if (planetaElegido != null) {
                        calcularDistancia(planetaElegido);
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
                1. Solaris - Explorer - Nave de Exploracion
                2. Orion - Supply - Nave de Recursos
                3. Artemis - fixer  -
                4. Aegis - Protector - Nave de defensa
                    """);

        System.out.print("De que Nave deseas obtener Informacion: ");
        option = request.nextInt();
        shipSelected = shipSelect(option);
        var velocidad = shipSpeed.get(shipSelected);

        switch (shipSelected) {
            case "SolarisExplorer":
                System.out.printf("- Nave: %s%n- Velocidad: %f%n", shipSelected, velocidad);
                System.out.println("""
                        - Realiza exploraciones rápidas y detecta rutas alternativas
                        - Transporte de tripulación, gestión de recursos, navegación a larga distancia.
                            """);
                break;
            case "OrionSupply":
                System.out.printf("- Nave: %s%n- Velocidad: %f%n", shipSelected, velocidad);
                break;
            case "Aegis":
                System.out.printf("- Nave: %s%n- Velocidad: %f%n", shipSelected, velocidad);
                break;
            default:
                break;
        }
    }

    private static void calcularDistancia(String planeta) {
        // Cuando se elije el destino, se mostrara en consola una barra de progreso,
        // inferiendo calculo de Distancia y tiempo estimado de Viaje
        // Tanto la velocidad como la distancia se toman globales y no cambian para el
        // calculo de la estimacion del tiempo, cambiaria para los eventos aleatorios en
        // donde cada nave tiene una velocidad predefinida
        // y la distancia como referencia seria la nave principal
        Long distance = planetsAndDistance.get(planeta);
        if (distance != null) {
            // ProgressBar
            System.out.printf("Distancia a %s: %,d Kilometros. %n", planeta, distance);
            // ProgressBar
            var EstimatedTime = calculatedEstimatedTime(distance);
            System.out.printf("Tiempo Estimado de Viaje: %.2f horas \n", EstimatedTime);

        }
    }

    public static double calculatedEstimatedTime(long distance) {

        double velocidad = 100_000.0;
        return distance / velocidad;
    }

    private static void iniciarMision() {
        nave(15000); // nave(15000);// recibe parametros -> tiempo estimado, distancia, velocidad,
        // combustible y oxigeno, eventos

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

    private static String shipSelect(int option) {
        if (option >= 1 && option <= shipSpeed.size()) {
            shipSelected = new ArrayList<>(shipSpeed.keySet()).get(option - 1);

        } else {
            System.out.println("Opcion No valida, Intente de Nuevo");
        }
        return shipSelected;
    }

    public static void elegirDestino() {
        int option;

        System.out.println("Lista de Planetas");
        int index = 1;
        for (String planeta : planetsAndDistance.keySet()) {
            System.out.printf("[%d] %s%n", index++, planeta);
        }

        System.out.print(" Elija el planeta al cual desearia Viajar: ");
        option = request.nextInt();
        if (option >= 1 && option <= planetsAndDistance.size()) {
            planetaElegido = new ArrayList<>(planetsAndDistance.keySet()).get(option - 1);
            System.out.println("Usted ha elegido viajar a: " + planetaElegido);
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

        System.out.print("Tu nombre: ");
        nombre = request.nextLine();
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

                                                                """);
        return nombre;

    }

    public static void nave(long duracionAnimacion) {

        // Diseño de la nave en varias líneas
        String[] nave = {
                "        //-A-\\\\",
                "  ___---=======---___",
                "(=__\\   /.. ..\\   /__=)",
                "     ---\\__O__/---"
        };

        // Dimensiones del espacio de animación
        int anchoPantalla = 100; // Ancho máximo de la pantalla
        int velocidad = 3; // Aumento de la velocidad de movimiento para un efecto más amplio
        long tiempoInicio = System.currentTimeMillis();

        // Animación
        int x = anchoPantalla / 2; // Comienza en el centro de la pantalla
        Random rand = new Random(); // Generador de números aleatorios
        boolean moviendoDerecha = rand.nextBoolean(); // Dirección aleatoria inicial
        while (System.currentTimeMillis() - tiempoInicio < duracionAnimacion) {
            // Limpia la consola (simulación)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Imprime cada línea del diseño con desplazamiento horizontal
            for (String linea : nave) {
                System.out.println(" ".repeat(x) + linea);
            } // Pausa para animación

            System.out.println("""


                    Ingresando a Puente Einstein-Rousen...
                    Velocidad: 100.000km/h
                    Combustible: 60%
                    Oxigeno:70%"
                    Tiempo Restante: 5 dias
                    """);

            try {
                Thread.sleep(300); // 40 ms
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido.");
            }

            // Mueve la nave de manera aleatoria (con rebote)
            if (rand.nextBoolean()) {
                // Cambiar aleatoriamente la dirección
                moviendoDerecha = !moviendoDerecha;
            }

            if (moviendoDerecha) {
                x += velocidad; // Movimiento hacia la derecha
                if (x >= anchoPantalla - nave[0].length()) {
                    moviendoDerecha = false; // Cambia dirección
                }
            } else {
                x -= velocidad; // Movimiento hacia la izquierda
                if (x <= 0) {
                    moviendoDerecha = true; // Cambia dirección
                }
            }
        }

        // Mensaje al finalizar la animación
        System.out.println("\n¡Viaje completado!");
    }

}
