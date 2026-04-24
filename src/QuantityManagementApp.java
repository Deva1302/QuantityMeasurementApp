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
        return valueInFeet / toFeetFactor; // convert from feet to this unit
    }
}

class Quantity {
    private double value;
    private Unit unit;

    public Quantity(double value, Unit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
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

    // ✅ UC5: Conversion method
    public double convertTo(Unit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = this.toBase(); // step 1: convert to feet
        return targetUnit.fromBase(baseValue); // step 2: convert to target
    }

    // Static version (as per your question)
    public static double convert(double value, Unit source, Unit target) {
        Quantity q = new Quantity(value, source);
        return q.convertTo(target);
    }
}

public class QuantityManagementApp {

    public static void main(String[] args) {

        System.out.println("Running UC5: Unit Conversion\n");

        // -------- BASIC CONVERSIONS --------
        System.out.println("1 ft → inch = " + Quantity.convert(1, Unit.FEET, Unit.INCH));
        System.out.println("1 yard → inch = " + Quantity.convert(1, Unit.YARD, Unit.INCH));
        System.out.println("30.48 cm → ft = " + Quantity.convert(30.48, Unit.CM, Unit.FEET));

        // -------- TEST CASES --------
        if (Math.abs(Quantity.convert(1, Unit.FEET, Unit.INCH) - 12) < 0.0001)
            System.out.println("Test 1 Passed: 1 ft = 12 in");

        if (Math.abs(Quantity.convert(1, Unit.YARD, Unit.INCH) - 36) < 0.0001)
            System.out.println("Test 2 Passed: 1 yd = 36 in");

        if (Math.abs(Quantity.convert(30.48, Unit.CM, Unit.FEET) - 1) < 0.0001)
            System.out.println("Test 3 Passed: 30.48 cm = 1 ft");

        // -------- EDGE CASE --------
        try {
            Quantity.convert(Double.NaN, Unit.FEET, Unit.INCH);
            System.out.println("Test 4 Failed");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 4 Passed: Invalid input handled");
        }

        System.out.println("\nUC5 Completed.");
    }
}