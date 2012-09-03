package com.github.kumaraman21.intellijbehave.highlighter;

import static org.fest.assertions.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.utility.LocalizedKeywordsProvider;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class StoryLocalizedLexer_FrenchTest {

    private StoryLocalizedLexer storyLexer;

    @Test
    public void parse_basicScenario() {
        storyLexer = new StoryLocalizedLexer(new LocalizedKeywordsProvider());
        storyLexer.changeLocale("fr");
        storyLexer.start("Scénario: une simple sortie\n" +
                "Etant donné que nous allons promener notre chienne\n" +
                "Quand on sera dehors\n" +
                "Alors elle pourra se soulager!");

        assertToken(StoryTokenType.SCENARIO_TYPE, "Scénario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " une simple sortie");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE, "Etant donné que");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " nous allons promener notre chienne");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE, "Quand");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " on sera dehors");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE, "Alors");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " elle pourra se soulager!");
        advanceAndAssert(null);
    }

    @Test
    public void parse_commentAllowsToSwitchLanguage() {
        storyLexer = new StoryLocalizedLexer(new LocalizedKeywordsProvider());
        // make sure one is not in fr by default
        storyLexer.changeLocale("en");
        storyLexer.start("!-- language:fr\n" +
                "Scénario: une simple sortie\n" +
                "Etant donné que nous allons promener notre chienne\n" +
                "Quand on sera dehors\n" +
                "Alors elle pourra se soulager!");

        assertToken(StoryTokenType.COMMENT, "!-- language:fr");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scénario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " une simple sortie");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE, "Etant donné que");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " nous allons promener notre chienne");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE, "Quand");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " on sera dehors");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE, "Alors");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " elle pourra se soulager!");
        advanceAndAssert(null);
    }

    private void advanceAndAssert(@Nullable IElementType storyTokenType) {
        storyLexer.advance();
        assertThat(storyLexer.getTokenType()).isEqualTo(storyTokenType);
    }

    private void advanceAndAssert(IElementType storyTokenType, String content) {
        storyLexer.advance();
        assertToken(storyTokenType, content);
    }

    private void assertToken(IElementType storyTokenType, String content) {
        assertThat(storyLexer.getTokenSequence()).isEqualTo(content);
        assertThat(storyLexer.getTokenType()).isEqualTo(storyTokenType);
    }
}
