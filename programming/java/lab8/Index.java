package com;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;

class KeyComp implements Comparator<String> {
    public int compare(String o1, String o2) {
        // right order:
        try {
            Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(o1);
            Date date2 = new SimpleDateFormat("dd.MM.yyyy").parse(o2);
            return date1.compareTo(date2);
        } catch (ParseException e) {
            return o1.compareTo(o2);
        }
    }
}

class KeyCompReverse implements Comparator<String> {
    public int compare(String o1, String o2) {
        // reverse order:
        try {
            Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(o1);
            Date date2 = new SimpleDateFormat("dd.MM.yyyy").parse(o2);
            return date2.compareTo(date1);
        } catch (ParseException e) {
            return o2.compareTo(o1);
        }
    }
}

interface IndexBase {
    String[] getKeys(Comparator<String> comp);

    void put(String key, long value);

    boolean contains(String key);

    LinkedList<Long> get(String key);
}

class IndexOne2One implements Serializable, IndexBase {
    // Unique keys
    // class release version:
    private static final long serialVersionUID = 1L;

    private TreeMap<String, Long> map;

    public IndexOne2One() {
        map = new TreeMap<>();
    }

    public String[] getKeys(Comparator<String> comp) {
        String[] result = map.keySet().toArray(new String[0]);
        Arrays.sort(result, comp);
        return result;
    }

    public void put(String key, long value) {
        map.put(key, value);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public LinkedList<Long> get(String key) {
        long pos = map.get(key);
        LinkedList<Long> list = new LinkedList<Long>();
        list.add(pos);
        return list;
    }
}

class IndexOne2N implements Serializable, IndexBase {
    // Not unique keys
    // class release version:
    private static final long serialVersionUID = 1L;

    private TreeMap<String, LinkedList<Long>> map;

    public IndexOne2N() {
        map = new TreeMap<>();
    }

    public String[] getKeys(Comparator<String> comp) {
        String[] result = map.keySet().toArray(new String[0]);
        Arrays.sort(result, comp);
        return result;
    }

    public void put(String key, long value) {
        LinkedList<Long> list = map.get(key);
        list = Index.InsertValue(list, value);
        map.put(key, list);
    }

    public void put(String keys,   // few keys in one string
                    String keyDel, // key delimiter
                    long value) {
        StringTokenizer st = new StringTokenizer(keys, keyDel);
        int num = st.countTokens();
        for (int i = 0; i < num; ++i) {
            String key = st.nextToken();
            key = key.trim();
            put(key, value);
        }
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public LinkedList<Long> get(String key) {
        return map.get(key);
    }
}

class KeyNotUniqueException extends Exception {
    // class release version:
    private static final long serialVersionUID = 1L;

    public KeyNotUniqueException(String key) {
        super("Key is not unique: " + key);
    }
}

public class Index implements Serializable, Closeable {
    // class release version:
    private static final long serialVersionUID = 1L;

    public static LinkedList<Long> InsertValue(LinkedList<Long> list, long value) {
        if (list == null) {
            list = new LinkedList<>();
        }
        list.add(value);
        return list;
    }

    IndexOne2N fullNames;
    IndexOne2N numbers;
    IndexOne2N dates;

    public void test(PhoneBill bill) throws KeyNotUniqueException {
        assert (bill != null);
        if (fullNames.contains(bill.getFullName())) {
            throw new KeyNotUniqueException(bill.getFullName());
        }
        if (numbers.contains(bill.getNumber())) {
            throw new KeyNotUniqueException(bill.getNumber());
        }
        if (dates.contains(bill.getNumber())) {
            throw new KeyNotUniqueException(bill.getNumber());
        }
    }

    public void put(PhoneBill bill, long value) throws KeyNotUniqueException {
        test(bill);
        fullNames.put(bill.getFullName(), value);
        numbers.put(bill.getNumber(), value);
        dates.put(bill.getDate(), value);
    }

    public Index() {
        fullNames = new IndexOne2N();
        numbers = new IndexOne2N();
        dates = new IndexOne2N();
    }

    public static Index load(String name) throws IOException, ClassNotFoundException {
        Index obj;
        try {
            FileInputStream file = new FileInputStream(name);
            try (ZipInputStream zis = new ZipInputStream(file)) {
                ZipEntry zen = zis.getNextEntry();
                if (!zen.getName().equals(Buffer.zipEntryName)) {
                    throw new IOException("Invalid block format");
                }
                try (ObjectInputStream ois = new ObjectInputStream(zis)) {
                    obj = (Index) ois.readObject();
                }
            }
        } catch (FileNotFoundException e) {
            obj = new Index();
        }
        if (obj != null) {
            obj.save(name);
        }
        return obj;
    }

    private transient String filename = null;

    public void save(String name) {
        filename = name;
    }

    private void saveAs(String name) throws IOException {
        FileOutputStream file = new FileOutputStream(name);
        try (ZipOutputStream zos = new ZipOutputStream(file)) {
            zos.putNextEntry(new ZipEntry(Buffer.zipEntryName));
            zos.setLevel(ZipOutputStream.DEFLATED);
            try (ObjectOutputStream oos = new ObjectOutputStream(zos)) {
                oos.writeObject(this);
                oos.flush();
                zos.closeEntry();
                zos.flush();
            }
        }
    }

    public void close() throws IOException {
        saveAs(filename);
    }
}
