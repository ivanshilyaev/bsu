// variant 12

public class Test {
    static final int NUM = 10;
    static final int LENGTH = 8;

    public static void main(String[] args) {
        BoolVector[] array = new BoolVector[NUM];
        for (int i=0; i<10; ++i) {
            array[i] = new BoolVector(LENGTH);
        }
        System.out.println("Original vectors:");
        for (int i=0; i<10; ++i) {
            System.out.println(array[i].vector);
        }
        System.out.println("Conjunction of vectors (1 and 10, 2 and 9, etc.):");
        for (int i=0; i<5; ++i) {
            BoolVector res = BoolVector.Conjunction(array[i], array[NUM-1-i]);
            System.out.printf("%s ^ %s = %s \n", array[i].vector, array[NUM-1-i].vector, res.vector);
        }
        System.out.println("Disjunction of vectors (1 and 10, 2 and 9, etc.):");
        for (int i=0; i<5; ++i) {
            BoolVector res = BoolVector.Disjunction(array[i], array[NUM-1-i]);
            System.out.printf("%s v %s = %s \n", array[i].vector, array[NUM-1-i].vector, res.vector);
        }
        System.out.println("Negation of original vectors:");
        for (int i=0; i<10; ++i) {
            array[i].Negation();
            System.out.println(array[i].vector);
            array[i].Negation(); // returning vectors back into the original state
        }
        System.out.println("Counting ones and zeros:");
        for (int i=0; i<10; ++i) {
            System.out.printf("Vector: %s, ones: %d, zeros: %d \n", array[i].vector, array[i].countOnes(), array[i].countZeros());
        }
    }
}
