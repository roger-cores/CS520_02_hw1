# CS520-02 HW1 Roger Correia

## About sieve.asm

 I was able to get the program **sieve.asm** run well on my simulator but since the program uses a different register for each number to be tested for prime number, it **only prints prime numbers under 30**. This is **because the simulator has 32 registers**. *(31 is not printed because register 31 is used to store results of CMP.)*

 The program had some other problems too. For example, register 2 is used to store results of register 2 and for some other calculations. I had to *movc r2, 2* to ensure that the program works correctly.

## The simulator supports all of the following:

* Data dependency
* Branch Resolution (CMP, JMP and BRA)
* All the instructions used in sieve.asm in the syntax they are used in (eg: two syntaxes of ADD. One with two and one with three arguments)
* All the comparison codes used in sieve.asm (Only EQ, LE, LT, GE, GT)


## The simulator lacks the following features:

* Stop fetching new instructions once HALT is encountered.
* Exception Handling

## Changes made outside implementation package

* Added integer codes in *EnumComparison*. These codes can then be stored in registers.
* Added *protected GlobalData globals;* in *PipelineStageBase* for convenience.
* Moved all the packages into com.cao.hw1 package for convenience.

## Track my progress on [Trello](https://trello.com/b/AXXj5xY3)

## Most Current Output:

```
r[2]	:	2
r[2]	:	3
r[2]	:	5
r[2]	:	7
r[2]	:	11
r[2]	:	13
r[2]	:	17
r[2]	:	19
r[2]	:	23
r[2]	:	29
```

