import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BrickLayout {

    private ArrayList<Brick> bricks;
    private int[][] grid;

    public BrickLayout(String inputFile) {
        ArrayList<String> fileData = getFileData(inputFile);
        bricks = new ArrayList<Brick>();
        for (String line : fileData) {
            String[] points = line.split(",");
            int start = Integer.parseInt(points[0]);
            int end = Integer.parseInt(points[1]);
            Brick b = new Brick(start, end);
            bricks.add(b);
        }
        grid = new int[30][40];
    }

    public int[][] getGrid() {
        return grid;
    }
    long originalTime = System.currentTimeMillis();

    private int i = 0;
    public void dropOneBrick() {
        ArrayList<Integer> counter = new ArrayList<>();
        for (int col = bricks.get(i).getStart(); col <= bricks.get(i).getEnd(); col++) {
            int count = 29;
            for (int row = 29; row >= 0; row--) {
                if (grid[row][col - 1] == 1) {
                    count = row - 1;
                }
            }
            counter.add(count);
        }

        int nextLayer = counter.get(0);
        for (int j = 1; j < counter.size(); j++) {
            if (counter.get(j) < nextLayer) {
                nextLayer = counter.get(j);
            }
        }

        //time
        long time = System.currentTimeMillis();
        int currentRow = 0;

        if ((time - originalTime) > 500) {
            while (currentRow < nextLayer) {
                if ((time - originalTime) > 500){
                    currentRow++;
                for (int col = bricks.get(i).getStart(); col <= bricks.get(i).getEnd(); col++) {
                    grid[currentRow - 1][col - 1] = 0;
                }
                }
                for (int col = bricks.get(i).getStart(); col <= bricks.get(i).getEnd(); col++) {
                    grid[currentRow][col - 1] = 1;
                }
            }
//            if (currentRow > nextLayer) {
//                currentRow = 0;
//            }
            originalTime = System.currentTimeMillis();
            i++;

//            for (int col = bricks.get(i).getStart(); col <= bricks.get(i).getEnd(); col++) {
//                int row = nextLayer;
//                grid[row][col - 1] = 1;
//            }
//            i++;
        }
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