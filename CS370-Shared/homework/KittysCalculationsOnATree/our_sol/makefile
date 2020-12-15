#Authors: Eric Altenburg, Hamzah Nizami, Constance Xu
#Description: Makefile for Kitty's Calculations on the Tree HackerRank
#May 5th, 2020
#I pledge my honor that I have abided by the Stevens Honor System.

CXX      = g++
CXX_FILE = $(wildcard *.cpp)
TARGET   = $(patsubst %.cpp,%,$(CXX_FILE))
CXXFLAGS = -g -fmessage-length=0 -std=c++14

all:
	$(CXX) $(CXXFLAGS) $(CXX_FILE) -o $(TARGET)
clean:
	rm -f $(TARGET) $(TARGET).exe
