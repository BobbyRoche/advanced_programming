#!/bin/bash
#Robert Roche
#CS265 Section 004

for filename in *
	do
		echo -n "$filename" 
		cat "$filename" | wc -wl
	done
:
