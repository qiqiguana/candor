/*
 * @(#)CommonNoteModel.java
 * Created on 2005-7-14
 * 
 * Copyright AllenStudio
 */
package com.allenstudio.ir.core.plugins;

/**
 * 
 * @author Allen Chue
 */
public class CommonNoteModel implements NoteModel {

    public String[] getThumbnailContent() {
        String[] test = {"Test", "Hello, world! This is a temporary message." +
                " I hope you can see it. This component is CommonNote that extends" +
                " the AbstractNote object."};
        return test;
    }

    public boolean isSelected() {
        return false;
    }

    public void setSelected(boolean b) {
    }

}
