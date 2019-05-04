package co.eliseev;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class DIYarrayListTest {

    @Test
    void test_CollectionsAddAll() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");

        Collections.addAll(strings, "e", "f");

        assertEquals(6, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("b", strings.get(1));
        assertEquals("c", strings.get(2));
        assertEquals("d", strings.get(3));
        assertEquals("e", strings.get(4));
        assertEquals("f", strings.get(5));

    }

    @Test
    void test_CollectionsCopy() {
        List<String> src = new ArrayList<>();
        src.add("a");
        src.add("b");
        src.add("c");
        src.add("d");
        List<String> dst = Arrays.asList(new String[src.size()]);

        Collections.copy(dst, src);

        for (int i = 0; i < dst.size(); i++) {
            assertEquals(src.get(i), dst.get(i));
        }
    }

    @Test
    void test_CollectionsSort() {
        List<String> strings = new DIYarrayList<>();
        strings.add("d");
        strings.add("b");
        strings.add("a");
        strings.add("c");
        assertEquals(4, strings.size());

        Collections.sort(strings, String::compareTo);
        assertEquals(4, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("b", strings.get(1));
        assertEquals("c", strings.get(2));
        assertEquals("d", strings.get(3));
    }

    @Test
    void test_newDIYArrayList_default() {
        assertEquals(0, new DIYarrayList<>().size());

        assertThrows(IllegalArgumentException.class, () ->
                        new DIYarrayList<>(-1),
                "Initial size must be positive, but was -1");
    }

    @Test
    void test_add() {
        List<String> strings = new DIYarrayList<>();
        assertEquals(0, strings.size());

        int fillSize = 300;
        for (int i = 0; i < fillSize; i++) {
            strings.add("");
        }
        assertEquals(fillSize, strings.size());
    }

    @Test
    void test_add_byIndex() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        assertEquals(2, strings.size());
        assertEquals("b", strings.get(1));

        strings.add(1, "bb");

        assertEquals(2, strings.size());
        assertEquals("bb", strings.get(1));

        strings.add(1, null);
        assertEquals(2, strings.size());
        assertNull(strings.get(1));

        assertThrows(IndexOutOfBoundsException.class, () ->
                        new DIYarrayList<>().add(-1, "b"),
                "Index must be positive, but was -1");

        assertThrows(IndexOutOfBoundsException.class, () ->
                        new DIYarrayList<>().add(10, "b"),
                "Index: 10, Size: 0");
    }

    @Test
    void test_isEmpty() {
        List<String> strings = new DIYarrayList<>();
        assertTrue(strings.isEmpty());

        strings.add("");
        assertFalse(strings.isEmpty());
    }

    @Test
    void test_contains() {
        List<String> strings = new DIYarrayList<>();
        String element1 = "aaa";
        String element2 = "aab";

        strings.add(element1);
        strings.add(element2);

        assertTrue(strings.contains(element1));
        assertFalse(strings.contains("aac"));
    }

    @Test
    void test_iterator() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");

        Iterator<String> iterator = strings.iterator();
        assertTrue(iterator.hasNext());

        Iterator<String> iterator2 = strings.iterator();
        assertTrue(iterator2.hasNext());
        while (iterator2.hasNext()) {
            iterator2.remove();
        }
        assertTrue(strings.isEmpty());
    }


    @Test
    void test_toArray() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        Object[] objects = strings.toArray();

        assertEquals(4, objects.length);
        assertEquals("a", objects[0]);
        assertEquals("b", objects[1]);
        assertEquals("c", objects[2]);
        assertEquals("d", objects[3]);
    }

    @Test
    void test_toArray_arg() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");

        assertEquals(4, strings.size());
        String[] initArray = new String[5];
        String[] stringsArray = strings.toArray(initArray);

        assertEquals(5, stringsArray.length);
        assertEquals("a", stringsArray[0]);
        assertEquals("b", stringsArray[1]);
        assertEquals("c", stringsArray[2]);
        assertEquals("d", stringsArray[3]);
        assertNull(stringsArray[4]);
    }

    @Test
    void test_remove_byIndex() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        assertEquals("c", strings.remove(2));
        assertEquals(3, strings.size());
    }

    private class Data {
        int x;
        int y;

        Data(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Test
    void test_remove_byObject() {
        List<Data> elements = new DIYarrayList<>();

        Data e1 = new Data(1, 1);
        Data e2 = new Data(2, 2);
        Data e3 = new Data(3, 3);
        Data e4 = new Data(4, 4);

        elements.add(e1);
        elements.add(e2);
        elements.add(e3);
        elements.add(e4);

        assertEquals(4, elements.size());

        assertTrue(elements.remove(e3));
        assertEquals(3, elements.size());
        assertFalse(elements.remove(new Data(5, 5)));
    }

    @Test
    void test_clear() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());
        strings.clear();
        assertTrue(strings.isEmpty());
    }

    @Test
    void test_get() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());
        assertEquals("b", strings.get(1));


        assertThrows(IndexOutOfBoundsException.class, () ->
                        new DIYarrayList<>().get(-1),
                "Index must be positive, but was -1");


        assertThrows(IndexOutOfBoundsException.class, () ->
                        new DIYarrayList<>().get(2),
                "Index: 2, Size: 0");

    }

    @Test
    void test_set() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        strings.set(1, "bb");

        assertEquals(4, strings.size());
        assertEquals("bb", strings.get(1));

        assertThrows(IndexOutOfBoundsException.class, () ->
                        new DIYarrayList<>().set(-1, "bb"),
                "Index must be positive, but was -1");


        assertThrows(IndexOutOfBoundsException.class, () ->
                        strings.set(100, "bb"),
                "Index: 100, Size: 4");
    }

    @Test
    void test_containsAll() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        List<String> strings2 = new DIYarrayList<>();
        strings2.add("a");
        strings2.add("b");
        strings2.add("c");
        strings2.add("d");
        assertEquals(4, strings2.size());

        assertTrue(strings.containsAll(strings2));

        List<String> strings3 = new DIYarrayList<>();
        strings3.add("a");
        strings3.add("y");
        assertEquals(2, strings3.size());

        assertFalse(strings.containsAll(strings3));
        assertTrue(strings.containsAll(new DIYarrayList<>()));

    }

    @Test
    void test_forEach() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        List<String> strings2 = new DIYarrayList<>();
        Consumer<String> consumer = strings2::add;
        strings.forEach(consumer);
        assertEquals(4, strings2.size());


        assertThrows(NullPointerException.class, () ->
                strings.forEach(null));

    }

    @Test
    void test_indexOf() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add(null);
        assertEquals(5, strings.size());

        assertEquals(2, strings.indexOf("c"));
        assertEquals(-1, strings.indexOf("cc"));
        assertEquals(4, strings.indexOf(null));
        assertEquals(0, strings.indexOf("a"));

    }

    @Test
    void test_lastIndexOf() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add(null);
        strings.add("b");
        strings.add(null);
        strings.add("z");
        assertEquals(5, strings.size());
        assertEquals(3, strings.lastIndexOf(null));
        assertEquals(-1, strings.lastIndexOf("c"));
        assertEquals(0, strings.lastIndexOf("a"));

    }

    @Test
    void test_addAll_collection() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        List<String> strings2 = new DIYarrayList<>();
        strings2.add("e");
        strings2.add("f");
        strings2.add("g");
        strings2.add("h");
        assertEquals(4, strings2.size());

        strings.addAll(strings2);
        assertEquals(8, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("b", strings.get(1));
        assertEquals("c", strings.get(2));
        assertEquals("d", strings.get(3));
        assertEquals("e", strings.get(4));
        assertEquals("f", strings.get(5));
        assertEquals("g", strings.get(6));
        assertEquals("h", strings.get(7));

        assertThrows(NullPointerException.class, () ->
                strings.addAll(null));

    }

    @Test
    void test_addAll_indexCollection() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        List<String> strings2 = new DIYarrayList<>();
        strings2.add("e");
        strings2.add("f");
        strings2.add("g");
        strings2.add("h");
        assertEquals(4, strings2.size());

        strings.addAll(2, strings2);
        assertEquals(8, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("b", strings.get(1));
        assertEquals("e", strings.get(2));
        assertEquals("f", strings.get(3));
        assertEquals("g", strings.get(4));
        assertEquals("h", strings.get(5));
        assertEquals("c", strings.get(6));
        assertEquals("d", strings.get(7));


        assertThrows(IndexOutOfBoundsException.class, () ->
                        strings.addAll(-1, new DIYarrayList<>()),
                "Index must be positive, but was -1");

        assertThrows(IndexOutOfBoundsException.class, () ->
                        strings.addAll(99, new DIYarrayList<>()),
                "Index: 99, Size: 8");

        assertThrows(NullPointerException.class, () ->
                strings.addAll(0, null));


    }

    @Test
    void test_removeAll() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        List<String> stringsToRemove = new DIYarrayList<>();
        stringsToRemove.add("b");
        stringsToRemove.add("c");
        assertEquals(2, stringsToRemove.size());

        assertTrue(strings.removeAll(stringsToRemove));
        assertEquals(2, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("d", strings.get(1));

        assertFalse(strings.removeAll(stringsToRemove));


        assertThrows(NullPointerException.class, () ->
                strings.removeAll(null));

    }

    @Test
    void test_removeIf() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());


        assertTrue(strings.removeIf(element -> element.equals("b")));
        assertEquals(3, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("c", strings.get(1));
        assertEquals("d", strings.get(2));

        assertThrows(NullPointerException.class, () ->
                strings.removeIf(null));
    }

    @Test
    void test_retainAll() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        List<String> retainStrings = new DIYarrayList<>();
        retainStrings.add("b");
        retainStrings.add("c");
        assertEquals(2, retainStrings.size());

        assertTrue(strings.retainAll(retainStrings));
        assertEquals(2, strings.size());
        assertEquals("b", strings.get(0));
        assertEquals("c", strings.get(1));


        List<String> strings2 = new DIYarrayList<>();
        assertFalse(strings2.retainAll(retainStrings));
        assertTrue(strings2.isEmpty());
        assertEquals(0, strings2.size());

        assertThrows(NullPointerException.class, () ->
                strings.retainAll(null));

        assertTrue(strings.retainAll(new DIYarrayList<>()));
        assertEquals(0, strings.size());
    }

    @Test
    void test_replaceAll() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        strings.replaceAll(String::toUpperCase);
        assertEquals(4, strings.size());
        assertEquals("A", strings.get(0));
        assertEquals("B", strings.get(1));
        assertEquals("C", strings.get(2));
        assertEquals("D", strings.get(3));

        List<String> strings2 = new DIYarrayList<>();
        strings2.replaceAll(String::toUpperCase);
        assertTrue(strings2.isEmpty());


        assertThrows(NullPointerException.class, () ->
                strings.replaceAll(null));
    }

    @Test
    void test_sort() {
        List<String> strings = new DIYarrayList<>();
        strings.add("d");
        strings.add("b");
        strings.add("a");
        strings.add("c");
        assertEquals(4, strings.size());

        strings.sort(String::compareTo);
        assertEquals(4, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("b", strings.get(1));
        assertEquals("c", strings.get(2));
        assertEquals("d", strings.get(3));

        List<String> emptyStrings = new DIYarrayList<>();

        emptyStrings.sort(String::compareTo);
        assertEquals(0, emptyStrings.size());

        assertThrows(NullPointerException.class, () ->
                emptyStrings.sort(null));
    }

    @Test
    void test_sublist() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertEquals(4, strings.size());

        List<String> newStrings = strings.subList(1, 3);
        assertEquals(4, strings.size());
        assertEquals(2, newStrings.size());
        assertEquals("b", newStrings.get(0));
        assertEquals("c", newStrings.get(1));


        assertThrows(IndexOutOfBoundsException.class, () ->
                        strings.subList(-1, 3),
                "Index must be positive, but was -1");

        assertThrows(IllegalArgumentException.class, () ->
                        strings.subList(1, -3),
                "FromIndex in higher than toIndex. From: 1, To: -3");

        assertThrows(IllegalArgumentException.class, () ->
                        strings.subList(5, 3),
                "FromIndex in higher than toIndex. From: 5, To: 3");
    }

    @Test
    void test_listIterator() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        assertEquals(3, strings.size());

        ListIterator<String> iterator = strings.listIterator();

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertEquals(1, iterator.nextIndex());

        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());

        iterator.add("d");
        assertEquals("c", strings.get(2));
        assertEquals("d", strings.get(3));
        assertEquals(4, strings.size());
        assertTrue(iterator.hasPrevious());

        assertEquals(3, iterator.previousIndex());
        assertEquals("d", iterator.previous());

        iterator.previous();
        iterator.remove();
        assertEquals("b", iterator.previous());
        assertEquals("a", iterator.previous());
        assertFalse(iterator.hasPrevious());

        iterator.set("aa");
        iterator.next();
        iterator.next();
        assertEquals(3, strings.size());

        iterator.set(null);
        assertEquals(3, strings.size());

        assertEquals("aa", strings.get(0));
        assertNull(strings.get(1));
        assertEquals("d", strings.get(2));


        assertThrows(NoSuchElementException.class,
                new DIYarrayList<>().listIterator()::next);
        assertThrows(NoSuchElementException.class,
                new DIYarrayList<>().listIterator()::previous);

    }

    @Test
    void test_listIterator_index() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        assertEquals(3, strings.size());

        ListIterator<String> iterator = strings.listIterator(2);

        /*
            Unexpected behavior of ArrayList:

            If we create new iterator with Index arg and try to call methods 'set' or 'remove'
            it throws IllegalStateException because 'lastRet' is by default -1.
         */


        iterator.set("cc");
        assertEquals(3, strings.size());
        assertEquals("a", strings.get(0));
        assertEquals("b", strings.get(1));
        assertEquals("cc", strings.get(2));


        /*
            Test empty collection
         */
        List<String> strings2 = new DIYarrayList<>();
        ListIterator<String> iterator2 = strings2.listIterator(0);
        iterator2.add("a");
        assertEquals(1, strings2.size());
        assertEquals("a", strings2.get(0));


        assertThrows(IndexOutOfBoundsException.class, () ->
                        strings.listIterator(-1),
                "Index must be positive, but was -1");

        assertThrows(IndexOutOfBoundsException.class, () ->
                        strings.listIterator(10),
                "Index: 10, Size: 3");
    }
}
