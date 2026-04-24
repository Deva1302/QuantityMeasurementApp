// QuantityManagementApp.java

class Feet {
    private double value;

    public Feet(double value) {
        this.value = value;
    }

    public boolean equals(Feet other) {
        if (other == null) return false;
        return Math.abs(this.value - other.value) < 0.0001;
    }
}

class Inch {
    private double value;

    public Inch(double value) {
        this.value = value;
    }

    public boolean equals(Inch other) {
        if (other == null) return false;
        return Math.abs(this.value - other.value) < 0.0001;
    }
}

public class QuantityManagementApp {

    // Static method for Feet comparison
    public static boolean compareFeet(double a, double b) {
        Feet f1 = new Feet(a);
        Feet f2 = new Feet(b);
        return f1.equals(f2);
    }

    // Static method for Inch comparison
    public static boolean compareInch(double a, double b) {
        Inch i1 = new Inch(a);
        Inch i2 = new Inch(b);
        return i1.equals(i2);
    }

    public static void main(String[] args) {

        System.out.println("Running UC2: Feet & Inch Equality\n");

        // -------- FEET TESTS --------
        if (compareFeet(0, 0))
            System.out.println("Feet Test 1 Passed: 0 ft == 0 ft");
        else
            System.out.println("Feet Test 1 Failed");

        if (compareFeet(1, 1))
            System.out.println("Feet Test 2 Passed: 1 ft == 1 ft");
        else
            System.out.println("Feet Test 2 Failed");

        if (!compareFeet(1, 2))
            System.out.println("Feet Test 3 Passed: 1 ft != 2 ft");
        else
            System.out.println("Feet Test 3 Failed");

        if (!new Feet(1).equals(null))
            System.out.println("Feet Test 4 Passed: Null handled");
        else
            System.out.println("Feet Test 4 Failed");


        // -------- INCH TESTS --------
        if (compareInch(0, 0))
            System.out.println("Inch Test 1 Passed: 0 in == 0 in");
        else
            System.out.println("Inch Test 1 Failed");

        if (compareInch(5, 5))
            System.out.println("Inch Test 2 Passed: 5 in == 5 in");
        else
            System.out.println("Inch Test 2 Failed");

        if (!compareInch(5, 10))
            System.out.println("Inch Test 3 Passed: 5 in != 10 in");
        else
            System.out.println("Inch Test 3 Failed");

        if (!new Inch(5).equals(null))
            System.out.println("Inch Test 4 Passed: Null handled");
        else
            System.out.println("Inch Test 4 Failed");

        System.out.println("\nUC2 Completed.");
    }
}