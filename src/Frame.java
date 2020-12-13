import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frame extends JFrame implements ActionListener, MouseListener {

    private JMenuItem openImages, exportImage, createImage, settingsMenu;
    private CollageCreator collageCreator;
    private JPanel paintPanel;
    private Format defaultResolution = new Format(300, 300);
    private OptionFrame optionFrame;

    public Frame(int width, int height) {
        collageCreator = new CollageCreator(defaultResolution);
        createFrame(width, height);
        createMenu();
        optionFrame = new OptionFrame(collageCreator);
    }

    public Frame() {
        this(800, 600);
    }

    private void createFrame(int width, int height) {
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Collage Creator");
        getContentPane().setBackground(Color.WHITE);
        addPaintPanel();
        addKeyListener(collageCreator.getKeyHandler());
        setVisible(true);
    }

    private void addPaintPanel() {
        setLayout(new BorderLayout());
        paintPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage img = collageCreator.getCollageImage();
                double factor = calculateFactor();
                int width = (int) (factor * img.getWidth()), height = (int) (factor * img.getHeight());
                g.drawImage(img, 0, 0, width, height, this);

                int selectedIndex = collageCreator.getSelectedIndex();
                if(selectedIndex!=-1){
                    int row = selectedIndex / (collageCreator.getCols());
                    int col = selectedIndex % (collageCreator.getCols());
                    Format format = collageCreator.getFormat();
                    g.drawRect((int) (col * format.getWidth()*factor), (int) (row * format.getHeight()*factor),
                            (int) (format.getWidth()*factor), (int) (format.getHeight()*factor));
                }
                repaint();
            }
        };
        paintPanel.addMouseListener(this);
        add(paintPanel);
        setVisible(true);
    }

    public double calculateFactor() {
        Format res = collageCreator.getResolution();
        double factor = 1;
        double width = paintPanel.getWidth(), height = paintPanel.getHeight();
        factor = Math.max(res.getWidth() / width, res.getHeight() / height);
        return 1d / factor;
    }

    private void createMenu() {
        JMenuBar menuBar;

        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "Import all the images");

        // openImage
        openImages = new JMenuItem("Open Images");
        openImages.addActionListener(this::actionPerformed);
        fileMenu.add(openImages);

        // export Image
        exportImage = new JMenuItem("Export Image");
        exportImage.addActionListener(this::actionPerformed);
        fileMenu.add(exportImage);

        menuBar.add(fileMenu);


        JMenu runMenu = new JMenu("Settings");
        fileMenu.setMnemonic(KeyEvent.VK_B);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "Import all the images");

        // openImage
        settingsMenu = new JMenuItem("Collage Settings");
        settingsMenu.addActionListener(this::actionPerformed);
        runMenu.add(settingsMenu);

        /*createImage = new JMenuItem("Create Collage from Images");
        createImage.addActionListener(this::actionPerformed);
        runMenu.add(createImage);*/

        menuBar.add(runMenu);

        this.setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(openImages)) {
            JOptionPane.showMessageDialog(null, "Not available");
        } else if (event.getSource().equals(exportImage)) {
            String path = FileHandler.chooseOutputImage();
            File file = new File(path);
            String type = path.substring(path.length()-3, path.length()).toUpperCase();
            try {
                ImageIO.write(collageCreator.createImage(collageCreator.getExportScale()), type, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Export finished!");
            System.out.println("Exported!");
        } else if (event.getSource().equals(createImage)) {
            //not existing
        } else if (event.getSource().equals(settingsMenu)) {
            optionFrame.setVisible(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        double factor = calculateFactor();
        int x = (int) (1d / factor * event.getX()), y = (int) (1d / factor * event.getY());
        System.out.println(y);
        collageCreator.selectImage(x, y);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
