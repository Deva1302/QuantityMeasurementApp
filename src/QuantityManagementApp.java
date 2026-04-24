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
        return value * toFeetFactor;
    }

    public double fromBase(double valueInFeet) {
        return valueInFeet / toFeetFactor;
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

    // UC6: Addition (same unit as first operand)
    public Quantity add(Quantity other) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }
        double sumBase = this.toBase() + other.toBase();
        double result = this.unit.fromBase(sumBase);
        return new Quantity(result, this.unit);
    }

    // ✅ UC7: Addition with target unit
    public Quantity add(Quantity other, Unit targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        // Step 1: convert both to base (feet)
        double sumBase = this.toBase() + other.toBase();

        // Step 2: convert to target unit
        double result = targetUnit.fromBase(sumBase);

        return new Quantity(result, targetUnit);
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

        System.out.println("Running UC7: Addition with Target Unit\n");

        // -------- TEST CASES --------

        // 1 ft + 12 in = 2 ft → in yards = 0.667 yd
        Quantity q1 = new Quantity(1, Unit.FEET);
        Quantity q2 = new Quantity(12, Unit.INCH);
        Quantity result1 = q1.add(q2, Unit.YARD);

        if (Math.abs(result1.getValue() - 0.6667) < 0.001)
            System.out.println("Test 1 Passed: 1 ft + 12 in = ~0.667 yd");
        else
            System.out.println("Test 1 Failed");


        // 1 yard + 3 ft = 2 yard → in feet = 6 ft
        Quantity q3 = new Quantity(1, Unit.YARD);
        Quantity q4 = new Quantity(3, Unit.FEET);
        Quantity result2 = q3.add(q4, Unit.FEET);

        if (Math.abs(result2.getValue() - 6.0) < 0.0001)
            System.out.println("Test 2 Passed: 1 yd + 3 ft = 6 ft");
        else
            System.out.println("Test 2 Failed");


        // 30.48 cm + 1 ft = 2 ft → in cm = 60.96 cm
        Quantity q5 = new Quantity(30.48, Unit.CM);
        Quantity q6 = new Quantity(1, Unit.FEET);
        Quantity result3 = q5.add(q6, Unit.CM);

        if (Math.abs(result3.getValue() - 60.96) < 0.01)
            System.out.println("Test 3 Passed: 30.48 cm + 1 ft = 60.96 cm");
        else
            System.out.println("Test 3 Failed");


        // Edge case: null target
        try {
            q1.add(q2, null);
            System.out.println("Test 4 Failed");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 4 Passed: Null target handled");
        }

        System.out.println("\nUC7 Completed.");
    }
}