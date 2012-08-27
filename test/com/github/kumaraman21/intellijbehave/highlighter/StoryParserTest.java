package com.github.kumaraman21.intellijbehave.highlighter;

import com.github.kumaraman21.intellijbehave.parser.StoryElementType;
import com.github.kumaraman21.intellijbehave.parser.StoryParser;
import com.github.kumaraman21.intellijbehave.parser.StoryParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.openapi.project.Project;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 *
 * @see IntelliJBehaveBaseTestCase
 */
@Ignore("... need a full plateform initialization!?!")
public class StoryParserTest {

    @Test
    public void test1() throws Throwable {
        Project project = Mockito.mock(Project.class);
        PsiBuilder builder = new PsiBuilderImpl(project, null, new StoryParserDefinition(), new StoryLexer(), null, Samples.SIMPLE_SAMPLE, null, null);

        StoryParser parser = new StoryParser();
        parser.parse(StoryElementType.STORY_FILE, builder);
    }
}
