package Persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Negocio.EGPaso;
import Soporte.JavaOAHashTable;

/**
 * Clase de Persistencia, la cual realiza las lecturas de archivo y la grabación en memoria de los datos obtenidos.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class SistemaArchivos {

    /**
     * Realiza la lectura del archivo "descripción_postulaciones.dsv" y graba en memoria los datos recuperados.
     *
     * @param listasCandidatos hashTable en donde debe almacenar los datos recuperados.
     * @throws FileNotFoundException si el archivo no se encuentra.
     */
    public void loadPostulaciones(JavaOAHashTable<Integer, String> listasCandidatos) throws FileNotFoundException {
        Scanner archiveDP = new Scanner(new File("descripcion_postulaciones.dsv"));
        archiveDP.nextLine();

        while (archiveDP.hasNextLine()) {
            String[] line = archiveDP.nextLine().split("\\|");
            if (line[0].equals("000100000000000")) {
                listasCandidatos.put(Integer.parseInt(line[2]), line[3]);
            }
        }
        archiveDP.close();
    }

    /**
     * Realiza la lectura del archivo "descripcion_regiones.dsv" y graba en memoria los datos recuperados.
     *
     * @param egPaso clase contenedora de toda la información necesario para la gestión del proyecto.
     * @throws FileNotFoundException si el archivo no se encuentra.
     */
    public void loadRegiones(EGPaso egPaso) throws FileNotFoundException {
        Scanner archiveDR = new Scanner(new File("descripcion_regiones.dsv"));
        archiveDR.nextLine();

        while (archiveDR.hasNextLine()) {
            String[] linea = archiveDR.nextLine().split("\\|");
            egPaso.addUbicacion(linea[0], linea[1]);
        }

        archiveDR.close();
    }

    /**
     * Realiza la lectura del archivo "mesas_totales_agrp_politica.dsv" y graba en memoria los datos recuperados.
     *
     * @param egPaso clase contenedora de toda la información necesario para la gestión del proyecto.
     * @throws FileNotFoundException si el archivo no se encuentra.
     */
    public void loadTotales(EGPaso egPaso) throws FileNotFoundException {
        Scanner archiveMTAP = new Scanner(new File("mesas_totales_agrp_politica.dsv"));
        archiveMTAP.nextLine();

        while (archiveMTAP.hasNextLine()) {
            String[] linea = archiveMTAP.nextLine().split("\\|");
            if (linea[4].equals("000100000000000")) {
                int codigoL = Integer.parseInt(linea[5]);
                int votos = Integer.parseInt(linea[6]);
                egPaso.contabilizarVotos(linea[2], linea[3].substring(5), codigoL, votos);
            }
        }

        archiveMTAP.close();
    }

}
