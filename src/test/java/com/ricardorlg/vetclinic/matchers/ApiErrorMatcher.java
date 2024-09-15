package com.ricardorlg.vetclinic.matchers;

import com.ricardorlg.vetclinc.models.api.ErrorsItem;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class ApiErrorMatcher extends TypeSafeMatcher<List<ErrorsItem>> {
    private final String expectedErrorCode;
    private final String expectedErrorField;

    private ApiErrorMatcher(String expectedErrorCode, String expectedErrorField) {
        this.expectedErrorCode = expectedErrorCode;
        this.expectedErrorField = expectedErrorField;
    }

    public static TypeSafeMatcher<List<ErrorsItem>> hasErrorWithCodeAndField(String expectedErrorCode, String expectedErrorField) {
        return new ApiErrorMatcher(expectedErrorCode, expectedErrorField);
    }

    @Override
    protected boolean matchesSafely(List<ErrorsItem> errorsItems) {
        return errorsItems.stream()
                .anyMatch(error -> error.code().equals(expectedErrorCode) && error.field().equals(expectedErrorField));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an error with code ")
                .appendValue(expectedErrorCode)
                .appendText(" and field ")
                .appendValue(expectedErrorField);
    }

    @Override
    protected void describeMismatchSafely(List<ErrorsItem> item, Description mismatchDescription) {
        mismatchDescription.appendText("errors were ")
                .appendValue(item.stream().map(errorsItem -> errorsItem.code() + " - " + errorsItem.field()).toList());
    }
}
