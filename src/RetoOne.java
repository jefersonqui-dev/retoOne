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

    private static Map<String, Long> planetsAndDistance = new LinkedHashMap<>() {
        {
            put("Mercurio", 77_000_000L);
            put("Venus", 61_1000L);
            put("Marte", 54_000_000L);
            put("Jupiter", 587_000_000L);
            put("Saturno", 1_345_000_000L);
            put("Urano", 2_725_000_000L);
            put("Neptuno", 4_351_400_000L);
        }
    };

    private static Map<String, Double> shipSpeed = new LinkedHashMap<>() {
        {
            put("MillenniumFalcon", 50_000.0);
            put("USSEnterprise", 75_000.0);
            put("Nostromo", 80_000.0);
            put("Serenity", 100_000.0);
        }
    };
    private static Map<String, Double> consumoCombustible = new LinkedHashMap<>() {
        {
            put("MillenniumFalcon", 50.0); // consumo en uniades por cada millon de kilometros
            put("USSEnterprise", 75.0);
            put("Nostromo", 80.0);
            put("Serenity", 100.0);
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
    private static String shipSelected;
    private static String name;
    private static double velocidad;
    private static double fuel;
    private static double oxygen;
    private static long distance;
    private static String chosenShip;
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
                            startMission(chosenPlanet);
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
                System.out.printf(YELLOW + "[%d] %s, Velocidad: %.1f km/h\n" + RESET, index++, ship,
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
        shipSelected = shipSelect(option);
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
        Long distancia = planetsAndDistance.get(planeta);
        if (distancia != null) {
            velocidad = shipSpeed.getOrDefault(shipSelected, 100000.0);
            System.out.printf(YELLOW +
                    "Distancia a %s:      %,d kilometros\n" +
                    RESET, planeta, distancia);

            var EstimatedTime = calculatedEstimatedTime(distancia, velocidad);
            var days = (int) EstimatedTime / 24;
            var remainingHours = EstimatedTime % 24;
            var remainingHoursInt = (int) remainingHours;
            System.out.printf(YELLOW +
                    "Tiempo Estimado:        %d dias y %d horas  %n"
                    + RESET, days, remainingHoursInt, remainingHoursInt);
        }
    }

    public static double calculatedEstimatedTime(long distance, double velocidad) {
        return distance / velocidad;
    }

    private static void startMission(String planeta) {
        // long duracionAnimacion, double fuel, double oxygen, double
        // velocidadNave, String destino, String remainingTime

        distance = planetsAndDistance.get(planeta);
        double estimatedTimeHours = calculatedEstimatedTime(distance, velocidad);
        double remainingTime = estimatedTimeHours;
        long duracionAnimacion = (long) (estimatedTimeHours * 1000);
        nave(duracionAnimacion, 100000, chosenPlanet, remainingTime);

        // Aqui se introducen eventos aleatorios y utiliza los funciones de las naves
        // alternativas
        // para completar la mision.
        // Los eventos aleatorios, deben ser random, ("""vertuto""")
    }

    private static void calculateResources() {
        System.out.println("!Calculando Recursos para tu Viaje: ");
        mostrarBarraDeProgreso(3000);
        initialMissionSetup();
    }

    public static double oxygen() {
        distance = planetsAndDistance.get(chosenPlanet);
        double oxygen = distance * 100;// unidades de oxigeno por cada millon de kilometros(100 )
        return oxygen;
    }

    public static double fuelAdjust() {
        distance = planetsAndDistance.get(chosenPlanet);
        double fuel = consumoCombustible.get(chosenShip) * distance;
        return fuel;

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

    public static void initialMissionSetup() {

        fuel = fuelAdjust();
        oxygen = oxygen();

        System.out.printf(YELLOW +
                "Combustible Necesario:             %.1f galones\n" +
                "Oxigeno Necesario:                 %.1f unidades\n" +
                RESET, velocidad, fuel, oxygen);
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

    public static void nave(long duracionAnimacion, double velocidadNave,
            String destino, double remainingTime) {
        calculateResources();
        mostrarBarraDeProgresoConCuentaRegresiva(10);
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

        // Dimensiones del espacio de animación
        int anchoPantalla = 100; // Ancho máximo de la pantalla
        int velocidad = 3; // Aumento de la velocidad de movimiento para un efecto más amplio
        long tiempoInicio = System.currentTimeMillis();

        // Animación
        int x = anchoPantalla / 2; // Comienza en el centro de la pantalla
        Random rand = new Random(); // Generador de números aleatorios
        boolean moviendoDerecha = rand.nextBoolean(); // Dirección aleatoria inicial
        double distanciaRecorrida = 0;
        while (System.currentTimeMillis() - tiempoInicio < duracionAnimacion) {
            // Limpia la consola (simulación)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Imprime cada línea del diseño con desplazamiento horizontal
            for (String linea : nave) {
                System.out.println(YELLOW + " ".repeat(x) + linea + RESET);
            } // Pausa para animación
            double porcentajeAlcanzado = (distanciaRecorrida / distance) * 100;
            System.out.printf(PURPLE + """

                    Destino: %s
                    Nave: %s
                    Velocidad: %.2f km/h
                    Combustible: %.2f galones
                    oxygen: %.2f unidades
                    Tiempo Restante:%.2f
                    Porcentaje de la Mision Completada: %.2f%%
                    """ + RESET, destino, chosenShip, velocidadNave, fuel, oxygen, remainingTime, porcentajeAlcanzado);
            // simulla consumo de combustible y oxygen
            fuel -= (distance / velocidadNave) * 0.1;
            oxygen -= (distance / velocidadNave) * 0.05;
            distanciaRecorrida += velocidadNave * 0.0833;

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
        System.out.println(YELLOW + "\n¡Viaje completado!" + RESET);
    }

    public static void DrawLine() {
        System.out.println(CYAN +
                "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■" +
                RESET);
    }

    public static void mostrarBarraDeProgreso(long duracionTotal) {
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

}
