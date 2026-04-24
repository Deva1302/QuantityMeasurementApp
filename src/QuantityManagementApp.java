// QuantityManagementApp.java

enum Unit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CM(0.0328084);

    private final double toFeetFactor;

    Unit(double factor) {
        this.toFeetFactor = factor;
    }

    public double toBase(double value) {
        return value * toFeetFactor; // convert to feet
    }

    public double fromBase(double valueInFeet) {
        return valueInFeet / toFeetFactor; // convert from feet
    }
}

class Quantity {
    private double value;
    private Unit unit;

    public Quantity(double value, Unit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    private double toBase() {
        return unit.toBase(value);
    }

    public boolean equals(Quantity other) {
        if (other == null) return false;
        return Math.abs(this.toBase() - other.toBase()) < 0.0001;
    }

    // UC5: Conversion
    public double convertTo(Unit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        double base = this.toBase();
        return targetUnit.fromBase(base);
    }

    // ✅ UC6: Addition
    public Quantity add(Quantity other) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }

        // Step 1: convert both to base (feet)
        double sumBase = this.toBase() + other.toBase();

        // Step 2: convert back to unit of first operand
        double resultValue = this.unit.fromBase(sumBase);

        return new Quantity(resultValue, this.unit);
    }

    // Static version (optional)
    public static Quantity add(Quantity q1, Quantity q2) {
        return q1.add(q2);
    }

    public double getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }
}

public class QuantityManagementApp {

    public static void main(String[] args) {

        System.out.println("Running UC6: Addition of Length Units\n");

        // -------- TEST CASES --------

        // 1 ft + 12 in = 2 ft
        Quantity q1 = new Quantity(1, Unit.FEET);
        Quantity q2 = new Quantity(12, Unit.INCH);
        Quantity result1 = q1.add(q2);

        if (Math.abs(result1.getValue() - 2.0) < 0.0001)
            System.out.println("Test 1 Passed: 1 ft + 12 in = 2 ft");
        else
            System.out.println("Test 1 Failed");


        // 1 yard + 3 ft = 2 yard
        Quantity q3 = new Quantity(1, Unit.YARD);
        Quantity q4 = new Quantity(3, Unit.FEET);
        Quantity result2 = q3.add(q4);

        if (Math.abs(result2.getValue() - 2.0) < 0.0001)
            System.out.println("Test 2 Passed: 1 yd + 3 ft = 2 yd");
        else
            System.out.println("Test 2 Failed");


        // 2 ft + 24 in = 4 ft
        Quantity q5 = new Quantity(2, Unit.FEET);
        Quantity q6 = new Quantity(24, Unit.INCH);
        Quantity result3 = q5.add(q6);

        if (Math.abs(result3.getValue() - 4.0) < 0.0001)
            System.out.println("Test 3 Passed: 2 ft + 24 in = 4 ft");
        else
            System.out.println("Test 3 Failed");


        // Edge case: null
        try {
            q1.add(null);
            System.out.println("Test 4 Failed");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 4 Passed: Null handled");
        }

        System.out.println("\nUC6 Completed.");
    }
}