import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class RetoOne {
    public static String RESET = "\033[0m";
    public static String RED = "\033[0;31m";
    public static String GREEN = "\033[0;32m";
    public static String YELLOW = "\033[0;36m";
    public static String BLUE = "\033[0;34m";
    public static String CYAN = "\033[0;36m";
    public static String PURPLE = "\033[0;35m";

    // Mapas Clave - Valor

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
            put("USSEnterprise", 70.5);
            put("Nostromo", 80.0);
            put("Serenity", 50.0);
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
    // variables globales
    private static String chosenPlanet;
    private static String name;
    private static double speed;
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

        // Validacion: Solicitar si esta preparado para comenzar
        do {
            System.out.print("(S/N): ");
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

        // Muestra el Menu Principal
        int option;
        boolean exit = false;
        do {
            System.out.println(YELLOW + """
                    1. Seleccionar Destino
                    2. Elegir Nave
                    3. Iniciar Simulación
                    4. Informacion De Las Naves
                    0. Salir
                        """ + RESET);
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
                        if (chosenPlanet == null && chosenShip == null) {
                            System.out.println(PURPLE
                                    + "Debe Elegir un destino y una nave antes de Iniciar la Simulacion:" + RESET);
                        } else {

                            spaceMissionSimulation();
                        }
                        break;
                    case 4:
                        fleetInformation();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println(PURPLE + "Fuera de rango ,ingrese una opcion entre (0 - 4)" + RESET);
                        break;
                }
            } else {
                System.out.println(PURPLE + "Entrada Invalida. Porfavor Ingrese un numero.(0 - 4)" + RESET);
                request.next();// limpiar entrada no valida
            }
        } while (!exit);
        System.out.println("¡Gracias por usar el Simulador! ¡Hasta la próxima misión!");
    }

    // Despliega la lista de planetas

    public static void chooseDestination() {
        int option = 0;
        boolean inputValido = false;
        do {
            System.out.println("Lista de Planetas");
            int index = 1;
            // Itera sobre las claves del mapa plantsAndDistance
            for (String planeta : planetsAndDistance.keySet()) {
                // Se imprimen en consola
                System.out.printf(YELLOW + "[%d] %s%n", index++, planeta + RESET);
            }
            // Entrada al usuario
            System.out.print(" Elija el planeta al cual desearia Viajar: ");
            if (request.hasNextInt()) {
                option = request.nextInt();
                if (option > 0 && option <= planetsAndDistance.size()) {
                    inputValido = true;
                } else {
                    System.out.println(RED + "Opcion Fuera de rango, Intente de Nuevo. (1 - 7)" + RESET);
                }

            } else {
                System.out.println(RED + "Entrada Invalida. por favor. Ingrese un Número(1 - 7)." + RESET);
                request.next();
            }
        } while (!inputValido);

        chosenPlanet = new ArrayList<>(planetsAndDistance.keySet()).get(option - 1);
        System.out.println(YELLOW + "Usted ha elegido viajar a: " + chosenPlanet + RESET);

    }

    // Despliega la lista de Naves
    private static void choseInterplanetaryShip() {

        int option = 0;
        boolean inputValido = false;

        do {
            System.out.println("Lista de Naves Interplanetarias: ");
            int index = 1;

            // Iterera sobre las claves del mapa e imprime su indice, Nave y Velocidad
            for (String ship : shipSpeed.keySet()) {
                System.out.printf(YELLOW + "[%d] %s, Velocidad: %.1f Mkm/h\n" + RESET, index++, ship,
                        shipSpeed.get(ship));
            }

            // Entrada al usuario
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
        // entrada valida: almacena el planeta seleccionado en chosenShip
        chosenShip = new ArrayList<>(shipSpeed.keySet()).get(option - 1);
        System.out.println(YELLOW + "Usted ha elegido Viajar con: " + chosenShip + RESET);

    }

    // Proporciona Informacion de las Naves
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
        speed = shipSpeed.get(shipSelected);

        String[] description = shipDescriptions.getOrDefault(shipSelected,
                new String[] { "Desconocida", "No esp", "No esp" });
        var shipOvjetive = description[0];
        var mainFunction = description[1];
        var utility = description[2];
        switch (shipSelected) {
            case "MillenniumFalcon":
                printShipInformation(shipSelected, speed, shipOvjetive, mainFunction,
                        utility);
                break;
            case "USSEnterprise":
                printShipInformation(shipSelected, speed, shipOvjetive, mainFunction,
                        utility);
                break;
            case "Nostromo":
                printShipInformation(shipSelected, speed, shipOvjetive, mainFunction,
                        utility);
                break;
            case "Serenity":
                printShipInformation(shipSelected, speed, shipOvjetive, mainFunction,
                        utility);
                break;
            default:
                break;

        }
    }

    private static void printShipInformation(String shipType, double speed,
            String objetive,
            String mainFunction, String utility) {
        DrawLine();
        System.out.printf(CYAN + """
                    Nave: <<<%s>>>
                    Objetivo: %s
                    Velocidad: %.2f km/h
                    Funcion Principal: %s
                    Utiliad: %s
                """ + RESET, shipType, objetive, speed, mainFunction, utility);
        DrawLine();

    }

    private static void calculateDistance(String planet) {
        System.out.println("Calculando Distancia y Tiempo Estimado de Viaje");
        showProgressBar(3000);
        var distance = planetsAndDistance.get(planet);
        if (distance != null) {
            speed = shipSpeed.get(chosenShip);
            System.out.printf(PURPLE +
                    "Distance a %s:      %f Mkm\n" +
                    RESET, planet, distance);

            EstimatedTime = calculatedEstimatedTime(distance, speed);
            System.out.printf(PURPLE + "Tiempo Estimado: %.2f Dias\n" + RESET, EstimatedTime);

        }
    }

    public static double calculatedEstimatedTime(double distance, double speed) {
        return distance / (speed * 24); // devuelve el tiempo en dias
    }

    private static void calculateResources() {
        System.out.println("!Calculando Recursos para tu Viaje: ");
        showProgressBar(5000);
        var distancia = planetsAndDistance.get(chosenPlanet);

        System.out.printf(YELLOW +
                "Combustible Necesario:             %.1f galones\n" +
                "Oxigeno Necesario:                 %.1f Litros\n" +
                RESET, fuelAdjust(distancia), oxygen(distancia));

        System.out.print("¿Deseas llevar la cantidad recomendada? (S/N) (N: Editar ): ");
        request.nextLine();
        var option = request.nextLine().toUpperCase();
        if (option.equals("S")) {
            fuelReserve = fuelAdjust(distancia);
            oxigenReserve = oxygen(distancia);
        } else {
            do {
                System.out.print(
                        PURPLE + "Ingresa la cantidad de combustible interplanetario que deseas llevar: " + RESET);
                fuelReserve = request.nextDouble();
                System.out.print(PURPLE + "Ingresa la cantidad de oxigeno interplanetario que deseas llevar: " + RESET);
                oxigenReserve = request.nextDouble();
                if (fuelReserve <= 0 || oxigenReserve <= 0) {
                    System.out.println("Cantidad de recursos invalida. Intentelo de nuevo.");
                } else {
                    System.out.println(YELLOW + "Recursos cargados exitosamente." + RESET);
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

    private static void spaceMissionSimulation() {
        var distancia = planetsAndDistance.get(chosenPlanet);
        if (distancia == null) {
            System.err.println("El planeta elegido no está en la lista.");
            return;
        }
        System.out.println("Iniciando Simulacion de Viaje...");
        calculateResources();
        showProgressBarWithCountdown(5);

        try {
            Thread.sleep(1000);

            // System.out.printf(PURPLE + """
            // // Se Imprime
            // Destino: %s
            // Nave: %s
            // Velocidad: %.2f km/h
            // Distancia: %.2f km
            // Combustible: %.2f galones
            // oxygen: %.2f unidades
            // Tiempo Estimado: %.2f dias
            // """ + RESET, chosenPlanet, chosenShip, speed, distancia, fuelReserve,
            // oxigenReserve,
            // EstimatedTime);

            // variables para los calculos del viaje:
            var kilometersPerPercent = distancia / 100;
            var fuelPerPercent = kilometersPerPercent * 5;
            var oxygenPerPercent = kilometersPerPercent * 2;
            var timePerPercent = EstimatedTime / 100;
            Random rand = new Random();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Simulacion del Viaje
            for (int i = 0; i <= 100; i++) {

                if (i == 0) {
                    System.out.println("Inicio de la mision");
                } else if (i == 50) {
                    System.out.println("Le resta el 50% de distancia para llegar a: " + chosenPlanet);
                } else if (i == 100) {
                    DrawLine();
                    System.out.printf("""
                            Progreso: %d%%
                            Haz llegado a %s
                            Combustible restante: %.2f galones
                            Oxigeno restante: %.2f litros
                            """, i, chosenPlanet, fuelReserve, oxigenReserve);
                    DrawLine();
                    break;
                }

                // calculos del Viaje
                var travelKm = kilometersPerPercent * i;

                if (rand.nextInt(20) == rand.nextInt(20)) {
                    randomEvents(rand.nextInt(3) + 1);
                    Thread.sleep(3000);
                    System.out.print("\r                                   ");
                }
                System.out.print("\033[H\033[2J");
                System.out.flush();
                int barLength = 80;
                System.out.printf(PURPLE + """
                        Progreso:  %s
                        Destino: %s
                        Nave: %s
                        Distancia recorrida: %.2f millones de kilómetros
                        Tiempo restante para el destino: %.2f días
                        Combustible restante: %.2f galones
                        Oxígeno restante: %.2f litros
                         """ + RESET,
                        generateProgressBar(i, barLength), chosenPlanet, chosenShip,
                        travelKm, // Distancia recorrida en millones de kilómetros
                        EstimatedTime, // Tiempo estimado para llegar a destino
                        fuelReserve, // Combustible restante
                        oxigenReserve // Oxígeno restante

                );

                EstimatedTime -= timePerPercent;
                fuelReserve -= fuelPerPercent;
                oxigenReserve -= oxygenPerPercent;

                if (fuelReserve <= 0) {
                    System.out.println(RED + chosenShip + " se ha quedado sin combustible, Mision fallida." + RESET);
                    break;
                } else if (oxigenReserve <= 0) {
                    System.out.println(RED + chosenShip + " se ha quedado sin oxigeno, Mision fallida." + RESET);
                    break;
                }
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) { // Manejo de la excepcion si el hilo es interrumpido
            System.err.println("El hilo fue interrumpido: " + e.getMessage());

        }
    }

    public static String generateProgressBar(int percentage, int length) {
        int filledLength = (int) Math.round((percentage / 100.0) * length);
        StringBuilder bar = new StringBuilder();
        for (int j = 0; j < filledLength; j++) {
            bar.append("■");
        }
        for (int j = filledLength; j < length; j++) {
            bar.append(" ");
        }
        return "[" + bar + "] " + percentage + "%";
    }

    // Selecciona los eventos aleatorios, al recibir el parametro num
    private static void randomEvents(int num) {
        switch (num) {
            case 1:
                encounterAlienTechnology();
                break;
            case 2:
                handleSystemFailures();
                break;
            case 3:
                handleCollisionWithDebris();
                break;
            default:
                System.err.println("Sin evento aleatorio");
        }
    }

    public static void encounterAlienTechnology() {

        double oxygen = oxigenReserve * 0.04; // Toma el 4% y lo va a dismunuir de la cantidad actual
        double fuel = fuelReserve * 0.04;
        var startEvent = true;
        int option;

        System.out.printf(YELLOW + """
                         ENCUENTRO CON TECNOLOGÍA ALIENÍGENA DESCONOCIDA
                 =================================================================
                La nave ha detectado una extraña anomalía en el espacio,
                posiblemente originada por una presencia alienígena.
                El sistema alerta sobre una gran amenaza.
                 ¿Qué deseas hacer?
                 -----------------------------------------------------------------
                 1.  Preparar la Nave para un Encuentro Hostil: !Usar Oxigeno!
                     - Consumirá: %.2f litros de oxígeno
                     - Oxígeno restante: %.2f litros
                 -----------------------------------------------------------------
                 2.  Activar Sistemas de Defensa Avanzados: ¡Usar Combustible!
                     - Consumirá: %.2f Galones de Combustible
                     - Combustible restante: %.2f galones
                 =================================================================
                 """ + RESET,
                oxygen, oxigenReserve, fuel, fuelReserve);

        do {
            System.out.print("Ingresa el numero de la accion que deseas realizar: ");
            option = request.nextInt();
            request.nextLine();
            switch (option) {
                case 1:
                    System.out.println(YELLOW + "Preparando " + chosenShip + " Para encuentro Hostil." + RESET);
                    oxigenReserve -= oxygen; // resta el porcentaje designado y actualiza el valor de la variable
                    System.out.printf(PURPLE + "Te restan %.2f Litros de Oxigeno." + RESET, oxigenReserve);
                    startEvent = false;
                    break;
                case 2:
                    System.out.println(
                            YELLOW + "Se han activado los sistemas avanzados de defensa de " + chosenShip + RESET);
                    fuelReserve -= fuel;
                    System.out.printf(PURPLE + "Te restan %.2f Galones de Combustible." + RESET, fuelReserve);
                    startEvent = false;
                    break;
                default:
                    System.out.println("Opcion invalida. Intentelo de nuevo.");
                    break;
            }

        } while (startEvent);

    }

    public static void handleSystemFailures() {

        double oxygen = oxigenReserve * 0.08; // Toma el 8% de las reservas actuales
        double fuel = fuelReserve * 0.08;
        var startEvent = true;
        int option;

        // Depende de la ocion que tome, sera lo que se consumira
        System.out.printf(YELLOW + """
                            FALLOS EN LOS SISTEMAS DE SOPORTE VITAL
                =================================================================
                Se ha detectado un mal funcionamiento en sistemas y otras fallas .
                ¿Qué deseas hacer?
                -----------------------------------------------------------------
                1.   Desactivar los Mecanismos  y activar los mecanismos de respaldo: ¡Usar Oxigeno!
                    - Consumirá: %.2f litros de oxígeno
                    - Oxígeno restante: %.2f litros
                -----------------------------------------------------------------
                2.   Utilizar los Módulos Inteligentes para suplir los sistemas: ¡Usar Combustible!
                    - Consumirá: %.2f Galones de Combustible
                    - Combustible restante: %.2f galones
                =================================================================
                """ + RESET,
                oxygen, oxigenReserve, fuel, fuelReserve);

        do {
            System.out.print("Ingresa el numero de la accion que deseas realizar: ");
            option = request.nextInt();

            request.nextLine();
            switch (option) {
                case 1:
                    System.out.println(YELLOW + "Mecanismos de " + chosenShip + " de respaldo Activados." + RESET);
                    oxigenReserve -= oxygen;
                    System.out.printf(PURPLE + "Te restan %.2f Litros de Oxigeno." + RESET, oxigenReserve);
                    startEvent = false;
                    break;
                case 2:
                    System.out.println(YELLOW + "Modulo inteligente de " + chosenShip + " Activado." + RESET);
                    fuelReserve -= fuel;
                    System.out.printf(PURPLE + "Te restan %.2f Galones de Combustible." + RESET, fuelReserve);
                    startEvent = false;
                    break;
                default:
                    System.out.println("Opcion invalida. Intentelo de nuevo.");
                    break;
            }

        } while (startEvent);
    }

    public static void handleCollisionWithDebris() {

        double oxygen = oxigenReserve * 0.1; // Toma el 30% y lo va a dismunuir de la cantidad actual
        double fuel = fuelReserve * 0.1;
        var startEvent = true;
        int option;

        System.out.printf(
                YELLOW + """
                                          PASANDO ZONA DE ASTEROIDES
                        =================================================================
                        La nave ha recibido un impacto con un objeto, lo cual ha perforado el casco.
                        Se requieren reparaciones inmediatas. ¿Qué deseas hacer?
                        -----------------------------------------------------------------
                        1.   Detener el trayecto, revisar el impacto y repararlo: ¡Usar Oxigeno!
                            - Consumirá: %.2f litros de oxígeno
                            - Oxígeno restante: %.2f litros
                        -----------------------------------------------------------------
                        2.   Desprender el módulo afectado de la nave , ¡Usar Combustible!
                            - Consumira: %.2f Galones de Combustible
                            - Combustible restante: %.2f galones
                        =================================================================
                        """
                        + RESET,
                oxygen, oxigenReserve, fuel, fuelReserve);

        do {
            System.out.print("Ingresa el numero de la accion que deseas realizar: ");
            option = request.nextInt();

            request.nextLine();
            switch (option) {
                case 1:
                    System.out.println(YELLOW + "Se realizo la reparación del area afectada de " + chosenShip + RESET);
                    oxigenReserve -= oxygen;
                    System.out.printf(PURPLE + "Te restan %.2f Litros de Oxigeno." + RESET, oxigenReserve);
                    startEvent = false;
                    break;
                case 2:
                    System.out.println(YELLOW + "Se ha retirado modulo afectado de " + chosenShip + RESET);
                    fuelReserve -= fuel;
                    System.out.printf(PURPLE + "Te restan %.2f Galones de Combustible." + RESET, fuelReserve);
                    startEvent = false;
                    break;
                default:
                    System.out.println("Opcion invalida. Intentelo de nuevo.");
                    break;
            }

        } while (startEvent);

    }

    public static String introduceMission() {

        System.out.println(YELLOW + """
                =====================================================
                |              SIMULADOR INTERPLANETARIO            |
                |               MISION DE EXPLORACION               |
                =====================================================""" + RESET);
        System.out.print("Por favor dinos tu nombre: ");
        name = request.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(YELLOW + "  !Bienvenid@! a tu Viaje Interplanetario" + RESET);

        return name;

    }

    public static void DrawLine() {
        System.out.println(CYAN +
                "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■" +
                RESET);
    }

    public static void showProgressBar(double duracionTotal) {
        int screenWidth = 100; // Ancho de la barra de progreso
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime <= duracionTotal) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int progress = (int) ((elapsedTime * screenWidth) / duracionTotal);

            // Crear la barra de progress
            StringBuilder bar = new StringBuilder();
            for (int i = 0; i <= screenWidth; i++) {
                if (i <= progress) {
                    bar.append(YELLOW + "■" + RESET); // bar//
                } else {
                    bar.append(" ");
                }
            }

            // Imprimir la bar de progress con porcentaje
            int percentage = (progress * 100) / screenWidth;
            System.out.print("\r[" + bar + "] " + percentage + "%");

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

    public static void showProgressBarWithCountdown(int segundos) {
        int screenWidth = 10; // Número total de caracteres en la barra de progreso
        System.out.println("El viaje interplanetario comenzará en:");

        long startTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startTime) / 1000 < segundos) {
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            int remainingTime = segundos - (int) elapsedTime;

            // Calcular el progreso de la barra
            int progress = (int) ((double) remainingTime / segundos * screenWidth);

            // Construir la barra de progreso
            String progressBar = "■".repeat(progress) + " ".repeat(screenWidth - progress);

            // Imprimir la barra de progreso y el tiempo restante
            System.out.printf(YELLOW + "\r[%s] %d segundos" + RESET, progressBar, remainingTime);

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
