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

    private float myXRotation;
    private float myYRotation;

    public Camera() {
        this(new Point(0, 1, 40), new Point(0, 0, 0), new Vector(0, 1, 0));        
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
        
        gl.glMatrixRotatedEXT();

        gl.glRotatef(myYRotation, 1f, 0f, 0f);
        gl.glRotatef(myXRotation, 0f, 1f, 0f);
    }

    public void turn(float xDegrees, float yDegrees) {
        myXRotation += xDegrees;
        myYRotation += yDegrees;
    }

    public void moveForward(float amnt) {
        myPosition[2] -= amnt;
    }

    public void pan(float x, float y) {
        myPosition[0] += x;
        myPosition[1] += y;

        myLookAt[0] += x;
        myLookAt[1] += y;
    }

}
