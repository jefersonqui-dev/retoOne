import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class RetoOne {
    public static String RESET = "\033[0m";
    public static String RED = "\033[0;31m"; // Red
    public static String GREEN = "\033[0;32m"; // Green
    public static String YELLOW = "\033[0;36m"; // Yellow
    public static String BLUE = "\033[0;34m"; // Blue
    public static String CYAN = "\033[0;36m"; // Cyan
    public static String PURPLE = "\033[0;35m"; // White

    private static Map<String, Double> planetsAndDistance = new LinkedHashMap<>() {
        {
            put("Mercurio", 71.0);// Distancias en Millones de km
            put("Venus", 61.0);
            put("Marte", 54.0);
            put("Jupiter", 587.0);
            put("Saturno", 1345.0);
            put("Urano", 2725.0);
            put("Neptuno", 4351.0);
        }
    };

    private static Map<String, Double> shipSpeed = new LinkedHashMap<>() {
        {
            put("MillenniumFalcon", 100.0);
            put("USSEnterprise", 7.5);
            put("Nostromo", 8.0);
            put("Serenity", 5.0);
        }
    };
    private static Map<String, Double> consumoCombustible = new LinkedHashMap<>() {
        {
            put("MillenniumFalcon", 0.6); // consumo de unidades de combustible
            put("USSEnterprise", 0.3);
            put("Nostromo", 0.2);
            put("Serenity", 0.2);
        }
    };

    private static Map<String, String[]> shipDescriptions = Map.of(
            "MillenniumFalcon", new String[] {
                    "Caza Estelar",
                    "Una de las naves más rápidas y famosas de la galaxia, ideal para contrabandistas y rebeldes.",
                    "Alta velocidad, capacidad de carga y habilidades de evasión excepcionales."
            },
            "USSEnterprise", new String[] {
                    "Nave Autonoma de exploración",
                    "Equipada con sensores Avanzados para exploracion intergalactica",
                    "Alta velocidad y capacidad de gestion de recursos, ideal para descubrir lo desconocido"
            },
            "Nostromo", new String[] {
                    "Nave de Reparacion",
                    "Ideal para misiones prolongadas, realizar autodiagnosticos y reparaciones menores",
                    "Robustez y durabilidad en condiciones extremas"
            },
            "Serenity", new String[] {
                    "Nave de Tranporte Comercial",
                    "Realiza reparaciones mecanicas y electronic",
                    "Sistemas Avanzados de monitoreo y reparacion para misiones críticas"
            });

    private static String chosenPlanet;
    private static String name;
    private static double velocidad;
    private static double fuel;
    private static double oxygen;
    private static double distance;
    private static String chosenShip;
    private static double EstimatedTime;
    private static double oxigenReserve;
    private static double fuelReserve;
    static Scanner request = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        introduceMission();
        showMenu();

    }

    public static void showMenu() {

        System.out.println(BLUE + "**¿Estás listo para comenzar, " + name + "?" + RESET);
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
                    2. Elegir Nave
                    3. Ajustar Recursos
                    4. Iniciar Mision
                    5. Informacion De Las Naves
                    0. Salir
                        """);
            System.out.print("Elige una Opcion: ");

            if (request.hasNextInt()) {
                option = request.nextInt();
                switch (option) {
                    case 1:
                        chooseDestination();
                        break;
                    case 2:
                        if (chosenPlanet == null) {
                            System.out.println(PURPLE + "Debe elegir un destino antes de elegir una nave: " + RESET);
                        } else {
                            choseInterplanetaryShip();
                            calculateDistance(chosenPlanet);
                        }
                        break;
                    case 3:
                        ajustResources();
                        break;
                    case 4:
                        if (chosenPlanet == null && chosenShip == null) {
                            System.out.println(PURPLE
                                    + "Debe Elegir un destino y una nave antes de Iniciar la Simulacion:" + RESET);
                        } else {
                            //
                            // startMission(chosenPlanet, chosenShip);
                        }
                        break;
                    case 5:
                        fleetInformation();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println(PURPLE + "Opcion no valida, Intente de Nuevo" + RESET);
                        break;
                }
            } else {
                System.out.println(PURPLE + "Entrada Invalida.Porfavor Ingrese un numero.(0 - 4)" + RESET);
                request.next();// limpiar entrada no valida
            }
        } while (!exit);
        System.out.println("¡Gracias por usar el Simulador! ¡Hasta la próxima misión!");
    }

    private static void ajustResources() {
    }

    private static void choseInterplanetaryShip() {

        int option = 0;
        boolean inputValido = false;
        do {
            System.out.println("Lista de Naves Interplanetarias: ");
            int index = 1;
            for (String ship : shipSpeed.keySet()) {
                System.out.printf(YELLOW + "[%d] %s, Velocidad: %.1f Mkm/h\n" + RESET, index++, ship,
                        shipSpeed.get(ship));
            }
            System.out.println("Elija La Nave a la cual desearia Viajar: ");
            if (request.hasNextInt()) {
                option = request.nextInt();
                if (option > 0 && option <= shipSpeed.size()) {
                    inputValido = true;
                } else {
                    System.out.println(PURPLE + "Opcion fuera de rango, intente de nuevo (1 - 4 )" + RESET);
                }
            } else {
                System.out.println(PURPLE + "Entrada Invalida. porfavor ingrese un numero Valido" + RESET);
                request.next();
            }
        } while (!inputValido);
        chosenShip = new ArrayList<>(shipSpeed.keySet()).get(option - 1);

        System.out.println(YELLOW + "Usted ha elegido Viajar con: " + chosenShip + RESET);

    }

    private static void fleetInformation() {

        int option = 0;
        boolean inputValido = false;
        do {
            System.out.println(YELLOW + """
                    [1] MillenniumFalcon
                    [2] USSEnterprise
                    [3] Nostromo
                    [4] Serenity
                    """ + RESET);
            System.out.print("De que nave desea obtener Informacion (1-4): ");

            if (request.hasNextInt()) { // Verifica si la entrada es un entero
                option = request.nextInt();
                if (option >= 1 && option <= 4) {
                    inputValido = true; // La entrada es válida
                } else {
                    System.out.println("Opción fuera de rango. Intente de nuevo.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                request.next(); // Limpiar la entrada no válida
            }
        } while (!inputValido);
        String shipSelected = shipSelect(option);
        velocidad = shipSpeed.get(shipSelected);

        String[] description = shipDescriptions.getOrDefault(shipSelected,
                new String[] { "Desconocida", "No esp", "No esp" });
        var shipOvjetive = description[0];
        var mainFunction = description[1];
        var utility = description[2];
        switch (shipSelected) {
            case "MillenniumFalcon":
                printShipInformation(shipSelected, velocidad, shipOvjetive, mainFunction,
                        utility);
                break;
            case "USSEnterprise":
                printShipInformation(shipSelected, velocidad, shipOvjetive, mainFunction,
                        utility);
                break;
            case "Nostromo":
                printShipInformation(shipSelected, velocidad, shipOvjetive, mainFunction,
                        utility);
                break;
            case "Serenity":
                printShipInformation(shipSelected, velocidad, shipOvjetive, mainFunction,
                        utility);
                break;
            default:
                break;

        }
    }

    private static void printShipInformation(String tipoNave, double velocidad,
            String objetivo,
            String mainFunction, String utility) {
        DrawLine();
        System.out.printf(CYAN + """
                    Nave: <<<%s>>>
                    Objetivo: %s
                    Velocidad: %.2f km/h
                    Funcion Principal: %s
                    Utiliad: %s
                """ + RESET, tipoNave, objetivo, velocidad, mainFunction, utility);
        DrawLine();

    }

    private static void calculateDistance(String planeta) {
        System.out.println("Calculando Distancia y Tiempo Estimado de Viaje");
        mostrarBarraDeProgreso(3000);
        var distancia = planetsAndDistance.get(planeta);
        if (distancia != null) {
            velocidad = shipSpeed.get(chosenShip);
            System.out.printf(YELLOW +
                    "Distancia a %s:      %f Mkm\n" +
                    RESET, planeta, distancia);

            EstimatedTime = calculatedEstimatedTime(distancia, velocidad);
            System.out.printf(YELLOW + "Tiempo Estimado: %.2f Dias\n" + RESET, EstimatedTime);

        }
    }

    public static double calculatedEstimatedTime(double distance, double velocidad) {
        return distance / velocidad * 24;
    }

    private static void calculateResources() {
        System.out.println("!Calculando Recursos para tu Viaje: ");
        mostrarBarraDeProgreso(3000);
        var distancia = planetsAndDistance.get(chosenPlanet);

        System.out.printf(YELLOW +
                "Combustible Necesario:             %.1f galones\n" +
                "Oxigeno Necesario:                 %.1f Litros\n" +
                RESET, fuelAdjust(distancia), oxygen(distancia));

        System.out.print("¿Deseas llevar la cantidad recomendada? (S/N) : ");
        request.nextLine();
        var option = request.nextLine().toUpperCase();
        if (option.equals("S")) {
            fuelReserve = fuelAdjust(distancia);
            oxigenReserve = oxygen(distancia);
        } else {
            do {
                System.out.print("Ingresa la cantidad de combustible interplanetario que deseas llevar: ");
                fuelReserve = request.nextDouble();
                System.out.print("Ingresa la cantidad de oxigeno interplanetario que deseas llevar: ");
                oxigenReserve = request.nextDouble();
                if (fuelReserve <= 0 || oxigenReserve <= 0) {
                    System.out.println("Cantidad de recursos invalida. Intentelo de nuevo.");
                } else {
                    System.out.println("Recursos cargados exitosamente.");
                }
            } while (fuelReserve <= 0 || oxigenReserve <= 0);
        }
    }

    public static double oxygen(double distance) {

        var necesaryOxigen = distance * 2;
        return necesaryOxigen + (necesaryOxigen * 0.2);
    }

    public static double fuelAdjust(double distance) {

        var necesaryFuel = distance * 5;
        return necesaryFuel + (necesaryFuel * 0.2);
    }

    private static String shipSelect(int option) {

        return switch (option) {
            case 1 -> "MillenniumFalcon";
            case 2 -> "USSEnterprise";
            case 3 -> "Nostromo";
            case 4 -> "Serenity";
            default -> "Desconocida";
        };
    }

    public static void chooseDestination() {
        int option = 0;
        boolean inputValido = false;
        do {
            System.out.println("Lista de Planetas");
            int index = 1;
            for (String planeta : planetsAndDistance.keySet()) {
                System.out.printf(YELLOW + "[%d] %s%n", index++, planeta + RESET);
            }

            System.out.print(" Elija el planeta al cual desearia Viajar: ");
            if (request.hasNextInt()) {
                option = request.nextInt();
                if (option > 0 && option <= planetsAndDistance.size()) {
                    inputValido = true;
                } else {
                    System.out.println(PURPLE + "Opcion Fuera de rango, Intente de Nuevo." + RESET);
                }

            } else {
                System.out.println(PURPLE + "Entrada Invalida. por favor. Ingrese un Numero(1 - 7)." + RESET);
                request.next();
            }
        } while (!inputValido);

        chosenPlanet = new ArrayList<>(planetsAndDistance.keySet()).get(option - 1);
        System.out.println(YELLOW + "Usted ha elegido viajar a: " + chosenPlanet + RESET);

    }

    public static String introduceMission() {

        System.out.println(YELLOW + """
                                                       =====================================================
                                                       |              SIMULADOR INTERPLANETARIO            |
                                                       |               MISION DE EXPLORACION               |
                                                       =====================================================

                """ + RESET);
        System.out.print("Por favor dinos tu nombre: ");
        name = request.nextLine();
        System.out.println(YELLOW + "  !Bienvenid@! a tu Viaje Interplanetario" + RESET);

        return name;

    }

    public static void nave(double duracionAnimacion) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Error en la Simulación");
        }
        // Diseño de la nave en varias líneas
        String[] nave = {
                "        //-A-\\\\",
                "  ___---=======---___",
                "(=__\\   /.. ..\\   /__=)",
                "     ---\\__O__/---"
        };

        int anchoPantalla = 100; // Ancho máximo de la pantalla
        int speedSimulate = 2; // Aumento de la velocidad de movimiento para un efecto más amplio
        double tiempoInicio = System.currentTimeMillis();

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
                System.out.println(YELLOW + " ".repeat(Math.max(0, x)) + linea + RESET);
            }

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
                x += speedSimulate; // Movimiento hacia la derecha
                if (x >= anchoPantalla - nave[0].length()) {
                    moviendoDerecha = false; // Cambia dirección
                }
            } else {
                x -= speedSimulate; // Movimiento hacia la izquierda
                if (x <= 0) {
                    x = 0;
                    moviendoDerecha = true; // Cambia dirección
                }
            }
        }

    }

    public static void DrawLine() {
        System.out.println(CYAN +
                "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■" +
                RESET);
    }

    public static void mostrarBarraDeProgreso(double duracionTotal) {
        int anchoPantalla = 100; // Ancho de la barra de progreso
        long tiempoInicio = System.currentTimeMillis();

        while (System.currentTimeMillis() - tiempoInicio <= duracionTotal) {
            long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
            int progreso = (int) ((tiempoTranscurrido * anchoPantalla) / duracionTotal);

            // Crear la barra de progreso
            StringBuilder barra = new StringBuilder();
            for (int i = 0; i <= anchoPantalla; i++) {
                if (i <= progreso) {
                    barra.append(YELLOW + "■" + RESET); // barra//
                } else {
                    barra.append(" ");
                }
            }

            // Imprimir la barra de progreso con porcentaje
            int porcentaje = (progreso * 100) / anchoPantalla;
            System.out.print("\r[" + barra + "] " + porcentaje + "%");

            // Pausa para la animación
            try {
                Thread.sleep(100); // Pausa de 100 ms
            } catch (InterruptedException e) {
                System.out.println("\nEl hilo fue interrumpido.");
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                break;
            }
        }

        System.out.println("\n¡Calculo completado!");
    }

    public static void mostrarBarraDeProgresoConCuentaRegresiva(int segundos) {
        int anchoPantalla = 10; // Número total de caracteres en la barra de progreso
        System.out.println("El viaje interplanetario comenzará en:");

        long tiempoInicio = System.currentTimeMillis();

        while ((System.currentTimeMillis() - tiempoInicio) / 1000 < segundos) {
            long tiempoTranscurrido = (System.currentTimeMillis() - tiempoInicio) / 1000;
            int tiempoRestante = segundos - (int) tiempoTranscurrido;

            // Calcular el progreso de la barra
            int progreso = (int) ((double) tiempoRestante / segundos * anchoPantalla);

            // Construir la barra de progreso
            String barraDeProgreso = "■".repeat(progreso) + " ".repeat(anchoPantalla - progreso);

            // Imprimir la barra de progreso y el tiempo restante
            System.out.printf(YELLOW + "\r[%s] %d segundos" + RESET, barraDeProgreso, tiempoRestante);

            // Pausar para la animación
            try {
                Thread.sleep(1000); // Pausa de 1 segundo
            } catch (InterruptedException e) {
                System.out.println("\nInterrupción en la cuenta regresiva.");
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                break;
            }
        }

        System.out.println(YELLOW + "\n¡Despegue!" + RESET);
    }

}
