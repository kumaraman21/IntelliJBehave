package com.github.kumaraman21.intellijbehave.spellchecker;

import static com.intellij.spellchecker.tokenizer.SpellcheckingStrategy.EMPTY_TOKENIZER;
import static com.intellij.spellchecker.tokenizer.SpellcheckingStrategy.TEXT_TOKENIZER;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

/**
 * Unit test for {@link JBehaveSpellcheckerStrategy}.
 */
public class JBehaveSpellcheckerStrategyTest extends BasePlatformTestCase {

    public void testProvideSpellCheckFixesLeafStoryElements() {
        String text = "Narrative:\n" +
            "I want tea\n" +
            "\n" +
            "Scenario: Search for tea\n" +
            "\n" +
            "Given I open 'http://duckduckgo.com/'\n" +
            "When I <caret>search for 'shu puerh'\n" +
            "Then there should be 15 results\n" +
            "Then the text 'shu puerh' should be present";

        myFixture.configureByText(StoryFileType.STORY_FILE_TYPE, text);
        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertThat(new JBehaveSpellcheckerStrategy().getTokenizer(element)).isSameAs(TEXT_TOKENIZER);
    }

    public void testNotProvideSpellCheckFixesForNonStoryElements() {
        String text = "Narrative:\n" +
            "I want tea\n" +
            "\n" +
            "Scenario: Search for tea\n" +
            "<caret>\n" +
            "Given I open 'http://duckduckgo.com/'\n" +
            "When I search for 'shu puerh'\n" +
            "Then there should be 15 results\n" +
            "Then the text 'shu puerh' should be present";

        myFixture.configureByText(StoryFileType.STORY_FILE_TYPE, text);
        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertThat(new JBehaveSpellcheckerStrategy().getTokenizer(element)).isSameAs(EMPTY_TOKENIZER);
    }
}
