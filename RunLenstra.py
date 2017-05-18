import os
import sys

if len(sys.argv) != 2:
    print "Usage: python RunLenstra.py <input file name>"
else:
    fileName = sys.argv[1]
    numbers = open(fileName, 'r')
    for number in numbers.readlines():
        os.system("java Lenstra " + number)
    numbers.close()
