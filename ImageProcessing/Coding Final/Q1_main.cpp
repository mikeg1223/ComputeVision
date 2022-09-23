#include <fstream>
#include <iostream>

class Rle{
    public:
    int numRow, numCol, minVal, maxVal;
    int startCol, startRow, length, current;

    Rle();

    void run(std::ifstream& i, std::ofstream& o);


};


int main(int argv, char** args){

 
    std::string input_filename = args[1];
    std::string output_filename = args[2];
    std::ifstream inp(input_filename);
    std::ofstream outp(output_filename);

    Rle rle;
    rle.run(inp, outp);
    
    inp.close();
    outp.close();
    
}

Rle::Rle(){
    startCol = 0;
    startRow = 0;
    length = 0;
    current = -1;
}

void Rle::run(std::ifstream& i, std::ofstream& o){
    i >> numRow;
    i >> numCol;
    i >> minVal;
    i >> maxVal;
    o << numRow;
    o << " ";
    o << numCol;
    o << " ";
    o << minVal;
    o << " ";
    o << maxVal;
    o << "\n";

    int next;

    for(int row = 0; row < numRow; ++row){
        i >> current;
        o << row;
        o << " ";
        o << 0;
        o << " ";
        o << current;
        o << " ";
        length = 1;
        for(int col = 1; col < numCol; ++col){
            i >> next;
            if(next != current){
                current = next;
                o << length;
                o << " ";
                o << row;
                o << " ";
                o << col;
                o << " ";
                o << current;
                o << " ";
                length = 1;
            }
            else{
                ++length;
            }
        }
        o << length;
        o << " ";
    }
}