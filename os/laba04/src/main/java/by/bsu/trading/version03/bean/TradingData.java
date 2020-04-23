package by.bsu.trading.version03.bean;

import java.util.List;

public class TradingData {
    private String testName;
    private int m;
    private int n;
    private int k;
    private List<Double> prices;
    private String inputFileName;
    private String outputFileName;
    private String resultFileName;

    public TradingData(String testName, int m, int n, int k, List<Double> prices,
                       String inputFileName, String outputFileName) {
        this.testName = testName;
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
        outputFileName = "";
        resultFileName = "";
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
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

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }
}