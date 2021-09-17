package Persistencia;

import Excepciones.ArchiveIOException;
import Negocio.EGPaso;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Clase de Persistencia, la cual tiene como objetivo, serializar o grabar datos en un archivo en Disco Local.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class ArchiveWriter {

    private String archive;

    /**
     * Crea una instancia de ArchiveWriter, asignandole al atributo "archive" el nombre de archivo pasado por parametro.
     *
     * @param archive nombre de archivo.
     */
    public ArchiveWriter(String archive) { this.archive = archive; }

    /**
     * Crea una instancia de ArchiveWriter, asignandole al atributo "archive" un nombre de archivo por defecto.
     */
    public ArchiveWriter() { this("dataArchive.dat"); }

    /**
     * Serializa el contenido pasado por parámetro y lo graba en el archivo asignado por el atributo "archive".
     *
     * @param egPaso dato a serializar.
     * @throws ArchiveIOException Error de grabación.
     */
    public void writer(EGPaso egPaso) throws ArchiveIOException {
        try {
            FileOutputStream fos = new FileOutputStream(this.archive);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(egPaso);

            oos.close();
            fos.close();
        } catch (Exception ex) { throw new ArchiveIOException("Error al Grabar Datos."); }
    }

}
