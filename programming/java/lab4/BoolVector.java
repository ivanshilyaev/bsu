public class BoolVector {
    int N = 0;
    String vector;

    public BoolVector() {
        N = 0;
        vector = "";
    }

    public BoolVector(int n) {
        assert (n > 0);
        Init(n);
    }

    public static BoolVector Conjunction(BoolVector v1, BoolVector v2) {
        BoolVector res = new BoolVector(v1.N);
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<v1.N; ++i) {
            if (v1.vector.charAt(i) == '1' && v2.vector.charAt(i) == '1')
                sb.append('1');
            else
                sb.append('0');
        }
        res.vector = sb.toString();
        return res;
    }

    public static BoolVector Disjunction(BoolVector v1, BoolVector v2) {
        BoolVector res = new BoolVector(v1.N);
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<v1.N; ++i) {
            if (v1.vector.charAt(i) == '1' || v2.vector.charAt(i) == '1')
                sb.append('1');
            else
                sb.append('0');
        }
        res.vector = sb.toString();
        return res;
    }

    public void Negation() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<N; ++i) {
            if (vector.charAt(i) == '1')
                sb.append('0');
            else
                sb.append('1');
        }
        vector = sb.toString();
    }

    public int countOnes() {
        int count = 0;
        for (int i=0; i<N; ++i) {
            if (vector.charAt(i) == '1')
                ++count;
        }
        return count;
    }

    public int countZeros() {
        int count = 0;
        for (int i=0; i<N; ++i) {
            if (vector.charAt(i) == '0')
                ++count;
        }
        return count;
    }

    private void Init(int n) {
        N = n;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<n; ++i) {
            sb.append(Math.round(Math.random()));
        }
        vector = sb.toString();
    }
}
