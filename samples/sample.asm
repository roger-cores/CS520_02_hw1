    movc r10, 100           ; Select maximum value in list of primes



; Initialize list of numbers
    movc r1, 0              ; Load R1 with zero
init_list:
    store r1, r1            ; Store the value of R1 in memory location R1
    add r1, r1, 1           ; Increment R1
    cmp r2, r1, r10         ; Compare R1 to R10, put comparison code in R2
    bra lt, r2, init_list   ; Loop if counter < 100

    ; Mark 1 as not prime
    movc r1, 0              ; Load R1 with zero
    store r1, r1, 1         ; Store 0 in location 1 (R1+1)
    


    out r0
    out r1
    out r2
    out r3
    out r4
    out r5
    out r6
    out r7
    out r8
    out r9
    out r10
    out r11
    out r12
    ;out r13
    ;out r14
    ;out r15
    ;out r16
    ;out r17
    ;out r18
    ;out r19
    ;out r20
    ;out r21
    ;out r22
    ;out r23
    ;out r24
    ;out r25
    ;out r26
    ;out r27
    ;out r28
    ;out r29
    ;out r30
    ;out r31
HALT