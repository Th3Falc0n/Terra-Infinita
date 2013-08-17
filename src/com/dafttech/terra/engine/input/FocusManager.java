package com.dafttech.terra.engine.input;

public class FocusManager {
    static IFocusableTyping typeFocus = null;
    
    public static boolean acquireTypeFocus(IFocusableTyping listener) {
        if(typeFocus != null) return false;
        typeFocus = listener;
        return true;
    }
    
    public static boolean releaseTypeFocus(IFocusableTyping listener) {
        if(typeFocus != listener) return false;
        typeFocus = null;
        return true;
    }
    
    public static boolean typeFocusAssigned() {
        return typeFocus != null;
    }
}
