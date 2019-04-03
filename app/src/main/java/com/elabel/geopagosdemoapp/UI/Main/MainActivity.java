package com.elabel.geopagosdemoapp.UI.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.elabel.geopagosdemoapp.Data.Api.ApiConstants;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.core.MercadoPagoCheckout.Builder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elabel.geopagosdemoapp.Model.Preference.Preference;
import com.elabel.geopagosdemoapp.R;
import com.elabel.geopagosdemoapp.UI.Payments.PaymentFragment;
import com.elabel.geopagosdemoapp.VM.MainViewModel;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;

public class MainActivity extends AppCompatActivity implements ListenerMain{
    @BindView(R.id.txtTotalAmount)
    TextView txtTotalAmount;
    @BindView(R.id.edtAdd)
    EditText edtAdd;
    @BindView(R.id.btnPay)
    Button btnPay;
    @BindView(R.id.btnAdd)
    Button btnAdd;

    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        vm = ViewModelProviders.of(this).get(MainViewModel.class);
        showData();

        //btns
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edtAdd.getText())){
                    vm.addAmount(edtAdd.getText().toString());
                    showData();
                    clearEdtAddText();
                }
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putDouble(PaymentFragment.AMOUNT,Double.valueOf(txtTotalAmount.getText().toString()));
                launchFrag(bundle);
            }
        });

    }

    private void showData(){
        if(vm.amountEnable()){
            txtTotalAmount.setText(vm.getAmount());
            btnPay.setVisibility(View.VISIBLE);
        }
    }

    private void clearEdtAddText(){
        edtAdd.setText("");
        edtAdd.clearFocus();
    }

    private void launchFrag(Bundle bundle){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = new PaymentFragment();
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        ft.add(R.id.frameContainer,fragment,null);
        ft.commit();
    }

    @Override
    public void reloadFragments() {
        launchFrag(null);
    }

    @Override
    public void initPay(Preference preference) {
        new MercadoPagoCheckout.Builder(ApiConstants.API_KEY, preference.getId())
                .build()
                .startPayment(this, -1);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == -1) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                final Payment payment = (Payment) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT);
                Toast.makeText(this, "Resultado del pago: " + payment.getPaymentStatus(), Toast.LENGTH_SHORT).show();
                //((TextView) findViewById(R.id.mp_results)).setText("Resultado del pago: " + payment.getStatus());
                //Done!
            } else if (resultCode == RESULT_CANCELED) {
                if (data != null && data.getExtras() != null
                        && data.getExtras().containsKey(MercadoPagoCheckout.EXTRA_ERROR)) {
                    final MercadoPagoError mercadoPagoError =
                            (MercadoPagoError) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR);
                    Toast.makeText(this, "Error: " +  mercadoPagoError.getMessage(), Toast.LENGTH_SHORT).show();
                    //((TextView) findViewById(R.id.mp_results)).setText("Error: " +  mercadoPagoError.getMessage());
                    //Resolve error in checkout
                } else {
                    //Resolve canceled checkout
                }
            }
        }
    }
}
