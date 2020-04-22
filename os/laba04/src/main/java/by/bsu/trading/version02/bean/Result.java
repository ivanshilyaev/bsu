package by.bsu.trading.version02.bean;

public class Result {
    private String testName;
    private String resultFileName;
    private String outputFileName;

    public Result(String testName, String resultFileName, String outputFileName) {
        this.testName = testName;
        this.resultFileName = resultFileName;
        this.outputFileName = outputFileName;
    }

    public String getTestName() {
        return testName;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }
}
