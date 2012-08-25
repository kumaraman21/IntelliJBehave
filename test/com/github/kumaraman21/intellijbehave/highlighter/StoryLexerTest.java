package com.github.kumaraman21.intellijbehave.highlighter;

import static org.fest.assertions.api.Assertions.assertThat;

import com.intellij.psi.tree.IElementType;

import org.junit.Test;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class StoryLexerTest {

    private static final String SAMPLE_1 =
            "Scenario: An unknown user cannot be logged\n" + //
            "\n" + //
            "Meta:\n" + //
            "@skip\n" + //
            "\n" + //
            "Given i am the user with nickname: \"weird\"\n" + //
            "When i try to login using the password \"soweird\"\n" + //
            "Then i get an error message of type \"Wrong Credentials\"\n";
    private StoryLexer storyLexer;

    @Test
    public void traceAll () {
        traceAll(SAMPLE_1);
    }

    @Test
    public void simpleStory () {
        storyLexer = new StoryLexer();
        storyLexer.start(SAMPLE_1);

        assertThat(storyLexer.getTokenType()).isEqualTo(StoryTokenType.SCENARIO_TEXT);
        assertThat(storyLexer.getTokenSequence()).isEqualTo("Scenario: An unknown user cannot be logged");

        advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "Scenario: ");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META, "Meta:");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META_KEY, "@skip");
    }

    private void advanceAndAssert(IElementType storyTokenType) {
        storyLexer.advance();
        assertThat(storyLexer.getTokenType()).isEqualTo(storyTokenType);
    }

    private void advanceAndAssert(IElementType storyTokenType, String content) {
        storyLexer.advance();
        assertThat(storyLexer.getTokenType()).isEqualTo(storyTokenType);
        assertThat(storyLexer.getTokenSequence()).isEqualTo(content);
    }

    private void traceAll(String content) {
        StoryLexer storyLexer = new StoryLexer();
        storyLexer.start(content);

        IElementType tokenType;
        do {
            tokenType = storyLexer.getTokenType();
            System.out.println("[" +
                    rightPad(tokenType, "STORY_DESCRIPTION".length()) + "]" +
                    rightPad(storyLexer.lexerState(), "IN_DIRECTIVE".length()) +
                    ": >>" + escape(storyLexer.getTokenSequence()) + "<<");

            storyLexer.advance();
            tokenType = storyLexer.getTokenType();
        }
        while(tokenType!=null);
    }

    private String rightPad(Object object, int length) {
        StringBuilder builder = new StringBuilder(object.toString());
        while(builder.length()<length) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private String escape(CharSequence tokenSequence) {
        return tokenSequence.toString().replace("\n","\\n").replace("\r", "\\r");
    }

    public static final String SAMPLE_2 = "Narrative: \n" + //
            "In order to play a game\n" + //
            "As a player\n" + //
            "I want to be able to create and manage my account\n" + //
            "\n" + //
            "Scenario: An unknown user cannot be logged\n" + //
            "\n" + //
            "Meta:\n" + //
            "@skip\n" + //
            "\n" + //
            "Given i am the user with nickname: \"weird\"\n" + //
            "When i try to login using the password \"soweird\"\n" + //
            "Then i get an error message of type \"Wrong Credentials\"\n" + //
            "\n" + //
            "\n" + //
            "Scenario: A known user cannot be logged using a wrong password\n" + //
            "\n" + //
            "Given the following existing users:\n" + //
            "| nickname | password |\n" + //
            "|   Travis |   PacMan |\n" + //
            "Given i am the user with nickname: \"Travis\"\n" + //
            "When i try to login using the password \"McCallum\"\n" + //
            "Then i get an error message of type \"Wrong Credentials\"\n" + //
            "\n" + //
            "\n" + //
            "Scenario: A known user can be logged using the right password\n" + //
            "\n" + //
            "Given the following existing users:\n" + //
            "| nickname | password |\n" + //
            "|   Travis |   PacMan |\n" + //
            "Given i am the user with nickname: \"Travis\"\n" + //
            "When i try to login using the password \"PacMan\"\n" + //
            "Then i get logged\n" + //
            "And a welcome message is displayed\n" + //
            "\n" + //
            "\n" + //
            "Scenario: A user can create a new account\n" + //
            "\n" + //
            "Given i'm on the login page\n" + //
            "And the \"create account\" behavior is allowed\n" + //
            "When i create a new account with the following data:\n" + //
            "| nickname | password1 | password2 |      email       |\n" + //
            "|   Travis |   PacMan  |   PacMan  | travis@subsp.ace |\n" + //
            "Then i get logged\n" + //
            "And a welcome message is displayed\n" + //
            "\n" + //
            "\n" + //
            "Scenario: Email is required to create a new account\n" + //
            "\n" + //
            "Given i'm on the login page\n" + //
            "And the \"create account\" behavior is allowed\n" + //
            "When i create a new account with the following data:\n" + //
            "| nickname | password |      email       |\n" + //
            "|   Travis |   PacMan |                  |\n" + //
            "Then i get an error message of type \"Email Missing\"\n" + //
            "\n" + //
            "\n" + //
            "Scenario: Two identical passwords input are required to create a new account\n" + //
            "\n" + //
            "Given i'm on the login page\n" + //
            "And the \"create account\" behavior is allowed\n" + //
            "When i create a new account with the following data:\n" + //
            "| nickname | password1 | password2 |      email       |\n" + //
            "|   Travis |   PacMan  |   PocMan  | travis@subsp.ace |\n" + //
            "Then i get an error message of type \"Password Double-Check Failed\"\n" + //
            "\n" + //
            "\n" + //
            "Scenario: Nickname must be unique on account creation\n" + //
            "\n" + //
            "Given the following existing users:\n" + //
            "| nickname | password |\n" + //
            "|   Travis |   PacMan |\n" + //
            "Given i'm on the login page\n" + //
            "And the \"create account\" behavior is allowed\n" + //
            "When i create a new account with the following data:\n" + //
            "| nickname | password1 | password2 |      email       |\n" + //
            "|   Travis |   PucMan  |   PucMan  | travis@subsp.ace |\n" + //
            "Then i get an error message of type \"Nickname Already Used\"\n" + //
            "\n";
}
