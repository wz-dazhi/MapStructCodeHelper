package com.wz.mapstruct.ui.panel;

import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;
import com.wz.mapstruct.constants.UIConsts;
import com.wz.mapstruct.helper.MapStructHelper;
import com.wz.mapstruct.helper.SearchHelper;
import com.wz.mapstruct.ui.listener.BeanListener;
import com.wz.mapstruct.ui.listener.ConverterPackageListener;
import com.wz.mapstruct.util.ComboBoxUtil;
import com.wz.mapstruct.util.GeneratorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @projectName: map-struct-code-helper
 * @package: com.wz.com.wz.mapstruct.ui.panel
 * @className: GeneratorPanel
 * @description:
 * @author: zhi
 * @date: 2021/1/29
 * @version: 1.0
 */
public class GeneratorCenterPanel extends JPanel {
    private ComboBox<String> moduleComboBox;
    private JTextField moduleSourcePath;
    private JTextField sourceBean;
    private JTextField targetBean;
    private JTextField converterPackage;
    private MapStructHelper h;

    public GeneratorCenterPanel(Project p) {
        this.h = MapStructHelper.getInstance(p);
        h.initHelper();
        this.init();
    }

    /**
     * 初始化面板中间部分
     */
    private void init() {
        final Box box = Box.createVerticalBox();
        box.add(this.projectPanel());
        box.add(this.modulePanel());
        box.add(this.moduleSourcePanel());
        box.add(this.sourcePanel());
        box.add(this.targetPanel());
        box.add(this.converterPackagePanel());
        this.add(box);
    }

    private JPanel projectPanel() {
        final JPanel projectPanel = new JPanel();
        final JTextField projectField = this.textField(h.getProjectName());
        projectField.setEnabled(false);
        projectPanel.add(this.label("Project: "));
        projectPanel.add(projectField);
        return projectPanel;
    }

