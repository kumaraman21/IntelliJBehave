package com.github.kumaraman21.intellijbehave.highlighter;

import static com.github.kumaraman21.intellijbehave.Samples.COMPLEX_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.EXAMPLES_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.LONG_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.META_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.MULTILINE_LONG_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.MULTILINE_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.SIMPLE_SAMPLE;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.intellij.psi.tree.IElementType;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class StoryLexerTest {

	private StoryLexer storyLexer;

	@Test
	@Ignore
	public void traceAll() {
		//traceAll(SIMPLE_SAMPLE);
		//traceAll(LONG_SAMPLE);
		//traceAll(META_SAMPLE);
		//traceAll(EXAMPLES_SAMPLE);
		traceAll(COMPLEX_SAMPLE);
	}

	@Test
	public void parseSimpleSample() {
		storyLexer = new StoryLexer();
		storyLexer.start(SIMPLE_SAMPLE);

		assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "An unknown user cannot be logged");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META, "Meta:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META_KEY, "@skip");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"weird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try to login using the password \"soweird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "Then ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i get an error message of type \"Wrong Credentials\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
	}

	@Test
	public void parseMultilineSample() {
		storyLexer = new StoryLexer();
		storyLexer.start(MULTILINE_SAMPLE);

		assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "An unknown user cannot be logged");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META, "Meta:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META_KEY, "@skip");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"weird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try to login using the password \"soweird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "Then ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i get an error message of type \"Wrong Credentials\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
	}

	@Test
	public void parseMultilineLong() {
		storyLexer = new StoryLexer();
		storyLexer.start(MULTILINE_LONG_SAMPLE);

		assertToken(StoryTokenType.STORY_DESCRIPTION, "Narrative: ");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, "In order to play a game");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, "As a player");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, "I want to be able to create and manage my account");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "An unknown user cannot be logged");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META, "Meta:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META_KEY, "@skip");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"weird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try to login using the password \"soweird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "Then ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i get an error message of type \"Wrong Credentials\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "A known user cannot be logged using a wrong password");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "the following existing users:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, " nickname ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, " password ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   Travis ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   PacMan ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"Travis\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try to login using the password \"McCallum\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "Then ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i get an error message of type \"Wrong Credentials\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "A known user can be logged using the right password");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "the following existing users:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, " nickname ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, " password ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   Travis ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   PacMan ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"Travis\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try to login using the password \"PacMan\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "Then ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i get logged");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "And ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "a welcome message is displayed");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try again to login using the password \"PacMan\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
	}

	@Test
	public void parseMetaSample() {
		storyLexer = new StoryLexer();
		storyLexer.start(META_SAMPLE);

		assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "An unknown user cannot be logged");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META, "Meta:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META_KEY, "@author");
		advanceAndAssert(StoryTokenType.META_TEXT, " carmen");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META_KEY, "@skip");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"weird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
	}

	@Test
	public void parseLongSample() {
		storyLexer = new StoryLexer();
		storyLexer.start(LONG_SAMPLE);

		assertToken(StoryTokenType.STORY_DESCRIPTION, "Narrative: ");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, "In order to play a game");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, "As a player");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, "I want to be able to create and manage my account");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "An unknown user cannot be logged");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META, "Meta:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.META_KEY, "@skip");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"weird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try to login using the password \"soweird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "Then ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i get an error message of type \"Wrong Credentials\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "A known user cannot be logged using a wrong password");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "the following existing users:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, " nickname ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, " password ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   Travis ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   PacMan ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"Travis\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try to login using the password \"McCallum\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "Then ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i get an error message of type \"Wrong Credentials\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);

		// ...
	}

	@Test
	public void parseExamples() {
		storyLexer = new StoryLexer();
		storyLexer.start(EXAMPLES_SAMPLE);

		assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "An unknown user cannot be logged");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i am the user with nickname: \"<input>\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHEN_TYPE, "When ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i try to login using the password \"soweird\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.THEN_TYPE, "Then ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "i get an error message of type \"Wrong Credentials\"");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.EXAMPLE_TYPE, "Examples:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "  login   ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   password   ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "  Travis  ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   Pacman     ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "  Vlad    ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.TABLE_CELL, "   Thundercat ");
		advanceAndAssert(StoryTokenType.TABLE_DELIM);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario: ");
		advanceAndAssert(StoryTokenType.SCENARIO_TEXT, "A known user can be logged using the right password");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.WHITE_SPACE);
		advanceAndAssert(StoryTokenType.GIVEN_TYPE, "Given ");
		advanceAndAssert(StoryTokenType.STEP_TEXT, "the following existing users:");
		advanceAndAssert(StoryTokenType.WHITE_SPACE);

		// ...


	}

	private void advanceAndAssert(final IElementType storyTokenType) {
		storyLexer.advance();
		assertThat(storyLexer.getTokenType()).isEqualTo(storyTokenType);
	}

	private void advanceAndAssert(final IElementType storyTokenType, final String content) {
		storyLexer.advance();
		assertToken(storyTokenType, content);
	}

	private void assertToken(final IElementType storyTokenType, final String content) {
		assertThat(storyLexer.getTokenType()).isEqualTo(storyTokenType);
		assertThat(storyLexer.getTokenText().replaceAll("\\n", "")).isEqualTo(content);
	}

	private void traceAll(final String content) {
		final StoryLexer storyLexer = new StoryLexer();
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
		while (tokenType != null);
	}

	private String rightPad(final Object object, final int length) {
		final StringBuilder builder = new StringBuilder(object.toString());
		while (builder.length() < length) {
			builder.append(" ");
		}
		return builder.toString();
	}

	private String escape(final CharSequence tokenSequence) {
		return tokenSequence.toString().replace("\n", "\\n").replace("\r", "\\r");
	}


}
