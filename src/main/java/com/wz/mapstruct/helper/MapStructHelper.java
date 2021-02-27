package com.wz.mapstruct.helper;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @projectName: map-struct-code-helper
 * @package: com.wz.com.wz.mapstruct.util
 * @className: MapStructHelper
 * @description:
 * @author: zhi
 * @date: 2021/2/1
 * @version: 1.0
 */
public final class MapStructHelper {

    private static volatile MapStructHelper helper;

    private Project project;

    private String projectName;

    private String projectBastPath;

    private Set<String> moduleNames = new CopyOnWriteArraySet<>();

    private Map<String, String> moduleSourcePathMap = new ConcurrentHashMap<>();

    private Map<String, Module> moduleMap = new ConcurrentHashMap<>();

    private MapStructHelper(Project p) {
        init(p);
    }

    private void init(Project p) {
        this.project = p;
        this.projectName = p.getName();
        this.projectBastPath = p.getBasePath();
        moduleNames.clear();
        moduleSourcePathMap.clear();
        moduleMap.clear();
    }

    public void doModuleAdded(Module m) {
        final String moduleName = m.getName();
        moduleNames.add(moduleName);

        String moduleSourcePath;
        final ModuleRootManager rootManager = ModuleRootManager.getInstance(m);
        final List<VirtualFile> sourceRoots = rootManager.getSourceRoots(JavaSourceRootType.SOURCE);
        if (!sourceRoots.isEmpty()) {
            moduleSourcePath = sourceRoots.get(0).getPath();
        } else {
            moduleSourcePath = ModuleUtil.getModuleDirPath(m);
        }
        moduleSourcePathMap.put(moduleName, moduleSourcePath);

        moduleMap.put(moduleName, m);
    }

    public void initHelper() {
        PropertiesHelper.init(this.project);
        SearchHelper.init(this.project);
    }

    public static MapStructHelper getInstance(Project p) {
        if (Objects.isNull(helper)) {
            synchronized (MapStructHelper.class) {
                if (Objects.isNull(helper)) {
                    helper = new MapStructHelper(p);
                }
            }
        }
        return helper;
    }

    public Project getProject() {
        return project;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectBastPath() {
        return projectBastPath;
    }

    public Set<String> getModuleNames() {
        return moduleNames;
    }

    public Map<String, String> getModuleSourcePathMap() {
        return moduleSourcePathMap;
    }

    public Map<String, Module> getModuleMap() {
        return moduleMap;
    }
}
