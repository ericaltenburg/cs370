#!/bin/bash

file=ReciprocalCycles.java

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
if [ "${file##*.}" = "py" ]; then
    if [ ! -z "$PYTHON_PATH" ]; then
        interpreter=$(which python.exe)
    else
        interpreter=$(which python3.2)
    fi
    command="$interpreter $file"
    echo -e "Testing $file\n"
elif [ "${file##*.}" = "java" ]; then
    language="java"
    command="java ${file%.java}"
    echo -n "Compiling $file..."
    javac $file
    echo -e "done\n"
fi

run_test_args() {
    (( ++total ))
    echo -n "Running test $total..."
    expected=$2
    received=$( $command $1 2>&1 | tr -d '\r' )
    if [ "$expected" = "$received" ]; then
        echo "success"
        (( ++num_right ))
    else
        echo -e "failure\n\nExpected$line\n$expected\nReceived$line\n$received\n"
    fi
}

run_test_args "" "Usage: java ReciprocalCycles <denominator>"
run_test_args "1 2 3" "Usage: java ReciprocalCycles <denominator>"
run_test_args "-13" "Error: Denominator must be an integer in [1, 2000]. Received '-13'."
run_test_args "euler" "Error: Denominator must be an integer in [1, 2000]. Received 'euler'."
run_test_args "1" "1/1 = 1"
run_test_args "2" "1/2 = 0.5"
run_test_args "3" "1/3 = 0.(3), cycle length 1"
run_test_args "4" "1/4 = 0.25"
run_test_args "5" "1/5 = 0.2"
run_test_args "6" "1/6 = 0.1(6), cycle length 1"
run_test_args "7" "1/7 = 0.(142857), cycle length 6"
run_test_args "8" "1/8 = 0.125"
run_test_args "9" "1/9 = 0.(1), cycle length 1"
run_test_args "10" "1/10 = 0.1"

echo -e "\nTotal tests run: $total"
echo -e "Number correct : $num_right"
echo -n "Percent correct: "
echo "scale=2; 100 * $num_right / $total" | bc

if [ "$language" = "java" ]; then
   echo -e -n "\nRemoving class files..."
   rm -f *.class
   echo "done"
fi
