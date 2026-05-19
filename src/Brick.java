public class Brick {
    private int start;
    private int end;

    private int currentRow;
    private int layer;

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }


    public Brick(int start, int end) {
        this.start = start;
        this.end = end;
        currentRow = 0;
        layer = 0;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String toString() {
        return "Start: " + start + " --- End: " + end;
    }
}