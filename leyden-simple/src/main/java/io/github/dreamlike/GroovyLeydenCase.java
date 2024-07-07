package io.github.dreamlike;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import io.github.dreamlike.Profile;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class GroovyLeydenCase implements LeydenCase{
    @Override
    public void run() {
        if (!Profile.enableGroovy) {
            System.out.println("disable groovy");
            return;
        }

        List<String> list = new ArrayList<>();
        String groovySourceCode = """       
                    def parseCode(String html) {
                     regex = ~'<code>(?<tag1>[a-zA-Z0-9]{6})</code>'
                       if(html.isBlank()) {
                        return null;
                       }
                       def matcher = regex.matcher(html)
                       if(matcher.find()) {
                           return matcher.group('tag1');
                       } else {
                        return null;
                       }
                    }
                    """;

        //故意的 测试一下加载的性能优化
        for (int i = 0; i < 1_0000; i++) {
            GroovyShell shell = new GroovyShell();
            Script script = shell.parse(groovySourceCode);
            String string = (String) script.invokeMethod("parseCode", String.format("<code>%06d</code>", i));
            list.add(string);
        }
        System.out.printf("groovy exc end res size %s, first %s\n", list.size(), list.getFirst());
    }
}
