package src;

import java.util.*;

public class Payment implements Comparable<Payment>, Iterable<String>, Iterator<String> {
    private String FIO = "Empty";
    private int year = 1970;
    private int salary = 0;
    private int numOfDays = 0;
    private int numOfWorkDays = 0;
    private int allowance = 0;
    private int taxes = 0;
    private int accruedSum = 0;
    private int restrainedSum = 0;

    // areas container
    private String[] areas = null;

    // iterator
    private int iterator_idx = 0;

    public static class ArgException extends Exception {
        private static final long serialVersionUID = 1L;

        ArgException(String arg) {
            super("Invalid argument: " + arg);
        }
    }

    private static final String[] names = {
                    "FIO",
                    "Year of employment",
                    "Salary",
                    "Days to work",
                    "Worked days",
                    "Allowance",
                    "Taxes",
                    "Accrued sum",
                    "Restrained sum"
    };
    private static String[] formatStr =  {
            "%-20s",
            "%-20s",
            "%-8s",
            "%-14s",
            "%-13s",
            "%-11s",
            "%-7s",
            "%-13s",
            "%-16s"
    };

    private static int sortBy = 0;
    private static int getSortBy() {
        return sortBy;
    }
    static void setSortBy(int value) {
        if (value >= names.length || value < 0)
            throw new IndexOutOfBoundsException();
        sortBy = value;
    }
    static String getSortByName() {
        return Payment.names[Payment.getSortBy()];
    }

