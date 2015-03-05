package framework.ext.math;

/**
 * A ray is an origin and a direction.
 *
 * @author rcd
 */
public class Ray {
    /**
     * This quantity represents a "small amount" to handle comparisons in floating point
     * computations. It is often useful to have one global value across the ray tracer that stands
     * for a "small amount".
     */
    public static final double EPSILON = 1e-6;

    /** The starting point of the ray. */
    private Point start;

    /** The normalized direction in which the ray travels. */
    public Vector direction;

    /**
     * Default constructor.
     */
    public Ray () {
        this(new Point(), new Vector(1, 0, 0));
    }

    /**
     * Copy constructor.
     *
     * This constructor simply calls the explicit constructor with the necessary fields from the
     * input as parameters.
     *
     * @param other The ray to copy.
     */
    public Ray (Ray other) {
        this(other.getOrigin(), other.getDirection());
    }

    /**
     * The explicit constructor. This is the only constructor with any real code in it. Values
     * should be set here, and any variables that need to be calculated should be done here.
     *
     * @param newOrigin The origin of the new ray.
     * @param newDirection The direction of the new ray.
     */
    public Ray (Point origin, Vector direction) {
        setOrigin(new Point(origin));
        setDirection(new Vector(direction));
    }

    /**
     * This method returns the point in space which corresponds to a distance t units along the ray.
     *
     * @param t The distance along the ray.
     * @return The location in space at that distance along the ray.
     */
    public Point evaluate (double t) {
        return new Point(start).scaleAdd(t, direction);
    }

    /**
     * @return Returns the origin.
     */
    public Point getOrigin () {
        return start;
    }

    /**
     * @return Returns the direction.
     */
    public Vector getDirection () {
        return direction;
    }

    /**
     * @param origin The origin to set.
     */
    private void setOrigin (Point origin) {
        start = origin;
    }

    /**
     * @param direction The direction to set.
     */
    private void setDirection (Vector direction) {
        this.direction = direction;
        normalize();
    }

    /**
     * Normalize the direction vector of this ray.
     */
    public Ray normalize () {
        direction.normalize();
        return this;
    }
    
    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        return "start = " + start + "\ndirection = " + direction;
    }
}
