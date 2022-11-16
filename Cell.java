public class Cell {
    long xpos;
    long ypos;
    public Cell(long x, long y) {
        this.xpos = x;
        this.ypos = y;
    }

    public Cell(int x, int y) {
        this.xpos = x;
        this.ypos = y;
    }
    public long[] getPos() {

        return new long[]{xpos, ypos};
    }

}
