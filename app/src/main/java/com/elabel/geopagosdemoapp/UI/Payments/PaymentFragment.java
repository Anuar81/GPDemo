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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.Installments.PayerCost;
import com.elabel.geopagosdemoapp.Model.PM.PM;
import com.elabel.geopagosdemoapp.Model.Preference.Preference;
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
    @BindView(R.id.cardTxtInstallments)
    TextView txtInstallments;
    @BindView(R.id.cardLayoutRv)
    LinearLayout cardLayoutRv;
    @BindView(R.id.cardLayoutFinalStep)
    LinearLayout cardLayoutFinalStep;
    @BindView(R.id.cardTxtLFSTotalAmountNumber)
    TextView cardTxtLFSTotalAmount;
    @BindView(R.id.cardTxtLFSPMLegend)
    TextView cardTxtLFSPMLegends;
    @BindView(R.id.cardTxtLFSBankLegend)
    TextView cardTxtLFSBankLegend;
    @BindView(R.id.cardTxtLFSInstallmentsLegend)
    TextView cardTxtLFSInstallmentsLegend;

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
            vm.setInstallmentsTotal("");
            vm.setInstallmentMessage("");
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
                if(vm.getStep().equals(StepsNumbersUtils.BANK_STEP)){
                    loadListToAdapter(banks);
                }
            }
        });

        vm.getPcListLD().observe(this, new Observer<List<PayerCost>>() {
            @Override
            public void onChanged(List<PayerCost> payerCosts) {
                if(vm.getStep().equals(StepsNumbersUtils.INSTALLSMENTS_STEP)){
                    loadListToAdapter(payerCosts);
                }
            }
        });

        vm.getPreferenceIDLD().observe(this, new Observer<Preference>() {
            @Override
            public void onChanged(Preference preference) {
                listenerMain.initPay(preference);
            }
        });

        //btn
        btnCardForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFordward();
            }
        });

        btnCardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        btnCardFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.getPreference();
                btnCardFinish.setEnabled(false);
            }
        });

        return view;
    }

    private void initViews(){
        initLayoutAndBtnBottoms();
        txtCardTitle.setText(vm.getCardTitle());
        loadImgOrTxt(vm.getUrlOrTxt());
    }

    private void initLayoutAndBtnBottoms(){
        if (vm.getStep().equals(StepsNumbersUtils.FINAL_STEP)){
            cardLayoutFinalStep.setVisibility(View.VISIBLE);
            btnCardForward.setVisibility(View.INVISIBLE);
            setTxtToFinalStep();
        }else{
            cardLayoutRv.setVisibility(View.VISIBLE);
            btnCardFinish.setVisibility(View.INVISIBLE);
        }
    }

    private void setTxtToFinalStep(){
        cardTxtLFSTotalAmount.setText("$ " + vm.getAmount().toString());
        cardTxtLFSPMLegends.setText(vm.getPmLegend());
        cardTxtLFSBankLegend.setText(vm.getBankLegend());
        cardTxtLFSInstallmentsLegend.setText(vm.getInstallmentMessage());
    }

    private void loadListToAdapter(List<?>list){
        if(!list.isEmpty()){
            recyclerView.setVisibility(View.VISIBLE);
            adapterRV.setPaymentsList(list);
            adapterRV.notifyDataSetChanged();
        }
    }

    private void loadImgOrTxt(String urlOrTxt){
        if(vm.getStep().equals(StepsNumbersUtils.INSTALLSMENTS_STEP)){
            txtInstallments.setText(urlOrTxt);
        }else if(!vm.getStep().equals(StepsNumbersUtils.FINAL_STEP) && !urlOrTxt.isEmpty()){
            Picasso.get().load(urlOrTxt).into(imgCardThumbs);
            imgCardThumbs.setVisibility(View.VISIBLE);
        }
    }

    private void goBack(){
        if(vm.goBack()){
            listenerMain.reloadFragments();
            getActivity().getSupportFragmentManager().beginTransaction().remove(PaymentFragment.this).commit();
        }else{
            getActivity().getSupportFragmentManager().beginTransaction().remove(PaymentFragment.this).commit();
        }

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
    public void paymentsListener(String leyenda, String id, String imgOrTxt) {
        vm.saveDatainVM(leyenda,id,imgOrTxt);
        loadImgOrTxt(imgOrTxt);
    }

}
