// QuantityManagementApp.java

// ✅ UC8: Standalone Unit enum (Top-level responsibility)
enum Unit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CM(0.0328084);

    private final double toFeetFactor;

    Unit(double factor) {
        this.toFeetFactor = factor;
    }

    // Convert to base unit (feet)
    public double toBase(double value) {
        return value * toFeetFactor;
    }

    // Convert from base unit (feet)
    public double fromBase(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

// ✅ Simplified Quantity class (delegates conversion to Unit)
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
        return unit.toBase(value); // delegation
    }

    // Equality (UC3+)
    public boolean equals(Quantity other) {
        if (other == null) return false;
        return Math.abs(this.toBase() - other.toBase()) < 0.0001;
    }

    // Conversion (UC5)
    public double convertTo(Unit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        double base = this.toBase();
        return targetUnit.fromBase(base);
    }

    // Addition (UC6)
    public Quantity add(Quantity other) {
        if (other == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        double sumBase = this.toBase() + other.toBase();
        double result = this.unit.fromBase(sumBase);
        return new Quantity(result, this.unit);
    }

    // Addition with target unit (UC7)
    public Quantity add(Quantity other, Unit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        double sumBase = this.toBase() + other.toBase();
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

// Main class (unchanged API → backward compatibility)
public class QuantityManagementApp {

    public static void main(String[] args) {

        System.out.println("Running UC8: Refactored Design\n");

        // -------- Equality --------
        Quantity q1 = new Quantity(1, Unit.FEET);
        Quantity q2 = new Quantity(12, Unit.INCH);

        if (q1.equals(q2))
            System.out.println("Equality Passed: 1 ft == 12 in");

        // -------- Conversion --------
        double inches = q1.convertTo(Unit.INCH);
        System.out.println("1 ft in inches = " + inches);

        // -------- Addition UC6 --------
        Quantity result1 = q1.add(q2);
        System.out.println("1 ft + 12 in = " + result1.getValue() + " " + result1.getUnit());

        // -------- Addition UC7 --------
        Quantity result2 = q1.add(q2, Unit.YARD);
        System.out.println("1 ft + 12 in = " + result2.getValue() + " " + result2.getUnit());

        System.out.println("\nUC8 Completed.");
    }
}