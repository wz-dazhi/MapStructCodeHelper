package com.wz.mapstruct.ui.dialog;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.wz.mapstruct.ui.panel.GeneratorCenterPanel;
import com.wz.mapstruct.ui.panel.GeneratorSouthPanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author zhi
 */
public class GeneratorDialog extends DialogWrapper {
    private final GeneratorCenterPanel centerPanel;
    private final GeneratorSouthPanel southPanel;
    private AnActionEvent e;
    private Project p;

    public GeneratorDialog(AnActionEvent e) {
        super(true);
        System.out.println("调用生成构造方法");
        // 主窗口
        setTitle("MapStruct Title");
        this.e = e;
        this.p = e.getProject();
        this.centerPanel = new GeneratorCenterPanel(p);
        this.southPanel = new GeneratorSouthPanel();

        this.init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        System.out.println("-------------------> 创建面板");
        return this.centerPanel;
    }

    @Override
    protected JComponent createSouthPanel() {
        System.out.println("-------------------> 创建确定/取消按钮");
        this.southPanel.initListener(e -> {
            centerPanel.ok();
            super.doOKAction();
        }, e -> super.doCancelAction());
        return this.southPanel;
    }

}
