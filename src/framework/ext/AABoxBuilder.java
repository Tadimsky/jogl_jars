package framework.ext;

import com.jogamp.opengl.math.geom.AABBox;
import components.terrain.Face;
import components.terrain.Vertex;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jonno on 3/31/2015.
 */
public class AABoxBuilder {

    public static AABBox fromVertices(final List<Vertex> vertices) {
        float[] low = {Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, };
        float[] high = {Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE};

        for (Vertex v : vertices) {
            if (v.x < low[0]) {
                low[0] = v.x;
            }
            if (v.x > high[0]) {
                high[0] = v.x;
            }

            if (v.y < low[1]) {
                low[1] = v.y;
            }
            if (v.y > high[1]) {
                high[1] = v.y;
            }

            if (v.z < low[2]) {
                low[2] = v.z;
            }
            if (v.z > high[2]) {
                high[2] = v.z;
            }
        }
        return new AABBox(low, high);
    }

    public static AABBox fromFace(Face f) {
        return AABoxBuilder.fromVertices(Arrays.asList(f.getVertices()));
    }
}
