package framework.ext.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

/**
 * Created by Jonno on 3/5/2015.
 */
public class KeyMap implements KeyListener {
    
    private BitSet myKeySet;
    
    public KeyMap() {
        myKeySet = new BitSet(256);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // typing is not pressing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        myKeySet.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        myKeySet.clear(e.getKeyCode());
    }
    
    public boolean isKeyPressed(int keyCode) {
        return myKeySet.get(keyCode);
    }
}
