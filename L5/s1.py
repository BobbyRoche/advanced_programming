#!/usr/bin/env python

#Robert Roche
#s1 Python Program
import sys

fname = sys.argv[1]

f = sys.stdin

f = open(fname, "r" )
l = f.readline()
while l :
	l = l.strip()
	a = l.split(",")
	name = a[0]
	a.pop(0)
	new_list=[]
	for i in a:
		new_list.append(float(i))
	points =sum(new_list)
	total = len(new_list)
	average = points/total
	print('{0:7} {1:7.2f}'.format(name,average,))
	l = f.readline()
f.close()


