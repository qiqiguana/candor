# $Id: Makefile,v 1.8 2004/05/05 11:55:12 emill Exp $

TOPDIR:=../..
include $(TOPDIR)/config.mk

SUBDIRS:=gl

OBJECTS:=PlayerPanel DebugPanel ChatPanel GroupPanel ChatListener \
	 PlayerTree GUIFrame PlayerTreeModel PlayerListModel \
	 PlayerTreeRenderer GUIState ServerGUI StatsWindow \
	 GUIGLState AudioManager #WorldPanel Map

OBJECTS:=$(OBJECTS:%=%.class)

VPATH:=$(VPATH) $(OBJDIR)/gui

SUBDIR:=gl

.PHONY: clean


all: 	$(OBJECTS)
	@for dir in $(SUBDIR); do \
		(cd $$dir && $(MAKE) $@ || exit 1) \
	done

%.class: %.java
	$(JCC) $(JFLAGS) $<

clean:
	@rm -f *.class *~
