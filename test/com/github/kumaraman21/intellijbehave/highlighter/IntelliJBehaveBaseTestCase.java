package com.github.kumaraman21.intellijbehave.highlighter;

import com.github.kumaraman21.intellijbehave.parser.StoryElementType;
import com.github.kumaraman21.intellijbehave.parser.StoryParser;
import com.github.kumaraman21.intellijbehave.parser.StoryParserDefinition;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.testFramework.PsiTestCase;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.TestFixtureBuilder;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class IntelliJBehaveBaseTestCase extends PsiTestCase {

    private Project myProject;
    protected IdeaProjectTestFixture myFixture;
    protected CodeStyleSettings mySettings;

    public Project getProject() {
        return myProject;
    }

    protected CodeStyleSettings getSettings() {
        return CodeStyleSettingsManager.getSettings(myProject);
    }

//    protected void setSettings() {
//        final StoryFileType fileType = StoryFileType.STORY_FILE_TYPE;
//        mySettings = getSettings();
//        mySettings.getIndentOptions(fileType).INDENT_SIZE = 2;
//        mySettings.getIndentOptions(fileType).CONTINUATION_INDENT_SIZE = 2;
//        mySettings.getIndentOptions(fileType).TAB_SIZE = 2;
//    }

    protected void setUp() throws Exception {
        System.setProperty("idea.platform.prefix", "Idea");
        myFixture = createFixture();
        myFixture.setUp();
        myProject = myFixture.getProject();
    }

    protected IdeaProjectTestFixture createFixture() {
        TestFixtureBuilder<IdeaProjectTestFixture> fixtureBuilder = IdeaTestFixtureFactory.getFixtureFactory().createLightFixtureBuilder();
        return fixtureBuilder.getFixture();
    }

    protected void tearDown() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                try {
                    if(!myFixture.getProject().isDisposed())
                        myFixture.tearDown();
                } catch (Exception e) {
                    // mute
                }
            }
        });
    }

    public void testCase_Simple () {
        ASTNode astNode = doParse(Samples.SIMPLE_SAMPLE);
        System.out.println("IntelliJBehaveBaseTestCase.testCase_Simple: " + DebugUtil.treeToString(astNode, false));
    }

    public void testCase_Long () {
        ASTNode astNode = doParse(Samples.LONG_SAMPLE);
        System.out.println("IntelliJBehaveBaseTestCase.testCase_Long: " + DebugUtil.treeToString(astNode, false));
    }

    public void testCase_Meta () {
        ASTNode astNode = doParse(Samples.META_SAMPLE);
        System.out.println("IntelliJBehaveBaseTestCase.testCase_Meta: " + DebugUtil.treeToString(astNode, false));
    }

    public void testCase_Examples () {
        ASTNode astNode = doParse(Samples.EXAMPLES_SAMPLE);
        System.out.println("IntelliJBehaveBaseTestCase.testCase_Examples: " + DebugUtil.treeToString(astNode, false));
    }

    public void testCase_Complex() {
        ASTNode astNode = doParse(Samples.COMPLEX_SAMPLE);
        System.out.println("IntelliJBehaveBaseTestCase.testCase_Complex: " + DebugUtil.treeToString(astNode, false));
    }

    private boolean localizedParser = true;

    private ASTNode doParse(String content) {
        PsiBuilder builder = new PsiBuilderImpl(myProject,
                null,
                new StoryParserDefinition(),
                localizedParser ? new StoryLocalizedLexer() : new StoryLexer(),
                null,
                content,
                null,
                null);

        StoryParser parser = new StoryParser();
        return parser.parse(StoryElementType.STORY_FILE, builder);
    }

}
