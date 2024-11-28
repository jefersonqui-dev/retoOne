# RETO UNO - SIMULACIÓN VIAJE INTERPLANETARIO

Este programa permite realizar una simulacón interplanetaria de diferentes tipos de naves Espaciales a diferentes destinos **(Mercurio, Venus, Marte, Jupiter, Saturno, Urano y Neptuno)**, realiza calculo de **distancia y tiempo estimado de Viaje**, Se calcula el **combustible** y **oxigeno** necesarios para el viaje, ademas se simulan **eventos aleatorios** que pueden suceder durante el viaje, como **Fallo en los sitemas, Colisiones con asteroides, y encuentros con Naves Alienigenas** y debes tomar decisiones en cada caso, para continuar con la misión, por ejemplo para cada evento, se debera realizar **maniobras** que solucionen el problema, y se debera elegir si consumiras Oxigeno o Combustible extra para continuar con el viaje, si tomas una mala decision, **tu Nave quedara atrapada** en el vasto firmamento hasta agotar los recursos Disponibles.

### **Instrucciones para poder usar el programa**

#### **Instalación:**

- Clonar este Repositorio.

```
    git clone https://github.com/jefersonqui-dev/retoOne.git
```

#### **Abre el proyecto en Visual Studio Code o tu Editor de Codigo Favorito:**

- Navega al directorio de tu proyecto

```
    cd tu_repositorio
```

#### **Compilación y Ejecución:**

- Ve a la carpeta 'src' y selecciona la clase RetoOne.java
- Haz clic en el botón de ejecutar en la parte superior derecha o presiona F5.

---

### **Funcionamiento**

- Introduce tu nombre: El programa pedirá al usuario que introduzca su nombre.

**Menú Principal:**

    1. Seleccionar destino
    2. Elegir nave
    3. Iniciar simulación
    4. Información De Las Naves
    0. Salir

---

### **Funcionalidades:**

- El usuario puede seleccionar un destino de una lista de planetas y lunas. Esta selección es obligatoria antes de proceder a elegir una nave.

- Una vez seleccionado el destino, el usuario debe elegir una nave espacial. El programa calculará la distancia al destino y el tiempo estimado de viaje.

### **Iniciar Simulación**

Al iniciar la simulación:

- Se calculan los recursos necesarios como combustible y oxígeno.
  - El usuario debe validar si considera aumentar o disminuir los recursos
    -(S/N) Si digita S tomara los recursos calculados,
  - Si digita N usted podra ajustar los recursos según lo considere
- Se simula el viaje hasta llegar al destino o fallar por falta de recursos.

### **Eventos Aleatorios**

Se introducen eventos aleatorios mencionados anteriormente, el usuario debera decidir si sacrifica oxigeno o combustible para solucionar el problema y continuar con el viaje

---
