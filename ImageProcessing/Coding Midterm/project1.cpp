#include <iostream>
#include <fstream>


/*
Program Specs:
1. Compute histogram of the input image and display the histogram in two 
formats, see the output description below. 
2. Perform binary threshold operation on the input image with a given 
threshold value via argv[].
3. Output the result of the threshold in two formats, see the output 
description below
*/

class Image{
    public:

        //constructor
        Image(int nr, int nc, int mnv, int mxv, int tv);

        //destructor
        ~Image();
        
        //attributes
        int numRows;
        int numCols;
        int minVal;
        int maxVal;
        int* histArray;
        int thresholdVal;

        //functions
        void computeHist(std::ifstream& input);
        void printHist(std::ofstream& output);
        void dispHist(std::ofstream& output);
        void threshold(std::ifstream& input, 
                        std::ofstream& output1, 
                        std::ofstream& output2, int tv);

};

int main(int argc, char** argv){

    if(argc > 7 || argc < 7){
        std::cout << "incorrect command line args - must always"
                    "be in the format: " << std::endl;
        std::cout << "./program file_name threshold_value "
                    "output_file1 output_file2 output_file3 "
                    "output_file_4" << std::endl;
        return 0;
    }


    //store command line args
    std::string in_file_name = argv[1];
    int threshold_value = atoi(argv[2]);
    std::string out_file_names[4] = {argv[3], argv[4], argv[5], argv[6]};

    //open the files
    std::ifstream fin(in_file_name);
    std::ofstream fout1(out_file_names[0]), fout2(out_file_names[1]), 
                    fout3(out_file_names[2]), fout4(out_file_names[3]);

    //get the image's header info
    int image_header_info[4];
    for(int i = 0; i < 4; ++i){
        fin >> image_header_info[i];
    }


    //apparently not allowed to use the getline / stringstream method, 
    //leaving here in case of errors
    /*  
    std::string current_line;
    std::getline(fin, current_line);
    int image_header_info[4];
    std::string working_string;
    std::stringstream sstream(current_line);
    for(int i = 0; i < 4; ++i){
        std::getline(sstream, working_string, ' ');
        image_header_info[i] = stoi(working_string);
    }
    */
    
    //create image object
    Image image(image_header_info[0], image_header_info[1], 
            image_header_info[2], image_header_info[3], 
            threshold_value);

    //compute the histogram and print out to files
    image.computeHist(fin);
    image.printHist(fout1);
    image.dispHist(fout2);
    fin.close();
    
    //reopen input file
    fin.open(in_file_name);

    //output new thresholded image
    fout3 << "The threshold value used is " << threshold_value << "\n";
    fout4 << "The threshold value used is " << threshold_value << "\n";
    image.threshold(fin, fout3, fout4, threshold_value);

    //close all streams
    fin.close();
    fout1.close();
    fout2.close();
    fout3.close();
    fout4.close();

    return 0;
}

Image::Image(int nr, int nc, int mnv, int mxv, int tv) : numRows(nr), 
                numCols(nc), minVal(mnv), maxVal(mxv), thresholdVal(tv)
{
    int arr_end = mxv + 1;
    histArray = new int[arr_end];
    for(int i = 0; i < arr_end; ++i){
        histArray[i] = 0;
    }
}

Image::~Image(){
    delete[] histArray;
}

void Image::computeHist(std::ifstream& input){

    int total_pixels = numRows * numCols, working_int = 0;
    for(int i = 0; i < total_pixels; ++i){
        input >> working_int;
        histArray[working_int]++;
    }


    //apparently we cannot use the getline / stringstream method, 
    //leaving here in case of errors
    /* 
    std::string working_str;
    std::stringstream working_stream;
    while(!input.eof()){
        std::getline(input, working_str);
        working_stream = std::stringstream(working_str);
        while(!working_stream.eof()){
            std::getline(working_stream, working_str, ' ');
            if(working_str[0] < '0' || working_str[0] > '9') break;
            histArray[std::stoi(working_str)]++;
        }
    }
    */
}


void Image::printHist(std::ofstream& output){
    output << numRows << " " << numCols << " " << minVal 
            << " " << maxVal << "\n";
    int arr_end = maxVal + 1;
    for(int i = 0; i < arr_end; ++i){
        output << i << " " << histArray[i] << "\n";
    }
}


void Image::dispHist(std::ofstream& output){
    output << numRows << " " << numCols << " " << minVal
             << " " << maxVal << "\n";
    int arr_end = maxVal + 1;
    int max = 0;
    for(int i = 0; i < arr_end; ++i){
        output << i << " (" << histArray[i] << "):";
        max = histArray[i] > 70 ? 70 : histArray[i];
        for(int j = 0; j < max; ++j){
            output << "+";
        }
        output << "\n";
    }
}


void Image::threshold(std::ifstream& input, std::ofstream& output1, 
                        std::ofstream& output2, int tv){
    minVal = 0;
    maxVal = 1;
    int pixelVal = 0;
    output1 << numRows << " " << numCols << " " << minVal 
            << " " << maxVal << "\n";
    output2 << numRows << " " << numCols << " " << minVal 
            << " " << maxVal << "\n";

    //move passed the header
    for(int i = 0; i < 4; ++i){
        input >> pixelVal;
    }

    //read in the file and output depending on whether the value 
    //meets the given threshold tv
    for(int i = 0; i < numRows; ++i){
        for(int j = 0; j < numCols; ++j){
            input >> pixelVal;
            if(pixelVal >= tv){
                output1 << "1 ";
                output2 << "1 ";
            }
            else{
                output1 << "0 ";
                output2 << ". ";
            }        
        }
        output1 << "\n";
        output2 << "\n";

    }


    /* apparently we cant use the getline/stringstream method, 
    //leaving here in case of errors
    std::string working_str;
    std::stringstream working_stream;
    std::getline(input, working_str);
    while(!input.eof()){
        std::getline(input, working_str);
        working_stream = std::stringstream(working_str);
        while(!working_stream.eof()){
            std::getline(working_stream, working_str, ' ');
            if(working_str[0] < '0' || working_str[0] > '9') break;
            pixelVal = std::stoi(working_str);
            if(pixelVal >= tv){
                output1 << "1 ";
                output2 << "1 ";
            }
            else{
                output1 << "0 ";
                output2 << ". ";
            }
        }
        output1 << "\n";
        output2 << "\n";
    }
    */
}