# Resolución Numérica de EDOs: Método de Euler y Runge-Kutta 4to Orden (RK4)

Implementación modular en Java de los métodos numéricos de **Euler** y **Runge-Kutta de 4to orden (RK4)** para resolver Ecuaciones Diferenciales Ordinarias (EDOs) de primer orden con criterio de paro formal ($N$), variación del tamaño de paso ($h$) y comparación contra soluciones analíticas exactas.

Práctica correspondiente a la **Unidad 6** de la asignatura **SCC-1017 Métodos Numéricos** (Instituto Tecnológico Superior de Xalapa — Ingeniería en Sistemas Computacionales).

## Problemas de Valor Inicial (PVI) implementados

1. **Actividad 1 — Método de Euler (`MetodoEuler.java`)**
   - EDO: $y' = x - y$, con $y(0) = 1$, $x_{\text{final}} = 0.3$
   - Solución analítica exacta: $y(x) = x - 1 + 2e^{-x}$
   - Experimentación con tamaños de paso: $h \in \{0.1, 0.05, 0.01\}$

2. **Actividad 2 — Método de Runge-Kutta de 4to orden (`MetodoRungeKutta.java`)**
   - EDO: $y' = x + y$, con $y(0) = 1$, $x_{\text{final}} = 0.3$
   - Solución analítica exacta: $y(x) = 2e^{x} - x - 1$
   - Ejecución principal con $h = 0.1$ y análisis de convergencia para $h \in \{0.1, 0.05, 0.01\}$

## Estructura del proyecto

```text
ActividadUnidad6_EDOs/
├── .gitignore
├── ActividadUnidad6_EDOs.iml
├── README.md
└── src/
    ├── MetodoEuler.java
    └── MetodoRungeKutta.java
```

## Requisitos

- **Java Development Kit (JDK)** 8 o superior (probado con OpenJDK 25/26).
- **Git** para clonar el repositorio.
- *(Opcional)* **IntelliJ IDEA** para abrir el proyecto directamente.

## Instrucciones de clonación, compilación y ejecución desde la terminal

### 1. Clonar el repositorio

```bash
git clone [PEGAR_AQUI_URL_DE_TU_REPOSITORIO_GITHUB]
cd ActividadUnidad6_EDOs
```

### 2. Compilar las clases en Java

```bash
javac -encoding UTF-8 -d out src/MetodoEuler.java src/MetodoRungeKutta.java
```

### 3. Ejecutar el Método de Euler (Actividad 1)

Ejecuta el experimento completo para $h = 0.1$, $h = 0.05$ y $h = 0.01$:

```bash
java -cp out MetodoEuler
```

También puede especificarse un tamaño de paso puntual como argumento (por ejemplo, `0.1`):

```bash
java -cp out MetodoEuler 0.1
```

### 4. Ejecutar el Método de Runge-Kutta de 4to orden (Actividad 2)

```bash
java -cp out MetodoRungeKutta
```

O con un tamaño de paso específico:

```bash
java -cp out MetodoRungeKutta 0.1
```

### Ejecución desde IntelliJ IDEA

1. Abrir la carpeta `ActividadUnidad6_EDOs` en IntelliJ IDEA.
2. Verificar el SDK en **File > Project Structure > Project > SDK**.
3. Seleccionar la configuración **MetodoEuler** o **MetodoRungeKutta** en la barra superior y presionar **Run**.

## Resultados resumidos en $x_{\text{final}} = 0.3$

| Método | EDO | $h$ | Pasos ($N$) | $y_n$ (Numérico) | $y_{\text{exacta}}(0.3)$ | Error Absoluto |
| :--- | :--- | :---: | :---: | :---: | :---: | :---: |
| Euler | $y' = x - y$ | 0.10 | 3 | 0.758000 | 0.781636 | `0.023636` |
| Euler | $y' = x - y$ | 0.05 | 6 | 0.770184 | 0.781636 | `0.011453` |
| Euler | $y' = x - y$ | 0.01 | 30 | 0.779401 | 0.781636 | `0.002236` |
| RK4 | $y' = x + y$ | 0.10 | 3 | 1.39971699 | 1.39971762 | `6.21e-07` |
| RK4 | $y' = x + y$ | 0.05 | 6 | 1.39971757 | 1.39971762 | `4.05e-08` |
| RK4 | $y' = x + y$ | 0.01 | 30 | 1.39971762 | 1.39971762 | `6.69e-11` |
