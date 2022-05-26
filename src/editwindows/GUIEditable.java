package editwindows;

import javax.swing.*;

/**
 * GUIEditable interface provides getEditDialog method
 *
 * @version 1.0.0 28 April 2022
 * @author Alexander Kononov
 */
public interface GUIEditable {
    /**
     * Creates dialog that provides means to edit instance
     *
     * @return dialog frame
     */
    JDialog getEditDialog();
}