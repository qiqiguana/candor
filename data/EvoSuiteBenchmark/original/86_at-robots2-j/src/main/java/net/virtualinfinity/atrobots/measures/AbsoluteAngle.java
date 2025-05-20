package net.virtualinfinity.atrobots.measures;

/**
 * Represents an exact angle.
 *
 * @author Daniel Pitts
 * @see net.virtualinfinity.atrobots.measures.RelativeAngle
 */
public class AbsoluteAngle {
    private static final AbsoluteAngle[] bygreeTable = new AbsoluteAngle[256];
    private static final double RADIANS_PER_BYGREE = Math.PI / 128.0;
    private static final double BYGREES_PER_RADIANS = 128 / Math.PI;
    private static final int BYGREE_MASK = 255;
    private static final double FULL_CIRCLE_RADIANS = Math.PI * 2;

    static {
        for (int bygrees = 0; bygrees < bygreeTable.length; ++bygrees) {
            bygreeTable[bygrees] = new AbsoluateBygreeAngle(bygrees);
        }
    }

    protected boolean isExactBygrees() {
        return false;
    }

    private final double radians;

    private AbsoluteAngle(double radians) {
        if (radians < 0) {
            radians += FULL_CIRCLE_RADIANS;
        } else if (radians >= FULL_CIRCLE_RADIANS) {
            radians -= FULL_CIRCLE_RADIANS;
        }
        this.radians = radians;
    }

    public double cosine() {
        return Math.cos(radians);
    }

    public double sine() {
        return Math.sin(radians);
    }

    public AbsoluteAngle counterClockwise(RelativeAngle angle) {
        return fromRadians(getRadians() + angle.getRadians());
    }

    public double getRadians() {
        return radians;
    }

    public double getNormalizedRadians() {
        if (radians >= FULL_CIRCLE_RADIANS || radians < 0) {
            return Math.atan2(sine(), cosine());
        }
        return radians;
    }

    public Vector toUnitVector() {
        return toVector(1);
    }

    public Vector toVector(double magnitude) {
        return Vector.createPolar(this, magnitude);
    }

    public int getBygrees() {
        return radiansToBygrees(getRadians());
    }

    public AbsoluteAngle clockwise(RelativeAngle angle) {
        return fromRadians(getRadians() - angle.getRadians());
    }

    public byte getSignedBygrees() {
        return (byte) getBygrees();
    }

    public boolean isClockwiseCloser(AbsoluteAngle angle) {
        return getAngleCounterClockwiseTo(angle).compareTo(RelativeAngle.HALF_CIRCLE) < 0;
    }

    public String toString() {
        return getNormalizedRadians() + "r/" + getBygrees();
    }

    public RelativeAngle counterClockwiseFromStandardOrigin() {
        return RelativeAngle.fromRadians(getRadians());
    }

    public double getDegrees() {
        return getRadians() / Math.PI * 180;
    }

    public RelativeAngle getAngleCounterClockwiseTo(AbsoluteAngle counterClockwiseValue) {
        final double difference = getNormalizedRadians() - counterClockwiseValue.getNormalizedRadians();
        return RelativeAngle.fromRadians(difference < 0 ? difference + Math.PI * 2.0 : difference);
    }

    public Vector projectAngle(Vector vector) {
        final Vector unit = toVector(1);
        return unit.times(unit.dot(vector));
    }

    public static AbsoluteAngle fromBygrees(int value) {
        return bygreeTable[value & BYGREE_MASK];
    }

    public static AbsoluteAngle fromCartesian(double x, double y) {
        return fromRadians(Math.atan2(y, x));
    }

    public static AbsoluteAngle fromRadians(double radians) {
        return new AbsoluteAngle(radians);
    }

    private static int radiansToBygrees(double radians) {
        return (int) Math.round(64 + (radians * BYGREES_PER_RADIANS)) & BYGREE_MASK;
    }

    private static double bygreeToRadians(int bygrees) {
        return (bygrees - 64) * RADIANS_PER_BYGREE;
    }

    public AbsoluteAngle getSupplementary() {
        return fromRadians(Math.PI + getRadians());
    }

    private static class AbsoluateBygreeAngle extends AbsoluteAngle {
        private final double cosine;
        private final double sine;
        private final int bygrees;
        private final byte signedBygrees;
        private final double normalizedRadians;
        private final double degrees;
        private final RelativeAngle counterClockwiseFromStandardOrigin;

        public AbsoluateBygreeAngle(int bygrees) {
            super(bygreeToRadians(bygrees));
            final AbsoluteAngle template = AbsoluteAngle.fromRadians(bygreeToRadians(bygrees));
            switch (bygrees) {
                case 0:
                    this.cosine = 0;
                    this.sine = -1;
                    break;
                case 128:
                    this.cosine = 0;
                    this.sine = 1;
                    break;
                case 192:
                    this.cosine = -1;
                    this.sine = 0;
                    break;
                default:
                    this.cosine = template.cosine();
                    this.sine = template.sine();
                    break;
            }
            this.bygrees = bygrees;
            this.signedBygrees = template.getSignedBygrees();
            this.normalizedRadians = template.getNormalizedRadians();
            this.degrees = template.getDegrees();
            counterClockwiseFromStandardOrigin = RelativeAngle.fromBygrees(64 - this.bygrees);
        }

        @Override
        public double cosine() {
            return cosine;
        }

        @Override
        public double sine() {
            return sine;
        }

        @Override
        public int getBygrees() {
            return bygrees;
        }

        @Override
        public byte getSignedBygrees() {
            return signedBygrees;
        }

        @Override
        protected boolean isExactBygrees() {
            return true;
        }

        @Override
        public double getNormalizedRadians() {
            return normalizedRadians;
        }

        @Override
        public AbsoluteAngle counterClockwise(RelativeAngle angle) {
            if (angle.isExactBygrees()) {
                return fromBygrees(getBygrees() + angle.getBygrees());
            }
            return super.counterClockwise(angle);
        }

        @Override
        public AbsoluteAngle clockwise(RelativeAngle angle) {
            if (angle.isExactBygrees()) {
                return fromBygrees(getBygrees() - angle.getBygrees());
            }
            return super.clockwise(angle);
        }

        @Override
        public double getDegrees() {
            return degrees;
        }

        @Override
        public RelativeAngle getAngleCounterClockwiseTo(AbsoluteAngle counterClockwiseValue) {
            if (counterClockwiseValue.isExactBygrees()) {
                final int difference = getBygrees() - counterClockwiseValue.getBygrees();
                return RelativeAngle.fromBygrees(difference < 0 ? difference + 256 : difference);
            }
            return super.getAngleCounterClockwiseTo(counterClockwiseValue);
        }

        @Override
        public RelativeAngle counterClockwiseFromStandardOrigin() {
            return counterClockwiseFromStandardOrigin;
        }

        @Override
        public AbsoluteAngle getSupplementary() {
            return fromBygrees(bygrees + 128);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbsoluteAngle angle = (AbsoluteAngle) o;

        if (Double.compare(angle.radians, radians) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = radians != +0.0d ? Double.doubleToLongBits(radians) : 0L;
        return (int) (temp ^ (temp >>> 32));
    }
}
