// QuantityManagementApp.java

// ---------------- LENGTH (UNCHANGED UC8) ----------------
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

    public double fromBase(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

class Quantity {
    private double value;
    private Unit unit;

    public Quantity(double value, Unit unit) {
        if (!Double.isFinite(value) || unit == null)
            throw new IllegalArgumentException("Invalid input");
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
}

// ---------------- WEIGHT (UC9) ----------------

// ✅ Standalone WeightUnit enum
enum WeightUnit {
    KG(1.0),
    GRAM(0.001),           // 1 g = 0.001 kg
    POUND(0.453592);       // 1 lb ≈ 0.453592 kg

    private final double toKgFactor;

    WeightUnit(double factor) {
        this.toKgFactor = factor;
    }

    public double toBase(double value) {
        return value * toKgFactor; // convert to kg
    }

    public double fromBase(double baseValue) {
        return baseValue / toKgFactor; // convert from kg
    }
}

// ✅ QuantityWeight class
class QuantityWeight {
    private double value;
    private WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (!Double.isFinite(value) || unit == null)
            throw new IllegalArgumentException("Invalid input");
        this.value = value;
        this.unit = unit;
    }

    private double toBase() {
        return unit.toBase(value);
    }

    // Equality
    public boolean equals(QuantityWeight other) {
        if (other == null) return false;
        return Math.abs(this.toBase() - other.toBase()) < 0.0001;
    }

    // Conversion
    public QuantityWeight convertTo(WeightUnit target) {
        if (target == null)
            throw new IllegalArgumentException("Target unit null");

        double base = this.toBase();
        double result = target.fromBase(base);
        return new QuantityWeight(result, target);
    }

    // Addition (default: first operand unit)
    public QuantityWeight add(QuantityWeight other) {
        if (other == null)
            throw new IllegalArgumentException("Other null");

        double sumBase = this.toBase() + other.toBase();
        double result = this.unit.fromBase(sumBase);
        return new QuantityWeight(result, this.unit);
    }

    // Addition with target unit
    public QuantityWeight add(QuantityWeight other, WeightUnit target) {
        if (other == null || target == null)
            throw new IllegalArgumentException("Invalid input");

        double sumBase = this.toBase() + other.toBase();
        double result = target.fromBase(sumBase);
        return new QuantityWeight(result, target);
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }
}

// ---------------- MAIN ----------------
public class QuantityManagementApp {

    public static void main(String[] args) {

        System.out.println("Running UC9: Weight Measurement\n");

        // -------- EQUALITY --------
        QuantityWeight w1 = new QuantityWeight(1, WeightUnit.KG);
        QuantityWeight w2 = new QuantityWeight(1000, WeightUnit.GRAM);

        if (w1.equals(w2))
            System.out.println("Test 1 Passed: 1 kg == 1000 g");

        // -------- CONVERSION --------
        QuantityWeight w3 = new QuantityWeight(1, WeightUnit.POUND);
        QuantityWeight result1 = w3.convertTo(WeightUnit.KG);
        System.out.println("1 lb in kg = " + result1.getValue());

        // -------- ADDITION --------
        QuantityWeight w4 = new QuantityWeight(1, WeightUnit.KG);
        QuantityWeight w5 = new QuantityWeight(500, WeightUnit.GRAM);

        QuantityWeight result2 = w4.add(w5);
        System.out.println("1 kg + 500 g = " + result2.getValue() + " " + result2.getUnit());

        // -------- ADDITION WITH TARGET --------
        QuantityWeight result3 = w4.add(w5, WeightUnit.GRAM);
        System.out.println("1 kg + 500 g = " + result3.getValue() + " g");

        System.out.println("\nUC9 Completed.");
    }
}