package rs.ac.metropolitan.cs330.znamenitosti.gui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * @author nikola
 */
public class FancyTextView extends TextView {

    public FancyTextView(Context context) {
        super(context);
        init();
    }

    public FancyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FancyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setFont();
    }

    private void setFont() {
        try {
            if (getTypeface().isBold() && getTypeface().isItalic()) {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Essays1743-BoldItalic.ttf"));
            } else if (getTypeface().isBold()) {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Essays1743-Bold.ttf"));
            } else if (getTypeface().isItalic()) {
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Essays1743-Italic.ttf"));
            }
        } catch (Exception ex) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Essays1743.ttf"));
        }
    }
}
