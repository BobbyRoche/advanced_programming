#!/usr/bin/env python3


import sys
import unittest

from gInt import gInt

class Test_gInt( unittest.TestCase ):

	def setup( self ):
		self.add = gInt(4,4)
		self.addcopy = gInt(4,4)
		self.add2 = gInt(20, 6)
		self.add2copy = gInt(20,6)
		self.add3 = gInt(0, 2)
		self.add3copy = gInt(0, 2)
		self.mul1 = gInt(2, 8)
		self.mul1copy = gInt(2, 8)
		self.mul2 = gInt(5, 3)
		self.mul2copy = gInt(5, 3)
		self.mult3 = gInt(-2, 3)
		self.mult3copy = gInt(-2, 3)
		self.norm1 = gInt(3, 1)
		self.norm2 = gInt(2, 2)

	def test_equals(self):
		self.assertEqual(add,add,'Number not equal to itself')
		self.assertEqual(add2,add2copy, 'Number not equa to copy of itself')
		self.assertNotEqual(self.add,self.add2, 'Non equal numbers are equivalent')


	def test_add(self):
		r = self.add + self.add2
		self.assertEqual(self.add, self.addcopy, 'Left operand changed after addition')
		self.assertEqual(self.add2, self.add2copy, 'Right operand changed after addition')
		self.assertEqual(r, gInt(24,10), 'Addition with add and add2 failed'
	
		r = self.add + self.add3
		self.assertEqual(self.add, self.addcopy, 'Left operand changed after addition')
		self.assertEqual(self.add3, self.add3copy, 'Right operand changed after addition')
		self.assertEqual(r, gInt(4, 6), 'Addition with ass and add3 failed')

		r = self.add2 + self.add3
		self.assertEqual(self.add2, self.add2copy, 'Left operand changed after addition')
		self.assertEqual(self.add3, self.add3copy, 'Right operand changed after addition')
		self.assertEqual(r, gInt(20, 8)



	def test_mul(self):
		r = self.mul1 * self.mul2
		self.assertEqual(self.mul1, self.mul1copy, 'Left operand changed during multiplication')
		self.assertEqual(self.mul2, self.mul2copy, 'Right operand changed during multiplication')
		self.assertEqual(r, gInt(-14, 46)

		r = self.mul2 * self.mul3
		self.assertEqual(self.mul2, self.mul2copy, 'Left operand changed during multiplication')
		self.assertEqual(self.mul3, self.mul3copy, 'Right operand changed during multiplication')
		assertEqual(r, gInt(-19, 7)

		r= self.mul1 * self.mul3
		self.assertEqual(self.mul1, self.mul1copy, 'Left operand changed during multiplication')
		self.assertEqual(self.mul3, self.mul3copy, 'Right operand changed during multiplication')
		asserEqual(r, gInt(-20, 4)


	def test_norm(self): 
		r = gInt.norm(self.norm1)
		self.assertEqual(r, 10, 'Normalize failed with norm1')

		r = gInt(self.norm2)
		self.assertEqual(r, 8, 'Normalize with norm 2 failed')

if __name__ == '__main__':
	sys.argv.append('-v')
	unnittest.main()
