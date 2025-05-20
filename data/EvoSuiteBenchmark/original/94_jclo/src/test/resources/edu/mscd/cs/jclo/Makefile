BASE	= $*
TARGET	= $@
DEPENDS	= $<
NEWER	= $?
VERSION = $(shell cat VERSION)
SOURCES	= JCLO.java JCLOTests.java Example.java Version.java
CLASSES = $(SOURCES:.java=.class) JCLOArgs.class JCLOnly.class \
	  ExampleArgs.class

ROOT	= ../../../..
DIR	= edu/mscd/cs/jclo
PACKAGE	= edu.mscd.cs.jclo
FILES	= $(SOURCES) $(CLASSES) Makefile index index.html \
	  UPLOAD VERSION docs GOOD.1 GOOD.2 GOOD.3 GOOD.4 GOOD.5

.SUFFIXES: .java .class .jar

.java.class : 
	javac -classpath $(ROOT) -target 1.2 -source 1.2 -Xlint:unchecked $(DEPENDS)

JCLO-$(VERSION).jar : $(FILES)
	cd $(ROOT) ; jar -cMf $(DIR)/$(TARGET) $(patsubst %,$(DIR)/%,$(FILES))

index.html : index Makefile
	sed -e "s/VERSION/$(VERSION)/" < $(DEPENDS) > $(TARGET)

docs : JCLO.java
	javadoc -quiet -d $(TARGET) $(DEPENDS)

clean :
	rm -f JCLO-$(VERSION).jar $(CLASSES) index.html
	rm -rf docs

test : JCLO.class JCLOTests.class Main.class Example.class
	java -classpath $(ROOT) $(PACKAGE).JCLOTests | diff - GOOD.1
	java -classpath $(ROOT):. Main -a 5 -b | diff - GOOD.2
	java -classpath $(ROOT):. Example --a=5 --b --c=3.141592 --d=this \
	    that theother | diff - GOOD.3
	- java -classpath $(ROOT):. Example --e=5 2>&1 | \
	    sed -e 's/java:[0-9][0-9]*/java:#/' | diff - GOOD.4
	- java -classpath $(ROOT):. Example --c=true 2>&1 | \
	    sed -e 's/java:[0-9][0-9]*/java:#/' | diff - GOOD.5
	java -classpath $(ROOT) $(PACKAGE).JCLO --version

tag:
	cvs tag -R jclo`echo $(VERSION) | sed -e 's/\.//g'`

Version.java : VERSION
	echo "package edu.mscd.cs.jclo; public class Version { public static String getVersion() { return (\"$(VERSION)\"); }}" > $(TARGET)
