package com.wz.mapstruct.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;

import java.awt.*;

/**
 * @projectName: MapStructCodeHelper
 * @package: com.wz.mapstruct.util
 * @className: ProjectUtil
 * @description:
 * @author: zhi
 * @date: 2021/2/19
 * @version: 1.0
 */
public final class ProjectUtil {
    private ProjectUtil() {
    }

    public static Project getCurrentProject() {
        ProjectManager projectManager = ProjectManager.getInstance();
        Project[] openProjects = projectManager.getOpenProjects();
        if (openProjects.length == 0) {
            return projectManager.getDefaultProject();
        } else if (openProjects.length == 1) {
            // 只存在一个打开的项目则使用打开的项目
            return openProjects[0];
        }

        // 如果有项目窗口处于激活状态
        try {
            WindowManager wm = WindowManager.getInstance();
            for (Project project : openProjects) {
                Window window = wm.suggestParentWindow(project);
                if (window != null && window.isActive()) {
                    return project;
                }
            }
        } catch (Exception e) {
            LogUtil.log("Get current project error.", e);
        }

        //否则使用默认项目
        return projectManager.getDefaultProject();
    }

}
