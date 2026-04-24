// QuantityManagementApp.java

class Quantity {
    private double value; // value in feet

    public Quantity(double value) {
        this.value = value;
    }

    // Equality check
    public boolean equals(Quantity other) {
        if (other == null) return false;
        return Math.abs(this.value - other.value) < 0.0001;
    }
}

public class QuantityManagementApp {

    public static void main(String[] args) {

        System.out.println("Running UC1: Feet Measurement Equality\n");

        // Test 1: 0 ft == 0 ft
        Quantity q1 = new Quantity(0);
        Quantity q2 = new Quantity(0);

        if (q1.equals(q2)) {
            System.out.println("Test 1 Passed: 0 ft == 0 ft");
        } else {
            System.out.println("Test 1 Failed");
        }

        // Test 2: 1 ft == 1 ft
        Quantity q3 = new Quantity(1);
        Quantity q4 = new Quantity(1);

        if (q3.equals(q4)) {
            System.out.println("Test 2 Passed: 1 ft == 1 ft");
        } else {
            System.out.println("Test 2 Failed");
        }

        // Test 3: 1 ft != 2 ft
        Quantity q5 = new Quantity(1);
        Quantity q6 = new Quantity(2);

        if (!q5.equals(q6)) {
            System.out.println("Test 3 Passed: 1 ft != 2 ft");
        } else {
            System.out.println("Test 3 Failed");
        }

        // Test 4: Null check
        if (!q5.equals(null)) {
            System.out.println("Test 4 Passed: Null handled correctly");
        } else {
            System.out.println("Test 4 Failed");
        }

        System.out.println("\nUC1 Completed.");
    }
}