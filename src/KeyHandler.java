import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class KeyHandler implements KeyListener, ActionListener {
    private CollageCreator collageCreator;
    private Timer timer;
    private boolean left, right, up, down, zoomIn, zoomOut;

    public KeyHandler(CollageCreator collageCreator) {
        this.collageCreator = collageCreator;
        this.timer = new Timer(8, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == timer && (left||right||up||down||zoomOut||zoomIn)) {
            int index = collageCreator.getSelectedIndex();
            if(index != -1) {
                BufferedImage img = collageCreator.getSelectedImage();
                CollageImage[] images = collageCreator.getImages();
                if (left) {
                    images[index].moveX(-3, img);
                }
                if (right) {
                    images[index].moveX(3, img);
                }
                if (down) {
                    images[index].moveY(3, img);
                }
                if (up) {
                    images[index].moveY(-3, img);
                }
                if (zoomIn) {
                    images[index].zoomIn();
                }
                if (zoomOut) {
                    images[index].zoomOut();
                }
                collageCreator.paintOverImage();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            up = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            down= true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (key == KeyEvent.VK_W) {
            zoomIn = true;
        }
        if (key == KeyEvent.VK_S) {
            zoomOut = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            up = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            down= false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (key == KeyEvent.VK_W) {
            zoomIn = false;
        }
        if (key == KeyEvent.VK_S) {
            zoomOut = false;
        }
    }
}
