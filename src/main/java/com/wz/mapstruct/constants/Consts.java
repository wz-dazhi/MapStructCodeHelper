package com.wz.mapstruct.constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @package: com.wz.com.wz.mapstruct.constants
 * @className: Consts
 * @description:
 * @author: zhi
 * @date: 2021/1/26
 * @version: 1.0
 */
public final class Consts {
    private Consts() {
    }

    public static final List<String> EMPTY_STRING_LIST = new ArrayList<>(0);
    public static final Set<String> EMPTY_STRING_SET = new HashSet<>(0);

    public static final String PREFIX = "map-struct-code-helper:";
    public static final String DOT = ".";

    public static class Prop {
        public static final String PROJECT = PREFIX + "project";
        public static final String MODULE_NAMES = PREFIX + "module_names";
        public static final String MODULE_SOURCE_PATHS = PREFIX + "module_source_paths";
    }

    public static class Template {
        public static final String TEMPLATE = "package [(${package})];\n" +
                "\n" +
                "import [(${sourceBean})];\n" +
                "import [(${targetBean})];\n" +
                "import org.com.wz.mapstruct.InheritConfiguration;\n" +
                "import org.com.wz.mapstruct.InheritInverseConfiguration;\n" +
                "import org.com.wz.mapstruct.Mapper;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.util.stream.Stream;\n" +
                "\n" +
                "@Mapper\n" +
                "public interface [(${Converter})] {\n" +
                "\n" +
                "    /**\n" +
                "     * 映射同名属性\n" +
                "     */\n" +
                "    @InheritConfiguration\n" +
                "    [(${target})] sourceToTarget([(${source})] source);\n" +
                "\n" +
                "    /**\n" +
                "     * 反向，映射同名属性\n" +
                "     */\n" +
                "    @InheritInverseConfiguration(name = \"sourceToTarget\")\n" +
                "    [(${source})] targetToSource([(${target})] target);\n" +
                "\n" +
                "    /**\n" +
                "     * 映射同名属性，集合形式\n" +
                "     */\n" +
                "    @InheritConfiguration(name = \"sourceToTarget\")\n" +
                "    List<[(${target})]> sourceToTarget(List<[(${source})]> source);\n" +
                "\n" +
                "    /**\n" +
                "     * 反向，映射同名属性，集合形式\n" +
                "     */\n" +
                "    @InheritConfiguration(name = \"targetToSource\")\n" +
                "    List<[(${source})]> targetToSource(List<[(${target})]> target);\n" +
                "\n" +
                "    /**\n" +
                "     * 映射同名属性，集合流形式\n" +
                "     */\n" +
                "    List<[(${target})]> sourceToTarget(Stream<[(${source})]> stream);\n" +
                "\n" +
                "    /**\n" +
                "     * 反向，映射同名属性，集合流形式\n" +
                "     */\n" +
                "    List<[(${source})]> targetToSource(Stream<[(${target})]> stream);\n" +
                "}\n";
    }
}
