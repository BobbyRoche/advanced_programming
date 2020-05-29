#!usr/bin/env python3

###############################################

#Robert Roche

#Assignment2

#CS265--004

#This program converts infix expressions
#to postfix expressions and then evaluates them
################################################


import sys


postfix = []
temp = []


def evalPostfix(post):
	for token in post:
		if token.isdigit():
			temp.append(token)
		elif token is '+' or token is '-' or token is '*' or token is '%' or token is '/':
			y = temp.pop()
			x = temp.pop()
			temp.append(str(eval(x + token + y)))
	return temp.pop()
	


def precedence(s):
    if s is '(':
        return 0
    elif s is '+' or s is '-':
        return 1
    elif s is '*' or s is '/' or s is '%':
        return 2
    else:
        return 99
                 
def infix2postfix(infix): #this function takes the infix expression and converts it to post fix
	infix.append(')')		  
	temp.append('(')
	for i in infix :	#checks each element in 'infix' and performs necessary operations to convert it to postfix

		if i is '(' :		
			temp.append(i)

		elif i is ')' :
			next = temp.pop()
			while next is not '(':
				postfix.append(next)
				next = temp.pop()
			
		elif i is '+' or i is '-' or i is '*' or i is '%' or i is '/':
			p = precedence(i)
			while len(temp) is not 0 and p <= precedence(temp[-1]):
				postfix.append(temp.pop())
			temp.append(i)

		elif i is ' ':
			continue

		elif i.isdigit():
			postfix.append(i)
                 
	while len(temp) > 0 :	
		postfix.append(temp.pop())
	word = ""     							#makes the postfix notaion into 1 string
	for i in postfix:
		word = word + i + " "
	del postfix[:]
	return word

lineList = []
newList = []
if len(sys.argv)>=2:
	for line in open(sys.argv[1]):
		if len(line) <= 120:
			lineList.append(line.rstrip()) #if a line exceeds 120 characters it ignores that line
			
else:
	for line in sys.stdin:
		if len(line) <= 120:
			lineList.append(line.rstrip())
for line in lineList:
	exp = line.split()
	newList.append(infix2postfix(exp))
for i in newList:
	exp = i.split()
	print i + " = " + evalPostfix(exp)

