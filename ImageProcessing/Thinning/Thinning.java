import java.io.BufferedWriter;
import java.util.Scanner;


public class Thinning {
    public int numRows, numCols, minVal, maxVal, changeFlag, cycleCount;
    public int[][] aryOne, aryTwo;

    public Thinning(int[] vals){
        numRows = vals[0];
        numCols = vals[1];
        minVal = vals[2];
        maxVal = vals[3];

        aryOne = new int[numRows + 2][numCols + 2];
        aryTwo = new int[numRows + 2][numCols + 2];

    }

    public void zeroFrame(int[][] inp){
        int rows = numRows + 2, cols = numCols + 2;
        int lastRowIndex = rows - 1, lastColumnIndex = cols - 1;

        for(int i = 0; i < cols; ++i){
            inp[0][i] = 0;
            inp[lastRowIndex][i] = 0;
        }
        for(int i = 0; i < rows; ++i){
            inp[i][0] = 0;
            inp[i][lastColumnIndex] = 0;
        }

    }

    public void loadImage(Scanner inp, int[][] img){
        for(int i = 1; i < numRows + 1; ++i){
            for(int j = 1; j < numCols + 1; ++j){
                img[i][j] = inp.nextInt();
            }
        }
    }


    //always copy ary2 to ary1
    public void copyArys(){
        for(int i = 1; i < numRows + 1; ++i){
            for(int j = 1; j < numCols + 1; ++j){
                aryOne[i][j] = aryTwo[i][j];
            }
        }
    }
    

    public void thinning(){
        changeFlag = 0;
        northThinning();
        copyArys();
        southThinning();
        copyArys();
        westThinning();
        copyArys();
        eastThinning();
        copyArys();
    }

    public void northThinning(){
        int sum = 0, ts = 0, bs = 0, ls = 0, rs = 0;
        boolean flag;
        for(int i = 1; i < numRows + 1; ++i){
            for(int j = 1; j < numCols + 1; ++j){
                flag = false;
                if(aryOne[i][j] > 0 && aryOne[i-1][j] == 0){
                    flag = true;
                    sum = aryOne[i-1][j-1] + aryOne[i-1][j];
                    sum += aryOne[i-1][j+1] + aryOne[i][j-1];
                    sum += aryOne[i][j+1] + aryOne[i+1][j-1];
                    sum += aryOne[i+1][j] + aryOne[i+1][j+1];
                    if(sum < 4){
                        flag = false;
                    }
                    //case 1
                    if(flag && aryOne[i][j-1] == 0 && 
                        aryOne[i][j+1] == 0){
                        ts = aryOne[i-1][j-1] + aryOne[i-1][j];
                        ts +=  aryOne[i-1][j+1];
                        bs = aryOne[i+1][j-1] + aryOne[i+1][j];
                        bs += aryOne[i+1][j+1];
                        if(ts > 0 && bs > 0){
                            flag = false;
                        }
                    }
                    //case 2
                    if(flag && aryOne[i-1][j] == 0 && 
                        aryOne[i+1][j] == 0){
                        ls = aryOne[i-1][j-1] + aryOne[i][j-1];
                        ls += aryOne[i+1][j-1];
                        rs = aryOne[i-1][j+1] + aryOne[i][j+1];
                        rs += aryOne[i+1][j+1];
                        if(ls > 0 && rs >0){
                            flag = false;
                        }
                    }
                    //case alpha
                    if(flag && aryOne[i-1][j] + aryOne[i][j-1] == 0 && 
                        aryOne[i-1][j-1] > 0){
                        flag = false;
                    }
                    //case beta
                    if(flag && aryOne[i+1][j] + aryOne[i][j-1] == 0 && 
                        aryOne[i+1][j-1] > 0){
                        flag = false;
                    }
                    //case delta
                    if(flag && aryOne[i-1][j] + aryOne[i][j+1] == 0 && 
                        aryOne[i-1][j+1] > 0){
                        flag = false;
                    }
                    //case gamma
                    if(flag && aryOne[i+1][j] + aryOne[i][j+1] == 0 && 
                        aryOne[i+1][j+1] > 0){
                        flag = false;
                    }
                }
                if(flag){
                    aryTwo[i][j] = 0;
                    changeFlag++;
                }else{
                    aryTwo[i][j] = aryOne[i][j];
                }
            }
        }
    }

