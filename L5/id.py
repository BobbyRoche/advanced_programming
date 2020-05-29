#!/usr/bin/env python
#Robert Roche
#ID Python Program



import sys
import pprint


if len(sys.argv)<=1:
	f = open("tempfile.txt", "w")
	lines = sys.stdin.readlines()
	f.writelines(lines)
	f.close()
	f = open("tempfile.txt","r")

else:
	fname = sys.argv[1]
	f = sys.stdin
	f = open(fname, "r" )
	
	
l = f.readline()
d = dict()
while l :
	l = l.strip()
	a = l.split(" ",1)
	key = int(a[0])
	a.pop(0)
	for i in a:
		d.update({key : a})
	l = f.readline()

sort_keys = sorted(d)

i = 0
while i < len(sort_keys):
	print('{:<7}{:>7s}').format(sort_keys[i],d[sort_keys[i]])
	i+=1


f.close()


															
															

