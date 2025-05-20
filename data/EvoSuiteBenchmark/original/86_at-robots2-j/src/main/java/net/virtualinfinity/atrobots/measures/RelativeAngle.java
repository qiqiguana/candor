package net.virtualinfinity.atrobots.measures;

/**
 * Represents an amount of rotation.
 *
 * @author Daniel Pitts
 */
public class RelativeAngle implements Comparable<RelativeAngle> {
    public static final RelativeAngle HALF_CIRCLE;
    public static final RelativeAngle FULL_CIRCLE;
    private static final RelativeAngle[] bygreeTable = new RelativeAngle[256];

    static {
        for (int bygree = 0; bygree < 256; ++bygree) {
            final double radians = RelativeAngle.bygreesToRadians(bygree);
            bygreeTable[bygree] = new RelativeBygreeAngle(bygree, (byte) bygree, radians, radiansToDegrees(radians));
        }
        HALF_CIRCLE = RelativeAngle.fromBygrees(128);
        FULL_CIRCLE = RelativeAngle.fromBygrees(256);
    }

    private final double radians;

    private RelativeAngle(double radians) {
        this.radians = radians;
    }

    private double cosine() {
        return Math.cos(radians);
    }

    private double sine() {
        return Math.sin(radians);
    }

    public static RelativeAngle fromRadians(double radians) {
        return new RelativeAngle(radians);
    }

    public RelativeAngle plus(RelativeAngle angle) {
        return fromRadians(getRadians() + angle.getRadians());
    }

    public double getRadians() {
        return radians;
    }

    public RelativeAngle normalize() {
        if (radians >= Math.PI * 2 || radians < 0) {
            return fromRadians(Math.atan2(sine(), cosine()));
        }
        return this;
    }

    public int getBygrees() {
        return (int) Math.round(radians * 128 / Math.PI) & 255;
    }

    public static RelativeAngle fromBygrees(int value) {
        if (value <= 255) {
            if (value >= 0) {
                return bygreeTable[value];
            }
            if (value >= -255) {
                return bygreeTable[-value].negate();
            }
        }
        return new RelativeBygreeAngle(value, (byte) value, bygreesToRadians(value), radiansToDegrees(value));
    }

    private RelativeAngle negate() {
        return fromRadians(-radians);
    }

    private static double bygreesToRadians(int value) {
        return value * Math.PI / 128;
    }

    public byte getSignedBygrees() {
        return (byte) getBygrees();
    }

    public String toString() {
        return getRadians() + "r/" + getBygrees();
    }

    public int compareTo(RelativeAngle angle) {
        return Double.compare(getRadians(), angle.getRadians());
    }

    public double getDegrees() {
        return radiansToDegrees(getRadians());
    }

    private static double radiansToDegrees(double radians) {
        return radians * 180 / Math.PI;
    }

    public double dividedBy(RelativeAngle relativeAngle) {
        return radians / relativeAngle.radians;
    }

    boolean isExactBygrees() {
        return false;
    }

    public RelativeAngle dividedBy(double scale) {
        return fromRadians(radians / scale);
    }

    public RelativeAngle times(int scale) {
        return fromRadians(radians * scale);
    }

    public RelativeAngle times(double scale) {
        return fromRadians(radians * scale);
    }

    private static class RelativeBygreeAngle extends RelativeAngle {
        private final int bygree;
        private final byte signedBygree;
        private final double degrees;

        public RelativeBygreeAngle(int bygree, byte signedBygree, double radians, double degrees) {
            super(radians);
            this.bygree = bygree;
            this.signedBygree = signedBygree;
            this.degrees = degrees;
        }

        @Override
        public RelativeAngle normalize() {
            return bygreeTable[bygree & 255];
        }

        @Override
        public int getBygrees() {
            return bygree;
        }

        @Override
        public byte getSignedBygrees() {
            return signedBygree;
        }

        @Override
        public double getDegrees() {
            return degrees;
        }

        @Override
        boolean isExactBygrees() {
            return true;
        }

        @Override
        public RelativeAngle times(int scale) {
            return fromBygrees(bygree * scale);
        }
    }
}