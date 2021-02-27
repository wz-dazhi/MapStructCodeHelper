package com.wz.mapstruct.helper;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.AllClassesSearch;
import com.intellij.util.Query;
import com.wz.mapstruct.constants.Consts;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * @projectName: map-struct-code-helper
 * @package: com.wz.com.wz.mapstruct.util
 * @className: SearchHelper
 * @description:
 * @author: zhi
 * @date: 2021/2/1
 * @version: 1.0
 */
public final class SearchHelper {
    public static final Set<String> INIT_PACKAGE = new TreeSet<>();
    private static JavaPsiFacade javaPsiFacade;

    private static Project p;

    private SearchHelper() {
    }

    static void init(Project p) {
        SearchHelper.p = p;
        SearchHelper.javaPsiFacade = JavaPsiFacade.getInstance(p);
    }

    public static Set<String> findClassesByName(String className) {
        final PsiClass[] psiClasses = PsiShortNamesCache.getInstance(p).getClassesByName(className, GlobalSearchScope.allScope(p));
        return Stream.of(psiClasses)
                .map(PsiClass::getQualifiedName)
                .collect(toSet());
    }

    public static Set<String> findStartsWithClass(String className, SearchScope scope) {
        if (StringUtil.isEmpty(className)) {
            return Consts.EMPTY_STRING_SET;
        }

        final Query<PsiClass> query = AllClassesSearch.search(scope, p, str -> str.toLowerCase().startsWith(className.toLowerCase()));
        return query.findAll()
                .stream()
                .map(PsiClass::getQualifiedName)
                .filter(StringUtil::isNotEmpty)
                .collect(toSet());
    }

    public static PsiPackage findPackage(String packageName) {
        return javaPsiFacade.findPackage(packageName);
    }

    public static Set<String> findSubPackage(String packageName) {
        final PsiPackage psiPackage = findPackage(packageName);
        if (null == psiPackage) {
            return Consts.EMPTY_STRING_SET;
        }
        return Stream.of(psiPackage.getSubPackages())
                .map(PsiPackage::getQualifiedName)
                .collect(toSet());
    }

}
