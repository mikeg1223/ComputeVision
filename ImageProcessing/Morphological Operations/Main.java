import java.io.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner imgFile, structFile;
        BufferedWriter dialateOutFile, erodeOutFile, closingOutFile;
        BufferedWriter openingOutFile, prettyPrintFile;
        Morphology morphology;
        try{
            imgFile = new Scanner(new BufferedReader(new FileReader(args[0])));
            structFile = new Scanner(new BufferedReader(
                new FileReader(args[1])));
            dialateOutFile = new BufferedWriter(new FileWriter(args[2]));
            erodeOutFile = new BufferedWriter(new FileWriter(args[3]));
            closingOutFile = new BufferedWriter(new FileWriter(args[4]));
            openingOutFile = new BufferedWriter(new FileWriter(args[5]));
            prettyPrintFile = new BufferedWriter( new FileWriter(args[6]));

            //Constructor loads file info into the object
            morphology = new Morphology(imgFile, structFile);

            prettyPrintFile.write("Initial Zero Framed 2D Array: \n");
            Morphology.prettyPrint(morphology.zeroFramedAry, prettyPrintFile);
            prettyPrintFile.write("Initial Structured Element 2D Array: \n");
            Morphology.prettyPrint(morphology.structAry, prettyPrintFile);

            Morphology.zero2DAry(morphology.morphAry, morphology.rowSize, 
                                    morphology.colSize);
            morphology.computeDialation(morphology.zeroFramedAry, 
                                            morphology.morphAry);
            Morphology.aryToFile(morphology.morphAry, dialateOutFile);
            prettyPrintFile.write("Dialation Operation Output: \n");
            Morphology.prettyPrint(morphology.morphAry, prettyPrintFile);

            Morphology.zero2DAry(morphology.morphAry, morphology.rowSize, 
                                    morphology.colSize);
            morphology.computeErosion(morphology.zeroFramedAry, 
                                        morphology.morphAry);
            Morphology.aryToFile(morphology.morphAry, erodeOutFile);
            prettyPrintFile.write("Erosion Operation Output: \n");
            Morphology.prettyPrint(morphology.morphAry, prettyPrintFile);

            Morphology.zero2DAry(morphology.morphAry, morphology.rowSize, 
                                    morphology.colSize);
            morphology.computeOpening(morphology.zeroFramedAry, 
                                        morphology.morphAry, 
                                        morphology.tempAry);
            Morphology.aryToFile(morphology.morphAry, openingOutFile);
            prettyPrintFile.write("Opening Operation Output:\n");
            Morphology.prettyPrint(morphology.morphAry, prettyPrintFile);

            Morphology.zero2DAry(morphology.morphAry, morphology.rowSize, 
                                    morphology.colSize);
            morphology.computeClosing(morphology.zeroFramedAry, 
                                        morphology.morphAry, 
                                        morphology.tempAry);
            Morphology.aryToFile(morphology.morphAry, closingOutFile);
            prettyPrintFile.write("Closing Operation Output: \n");
            Morphology.prettyPrint(morphology.morphAry, prettyPrintFile);


            imgFile.close();
            structFile.close();
            dialateOutFile.close();
            erodeOutFile.close();
            closingOutFile.close();
            openingOutFile.close();
            prettyPrintFile.close();


        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
}
