package com.cscodetech.townclap.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.PaymentItem;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.cscodetech.townclap.utils.Utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;


public class PaystackActivity extends AppCompatActivity {

    private static final int CARD_NUMBER_TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int CARD_NUMBER_TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int CARD_NUMBER_DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char CARD_NUMBER_DIVIDER = '-';

    private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
    private static final char CARD_DATE_DIVIDER = '/';

    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;

    @BindView(R.id.cardNumberEditText)
    EditText cardNumberEditText;
    @BindView(R.id.cardDateEditText)
    EditText cardDateEditText;
    @BindView(R.id.cardCVCEditText)
    EditText cardCVCEditText;
    @BindView(R.id.txt_submit)
    TextView txtSubmit;
    @BindView(R.id.img_back)
    ImageView imgBack;


    SessionManager sessionManager;
    int amount = 0;
    User user;
    PaymentItem paymentItem;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paystack);
        ButterKnife.bind(this);

        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails("");
        amount = getIntent().getIntExtra("amount", 0);
        paymentItem = (PaymentItem) getIntent().getSerializableExtra("detail");
        List<String> elephantList = Arrays.asList(paymentItem.getmAttributes().split(","));
        Log.e("key", "--> " + elephantList.get(0));
        PaystackSdk.setPublicKey(elephantList.get(0));
        amount = amount * 100;
        imgBack.setOnClickListener(view -> finish());
    }

    public void performCharge(Card card) {
        custPrograssbar.prograssCreate(PaystackActivity.this);
        //create a Charge object
        Charge charge = new Charge();
        charge.setCard(card); //sets the card to charge
        charge.setEmail("");
        charge.setAmount(amount);
        PaystackSdk.chargeCard(PaystackActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.
                Log.e("Paystack response", "" + transaction.getReference());
                new VerifyOnServer().execute(transaction.getReference());


            }

            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.
                Log.e("Paystack Opt", "" + transaction.toString());
                custPrograssbar.closePrograssBar();
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
                custPrograssbar.closePrograssBar();

                Log.e("Paystack Error", "" + transaction.toString());
                Toast.makeText(PaystackActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                Log.e("Paystack Error1", "" + error.toString());
            }

        });
    }

    @OnClick(R.id.txt_submit)
    public void onClick() {
        Log.e("size", "-->" + cardDateEditText.getText().toString().length());
        if (cardDateEditText.getText().toString().length() == 5) {
            int mounth = Integer.parseInt(cardDateEditText.getText().toString().substring(0, 2));
            int year = Integer.parseInt(cardDateEditText.getText().toString().substring(3, 5));
            Card card = new Card(cardNumberEditText.getText().toString(), mounth, year, cardCVCEditText.getText().toString());
            if (card.isValid()) {
                // charge card
                performCharge(card);
            } else {
                Toast.makeText(PaystackActivity.this, "Please check your card details and try again", Toast.LENGTH_LONG).show();
                //do something
            }
        } else {
            Toast.makeText(PaystackActivity.this, "Please check your card details and try again", Toast.LENGTH_LONG).show();

        }

    }


    private class VerifyOnServer extends AsyncTask<String, Void, String> {
        private String reference;
        private String error;

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            custPrograssbar.closePrograssBar();
            if (result != null) {
                Log.e("result", "-->" + result);
                if (result.equalsIgnoreCase("Transaction was successful")) {
                    Utility.tragectionID = reference;
                    Utility.paymentsucsses = 1;
                    finish();
                } else {
                    Toast.makeText(PaystackActivity.this, "" + result, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(PaystackActivity.this, String.format("There was a problem verifying %s on the backend: %s ", this.reference, error), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... reference) {
            try {
                this.reference = reference[0];
                String json = String.format("{\"reference\":\"%s\"}", this.reference);
                String url1 = APIClient.baseUrl + "/eapi/e_verify.php?details=" + URLEncoder.encode(json, "UTF-8");

                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    InputStreamReader read = new InputStreamReader(in);
                    BufferedReader buff = new BufferedReader(read);
                    StringBuilder dta = new StringBuilder();
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        dta.append(chunks);
                    }

                    return dta.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                custPrograssbar.closePrograssBar();
                e.printStackTrace();
            }
            return null;
        }
    }


    @OnTextChanged(value = R.id.cardNumberEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void onCardNumberTextChanged(Editable s) {
        if (!isInputCorrect(s, CARD_NUMBER_TOTAL_SYMBOLS, CARD_NUMBER_DIVIDER_MODULO, CARD_NUMBER_DIVIDER)) {
            s.replace(0, s.length(), concatString(getDigitArray(s, CARD_NUMBER_TOTAL_DIGITS), CARD_NUMBER_DIVIDER_POSITION, CARD_NUMBER_DIVIDER));
        }
    }

    @OnTextChanged(value = R.id.cardDateEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void onCardDateTextChanged(Editable s) {
        if (!isInputCorrect(s, CARD_DATE_TOTAL_SYMBOLS, CARD_DATE_DIVIDER_MODULO, CARD_DATE_DIVIDER)) {
            s.replace(0, s.length(), concatString(getDigitArray(s, CARD_DATE_TOTAL_DIGITS), CARD_DATE_DIVIDER_POSITION, CARD_DATE_DIVIDER));
        }
    }

    @OnTextChanged(value = R.id.cardCVCEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void onCardCVCTextChanged(Editable s) {
        if (s.length() > CARD_CVC_TOTAL_SYMBOLS) {
            s.delete(CARD_CVC_TOTAL_SYMBOLS, s.length());
        }
    }

    private boolean isInputCorrect(Editable s, int size, int dividerPosition, char divider) {
        boolean isCorrect = s.length() <= size;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (i + 1) % dividerPosition == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    private String concatString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }
}
