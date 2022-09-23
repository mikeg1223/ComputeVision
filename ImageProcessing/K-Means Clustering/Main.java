import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    
    public static void main(String[] args){
        try{
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            int k = Integer.parseInt(args[1]);
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(args[2]));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(args[3]));

            Kmean kmean = new Kmean(br, k);
            
            kmean.kMeansClustering(bw1, bw2);
            

            br.close();
            bw1.close();
            bw2.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
