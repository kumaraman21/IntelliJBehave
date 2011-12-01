package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class StoryLexer extends FlexAdapter {
  public StoryLexer() {
    super(new _StoryLexer((Reader)null));
  }
}