    public void southThinning(){
        int sum = 0, ts = 0, bs = 0, ls = 0, rs = 0;
        boolean flag;
        for(int i = 1; i < numRows + 1; ++i){
            for(int j = 1; j < numCols + 1; ++j){
                flag = false;
                if(aryOne[i][j] > 0 && aryOne[i+1][j] == 0){
                    flag = true;
                    sum = aryOne[i-1][j-1] + aryOne[i-1][j];
                    sum += aryOne[i-1][j+1] + aryOne[i][j-1];
                    sum += aryOne[i][j+1] + aryOne[i+1][j-1];
                    sum += aryOne[i+1][j] + aryOne[i+1][j+1];
                    if(sum < 4){
                        flag = false;
                    }
                    //case 1
                    if(flag && aryOne[i][j-1] == 0 && 
                        aryOne[i][j+1] == 0){
                        ts = aryOne[i-1][j-1] + aryOne[i-1][j];
                        ts += aryOne[i-1][j+1];
                        bs = aryOne[i+1][j-1] + aryOne[i+1][j];
                        bs += aryOne[i+1][j+1];
                        if(ts > 0 && bs > 0){
                            flag = false;
                        }
                    }
                    //case 2
                    if(flag && aryOne[i-1][j] == 0 && 
                        aryOne[i+1][j] == 0){
                        ls = aryOne[i-1][j-1] + aryOne[i][j-1];
                        ls += aryOne[i+1][j-1];
                        rs = aryOne[i-1][j+1] + aryOne[i][j+1];
                        rs += aryOne[i+1][j+1];
                        if(ls > 0 && rs >0){
                            flag = false;
                        }
                    }
                    //case alpha
                    if(flag && aryOne[i-1][j] + aryOne[i][j-1] == 0 && 
                        aryOne[i-1][j-1] > 0){
                        flag = false;
                    }
                    //case beta
                    if(flag && aryOne[i+1][j] + aryOne[i][j-1] == 0 && 
                        aryOne[i+1][j-1] > 0){
                        flag = false;
                    }
                    //case delta
                    if(flag && aryOne[i-1][j] + aryOne[i][j+1] == 0 && 
                        aryOne[i-1][j+1] > 0){
                        flag = false;
                    }
                    //case gamma
                    if(flag && aryOne[i+1][j] + aryOne[i][j+1] == 0 && 
                        aryOne[i+1][j+1] > 0){
                        flag = false;
                    }
                }
                if(flag){
                    aryTwo[i][j] = 0;
                    changeFlag++;
                }else{
                    aryTwo[i][j] = aryOne[i][j];
                }
            }
        }
    }

    public void eastThinning(){
        int sum = 0, ts = 0, bs = 0, ls = 0, rs = 0;
        boolean flag;
        for(int i = 1; i < numRows + 1; ++i){
            for(int j = 1; j < numCols + 1; ++j){
                flag = false;
                if(aryOne[i][j] > 0 && aryOne[i][j+1] == 0){
                    flag = true;
                    sum = aryOne[i-1][j-1] + aryOne[i-1][j];
                    sum += aryOne[i-1][j+1] + aryOne[i][j-1];
                    sum += aryOne[i][j+1] + aryOne[i+1][j-1];
                    sum += aryOne[i+1][j] + aryOne[i+1][j+1];
                    if(sum < 3){
                        flag = false;
                    }
                    //case 1
                    if(flag && aryOne[i][j-1] == 0 && 
                        aryOne[i][j+1] == 0){
                        ts = aryOne[i-1][j-1] + aryOne[i-1][j];
                        ts += aryOne[i-1][j+1];
                        bs = aryOne[i+1][j-1] + aryOne[i+1][j];
                        bs += aryOne[i+1][j+1];
                        if(ts > 0 && bs > 0){
                            flag = false;
                        }
                    }
                    //case 2
                    if(flag && aryOne[i-1][j] == 0 && 
                        aryOne[i+1][j] == 0){
                        ls = aryOne[i-1][j-1] + aryOne[i][j-1];
                        ls += aryOne[i+1][j-1];
                        rs = aryOne[i-1][j+1] + aryOne[i][j+1];
                        rs += aryOne[i+1][j+1];
                        if(ls > 0 && rs >0){
                            flag = false;
                        }
                    }
                    //case alpha
                    if(flag && aryOne[i-1][j] + aryOne[i][j-1] == 0 && 
                        aryOne[i-1][j-1] > 0){
                        flag = false;
                    }
                    //case beta
                    if(flag && aryOne[i+1][j] + aryOne[i][j-1] == 0 && 
                        aryOne[i+1][j-1] > 0){
                        flag = false;
                    }
                    //case delta
                    if(flag && aryOne[i-1][j] + aryOne[i][j+1] == 0 && 
                        aryOne[i-1][j+1] > 0){
                        flag = false;
                    }
                    //case gamma
                    if(flag && aryOne[i+1][j] + aryOne[i][j+1] == 0 && 
                        aryOne[i+1][j+1] > 0){
                        flag = false;
                    }
                }
                if(flag){
                    aryTwo[i][j] = 0;
                    changeFlag++;
                }else{
                    aryTwo[i][j] = aryOne[i][j];
                }
            }
        }
    }

