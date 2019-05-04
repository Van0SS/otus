package co.eliseev;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class DIYarrayList<E> implements List<E> {

    private Object[] elements;

    private final int DEFAULT_CAPACITY = 10;

    private final double DEFAULT_THRESHOLD = 0.75;

    private int size;

    public DIYarrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public DIYarrayList(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Initial size must be positive, but was " + initialSize);
        }
        elements = new Object[initialSize];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        for (Object element : elements) {
            if (element != null) {
                if (element.equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Iterator<E> iterator() {
        return new MyItr();
    }

    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) elements[i];
            action.accept(element);
        }
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public <T> T[] toArray(T[] a) {
        System.arraycopy(elements, 0, a, 0, size);
        return a;
    }

    public boolean add(E e) {
        checkSize();
        elements[size++] = e;
        return true;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c);

        Object[] objects = c.toArray();
        int newLength = objects.length;
        System.arraycopy(objects, 0, elements, size, newLength);
        size += newLength;
        return true;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        Objects.requireNonNull(c);
        check(index);

        Object[] objects = c.toArray();
        int newLength = objects.length;

        /*
            Prepare old array:
            Move old elements after index to right
         */
        System.arraycopy(elements, index, elements, index + newLength, newLength);

        // Paste new elements to array
        System.arraycopy(objects, 0, elements, index, newLength);
        size += newLength;

        return true;
    }


    public boolean remove(Object o) {

        for (int i = 0; i < elements.length; i++) {

            Object existElement = elements[i];
            if (existElement != null && existElement.equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public E remove(int index) {

        @SuppressWarnings("unchecked") E oldValue = (E) elements[index];

        System.arraycopy(elements, index + 1, elements, index, size);
        elements[size--] = null;

        return oldValue;

    }

    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);

        int counter = 0;
        for (Object o : c) {
            if (indexOf(o) > 0) {
                remove(o);
                counter++;
            }
        }

        return counter > 0;
    }

    public boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);

        int i = 0;
        for (; i < size; ) {
            @SuppressWarnings("unchecked") E element = (E) elements[i];

            if (filter.test(element)) {
                remove(element);
            } else {
                i++;
            }
        }
        return true;
    }

    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);

        /*
            If elements of collection is empty - clear array
         */
        if (c.isEmpty()) {
            Arrays.fill(elements, null);
            size = 0;
            return true;
        }


        int oldIndex = 0;
        int newIndex = 0;
        boolean isChanged = false;
        for (; oldIndex < size; oldIndex++) {
            Object element = elements[oldIndex];
            if (c.contains(element)) {
                elements[newIndex++] = elements[oldIndex];
                isChanged = true;
            }
        }

        // Clean up
        for (; newIndex - 1 < size; newIndex++) { // FIXME newIndex-1 : because of newIndex++ in last operation
            elements[newIndex] = null;
            if (size > 0) size--; // FIXME
        }

        return isChanged;
    }

    @SuppressWarnings("unchecked")
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);

        for (int i = 0; i < size; i++) {
            elements[i] = operator.apply((E) elements[i]);
        }
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        Objects.requireNonNull(c);

        Arrays.sort((E[]) elements, 0, size, c);
    }

    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        check(index);
        return (E) elements[index];
    }


    /**
     * Return old value
     *
     * @param index
     * @param element
     * @return
     */
    public E set(int index, E element) {
        check(index);

        @SuppressWarnings("unchecked") E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }

    public void add(int index, E element) {
        check(index);

        elements[index] = element;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                Object element = elements[i];
                if (element != null) {
                    if (element.equals(o)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                Object element = elements[i];
                if (element != null && element.equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public ListIterator<E> listIterator() {
        return new MyItr();
    }

    public ListIterator<E> listIterator(int index) {
        check(index);
        return new MyItr(index);
    }

    @SuppressWarnings("unchecked")
    public List<E> subList(int fromIndex, int toIndex) {
        check(fromIndex, toIndex);

        int newSize = toIndex - fromIndex;

        DIYarrayList<E> newList = new DIYarrayList<>(newSize);

        for (int i = 0; i < size; i++) {
            if (i >= fromIndex && i < toIndex) {
                newList.add((E) elements[i]);
            }
        }
        return newList;
    }

    public Spliterator<E> spliterator() {
        throw new UnsupportedOperationException("spliterator");
    }

    private void checkSize() {
        if (size + 1 > elements.length * DEFAULT_THRESHOLD) {
            int oldLength = elements.length;
            // >> 1 faster than 'x * 1.5
            int newLength = oldLength + (oldLength >> 1);
            elements = Arrays.copyOf(elements, newLength);
        }
    }

    private void checkElements() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
    }

    private class MyItr implements Iterator<E>, ListIterator<E> {

        int cursor;
        int lastReturn = -1; // -1 if not used

        MyItr() {
        }

        MyItr(int index) {
            cursor = index;
            lastReturn = index;
        }

        public boolean hasNext() {
            return DIYarrayList.this.size > cursor;

        }

        public E next() {
            checkElements();

            return DIYarrayList.this.get(lastReturn = cursor++);
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            checkElements();
            return DIYarrayList.this.get(lastReturn = --cursor);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            System.arraycopy(elements, cursor + 1, elements, cursor, --size);
        }

        @Override
        public void set(E e) {
            elements[lastReturn] = e;
        }

        @Override
        public void add(E e) {

            System.arraycopy(elements, cursor, elements, cursor, size);
            elements[cursor++] = e;
            size++;

        }
    }


    private void check(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be positive, but was " + index);
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void check(int from, int to) {
        if (to < from) {
            throw new IllegalArgumentException("FromIndex in higher than toIndex. From: " + from + ", To: " + to);
        }
        check(from);
        check(to);
    }
}
