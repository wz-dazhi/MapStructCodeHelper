package com.wz.mapstruct.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.FileWriter;
import java.io.Writer;

/**
 * @projectName: test-wz-swagger
 * @package: com.wz.thymeleaf
 * @className: TextTest
 * @description:
 * @author: zhi
 * @date: 2021/1/16
 * @version: 1.0
 */
public class TextTest {
    public static void main(String[] args) throws Exception {
        final TemplateEngine engine = new TemplateEngine();
        final FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setTemplateMode(TemplateMode.TEXT);
        resolver.setCharacterEncoding("UTF-8");
        //resolver.setPrefix("/Users/wangzhi/work/project/map-struct-code-helper/resources/template/");
        resolver.setPrefix("resources/templates/");
        resolver.setSuffix(".java");
        engine.setTemplateResolver(resolver);



        testConverter(engine);
    }

    private static void testConverter(TemplateEngine engine) throws Exception {
        String outPath = "/Users/wangzhi/work/project/map-struct-code-helper/src/com/wz/com.wz.mapstruct";
        final String javaFile = "Template";
//        final File f = new File(outPath, javaFile);
//        f.createNewFile();

        Context context = new Context();
        context.setVariable("author", "zhi");
        context.setVariable("package", TextTest.class.getPackage().getName());
        context.setVariable("sourceBean", "com.wz.thymeleaf.bean.User");
        context.setVariable("targetBean", "com.wz.thymeleaf.bean.UserVO");
        context.setVariable("baseMapstructInterface", "com.wz.thymeleaf.converter.BaseMapping");
        context.setVariable("baseMapstruct", "BaseMapping");
        context.setVariable("source", "User");
        context.setVariable("target", "UserVO");
        context.setVariable("componentModel", null);
        final String userConverter = "UserConverter";
        context.setVariable("Converter", userConverter);
        Writer writer = new FileWriter(outPath + "/" + userConverter + ".java");
        writer.write(engine.process(javaFile, context));
        writer.flush();
        writer.close();
    }

}
