# $Id: Makefile,v 1.2 2004/03/31 21:12:03 bja Exp $

TOPDIR=../..
include $(TOPDIR)/config.mk

OBJECTS:=$(shell echo *.java)
OBJECTS:=$(OBJECTS:%.java=%.class)

VPATH:=$(VPATH) $(OBJDIR)/module

.PHONY: clean

all: $(OBJECTS)

%.class: %.java
	$(JCC) $(JFLAGS) $<

clean:
	@rm -f *.class *~
