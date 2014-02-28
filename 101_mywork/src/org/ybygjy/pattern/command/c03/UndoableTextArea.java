package org.ybygjy.pattern.command.c03;

import java.awt.TextArea;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Hashtable;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;
import javax.swing.undo.UndoManager;

/**
 * Receiver
 * @author WangYanCheng
 * @version 2012-12-12
 */
public class UndoableTextArea extends TextArea implements StateEditable {
    /**
     * serial number
     */
    private static final long serialVersionUID = -8536631533370324071L;
    private static final String KEY_STATE = "UndoableTextAreaKey";
    private boolean textChanged = false;
    private UndoManager undoMgr;
    private StateEdit currentEdit;
    public UndoableTextArea() {
        super();
        initUndoable();
    }
    public UndoableTextArea(String text) {
        super(text);
        initUndoable();
    }
    public UndoableTextArea(int rows, int columns) {
        super(rows, columns);
        initUndoable();
    }
    public UndoableTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
        initUndoable();
    }
    public UndoableTextArea(String text, int rows, int columns, int scrollbars) {
        super(text, rows, columns, scrollbars);
        initUndoable();
    }
    private void initUndoable() {
        undoMgr = new UndoManager();
        currentEdit = new StateEdit(this);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.isActionKey()) {
                    takeSnapshot();
                }
            }
        });
        addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                takeSnapshot();
            }
        });
        addTextListener(new TextListener() {
            public void textValueChanged(TextEvent event) {
                textChanged = true;
                takeSnapshot();
            }
        });
    }
    private void takeSnapshot() {
        if (textChanged) {
            currentEdit.end();
            undoMgr.addEdit(currentEdit);
            textChanged = false;
            currentEdit = new StateEdit(this);
        }
    }
    public boolean undo() {
        try {
            undoMgr.undo();
            return true;
        } catch (CannotUndoException cue) {
            cue.printStackTrace();
        }
        return false;
    }
    public boolean redo() {
        if (!undoMgr.canRedo()) {
            return false;
        }
        try {
            undoMgr.redo();
            return true;
        } catch (CannotRedoException cre) {
            cre.printStackTrace();
        }
        return false;
    }
    public void storeState(Hashtable<Object, Object> state) {
        state.put(KEY_STATE, getText());
    }

    public void restoreState(Hashtable<?, ?> state) {
        Object data = state.get(KEY_STATE);
        if (null != data) {
            setText((String) data);
        }
    }

}
