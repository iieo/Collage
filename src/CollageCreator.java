
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class CollageCreator {
    private CollageImage[] images;
    private BufferedImage collageImage, selectedImage;
    private int rows, cols;
    private int selectedIndex;
    private Format resolution;
    private Format format;
    private Graphics graphics;
    private KeyHandler keyHandler;
    private double exportScale;

    private final int MAX_COLUMN_ITEMS = 15;

    public CollageCreator(Format resolution) {
        this.selectedIndex = -1;
        this.exportScale = 5;
        this.cols = 1;
        this.rows = 1;
        this.resolution = resolution;
        this.keyHandler = new KeyHandler(this);
        loadFiles();
        calculateRowsAndCols();
        calculateFormat();
        createImage();
    }

    public boolean setRowsAndCols(int rows, int cols){
        if(rows * cols == images.length){
            this.rows = rows;
            this.cols = cols;
            calculateFormat();
            createImage();
            return true;
        }else{
             return false;
        }
    }

    public void setResolution(Format resolution){
        if(!resolution.equals(this.resolution)) {
            this.resolution = resolution;
            calculateFormat();
            createImage();
        }
    }

    private void loadFiles() {
        File[] files = FileHandler.chooseImages();
        images = new CollageImage[files.length];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            images[i] = new CollageImage(file);
        }
    }

    private void calculateRowsAndCols() {
        System.out.println("Amount of images::"+images.length);
        while (rows * cols != images.length) {
            cols++;
            if (cols > MAX_COLUMN_ITEMS) {
                cols = 1;
                rows++;
            }
            if (rows > MAX_COLUMN_ITEMS) {
                System.out.println(cols);
                System.out.println(rows);
                System.err.println("Couldn't find a good combination");
                System.exit(1);
            }
        }
        System.out.println("Rows::" + rows + "-Cols::" + cols);
    }

    private void calculateFormat() {
        format = new Format(resolution.getWidth() / cols, resolution.getHeight() / rows);
        for (CollageImage image : images) {
            image.setFormat(format);
        }
    }

    public int getIndexByPoint(int x, int y) {
        int c = (int) Math.floor(x / format.getWidth());
        int r = (int) Math.floor(y / format.getHeight());
        return r * cols + c;
    }

    public void selectImage(int x, int y) {
        int index = getIndexByPoint(x, y);
        CollageImage cImage = images[index];
        selectedImage = cImage.loadImage();
        selectedIndex = index;
        System.out.println("selected " + index);
    }

    public void paintOverImage() {
        if (selectedImage == null) {
            return;
        }
        int row = selectedIndex / cols;
        int col = selectedIndex % cols;

        CollageImage cImage = images[selectedIndex];
        BufferedImage img = cImage.getSubimage(selectedImage);

        graphics.drawImage(img, (int) (col * format.getWidth()), (int) (row * format.getHeight()), (int) format.getWidth(), (int) format.getHeight(), null);
    }

    public void createImage() {
        collageImage = createImage(1);
        graphics = collageImage.getGraphics();
    }

    public BufferedImage createImage(double factor) {
        Format outputResolution = resolution.multiply(factor);
        Format outputFormat = format.multiply(factor);
        BufferedImage outputImage = new BufferedImage((int) outputResolution.getWidth(), (int) outputResolution.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = outputImage.getGraphics();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int index = r * cols + c;
                CollageImage cImage = images[index];
                BufferedImage img = cImage.loadImage();
                assert (img != null);
                img = cImage.getSubimage(img);
                g.drawImage(img, (int) (c * outputFormat.getWidth()), (int) (r * outputFormat.getHeight()),
                        (int) outputFormat.getWidth(), (int) outputFormat.getHeight(), null);
            }
        }
        return outputImage;
    }


    public BufferedImage getCollageImage() {
        return collageImage;
    }

    public void setCollageImage(BufferedImage collageImage) {
        this.collageImage = collageImage;
    }

    public Format getResolution() {
        return resolution;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public BufferedImage getSelectedImage() {
        return selectedImage;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public CollageImage[] getImages() {
        return images;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Format getFormat() {
        return format;
    }

    public double getExportScale() {
        return exportScale;
    }

    public void setExportScale(double exportScale) {
        this.exportScale = exportScale;
    }
}
