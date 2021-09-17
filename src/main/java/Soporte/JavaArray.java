package Soporte;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/**
 * Una clase para emular el concepto de lista implementada sobre un arreglo, tal
 * como la clase java.util.ArrayList de Java (y al estilo de la clase List de
 * Python). Se aplícó una estrategia de desarrollo basada en emular en todo lo
 * posible el comportamiento de la clase Java.util.ArrayList tal como se define
 * en la documentación javadoc de la misma, pero sin entrar en el código fuente
 * original (o sea, una estrategia de desarrollo tipo "clean room": se puede
 * analizar la documentación y los requerimientos, pero no el código fuente
 * fuente ya existente).
 *
 * En esta segunda versión (y definitiva) la clase TSBArrayList deriva de la
 * clase AbsttactList (tal como java.util.ArrayList) e implementa las mismas
 * interfaces que implementa java.util.ArrayList. Se siguen aquí todas las
 * recomendaciones de implementación disponibles en la documentación javadoc de
 * la clase AbstractList.
 *
 * @author Ing. Valerio Frittelli - Ing. Felipe Steffolani.
 * @version Agosto de 2017 - Version 2.0 (final).
 * @param <E> la clase cuyos objetos serán admisibles para la lista.
 */
public class JavaArray<E> extends AbstractList <E> implements List <E>, RandomAccess, Cloneable, Serializable {

    private Object[] items;
    private int initialCapacity;
    private int count;

    /**
     * Crea una lista conteniendo los elementos de la colección que viene como
     * parámetro, en el orden en que son retornados por el iterador de esa
     * colección. Si parámetro c es null, el método lanza una excepción de
     * NullPointerException. Este constructor es sugerido desde la documentación
     * de la clase AbstractList.
     *
     * @param c la colección cuyos elementos serán copiados en la lista.
     * @throws NullPointerException si la referencia c es null.
     */
    public JavaArray(Collection <? extends E> c) {
        this.items = c.toArray();
        this.initialCapacity = c.size();
        this.count = c.size();
    }

    /**
     * Crea una lista con initialCapacity casilleros de capacidad, pero ninguno
     * ocupado realmente: la lista está vacía a todos los efectos prácticos. Si
     * el valor de initialCapacity es <= 0, el valor se ajusta a 10.
     *
     * @param initialCapacity la capacidad inicial de la lista.
     */
    public JavaArray(int initialCapacity) {
        if (initialCapacity <= 0) { initialCapacity = 10; }

        this.items = new Object[initialCapacity];
        this.initialCapacity = initialCapacity;
        this.count = 0;
    }

    /**
     * Crea una lista con capacidad inicial de 10 casilleros, pero ninguno
     * ocupado realmente: la lista está vacía a todos los efectos prácticos.
     * Este constructor es sugerido desde la documentación de la clase
     * AbstractList.
     */
    public JavaArray() { this(10); }

    /**
     * Aumenta la capacidad del arreglo de soporte, si es necesario, para
     * asegurar que pueda contener al menos un número de elementos igual al
     * indicado por el parámetro minCapacity.
     *
     * @param minCapacity - la mínima capacidad requerida.
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity == this.items.length) { return; }
        if (minCapacity < this.count) { return; }

        Object[] temp = new Object[minCapacity];
        System.arraycopy(this.items, 0, temp, 0, this.count);
        this.items = temp;
    }

    /**
     * Ajusta el tamaño del arreglo de soporte, para que coincida con el tamaño
     * de la lista. Puede usarse este método para que un programa ahorre un poco
     * de memoria en cuanto al uso de la lista, si es necesario.
     */
    public void trimToSize() {
        if (this.count == this.items.length) { return; }

        Object[] temp = new Object[this.count];
        System.arraycopy(this.items, 0, temp, 0, this.count);
        this.items = temp;
    }

    /**
     * Añade el objeto e en la posisicón index de la lista . La inserción será
     * rechazada si la referencia e es null (nen ese caso, el método sale sin
     * hacer nada). Si index coincide con el tamaño de la lista, el objeto e
     * será agregado exactamente al final de la lista, como si se hubiese
     * invocado a add(e). Este método es sugerido desde la documentación de la
     * clase AbstractList.
     *
     * @param index el índice de la casilla donde debe quedar el objeto e.
     * @param element el objeto a agregar en la lista.
     * @throws IndexOutOfBoundsException si index < 0 o index > size().
     */
    public void add(int index, E element) {
        if (index > this.count || index < 0) { throw new IndexOutOfBoundsException("Índice Fuera de Rango."); }
        if (element == null) { return; }
        if (this.count == this.items.length) { this.ensureCapacity(this.items.length * 2); }

        int t = this.count - index;
        System.arraycopy(this.items, index, this.items, index + 1, t);
        this.items[index] = element;
        this.count++;

        this.modCount++;
    }

