package com.elabel.geopagosdemoapp.UI.Payments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.PM.PM;
import com.elabel.geopagosdemoapp.R;
import com.elabel.geopagosdemoapp.UI.Main.ListenerMain;
import com.elabel.geopagosdemoapp.UI.Payments.Adapter.PaymentListenerInterface;
import com.elabel.geopagosdemoapp.UI.Payments.Adapter.PaymentsRVAdapter;
import com.elabel.geopagosdemoapp.Utils.StepsNumbersUtils;
import com.elabel.geopagosdemoapp.VM.PaymentsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements PaymentListenerInterface {
    public final static String AMOUNT = "amount";

    @BindView(R.id.txtCardTitle)
    TextView txtCardTitle;
    @BindView(R.id.cardRecycler)
    RecyclerView recyclerView;
    @BindView(R.id.imgCardThumbs)
    ImageView imgCardThumbs;
    @BindView(R.id.btnCardBack)
    Button btnCardBack;
    @BindView(R.id.btnCardFordward)
    Button btnCardForward;
    @BindView(R.id.btnCardFinish)
    Button btnCardFinish;
    @BindView(R.id.cardPayments)
    CardView cardPayments;

    private ListenerMain listenerMain;
    private PaymentsViewModel vm;
    private PaymentsRVAdapter adapterRV;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listenerMain = (ListenerMain)context;
    }

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this,view);

        vm = ViewModelProviders.of(getActivity()).get(PaymentsViewModel.class);

        Bundle bundle = getArguments();
        if(bundle != null){
            vm.setAmount(bundle.getDouble(AMOUNT));
            vm.setStep(StepsNumbersUtils.INITIAL_STEP);
        }

        initViews();

        //Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapterRV = new PaymentsRVAdapter(getContext(),this, vm.getStep());
        recyclerView.setAdapter(adapterRV);

        //observers
        vm.getPmListLD().observe(this, new Observer<List<PM>>() {
            @Override
            public void onChanged(List<PM> pms) {
                if(vm.getStep().equals(StepsNumbersUtils.INITIAL_STEP)){
                    loadListToAdapter(pms);
                }
            }
        });

        vm.getBanksListLD().observe(this, new Observer<List<Bank>>() {
            @Override
            public void onChanged(List<Bank> banks) {
                if(vm.getStep().equals(2)){
                    loadListToAdapter(banks);
                }
            }
        });

        //btn
        btnCardForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFordward();
            }
        });

        return view;
    }

    private void initViews(){
        txtCardTitle.setText(vm.getCardTitle());
        initBtnBottoms();
        imgCardThumbs.setVisibility(View.GONE);
    }

    private void initBtnBottoms(){
        if (vm.getStep() == StepsNumbersUtils.FINAL_STEP){
            btnCardBack.setVisibility(View.INVISIBLE);
            btnCardForward.setVisibility(View.INVISIBLE);
        }else{
            btnCardFinish.setVisibility(View.INVISIBLE);
        }
    }

    private void loadListToAdapter(List<?>list){
        if(!list.isEmpty()){
            recyclerView.setVisibility(View.VISIBLE);
            adapterRV.setPaymentsList(list);
            adapterRV.notifyDataSetChanged();
        }
    }

    private void loadImg(String url){
        Picasso.get().load(url).into(imgCardThumbs);
        imgCardThumbs.setVisibility(View.VISIBLE);
    }

    private void goFordward(){
        if(vm.goFordward()){
            listenerMain.reloadFragments();
            getActivity().getSupportFragmentManager().beginTransaction().remove(PaymentFragment.this).commit();
        }else{
            Toast.makeText(getContext(), getContext().getText(R.string.fragment_error_fordward), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void paymentsListener(String id, String imgOrTxt) {
        if(vm.getStep().equals(StepsNumbersUtils.INITIAL_STEP)){
            vm.setPmID(id);
            loadImg(imgOrTxt);
        }else if(vm.getStep().equals(StepsNumbersUtils.BANK_STEP)){
            vm.setBankID(id);
            loadImg(imgOrTxt);
        }
    }
}
