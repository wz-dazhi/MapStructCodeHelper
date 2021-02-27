package com.wz.mapstruct.components.project.impl;

import com.intellij.ProjectTopics;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.wz.mapstruct.components.project.MapStructProjectComponent;
import com.wz.mapstruct.helper.MapStructHelper;
import org.jetbrains.annotations.NotNull;

/**
 * @package: com.wz.com.wz.mapstruct.components.project.impl
 * @className: MapStructProjectComponentImpl
 * @description:
 * @author: zhi
 * @date: 2021/1/26
 * @version: 1.0
 */
public class MapStructProjectComponentImpl implements MapStructProjectComponent {
    private final Project project;
    private final MapStructHelper helper;

    public MapStructProjectComponentImpl(Project project) {
        System.out.println("开始调用构造方法 project component. project: " + project);
        this.project = project;
        this.helper = MapStructHelper.getInstance(project);
    }

    @Override
    public void initComponent() {
        final String projectName = project.getName();
        System.out.println("开始调用init Component方法, Project component init..." + projectName);
        // module监听
        project.getMessageBus()
                .connect()
                .subscribe(ProjectTopics.MODULES, new ModuleListener() {
                    @Override
                    public void moduleAdded(@NotNull Project project, @NotNull Module module) {
                        // 要接收有关模块更改的通知(要添加,删除或重命名的模块)
                        System.out.println("module 监听调用..");
                        helper.doModuleAdded(module);
                    }
                });
    }

    @Override
    public void projectOpened() {
        System.out.println(">>>>> projectOpened: " + project.isInitialized());

    }

    @Override
    public void disposeComponent() {
        System.out.println("开始调用dispose Component方法, Project component dispose..." + project.getName());
    }
}
