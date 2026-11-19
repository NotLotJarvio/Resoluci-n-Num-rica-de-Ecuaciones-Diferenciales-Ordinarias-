import java.util.Locale;

/**
 * Actividad 2 - Unidad 6: Resolucion Numerica de EDOs.
 * Implementacion modular del Metodo de Runge-Kutta de 4to orden (RK4) para el PVI:
 *   y' = x + y,  y(0) = 1,  x_final = 0.3
 * Solucion analitica exacta:
 *   y(x) = 2 * e^(x) - x - 1
 */
public class MetodoRungeKutta {

    /**
     * Evalua la funcion derivada f(x, y) = x + y de la EDO.
     *
     * @param x Valor actual de la variable independiente.
     * @param y Valor actual de la variable dependiente.
     * @return Valor de la pendiente f(x, y).
     */
    public static double evaluarFuncion(double x, double y) {
        return x + y;
    }

    /**
     * Evalua la solucion analitica exacta y(x) = 2e^(x) - x - 1.
     *
     * @param x Valor de la variable independiente.
     * @return Valor exacto de y(x).
     */
    public static double solucionExacta(double x) {
        return 2.0 * Math.exp(x) - x - 1.0;
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
     * Calcula un unico paso del algoritmo de Runge-Kutta de 4to orden (RK4).
     *
     * @param x Valor actual x_n.
     * @param y Valor actual y_n.
     * @param h Tamano de paso.
     * @return Arreglo {ySiguiente, k1, k2, k3, k4}.
     */
    public static double[] calcularPasoRK4(double x, double y, double h) {
        double k1 = evaluarFuncion(x, y);
        double k2 = evaluarFuncion(x + 0.5 * h, y + 0.5 * h * k1);
        double k3 = evaluarFuncion(x + 0.5 * h, y + 0.5 * h * k2);
        double k4 = evaluarFuncion(x + h, y + h * k3);

        double ySiguiente = y + (h / 6.0) * (k1 + 2.0 * k2 + 2.0 * k3 + k4);
        return new double[]{ySiguiente, k1, k2, k3, k4};
    }

    /**
     * Ejecuta el Metodo de Runge-Kutta de 4to orden (RK4) con criterio de paro formal N.
     *
     * @param x0             Valor inicial de x.
     * @param y0             Valor inicial y(x0).
     * @param h              Tamano de paso.
     * @param xFinal         Valor final del intervalo.
     * @param mostrarTabla   Indica si se imprime la tabla iterativa completa.
     * @return Arreglo {xFinalAlcanzado, yFinalAprox, yFinalExacta, errorFinal}.
     */
    public static double[] resolverRK4(double x0, double y0, double h, double xFinal, boolean mostrarTabla) {
        if (h <= 0.0 || xFinal < x0) {
            throw new IllegalArgumentException("El tamano de paso h debe ser positivo y xFinal >= x0.");
        }

        int nPasos = (int) Math.round((xFinal - x0) / h);
        double x = x0;
        double y = y0;

        if (mostrarTabla) {
            System.out.printf(Locale.US, "=== METODO DE RUNGE-KUTTA 4TO ORDEN (h = %.2f, N = %d pasos) ===%n", h, nPasos);
            System.out.println("n\t x_n\t y_n (RK4)\t y_exacta\t Error Abs.");
            System.out.println("-----------------------------------------------------------------");
            double yEx0 = solucionExacta(x);
            double err0 = calcularErrorAbsoluto(yEx0, y);
            System.out.printf(Locale.US, "%d\t %.4f\t %.8f\t %.8f\t %.2e%n", 0, x, y, yEx0, err0);
        }

        for (int i = 0; i < nPasos; i++) {
            double[] paso = calcularPasoRK4(x, y, h);
            y = paso[0];
            x = x0 + (i + 1) * h;

            if (mostrarTabla) {
                double yEx = solucionExacta(x);
                double errorAbs = calcularErrorAbsoluto(yEx, y);
                System.out.printf(Locale.US, "%d\t %.4f\t %.8f\t %.8f\t %.2e%n", i + 1, x, y, yEx, errorAbs);
            }
        }

        double yFinalExacta = solucionExacta(x);
        double errorFinal = calcularErrorAbsoluto(yFinalExacta, y);

        if (mostrarTabla) {
            System.out.println("-----------------------------------------------------------------\n");
        }

        return new double[]{x, y, yFinalExacta, errorFinal};
    }

    public static void main(String[] args) {
        double x0 = 0.0;
        double y0 = 1.0;
        double xFinal = 0.3;

        if (args.length > 0) {
            double hCustom = Double.parseDouble(args[0]);
            resolverRK4(x0, y0, hCustom, xFinal, true);
            return;
        }

        // Ejecucion principal solicitada en la Actividad 2 (h = 0.1)
        double[] res01 = resolverRK4(x0, y0, 0.1, xFinal, true);

        // Analisis adicional de convergencia O(h^4) variando h
        double[] res005 = resolverRK4(x0, y0, 0.05, xFinal, false);
        double[] res001 = resolverRK4(x0, y0, 0.01, xFinal, false);

        double[] pasos = {0.1, 0.05, 0.01};
        double[][] resumen = {res01, res005, res001};

        System.out.println("=== RESUMEN DE CONVERGENCIA RK4 EN x_final = 0.3 ===");
        System.out.println("h\t N\t y_n (RK4)\t y_exacta\t Error Abs.");
        System.out.println("-----------------------------------------------------------------");
        for (int idx = 0; idx < pasos.length; idx++) {
            int nPasos = (int) Math.round((xFinal - x0) / pasos[idx]);
            System.out.printf(Locale.US, "%.2f\t %d\t %.8f\t %.8f\t %.2e%n",
                    pasos[idx], nPasos, resumen[idx][1], resumen[idx][2], resumen[idx][3]);
        }
        System.out.println("-----------------------------------------------------------------");
    }
}
