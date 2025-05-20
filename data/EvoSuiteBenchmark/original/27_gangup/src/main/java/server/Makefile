# $Id: Makefile,v 1.2 2004/03/31 21:12:03 bja Exp $

TOPDIR=../..
include $(TOPDIR)/config.mk

OBJECTS:=server
#OBJECTS:=$(OBJECTS:%=%)

LIBS:=$(LIBS) -lpthread

ifeq "$(shell uname)" "SunOS"
LIBS:=$(LIBS) -lsocket -lnsl
endif

VPATH:=$(VPATH) $(OBJDIR)/server

.PHONY: clean

all: $(OBJECTS)

server: server.c
	$(GCC) $(CFLAGS) $< $(INCLUDES) $(LIBS) -o $(BINDIR)/$@

clean:
	@rm -f *.class *~ *.o
