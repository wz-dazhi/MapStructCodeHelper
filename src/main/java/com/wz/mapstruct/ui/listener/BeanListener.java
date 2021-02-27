package com.wz.mapstruct.ui.listener;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.wz.mapstruct.helper.SearchHelper;
import com.wz.mapstruct.util.CollectionUtil;
import com.wz.mapstruct.util.ComboBoxUtil;
import com.wz.mapstruct.util.ProjectUtil;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.Set;

/**
 * @projectName: map-struct-code-helper
 * @package: com.wz.com.wz.mapstruct.ui.listener
 * @className: BeanListener
 * @description:
 * @author: zhi
 * @date: 2021/2/2
 * @version: 1.0
 */
public final class BeanListener implements DefaultKeyListener {
    private final ComboBox<String> comboBox;
    private final JTextField sourceField;

    public BeanListener(ComboBox<String> comboBox, JTextField sourceField) {
        this.sourceField = sourceField;
        this.comboBox = comboBox;

        this.comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.sourceField.setText((String) this.comboBox.getSelectedItem());
            }
        });
    }

    @Override
    public void keyReleased(KeyEvent e) {
        final char ch = e.getKeyChar();
        final int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            if (keyCode == KeyEvent.VK_ENTER) {
                sourceField.setText((String) comboBox.getSelectedItem());
                comboBox.setPopupVisible(false);
            } else {
                ComboBoxUtil.comboBoxKeyReleased(e, comboBox);
            }
            return;
        }
        if (ch == KeyEvent.CHAR_UNDEFINED) {
            return;
        }
        String className = sourceField.getText();
        if (StringUtil.isNotEmpty(className)) {
            // 切割类名, 根据类名搜索
            if (className.contains(".")) {
                className = className.substring(className.lastIndexOf('.') + 1);
            }
            final Set<String> classNames = SearchHelper.findStartsWithClass(className, GlobalSearchScope.allScope(ProjectUtil.getCurrentProject()));
            if (CollectionUtil.isNotEmpty(classNames)) {
                comboBox.setModel(new DefaultComboBoxModel<>(classNames.toArray(new String[0])));
                comboBox.showPopup();
            } else {
                comboBox.hidePopup();
            }
        }
    }
}
