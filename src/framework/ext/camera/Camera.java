package framework.ext.camera;

import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;
import com.jogamp.opengl.math.VectorUtil;
import framework.ext.math.Point;
import framework.ext.math.Vector;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * Created by Jonno on 2/8/2015.
 */
public class Camera {
    private Point myPosition;

    Quaternion myOrientation;


    private Vector myLeft;
    private Vector myFront;
    private Vector myUp;


    public Camera() {
        this(new Point(0, 1, 40));
    }
    
    public Camera(Point position) {
        myOrientation = new Quaternion();
        myPosition = new Point(position);

        myLeft = new Vector(-1f, 0, 0);
        myFront = new Vector(0, 0, -1);
        myUp = new Vector(0, 1, 0);
    }

    public Vector getView() {
        Vector front = Vector.Z_AXIS;
        front = rotateByOrientation(front);
        front.negate();
        return front;

    }

    public void apply(GL2 gl, GLU glu) {
        //gl.glTranslatef(myPosition.x, myPosition.y, myPosition.z);
        myOrientation.normalize();

        float[] values = new float[16];
        Matrix4 m = new Matrix4();
        m.multMatrix(myOrientation.toMatrix(values, 0));

        m.transpose();
        m.translate(-myPosition.x, -myPosition.y, -myPosition.z);

        gl.glMultMatrixf(m.getMatrix(), 0);


    }

    public void rotate(float angle, Vector axis) {
        Quaternion q = new Quaternion();
        q.rotateByAngleNormalAxis((float) Math.toRadians(angle), axis.x, axis.y, axis.z);
        myOrientation.mult(q);
        myOrientation.normalize();
    }
    
    public void yaw(float angle) {
        angle = -(float)Math.toRadians(angle);

        Vector axis = rotateByOrientation(Vector.Y_AXIS);

        Quaternion q = new Quaternion();
        q.rotateByAngleNormalAxis(angle, axis.x, axis.y, axis.z);

        myLeft = rotateByOrientation(myLeft);
        myFront = rotateByOrientation(myFront);

        myOrientation = q.mult(myOrientation);

        //myOrientation.mult(q);
    }

    public void roll(float angle) {
        angle = -(float)Math.toRadians(angle);

        Vector axis = rotateByOrientation(Vector.Z_AXIS);

        Quaternion q = new Quaternion();
        q.rotateByAngleNormalAxis(angle, axis.x, axis.y, axis.z);

        myLeft = rotateByOrientation(myLeft);
        myUp = rotateByOrientation(myUp);
        myOrientation = q.mult(myOrientation);
        //myOrientation.mult(q);
    }

    public void pitch(float angle) {
        angle = -(float)Math.toRadians(angle);

        Vector axis = rotateByOrientation(Vector.X_AXIS);

        Quaternion q = new Quaternion();
        q.rotateByAngleNormalAxis(angle, axis.x, axis.y, axis.z);

        myUp = rotateByOrientation(myUp);
        myFront = rotateByOrientation(myFront);
        myOrientation = q.mult(myOrientation);
        //myOrientation.mult(q);
    }

    private Vector rotateByOrientation(Vector v) {
        float[] axis = new float[3];
        float[] vals = {v.x, v.y, v.z};
        myOrientation.rotateVector(axis, 0, vals, 0);

        Vector ret = new Vector(axis[0], axis[1], axis[2]);
        ret.normalize();
        return ret;
    }

    /**
     * Moves a certain distance in the direction that the camera is facing.
     * @param distance The distance to move.
     */
    public void move(float distance) {
        Vector movement = getView();
        movement.scale(distance);
        myPosition = myPosition.add(movement);
    }
    
    public Point getPosition() {
        return myPosition;
    }

    public void moveTowards(Point p) {

        Vector diff = p.sub(myPosition);
        Vector dir = diff.normalize();

        myOrientation = new Quaternion();

        float yaw = (float)Math.toDegrees(Math.atan2(dir.x, -dir.z));
        float pitch = (float)Math.toDegrees(Math.atan2(dir.z, dir.y));
        yaw(yaw);
        //pitch(pitch);

        //move(diff.length());
        this.myPosition = p;
    }
}
