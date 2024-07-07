package io.github.dreamlike;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.classfile.AccessFlags;
import java.lang.classfile.ClassFile;
import java.lang.classfile.constantpool.ConstantDynamicEntry;
import java.lang.classfile.constantpool.ConstantPoolBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.DirectMethodHandleDesc;
import java.lang.constant.DynamicCallSiteDesc;
import java.lang.constant.DynamicConstantDesc;
import java.lang.constant.MethodHandleDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessFlag;
import java.util.Set;

import static java.lang.constant.ConstantDescs.CD_Object;
import static java.lang.constant.ConstantDescs.INIT_NAME;
import static java.lang.constant.ConstantDescs.MTD_void;
import static java.lang.constant.ConstantDescs.ofConstantBootstrap;

@SupportedAnnotationTypes("io.github.dreamlike.EnableGenerate")
@SupportedSourceVersion(SourceVersion.RELEASE_22)
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

        hasGenerated = true;
        try {
            generateCondy();
            generateProfile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    private void generateCondy() throws IOException {
        ClassFile classFile = ClassFile.of();
        String className = Dynamic_Test_Case_Class_name;
        byte[] classByteCode = classFile.build(ClassDesc.of(className), cb -> {
            cb.withMethodBody(INIT_NAME, MTD_void, AccessFlags.ofMethod(AccessFlag.PUBLIC).flagsMask(), it -> {
                it.aload(0);
                it.invokespecial(CD_Object, INIT_NAME, MTD_void);
                it.return_();
            });

            cb.withMethodBody(
                    Condy_Method_Name,
                    MethodTypeDesc.of(ClassDesc.of(Object.class.getName())),
                    AccessFlags.ofMethod(AccessFlag.PUBLIC, AccessFlag.SYNTHETIC).flagsMask(),
                    it -> {
                        ConstantPoolBuilder poolBuilder = it.constantPool();
                        ConstantDynamicEntry constantDynamicEntry = poolBuilder.constantDynamicEntry(DynamicConstantDesc.of(
                                ofConstantBootstrap(
                                        ClassDesc.of("io.github.dreamlike.DynamicEntryLeydenCase")
                                        , "condyFactory", Object.class.describeConstable().get())
                        ));
                        it.constantInstruction(constantDynamicEntry.constantValue());
                        it.areturn();
                    }
            );

            cb.withMethodBody(
                    INDY_MTD,
                    MethodTypeDesc.of(ClassDesc.of(Object.class.getName())),
                    AccessFlags.ofMethod(AccessFlag.PUBLIC, AccessFlag.SYNTHETIC).flagsMask(),
                    it -> {

                        it.invokeDynamicInstruction(
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
