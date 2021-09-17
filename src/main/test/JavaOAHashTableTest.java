import Soporte.JavaOAHashTable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Assert;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase de Testeo, la cual tiene como objetivo testear cada método de la clase JavaOAHashTable.
 *
 * @author Facundo L. Acosta - Lucas Paillet - Camila Cocuzza
 */
public class JavaOAHashTableTest {

    private JavaOAHashTable<Integer, Integer> instance;
    private JavaOAHashTable<Integer, Integer> empty;

    public JavaOAHashTableTest() {  }

    @BeforeClass
    public static void setUpClass() {  }

    @AfterClass
    public static void tearDownClass() {  }

    @Before
    public void setUp() {
        System.out.println("* UtilsJUnit4Test: @Before method");

        int[] keys = {1, 3, 5, 7, 9, 2, 4, 6, 8, 0, 3};
        int[] values = {10, 30, 50, 70, 90, 20, 40, 60, 80, 0, 30};

        this.empty = new JavaOAHashTable<>();
        this.instance = new JavaOAHashTable<>();

        for (int i = 0; i < keys.length; i++) {
            this.instance.put(keys[i], values[i]);
        }
    }

    @After
    public void tearDown() {  }

    /**
     * Test del método testSize().
     */
    @org.junit.Test
    public void testSize() {
        System.out.println("size()");

        Assert.assertEquals(10, this.instance.size());
        Assert.assertEquals(0, this.empty.size());
    }

    /**
     * Test del método isEmpty().
     */
    @org.junit.Test
    public void testIsEmpty() {
        System.out.println("isEmpty()");

        Assert.assertFalse(this.instance.isEmpty());
        Assert.assertTrue(this.empty.isEmpty());
    }

    /**
     * Test del método containsKey().
     */
    @org.junit.Test
    public void testContainsKey() {
        System.out.println("containsKey()");

        Assert.assertTrue(this.instance.containsKey(4));
        Assert.assertFalse(this.instance.containsKey(84));
        Assert.assertFalse(this.empty.containsKey(13));
    }

    /**
     * Test del método containsValue().
     */
    @org.junit.Test
    public void testContainsValue() {
        System.out.println("containsValue()");

        Assert.assertTrue(this.instance.containsValue(90));
        Assert.assertFalse(this.instance.containsValue(98));
        Assert.assertFalse(this.empty.containsValue(50));
    }

    /**
     * Test del método get().
     */
    @org.junit.Test (expected = NullPointerException.class)
    public void testGet() {
        System.out.println("get()");

        Assert.assertEquals((Integer) 50, this.instance.get(5));
        Assert.assertNotEquals((Integer) 25, this.instance.get(5));
        this.empty.get(null);
        int r1 = this.empty.get(20);
    }

    /**
     * Test del método put().
     */
    @org.junit.Test (expected = NullPointerException.class)
    public void testPut() {
        System.out.println("put()");

        Assert.assertEquals((Integer) 50, this.instance.put(5, 99));
        Assert.assertNotEquals((Integer) 45, this.instance.put(3, 32));
        Assert.assertNull(this.instance.put(24, 20));
        Assert.assertNull(this.empty.put(1, 10));
        Integer v1 = this.instance.put(null, 5);
        Integer v2 = this.instance.put(5, null);
        Integer v3 = this.instance.put(null, null);
    }

    /**
     * Test del método remove().
     */
    @org.junit.Test (expected = NullPointerException.class)
    public void testRemove() {
        System.out.println("remove()");

        int size = this.instance.size();
        Assert.assertEquals((Integer) 30, this.instance.remove(3));
        Assert.assertEquals(size - 1, this.instance.size());
        Assert.assertNull(this.instance.remove(46));
        Integer r1 = this.empty.remove(null);
    }

    /**
     * Test del método putAll().
     */
    @org.junit.Test (expected = NullPointerException.class)
    public void testPutAll() {
        System.out.println("putAll()");

        JavaOAHashTable<Integer, Integer> table = new JavaOAHashTable<>();
        for (int i = 0; i < 6; i++) {
            table.put(i*4, i*5);
        }

        this.instance.putAll(table);
        Assert.assertEquals((Integer) 90, this.instance.get(9));
        Assert.assertEquals((Integer) 20, this.instance.get(16));
        Assert.assertNotEquals((Integer) 45, this.instance.get(16));

        JavaOAHashTable<Integer, Integer> nullTable = null;
        this.empty.putAll(nullTable);
    }

    /**
     * Test del método clear().
     */
    @org.junit.Test
    public void testClear() {
        System.out.println("clear()");

        this.instance.clear();
        Assert.assertEquals(0, this.instance.size());
        this.empty.clear();
        Assert.assertEquals(0, this.empty.size());
    }

    /**
     * Test del método clone().
     */
    @org.junit.Test
    public void testClone() {
        System.out.println("clone()");

        try {
            JavaOAHashTable<Integer, Integer> copy = (JavaOAHashTable<Integer, Integer>) this.instance.clone();
            Assert.assertEquals(this.instance, copy);
        } catch (CloneNotSupportedException ignored) {  }
    }

    /**
     * Test del método equals().
     */
    @org.junit.Test
    public void testEquals() {
        System.out.println("equals()");

        Assert.assertEquals(this.instance, this.instance);
        Assert.assertNotEquals(this.instance, this.empty);
    }

    /**
     * Test del método hashCode().
     */
    @org.junit.Test
    public void testHashCode() {
        System.out.println("hashCode()");

        Assert.assertNotEquals(this.instance.hashCode(), this.empty.hashCode());
    }

    /**
     * Test del método contains().
     */
    @org.junit.Test
    public void testContains() {
        System.out.println("contains()");

        Assert.assertTrue(this.instance.contains(90));
        Assert.assertFalse(this.instance.contains(33));
        Assert.assertFalse(this.empty.contains(40));
        Assert.assertFalse(this.empty.contains(null));
    }

    /**
     * Test del método keySet().
     */
    @org.junit.Test
    public void iteratorKeySet() {
        System.out.println("iteratorKeySet()");

        Iterator<Integer> iterator = this.instance.keySet().iterator();
        Assert.assertNotEquals(null, iterator);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertNotEquals(null, iterator.next());
        int size = this.instance.size();
        iterator.remove();
        Assert.assertEquals(size - 1, this.instance.size());
    }

    /**
     * Test del método entrySet.iterator().
     */
    @org.junit.Test
    public void iteratorEntrySet() {
        System.out.println("iteratorEntrySet()");

        Iterator<Map.Entry<Integer, Integer>> iterator = this.instance.entrySet().iterator();
        Assert.assertNotEquals(null, iterator);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertNotEquals(null, iterator.next().getKey());
        int size = this.instance.size();
        iterator.remove();
        Assert.assertEquals(size - 1, this.instance.size());
    }

    /**
     * Test del método valueCollection.iterator().
     */
    @org.junit.Test
    public void iteratorValueCollection() {
        System.out.println("iteratorValueCollection()");

        Iterator<Integer> iterator = this.instance.values().iterator();
        Assert.assertNotEquals(null, iterator);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertNotEquals(null, iterator.next());
        int size = this.instance.size();
        iterator.remove();
        Assert.assertEquals(size - 1, this.instance.size());
    }

}
