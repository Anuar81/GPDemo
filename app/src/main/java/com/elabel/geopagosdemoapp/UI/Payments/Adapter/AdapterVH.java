package com.elabel.geopagosdemoapp.UI.Payments.Adapter;

import android.view.View;
import android.widget.TextView;

import com.elabel.geopagosdemoapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterVH extends RecyclerView.ViewHolder {
    @BindView(R.id.txtItemPayment)
    TextView txtItemPayment;

    private String legend;
    private String id;
    private String imgOrTxt;

    public AdapterVH(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setData(String legend, String id, String imgOrTxt){
        txtItemPayment.setText(legend);
        this.legend = legend;
        this.id = id;
        this.imgOrTxt = imgOrTxt;
    }

    public String getLegend() {
        return legend;
    }

    public String getId() {
        return id;
    }

    public String getImgOrTxt() {
        return imgOrTxt;
    }
}
