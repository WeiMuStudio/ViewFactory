package com.luyinbros.viewlib;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAddressView extends ConstraintLayout {
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView fullAddressTextView;
    private View emptyView;
    private ImageView addressIconView;
    private ImageView editArrowIconView;
    private boolean isAllowEdit;

    public OrderAddressView(Context context) {
        super(context);
        init(context);
    }

    public OrderAddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OrderAddressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.widget_order_address, this);
        nameTextView = findViewById(R.id.orderAddressNameTextView);
        phoneTextView = findViewById(R.id.orderAddressPhoneTextView);
        fullAddressTextView = findViewById(R.id.orderAddressFullAddressTextView);
        emptyView = findViewById(R.id.orderAddressEmptyTextView);
        addressIconView = findViewById(R.id.addressIconView);
        editArrowIconView = findViewById(R.id.editArrowIconView);
        setAllowEdit(false);

        emptyInformation();
    }


    public void setInformation(String name, String phone, String fullAddress) {
        nameTextView.setText(name);
        phoneTextView.setText(phone);
        fullAddressTextView.setText(fullAddress);
        emptyView.setVisibility(GONE);
        nameTextView.setVisibility(VISIBLE);
        phoneTextView.setVisibility(VISIBLE);
        addressIconView.setVisibility(VISIBLE);
        fullAddressTextView.setVisibility(VISIBLE);
        setAllowEditVisibility(true);
    }

    public void setAllowEdit(boolean allowEdit) {
        isAllowEdit = allowEdit;
    }

    private void setAllowEditVisibility(boolean visibility) {
        if (isAllowEdit && nameTextView.getVisibility() == VISIBLE) {
            editArrowIconView.setVisibility(VISIBLE);
        } else {
            editArrowIconView.setVisibility(GONE);
        }
    }

    public void emptyInformation() {
        emptyView.setVisibility(VISIBLE);
        nameTextView.setVisibility(GONE);
        phoneTextView.setVisibility(GONE);
        addressIconView.setVisibility(GONE);
        fullAddressTextView.setVisibility(GONE);
        setAllowEditVisibility(false);
    }

}
