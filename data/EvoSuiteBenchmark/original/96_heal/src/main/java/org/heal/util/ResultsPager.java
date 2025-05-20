package org.heal.util;

/**
 * The creation of this class is intended to remove some of the java
 * code from the JSP pages by providing a means of abstracting the
 * math behind the paging scheme used to seperate search results onto
 * multiple pages.
 *
 * PAGING SYSTEM
 * The goal is to have a previous link, a next link, and then individual pages
 * that you can jump to.  If the number of pages to jump to is too high, then
 * we put ellipses at the beginning and end if necessary.  We determine this
 * because we only want to have 7 pages listed at a time.  If there are
 * more than seven pages, we will need at least one ellipsis.  We try to
 * keep the current page listing in the middle of the sequence, so if the
 * current page number is greater than 4, we have an ellipsis at the
 * beggining, and if the total number of pages is greater then the
 * current page number+3, we will have an ellipsis at the end.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class ResultsPager {

    private static final int MAX_PAGES_TO_DISPLAY = 7;
    private static final int MIDPOINT = MAX_PAGES_TO_DISPLAY/2;
    int totalResults;
    int resultsPerPage;
    int totalPages;
    int currentPage;
    int startPage;
    int stopPage;
    boolean startEllipsisNeeded = false;
    boolean stopEllipsisNeeded = false;

    public ResultsPager(int totalResults,int resultsPerPage, int currentPage) {
	this.totalResults = totalResults;
	this.resultsPerPage = resultsPerPage;
	this.currentPage = currentPage;
	totalPages = totalResults/resultsPerPage;
	if (totalResults%resultsPerPage != 0) {
            //Basically a ceiling function...
	    totalPages++;
	}
	startPage = 1;
	stopPage = totalPages;
	if (totalPages > MAX_PAGES_TO_DISPLAY) {
	    if (currentPage > (MIDPOINT+1)) {
		startEllipsisNeeded = true;
		startPage = currentPage - MIDPOINT;
	    }
	    if (totalPages - currentPage > MIDPOINT) {
		stopEllipsisNeeded = true;
		if (currentPage <= ((MIDPOINT)+1) ) {
		    stopPage = MAX_PAGES_TO_DISPLAY;			  
		} else {
		    stopPage = currentPage+MIDPOINT;
		}
	    }
	}
    }

    public int getPagingStart() {
	return startPage;
    }
    public int getPagingStop() {
	return stopPage;
    }
    public int getTotalPages() {
	return totalPages;
    }
    public boolean getStartEllipsisNeeded() {
	return startEllipsisNeeded;
    }
    public boolean getStopEllipsisNeeded() {
	return stopEllipsisNeeded;
    }
}





