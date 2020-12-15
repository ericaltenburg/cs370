#!/bin/bash

file=hashit.c

if [ ! -f "$file" ]; then
    echo -e "Error: File '$file' not found.\nTest failed."
    exit 1
fi

num_right=0
total=0
line="________________________________________________________________________"
compiler=
interpreter=
language=
extension=${file##*.}
if [ "$extension" = "py" ]; then
    if [ ! -z "$PYTHON_PATH" ]; then
        interpreter=$(which python.exe)
    else
        interpreter=$(which python3.2)
    fi
    command="$interpreter $file"
    echo -e "Testing $file\n"
elif [ "$extension" = "java" ]; then
    language="java"
    command="java ${file%.java}"
    echo -n "Compiling $file..."
    javac $file
    echo -e "done\n"
elif [ "$extension" = "c" ] || [ "$extension" = "cpp" ]; then
    language="c"
    command="valgrind --leak-check=full ./${file%.*}"
    echo -n "Compiling $file..."
    results=$(make 2>&1)
    if [ $? -ne 0 ]; then
        echo -e "\n$results"
        exit 1
    fi
    echo -e "done\n"
fi

run_test() {
    (( ++total ))
    echo -n "Running test $total..."
    expected=$2
    received=$( echo -e $1 | $command 2>&1 | tr -d '\r' )
    if [ "$expected" = "$received" ]; then
        echo "success"
        (( ++num_right ))
    else
        echo -e "failure\n\nExpected$line\n$expected\nReceived$line\n$received\n"
    fi
}
run_test "1
\n13
\nADD:marsz
\nADD:marsz
\nDEL:marsz
\nADD:marsz
\nADD:Dabrowski
\nADD:z
\nADD:ziemii
\nADD:wloskiej
\nADD:do
\nADD:Polski
\nDEL:od
\nDEL:do
\nDEL:wloskiej" ""

run_test "1
\n3
\nADD:e
\nDEL:e
\nADD:eD" ""

run_test "1
\n21
\nADD:B
\nADD:XZ
\nADD:ZY 
\nADD:bU 
\nADD:dT 
\nADD:fS 
\nADD:hR 
\nADD:jQ 
\nADD:lP 
\nADD:nO 
\nADD:pN 
\nADD:rM 
\nADD:tL 
\nADD:vK 
\nADD:xJ 
\nADD:zI 
\nADD:AEY 
\nADD:AHW 
\nADD:AKU 
\nADD:ANS 
\nADD:AQQ" ""

run_test "2
\n11
\nADD:marsz
\nADD:marsz
\nADD:Dabrowski
\nADD:z
\nADD:ziemii
\nADD:wloskiej
\nADD:do
\nADD:Polski
\nDEL:od
\nDEL:do
\nDEL:wloskiej
\n3
\nADD:aac
\nADD:aace
\nDEL:aac" ""

run_test "1
\n13
\nADD:marsz
\nADD:marsz
\nDEL:marsz
\nADD:marsz
\nADD:Dabrowski
\nADD:z
\nADD:ziemii
\nADD:wloskiej
\nADD:do
\nADD:Polski
\nDEL:od
\nDEL:do
\nDEL:wloskiej" ""

run_test "1
\n3
\nADD:e
\nDEL:e
\nADD:eD" ""

run_test "1
\n10
\nADD:ftchnu
\nADD:rwpheb
\nADD:ejkchx
\nADD:ybktmg
\nADD:tgyrmq
\nADD:bpsfov
\nADD:nmckrz
\nADD:vpxabg
\nADD:zogwfe
\nADD:bltgej" ""

run_test "1
\n4
\nADD:k 
\nADD:W 
\nDEL:k 
\nADD:W" ""

run_test "1
\n3
\nADD:acc
\nADD:acce
\nDEL:acc" ""

run_test "1
\n4
\nADD:papa 
\nADD:papa
\nDEL:papa
\nADD:papa" ""

run_test "1
\n4
\nADD:aac
\nADD:aace
\nDEL:aac
\nADD:aace" ""

run_test "5
\n13
\nADD:marsz
\nADD:marsz
\nDEL:marsz
\nADD:marsz
\nADD:Dabrowski
\nADD:z
\nADD:ziemii
\nADD:wloskiej
\nADD:do
\nADD:Polski
\nDEL:od
\nDEL:do
\nDEL:wloskiej
\n3
\nADD:e
\nDEL:e
\nADD:eD
\n3
\nADD:aac
\nADD:aace
\nDEL:aac 
\n10
\nADD:ftchnu
\nADD:rwpheb
\nADD:ejkchx
\nADD:ybktmg
\nADD:tgyrmq
\nADD:bpsfov
\nADD:nmckrz
\nADD:vpxabg
\nADD:zogwfe
\nADD:bltgej
\n4
\nADD:papa 
\nADD:papa
\nDEL:papa
\nADD:papa" ""

run_test "1
\n4
\nADD:bbb
\nDEL:bbb
\nADD:aac
\nDEL:bbb" ""

run_test "1
\n5
\nADD:bbb
\nDEL:bbb
\nADD:aac
\nDEL:bbb
\nADD:bbb" ""

run_test "1
\n4
\nADD:mohit
\nADD:ca
\nDEL:aac 
\nADD:aac" "" 

run_test "1
\n4
\nADD:mohit
\nADD:cA
\nDEL:aac 
\nADD:aac" ""

run_test "1 
\n4
\nADD:mohit
\nADD:cA
\nDEL:aac 
\nADD:aac" "" 

run_test "1 
\n4
\nADD:mohit
\nADD:ca
\nDEL:aac 
\nADD:aac" ""

run_test "2
\n13
\nADD:marsz
\nADD:marsz
\nDEL:marsz
\nADD:marsz
\nADD:Dabrowski
\nADD:z
\nADD:ziemii
\nADD:wloskiej
\nADD:do
\nADD:Polski
\nDEL:od
\nDEL:do
\nDEL:wloskiej
\n13
\nADD:marsz
\nADD:marsz
\nDEL:marsz
\nADD:marsz
\nADD:Dabrowski
\nADD:z
\nADD:ziemii
\nADD:wloskiej
\nADD:do
\nADD:Polski
\nDEL:od
\nDEL:do
\nDEL:wloskiej" ""

run_test "1
\n4
\nADD:k 
\nADD:W 
\nDEL:k 
\nADD:W" ""

run_test "1 
\n3 
\nADD:aac 
\nADD:aace 
\nDEL:aac" ""

run_test "1 
\n1
\nADD:aac     
\nADD:aace  
\nDEL:aac" ""

run_test "2
\n11
\nADD:marsz
\nADD:marsz
\nADD:Dabrowski
\nADD:z
\nADD:ziemii
\nADD:wloskiej
\nADD:do
\nADD:Polski
\nDEL:od
\nDEL:do
\nDEL:wloskiej
\n3
\nADD:aac
\nADD:aace
\nDEL:aac" ""

run_test "1 
\n22 
\nADD:B 
\nADD:XZ 
\nADD:ZY 
\nADD:bU 
\nADD:dT 
\nADD:fS 
\nADD:hR 
\nADD:jQ 
\nADD:lP 
\nADD:nO 
\nADD:pN 
\nADD:rM 
\nADD:tL 
\nADD:vK 
\nADD:xJ 
\nADD:zI 
\nADD:AEY 
\nADD:AHW 
\nADD:AKU 
\nADD:ANS 
\nADD:AQQ
\nDEL:AQQ" ""

run_test "1 
\n22 
\nADD:B 
\nADD:XZ 
\nADD:ZY 
\nADD:bU 
\nADD:dT 
\nADD:fS 
\nADD:hR 
\nADD:jQ 
\nADD:lP 
\nADD:nO 
\nADD:pN 
\nADD:rM 
\nADD:tL 
\nADD:vK 
\nADD:xJ 
\nADD:zI 
\nADD:AEY 
\nADD:AHW 
\nADD:AKU 
\nADD:ANS 
\nADD:AQQ
\nDEL:ANS" ""

echo -e "\nTotal tests run: $total"
echo -e "Number correct : $num_right"
echo -n "Percent correct: "
echo "scale=2; 100 * $num_right / $total" | bc

if [ "$language" = "java" ]; then
    echo -e -n "\nRemoving class files..."
    rm -f *.class
    echo "done"
elif [ "$language" = "c" ]; then
    echo -e -n "\nCleaning project..."
    make clean > /dev/null 2>&1
    echo "done"
fi