    public void westThinning(){
        int sum = 0, ts = 0, bs = 0, ls = 0, rs = 0;
        boolean flag;
        for(int i = 1; i < numRows + 1; ++i){
            for(int j = 1; j < numCols + 1; ++j){
                flag = false;
                if(aryOne[i][j] > 0 && aryOne[i][j-1] == 0){
                    flag = true;
                    sum = aryOne[i-1][j-1] + aryOne[i-1][j];
                    sum += aryOne[i-1][j+1] + aryOne[i][j-1];
                    sum += aryOne[i][j+1] + aryOne[i+1][j-1];
                    sum += aryOne[i+1][j] + aryOne[i+1][j+1];
                    if(sum < 3){
                        flag = false;
                    }
                    //case 1
                    if(flag && aryOne[i][j-1] == 0 && 
                        aryOne[i][j+1] == 0){
                        ts = aryOne[i-1][j-1] + aryOne[i-1][j];
                        ts += aryOne[i-1][j+1];
                        bs = aryOne[i+1][j-1] + aryOne[i+1][j];
                        bs +=  + aryOne[i+1][j+1];
                        if(ts > 0 && bs > 0){
                            flag = false;
                        }
                    }
                    //case 2
                    if(flag && aryOne[i-1][j] == 0 && 
                        aryOne[i+1][j] == 0){
                        ls = aryOne[i-1][j-1] + aryOne[i][j-1];
                        ls += aryOne[i+1][j-1];
                        rs = aryOne[i-1][j+1] + aryOne[i][j+1];
                        rs += aryOne[i+1][j+1];
                        if(ls > 0 && rs >0){
                            flag = false;
                        }
                    }
                    //case alpha
                    if(flag && aryOne[i-1][j] + aryOne[i][j-1] == 0 && 
                        aryOne[i-1][j-1] > 0){
                        flag = false;
                    }
                    //case beta
                    if(flag && aryOne[i+1][j] + aryOne[i][j-1] == 0 && 
                        aryOne[i+1][j-1] > 0){
                        flag = false;
                    }
                    //case delta
                    if(flag && aryOne[i-1][j] + aryOne[i][j+1] == 0 && 
                        aryOne[i-1][j+1] > 0){
                        flag = false;
                    }
                    //case gamma
                    if(flag && aryOne[i+1][j] + aryOne[i][j+1] == 0 && 
                        aryOne[i+1][j+1] > 0){
                        flag = false;
                    }
                }
                if(flag){
                    aryTwo[i][j] = 0;
                    changeFlag++;
                }else{
                    aryTwo[i][j] = aryOne[i][j];
                }
            }
        }
    }

    public void prettyPrint(int[][] ary, BufferedWriter outp, 
                                int numCycles){
        try{
            outp.write("Pretty Print of Thinning Cycle " + 
                Integer.toString(numCycles) + " including frame: \n");
            for(int i = 0; i < numRows + 2; ++i){
                for(int j = 0; j < numCols + 2; ++j){
                    if(ary[i][j] == 0) {
                        outp.write(". ");
                    }else{
                        outp.write(Integer.toString(ary[i][j]) + " ");
                    }
                }
                outp.write("\n");
            }
            outp.write("\n\n");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void printAry(int[][] ary, BufferedWriter outp){
        try{
            for(int i = 1; i < numRows + 1; ++i){
                for(int j = 1; j < numCols + 1; ++j){
                    outp.write(Integer.toString(ary[i][j]) + " ");
                }
                outp.write("\n");
            }
            outp.write("\n\n");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
