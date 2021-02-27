package com.wz.mapstruct.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.wz.mapstruct.ui.dialog.GeneratorDialog;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author zhi
 */
public class GeneratorAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("---------actionPerformed action: " + e.getProject());
        final GeneratorDialog dialog = new GeneratorDialog(e);
        dialog.setResizable(false);
        dialog.showAndGet();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        System.out.println("---------update action: " + e.getProject());
        super.update(e);
        final Project p = e.getProject();
        if (Objects.isNull(p) || !p.isInitialized() || p.isDisposed()) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        e.getPresentation().setEnabledAndVisible(true);
    }

}
