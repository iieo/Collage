public class Main {
    public static void main(String args[]){
        long heapSize = Runtime.getRuntime().totalMemory();
        long heapMaxSize = Runtime.getRuntime().maxMemory();
        long heapFreeSize = Runtime.getRuntime().freeMemory();
        System.out.println("heapsize"+formatSize(heapSize));
        System.out.println("heapmaxsize"+formatSize(heapMaxSize));
        System.out.println("heapFreesize"+formatSize(heapFreeSize));
        new Frame();
    }
    public static String formatSize(long v) {
        if (v < 1024) return v + " B";
        int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
        return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
    }
}
