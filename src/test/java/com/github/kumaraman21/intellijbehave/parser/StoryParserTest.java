package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.Samples;
import com.github.kumaraman21.intellijbehave.highlighter.StoryLexer;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.impl.PsiBuilderImpl;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class StoryParserTest extends IntelliJBehaveBaseTestCase {

    public void _testParseStoryFile() {
        PsiBuilder builder = new PsiBuilderImpl(getProject(),
                null,
                new StoryParserDefinition(),
                new StoryLexer(),
                null,
                Samples.SIMPLE_SAMPLE,
                null,
                null);

        StoryParser parser = new StoryParser();
        parser.parse(StoryElementType.STORY_FILE, builder);
    }
}
