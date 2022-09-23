import java.io.*;
import java.util.Scanner;

public class Morphology {
    public int numImgRows, numImgCols, imgMin, imgMax;
    public int numStructRows, numStructCols, structMin, structMax;
    public int rowOrigin, colOrigin;
    public int rowFrameSize, colFrameSize;
    public int extraRows, extraCols, rowSize, colSize;
    public int[][] zeroFramedAry, morphAry, tempAry, structAry;

    public Morphology(Scanner img, Scanner struct){
        numImgRows = img.nextInt();
        numImgCols = img.nextInt();
        imgMin = img.nextInt();
        imgMax = img.nextInt();

        numStructRows = struct.nextInt();
        numStructCols = struct.nextInt();
        structMin = struct.nextInt();
        structMax = struct.nextInt();
        rowOrigin = struct.nextInt();
        colOrigin = struct.nextInt();
        
        rowFrameSize = numStructRows / 2;
        colFrameSize = numStructCols / 2;

        extraRows = rowFrameSize*2;
        extraCols = colFrameSize*2;

        rowSize = numImgRows + extraRows;
        colSize = numImgCols + extraCols;

        zeroFramedAry = new int[rowSize][colSize];
        morphAry = new int[rowSize][colSize];
        tempAry = new int[rowSize][colSize];
        structAry = new int[numStructRows][numStructCols];

        Morphology.zero2DAry(zeroFramedAry, rowSize, colSize);
        loadImg(img, zeroFramedAry);

        Morphology.zero2DAry(structAry, numStructRows, numStructCols);
        loadStruct(struct, structAry);
    }

    public static void zero2DAry(int[][] ary, int nRows, int nCols){
        for(int i = 0; i < nRows; ++i){
            for(int j = 0; j < nCols; ++j){
                ary[i][j] = 0;
            }
        }
    }
    

    private void loadImg(Scanner imgFile, int[][] zeroFramedArray){
        int rowEnd = rowSize - rowFrameSize, colEnd = colSize - colFrameSize;
        for(int i = rowFrameSize; i < rowEnd; ++i){
            for(int j = colFrameSize; j < colEnd; ++j){
                zeroFramedArray[i][j] = imgFile.nextInt();
            }
        }
    }

    private void loadStruct(Scanner structFile, int[][] structArray){
        for(int i = 0; i < numStructRows; ++i){
            for(int j = 0; j < numStructCols; ++j){
                structArray[i][j] = structFile.nextInt();
            }
        }
    }

    public void computeDialation(int[][] inAry, int[][] outAry){
        for(int i = rowFrameSize; i < rowSize - rowFrameSize; ++i){
            for(int j = colFrameSize; j < colSize-colFrameSize; ++j){
                if(inAry[i][j] > 0){
                    //do we need the inAry? seems irrelevent
                    onePixelDialation(i, j, inAry, outAry);
                }
            }
        }

    }

    public void computeErosion(int[][] inAry, int[][] outAry){
        for(int i = rowFrameSize; i < rowSize - rowFrameSize; ++i){
            for(int j = colFrameSize; j < colSize - colFrameSize; ++j){
                if(inAry[i][j] > 0){
                    onePixelErosion(i, j, inAry, outAry);
                }
            }
        }
    }

    public void computeOpening(int[][] inAry, int[][] outAry, int [][] temp){
        computeDialation(inAry, tempAry);
        computeErosion(tempAry, outAry);
    }

    public void computeClosing(int[][] inAry, int[][] outAry, int [][] temp){
        computeErosion(inAry, tempAry);
        computeDialation(tempAry, outAry);
    }

    public void onePixelDialation(int i, int j, int[][] inAry, int[][] outAry){
        int iOffset = i - rowOrigin, jOffset = j - colOrigin;

        for(int r = 0; r < numStructRows; ++r){
            for(int c = 0; c< numStructCols; ++c){
                if(structAry[r][c] > 0){
                    outAry[iOffset + r][jOffset + c] = 1;
                }
            }
        }
    }

    public void onePixelErosion(int i, int j, int[][] inAry, int[][] outAry){
        boolean matchFlag = true;
        int iOffset = i - rowOrigin, jOffset = j - colOrigin;

        for(int r = 0; r < numStructRows && matchFlag; ++r){
            for(int c = 0; c < numStructCols && matchFlag; ++c){
                if(structAry[r][c] > 0 && inAry[iOffset + r][jOffset + c] <= 0){
                    matchFlag = false;
                }
            }
        }
        if(matchFlag){
            outAry[i][j] = 1;
        }
        else{
            outAry[i][j] = 0;
        }
    }

    public static void aryToFile(int[][] ary, BufferedWriter outfile){
        try{
            int r = ary.length, c = 0;
            for(int i = 0; i < r; ++i){
                c = ary[i].length;
                for(int j = 0; j < c; ++j){
                    outfile.write(Integer.toString(ary[i][j]) + " ");
                }
                outfile.write("\n");
            }
            outfile.write("\n\n");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void prettyPrint(int[][] ary, BufferedWriter outfile){
        try{
            int r = ary.length, c = 0;
            for(int i = 0; i < r; ++i){
                c = ary[i].length;
                for(int j = 0; j < c; ++j){
                    if(ary[i][j] == 0){
                        outfile.write(". ");
                    }
                    else{
                        outfile.write("1 ");
                    }
                }
                outfile.write("\n");
            }
            outfile.write("\n\n");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

}
