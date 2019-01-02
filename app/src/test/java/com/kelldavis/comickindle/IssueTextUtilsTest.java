package com.kelldavis.comickindle;

import com.kelldavis.comickindle.utils.IssueTextUtils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IssueTextUtilsTest {

  private static final String ISSUE_NAME = "King Solomon\'s Frog";
  private static final String VOLUME_NAME = "Black Panther (1977)";
  private static final int ISSUE_NUMBER = 1;

  private static final String FORMATTED_NAME_FULL = "Black Panther (1977) #1 - King Solomon\'s Frog";
  private static final String FORMATTED_NAME_SHORT = "Black Panther (1977) #1";

  @Test
  public void testGetFormattedIssueName() {

    // Assert
    assertEquals("getFormattedIssueName method returned incorrect string!",
        FORMATTED_NAME_FULL,
        IssueTextUtils.getFormattedIssueName(ISSUE_NAME, VOLUME_NAME, ISSUE_NUMBER));

    assertEquals("getFormattedIssueName method returned incorrect string!",
        FORMATTED_NAME_SHORT,
        IssueTextUtils.getFormattedIssueName(null, VOLUME_NAME, ISSUE_NUMBER));
  }

  @Test
  public void testGetFormattedIssueDetailsTitle() {
    assertEquals("getFormattedIssueTitle method returned incorrect string!",
        FORMATTED_NAME_SHORT,
        IssueTextUtils.getFormattedIssueTitle(VOLUME_NAME, ISSUE_NUMBER));
  }

}
