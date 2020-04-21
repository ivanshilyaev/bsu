package by.bsu.trading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.Callable;

public class OutputChecker implements Callable<Boolean> {
    private List<String> result;
    private String out;

    public OutputChecker(List<String> result, String out) {
        this.result = result;
        this.out = out;
    }

    @Override
    public Boolean call() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(out));
        String line = bufferedReader.readLine();
        int i = 0;
        while (line != null) {
            if (!line.equals(result.get(i))) return false;
            ++i;
            line = bufferedReader.readLine();
        }
        return true;
    }
}
