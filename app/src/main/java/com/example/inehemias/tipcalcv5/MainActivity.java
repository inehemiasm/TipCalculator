package com.example.inehemias.tipcalcv5;

        import android.icu.text.DecimalFormat;
        import android.os.Build;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.TextView;

        import android.text.Editable;
        import android.text.TextWatcher;
        import android.widget.EditText;
        import android.widget.SeekBar;

        import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextBillAmount;
    private TextView textView_BillAmount;
    private TextView percentage;
    private TextView display_tip;
    private TextView display_total_bill;
    private TextView tip_label;
    private TextView messageToUser;

    private double amount = 0.0;
    private double percent = 0.0;
    private double tip = 0;
    private double total = 0;
    String message = " ";



    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializers();
        setters();



    }
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //surround risky calculations with try catch (what if billAmount is 0 ?
            //charSequence is converted to a String and parsed to a double for you

            amount = Double.parseDouble(charSequence.toString()) / 100;
            //setText on the textView
            //perform tip and total calculation and update UI by calling calculate
            calculate();//uncomment this line
        }



        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    // calculate and display tip and total amounts
    private void calculate() {
        // format percent and display in percentTextView
        percentage.setText(percentFormat.format(percent));

        // calculate the tip and total
        tip = amount * percent;
        //use the tip example to do the same for the Total

        // display tip and total formatted as currency
        //user currencyFormat instead of percentFormat to set the textViewTip
        display_tip.setText(currencyFormat.format(tip));
        //use the tip example to do the same for the Total

        //computes the total by adding the amount spent plus tip
        total= amount + tip;

        //sets the display to display the total
        display_total_bill.setText(currencyFormat.format(total));

        //display formatted bill ammount
        textView_BillAmount.setText(currencyFormat.format(amount));

        messageToUser.setText(addCoolStuff(percent));


    }
    private void initializers(){

        textView_BillAmount = (TextView) findViewById(R.id.textView_BillAmount);
        percentage = (TextView) findViewById(R.id.percentage);
        display_tip = (TextView) findViewById(R.id.display_tip);
        display_total_bill = (TextView) findViewById(R.id.display_total_bill);
        tip_label= (TextView) findViewById(R.id.tip_label);
        messageToUser=(TextView)findViewById(R.id.MessageToUser);
    }


    private void setters(){

        final EditText editTextBillAmount = (EditText) findViewById(R.id.editText_BillAmount);
        editTextBillAmount.addTextChangedListener(amountEditTextWatcher);
        percentage.setText(percentFormat.format(percent));
        display_tip.setText(currencyFormat.format(tip));
        tip_label.setText("Tip");
        messageToUser.setText(addCoolStuff(percent));


        SeekBar seekBarPercent = (SeekBar) findViewById(R.id.seekBar);
        seekBarPercent.setOnSeekBarChangeListener(seekbarListener);

    }


    //Adding a cool message to the user depending on their tip percentage.
    private String addCoolStuff(double val){

        int unicode = 0x1F60A;

        int monkey_no_see = 0x1F648;


        val=val*100;
        int locAmount = (int) amount;
        if ( locAmount==0){

            message = "How cool are you? " + getEmojiByUnicode(unicode);
        }

        else if (val<=10){
            message= "Waiter wont serve you again!!";
        }
        else if (val>10 && val<18) {
            message = " Waiter will give you poor service!!";
        }
        else if (val == 18.00){

            message = "Perfect Tipper!!  " + getEmojiByUnicode(unicode);
        }

        else if(val>18.00 && val<50.00){

            message= "Be Careful, Waiters will run to serve you!  " + getEmojiByUnicode(unicode);
        }

        else if (val > 50.00){

            message= "Want to be my Boss?  " + getEmojiByUnicode(unicode);
        }
        else {message= "How Cool are you? ";}

        return message;

    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }




    private final SeekBar.OnSeekBarChangeListener seekbarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0;
            calculate();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}


