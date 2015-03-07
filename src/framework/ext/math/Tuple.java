package framework.ext.math;

/**
 * Base class for 3d tuples (i.e., points, vectors, and colors).
 *
 * @author arbree
 */
public class Tuple {
    /** The x coordinate of the Tuple3. */
    public float x;
    /** The y coordinate of the Tuple3. */
    public float y;
    /** The z coordinate of the Tuple3. */
    public float z;

    /**
     * Default constructor.
     */
    public Tuple () {
        this(0, 0, 0);
    }

    /**
     * Copy constructor.
     */
    public Tuple (Tuple newTuple) {
        this(newTuple.x, newTuple.y, newTuple.z);
    }

    /**
     * Explicit constructor.
     */
    public Tuple (float newX, float newY, float newZ) {
        x = newX;
        y = newY;
        z = newZ;
    }

    /**
     * Sets this tuple to have the contents of another tuple.
     */
    public void set (Tuple inTuple) {
        x = inTuple.x;
        y = inTuple.y;
        z = inTuple.z;
    }

    /**
     * Set the value of this Tuple3 to the three input values
     */
    public void set (float inX, float inY, float inZ) {
        x = inX;
        y = inY;
        z = inZ;
    }
    
    public void multiply(float scale) {
        x = x * scale;
        y = y * scale;
        z = z * scale;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        return "[" + x + "," + y + "," + z + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (Float.compare(tuple.x, x) != 0) return false;
        if (Float.compare(tuple.y, y) != 0) return false;
        if (Float.compare(tuple.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }
}
