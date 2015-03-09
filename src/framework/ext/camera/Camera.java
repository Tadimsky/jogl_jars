package framework.ext.camera;

import framework.ext.math.Point;
import framework.ext.math.Vector;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * Created by Jonno on 2/8/2015.
 */
public class Camera {
    private Point myPosition;
    private Point myLookAt;
    private Vector myUp;

    private float myRollAmount;

    public Camera() {
        this(new Point(0, 1, 40), new Point(0, 1, 41), Vector.Y_AXIS);
    }
    
    public Camera(Point position){
        this(position, position.add(new Vector(0, 0, 1)), Vector.Y_AXIS);
    }
    
    public Camera(Point position, Point lookAt, Vector up) {
        myPosition = new Point(position);
        myLookAt = new Point(lookAt);
        
        myUp = new Vector(up);
        myUp.normalize();
    }

    public void apply(GL2 gl, GLU glu) {
        glu.gluLookAt(myPosition.x, myPosition.y, myPosition.z,
                myLookAt.x, myLookAt.y, myLookAt.z,
                myUp.x, myUp.y, myUp.z);
    }
    
    public void rotate(float angle, Vector axis) {
        // Axis-Angle rotation: http://www.gametutorials.com/tutorial/camera-part2-2/
        Vector viewDir = getViewVector();
        viewDir.normalize();
        
        float radAngle = (float)(angle * Math.PI / 180.0f);
        
        float cos = (float)Math.cos(radAngle);
        float sin = (float)Math.sin(radAngle);
        
        Vector newLookAt = new Vector();
        
        newLookAt.x = (cos + (1 - cos) * (axis.x  * axis.x)) * viewDir.x;
        newLookAt.x += (((1 - cos) * axis.x  * axis.y) - (axis.z * sin)) * viewDir.y;
        newLookAt.x += (((1 - cos) * axis.x  * axis.z) + (axis.y * sin)) * viewDir.z;

        newLookAt.y = (((1 - cos) * axis.x  * axis.y) + (axis.z * sin)) * viewDir.x;
        newLookAt.y += (cos + ((1 - cos) * axis.y * axis.y)) * viewDir.y;
        newLookAt.y += (((1 - cos) * axis.y  * axis.z) - (axis.x * sin)) * viewDir.z;

        newLookAt.z = (((1 - cos) * axis.x  * axis.z) - (axis.y * sin)) * viewDir.x;
        newLookAt.z += ((1 - cos) * axis.y  * axis.z) + (axis.x * sin) * viewDir.y;
        newLookAt.z += (cos + ((1 - cos) * axis.z * axis.z)) * viewDir.z;
        
        myLookAt = myPosition.add(newLookAt);
    }
    
    public void yaw(float angle) {
        rotate(angle, myUp);        
    }

    public void roll(float angle) {
        //rotate(angle, getViewVector());
    }

    public void pitch(float angle) {
        rotate(angle, getPitchVector());
    }
    
    public Vector getViewVector() {
        return myLookAt.sub(myPosition).normalize();
    }

    public Vector getPitchVector() {
        Vector v = getViewVector();
        v = v.cross(myUp).normalize();
        return v;
    }

    /**
     * Moves a certain distance in the direction that the camera is facing.
     * @param distance The distance to move.
     */
    public void move(float distance) {
        Vector direction = getViewVector();
        myPosition.x += direction.x * distance;
        myPosition.y += direction.y * distance;
        myPosition.z += direction.z * distance;

        myLookAt.x += direction.x * distance;
        myLookAt.y += direction.y * distance;
        myLookAt.z += direction.z * distance;
    }
    
    public Point getPosition() {
        return myPosition;
    }
}
