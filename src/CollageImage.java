import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

public class CollageImage {
    private final static double ZOOM_STEP = 0.005;
    private File file;
    private double x, y;
    private double zoom;
    private int width, height;
    private Format format;

    public CollageImage(File file, Format format, int x, int y) {
        this.file = file;
        this.format = format;
        this.x = x;
        this.y = y;
        this.zoom = 1;
    }

    public CollageImage(File file, Format format) {
        this(file, format, 0,0);
    }

    public CollageImage(File file) {
        this(file, null);
    }

    public BufferedImage getSubimage(BufferedImage img) {
        double factor = calculateFactor(img);
        double width = format.getWidth() * factor;
        double height = format.getHeight() * factor;
        BufferedImage stripedImage;
        try {
            stripedImage = img.getSubimage((int) x, (int) y, (int) width, (int) height);
        }catch (RasterFormatException e){
            this.x = 0;
            this.y = 0;
            stripedImage = img.getSubimage((int) x, (int) y, (int) width, (int) height);
        }
        return stripedImage;
    }

    public BufferedImage loadImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private double calculateFactor(BufferedImage img) {
        return Math.min((double) img.getWidth() / format.getWidth(), (double) img.getHeight() / format.getHeight())*zoom;
    }

    public void moveX(int i, BufferedImage img) {
        double factor = calculateFactor(img);
        if (x + i > 0 && x + i < img.getWidth() - format.getWidth() * factor) {
            x += i;
        }
    }

    public void moveY(int i, BufferedImage img) {
        double factor = calculateFactor(img);
        if (y + i > 0 && y + i < img.getHeight() - format.getHeight() * factor) {
            y += i;
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void zoomIn() {
        if (this.zoom - ZOOM_STEP > 0) {
            this.zoom -= ZOOM_STEP;
        } else {
            this.zoom = ZOOM_STEP;
        }
    }

    public void zoomOut() {
        if (this.zoom + ZOOM_STEP <= 1) {
            this.zoom += ZOOM_STEP;
        } else {
            this.zoom = 1;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
}
