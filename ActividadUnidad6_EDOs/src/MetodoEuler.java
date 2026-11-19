import java.util.Locale;

/**
 * Actividad 1 - Unidad 6: Resolucion Numerica de EDOs.
 * Implementacion modular del Metodo de Euler para el problema de valor inicial:
 *   y' = x - y,  y(0) = 1,  x_final = 0.3
 * Solucion analitica exacta:
 *   y(x) = x - 1 + 2 * e^(-x)
 */
public class MetodoEuler {

    /**
     * Evalua la funcion derivada f(x, y) = x - y de la EDO.
     *
     * @param x Valor actual de la variable independiente.
     * @param y Valor actual de la variable dependiente.
     * @return Valor de la pendiente f(x, y).
     */
    public static double evaluarFuncion(double x, double y) {
        return x - y;
    }

    /**
     * Evalua la solucion analitica exacta y(x) = x - 1 + 2e^(-x).
     *
     * @param x Valor de la variable independiente.
     * @return Valor exacto de y(x).
     */
    public static double solucionExacta(double x) {
        return x - 1.0 + 2.0 * Math.exp(-x);
    }

    /**
     * Calcula el error absoluto |y_exacta - y_aprox|.
     *
     * @param yExacta Valor analitico exacto.
     * @param yAprox  Valor aproximado numericamente.
     * @return Error absoluto.
     */
    public static double calcularErrorAbsoluto(double yExacta, double yAprox) {
        return Math.abs(yExacta - yAprox);
    }

    /**
     * Ejecuta el Metodo de Euler de forma modular con criterio de paro formal N.
     *
     * @param x0     Valor inicial de x.
     * @param y0     Valor inicial y(x0).
     * @param h      Tamano de paso.
     * @param xFinal Valor final del intervalo.
     * @return Arreglo {xFinalAlcanzado, yFinalAprox, yFinalExacta, errorFinal}.
     */
    public static double[] resolverEuler(double x0, double y0, double h, double xFinal) {
        if (h <= 0.0 || xFinal < x0) {
            throw new IllegalArgumentException("El tamano de paso h debe ser positivo y xFinal >= x0.");
        }

        // Criterio de paro (numero total de pasos N)
        int nPasos = (int) Math.round((xFinal - x0) / h);
        double x = x0;
        double y = y0;

        System.out.printf(Locale.US, "=== METODO DE EULER (h = %.2f, N = %d pasos) ===%n", h, nPasos);
        System.out.println("n\t x_n\t y_n (Euler)\t y_exacta\t Error Abs.");
        System.out.println("-----------------------------------------------------------------");

        double yEx0 = solucionExacta(x);
        double err0 = calcularErrorAbsoluto(yEx0, y);
        System.out.printf(Locale.US, "%d\t %.4f\t %.6f\t %.6f\t %.6f%n", 0, x, y, yEx0, err0);

        for (int i = 0; i < nPasos; i++) {
            double pendiente = evaluarFuncion(x, y);
            y = y + h * pendiente;
            x = x0 + (i + 1) * h;

            double yEx = solucionExacta(x);
            double errorAbs = calcularErrorAbsoluto(yEx, y);
            System.out.printf(Locale.US, "%d\t %.4f\t %.6f\t %.6f\t %.6f%n", i + 1, x, y, yEx, errorAbs);
        }

        double yFinalExacta = solucionExacta(x);
        double errorFinal = calcularErrorAbsoluto(yFinalExacta, y);
        System.out.println("-----------------------------------------------------------------\n");

        return new double[]{x, y, yFinalExacta, errorFinal};
    }

    public static void main(String[] args) {
        double x0 = 0.0;
        double y0 = 1.0;
        double xFinal = 0.3;

        // Si se pasa un tamano de paso h por consola, ejecuta solo ese valor;
        // en caso contrario ejecuta el experimento completo (h = 0.1, 0.05 y 0.01).
        double[] pasos;
        if (args.length > 0) {
            pasos = new double[]{Double.parseDouble(args[0])};
        } else {
            pasos = new double[]{0.1, 0.05, 0.01};
        }

        double[][] resumen = new double[pasos.length][];
        for (int idx = 0; idx < pasos.length; idx++) {
            resumen[idx] = resolverEuler(x0, y0, pasos[idx], xFinal);
        }

        if (pasos.length > 1) {
            System.out.println("=== RESUMEN COMPARATIVO DE VARIACION DE h EN x_final = 0.3 ===");
            System.out.println("h\t N\t y_n (Euler)\t y_exacta\t Error Abs.");
            System.out.println("-----------------------------------------------------------------");
            for (int idx = 0; idx < pasos.length; idx++) {
                int nPasos = (int) Math.round((xFinal - x0) / pasos[idx]);
                System.out.printf(Locale.US, "%.2f\t %d\t %.6f\t %.6f\t %.6f%n",
                        pasos[idx], nPasos, resumen[idx][1], resumen[idx][2], resumen[idx][3]);
            }
            System.out.println("-----------------------------------------------------------------");
        }
    }
}
