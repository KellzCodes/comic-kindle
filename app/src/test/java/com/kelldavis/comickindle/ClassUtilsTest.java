package com.kelldavis.comickindle;

import static org.junit.Assert.assertEquals;

import com.kelldavis.comickindle.model.VolumeInfoList;
import com.kelldavis.comickindle.utils.ClassUtils;

import org.junit.Test;

public class ClassUtilsTest {

  @Test
  public void testGetFieldsReturnsCorrectString_forAutoValueClass() {

    // Arrange
    String detectedAVFields = ClassUtils.getMethodsList(VolumeInfoList.class);
    String actualFields = "count_of_issues,id,image,name,publisher,start_year";

    // Assert
    assertEquals("getMethodsList method returned incorrect list!",
        actualFields,
        detectedAVFields);
  }
}