package com;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyLom implements Comparable<MyLom> {
    private int num;
    private ArrayList<MyXY> lom;
    private double length;

    public MyLom(ArrayList<MyXY> lom, int num) {
        this.lom = lom;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<MyXY> getLom() {
        return lom;
    }

    public double getLength() {
        return length;
    }

    public static MyLom parseMyLom(String line, int num) {
        StringTokenizer tokenizer = new StringTokenizer(line, ", \t\r\n");
        ArrayList<MyXY> list = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            MyXY o = MyXY.parseMyXY(word);
            if (o != null)
                list.add(o);
            else
                return null;
        }
        if (!list.isEmpty())
            return new MyLom(list, num);
        return null;
    }

    public void countLength() {
        length = 0;
        if (lom.size() == 1)
            return;
        for (int i=0; i<lom.size() - 1; ++i) {
            length += Math.sqrt(Math.pow(lom.get(i+1).getX() - lom.get(i).getX(), 2)
                    + Math.pow(lom.get(i+1).getY() - lom.get(i).getY(), 2));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MyXY e : lom) {
            sb.append(e.toString());
            sb.append(" ");
        }
        return "num: " + num + ", length: " + length + ", lom: " + sb.toString();
    }

    @Override
    public int compareTo(MyLom myLom) {
        return Double.compare(myLom.length, this.length);
    }
}
