
public class Format {

    private double width, height;
    private double ratio;

    public Format(double width, double height) {
        this.width = width;
        this.height = height;
        this.ratio = width / height;
    }
    public Format() {
        this.width = 1;
        this.height = 1;
        this.ratio = 1;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Format multiply(double factor) {
        return new Format(width * factor, height * factor);
    }
}