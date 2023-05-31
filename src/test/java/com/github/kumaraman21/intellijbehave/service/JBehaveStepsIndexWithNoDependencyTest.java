package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

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
        return new JBehaveStepsIndexTest.ContentRootsProjectDescriptor();
    }

    public void testFindsNoStepDefinitionDueToNoJBehaveAnnotationsAvailable() {
        myFixture.configureByFile("test/resources/has_java_step_def.story");

        JBehaveStep step = (JBehaveStep) myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        var stepDefinitions = JBehaveStepsIndex.getInstance(getProject()).findStepDefinitions(step);

        assertThat(stepDefinitions).isEmpty();
    }
}
