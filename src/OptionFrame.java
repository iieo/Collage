import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionFrame extends JFrame implements ActionListener {
    private JLabel lblRows, lblCols, lblResolutionWidth, lblResolutionHeight, lblFormat, lblResolution, lblError, lblExport, lblExportScale;
    private JTextField rows, cols, resolutionWidth, resolutionHeight, scale;
    private JButton btnApply;
    private CollageCreator collageCreator;

    public OptionFrame(CollageCreator collageCreator) {
        this.collageCreator = collageCreator;
        createFrame();
    }

    private void createFrame() {
        setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Options");
        getContentPane().setBackground(Color.WHITE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        setAlwaysOnTop(true);

        addComponents();
        setLayout(null);
    }

    private void addComponents() {
        int yPos = 0, yStep = 30;
        /*   FORMAT   */
        lblFormat = new JLabel("Format");
        lblFormat.setBounds(0, yPos, getWidth(), 30);
        add(lblFormat);
        yPos += yStep;

        lblRows = new JLabel("Rows:");
        lblRows.setBounds(0, yPos, getWidth(), 30);
        add(lblRows);
        yPos += yStep;

        rows = new JTextField();
        rows.setBounds(0, yPos, getWidth(), 30);
        PlainDocument doc = (PlainDocument) rows.getDocument();
        doc.setDocumentFilter(new IntFilter());
        rows.setText(String.valueOf(collageCreator.getRows()));
        add(rows);
        yPos += yStep;

        lblCols = new JLabel("Cols:");
        lblCols.setBounds(0, yPos, getWidth(), 30);
        add(lblCols);
        yPos += yStep;

        cols = new JTextField();
        cols.setBounds(0, yPos, getWidth(), 30);
        PlainDocument doc2 = (PlainDocument) cols.getDocument();
        doc2.setDocumentFilter(new IntFilter());
        cols.setText(String.valueOf(collageCreator.getCols()));
        add(cols);
        yPos += yStep;

        /*   RESOLUTION   */
        lblResolution = new JLabel("Resolution");
        lblResolution.setBounds(0, yPos, getWidth(), 30);
        add(lblResolution);
        yPos += yStep;

        lblResolutionWidth = new JLabel("Width:");
        lblResolutionWidth.setBounds(0, yPos, getWidth(), 30);
        add(lblResolutionWidth);
        yPos += yStep;

        resolutionWidth = new JTextField();
        resolutionWidth.setBounds(0, yPos, getWidth(), 30);
        PlainDocument doc3 = (PlainDocument) resolutionWidth.getDocument();
        doc3.setDocumentFilter(new IntFilter());
        resolutionWidth.setText(String.valueOf((int)collageCreator.getResolution().getWidth()));
        add(resolutionWidth);
        yPos += yStep;

        lblResolutionHeight = new JLabel("Height:");
        lblResolutionHeight.setBounds(0, yPos, getWidth(), 30);
        add(lblResolutionHeight);
        yPos += yStep;

        resolutionHeight = new JTextField();
        resolutionHeight.setBounds(0, yPos, getWidth(), 30);
        PlainDocument doc4 = (PlainDocument) resolutionHeight.getDocument();
        doc4.setDocumentFilter(new IntFilter());
        resolutionHeight.setText(String.valueOf((int)collageCreator.getResolution().getHeight()));
        add(resolutionHeight);
        yPos += yStep;

        /*   EXPORT SCALE   */
        lblExport = new JLabel("Export");
        lblExport.setBounds(0, yPos, getWidth(), 30);
        add(lblExport);
        yPos += yStep;

        lblExportScale = new JLabel("Scale:");
        lblExportScale.setBounds(0, yPos, getWidth(), 30);
        add(lblExportScale);
        yPos += yStep;

        scale = new JTextField();
        scale.setBounds(0, yPos, getWidth(), 30);
        PlainDocument doc5 = (PlainDocument) scale.getDocument();
        doc5.setDocumentFilter(new DoubleFilter());
        scale.setText(String.valueOf((int)collageCreator.getExportScale()));
        add(scale);
        yPos += yStep;

        btnApply = new JButton("Apply");
        btnApply.setBackground(new Color(0x27ae60));
        btnApply.setForeground(Color.WHITE);
        btnApply.setBounds(0, yPos+yStep, getWidth(), 30);
        btnApply.addActionListener(this);
        add(btnApply);
        yPos += 2*yStep;

        lblError = new JLabel("");
        lblError.setBounds(0, yPos, getWidth(), 30);
        lblError.setForeground(Color.RED);
        add(lblError);
        yPos += yStep;

        setSize(getWidth(), yPos + yStep);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == btnApply){
            int rowsAmount = Integer.parseInt(rows.getText());
            int colsAmount = Integer.parseInt(cols.getText());
            int resWidth = Integer.parseInt(resolutionWidth.getText());
            int resHeight = Integer.parseInt(resolutionHeight.getText());
            double exportScale = Double.valueOf(scale.getText());
            collageCreator.setResolution(new Format(resWidth, resHeight));
            collageCreator.setExportScale(exportScale);
            boolean acceptedRowCol = collageCreator.setRowsAndCols(rowsAmount, colsAmount);
            if(acceptedRowCol){
                lblError.setText(null);
            }else{
                lblError.setText("Rows and Columns ratio not accepted!");
            }
        }
    }


}
