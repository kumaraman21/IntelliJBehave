package com.github.kumaraman21.intellijbehave.parser;

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
    final PsiBuilder.Marker rootMarker = builder.mark();

    parseStory(builder);

    rootMarker.done(root);
    return builder.getTreeBuilt();
  }

  private void parseStory(PsiBuilder builder) {
    final PsiBuilder.Marker storyMarker = builder.mark();
    parseStoryDescriptionLinesIfPresent(builder);
    parseScenarios(builder);
    storyMarker.done(StoryElementType.STORY);
  }

  private void parseStoryDescriptionLinesIfPresent(PsiBuilder builder) {
    if(builder.getTokenType() == StoryTokenType.STORY_DESCRIPTION) {
      while(builder.getTokenType() == StoryTokenType.STORY_DESCRIPTION) {
       parseStoryDescriptionLine(builder);
      }
     }
  }

  private void parseStoryDescriptionLine(PsiBuilder builder) {
   builder.advanceLexer();
  }

  private void parseScenarios(PsiBuilder builder) {
    if(builder.getTokenType() == StoryTokenType.SCENARIO_TEXT) {
      while(builder.getTokenType() == StoryTokenType.SCENARIO_TEXT) {
        parseScenario(builder);
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
    parseSteps(builder);
    parseStoryDescriptionLinesIfPresent(builder);
    stepMarker.done(StoryElementType.SCENARIO);
  }

  private void parseSteps(PsiBuilder builder) {
    if(builder.getTokenType() == StoryTokenType.STEP_TYPE) {
      while(builder.getTokenType() == StoryTokenType.STEP_TYPE) {
        parseStep(builder);
      }
    }
    else {
      builder.error("Step expected");
    }
  }

  private void parseStep(PsiBuilder builder) {
    final PsiBuilder.Marker stepMarker = builder.mark();
    parseStepType(builder);
    parseStepText(builder);
    stepMarker.done(StoryElementType.STEP);
  }

  private void parseStepType(PsiBuilder builder) {
    builder.advanceLexer();
  }

  private void parseStepText(PsiBuilder builder) {
    if(builder.getTokenType() == StoryTokenType.STEP_TEXT) {
      builder.advanceLexer();
    }
    else {
      builder.error("Step text expected");
    }
  }
}
