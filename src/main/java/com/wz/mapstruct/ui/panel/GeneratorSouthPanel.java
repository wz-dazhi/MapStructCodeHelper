package com.wz.mapstruct.ui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @projectName: map-struct-code-helper
 * @package: com.wz.com.wz.mapstruct.ui.panel
 * @className: GeneratorSouthPanel
 * @description:
 * @author: zhi
 * @date: 2021/2/8
 * @version: 1.0
 */
public class GeneratorSouthPanel extends JPanel {
    private final JButton okButton;
    private final JButton cancelButton;

    public GeneratorSouthPanel() {
        super(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        this.okButton = new JButton("确认");
        this.cancelButton = new JButton("取消");

        this.add(okButton);
        this.add(cancelButton);
    }

    public void initListener(ActionListener okListener, ActionListener cancelListener) {
        okButton.addActionListener(okListener);
        cancelButton.addActionListener(cancelListener);
    }
}
