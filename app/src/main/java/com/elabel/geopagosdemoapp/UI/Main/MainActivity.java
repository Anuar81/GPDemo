package com.elabel.geopagosdemoapp.UI.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elabel.geopagosdemoapp.R;
import com.elabel.geopagosdemoapp.UI.Payments.PaymentFragment;
import com.elabel.geopagosdemoapp.VM.MainViewModel;

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
}
