package io.github.dreamlike;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.classfile.AccessFlags;
import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.DynamicCallSiteDesc;
import java.lang.constant.DynamicConstantDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessFlag;
import java.util.Set;

import static java.lang.constant.ConstantDescs.CD_Object;
import static java.lang.constant.ConstantDescs.INIT_NAME;
import static java.lang.constant.ConstantDescs.MTD_void;
import static java.lang.constant.ConstantDescs.ofConstantBootstrap;

@SupportedAnnotationTypes("io.github.dreamlike.EnableGenerate")
@SupportedSourceVersion(SourceVersion.RELEASE_22)
@SupportedOptions({"quarkus.count", "simple.enable", "enableGroovy", "spring.count"})
public class LeydenCompileProcessor extends AbstractProcessor {
    private static final String Dynamic_Test_Case_Class_name = "DynamicEntryLeydenCaseTestCase";

    private static final String Condy_Method_Name = "condyInvoke";

    private static final String INDY_MTD = "indyInvoke";
    private ProcessingEnvironment env;

    private boolean hasGenerated = false;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        this.env = env;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        if (hasGenerated) {
            return false;
        }
        this.env.getOptions().forEach((k, v) -> System.out.println(k + ":" + v));
        hasGenerated = true;
        var enableSimple = Boolean.parseBoolean(this.env.getOptions().getOrDefault("simple.enable", "false"));
        if (enableSimple) {
            try {
                generateCondy();
                generateProfile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        var springCount = Integer.parseInt(this.env.getOptions().getOrDefault("spring.count", "0"));

        if (springCount > 0) {
            generateSpringBean(springCount);
        }

        var quarkusCount = Integer.parseInt(this.env.getOptions().getOrDefault("quarkus.count", "0"));
        if (quarkusCount > 0) {
            generateQuarkusBean(quarkusCount);
        }

        return false;
    }


    private void generateQuarkusBean(int count) {
        boolean b = Boolean.parseBoolean(this.env.getOptions().getOrDefault("enableUnRemovable", "false"));
        String unRemove = b ? "@Unremovable" : "";
        String beanTemplate = """
                package io.github.dreamlike.beans.generated;
                    import io.github.dreamlike.beans.Enhance;
                    import io.quarkus.arc.Unremovable;
                    import jakarta.inject.Singleton;
                   @Singleton
                   %s
                   public class Service%d {
                
                       @Enhance
                       public void run() {
                
                       }
                   }
                
                """;
        for (int i = 0; i < count; i++) {
            try (OutputStream outputStream = env.getFiler().createSourceFile(String.format("io.github.dreamlike.beans.generated.Service%d", i)).openOutputStream()) {
                outputStream.write(String.format(beanTemplate, unRemove, i).getBytes());
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void generateSpringBean(int count) {
        String beanTemplate = """
                package io.github.dreamlike.generated;
                
                 @org.springframework.stereotype.Service
                 public class Service%d implements io.github.dreamlike.BaseService {
                
                 }
                
                """;
        for (int i = 0; i < count; i++) {
            try (OutputStream outputStream = env.getFiler().createSourceFile(String.format("io.github.dreamlike.generated.Service%d", i)).openOutputStream()) {
                outputStream.write(String.format(beanTemplate, i).getBytes());
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    private void generateCondy() throws IOException {
        ClassFile classFile = ClassFile.of();
        String className = Dynamic_Test_Case_Class_name;
        byte[] classByteCode = classFile.build(ClassDesc.of(className), cb -> {

            cb.withMethodBody(INIT_NAME, MTD_void, AccessFlag.PUBLIC.mask(), it -> {
                it.aload(0);
                it.invokespecial(CD_Object, INIT_NAME, MTD_void);
                it.return_();
            });

            cb.withMethodBody(
                    Condy_Method_Name,
                    MethodTypeDesc.of(ClassDesc.of(Object.class.getName())),
                    AccessFlag.PUBLIC.mask() | AccessFlag.SYNTHETIC.mask(),
                    it -> {

                        it.loadConstant(
                                DynamicConstantDesc.of(
                                        ofConstantBootstrap(
                                                ClassDesc.of("io.github.dreamlike.DynamicEntryLeydenCase")
                                                , "condyFactory", Object.class.describeConstable().get())
                                )
                        );
                        it.areturn();
                    }
            );

            cb.withMethodBody(
                    INDY_MTD,
                    MethodTypeDesc.of(ClassDesc.of(Object.class.getName())),
                    AccessFlag.PUBLIC.mask() | AccessFlag.SYNTHETIC.mask(),
                    it -> {

                        it.invokedynamic(
                                DynamicCallSiteDesc.of(
                                        ConstantDescs.ofCallsiteBootstrap(
                                                ClassDesc.of("io.github.dreamlike.DynamicEntryLeydenCase"),
                                                "indyFactory",
                                                ConstantCallSite.class.describeConstable().get()
                                        ),
                                        "get",
                                        MethodType.methodType(Object.class).describeConstable().get()
                                )
                        );
                        it.areturn();
                    }
            );
        });
        generate(className, classByteCode);
    }

    private void generate(String className, byte[] bytecode) throws IOException {
        try (var ouputStream = this.env.getFiler().createClassFile(className).openOutputStream()) {
            ouputStream.write(bytecode);
            ouputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateProfile() throws IOException {
        boolean enableGroovy = Boolean.parseBoolean(env.getOptions().getOrDefault("enableGroovy", "false"));
        String template = """
                package io.github.dreamlike;
                
                public class Profile {
                    public static final boolean enableGroovy = %s;
                }
                
                """;
        String content = String.format(template, enableGroovy ? "true" : "false");

        try (OutputStream outputStream = env.getFiler().createSourceFile("Profile").openOutputStream()) {
            outputStream.write(content.getBytes());
            outputStream.flush();
        }
    }
}
