package Negocio;

import Soporte.Acumulador;
import Soporte.JavaArray;
import Soporte.JavaOAHashTable;
import java.io.Serializable;

/**
 * Clase de Negocio, la cual, tiene como objetivo manejar el arbol de ubicaciones del pais, dividiendolo por distritos,
 * secciones y circuitos; ademas, se inserta a cada ubicación una tabla de la lista de candidatos presidenciales.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class EGPaso implements Serializable {

    private Ubicacion argentina;
    private JavaOAHashTable<Integer, String> listasCandidatos;

    /**
     * Crea una instancia de EGPaso, asignando la lista de candidatos, una nueva ubicación de pais y asigna el nombre
     * del pais.
     *
     * @param listasCandidatos es una JavaOAHashTable que se utiliza para crear una nueva ubicación.
     */
    public EGPaso(JavaOAHashTable<Integer, String> listasCandidatos) {
        this.listasCandidatos = listasCandidatos;
        this.argentina = new Ubicacion(true, listasCandidatos);
        this.argentina.setNombre("Argentina");
    }

    /**
     * De acuerdo al código de ubicación recibido por el archivo, la asigna en su correspondiente hoja del arbol.
     *
     * @param codigoUbicacion el cual va a ser rebanado en varios pedazos para su procesamiento.
     * @param nombreUbicacion el cual se va a asignar a la nueva ubicación.
     */
    public void addUbicacion(String codigoUbicacion, String nombreUbicacion) {
        String codigoDistrito = "", codigoSElectoral = "", codigoCElectoral = "";

        switch (codigoUbicacion.length()) {
            case 8: return;
            case 11: codigoCElectoral = codigoUbicacion.substring(5, 11);
            case 5: codigoSElectoral = codigoUbicacion.substring(2, 5);
            case 2: codigoDistrito = codigoUbicacion.substring(0, 2);
        }

        Ubicacion distrito = null, sElectoral = null, cElectoral = null;

        if (!codigoDistrito.equals("")) {
            if (!this.argentina.existeSubUbicacion(codigoDistrito)) {
                this.argentina.setSubUbicacion(codigoDistrito, new Ubicacion(true, this.listasCandidatos));
            }
            distrito = this.argentina.getSubUbicacion(codigoDistrito);
        }

        if (!codigoSElectoral.equals("")) {
            assert distrito != null;
            if (!distrito.existeSubUbicacion(codigoSElectoral)) {
                distrito.setSubUbicacion(codigoSElectoral, new Ubicacion(true, this.listasCandidatos));
            }
            sElectoral = distrito.getSubUbicacion(codigoSElectoral);
        }

        if (!codigoCElectoral.equals("")) {
            assert sElectoral != null;
            if (!sElectoral.existeSubUbicacion(codigoCElectoral)) {
                sElectoral.setSubUbicacion(codigoCElectoral, new Ubicacion(true, this.listasCandidatos));
            }
            cElectoral = sElectoral.getSubUbicacion(codigoCElectoral);
        }

        switch (codigoUbicacion.length()) {
            case 11: if (cElectoral != null) cElectoral.setNombre(nombreUbicacion); break;
            case 5: if (sElectoral != null) sElectoral.setNombre(nombreUbicacion); break;
            case 2: if (distrito != null) distrito.setNombre(nombreUbicacion); break;
        }
    }

    /**
     * Método para realizar la sumatoria de votos en cada ubicación.
     *
     * @param codigoUbicacion el cual va a ser rebanado en varios pedazos para su procesamiento.
     * @param codigoMesa el cual se utiliza para realizar la sumatoria de votos en dicha mesa.
     * @param codigoLista el cual se utiliza para acceder al número de lista a realizar la sumatoria.
     * @param votos lleva asignado la cantidad de votos a sumar.
     */
    public void contabilizarVotos(String codigoUbicacion, String codigoMesa, int codigoLista, int votos) {
        String codigoDistrito, codigoSElectoral, codigoCElectoral;
        codigoCElectoral = codigoUbicacion.substring(5, 11);
        codigoSElectoral = codigoUbicacion.substring(2, 5);
        codigoDistrito = codigoUbicacion.substring(0, 2);

        Ubicacion distrito, sElectoral, cElectoral, mesa;
        distrito = this.argentina.getSubUbicacion(codigoDistrito);
        sElectoral = distrito.getSubUbicacion(codigoSElectoral);
        cElectoral = sElectoral.getSubUbicacion(codigoCElectoral);

        if (!cElectoral.existeSubUbicacion(codigoMesa)) {
            mesa = new Ubicacion(false, this.listasCandidatos);
            cElectoral.setSubUbicacion(codigoMesa, mesa);
        } else { mesa = cElectoral.getSubUbicacion(codigoMesa); }

        this.argentina.incrementarVotos(codigoLista, votos);
        distrito.incrementarVotos(codigoLista, votos);
        sElectoral.incrementarVotos(codigoLista, votos);
        cElectoral.incrementarVotos(codigoLista, votos);
        mesa.incrementarVotos(codigoLista, votos);
    }

    /**
     * Crea una lista que contenga a los nombres de las ubicaciones que existen de acuerdo a un código de ubicación,
     * la misma se va a utilizar para asignar al box correspondiente de acuerdo la opcion ingresada.
     *
     * @param opcion se utiliza como indicador de que lista debe crear.
     * @param codigoUbicacion el cual va a ser rebanado en varios pedazos para su procesamiento.
     * @return lista de nombres de acuerdo al código de ubicación.
     */
    public JavaArray<String> listaNombresUbicaciones(int opcion, String codigoUbicacion) {
        String codigoDistrito = "", codigoSElectoral = "", codigoCElectoral = "";

        switch (codigoUbicacion.length()) {
            case 11: codigoCElectoral = codigoUbicacion.substring(5, 11);
            case 5: codigoSElectoral = codigoUbicacion.substring(2, 5);
            case 2: codigoDistrito = codigoUbicacion.substring(0, 2);
        }

        Ubicacion distrito, sElectoral, cElectoral;

        switch (opcion) {
            default:
                return this.argentina.getNombresUbicaciones();
            case 1:
                distrito = this.argentina.getSubUbicacion(codigoDistrito);
                return distrito.getNombresUbicaciones();
            case 2:
                distrito = this.argentina.getSubUbicacion(codigoDistrito);
                sElectoral = distrito.getSubUbicacion(codigoSElectoral);
                return sElectoral.getNombresUbicaciones();
            case 3:
                distrito = this.argentina.getSubUbicacion(codigoDistrito);
                sElectoral = distrito.getSubUbicacion(codigoSElectoral);
                cElectoral = sElectoral.getSubUbicacion(codigoCElectoral);
                return cElectoral.getNombresUbicaciones();
        }
    }

    /**
     * Crea una lista que contenga a los acumuladores de las listas que existen de acuerdo a un código de ubicación,
     * la misma se va a utilizar para realizar la vista en la tabla de la interfaz gráfica.
     *
     * @param opcion se utiliza como indicador de que lista debe crear.
     * @param codigoUbicacion el cual va a ser rebanado en varios pedazos para su procesamiento.
     * @return Lista de acumuladores de cada lista presidencial de acuerdo al código de ubicación.
     */
    public JavaOAHashTable<Integer, Acumulador> votacionesFiltradas(int opcion, String codigoUbicacion) {
        String codigoDistrito = "", codigoSElectoral = "", codigoCElectoral = "", codigoMesa = "";

        switch (codigoUbicacion.length()) {
            case 17: codigoMesa = codigoUbicacion.substring(11, 17);
            case 11: codigoCElectoral = codigoUbicacion.substring(5, 11);
            case 5: codigoSElectoral = codigoUbicacion.substring(2, 5);
            case 2: codigoDistrito = codigoUbicacion.substring(0, 2);
        }

        Ubicacion distrito, sElectoral, cElectoral, mesa;

        switch (opcion) {
            default:
                return this.argentina.getListasCandidatos();
            case 1:
                distrito = this.argentina.getSubUbicacion(codigoDistrito);
                return distrito.getListasCandidatos();
            case 2:
                distrito = this.argentina.getSubUbicacion(codigoDistrito);
                sElectoral = distrito.getSubUbicacion(codigoSElectoral);
                return sElectoral.getListasCandidatos();
            case 3:
                distrito = this.argentina.getSubUbicacion(codigoDistrito);
                sElectoral = distrito.getSubUbicacion(codigoSElectoral);
                cElectoral = sElectoral.getSubUbicacion(codigoCElectoral);
                return cElectoral.getListasCandidatos();
            case 4:
                distrito = this.argentina.getSubUbicacion(codigoDistrito);
                sElectoral = distrito.getSubUbicacion(codigoSElectoral);
                cElectoral = sElectoral.getSubUbicacion(codigoCElectoral);
                mesa = cElectoral.getSubUbicacion(codigoMesa);
                return mesa.getListasCandidatos();
        }
    }
}
