package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.lexer.LayeredLexer;

public class StorySyntaxHighlightingLexer extends LayeredLexer {
  public StorySyntaxHighlightingLexer() {
    super(new StoryLexer());
  }
}
