package Soporte;

import java.io.Serializable;

/**
 * Clase de Soporte, la cual tiene como objetivo, acumular números.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class Acumulador implements Serializable {

    private int acumulador;

    /**
     * Inicializa la variable que utiliza el acumulador en 0(cero).
     */
    public Acumulador() { this.acumulador = 0; }

    /**
     * Retorna la variable que utiliza el acumulador.
     *
     * @return variable acumuladora.
     */
    public int getAcumulador() { return this.acumulador; }

    /**
     * Incrementa la variable acumuladora en una cantidad especificada por el parámetro acumulador.
     *
     * @param acumulador valor a acumular.
     */
    public void incrementarAcumulador(int acumulador) { this.acumulador += acumulador; }

}
