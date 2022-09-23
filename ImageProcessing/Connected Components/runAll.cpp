#include <windows.h>
#include <string>
#include <tchar.h>

using namespace std;

int main(){
    STARTUPINFO info14, info18, info24, info28, combo;
    ZeroMemory(&info14, sizeof(info14));
    info14.cb = sizeof(info14);
    ZeroMemory(&info18, sizeof(info18));
    info18.cb = sizeof(info18);
    ZeroMemory(&info24, sizeof(info24));
    info24.cb = sizeof(info24);
    ZeroMemory(&info28, sizeof(info28));
    info28.cb = sizeof(info28);
    ZeroMemory(&combo, sizeof(combo));
    combo.cb = sizeof(combo);
    
    PROCESS_INFORMATION pi14, pi18, pi24, pi28, piCombo;
    LPCWSTR path; 
    LPSTR comd;
    string s = "./main.exe data1.txt 4 rfprettyprint14.txt labelfile14.txt propertyFile14.txt";
    char *temp = new char[s.length()+1];
    strcpy(temp, s.c_str());
    comd = temp;
    CreateProcess(NULL, comd, NULL, NULL, TRUE, 0, NULL, NULL, &info14, &pi14);
    delete[] temp;
    s = "./main.exe data2.txt 4 rfprettyprint24.txt labelfile24.txt propertyFile24.txt";
    temp = new char[s.length()+1];
    strcpy(temp, s.c_str());
    comd = temp;
    CreateProcess(NULL, comd, NULL, NULL, TRUE, 0, NULL, NULL, &info24, &pi24);
    delete[] temp;
    HANDLE* h = new HANDLE[2]{pi14.hProcess, pi24.hProcess};
    WaitForMultipleObjects(2, h, TRUE, INFINITE);
    delete[] h;
    CloseHandle(pi14.hProcess);
    CloseHandle(pi14.hThread);
    CloseHandle(pi24.hProcess);
    CloseHandle(pi24.hThread);
    s = "./main.exe data1.txt 8 rfprettyprint18.txt labelfile18.txt propertyFile18.txt";
    temp = new char[s.length()+1];
    strcpy(temp, s.c_str());
    comd = temp;
    CreateProcess(NULL, comd, NULL, NULL, TRUE, 0, NULL, NULL, &info18, &pi18);
    delete[] temp;
    s = "./main.exe data2.txt 8 rfprettyprint28.txt labelfile28.txt propertyFile28.txt";
    temp = new char[s.length()+1];
    strcpy(temp, s.c_str());
    comd = temp;
    CreateProcess(NULL, comd, NULL, NULL, TRUE, 0, NULL, NULL, &info28, &pi28);
    h = new HANDLE[2]{pi18.hProcess, pi28.hProcess};
    WaitForMultipleObjects(2, h, TRUE, INFINITE);
    delete[] h;
    CloseHandle(pi18.hProcess);
    CloseHandle(pi18.hThread);
    CloseHandle(pi28.hProcess);
    CloseHandle(pi28.hThread);
    s = "python combine.py";
    temp = new char[s.length() + 1];
    strcpy(temp, s.c_str());
    comd = temp;
    CreateProcess(NULL, comd, NULL, NULL, TRUE, 0, NULL, NULL, &combo, &piCombo);
    h = new HANDLE{&piCombo.hProcess};
    WaitForSingleObject(h, INFINITE);
    delete h;
    CloseHandle(piCombo.hProcess);
    CloseHandle(piCombo.hThread);
}