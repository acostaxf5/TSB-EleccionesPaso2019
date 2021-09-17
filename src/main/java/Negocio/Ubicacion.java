package Negocio;

import Soporte.*;
import java.io.Serializable;
import java.util.*;

/**
 * Clase de Negocio, la cual tiene como objetivo contener todos los datos de dicha ubicación, tales como el nombre,
 * la lista de candidatos y las sub-ubicaciones que llegara a poder tener dicha ubicación.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class Ubicacion implements Serializable {

    private String nombre;
    private JavaOAHashTable<Integer, Acumulador> listasCandidatos;
    private JavaOAHashTable<String, Ubicacion> subUbicaciones;

    /**
     * Crea una instancia de Ubicacion, creando una tabla de Hash en las sub-ubicaciones en caso de que la bandera este
     * activa; ademas, asigna el nombre a la ubicación y la lista de candidatos.
     *
     * @param okSubUbicacion bandera para informar si se debe crear o no una tabla de sub-ubicaciones de la ubicación.
     * @param listasCandidatos es una JavaOAHashTable que se utiliza para crear una nueva ubicación.
     */
    public Ubicacion(boolean okSubUbicacion, JavaOAHashTable<Integer, String> listasCandidatos) {
        if (okSubUbicacion) { this.subUbicaciones = new JavaOAHashTable<>(); }

        this.nombre = "";
        this.listasCandidatos = new JavaOAHashTable<>();
        for (Integer key : listasCandidatos.keySet()) {
            if (key != null) {
                this.listasCandidatos.put(key, new Acumulador());
            }
        }
    }

    /**
     * Verifica la existencia de una sub-ubicación con el código de ubicación recibido por parámetro.
     *
     * @param codigoUbicacion código de ubicación a consultar.
     * @return boolean -> true si existe; false en caso contrario.
     */
    public boolean existeSubUbicacion(String codigoUbicacion) {
        return this.subUbicaciones.get(codigoUbicacion) != null;
    }

    /**
     * Agrega una sub-ubicación a la tabla de sub-ubicaciones existentes.
     *
     * @param codigoUbicacion código de ubicación a agregar la nueva sub-ubicación.
     * @param ubicacion es la nueva ubicación a agregar en dicha sub-ubicación.
     */
    public void setSubUbicacion(String codigoUbicacion, Ubicacion ubicacion) {
        this.subUbicaciones.put(codigoUbicacion, ubicacion);
    }

    /**
     * Obtiene y retorna una sub-ubicación de acuerdo al código de ubicación.
     *
     * @param codigoUbicacion código de ubicación a localizar.
     * @return ubicación localizada.
     */
    public Ubicacion getSubUbicacion(String codigoUbicacion) {
        return this.subUbicaciones.get(codigoUbicacion);
    }

    /**
     * Incrementa los votos de la lista de candidatos en determinada ubicación.
     *
     * @param codigoUbicacion código de ubicación a obtener.
     * @param votos cantidad de votaciones a incrementar.
     */
    public void incrementarVotos(int codigoUbicacion, int votos) {
        this.listasCandidatos.get(codigoUbicacion).incrementarAcumulador(votos);
    }

    /**
     * Asigna (o en su defecto, reemplaza) un nombre a la ubicación.
     *
     * @param nombre que se debera asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Realiza un listado con todos los nombres de las sub-ubicaciones existentes, la ordena y retorna.
     *
     * @return listado de nombres de las sub-ubicaciones.
     */
    public JavaArray<String> getNombresUbicaciones() {
        Iterator<Map.Entry<String, Ubicacion>> iterator = this.subUbicaciones.entrySet().iterator();
        JavaArray<String> nombres = new JavaArray<>();

        while (iterator.hasNext()) {
            Map.Entry<String, Ubicacion> next = iterator.next();
            if (next.getKey() != null) {
                if (next.getValue().nombre.equals("")) { nombres.add(next.getKey()); }
                else { nombres.add(next.getKey() + "- " + next.getValue().nombre); }
            }
        }

        Collections.sort(nombres);

        nombres.add(0,"Todo");
        return nombres;
    }

    /**
     * Retorna la lista de candidatos.
     *
     * @return lista de candidatos.
     */
    public JavaOAHashTable<Integer, Acumulador> getListasCandidatos() {
        return this.listasCandidatos;
    }

}
