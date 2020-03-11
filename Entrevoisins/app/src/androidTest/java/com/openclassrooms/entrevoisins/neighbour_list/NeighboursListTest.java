
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we delete an item from the favorites list, the item is no more shown in the 2 lists  //?!?
     */
    @Test
    public void myNeighboursFavorite_deleteAction_shouldRemoveItem() {
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.details_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.details_na)).check(matches(isDisplayed()))
                .check(matches(isDisplayed()))
                .check(matches(withText("Vincent")));
        onView(withId(R.id.fav))
                .perform(click());
        pressBack();
        onView(withContentDescription("Favorites")).perform(click());
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(1))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(0));
        onView(withContentDescription("My neighbours")).perform(click());

        onView(Matchers.allOf(withId(R.id.item_list_name), withText("Vincent"),childAtPosition(Matchers.allOf(withId(R.id.constraint),childAtPosition(withId(R.id.list_neighbours),3)),1),isDisplayed()))
                .check(doesNotExist());

    }

    /**
     * When we click on an item, Details activity shown   //1
     */
    @Test
    public void myNeighbourDetailsActivity_isDisplay() {
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_activity)).check(matches(isDisplayed()));
    }

    /**
     * When we click on an item, Details activity shown NeighbourDetailsName is correct  //2
     */
    @Test
    public void myNeighbourDetailsName_isDisplay() {
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.details_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.details_na)).check(matches(isDisplayed()))
                .check(matches(withText("Jack")));
    }

    /**
     * Check favorite list contain only favorite neighbour  //4
     */
    @Test
    public void checkFavoritesList() {
        onView(withContentDescription("Favorites")).perform(click());
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(0));

        onView(withContentDescription("My neighbours")).perform(click());
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.details_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.fav)).perform(click());
        pressBack();

        onView(withContentDescription("Favorites")).perform(click());
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .check(withItemCount(1)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.details_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.fav)).perform(click());
        pressBack();
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .check(withItemCount(0));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}