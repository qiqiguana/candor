# $Id: Makefile,v 1.3 2004/04/27 19:26:22 bja Exp $

TOPDIR=../..
include $(TOPDIR)/config.mk

OBJECTS:=Unpacker ObjectManager ManagedObject AbstractFactory #MapEdit
OBJECTS:=$(OBJECTS:%=%.class)

VPATH:=$(VPATH) $(OBJDIR)/util

.PHONY: clean

all: $(OBJECTS)

%.class: %.java
	$(JCC) $(JFLAGS) $<

clean:
	@rm -f *.class *~
