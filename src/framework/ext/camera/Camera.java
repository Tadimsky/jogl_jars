package framework.ext.camera;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * Created by Jonno on 2/8/2015.
 */
public class Camera {
    private float[] myPosition;
    private float[] myLookAt;
    private float[] myUp;
    
    private float myXRotation;
    private float myYRotation;

    public Camera() {
        myPosition = new float[] {0f, 1f, 40f};
        myLookAt = new float[] {0,0,0};
        myUp = new float[] {0,1,0};
        myXRotation = 0;
    }
    
    public void apply(GL2 gl, GLU glu) {
        glu.gluLookAt(myPosition[0], myPosition[1], myPosition[2],
                myLookAt[0], myLookAt[1], myLookAt[2],
                myUp[0], myUp[1], myUp[2]);

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
