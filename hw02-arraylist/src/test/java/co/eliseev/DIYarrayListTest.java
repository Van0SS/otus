package co.eliseev;


import com.sun.tools.javac.util.Pair;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class DIYarrayListTest {

    @Test
    public void test_CollectionsAddAll() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");

        Collections.addAll(strings, "e", "f");

        assertThat(strings, hasSize(6));
        assertThat(strings.get(0), equalTo("a"));
        assertThat(strings.get(1), equalTo("b"));
        assertThat(strings.get(2), equalTo("c"));
        assertThat(strings.get(3), equalTo("d"));
        assertThat(strings.get(4), equalTo("e"));
        assertThat(strings.get(5), equalTo("f"));

    }

    @Test
    public void test_CollectionsCopy() {
        List<String> src = new ArrayList<>();
        src.add("a");
        src.add("b");
        src.add("c");
        src.add("d");
        List<String> dst = Arrays.asList(new String[src.size()]);

        Collections.copy(dst, src);

        for (int i = 0; i < dst.size(); i++) {
            assertThat(dst.get(i), equalTo(src.get(i)));
        }
    }

    @Test
    public void test_CollectionsSort() {
        List<String> strings = new DIYarrayList<>();
        strings.add("d");
        strings.add("b");
        strings.add("a");
        strings.add("c");
        assertThat(strings, hasSize(4));

        Collections.sort(strings, String::compareTo);
        assertThat(strings, hasSize(4));
        assertThat(strings.get(0), equalTo("a"));
        assertThat(strings.get(1), equalTo("b"));
        assertThat(strings.get(2), equalTo("c"));
        assertThat(strings.get(3), equalTo("d"));
    }

    @Test
    public void test_newDIYArrayList_default() {
        List<String> strings = new DIYarrayList<>();
        assertThat(strings.size(), equalTo(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_newDIYArrayList_negativeSize() {
        try {
            new DIYarrayList<>(-1);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), equalTo("Initial size must be positive, but was -1"));
            throw ex;
        }
        throw new RuntimeException();
    }

    @Test
    public void test_add() {
        List<String> strings = new DIYarrayList<>();
        assertThat(strings.size(), equalTo(0));

        int fillSize = 300;
        for (int i = 0; i < fillSize; i++) {
            strings.add("");
        }
        assertThat(strings.size(), equalTo(fillSize));
    }

    @Test
    public void test_add_byIndex() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        assertThat(strings, hasSize(2));
        assertThat(strings.get(1), equalTo("b"));

        strings.add(1, "bb");

        assertThat(strings, hasSize(2));
        assertThat(strings.get(1), equalTo("bb"));

        strings.add(1, null);
        assertThat(strings, hasSize(2));
        assertThat(strings.get(1), nullValue());

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_add_negativeIndex() {
        List<String> strings = new DIYarrayList<>();
        try {
            strings.add(-1, "b");
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index must be positive, but was -1"));
            throw expectedException;
        }
        throw new RuntimeException();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_add_greaterIndex() {
        List<String> strings = new DIYarrayList<>();
        try {
            strings.add(10, "b");
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index: 10, Size: 0"));
            throw expectedException;
        }
        throw new RuntimeException();
    }

    @Test
    public void test_isEmpty() {
        List<String> strings = new DIYarrayList<>();
        assertThat(strings.isEmpty(), equalTo(true));

        strings.add("");
        assertThat(strings.isEmpty(), equalTo(false));
    }

    @Test
    public void test_contains() {
        List<String> strings = new DIYarrayList<>();
        String element1 = "aaa";
        String element2 = "aab";

        strings.add(element1);
        strings.add(element2);

        assertThat(strings.contains(element1), equalTo(true));
        assertThat(strings.contains("aac"), equalTo(false));
    }

    @Test
    public void test_iterator() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");

        Iterator<String> iterator = strings.iterator();
        assertThat(iterator.hasNext(), equalTo(true));

        Iterator<String> iterator2 = strings.iterator();
        assertThat(iterator2.hasNext(), equalTo(true));
        while (iterator2.hasNext()) {
            iterator2.remove();
        }
        assertThat(strings.isEmpty(), equalTo(true));
    }


    @Test
    public void test_toArray() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        Object[] objects = strings.toArray();

        assertThat(objects.length, equalTo(4));
        assertThat(objects[0], equalTo("a"));
        assertThat(objects[1], equalTo("b"));
        assertThat(objects[2], equalTo("c"));
        assertThat(objects[3], equalTo("d"));
    }

    @Test
    public void test_toArray_arg() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");

        assertThat(strings, hasSize(4));
        String[] initArray = new String[5];
        String[] stringsArray = strings.toArray(initArray);

        assertThat(stringsArray.length, equalTo(5));
        assertThat(stringsArray[0], equalTo("a"));
        assertThat(stringsArray[1], equalTo("b"));
        assertThat(stringsArray[2], equalTo("c"));
        assertThat(stringsArray[3], equalTo("d"));
        assertThat(stringsArray[4], nullValue());
    }

    @Test
    public void test_remove_byIndex() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        assertThat(strings.remove(2), equalTo("c"));
        assertThat(strings, hasSize(3));
    }

    @Test
    public void test_remove_byObject() {
        List<Pair> elements = new DIYarrayList<>();

        Pair<Integer, Integer> e1 = new Pair<>(1, 1);
        Pair<Integer, Integer> e2 = new Pair<>(2, 2);
        Pair<Integer, Integer> e3 = new Pair<>(3, 3);
        Pair<Integer, Integer> e4 = new Pair<>(4, 4);

        elements.add(e1);
        elements.add(e2);
        elements.add(e3);
        elements.add(e4);

        assertThat(elements, hasSize(4));

        assertThat(elements.remove(e3), equalTo(true));
        assertThat(elements, hasSize(3));
        assertThat(elements.remove(new Pair<>(5, 5)), equalTo(false));
    }

    @Test
    public void test_clear() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));
        strings.clear();
        assertThat(strings.isEmpty(), equalTo(true));
    }

    @Test
    public void test_get() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));
        assertThat(strings.get(1), equalTo("b"));

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_get_negativeIndex() {
        List<String> strings = new DIYarrayList<>();
        try {
            strings.get(-1);
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index must be positive, but was -1"));
            throw expectedException;
        }
        throw new RuntimeException();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_get_greaterIndex() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        try {
            strings.get(2);
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index: 2, Size: 1"));
            throw expectedException;
        }
        throw new RuntimeException();
    }

    @Test
    public void test_set() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        strings.set(1, "bb");

        assertThat(strings, hasSize(4));
        assertThat(strings.get(1), equalTo("bb"));

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_set_negativeIndex() {
        List<String> strings = new DIYarrayList<>();

        try {
            strings.set(-1, "bb");
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index must be positive, but was -1"));
            throw expectedException;
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_set_greaterIndex() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        try {
            strings.set(100, "bb");
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index: 100, Size: 4"));
            throw expectedException;
        }
    }

    @Test
    public void test_containsAll() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        List<String> strings2 = new DIYarrayList<>();
        strings2.add("a");
        strings2.add("b");
        strings2.add("c");
        strings2.add("d");
        assertThat(strings2, hasSize(4));

        assertThat(strings.containsAll(strings2), equalTo(true));

        List<String> strings3 = new DIYarrayList<>();
        strings3.add("a");
        strings3.add("y");
        assertThat(strings3, hasSize(2));

        assertThat(strings.containsAll(strings3), equalTo(false));
        assertThat(strings.containsAll(new DIYarrayList<>()), equalTo(true));

    }

    @Test
    public void test_forEach() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        List<String> strings2 = new DIYarrayList<>();
        Consumer<String> consumer = strings2::add;
        strings.forEach(consumer);

        assertThat(strings2, hasSize(4));

    }

    @Test(expected = NullPointerException.class)
    public void test_forEach_nullConsumer() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        strings.forEach(null);
    }

    @Test
    public void test_indexOf() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add(null);
        assertThat(strings, hasSize(5));

        assertThat(strings.indexOf("c"), equalTo(2));
        assertThat(strings.indexOf("cc"), equalTo(-1));
        assertThat(strings.indexOf(null), equalTo(4));
        assertThat(strings.indexOf("a"), equalTo(0));

    }

    @Test
    public void test_lastIndexOf() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add(null);
        strings.add("b");
        strings.add(null);
        strings.add("z");
        assertThat(strings, hasSize(5));
        assertThat(strings.lastIndexOf(null), equalTo(3));
        assertThat(strings.lastIndexOf("c"), equalTo(-1));
        assertThat(strings.lastIndexOf("a"), equalTo(0));

    }

    @Test
    public void test_addAll_collection() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        List<String> strings2 = new DIYarrayList<>();
        strings2.add("e");
        strings2.add("f");
        strings2.add("g");
        strings2.add("h");
        assertThat(strings2, hasSize(4));

        strings.addAll(strings2);
        assertThat(strings, hasSize(8));
        assertThat(strings.get(0), equalTo("a"));
        assertThat(strings.get(1), equalTo("b"));
        assertThat(strings.get(2), equalTo("c"));
        assertThat(strings.get(3), equalTo("d"));
        assertThat(strings.get(4), equalTo("e"));
        assertThat(strings.get(5), equalTo("f"));
        assertThat(strings.get(6), equalTo("g"));
        assertThat(strings.get(7), equalTo("h"));

    }

    @Test(expected = NullPointerException.class)
    public void test_addAll_collection_fail() {
        List<String> strings = new DIYarrayList<>();
        strings.addAll(null);
    }

    @Test
    public void test_addAll_indexCollection() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        List<String> strings2 = new DIYarrayList<>();
        strings2.add("e");
        strings2.add("f");
        strings2.add("g");
        strings2.add("h");
        assertThat(strings2, hasSize(4));

        strings.addAll(2, strings2);
        assertThat(strings, hasSize(8));
        assertThat(strings.get(0), equalTo("a"));
        assertThat(strings.get(1), equalTo("b"));
        assertThat(strings.get(2), equalTo("e"));
        assertThat(strings.get(3), equalTo("f"));
        assertThat(strings.get(4), equalTo("g"));
        assertThat(strings.get(5), equalTo("h"));
        assertThat(strings.get(6), equalTo("c"));
        assertThat(strings.get(7), equalTo("d"));
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void test_addAll_indexCollection_negativeIndex() {
        List<String> strings = new DIYarrayList<>();

        try {
            strings.addAll(-1, new DIYarrayList<>());
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index must be positive, but was -1"));
            throw expectedException;
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_addAll_indexCollection_greaterIndex() {
        List<String> strings = new DIYarrayList<>();

        try {
            strings.addAll(99, new DIYarrayList<>());
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index: 99, Size: 0"));
            throw expectedException;
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_addAll_indexCollection_error() {
        List<String> strings = new DIYarrayList<>();
        strings.addAll(0, null);
    }

    @Test
    public void test_removeAll() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        List<String> stringsToRemove = new DIYarrayList<>();
        stringsToRemove.add("b");
        stringsToRemove.add("c");
        assertThat(stringsToRemove, hasSize(2));

        assertThat(strings.removeAll(stringsToRemove), equalTo(true));
        assertThat(strings, hasSize(2));
        assertThat(strings.get(0), equalTo("a"));
        assertThat(strings.get(1), equalTo("d"));

        assertThat(strings.removeAll(stringsToRemove), equalTo(false));

    }

    @Test(expected = NullPointerException.class)
    public void test_removeAll_error() {
        List<String> strings = new DIYarrayList<>();
        strings.removeAll(null);
    }

    @Test
    public void test_removeIf() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));


        assertThat(strings.removeIf(element -> element.equals("b")), equalTo(true));
        assertThat(strings, hasSize(3));
        assertThat(strings.get(0), equalTo("a"));
        assertThat(strings.get(1), equalTo("c"));
        assertThat(strings.get(2), equalTo("d"));
    }

    @Test(expected = NullPointerException.class)
    public void test_removeIf_null() {
        List<String> strings = new DIYarrayList<>();
        strings.removeIf(null);
    }


    @Test
    public void test_retainAll() {

        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        List<String> retainStrings = new DIYarrayList<>();
        retainStrings.add("b");
        retainStrings.add("c");
        assertThat(retainStrings, hasSize(2));

        assertThat(strings.retainAll(retainStrings), equalTo(true));
        assertThat(strings, hasSize(2));
        assertThat(strings.get(0), equalTo("b"));
        assertThat(strings.get(1), equalTo("c"));


        List<String> strings2 = new DIYarrayList<>();
        assertThat(strings2.retainAll(retainStrings), equalTo(false));
        assertThat(strings2.isEmpty(), equalTo(true));
        assertThat(strings2, hasSize(0));


    }

    @Test(expected = NullPointerException.class)
    public void test_retainAll_null() {
        List<String> strings = new DIYarrayList<>();
        strings.retainAll(null);
    }

    @Test
    public void test_retailAll_toEmptyCollection() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        assertThat(strings.retainAll(new DIYarrayList<>()), equalTo(true));
        assertThat(strings, hasSize(0));
    }

    @Test
    public void test_replaceAll() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        strings.replaceAll(String::toUpperCase);
        assertThat(strings, hasSize(4));
        assertThat(strings.get(0), equalTo("A"));
        assertThat(strings.get(1), equalTo("B"));
        assertThat(strings.get(2), equalTo("C"));
        assertThat(strings.get(3), equalTo("D"));

        List<String> strings2 = new DIYarrayList<>();
        strings2.replaceAll(String::toUpperCase);
        assertThat(strings2.isEmpty(), equalTo(true));
    }

    @Test(expected = NullPointerException.class)
    public void test_replaceAll_null() {
        List<String> strings = new DIYarrayList<>();
        strings.replaceAll(null);
    }

    @Test
    public void test_sort() {
        List<String> strings = new DIYarrayList<>();
        strings.add("d");
        strings.add("b");
        strings.add("a");
        strings.add("c");
        assertThat(strings, hasSize(4));

        strings.sort(String::compareTo);
        assertThat(strings, hasSize(4));
        assertThat(strings.get(0), equalTo("a"));
        assertThat(strings.get(1), equalTo("b"));
        assertThat(strings.get(2), equalTo("c"));
        assertThat(strings.get(3), equalTo("d"));

        List<String> strings2 = new DIYarrayList<>();
        strings2.sort(String::compareTo);
        assertThat(strings2, hasSize(0));
    }

    @Test(expected = NullPointerException.class)
    public void test_sort_null() {
        List<String> strings = new DIYarrayList<>();
        strings.sort(null);
    }

    @Test
    public void test_sublist() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        assertThat(strings, hasSize(4));

        List<String> newStrings = strings.subList(1, 3);
        assertThat(strings, hasSize(4));
        assertThat(newStrings, hasSize(2));
        assertThat(newStrings.get(0), equalTo("b"));
        assertThat(newStrings.get(1), equalTo("c"));
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void test_sublist_negativeFromIndex() {
        List<String> strings = new DIYarrayList<>();
        try {
            strings.subList(-1, 3);
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index must be positive, but was -1"));
            throw expectedException;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_sublist_negativeToIndex() {
        List<String> strings = new DIYarrayList<>();
        try {
            strings.subList(1, -3);
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("FromIndex in higher than toIndex. From: 1, To: -3"));
            throw expectedException;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_sublist_fromIndexLessThanToIndex() {
        List<String> strings = new DIYarrayList<>();
        try {
            strings.subList(5, 3);
        } catch (IllegalArgumentException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("FromIndex in higher than toIndex. From: 5, To: 3"));
            throw expectedException;
        }
    }


    @Test
    public void test_listIterator() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        assertThat(strings, hasSize(3));

        ListIterator<String> iterator = strings.listIterator();

        assertThat(iterator.hasNext(), equalTo(true));
        assertThat(iterator.next(), equalTo("a"));
        assertThat(iterator.nextIndex(), equalTo(1));

        iterator.next();
        iterator.next();

        assertThat(iterator.hasNext(), equalTo(false));
        iterator.add("d");
        assertThat(strings.get(2), equalTo("c"));
        assertThat(strings.get(3), equalTo("d"));
        assertThat(strings, hasSize(4));
        assertThat(iterator.hasPrevious(), equalTo(true));
        assertThat(iterator.previousIndex(), equalTo(3));
        assertThat(iterator.previous(), equalTo("d"));
        iterator.previous();
        iterator.remove();
        assertThat(iterator.previous(), equalTo("b"));
        assertThat(iterator.previous(), equalTo("a"));
        assertThat(iterator.hasPrevious(), equalTo(false));
        iterator.set("aa");
        iterator.next();
        iterator.next();
        assertThat(strings, hasSize(3));
        iterator.set(null);
        assertThat(strings, hasSize(3));

        assertThat(strings.get(0), equalTo("aa"));
        assertThat(strings.get(1), equalTo(null));
        assertThat(strings.get(2), equalTo("d"));

    }

    @Test(expected = NoSuchElementException.class)
    public void test_listIterator_failNext() {
        List<String> strings = new DIYarrayList<>();
        ListIterator<String> iterator = strings.listIterator();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void test_listIterator_failPrevious() {
        List<String> strings = new DIYarrayList<>();
        ListIterator<String> iterator = strings.listIterator();
        iterator.previous();
    }

    @Test
    public void test_listIterator_index() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        assertThat(strings, hasSize(3));

        ListIterator<String> iterator = strings.listIterator(2);

        /*
            Unexpected behavior of ArrayList:

            If we create new iterator with Index arg and try to call methods 'set' or 'remove'
            it throws IllegalStateException because 'lastRet' is by default -1.
         */


        iterator.set("cc");
        assertThat(strings, hasSize(3));
        assertThat(strings.get(0), equalTo("a"));
        assertThat(strings.get(1), equalTo("b"));
        assertThat(strings.get(2), equalTo("cc"));


        /*
            Test empty collection
         */
        List<String> strings2 = new DIYarrayList<>();
        ListIterator<String> iterator2 = strings2.listIterator(0);
        iterator2.add("a");
        assertThat(strings2, hasSize(1));
        assertThat(strings2.get(0), equalTo("a"));

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_listIterator_negativeIndex() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        assertThat(strings, hasSize(3));

        try {
            strings.listIterator(-1);
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index must be positive, but was -1"));
            throw expectedException;
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_listIterator_greaterIndex() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        assertThat(strings, hasSize(3));

        try {
            strings.listIterator(10);
        } catch (IndexOutOfBoundsException expectedException) {
            assertThat(expectedException.getMessage(), equalTo("Index: 10, Size: 3"));
            throw expectedException;
        }
    }

    @Test
    public void test_spliterator() {
        List<String> strings = new DIYarrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        assertThat(strings, hasSize(3));

        Spliterator<String> spliterator = strings.spliterator();
    }
}
