package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.testFramework.fixtures.MavenDependencyUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootType;

/**
 * Functional test for {@link JBehaveStepsIndex}.
 */
public class JBehaveStepsIndexWithNoDependencyTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
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
        return new JBehaveStepsIndexTest.ContentRootsProjectDescriptor(false);
    }

    public void testFindsNoStepDefinitionDueToNoJBehaveAnnotationsAvailable() {
        myFixture.configureByFile("test/resources/has_java_step_def.story");

        JBehaveStep step = (JBehaveStep) myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        var stepDefinitions = JBehaveStepsIndex.getInstance(getProject()).findStepDefinitions(step);

        assertThat(stepDefinitions).isEmpty();
    }
}
