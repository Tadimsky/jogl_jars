package framework.ext;

import com.jogamp.opengl.util.gl2.GLUT;
import framework.ext.input.KeyMap;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * Created by Jonno on 3/4/2015.
 */
public abstract class VisualComponent {
    /**
     * Draw all of the objects to display.
     */
    public abstract void display (GL2 gl, GLU glu, GLUT glut);

    /**
     * Animate the component.
     */
    public abstract void animate (GL2 gl, GLU glu, GLUT glut);

    /**
     * Process the Key Map
     * @param keyMap
     */
    public void processKeys(KeyMap keyMap) {
        // Not required, but potentially useful.
    }

    /**
     * Initialize the object, with access to OpenGL context
     * @param gl
     * @param glu
     * @param glut
     */
    public void init(GL2 gl, GLU glu, GLUT glut) {
        // Not required, but potentially useful.
    }
}
