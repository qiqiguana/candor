/*
 * @(#)CommonNote.java
 * Created on 2005-7-14
 * 
 * Copyright AllenStudio
 */
package com.allenstudio.ir.core.plugins;

import javax.swing.*;
import com.allenstudio.ir.ui.*;

/**
 * 
 * @author Allen Chue
 */
public class CommonNote extends AbstractNote {
    private String otherInfo;
    
    public CommonNote() {
        
    }

    @Override
    public String getTitle() {
        return "Title";
    }

    @Override
    public String getDescription() {
        return "This is a testing description. 希望你能看到它。";
    }
    
    @Override
    public Icon getIcon() {
        return new ImageIcon(NoteList.class.getResource("resources/test.gif"));
    }

    @Override
    public String getOtherInfoText() {
        String info = getOtherInfo();
        
        if (info == null || "".equals(info)) {//Note: NullPointerException never thrown in this statement
            return " ";//In order not to make the JLabel blank
        }
        else {
            return info;
        }
    }

    public String getOtherInfo() {
        return this.otherInfo;
    }
    
    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
    
}
