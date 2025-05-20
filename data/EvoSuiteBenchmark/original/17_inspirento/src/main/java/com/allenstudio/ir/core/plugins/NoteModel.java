/*
 * @(#)NoteModel.java
 * Created on 2005-7-14
 * 
 * Copyright AllenStudio
 */
package com.allenstudio.ir.core.plugins;

/**
 * State and data model for <code>AbstractNote</code>s.
 * Different types of notes should own their data models
 * that implements this interface.
 * 
 * @author Allen Chue
 */
public interface NoteModel {
    /**
     * Returns the content that is to be rendered
     * in the note's thumbnail view.<br>
     * <em>Note here it is somewhat hard to customize
     * a self-defined style to display the contents, for
     * only a <code>String[]</code> array is returned without
     * any style decoration declaration. And in this version,
     * the program will simply paint each element in
     * this array line by line. So if you want any text
     * decoration, I suggest you use HTML tags to implement
     * the effect.</em>
     * @return the content to be rendered
     */
    //[PENDING] Does SwingUtilities2.paintText() method support HTML?
    //TODO 使用一定方法在此方法中实现可以定义内容画出来的样式
    public String[] getThumbnailContent();
    
    /**
     * Returns if the note is selected.
     * @return
     */
    boolean isSelected();
    
    /**
     * Selects or deselects a note
     * @param true for select
     *        false for deselect
     */
    void setSelected(boolean b);
}
