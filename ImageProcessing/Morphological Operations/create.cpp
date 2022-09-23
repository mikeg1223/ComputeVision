#include <iostream>
#include <fstream>
#include <windows.h>
#include <string>

using namespace std;

int main(){
    string output, p, dn, d;

    for(int i = 1; i <= 2; ++i){
        for(int j = 1; j <=3; ++j){
            
            output = "echo Image " + to_string(i) + ", Structured Element " + to_string(j) + " >> output1.txt";
            system(output.c_str());
            output = "echo **************************************** >> output1.txt";
            system(output.c_str());
            p = "type prettyprint" + to_string(i) + to_string(j) + ".txt >> output1.txt";
            system(p.c_str());
            dn = "echo Dialation: >> output1.txt";
            system(dn.c_str());
            d ="type dialation" + to_string(i) + to_string(j) + ".txt >>  output1.txt";
            system(d.c_str());
            dn = "echo Erosion: >>  output1.txt";
            system(dn.c_str());
            d ="type erosion" + to_string(i) + to_string(j) + ".txt >>  output1.txt";
            system(d.c_str());
            dn = "echo Opening: >>  output1.txt";
            system(dn.c_str());
            d ="type opening" + to_string(i) + to_string(j) + ".txt >>  output1.txt";
            system(d.c_str());
            dn = "echo Closing: >>  output1.txt";
            system(dn.c_str());
            d ="type closing" + to_string(i) + to_string(j) + ".txt >>  output1.txt";
            system(d.c_str());
            
            
        }
    }
}