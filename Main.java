import java.io.*;
import java.text.DecimalFormat;
import java.util.*;



public class Main{
    private int gridNum = 0;
    private float gamma = 0;
    private float[] noise;
    private float[][] state;
    private boolean[][] isTerminal;
    private String fileName = "i1.txt";

    private void readFile(){
        try {
            File myFile = new File(fileName);
            Scanner myReader = new Scanner(myFile);
            for(int i=0;i<3;i=i){
                String data = myReader.nextLine();
                if(data.charAt(0)=='#'){
                    continue;
                }
                if(i==0){
                    gridNum = Integer.parseInt(data);
                    state = new float[gridNum][gridNum];
                    isTerminal = new boolean[gridNum][gridNum];
                    i++;
                    continue;
                }
                if(i==1){
                    String[] temp;
                    temp = data.split("#");
                    gamma = Float.parseFloat(temp[0]);
                    i++;
                    continue;
                }
                if(i==2){
                    String[] temp;
                    temp = data.split("#");
                    String noiseList=temp[0];
                    String[] temp2;
                    temp2 = noiseList.split("\\,");
                    noise = new float[temp2.length];
                    for(int j=0;j<noise.length;j++){
                        noise[j]=Float.parseFloat(temp2[j]);
                    }
                    i++;
                }
            }


            int row=0;
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                if(data.length()==0 || data.charAt(0)=='#'){
                    continue;
                }
                String[] temp;
                temp = data.split("#");
                String states=temp[0];
                states=states.trim();
                temp=states.split("\\,");
                int column = 0;
                for(String x:temp){
                    if(x.equals("X")){
                        state[row][column]=0;
                        isTerminal[row][column]=false;
                        column++;
                    }
                    else{
                        state[row][column]=Float.parseFloat(x);
                        isTerminal[row][column] = true;
                        column++;
                    }
                }
                row++;
            }
            myReader.close();
            System.out.println("grid:"+gridNum);
            System.out.println("gamma:"+gamma);
            for(int j=0;j<noise.length;j++){
                System.out.println(noise[j]);
            }
            for(int i=0;i<gridNum;i++){
                for(int j=0;j<gridNum;j++){
                    System.out.print(state[i][j]+" ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private float calculateValue(int direction, int row, int column, float nowState[][]){
        float result = 0;
        if(noise.length==3){
            if(direction==0){
                if(row==0){
                    if(column==0){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2];
                    }
                    else{
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2];
                    }
                }
                else{
                    if(column==0){
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2];
                    }
                    else{
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2];
                    }
                }
            }
            else if(direction==1){
                if(column==0){
                    if(row==0){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2];  
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2];
                    }
                    else{
                        result=nowState[row][column]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2];
                    }
                }
                else{
                    if(row==0){
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2];
                    }
                    else{
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2];
                    }
                }
            }
            else if(direction==2){
                if(row==(gridNum-1)){
                    if(column==0){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2];
                    }
                    else{
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2];
                    }
                }
                else{
                    if(column==0){
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2];
                    }
                    else{
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2];
                    }
                }
            }
            else if(direction==3){
                if(column==(gridNum-1)){
                    if(row==0){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2];
                    }
                    else{
                        result=nowState[row][column]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2];
                    }
                }
                else{
                    if(row==0){
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2];
                    }
                    else{
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2];
                    }
                }
            }
            }
        if(noise.length==4){
            if(direction==0){
                if(row==0){
                    if(column==0){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2]+nowState[row+1][column]*gamma*noise[3];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row+1][column]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2]+nowState[row+1][column]*gamma*noise[3];
                    }
                }
                else if(row==(gridNum-1)){
                    if(column==0){
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                }
                else{
                    if(column==0){
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2]+nowState[row+1][column]*gamma*noise[3];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row+1][column]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row-1][column]*gamma*noise[0]+nowState[row][column-1]*gamma*noise[1]+nowState[row][column+1]*gamma*noise[2]+nowState[row+1][column]*gamma*noise[3];
                    }
                }
            }
            else if(direction==1){
                if(column==0){
                    if(row==0){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row][column+1]*gamma*noise[3];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2]+nowState[row][column+1]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row][column]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2]+nowState[row][column+1]*gamma*noise[3];
                    }
                }
                else if(column==(gridNum-1)){
                    if(row==0){
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                }
                else{
                    if(row==0){
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row][column+1]*gamma*noise[3];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2]+nowState[row][column+1]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row][column-1]*gamma*noise[0]+nowState[row+1][column]*gamma*noise[1]+nowState[row-1][column]*gamma*noise[2]+nowState[row][column+1]*gamma*noise[3];
                    }
                }
            }
            else if(direction==2){
                if(row==(gridNum-1)){
                    if(column==0){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row-1][column]*gamma*noise[3];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2]+nowState[row-1][column]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2]+nowState[row-1][column]*gamma*noise[3];
                    }
                }
                else if(row==0){
                    if(column==0){
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }

                }
                else{
                    if(column==0){
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row-1][column]*gamma*noise[3];
                    }
                    else if(column==(gridNum-1)){
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2]+nowState[row-1][column]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row+1][column]*gamma*noise[0]+nowState[row][column+1]*gamma*noise[1]+nowState[row][column-1]*gamma*noise[2]+nowState[row-1][column]*gamma*noise[3];
                    }
                }
            }
            else if(direction==3){
                if(column==(gridNum-1)){
                    if(row==0){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row+1][column]*gamma*noise[2]+nowState[row][column-1]*gamma*noise[3];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column]*gamma*noise[0]+nowState[row-1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row][column-1]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row][column]*gamma*noise[0]+nowState[row-1][column]*gamma*noise[1]+nowState[row+1][column]*gamma*noise[2]+nowState[row][column-1]*gamma*noise[3];
                    }
                }
                else if(column==0){
                    if(row==0){
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row+1][column]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row-1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row-1][column]*gamma*noise[1]+nowState[row+1][column]*gamma*noise[2]+nowState[row][column]*gamma*noise[3];
                    }
                }
                else{
                    if(row==0){
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row][column]*gamma*noise[1]+nowState[row+1][column]*gamma*noise[2]+nowState[row][column-1]*gamma*noise[3];
                    }
                    else if(row==(gridNum-1)){
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row-1][column]*gamma*noise[1]+nowState[row][column]*gamma*noise[2]+nowState[row][column-1]*gamma*noise[3];
                    }
                    else{
                        result=nowState[row][column+1]*gamma*noise[0]+nowState[row-1][column]*gamma*noise[1]+nowState[row+1][column]*gamma*noise[2]+nowState[row][column-1]*gamma*noise[3];
                    }
                }
            }
            
            
        }
        return result;
    }

    private void valueIteration(){
        float VIState[][]=new float[gridNum][gridNum];
        for(int i=0;i<gridNum;i++){
            for(int j=0;j<gridNum;j++){
                VIState[i][j]=state[i][j];
            }
        }
        long startTime=System.nanoTime();
        boolean noChange = false;
        while(!noChange){
            noChange=true;
            for(int row = 0; row < gridNum;row++){
                for(int column=0;column < gridNum;column++){
                    if(isTerminal[row][column]==false){
                        float upValue=calculateValue(0, row, column, VIState);
                        float leftValue=calculateValue(1, row, column, VIState);
                        float downValue=calculateValue(2, row, column, VIState);
                        float rightValue=calculateValue(3, row, column, VIState);
                        float originalValue=VIState[row][column];
                        VIState[row][column]=Math.max(VIState[row][column],upValue);
                        VIState[row][column]=Math.max(VIState[row][column],leftValue);
                        VIState[row][column]=Math.max(VIState[row][column],downValue);
                        VIState[row][column]=Math.max(VIState[row][column],rightValue);
                        if(originalValue!=VIState[row][column]){
                            noChange=false;
                        }
                    }
                }
            }
        }
        long endTime=System.nanoTime();
        System.out.println("-----------------Value iteration-----------------");
        System.out.println("Cost time:"+ (endTime-startTime)+"ns");
        DecimalFormat df = new DecimalFormat("0.0");
        for(int i=0;i<gridNum;i++){
            for(int j=0;j<gridNum;j++){
                System.out.print(df.format(VIState[i][j])+" ");
            }
            System.out.println();
        }

    }

    private void policyIteration(){
        float PIState[][]=new float[gridNum][gridNum];
        int PIDir[][]=new int[gridNum][gridNum];
        for(int i=0;i<gridNum;i++){
            for(int j=0;j<gridNum;j++){
                PIState[i][j]=state[i][j];
                if(isTerminal[i][j]==false){
                    PIDir[i][j]=0;
                }
            }
        }
    
        long startTime=System.nanoTime();
        boolean noChangeStart = false;
        while(!noChangeStart){
                noChangeStart=true;
            for(int row = 0; row < gridNum;row++){
                for(int column=0;column < gridNum;column++){
                    if(isTerminal[row][column]==false){
                        float originalValue=PIState[row][column];
                        float upValue=calculateValue(0, row, column, PIState);
                        PIState[row][column]=Math.max(upValue, PIState[row][column]);
                        if(originalValue!=PIState[row][column]){
                            noChangeStart=false;
                        }
                    }
                }
            }  
        }

        boolean noDirectionChange = false;
        while(!noDirectionChange){
            noDirectionChange=true;
            for(int row = 0; row < gridNum;row++){
                for(int column=0;column < gridNum;column++){
                    float maxValue=0;
                    int bestDir=PIDir[row][column];
                    float upValue=calculateValue(0, row, column, PIState);
                    float leftValue=calculateValue(1, row, column, PIState);
                    float downValue=calculateValue(2, row, column, PIState);
                    float rightValue=calculateValue(3, row, column, PIState);
                    maxValue=Math.max(maxValue, upValue);
                    maxValue=Math.max(maxValue, leftValue);
                    maxValue=Math.max(maxValue, downValue);
                    maxValue=Math.max(maxValue, rightValue);
                    if(maxValue==upValue){
                        bestDir=0;
                    }
                    else if(maxValue==leftValue){
                        bestDir=1;
                    }
                    else if(maxValue==downValue){
                        bestDir=2;
                    }
                    else if(maxValue==rightValue){
                        bestDir=3;
                    }
                    if(bestDir!=PIDir[row][column]){
                        PIDir[row][column]=bestDir;
                        noDirectionChange=false;
                    }  
                }
            }
            boolean noChange = false;
            while(!noChange){
                noChange=true;
                for(int row = 0; row < gridNum;row++){
                    for(int column=0;column < gridNum;column++){
                        if(isTerminal[row][column]==false){
                            float originalValue=PIState[row][column];
                            float changedValue=calculateValue(PIDir[row][column], row, column, PIState);
                            PIState[row][column]=Math.max(changedValue, PIState[row][column]);
                            if(originalValue!=PIState[row][column]){
                                noChange=false;
                            }
                        }
                    }
                }  
            } 
        }

        long endTime=System.nanoTime();
        System.out.println("-----------------Policy iteration-----------------");
        System.out.println("Cost time:"+ (endTime-startTime)+"ns");
        DecimalFormat df = new DecimalFormat("0.0");
        for(int i=0;i<gridNum;i++){
            for(int j=0;j<gridNum;j++){
                System.out.print(df.format(PIState[i][j])+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.readFile();
        main.valueIteration();
        main.policyIteration();
    }
}