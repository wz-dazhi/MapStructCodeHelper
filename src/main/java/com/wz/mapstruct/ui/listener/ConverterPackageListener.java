package com.wz.mapstruct.ui.listener;

import com.intellij.openapi.ui.ComboBox;
import com.wz.mapstruct.constants.Consts;
import com.wz.mapstruct.helper.SearchHelper;
import com.wz.mapstruct.util.CollectionUtil;
import com.wz.mapstruct.util.ComboBoxUtil;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * @projectName: map-struct-code-helper
 * @package: com.wz.com.wz.mapstruct.ui.listener
 * @className: ConverterPackageListener
 * @description:
 * @author: zhi
 * @date: 2021/2/2
 * @version: 1.0
 */
public class ConverterPackageListener implements DefaultKeyListener {
    private final JTextField converterPackage;
    private final ComboBox<String> comboBox;
    private final Set<String> initPackageSet;

    public ConverterPackageListener(JTextField converterPackage, ComboBox<String> comboBox, Set<String> initPackageSet) {
        this.converterPackage = converterPackage;
        this.comboBox = comboBox;
        this.initPackageSet = initPackageSet;

        this.comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.converterPackage.setText((String) this.comboBox.getSelectedItem());
            }
        });
    }

    @Override
    public void keyReleased(KeyEvent e) {
        final char ch = e.getKeyChar();
        final int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            if (keyCode == KeyEvent.VK_ENTER) {
                converterPackage.setText((String) comboBox.getSelectedItem());
                comboBox.setPopupVisible(false);
            } else {
                ComboBoxUtil.comboBoxKeyReleased(e, comboBox);
            }
            return;
        }
        if (ch == KeyEvent.CHAR_UNDEFINED) {
            return;
        }

        Set<String> newItems = this.matchComboBox();
        if (!newItems.isEmpty()) {
            comboBox.setModel(new DefaultComboBoxModel<>(newItems.toArray(new String[0])));
            comboBox.showPopup();
        } else {
            comboBox.hidePopup();
        }
    }

    private Set<String> matchComboBox() {
        Set<String> newItems = new HashSet<>();
        final String packageName = converterPackage.getText();
        //System.out.println("-------输入了: " + packageName);
        // 取出下拉列表元素
        final ComboBoxModel<String> boxModel = comboBox.getModel();
        for (int i = 0; i < boxModel.getSize(); i++) {
            final String element = boxModel.getElementAt(i);
            // 完全匹配, 查子包
            if (element.equals(packageName)) {
                if (!element.contains(Consts.DOT) && !packageName.contains(Consts.DOT)) {
                    newItems.add(element);
                } else {
                    newItems = SearchHelper.findSubPackage(packageName);
                    break;
                }
            } else if (element.contains(packageName)) {
                // 包含 . , 过滤并添加
                if (packageName.contains(Consts.DOT)) {
                    final Set<String> subPackage = SearchHelper.findSubPackage(packageName.substring(0, packageName.lastIndexOf('.')));
                    this.subPackageFilterStartsWith(subPackage, newItems, packageName);
                } else {
                    if (!element.contains(Consts.DOT)) {
                        newItems.add(element);
                    } else if (element.contains(Consts.DOT)) {
                        final Set<String> subPackage = SearchHelper.findSubPackage(packageName);
                        this.subPackageFilterStartsWith(subPackage, newItems, packageName);
                    }
                }
            } else if (!element.contains(packageName)) {
                // 不包含
                if (packageName.contains(Consts.DOT)) {
                    final Set<String> subPackage = SearchHelper.findSubPackage(packageName.substring(0, packageName.lastIndexOf('.')));
                    this.subPackageFilterStartsWith(subPackage, newItems, packageName);
                }
            }
        }
        // 没有匹配到, 过滤初始化的包列表
        if (CollectionUtil.isEmpty(newItems)) {
            initPackageSet.stream()
                    .filter(s -> s.contains(packageName))
                    .forEach(newItems::add);
        }

        return newItems;
    }

    private void subPackageFilterStartsWith(final Set<String> subPackage, final Set<String> newItems, final String packageName) {
        subPackage.stream().filter(s -> s.startsWith(packageName)).forEach(newItems::add);
    }
}
