# $Id: Makefile,v 1.10 2004/05/05 11:55:12 emill Exp $

TOPDIR=../../..
include $(TOPDIR)/config.mk

OBJECTS:= FrameStats PickRay Util3D ActorManager Actor\
	  World XithMapRenderer HeadUpDisplay \
	  GLWorldPanel Camera CameraControl ParticleSystem
OBJECTS:=$(OBJECTS:%=%.class)

VPATH:=$(VPATH) $(OBJDIR)/gui/gl

.PHONY: clean

all: $(OBJECTS)

%.class: %.java
	$(JCC) $(JFLAGS) $<

clean:
	@rm -f *.class *~
