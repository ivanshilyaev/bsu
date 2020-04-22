package by.bsu.trading.version02.bean;

import java.util.List;

public class TradingData {
    private int id;
    private int m;
    private int n;
    private int k;
    private List<Double> prices;
    private String inputFileName;
    private String outputFileName;

    public TradingData(int id, int m, int n, int k, List<Double> prices,
                       String inputFileName, String outputFileName) {
        this.id = id;
        this.m = m;
        this.n = n;
        this.k = k;
        this.prices = prices;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public TradingData(int m, int n, int k, List<Double> prices, String inputFileName) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.prices = prices;
        this.inputFileName = inputFileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