    /**
     * Remueve de la lista el elemento contenido en la posición index. Los
     * objetos ubicados a la derecha de este, se desplazan un casillero a la
     * izquierda. El objeto removido es retornado. La capacidad de la lista no
     * se altera. Si el valor de index no es válido, el método lanzará una
     * excepción de IndexOutOfBoundsException. Este método es sugerido desde la
     * documentación de la clase AbstractList.
     *
     * @param index el índice de la casilla a remover.
     * @return el objeto removido de la lista.
     * @throws IndexOutOfBoundsException si index < 0 o index >= size().
     */
    public E remove(int index) {
        if (index > this.count || index < 0) { throw new IndexOutOfBoundsException("Índice Fuera de Rango."); }
        int t = this.items.length;
        if (this.count < t/2) { this.ensureCapacity(t/2); }

        Object old = this.items[index];
        int n = this.count;
        System.arraycopy(this.items, index + 1, this.items, index, n-index-1);
        this.count--;
        this.items[this.count] = null;
        this.modCount++;

        return (E) old;
    }

    /**
     * Elimina todo el contenido de la lista, y reinicia su capacidad al valor
     * de la capacidad con que fue creada originalmente. La lista queda vacía
     * luego de invocar a clear().
     */
    public void clear() {
        this.items = new Object[this.initialCapacity];
        this.count = 0;

        this.modCount = 0;
    }

    /**
     * Devuelve true si la lista contiene al elemento e. Si e es null el método
     * retorna false. Puede lanzar una excepción de ClassCastException si la clase
     * de e no es compatible con el contenido de la lista.
     *
     * @param element el objeto a buscar en la lista.
     * @return true si la lista contiene al objeto e.
     * @throws ClassCastException si e no es compatible con los objetos de la lista.
     */
    public boolean contains(Object element) {
        if (element == null) { return false; }

        for (int i = 0; i < this.count; i++) {
            if (element.equals(this.items[i])) { return true; }
        }
        return false;
    }

    /**
     * Retorna el objeto contenido en la casilla index. Si el valor de index no
     * es válido, el método lanzará una excepción de la clase
     * IndexOutOfBoundsException. Este método es sugerido desde la documentación
     * de la clase AbstractList.
     *
     * @param index índice de la casilla a acceder.
     * @return referencia al objeto contenido en la casilla index.
     * @throws IndexOutOfBoundsException si index < 0 o index >= size().
     */
    public E get(int index) {
        if (index < 0 || index >= this.count) { throw new IndexOutOfBoundsException("Índice Fuera de Rango."); }

        return (E) this.items[index];
    }

    /**
     * Devuelve true si la lista no contiene elementos.
     *
     * @return true si la lista está vacía.
     */
    public boolean isEmpty() { return (this.count == 0); }

    /**
     * Reemplaza el objeto en la posición index por el referido por element, y
     * retorna el objeto originalmente contenido en la posición index. Si el
     * valor de index no es válido, el método lanzará una excepción de la clase
     * IndexOutOfBoundsException. Este método es sugerido desde la documentación
     * de la clase AbstractList.
     *
     * @param index índice de la casilla a acceder.
     * @param element el objeto que será ubicado en la posición index.
     * @return el objeto originalmente contenido en la posición index.
     * @throws IndexOutOfBoundsException si index < 0 o index >= size().
     */
    public E set(int index, E element) {
        if (index < 0 || index >= this.count) { throw new IndexOutOfBoundsException("Índice Fuera de Rango."); }

        Object old = this.items[index];
        this.items[index] = element;
        return (E) old;
    }

    /**
     * Retorna el tamaño de la lista: la cantidad de elementos realmente
     * contenidos en ella. Este método es sugerido desde la documentación de la
     * clase AbstractList.
     *
     * @return la cantidad de elementos que la lista contiene.
     */
    public int size() { return this.count; }

    /**
     * Retorna una copia superficial de la lista (no se clonan los objetos que
     * la lista contiene: se retorna una lista que contiene las direcciones de
     * los mismos objetos que contiene la original).
     *
     * @return una copia superficial de la lista.
     * @throws java.lang.CloneNotSupportedException si la clase no implementa la interface Cloneable.
     */
    public Object clone() throws CloneNotSupportedException {
        JavaArray<?> temp = (JavaArray<?>) super.clone();
        temp.items = new Object[count];
        System.arraycopy(this.items, 0, temp.items, 0, count);

        temp.modCount = 0;

        return temp;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder("{");

        for (int i = 0; i < this.count; i++) {
            buff.append(this.items[i]);
            if (i < this.count - 1) { buff.append(", "); }
        }
        buff.append("}");

        return buff.toString();
    }
}
