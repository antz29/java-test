package com.ford.henrysgroceries.utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class EqualsBigDecimalMatcher extends TypeSafeMatcher<BigDecimal> {

    private BigDecimal expected;

    public static Matcher<BigDecimal> is(BigDecimal expected) {
        return new EqualsBigDecimalMatcher(expected);
    }

    private EqualsBigDecimalMatcher(BigDecimal expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(BigDecimal actual) {
        return actual.setScale(2).compareTo(expected.setScale(2)) == 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equals " + NumberFormat.getCurrencyInstance().format(expected));
    }


}
