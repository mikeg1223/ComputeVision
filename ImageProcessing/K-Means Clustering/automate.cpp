#include <windows.h>
#include <string>
using namespace std;

int main(){
    string input = "data.txt";
    string outputs[2][3][4];
    for(int i = 0; i < 2; ++i){
        for(int j = 0; j < 3; ++j){
            for(int k = 0; k < 4; ++k){
                outputs[i][j][k] = "outputs" + to_string(i+1) + to_string(j+3) + to_string(k+1) + ".txt";
            }
        }
    }

    for(int j = 0; j < 3; ++j){
        for(int k = 0; k < 4; ++k){
            STARTUPINFO sui;
            ZeroMemory(&sui, sizeof(sui));
            sui.cb = sizeof(sui);
            PROCESS_INFORMATION pi;
            LPSTR comd;
            string s = "java Main " + input + " " + to_string(j+3) + " " + outputs[0][j][k] + " " + outputs[1][j][k]  ;
            char *temp = new char[s.length()+1];
            strcpy(temp, s.c_str());
            comd = temp;
            CreateProcess(NULL, comd, NULL, NULL, TRUE, 0, NULL, NULL, &sui, &pi);
            HANDLE h(pi.hProcess);
            WaitForSingleObject(h, INFINITE);
        }
    }
     
    
}