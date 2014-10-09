/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package anssgeocoder;

import java.io.*;
import java.util.*;

/**
 *
 * @author Khepry Quixote <khepry.quixote@gmail.com>
 */
public class Earthquakes implements Serializable, List<Earthquake> {

    List<Earthquake> list = new ArrayList<>();

    public Earthquakes() {
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<Earthquake> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(Earthquake e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Earthquake> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Earthquake> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Earthquake get(int index) {
        return list.get(index);
    }

    @Override
    public Earthquake set(int index, Earthquake element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, Earthquake element) {
        list.add(index, element);
    }

    @Override
    public Earthquake remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Earthquake> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Earthquake> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<Earthquake> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    public void toCsvFile(File targetFile, String separator, String terminator) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile))) {
            boolean outputHeader = true;
            for (Earthquake earthquake : list) {
                bw.write(earthquake.toCsvString(separator, terminator, outputHeader));
                outputHeader = false;
            }
        }
    }
}
