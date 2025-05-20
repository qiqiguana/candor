# $Id: Makefile,v 1.2 2004/04/27 19:26:21 bja Exp $

TOPDIR=../..
include $(TOPDIR)/config.mk

OBJECTS:=TileMap	  
OBJECTS:=$(OBJECTS:%=%.class)

VPATH:=$(VPATH) $(OBJDIR)/map

.PHONY: clean

all: $(OBJECTS)

%.class: %.java
	$(JCC) $(JFLAGS) $<

clean:
	@rm -f *.class *~
