# $Id: Makefile,v 1.4 2004/04/27 19:26:20 bja Exp $

TOPDIR=..
include $(TOPDIR)/config.mk

SUBDIR=module server util map gui state

.PHONY: clean

all:
	@for dir in $(SUBDIR); do \
		(cd $$dir && $(MAKE) $@ || exit 1) \
	done

#clean:
#	rm -f *~
#	@for dir in $(SUBDIR); do \
#		(cd $$dir && $(MAKE) $@ || exit 1) \
#	done
