package com.wz.mapstruct.util;

import com.intellij.openapi.ui.ComboBox;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @package: com.wz.com.wz.mapstruct.util
 * @className: ComboBoxUtil
 * @description:
 * @author: zhi
 * @date: 2021/2/2
 * @version: 1.0
 */
public final class ComboBoxUtil {
    private ComboBoxUtil() {
    }

    public static <E> void removeButtonComponent(ComboBox<E> comboBox, Class<? extends Component> c) {
        final Component[] boxComponents = comboBox.getComponents();
        for (Component boxComponent : boxComponents) {
            if (boxComponent.getClass() == c) {
                comboBox.remove(boxComponent);
            }
        }
    }

    public static <E> void comboBoxKeyReleased(KeyEvent e, ComboBox<E> comboBox) {
        final int itemCount = comboBox.getItemCount();
        int index = comboBox.getSelectedIndex();
        if (e.getKeyCode() == KeyEvent.VK_DOWN && index < itemCount - 1) {
            index += 1;
        } else if (e.getKeyCode() == KeyEvent.VK_UP && index >= 1) {
            index -= 1;
        }
        comboBox.setSelectedIndex(index);
    }

}
