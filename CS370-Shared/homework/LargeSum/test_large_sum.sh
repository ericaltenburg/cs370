#!/bin/bash

file=LargeSum.java

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
        interpreter=$(which python3)
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
    command="./${file%.*}"
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
    expected=$1
    received=$( $command 2>&1 | tr -d '\r' )
    if [ "$expected" = "$received" ]; then
        echo "success"
        (( ++num_right ))
    else
        echo -e "failure\n\nExpected$line\n$expected\nReceived$line\n$received\n"
    fi
}

(cat input01.txt) > input.txt
run_test "Full sum: 2907447669274202518718925946276466883113979921525384"$'\n'"First 10 digits: 2907447669"

(cat input02.txt) > input.txt
run_test "Full sum: 10427444155065985310343"$'\n'"First 10 digits: 1042744415"

(cat input03.txt) > input.txt
run_test "Full sum: 0"$'\n'"First 10 digits: 0"

(cat input04.txt) > input.txt
run_test "Full sum: 19999999999999999999999999999999999999999999999999800"$'\n'"First 10 digits: 1999999999"

(cat input05.txt) > input.txt
run_test "Full sum: 5537376230390876637302048746832985971773659831892672"$'\n'"First 10 digits: 5537376230"

(cat input06.txt) > input.txt
run_test "Full sum: 5268807420614299680984200708052087932695579011657740"$'\n'"First 10 digits: 5268807420"

(cat input07.txt) > input.txt
run_test "Full sum: 47999754440806310990036549320387205234951506885"$'\n'"First 10 digits: 4799975444"

(cat input08.txt) > input.txt
run_test "Full sum: 839"$'\n'"First 10 digits: 839"

(cat input09.txt) > input.txt
run_test "Full sum: 4535532980373166289555340390495264743589404654525293"$'\n'"First 10 digits: 4535532980"

(cat input10.txt) > input.txt
run_test "Full sum: 10000000000000000000000000000000000000000000000000100"$'\n'"First 10 digits: 1000000000"

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
