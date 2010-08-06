/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.view.client;

import com.google.gwt.view.client.SelectionModel.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel.SelectionChangeHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests for {@link DefaultSelectionModel}.
 */
public class DefaultSelectionModelTest extends AbstractSelectionModelTest {

  /**
   * A mock {@link DefaultSelectionModel} used for testing. By default, all
   * strings that start with "selected" are selected.
   */
  private static class MockDefaultSelectionModel extends
      DefaultSelectionModel<String> {

    @Override
    public boolean isDefaultSelected(String object) {
      return object == null ? false : object.startsWith("selected");
    }
  }

  public void testIsSelectedWithoutExceptions() {
    DefaultSelectionModel<String> model = createSelectionModel();
    assertFalse(model.isSelected(null));
    assertFalse(model.isSelected("test"));
    assertTrue(model.isSelected("selected"));
    assertTrue(model.isSelected("selected0"));
  }

  public void testSelectedChangeEvent() {
    DefaultSelectionModel<String> model = createSelectionModel();
    SelectionChangeHandler handler = new SelectionChangeHandler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        finishTest();
      }
    };
    model.addSelectionChangeHandler(handler);

    delayTestFinish(2000);
    model.setSelected("test", true);
  }

  public void testNoDuplicateChangeEvent() {
    DefaultSelectionModel<String> model = createSelectionModel();
    SelectionChangeHandler handler = new SelectionChangeHandler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        fail();
      }
    };

    model.setSelected("selected999", false);
    model.addSelectionChangeHandler(handler);
    model.setSelected("selected999", false); // Should not fire change event
    model.setSelected("selected999", false); // Should not fire change event
  }

  public void testSetSelectedDefault() {
    Map<Object, Boolean> exceptions = new HashMap<Object, Boolean>();
    DefaultSelectionModel<String> model = createSelectionModel();
    assertTrue(model.isSelected("selected0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(0, model.getExceptions(exceptions).size());

    model.setSelected("selected0", true);
    assertTrue(model.isSelected("selected0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(0, model.getExceptions(exceptions).size());

    model.setSelected("selected0", false);
    assertFalse(model.isSelected("selected0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(1, model.getExceptions(exceptions).size());
    assertFalse(exceptions.get("selected0"));

    model.setSelected("selected0", true);
    assertTrue(model.isSelected("selected0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(0, model.getExceptions(exceptions).size());
  }

  public void testSetSelectedNonDefault() {
    DefaultSelectionModel<String> model = createSelectionModel();
    assertFalse(model.isSelected("test0"));
    assertFalse(model.isSelected("test1"));
    assertTrue(model.isSelected("selected0"));

    model.setSelected("test0", true);
    assertTrue(model.isSelected("test0"));
    assertFalse(model.isSelected("test1"));
    assertTrue(model.isSelected("selected0"));

    model.setSelected("test1", true);
    assertTrue(model.isSelected("test0"));
    assertTrue(model.isSelected("test1"));
    assertTrue(model.isSelected("selected0"));

    model.setSelected("test1", false);
    assertTrue(model.isSelected("test0"));
    assertFalse(model.isSelected("test1"));
    assertTrue(model.isSelected("selected0"));
  }

  public void testSetSelectedWithKeyProvider() {
    Map<Object, Boolean> exceptions = new HashMap<Object, Boolean>();
    DefaultSelectionModel<String> model = createSelectionModel();
    ProvidesKey<String> keyProvider = new ProvidesKey<String>() {
      public Object getKey(String item) {
        return item.toUpperCase();
      }
    };
    model.setKeyProvider(keyProvider);
    assertFalse(model.isSelected("test"));
    assertTrue(model.isSelected("selected0"));
    assertFalse(model.isSelected("SELECTED0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(0, model.getExceptions(exceptions).size());

    model.setSelected("selected0", true);
    assertFalse(model.isSelected("test"));
    assertTrue(model.isSelected("selected0"));
    assertFalse(model.isSelected("SELECTED0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(0, model.getExceptions(exceptions).size());

    model.setSelected("selected0", false);
    assertFalse(model.isSelected("test"));
    assertFalse(model.isSelected("selected0"));
    assertFalse(model.isSelected("SELECTED0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(1, model.getExceptions(exceptions).size());
    assertFalse(exceptions.get("SELECTED0"));

    model.setSelected("selected0", true);
    assertFalse(model.isSelected("test"));
    assertTrue(model.isSelected("selected0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(0, model.getExceptions(exceptions).size());

    model.setSelected("test", true);
    assertTrue(model.isSelected("test"));
    assertTrue(model.isSelected("selected0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(1, model.getExceptions(exceptions).size());
    assertTrue(exceptions.get("TEST"));

    model.setSelected("test", false);
    assertFalse(model.isSelected("test"));
    assertTrue(model.isSelected("selected0"));
    assertTrue(model.isSelected("selected1"));
    assertEquals(0, model.getExceptions(exceptions).size());
  }

  @Override
  protected DefaultSelectionModel<String> createSelectionModel() {
    return new MockDefaultSelectionModel();
  }
}
