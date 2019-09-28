# makefile

.SUFFIXES: .class .java

TARG=Test
JC=javac -g
JM=java -ea -jar
JR=jar -cfe

OBJ=BoolVector.class Test.class

.java.class:
	$(JC) $<

run: build
	$(JM) $(TARG).jar

build: 	$(OBJ)
	$(JR) $(TARG).jar Test $(OBJ)

clean:
	for %%f in ($(OBJ)) do del %%f
	del $(TARG).jar

BoolVector.class: BoolVector.java makefile

Test.class: Test.java makefile

# eof makefile
