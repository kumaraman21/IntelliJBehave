package com.github.kumaraman21.intellijbehave.language;

import com.github.kumaraman21.intellijbehave.highlighter.StorySyntaxHighlighter;
import com.intellij.lang.CompositeLanguage;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

public class StoryLanguage extends CompositeLanguage {
  public static final StoryLanguage STORY_LANGUAGE = new StoryLanguage();

  private StoryLanguage() {
    super("Story", "text/story");
    SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
      @NotNull
      protected SyntaxHighlighter createHighlighter() {
        return new StorySyntaxHighlighter();
      }
    });
  }
}
