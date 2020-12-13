import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileHandler {

    public static File[] chooseImages(){
        FileDialog fd = new FileDialog(new JFrame(), "Load", FileDialog.LOAD);
        fd.setMultipleMode(true);
        fd.setDirectory(System.getProperty("user.home")+"/Bilder");
        fd.setFile("*.jpg;*.png;*.jpeg");
        fd.setVisible(true);
        return fd.getFiles();
    }
    public static String chooseOutputImage(){
        FileDialog fd = new FileDialog(new JFrame(), "Save", FileDialog.SAVE);
        fd.setMultipleMode(false);
        fd.setFile("collection.png");
        fd.setVisible(true);
        return fd.getFiles()[0].getAbsolutePath();
    }
}
