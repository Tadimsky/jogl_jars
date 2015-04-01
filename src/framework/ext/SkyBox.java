package framework.ext;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import components.terrain.Vertex;
import framework.ext.math.Point;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jonno on 3/8/2015.
 */
public class SkyBox extends  VisualComponent {
    public static final int FRONT = 0, BACK = 1, LEFT = 2, RIGHT = 3, UP = 4, DOWN = 5;

    private Texture[] myTextures;
    private File[] myTextureFiles;
    private float mySize;
    private Point myCenterPosition;

    private int polygonMode;
    private boolean drawSkybox;

    public SkyBox(String[] files, float size) {
        assert files.length == 6;
        myTextureFiles = new File[files.length];
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            File f = new File(file);
            if (!f.exists()) {
                System.out.printf("File %s does not exist.", file);
                System.exit(-1);
            }
            myTextureFiles[i] = f;
        }
        mySize = size;
        polygonMode = GL2.GL_FILL;
        drawSkybox = true;
    }

    public void setCenterPosition(Point p) {
        myCenterPosition = p;
    }


    @Override
    public void display(GL2 gl, GLU glu, GLUT glut) {
        if (!drawSkybox) {
            return;
        }

        float x = myCenterPosition.x - mySize / 2;
        float y = myCenterPosition.y - mySize / 2;
        float z = myCenterPosition.z - mySize / 2;

        float width = mySize;
        float height = mySize;
        float length = mySize;

        // draw cube
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, polygonMode);

        gl.glDisable(GL2.GL_LIGHTING);
        gl.glDepthMask(false);
        gl.glNormal3f(0, 1, 0);
        drawFaceFB(gl, myTextures[FRONT], new Vertex(x, y, z + length), new Vertex(x, y + height, z + length),
                new Vertex(x + width, y + height, z + length), new Vertex(x + width, y, z + length));
        
        drawFaceFB(gl, myTextures[BACK], new Vertex(x + width, y, z), new Vertex(x + width, y + height, z),
                new Vertex(x, y + height, z), new Vertex(x, y, z));

        drawFaceFB(gl, myTextures[LEFT], new Vertex(x,y,z), new Vertex(x,y + height,z), new Vertex(x, y+height, z+length),
                new Vertex(x, y, z + length));

        drawFaceFB(gl, myTextures[RIGHT], new Vertex(x + width,y,z + length), new Vertex(x + width, y+ height, z+length),
                new Vertex(x + width, y + height, z), new Vertex(x + width,y,z));

        drawFaceFB(gl, myTextures[UP], new Vertex(x, y + height, z + length), new Vertex(x,y + height,z), 
                new Vertex(x + width,y + height,z), new Vertex(x + width, y+ height, z+length));

        drawFaceFB(gl, myTextures[DOWN],
                new Vertex(x, y, z), new Vertex(x, y, z+length), new Vertex(x + width, y, z + length), new Vertex(x + width,y,z));

        gl.glDepthMask(true);
        gl.glEnable(GL2.GL_LIGHTING);
    }

    private void drawFaceFB(GL2 gl, Texture t, Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
        t.enable(gl);
        t.bind(gl);
        TextureCoords coords = t.getImageTexCoords();
        gl.glBegin(GL2.GL_QUADS);
        {
            gl.glTexCoord2f(coords.right(), coords.bottom());
            gl.glVertex3f(v1.x, v1.y, v1.z);
            gl.glTexCoord2f(coords.right(), coords.top());
            gl.glVertex3f(v2.x, v2.y, v2.z);
            gl.glTexCoord2f(coords.left(), coords.top());
            gl.glVertex3f(v3.x, v3.y, v3.z);
            gl.glTexCoord2f(coords.left(), coords.bottom());
            gl.glVertex3f(v4.x, v4.y, v4.z);
        }
        gl.glEnd();
        t.disable(gl);
    }

    @Override
    public void animate(GL2 gl, GLU glu, GLUT glut) {

    }

    public void keyPressed (int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_1:
                polygonMode = polygonMode == GL2.GL_FILL ? GL2.GL_LINE : GL2.GL_FILL;
                break;
            case KeyEvent.VK_2:
                drawSkybox = !drawSkybox;
                break;
        }

    }

    @Override
    public void init(GL2 gl, GLU glu, GLUT glut) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        myTextures = new Texture[myTextureFiles.length];
        for (int i = 0; i < myTextureFiles.length; i++) {
            File f = myTextureFiles[i];
            try {
                Texture text = TextureIO.newTexture(f, false);
                myTextures[i] = text;
                
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
