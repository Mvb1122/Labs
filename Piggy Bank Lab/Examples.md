## Piggy Bank Lab Parse examples
 1. 36 quarters, 25 pennies
 3. 13 dimes, 5 nickels
 4. 26 dollars, 5 half-dollars
 5. 13 pennies, 36 nickels, 12 dollars, 15 quarters, 12 half dollars
 6. 12 pennies, 12 pennies, 12 pennies, 12 pennies, 14 quarters

# Rules
As you can probably tell, you can append any number of any of the coins, and those coins are:

	Pennies
	Nickels
	Dimes
	Quarters
	Half-Dollars
	Dollars
Additionally, duplicate declarations are also allowed, so you can do something like in Example #6, where you place the same coin multiple times in a line. The parser just adds the declarations, so #6 has an apparent value of 48 pennies, 14 quarters.