    // check if data is valid
    private boolean validFIO(String str) {
        return str != null && str.length() > 0;
    }
    private boolean validYear(String str) {
        try {
            int y = Integer.parseInt(str);
            return y>=1970 && y<2020;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean validSalary(String str) {
        try {
            int s = Integer.parseInt(str);
            return s>0;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean validNumOfDays(String str) {
        try {
            int y = Integer.parseInt(str);
            return y>0 && y<32;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean validAllowance(String str) {
        try {
            int y = Integer.parseInt(str);
            return y>=0 && y<=100;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    // transforming
    private void areaToData (int idx) {
        switch (idx) {
            case 0: FIO = areas[idx]; break;
            case 1: year = Integer.parseInt(areas[idx]); break;
            case 2: salary = Integer.parseInt(areas[idx]); break;
            case 3: numOfDays = Integer.parseInt(areas[idx]); break;
            case 4: numOfWorkDays = Integer.parseInt(areas[idx]); break;
            case 5: allowance = Integer.parseInt(areas[idx]); break;
            case 6: taxes = Integer.parseInt(areas[idx]); break;
            case 7: accruedSum = Integer.parseInt(areas[idx]); break;
            case 8: restrainedSum = Integer.parseInt(areas[idx]); break;
        }
    }
    private void dataToArea (int idx) {
        switch (idx) {
            case 0: areas[idx] = FIO; break;
            case 1: areas[idx] = Integer.toString(year); break;
            case 2: areas[idx] = Integer.toString(salary); break;
            case 3: areas[idx] = Integer.toString(numOfDays); break;
            case 4: areas[idx] = Integer.toString(numOfWorkDays); break;
            case 5: areas[idx] = Integer.toString(allowance); break;
            case 6: areas[idx] = Integer.toString(taxes); break;
            case 7: areas[idx] = Integer.toString(accruedSum); break;
            case 8: areas[idx] = Integer.toString(restrainedSum); break;
        }
    }

    // indexator:
    private int length() {
        return areas.length;
    }
    public String getArea(int idx) {
        if (idx >= length() || idx < 0) {
            throw new IndexOutOfBoundsException();
        }
        return areas[idx];
    }
    private void setArea(int idx, String value) throws ArgException {
        if (idx >= length() || idx < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (( idx == 0 && !validFIO(value)) ||
                (idx == 1 && !validYear(value)) ||
                ((idx ==2 || idx ==7 || idx == 8) && !validSalary(value)) ||
                ((idx ==3 || idx ==4) && !validNumOfDays(value)) ||
                ((idx ==5 || idx ==6) && !validAllowance(value))) {
            throw new ArgException(value);
        }
        areas[idx] = value;
        areaToData(idx);
    }

    // Iterable<String>, Iterator<String>
    public Iterator<String> iterator() {
        reset();
        return this;
    }
    private void reset() {
        iterator_idx = 0;
    }
    public boolean hasNext() {
        return iterator_idx < areas.length;
    }
    public void remove() {
        //
    }
    public String next() {
        if (iterator_idx < areas.length) {
            return areas[iterator_idx++];
        }
        reset();
        return null;
    }

    // Comparable<src.Payment>
    public int compareTo(Payment cy) {
        switch (sortBy) {
            case 0: return FIO.compareTo(cy.areas[sortBy]);
            case 1: return Integer.compare(year, Integer.parseInt(cy.areas[sortBy]));
            case 2: return Integer.compare(salary, Integer.parseInt(cy.areas[sortBy]));
            case 3: return Integer.compare(numOfDays, Integer.parseInt(cy.areas[sortBy]));
            case 4: return Integer.compare(numOfWorkDays, Integer.parseInt(cy.areas[sortBy]));
            case 5: return Integer.compare(allowance, Integer.parseInt(cy.areas[sortBy]));
            case 6: return Integer.compare(taxes, Integer.parseInt(cy.areas[sortBy]));
            case 7: return Integer.compare(accruedSum, Integer.parseInt(cy.areas[sortBy]));
            case 8: return Integer.compare(restrainedSum, Integer.parseInt(cy.areas[sortBy]));
        }
        return 0;
    }
    // toString
    public String toString() {
        if (areas == null) {
            return " | | | | | | | | ";
        }
        StringBuilder res = new StringBuilder(areas[0]);
        for (int i=1; i<areas.length; ++i) {
            res.append("|").append(areas[i]);
        }
        return res.toString();
    }

    // constructors
    private void setup(String[] args) throws ArgException {
        if (args == null) {
            throw new ArgException("Null pointer passed for args");
        }
        if (args.length < 2 || args.length > names.length) {
            throw new ArgException(Arrays.toString(args));
        }
        areas = new String[names.length];
        int i = 0;
        for (; i < args.length; ++i) {
            if (args[i].length() == 0)
                dataToArea(i);
            else
                setArea(i, args[i]);
        }
        while (i < names.length) {
            dataToArea(i++);
        }
    }
    public Payment(String str) throws ArgException {
        if (str.isEmpty()) {
            throw new ArgException("Null pointer passed for str");
        }
        int num = 1, idx = 0, idxFrom = 0;
        while ((idx = str.indexOf( '|', idxFrom )) != -1) {
            idxFrom = idx + 1;
            ++num;
        }
        // allocate array
        String[] args = new String[num];
        // put all tokens to array
        idxFrom = 0; num = 0;
        while ((idx = str.indexOf( '|', idxFrom )) != -1) {
            args[num++] = str.substring(idxFrom, idx);
            idxFrom = idx + 1;
        }
        args[num] = str.substring(idxFrom);
        setup(args);
    }

    public Payment(String...args) throws ArgException {
        setup(args);
    }

    private static String format(Iterable<String> what) {
        StringBuilder result = new StringBuilder();
        int idx = 0;
        for(String str : what) {
            result.append(String.format(formatStr[idx++], str));
        }
        return result.toString();
    }

    static String format() {
        return Payment.format(Arrays.asList(Payment.names));
    }

    static String format(Payment cn) {
        return Payment.format(((Iterable<String>) cn));
    }

    // methods
    public int getAccruedSum() {
        return accruedSum;
    }
    public int getRestrainedSum() {
        return restrainedSum;
    }
    public int getMoneyOnHand() {
        return accruedSum-restrainedSum;
    }
}