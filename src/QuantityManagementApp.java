// QuantityManagementApp.java

enum Unit {
    FEET(1.0),
    INCH(1.0 / 12.0); // 1 inch = 1/12 feet

    private final double conversionFactor;

    Unit(double factor) {
        this.conversionFactor = factor;
    }

    public double toBase(double value) {
        return value * conversionFactor; // convert to feet (base unit)
    }
}

class Quantity {
    private double value;
    private Unit unit;

    public Quantity(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    // Convert to base unit (feet)
    private double toBase() {
        return unit.toBase(value);
    }

    // Generic equality (works across units)
    public boolean equals(Quantity other) {
        if (other == null) return false;
        return Math.abs(this.toBase() - other.toBase()) < 0.0001;
    }
}

public class QuantityManagementApp {

    // Static method for comparison (as per flow)
    public static boolean compare(double v1, Unit u1, double v2, Unit u2) {
        Quantity q1 = new Quantity(v1, u1);
        Quantity q2 = new Quantity(v2, u2);
        return q1.equals(q2);
    }

    public static void main(String[] args) {

        System.out.println("Running UC3: Generic Quantity Class (DRY)\n");

        // -------- SAME UNIT TESTS --------
        if (compare(1, Unit.FEET, 1, Unit.FEET))
            System.out.println("Test 1 Passed: 1 ft == 1 ft");
        else
            System.out.println("Test 1 Failed");

        if (!compare(1, Unit.FEET, 2, Unit.FEET))
            System.out.println("Test 2 Passed: 1 ft != 2 ft");
        else
            System.out.println("Test 2 Failed");

        if (compare(12, Unit.INCH, 12, Unit.INCH))
            System.out.println("Test 3 Passed: 12 in == 12 in");
        else
            System.out.println("Test 3 Failed");


        // -------- CROSS UNIT TESTS --------
        if (compare(1, Unit.FEET, 12, Unit.INCH))
            System.out.println("Test 4 Passed: 1 ft == 12 in");
        else
            System.out.println("Test 4 Failed");

        if (!compare(1, Unit.FEET, 10, Unit.INCH))
            System.out.println("Test 5 Passed: 1 ft != 10 in");
        else
            System.out.println("Test 5 Failed");


        // -------- EDGE CASE --------
        Quantity q = new Quantity(1, Unit.FEET);
        if (!q.equals(null))
            System.out.println("Test 6 Passed: Null handled");
        else
            System.out.println("Test 6 Failed");

        System.out.println("\nUC3 Completed.");
    }
}