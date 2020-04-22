package by.bsu.trading.version02.bean;

public class Result {
    private int id;
    private String resultFileName;
    private String outputFileName;

    public Result(int id, String resultFileName, String outputFileName) {
        this.id = id;
        this.resultFileName = resultFileName;
        this.outputFileName = outputFileName;
    }

    public int getId() {
        return id;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }
}
