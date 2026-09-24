import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Kaleb Agbobli
 * @author Andrew Pavel
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    @Test
    public void testConstructor() {
        Map<String, String> t = this.constructorTest();
        Map<String, String> r = this.constructorRef();

        assertEquals(r, t);
        assertEquals(0, t.size());
        assertEquals(0, r.size());
    }

    @Test
    public void testAddEmpty() {
        Map<String, String> t = this.constructorTest();
        Map<String, String> r = this.constructorRef();

        t.add("a", "1");
        r.add("a", "1");

        assertEquals(r, t);
    }

    @Test
    public void testAddMultiple() {
        Map<String, String> t = this.constructorTest();
        Map<String, String> r = this.constructorRef();

        t.add("a", "1");
        t.add("b", "2");
        t.add("c", "3");

        r.add("a", "1");
        r.add("b", "2");
        r.add("c", "3");

        assertEquals(r, t);
    }

    @Test
    public void testAddSameHashCode() {
        Map<String, String> t = this.constructorTest();
        Map<String, String> r = this.constructorRef();

        String key1 = "Aa";
        String key2 = "BB";

        t.add(key1, "1");
        t.add(key2, "2");

        r.add(key1, "1");
        r.add(key2, "2");

        assertEquals(r, t);
    }

    @Test
    public void testRemoveOnlyEntry() {
        Map<String, String> t = this.createFromArgsTest("a", "1");
        Map<String, String> r = this.createFromArgsRef("a", "1");

        Map.Pair<String, String> tp = t.remove("a");
        Map.Pair<String, String> rp = r.remove("a");

        assertEquals(rp, tp);
        assertEquals(r, t);
    }

    @Test
    public void testRemoveFirstOfSeveral() {
        Map<String, String> t = this.createFromArgsTest("a", "1", "b", "2", "c", "3");
        Map<String, String> r = this.createFromArgsRef("a", "1", "b", "2", "c", "3");

        Map.Pair<String, String> tp = t.remove("a");
        Map.Pair<String, String> rp = r.remove("a");

        assertEquals(rp, tp);
        assertEquals(r, t);
    }

    @Test
    public void testRemoveMiddleOfSeveral() {
        Map<String, String> t = this.createFromArgsTest("a", "1", "b", "2", "c", "3");
        Map<String, String> r = this.createFromArgsRef("a", "1", "b", "2", "c", "3");

        Map.Pair<String, String> tp = t.remove("b");
        Map.Pair<String, String> rp = r.remove("b");

        assertEquals(rp, tp);
        assertEquals(r, t);
    }

    @Test
    public void testRemoveLastOfSeveral() {
        Map<String, String> t = this.createFromArgsTest("a", "1", "b", "2", "c", "3");
        Map<String, String> r = this.createFromArgsRef("a", "1", "b", "2", "c", "3");

        Map.Pair<String, String> tp = t.remove("c");
        Map.Pair<String, String> rp = r.remove("c");

        assertEquals(rp, tp);
        assertEquals(r, t);
    }

    @Test
    public void testRemoveAnyOne() {
        Map<String, String> t = this.createFromArgsTest("a", "1");
        Map<String, String> r = this.createFromArgsRef("a", "1");

        Map.Pair<String, String> tp = t.removeAny();
        Map.Pair<String, String> rp = r.removeAny();

        assertEquals(rp, tp);
        assertEquals(r, t);
    }

    @Test
    public void testRemoveAnySeveral() {
        Map<String, String> t = this.createFromArgsTest("a", "1", "b", "2", "c", "3");
        Map<String, String> r = this.createFromArgsRef("a", "1", "b", "2", "c", "3");

        while (t.size() > 0) {
            Map.Pair<String, String> tp = t.removeAny();
            Map.Pair<String, String> rp = r.removeAny();

            assertEquals(rp, tp);
            assertEquals(r, t);
        }
    }

    @Test
    public void testValueOne() {
        Map<String, String> t = this.createFromArgsTest("a", "1");
        Map<String, String> r = this.createFromArgsRef("a", "1");

        assertEquals(r.value("a"), t.value("a"));
    }

    @Test
    public void testValueSeveral() {
        Map<String, String> t = this.createFromArgsTest("a", "1", "b", "2", "c", "3");
        Map<String, String> r = this.createFromArgsRef("a", "1", "b", "2", "c", "3");

        assertEquals(r.value("a"), t.value("a"));
        assertEquals(r.value("b"), t.value("b"));
        assertEquals(r.value("c"), t.value("c"));
    }

    @Test
    public void testHasKeyEmpty() {
        Map<String, String> t = this.constructorTest();
        Map<String, String> r = this.constructorRef();

        assertEquals(r.hasKey("a"), t.hasKey("a"));
    }

    @Test
    public void testHasKeyPresent() {
        Map<String, String> t = this.createFromArgsTest("a", "1", "b", "2");
        Map<String, String> r = this.createFromArgsRef("a", "1", "b", "2");

        assertEquals(r.hasKey("a"), t.hasKey("a"));
        assertEquals(r.hasKey("b"), t.hasKey("b"));
    }

    @Test
    public void testHasKeyAbsent() {
        Map<String, String> t = this.createFromArgsTest("a", "1", "b", "2");
        Map<String, String> r = this.createFromArgsRef("a", "1", "b", "2");

        assertEquals(r.hasKey("c"), t.hasKey("c"));
    }

    @Test
    public void testSizeEmpty() {
        Map<String, String> t = this.constructorTest();
        Map<String, String> r = this.constructorRef();

        assertEquals(r.size(), t.size());
    }

    @Test
    public void testSizeAfterAdd() {
        Map<String, String> t = this.constructorTest();
        Map<String, String> r = this.constructorRef();

        t.add("a", "1");
        r.add("a", "1");
        assertEquals(r.size(), t.size());

        t.add("b", "2");
        r.add("b", "2");
        assertEquals(r.size(), t.size());
    }

    @Test
    public void testSizeAfterRemove() {
        Map<String, String> t = this.createFromArgsTest("a", "1", "b", "2", "c", "3");
        Map<String, String> r = this.createFromArgsRef("a", "1", "b", "2", "c", "3");

        t.remove("b");
        r.remove("b");

        assertEquals(r.size(), t.size());
        assertEquals(r, t);
    }

}
