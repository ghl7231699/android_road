package com.ghl.common.widgets.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ghl.common.R;


/**
 * author: zhy
 * desc: 输入框类，如注册登录页
 */
public class LoginInputBox extends RelativeLayout {
    public static final int NormalType = 0;     //包含输入框上方title及输入框
    public static final int TextType = 1;       //包含输入框上方title及右侧文字
    public static final int CountryType = 2;    //包含输入框上方title及右侧选择国家
    public static final int ImageType = 3;      //包含输入框上方title，及右侧图片
    public static final int AUTO_CODE_RIGHT_TYPE = 4;       //验证码右侧

    private LinearLayout phone_country;         //输入框右侧选择国家
    private TextView login_text_text;           //输入框右侧文字
    private ImageView login_text_img;           //输入框右侧图片

    private TextView country_content;           //选择国家左侧文字
    private View country_line;                  //线
    private View mVCodeLine;                  //线

    private EditText phone_edit;                //输入框

    public LoginInputBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoginInputBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginInputBox(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.include_login_text, this);
        //输入框内容
        LinearLayout phone_content = view.findViewById(R.id.login_text_content);
        //输入框内容-内容输入
        phone_edit = phone_content.findViewById(R.id.login_text_edit);
        phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int style = s.length() > 0 ? Typeface.BOLD : Typeface.NORMAL;
                phone_edit.setTypeface(Typeface.defaultFromStyle(style));
                int textSize = s.length() > 0 ? 16 : 14;
                phone_edit.setTextSize(textSize);
            }
        });

        country_line = phone_content.findViewById(R.id.login_country_line);
        mVCodeLine = findViewById(R.id.login_code_line);
        //输入框内容-右侧选择国家
        phone_country = phone_content.findViewById(R.id.login_text_country);

        country_content = phone_country.findViewById(R.id.country_content);
        //输入框内容-其他文字，与国家选择、图片只能留一个，默认全是gone
        login_text_text = phone_content.findViewById(R.id.login_text_text);
        //输入框内容-验证码，与国家选择、文字只能选择一个，默认为gone
        login_text_img = phone_content.findViewById(R.id.login_text_image);
    }

    public Builder switchType(int type) {
        switch (type) {
            case NormalType:
                mVCodeLine.setVisibility(GONE);
                return new Builder().setType(GONE, GONE, GONE, GONE);
            case TextType:
                mVCodeLine.setVisibility(GONE);
                return new Builder().setType(GONE, VISIBLE, GONE, VISIBLE);
            case CountryType:
                mVCodeLine.setVisibility(GONE);
                return new Builder().setType(VISIBLE, GONE, GONE, VISIBLE);
            case ImageType:
                mVCodeLine.setVisibility(GONE);
                return new Builder().setType(GONE, GONE, VISIBLE, VISIBLE);
            case AUTO_CODE_RIGHT_TYPE:
                mVCodeLine.setVisibility(VISIBLE);
                return new Builder().setType(GONE, VISIBLE, GONE, GONE);
            default:
                return new Builder().setType(GONE, GONE, GONE, GONE);
        }
    }

    public class Builder {
        /**
         * desc: 设置右侧按键的显示与否状态
         * params:
         */
        public Builder setType(int countryType, int textType, int imageType, int lineType) {
            phone_country.setVisibility(countryType);
            login_text_text.setVisibility(textType);
            login_text_img.setVisibility(imageType);
            country_line.setVisibility(lineType);
            return this;
        }

        @Deprecated
        public Builder setTitle(String leftTitle, String rightTitle) {
            return this;
        }

        public Builder setBoxCountry(String flagName, String nationCode) {
            updateCountry(flagName, nationCode);
            return this;
        }

        public Builder setBoxButton(String content) {
            setContent(login_text_text, content);
            return this;
        }

        public Builder setBoxButtonColor(int color) {
            login_text_text.setTextColor(color);
            return this;
        }

        public Builder setBoxContent(String content) {
            setContent(phone_edit, content);
            return this;
        }

        public Builder setBoxHint(String content) {
            phone_edit.setHint(content);
            phone_edit.setHintTextColor(ContextCompat.getColor(getContext(), R.color.xz_cccccc));
            return this;
        }

        public Builder setBoxHint(final String noFocusContent, final String hasFocusContent) {
            setBoxHint(noFocusContent);
            phone_edit.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        phone_edit.setHint(hasFocusContent);
                    } else {
                        phone_edit.setHint(noFocusContent);
                    }
                }
            });
            phone_edit.setHintTextColor(ContextCompat.getColor(getContext(), R.color.xz_cccccc));
            return this;
        }

        public Builder setBoxInputType(int inputType) {
            phone_edit.setInputType(inputType);
            return this;
        }

        public Builder setBoxInputEnum(String inputEnum) {
            phone_edit.setKeyListener(DigitsKeyListener.getInstance(inputEnum));
            return this;
        }

        public Builder setBoxRawInputType(int type) {
            phone_edit.setRawInputType(type);
            return this;
        }

        /**
         * desc: 禁止选择、复制粘贴输入框内容
         * params:
         */
        public Builder setTextNoSelect() {
            phone_edit.setTextIsSelectable(false);
            phone_edit.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });

            phone_edit.setLongClickable(false);
            return this;
        }

        public String getBoxContent() {
            return phone_edit.getText().toString();
        }

        public TextView getBoxButton() {
            return login_text_text;
        }

        public ImageView getBoxImgButton() {
            return login_text_img;
        }

        public boolean isCountryShow() {
            return phone_country.getVisibility() == View.VISIBLE;
        }

        public void setOnClickListenerWithType(int type, OnClickListener listener) {
            if (type == TextType) {
                login_text_text.setOnClickListener(listener);
            } else if (type == CountryType) {
                phone_country.setOnClickListener(listener);
//                phone_title_right.setOnClickListener(listener);
            } else if (type == ImageType) {
                login_text_img.setOnClickListener(listener);
            }
        }

        public void clearEtFocus() {
            phone_edit.clearFocus();
        }

        public void addTextChangedListener(TextWatcher watcher) {
            phone_edit.addTextChangedListener(watcher);
        }

        public void addFoucusChangleListener(OnFocusChangeListener listener) {
            phone_edit.setOnFocusChangeListener(listener);
        }

        private void setContent(TextView textView, String content) {
            if (content != null) {
                textView.setText(content);
            }
        }

        private void updateCountry(String flagName, String nationCode) {
//            Drawable drawable = ImageTools.loadCountryImg(getContext(), 14, 20, flagName);
//            if (drawable == null) {
//                country_img.setVisibility(View.GONE);
//            } else {
//                country_img.setVisibility(View.VISIBLE);
//                country_img.setImageDrawable(drawable);
//            }
            country_content.setText(String.format("+ %s", nationCode));
        }

    }
}
