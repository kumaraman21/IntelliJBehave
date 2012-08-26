package com.github.kumaraman21.intellijbehave.highlighter;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Samples {

    public static final String SIMPLE_SAMPLE =
            "Scenario: An unknown user cannot be logged\n" + //
                    "\n" + //
                    "Meta:\n" + //
                    "@skip\n" + //
                    "\n" + //
                    "Given i am the user with nickname: \"weird\"\n" + //
                    "When i try to login using the password \"soweird\"\n" + //
                    "Then i get an error message of type \"Wrong Credentials\"\n";

    public static final String COMPLEX_SAMPLE =
            "Narrative: \n" + //
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
                    "\n";
}
