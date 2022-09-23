import java.io.*;
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args){
        try{
            Scanner scanner = new Scanner(new File(args[0]));
            BufferedWriter bufferedwriter1;
            bufferedwriter1 = new BufferedWriter(new FileWriter(new File(args[1])));
            BufferedWriter bufferedwriter2;
            bufferedwriter2 = new BufferedWriter(new FileWriter(new File(args[2])));
            int[] vals = new int[4];
            bufferedwriter1.write(args[0] + "\n\n");
            for(int i = 0; i < 4; ++i){
                vals[i] = scanner.nextInt();
                bufferedwriter1.write(Integer.toString(vals[i]) + " ");
            }
            bufferedwriter1.write("\n");
            Thinning thinning = new Thinning(vals);
            thinning.zeroFrame(thinning.aryOne);
            thinning.zeroFrame(thinning.aryTwo);
            thinning.loadImage(scanner, thinning.aryOne);
            thinning.cycleCount = 0;

            /* 
            Note: Per professor, even though this will print out a completely 
            empty array, I should include it as:
                 "Arytwo is the result of each iteration, print it as specs 
                  says." 
                  - Prof Phillips
            */
            thinning.prettyPrint(thinning.aryTwo, bufferedwriter2, 
                                    thinning.cycleCount);
            thinning.changeFlag = 0;
            do{
                thinning.thinning();
                thinning.cycleCount++;
                thinning.prettyPrint(thinning.aryTwo, bufferedwriter2, 
                                        thinning.cycleCount);
            }while(thinning.changeFlag > 0);
            thinning.printAry(thinning.aryOne, bufferedwriter1);

            scanner.close();
            bufferedwriter1.close();
            bufferedwriter2.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}