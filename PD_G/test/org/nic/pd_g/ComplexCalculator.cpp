/*
*	---ComplexCalculator---
*	A program to add, subtract, multiply and divide two complex numbers
*
*
*	@Version 0.1A
*	@Author N.Ballmann, M.Blunck, D.Burken, A.Hützen
*/

// header
#include "stdafx.h"
#include <conio.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <time.h>
#include <Windows.h>

//Preprocessor:
#define AE (unsigned char)142 
#define ae (unsigned char)132 
#define OE (unsigned char)153 
#define oe (unsigned char)148 
#define UE (unsigned char)154 
#define ue (unsigned char)129 
#define ss (unsigned char)225 

#define ADDITION 1
#define SUBTRACTION 2
#define MULTIPLICATION 3
#define DIVISION 4


/**
* Definition of a complex number
* with a real and an imaginary part
* defined as two doubles
*/
typedef struct
{
	double real, imag;
} complexNumber;


/**
* Addition of two complex numebrs
* @param a, b	complex numbers for mathematical operation
*
*/
inline complexNumber complexAdd(complexNumber a, complexNumber b)
{
	complexNumber result;
	result.real = a.real + b.real;
	result.imag = a.imag + b.imag;
	return result;
}

/**
* Subtraction of two complex numbers
* @param a, b	complex numbers for mathematical operation	
*
*/
inline complexNumber complexSub(complexNumber a, complexNumber b)
{
	complexNumber result;
	result.real = a.real - b.real;
	result.imag = a.imag - b.imag;
	return result;
}

/**
* Multiplication of two complex numbers
* @param a, b	complex numbers for mathematical operation
*
*/
inline complexNumber complexMul(complexNumber a, complexNumber b)
{
	complexNumber result;
	result.real = a.real * b.real - a.imag * b.imag;
	result.imag = a.real * b.imag + a.imag * b.real;
	return result;
}

/**
* Division of two complex Numbers
* @param a, b	complex numbers for mathematical operation
*
* b.real and b.imag can not be zero -> division through zero
*/
inline complexNumber complexDiv(complexNumber a, complexNumber b)
{
	complexNumber result = { -1,-1 };

	if(b.real != 0 && b.imag != 0)
	{
		
		result.real = (a.real * b.real + a.imag * b.imag) / (b.real * b.real + b.imag * b.imag);
		result.imag = (a.imag * b.real - a.real * b.imag) / (b.real * b.real + b.imag * b.imag);	
	}
	else
	{
		printf_s("\nReal- und Imagin%crteil der zweiten komplexen Zahl ist 0! Division durch 0 nicht erlaubt!", ae);
		Sleep(2000);
	}
	
	return result;
}

// globals
complexNumber cnum1;
complexNumber cnum2;
char nextAction;

/*
* @param result	the result of a mathematical operation
*
* prints the complex number result in the form: result.real + result.imag*i 
* to the command line
*/
void showResult(complexNumber result)
{
	printf_s("\n\n%.2lf + %.2lfi", result.real, result.imag);
}

/**
* Gives the user the choice of the mathematical operation 
* to be executed
*
* 1: ADDITION | 2: SUBTRACTION | 3: MULTIPLICATION | 4: DIVISION
*
* recursive exception-handling for wrong user input
*/
void showMenu()
{
	system("CLS");
	printf_s("Welche Rechnenoperation m%cchten Sie durchf%chren?", oe, ue); 
	printf_s("\n1: Addition\n2: Subtraktion\n3: Multiplikation\n4: Division\n");

	int userInput;

	scanf_s("%d", &userInput); fflush(stdin);

	switch (userInput)
	{
	case ADDITION:
		printf_s("\nDas Ergebnis der Operation Zahl1 + Zahl2 lautet:");
		showResult(complexAdd(cnum1, cnum2));
		break;

	case SUBTRACTION:
		printf_s("\nDas Ergebnis der Operation Zahl1 - Zahl2 lautet:");	
		showResult(complexSub(cnum1, cnum2));
		break;

	case MULTIPLICATION:
		printf_s("\nDas Ergebnis der Operation Zahl1 * Zahl2 lautet:");
		showResult(complexMul(cnum1, cnum2));
		break;

	case DIVISION:
		printf_s("\nDas Ergebnis der Operation Zahl1 / Zahl2 lautet:");
		showResult(complexDiv(cnum1, cnum2));
		break;

	default: 
		printf_s("\n%d, ist keine gueltige Eingabe...\nBitte wiederholen:\n", userInput);

		showMenu(); // recursive handling of wrong user input
	}
}

/*
* asks the user for the next step to be taken
*
* 1: another operation on the existing complex numbers
* 2: get two new complex numbers
* any other char: exit the program
*/
void nextStep()
{
	printf_s("\n\nWas m%cchten Sie als n%cchstes tun?", oe, ae);
	printf_s("\n1: Eine weitere Rechenoperation durchf%chren", ue);
	printf_s("\n2: Zwei neue komplexe Zahlen eingeben");
	printf_s("\nBeliebige Taste: Das Programm beenden\n");
	
	nextAction = fgetc(stdin);	
}

/*
* Getter for two complex numbers
*
*/
void getComplexNumbers()
{
	printf_s("\nBitte zwei Komplexe Zahlen eingeben.\nRealteil Zahl 1:");
	scanf_s("%lf", &cnum1.real); fflush(stdin);
	printf_s("\nImagin%crteil Zahl 1:", ae);
	scanf_s("%lf", &cnum1.imag); fflush(stdin);
	printf_s("\nRealteil Zahl 2:");
	scanf_s("%lf", &cnum2.real); fflush(stdin);
	printf_s("\nImagin%crteil Zahl 2:", ae);
	scanf_s("%lf", &cnum2.imag); fflush(stdin);
}

/*
* Main
* using goto statements for fun and profit
*/
int main(void)
{
	printf_s("Willkommen zum lustigen rechnen mit komplexen Zahlen!\n");  // start of the calculator -> welcome the user
	
	NewComplex:				// goto case 2
	getComplexNumbers();	 // ask for user input of two complex numbers

	NextOperation:			// goto case 1
	showMenu();				// ask the user for the mathematical operation to be used on the complex numbers
	
	nextStep();				// ask the user what has to be done afterwards
	switch(nextAction)
	{
	case '1':
		goto NextOperation;
	case '2':
		goto NewComplex;
	default: 
		goto Exit;
	}

	Exit:					// goto case 3
	printf_s("\n\nAuf Wiedersehen!");
	_getch();
	system(EXIT_SUCCESS);	// close the console
	return 0;				// exit the program
}

