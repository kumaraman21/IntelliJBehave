/*
 * Copyright 2011-12 Aman Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kumaraman21.intellijbehave.parser;

import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.STEP_TEXT_TO_STORY_ELEMENT_TYPE_MAPPING;

import com.github.kumaraman21.intellijbehave.highlighter.StoryTokenType;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;

public class StoryParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        builder.setDebugMode(true);
        final PsiBuilder.Marker rootMarker = builder.mark();
        parseStory(builder);

        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    private void parseStory(PsiBuilder builder) {
        final PsiBuilder.Marker storyMarker = builder.mark();

        skipWhitespacesOrComments(builder);
        parseStoryDescriptionLinesIfPresent(builder);
        parseScenarios(builder);
        storyMarker.done(StoryElementType.STORY);
    }

    private static void skipWhitespacesOrComments(PsiBuilder builder) {
        while(builder.getTokenType() == StoryTokenType.WHITE_SPACE
                || builder.getTokenType() == StoryTokenType.COMMENT) {
            if(builder.getTokenType() == StoryTokenType.COMMENT) {
                PsiBuilder.Marker commentMark = builder.mark();
                while(builder.getTokenType() == StoryTokenType.COMMENT) {
                    builder.advanceLexer();
                }
                commentMark.done(StoryElementType.COMMENT);
            }
            else {
                builder.advanceLexer();
            }
        }
    }

    private void parseStoryDescriptionLinesIfPresent(PsiBuilder builder) {
        if(builder.getTokenType() != StoryTokenType.STORY_DESCRIPTION) {
            return;
        }

        PsiBuilder.Marker marker = builder.mark();
        while (builder.getTokenType() == StoryTokenType.STORY_DESCRIPTION) {
            parseStoryDescriptionLine(builder);
            skipWhitespacesOrComments(builder);
        }
        marker.done(StoryElementType.STORY_DESCRIPTION);
    }

    private void parseStoryDescriptionLine(PsiBuilder builder) {
        builder.advanceLexer();
    }

    private void parseScenarios(PsiBuilder builder) {
        if (builder.getTokenType() == StoryTokenType.SCENARIO_TYPE) {
            while(builder.getTokenType() == StoryTokenType.SCENARIO_TYPE) {
                parseScenario(builder);
                skipWhitespacesOrComments(builder);
            }
        }
        else {
            builder.advanceLexer();
            builder.error("Scenario expected");
        }
    }

    private void parseScenario(PsiBuilder builder) {
        final PsiBuilder.Marker stepMarker = builder.mark();
        builder.advanceLexer();
        skipWhitespacesOrComments(builder);
        while (builder.getTokenType() == StoryTokenType.SCENARIO_TEXT) {
            parseScenarioText(builder);
            skipWhitespacesOrComments(builder);
        }
        parseMeta(builder);
        parseSteps(builder);
        skipWhitespacesOrComments(builder);
        parseStoryDescriptionLinesIfPresent(builder);
        skipWhitespacesOrComments(builder);
        parseExamples(builder);
        stepMarker.done(StoryElementType.SCENARIO);
    }

    private void parseScenarioText(PsiBuilder builder) {
        builder.advanceLexer();
    }

    private void parseMeta(PsiBuilder builder) {
        if (builder.getTokenType() == StoryTokenType.META) {
            final PsiBuilder.Marker stepMarker = builder.mark();
            while (builder.getTokenType() == StoryTokenType.META
                    || builder.getTokenType() == StoryTokenType.META_TEXT
                    || builder.getTokenType() == StoryTokenType.META_KEY) {
                builder.advanceLexer();
                skipWhitespacesOrComments(builder);
            }
            stepMarker.done(StoryElementType.META);
        }
    }

    private void parseSteps(PsiBuilder builder) {
        parseStoryDescriptionLinesIfPresent(builder);
        if (builder.getTokenType() == StoryTokenType.STEP_TYPE) {

            StoryElementType previousStepElementType = null;
            while (builder.getTokenType() == StoryTokenType.STEP_TYPE) {
                previousStepElementType = parseStep(builder, previousStepElementType);
                skipWhitespacesOrComments(builder);
                parseStoryDescriptionLinesIfPresent(builder);
            }
        }
        else {
            builder.error("At least one step expected");
        }
    }

    private StoryElementType parseStep(PsiBuilder builder, StoryElementType previousStepElementType) {
        final PsiBuilder.Marker stepMarker = builder.mark();

        StoryElementType currentStepElementType;

        String stepTypeText = builder.getTokenText().trim().toUpperCase();
        if (stepTypeText.equals("AND")) {
            currentStepElementType = previousStepElementType;
        }
        else {
            currentStepElementType = STEP_TEXT_TO_STORY_ELEMENT_TYPE_MAPPING.get(stepTypeText);
        }

        parseStepType(builder);
        parseStepText(builder);
        skipWhitespacesOrComments(builder);
        parseTableIfPresent(builder);
        skipWhitespacesOrComments(builder);
        stepMarker.done(currentStepElementType);

        return currentStepElementType;
    }

    private void parseStepType(PsiBuilder builder) {
        builder.advanceLexer();
    }

    private void parseStepText(PsiBuilder builder) {
        if (builder.getTokenType() == StoryTokenType.STEP_TEXT) {
            while (builder.getTokenType() == StoryTokenType.STEP_TEXT) {
                builder.advanceLexer();
                skipWhitespacesOrComments(builder);
            }
        }
        else {
            builder.error("Step text expected");
        }
    }

    private void parseExamples(PsiBuilder builder) {
        if (builder.getTokenType() == StoryTokenType.EXAMPLE_TYPE) {
            final PsiBuilder.Marker stepMarker = builder.mark();
            builder.advanceLexer();
            skipWhitespacesOrComments(builder);
            if (builder.getTokenType() == StoryTokenType.TABLE_DELIM) {
                parseTableIfPresent(builder);
            }
            else {
                builder.error("Table row expected");
            }

            stepMarker.done(StoryElementType.EXAMPLES);
        }
    }

    private void parseTableIfPresent(PsiBuilder builder) {
        if (builder.getTokenType() == StoryTokenType.TABLE_DELIM) {
            while (builder.getTokenType() == StoryTokenType.TABLE_DELIM
                    || builder.getTokenType() == StoryTokenType.TABLE_CELL) {
                parseTableToken(builder);
                skipWhitespacesOrComments(builder);
            }
        }
    }

    private void parseTableToken(PsiBuilder builder) {
        builder.advanceLexer();
    }
}
