package Soporte;

/**
 * Clase de Soporte, la cual tiene como objetivo, generar una instancia para insertar en la tabla de la interfaz
 * gráfica.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class VotoLista {

    private int numeroLista, cantidadVotos;
    private String nombreLista;

    /**
     * Crea una instancia de VotoLista, asignando un número de lista, un nombre de lista y una cantidad de votos.
     *
     * @param numeroLista número de la lista presidencial.
     * @param nombreLista nombre de la lista presidencial.
     * @param cantidadVotos cantidad de votos que contiene dicha lista presidencial.
     */
    public VotoLista(int numeroLista, String nombreLista, int cantidadVotos) {
        this.numeroLista = numeroLista;
        this.nombreLista = nombreLista;
        this.cantidadVotos = cantidadVotos;
    }

    /**
     * Retorna la variable del número de lista.
     *
     * @return variable del número de lista.
     */
    public int getNumeroLista() {
        return numeroLista;
    }

    /**
     * Reemplaza la variable del número de lista por el recibido por parámetro.
     *
     * @param numeroLista número de lista a reemplazar.
     */
    public void setNumeroLista(int numeroLista) {
        this.numeroLista = numeroLista;
    }

    /**
     * Retorna la variable de la cantidad de votos.
     *
     * @return variable de la cantidad de votos.
     */
    public int getCantidadVotos() {
        return cantidadVotos;
    }

    /**
     * Reemplaza la variable de la cantidad de votos por el recibido por parámetro.
     *
     * @param cantidadVotos cantidad de votos a reemplazar.
     */
    public void setCantidadVotos(int cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }

    /**
     * Retorna la variable del nombre de lista.
     *
     * @return variable del nombre de lista.
     */
    public String getNombreLista() {
        return nombreLista;
    }

    /**
     * Reemplaza la variable del nombre de la lista por el recibido por parámetro.
     *
     * @param nombreLista nombre de lista a reemplazar.
     */
    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

}