    private JPanel modulePanel() {
        final JPanel modulePanel = new JPanel();
        final String[] moduleNames = h.getModuleNames().toArray(new String[0]);
        moduleComboBox = new ComboBox<>(moduleNames);
        moduleComboBox.setPreferredSize(UIConsts.FIELD_PREFERRED_SIZE);
        moduleComboBox.setLayout(new BorderLayout());
        moduleComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                final String selectedModuleName = String.valueOf(moduleComboBox.getSelectedItem());
                moduleSourcePath.setText(h.getModuleSourcePathMap().get(selectedModuleName));
            }
        });

        modulePanel.add(this.label("Module: "));
        modulePanel.add(moduleComboBox);
        return modulePanel;
    }

    private JPanel moduleSourcePanel() {
        final JPanel moduleSourcePanel = new JPanel();
        final String selectedModuleName = String.valueOf(moduleComboBox.getSelectedItem());
        moduleSourcePath = this.textField(h.getModuleSourcePathMap().get(selectedModuleName));
        moduleSourcePanel.add(this.label("Module source 目录: "));
        moduleSourcePanel.add(moduleSourcePath);

        return moduleSourcePanel;
    }

    private JPanel sourcePanel() {
        sourceBean = this.textField(null);
        return this.beanPanel(sourceBean, "源Bean: ");
    }

    private JPanel targetPanel() {
        targetBean = this.textField(null);
        return this.beanPanel(targetBean, "目标Bean: ");
    }

    private JPanel beanPanel(JTextField textField, String labelText) {
        final JPanel beanPanel = new JPanel();
        textField.setPreferredSize(UIConsts.CHOOSE_FIELD_PREFERRED_SIZE);
        // 搜索类
        final ComboBox<String> comboBox = new ComboBox<>();
        comboBox.removeAllItems();
        comboBox.setPreferredSize(UIConsts.FIELD_COMBO_BOX_PREFERRED_SIZE);
        comboBox.setMaximumRowCount(15);
        // 删除下拉按钮
        ComboBoxUtil.removeButtonComponent(comboBox, JButton.class);
        // 按键监听
        textField.addKeyListener(new BeanListener(comboBox, textField));

        TextFieldWithBrowseButton textFieldWithBrowseButton = new TextFieldWithBrowseButton(textField);
        textFieldWithBrowseButton.add(comboBox, BorderLayout.SOUTH);
        textFieldWithBrowseButton.addActionListener(e -> {
            final TreeClassChooserFactory factory = TreeClassChooserFactory.getInstance(h.getProject());
            final TreeClassChooser classChooser = factory.createAllProjectScopeChooser("Class Chooser");
            classChooser.showDialog();
            final PsiClass selected = classChooser.getSelected();
            System.out.println("-----------> 选中的类: " + selected);
            if (Objects.nonNull(selected)) {
                final String qualifiedName = selected.getQualifiedName();
                // 兼容回车事件
                resetComboBox(comboBox, qualifiedName);
                // 鼠标选中
                textFieldWithBrowseButton.setText(qualifiedName);
            }
        });

        beanPanel.add(this.label(labelText));
        beanPanel.add(textFieldWithBrowseButton);
        return beanPanel;
    }

    private JPanel converterPackagePanel() {
        final JPanel converterPackagePanel = new JPanel();
        converterPackage = this.textField(null);
        converterPackage.setPreferredSize(UIConsts.CHOOSE_FIELD_PREFERRED_SIZE);

        final ComboBox<String> comboBox = new ComboBox<>();
        comboBox.removeAllItems();
        // 初始化包名, 去重操作
        comboBox.setModel(new DefaultComboBoxModel<>(SearchHelper.INIT_PACKAGE.toArray(new String[0])));
        comboBox.setPreferredSize(UIConsts.FIELD_COMBO_BOX_PREFERRED_SIZE);
        comboBox.setMaximumRowCount(10);
        // 删除下拉按钮
        ComboBoxUtil.removeButtonComponent(comboBox, JButton.class);
        // 按键监听
        converterPackage.addKeyListener(new ConverterPackageListener(converterPackage, comboBox, SearchHelper.INIT_PACKAGE));

        TextFieldWithBrowseButton textFieldWithBrowseButton = new TextFieldWithBrowseButton(converterPackage);
        textFieldWithBrowseButton.add(comboBox, BorderLayout.SOUTH);
        textFieldWithBrowseButton.addActionListener(e -> {
            PackageChooserDialog chooserDialog = new PackageChooserDialog("Package Chooser", h.getProject());
            if (chooserDialog.showAndGet()) {
                final PsiPackage psiPackage = chooserDialog.getSelectedPackage();
                System.out.println("-----------> 选中的包: " + psiPackage);
                if (Objects.nonNull(psiPackage)) {
                    final String qualifiedName = psiPackage.getQualifiedName();
                    // 兼容回车事件
                    resetComboBox(comboBox, qualifiedName);
                    // 鼠标选中
                    textFieldWithBrowseButton.setText(qualifiedName);
                }
            }
        });
        converterPackagePanel.add(this.label("converter接口包名: "));
        converterPackagePanel.add(textFieldWithBrowseButton);
        return converterPackagePanel;
    }

    private void resetComboBox(final ComboBox<String> comboBox, final String selectedItem) {
        if (!Objects.equals(comboBox.getSelectedItem(), selectedItem)) {
            comboBox.removeAllItems();
            comboBox.setModel(new DefaultComboBoxModel<>(new String[]{selectedItem}));
            comboBox.setSelectedItem(selectedItem);
        }
    }

    private JLabel label(String text) {
        final JLabel label = new JLabel(text);
        label.setPreferredSize(UIConsts.LABEL_PREFERRED_SIZE);
        return label;
    }

    private JTextField textField(String text) {
        final JTextField field = new JTextField(text);
        field.setPreferredSize(UIConsts.FIELD_PREFERRED_SIZE);
        return field;
    }

    public void ok() {
//        System.out.println("选中的模块是: " + moduleComboBox.getSelectedItem());
//        System.out.println("模块的source是: " + moduleSourcePath.getText());
//        System.out.println("源Bean是: " + sourceBean.getText());
//        System.out.println("目标Bean: " + targetBean.getText());
//        System.out.println("converter 包名是: " + converterPackage.getText());

        final Map<String, Object> variables = new HashMap<>();
        //variables.put("author", "zhi");
        variables.put("sourceBean", sourceBean.getText());
        variables.put("targetBean", targetBean.getText());
        variables.put("source", sourceBean.getText().substring(sourceBean.getText().lastIndexOf('.') + 1));
        variables.put("target", targetBean.getText().substring(targetBean.getText().lastIndexOf('.') + 1));
        variables.put("package", converterPackage.getText());
        variables.put("Converter", sourceBean.getText().substring(sourceBean.getText().lastIndexOf('.') + 1) + "Converter");
//        variables.put("baseMapstructInterface", null);
//        variables.put("baseMapstruct", null);
//        variables.put("componentModel", null);

        final String outPath = moduleSourcePath.getText() + "/" + converterPackage.getText().replaceAll("\\.", "/");
        GeneratorUtil.doGenerator(variables, outPath);
    }
}
