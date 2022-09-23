import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Random;
import java.util.StringTokenizer;

public class Kmean{

    public int k, numPts, numRows, numCols, change;
    public int[][] displayArray;
    public Point[] pointSet;
    public Point[] kCentroidArray;
    

    public Kmean(BufferedReader br, int K){
        change = 0;
        k = K;
        try{
            StringTokenizer st = new StringTokenizer(br.readLine());
            numRows = Integer.parseInt(st.nextToken());
            numCols = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            numPts = Integer.parseInt(st.nextToken());

            displayArray = new int[numRows][numCols];

            pointSet = new Point[numPts];

            kCentroidArray = new Point[k + 1];

            loadPointSet(br, st);

        }catch(Exception E){
            System.out.println(E.getMessage());
            System.exit(0);
        }
    }


    public void loadPointSet(BufferedReader br, StringTokenizer st){
        try{
            for(int i = 0; i < numPts; ++i){
                st = new StringTokenizer(br.readLine());
                pointSet[i] = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
            }
        }catch(Exception E){
            System.out.println(E.getMessage());
            System.exit(0);
        }
    }


    public void kMeansClustering(BufferedWriter first, BufferedWriter second){
        int iter = 0, change, minLabel;
        selectKCentroids();
        do{
            change = 0;
            ++iter;
            plotDisplayArray();
            prettyPrint(first, iter);
            for(Point p : pointSet){
                minLabel = distanceMinLabel(p, p.distance);
                if (p.label != minLabel){
                    p.label = minLabel;
                    p.distance = computeDistance(p, p.label);
                    ++change;
                }
            }
            if(change >= 2) computeCentroids();
        }while(change > 2);

        try{
            second.write(Integer.toString(numRows) + " " + Integer.toString(numCols) + "\n");
            second.write(Integer.toString(numPts) + "\n");
            for(Point P : pointSet){
                second.write(Integer.toString((int)P.x) + " " + Integer.toString((int)P.y) + " " + Integer.toString((int)P.label) + "\n");
            }
        }catch(Exception E){
            System.out.println(E.getMessage());
            System.exit(0);
        }
    }


    public void selectKCentroids(){
        boolean[] arr = new boolean[numPts];
        Random rand = new Random();
        for(int i = 0; i < arr.length; ++i){
            arr[i] = true;
        }
        int index;
        for(int i = 1; i <= k; ++i){
            index = 0;
            do{
                index = rand.nextInt(numPts);
            }while(!arr[index]);
            kCentroidArray[i] = new Point(pointSet[index].x, pointSet[index].y);
            kCentroidArray[i].label = index;
            kCentroidArray[i].distance = 0;
            arr[index] = false;
        }

        for(Point p : pointSet){
            p.label = rand.nextInt(k) + 1;
            p.distance = computeDistance(p, p.label);
        }
    }

    public void computeCentroids(){
        double[] sumX = new double[k+1];
        double[] sumY = new double[k+1];
        int[] totalPt = new int[k+1];
        for(int i = 0; i <= k; ++i){
            sumX[i] = 0.0;
            sumY[i] = 0.0;
            totalPt[i] = 0;
        }
        for(int i = 0; i < numPts; ++i){
            sumX[pointSet[i].label] += pointSet[i].x;
            sumY[pointSet[i].label] += pointSet[i].y;
            totalPt[pointSet[i].label]++;
        }
        for(int i = 1; i <= k; ++i){
            if(totalPt[i] > 0){
                kCentroidArray[i].x = sumX[i] / totalPt[i];
                kCentroidArray[i].y = sumY[i] / totalPt[i];
            }
        }
    }

    public int distanceMinLabel(Point p, double minDist){
        int minLabel = p.label;
        double currDist = 0;
        for(int i = 1; i <= k; ++i){
            currDist = computeDistance(p, i);
            if (currDist < minDist){
                minDist = currDist;
                minLabel = i;
            }
        }
        return minLabel;
    }

    public double computeDistance(Point p, int centroidLabel){
        double dx = p.x - kCentroidArray[centroidLabel].x;
        double dy = p.y - kCentroidArray[centroidLabel].y;
        return Math.sqrt(dx*dx + dy*dy);
    }


    public void plotDisplayArray(){
        for(int i = 0; i < numRows; ++i){
            for(int j = 0; j < numCols; ++j){
                displayArray[i][j] = 0;
            }
        }
        int i = 0;
        int j = 0;
        for(Point p : pointSet){
            i =  (int)Math.floor(p.x);
            j = (int)Math.floor(p.y);
            displayArray[i][j] = p.label;
        }
    }

    public void prettyPrint(BufferedWriter bw, int iteration){
        try{
            char[] outp = new char[1];
            bw.write("Printing Iteration " + Integer.toString(iteration)+ " and k = " + Integer.toString(k) + "\n");
            for(int i = 0; i < numRows; ++i){
                for(int j = 0; j < numCols; ++j){
                    int value = 'A' - 1 + displayArray[i][j];
                    if (value < 'A') outp[0] = '.';
                    else outp[0] = (char)value;
                    bw.write(outp);
                    //bw.write(" ");
                }
                bw.write("\n");
            }
        }catch(Exception E){
            System.out.println(E.getMessage());
            System.exit(0);
        }
    }
}