package uk.cpjsmith.ponypaper;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import android.util.Log;

/**
 * A {@link android.preference.Preference} that displays a number picker as a dialog.
 */
public class NumberPickerPreference extends DialogPreference {

    // allowed range
    public static final int DEFAULT_MAX_VALUE = 10;
    public static final int DEFAULT_MIN_VALUE = 1;
    // enable or disable the 'circular behavior'
    public static final boolean DEFAULT_WRAP_SELECTOR_WHEEL = true; 

    private NumberPicker picker;
    private int value;

    private int maxValue;
    private int minValue;
    private boolean wrapSelectorWheel;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        maxValue = attrs.getAttributeIntValue("http://cpjsmith.uk/ponypaper/custom", "maxValue", DEFAULT_MAX_VALUE);
        minValue = attrs.getAttributeIntValue("http://cpjsmith.uk/ponypaper/custom", "minValue", DEFAULT_MIN_VALUE);
        wrapSelectorWheel = attrs.getAttributeBooleanValue("http://cpjsmith.uk/ponypaper/custom", "wrap", DEFAULT_WRAP_SELECTOR_WHEEL);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        maxValue = attrs.getAttributeIntValue("http://cpjsmith.uk/ponypaper/custom", "maxValue", DEFAULT_MAX_VALUE);
        minValue = attrs.getAttributeIntValue("http://cpjsmith.uk/ponypaper/custom", "minValue", DEFAULT_MIN_VALUE);
        wrapSelectorWheel = attrs.getAttributeBooleanValue("http://cpjsmith.uk/ponypaper/custom", "wrap", DEFAULT_WRAP_SELECTOR_WHEEL);
    }

    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);

        return dialogView;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setWrapSelectorWheel(wrapSelectorWheel);
        picker.setValue(getValue());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            int newValue = picker.getValue();
            if (callChangeListener(newValue)) {
                setValue(newValue);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, minValue);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedInt(minValue) : (Integer) defaultValue);
    }

    public void setValue(int value) {
        this.value = value;
        persistInt(this.value);
    }

    public int getValue() {
        return this.value;
    }
}
