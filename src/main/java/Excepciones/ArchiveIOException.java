package Excepciones;

/**
 * Clase de Excepciones, Deriva de Exception y tiene como objetivo, crear un nuevo objeto de Excepcion para
 * ser informada.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class ArchiveIOException extends Exception {

    private String mensagge;

    /**
     * Crea una instancia de ArchiveIOException, asignando al atributo "mensagge" un mensaje de error pasado por
     * parámetro.
     *
     * @param mensagge mensaje de error.
     */
    public ArchiveIOException(String mensagge) { this.mensagge = mensagge; }

    /**
     * Crea una instancia de ArchiveIOException, asignando al atributo "mensagge" un mensaje de error por defecto.
     */
    public ArchiveIOException() { this("Error al Serializar."); }

    /**
     * Retorna el atributo mensagge como String.
     *
     * @return mensagge.
     */
    @Override
    public String toString() { return this.mensagge; }

}
