package com.elabel.geopagosdemoapp.UI.Payments.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.PM.PM;
import com.elabel.geopagosdemoapp.R;
import com.elabel.geopagosdemoapp.Utils.StepsNumbersUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentsRVAdapter extends RecyclerView.Adapter<AdapterVH> {
    private List<?> paymentsList;
    private Context context;
    private PaymentListenerInterface listener;
    private int step;
    private PM pm;
    private Bank bank;

    public PaymentsRVAdapter(Context context, PaymentListenerInterface listener, int step) {
        this.context = context;
        this.listener = listener;
        this.step = step;
        paymentsList = new ArrayList<>();
    }

    public void setPaymentsList(List<?> paymentsList) {
        this.paymentsList = paymentsList;
    }

    @NonNull
    @Override
    public AdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payments,parent,false);
        return new AdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVH holder, int position) {
        if(step == StepsNumbersUtils.INITIAL_STEP){
            pm = (PM) paymentsList.get(position);
            holder.setData(pm.getName(), pm.getId(), pm.getSecureThumbnail());
        }else if (step == StepsNumbersUtils.BANK_STEP){
            bank = (Bank) paymentsList.get(position);
            holder.setData(bank.getName(), bank.getId(), bank.getSecureThumbnail());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.paymentsListener(holder.getId(), holder.getImgOrTxt());
            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentsList.size();
    }
}
