#/bin/bash 
file=ReciprocalCycles.java

if [ ! -f "$file" ]; then
    echo -e "Error: File '$file' not found.\nTest failed."
    exit 1
fi

touch log.txt
javac BReciprocalCycles.java 
javac ReciprocalCycles.java 
touch expected.txt
touch recieved.txt

for i in {1..2000}
do
	echo "Iteration: $i" 
	java ReciprocalCycles $i > recieved.txt
	java BReciprocalCycles $i > expected.txt 
	diff recieved.txt expected.txt > log.txt
	cat log.txt
	echo "-------------------ITERATION $i DONE-----------------------" >> log.txt
	echo "                                                           " >> log.txt
done
rm recieved.txt
rm expected.txt
rm ReciprocalCycles.class 
rm BReciprocalCycles.class 

