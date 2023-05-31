package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.io.File;

/**
 * Functional test for {@link JBehaveStepsIndex}.
 */
public class JBehaveStepsIndexTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        loadLibrary(myFixture.getProjectDisposable(), getModule(), "JBehave Core", "jbehave-core-5.1.1.jar");
        myFixture.copyDirectoryToProject("src", "");
        myFixture.copyFileToProject("main/java/StepDefs.java");
        myFixture.copyFileToProject("main/java/OtherStepDefs.java");
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/stepsindex";
    }

    @Override
    protected @NotNull LightProjectDescriptor getProjectDescriptor() {
        return new ContentRootsProjectDescriptor();
    }

    // findStepDefinitions

    public void testFindsSingleStepDefinition() {
        myFixture.configureByFile("test/resources/has_java_step_def.story");

        var stepDefinitions = JBehaveStepsIndex.getInstance(getProject()).findStepDefinitions(getStep());

        assertThat(stepDefinitions).hasSize(1);
        assertThat(stepDefinitions.iterator().next().getAnnotatedMethod().getContainingClass().getQualifiedName()).isEqualTo("StepDefs");
        assertThat(stepDefinitions.iterator().next().getAnnotatedMethod().getSignature(PsiSubstitutor.EMPTY))
                .hasToString("MethodSignatureBackedByPsiMethod: openAUrl([PsiType:String])");
    }

    //NOTE: at the moment, this only returns the first found step definition, regardless of the step pattern
    // existing for multiple different step types, e.g. for a @Given and a @Then step def, and regardless of step priority.
    //Supporting returning multiple matching step definitions should be revisited.
    public void testFindsMultipleStepDefinitions() {
        myFixture.configureByFile("test/resources/has_multiple_java_step_def.story");

        var stepDefinitions = JBehaveStepsIndex.getInstance(getProject()).findStepDefinitions(getStep());

        assertThat(stepDefinitions).hasSize(1);
        assertThat(stepDefinitions.iterator().next().getAnnotatedMethod().getContainingClass().getQualifiedName()).isEqualTo("OtherStepDefs");
        assertThat(stepDefinitions.iterator().next().getAnnotatedMethod().getSignature(PsiSubstitutor.EMPTY))
                .hasToString("MethodSignatureBackedByPsiMethod: checkResultListSize([PsiType:int])");
    }

    public void testFindsNoStepDefinition() {
        myFixture.configureByFile("test/resources/has_no_java_step_def.story");

        var stepDefinitions = JBehaveStepsIndex.getInstance(getProject()).findStepDefinitions(getStep());

        assertThat(stepDefinitions).isEmpty();
    }

    private JBehaveStep getStep() {
        return (JBehaveStep) myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
    }

    private static void loadLibrary(@NotNull Disposable projectDisposable, @NotNull Module module, String libraryName, String libraryJarName) {
        String libPath = PathUtil.toSystemIndependentName(new File("lib").getAbsolutePath());
        VfsRootAccess.allowRootAccess(projectDisposable, libPath);
        PsiTestUtil.addLibrary(projectDisposable, module, libraryName, libPath, libraryJarName);
    }

    public static class ContentRootsProjectDescriptor extends ProjectDescriptor {
        public ContentRootsProjectDescriptor() {
            super(LanguageLevel.JDK_11);
        }

        @Override
        public Sdk getSdk() {
            return JavaSdk.getInstance().createJdk("Real JDK", System.getenv("JAVA_HOME"), false);
        }

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            super.configureModule(module, model, contentEntry);
            contentEntry.clearSourceFolders();

            String contentEntryUrl = contentEntry.getUrl();
            contentEntry.addSourceFolder(contentEntryUrl + "/main/java", JavaSourceRootType.SOURCE);
            contentEntry.addSourceFolder(contentEntryUrl + "/main/resources", JavaResourceRootType.RESOURCE);
            contentEntry.addSourceFolder(contentEntryUrl + "/test/java", JavaSourceRootType.TEST_SOURCE);
            contentEntry.addSourceFolder(contentEntryUrl + "/test/resources", JavaResourceRootType.TEST_RESOURCE);
        }
    }
}
