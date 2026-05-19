import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BrickLayout {

    private final int l = 1;
    private ArrayList<Brick> bricks = new ArrayList<Brick>();
    private ArrayList<Brick> bricks2 = new ArrayList<Brick>();
    private int[][] grid;
    private int col;
    private int currRow;
    private long time;
    private int count = 0;

    public BrickLayout(String inputFile, int col, boolean dropAll) {
        this.col = col;
        ArrayList<String> fileData = getFileData(inputFile);
        for (String line : fileData) {
            String[] points = line.split(",");
            int start = Integer.parseInt(points[0]);
            int end = Integer.parseInt(points[1]);
            Brick b = new Brick(start, end);
            bricks.add(b);
            bricks2.add(b);
        }
        grid = new int[30][40];
        currRow = grid.length - 1;
    }

    public int[][] getGrid() {
        return grid;
    }

    long originalTime = System.currentTimeMillis();

    private int i = 0;

    public void dropOneBrick() {

        if (!bricks.isEmpty()){
            Brick b = bricks.removeFirst();
            int start = b.getStart();
            int end = b.getEnd();
            boolean placed = false;

            while (!placed){
                if(!checkRow (currRow, start, end)){
                    if (currRow > 1 && currRow < grid.length - 1){
                        currRow++;
                    }
                    if (checkRow (currRow, start, end)){
                        currRow--;
                    }
                } else {
                    while (checkRow(currRow, start,end)){
                        if (checkRow(currRow, start,end) && (currRow > 1)){
                            currRow--;
                        }
                    }
                }
                for (int j = start; j <= end; j++) {
                    grid[currRow][j] = 1;
                }
                placed = true;
            }
        }
    }

    public void placeOneBrick(){
        int start;
        int end;
        if (!bricks2.isEmpty()){
            Brick b = bricks2.removeFirst();
            start = b.getStart();
            end = b.getEnd();

            for (int j = start; j <= end; j++) {
                grid[b.getLayer()][i] = 1;
            }
        }
    }

    public void fallingBricks(){
        placeOneBrick();

        // first round gets diff between the changing bricks
        // second round gets bricks len
        for (int j = 0; j < (bricks.size() - bricks2.size()); j++) {
            int start = bricks.get(j).getStart();
            int end = bricks.get(j).getEnd();
            int height = bricks.get(j).getLayer();

            if (height < 29 && !checkUnderBrick(height, start, end - start)) { // block moves down
                for (int i = start; i <= end; i++) { // set prev block to zeroes
                    grid[height][i] = 0;
                }

                bricks.get(j).setLayer(bricks.get(j).getLayer() + 1);

                for (int i = start; i <= end; i++) { // new loc is filled with ones
                    grid[bricks.get(j).getLayer()][i] = 1;
                }
            } else if (height == 29 || checkUnderBrick(height, start, end - start)){ // removes brick from list
                bricks.remove(j);
                j--;
            }
        }
//        }
    }

    public boolean checkUnderBrick(int r, int c, int length){ // check for the length of the brick
        for (int i = c; i < c + length; i++){
            if (grid[r+1][i] == 1){
                return true;
            }
        }
        return false;
    }

    private boolean checkRow(int row){
        for (int c = 0; c < grid[0].length; c++){
            if (grid[row][c] == 1){
                return true;
            }
        }
        return false;
    }

    private boolean checkRow(int row, int start, int end){
        for (int s = start; s <= end; s++){
            if (grid[row][s] == 1){
                return true;
            }
        }
        return false;
    }

        public ArrayList<String> getFileData (String fileName){
            File f = new File(fileName);
            Scanner s = null;
            try {
                s = new Scanner(f);
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                System.exit(1);
            }
            ArrayList<String> fileData = new ArrayList<String>();
            while (s.hasNextLine())
                fileData.add(s.nextLine());

            return fileData;
        }
    }