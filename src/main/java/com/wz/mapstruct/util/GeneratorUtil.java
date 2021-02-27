package com.wz.mapstruct.util;

import com.wz.mapstruct.constants.Consts;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @package: com.wz.com.wz.mapstruct.util
 * @className: GeneratorUtil
 * @description:
 * @author: zhi
 * @date: 2021/2/8
 * @version: 1.0
 */
public final class GeneratorUtil {
    private static final TemplateEngine TEMPLATE_ENGINE = new TemplateEngine();
    //private static final String TEMPLATE_FILE = "Template";

    static {
        //final FileTemplateResolver resolver = new FileTemplateResolver();
        final StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.TEXT);

//        resolver.setPrefix("templates/");
//        resolver.setSuffix(".java");
        TEMPLATE_ENGINE.setTemplateResolver(resolver);
    }

    private GeneratorUtil() {
    }

    public static void doGenerator(Map<String, Object> variables, String outPath) {
        final Context context = new Context();
        context.setVariables(variables);
        try (Writer writer = new FileWriter(outPath + "/" + variables.get("Converter") + ".java")) {
            //writer.write(TEMPLATE_ENGINE.process(TEMPLATE_FILE, context));
            writer.write(TEMPLATE_ENGINE.process(Consts.Template.TEMPLATE, context));
            writer.flush();
            MessagesUtil.showInfoMessage("MapStructCodeHelper", "Generation Successful.");
        } catch (Exception e) {
            LogUtil.log("生成代码发生异常.", e);
        }
    }

}
