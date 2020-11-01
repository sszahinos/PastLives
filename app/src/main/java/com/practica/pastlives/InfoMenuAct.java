//https://programacionymas.com/blog/como-pedir-fecha-android-usando-date-picker

package com.practica.pastlives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class InfoMenuAct extends AppCompatActivity implements View.OnClickListener {
    private int counter = 0;
    private TextView tvMessage;
    private EditText etTextRW, etTextR;
    private ProgressBar pb;
    private Button bContinue, bBack;
    private Spinner spinner;
    private ImageView iv1, iv2, iv3, iv4;
    private RadioButton bR1, bR2, bR3;

    String name, animal, place, gift;
    QuestionState questionState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //onRestoreInstanceState();
        setContentView(R.layout.activity_info_menu);

        questionState = QuestionState.EMPTY;
        setAskName();

        bContinue = findViewById(R.id.bContinue);
        bBack = findViewById(R.id.bBack);
        pb = findViewById(R.id.progressBar);
        tvMessage = findViewById(R.id.tvMessage);
        etTextRW = findViewById(R.id.etTextRW);
        etTextR = findViewById(R.id.etTextR);
        spinner = findViewById(R.id.spinner);
        iv1 = findViewById(R.id.ivPlace1);
        iv2 = findViewById(R.id.ivPlace2);
        iv3 = findViewById(R.id.ivPlace3);
        iv4 = findViewById(R.id.ivPlace4);
        bR1 = findViewById(R.id.bRadio1);
        bR2 = findViewById(R.id.bRadio2);
        bR3 = findViewById(R.id.bRadio3);

        bContinue.setOnClickListener(this);
        bBack.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);

        while (true) {
            ask();
        }



    }

    /**
     * Question state selector
     */
    private void ask() {
        switch (questionState) {
            case EMPTY:
                setAskName();
                name = askName();
                questionState = QuestionState.NAME;
                break;
            case NAME:
                //TODO Calendario
                setAskDate();
                questionState = QuestionState.DATE;
                break;
            case DATE:
                setAskAnimal();
                //animal = askAnimal();
                questionState = QuestionState.ANIMAL;
                break;
            case ANIMAL:
                setAskPlace();
                //place = askPlace();
                questionState = QuestionState.PLACE;
                break;
            case PLACE:
                setAskGift();
                //gift = askGift();
                questionState = QuestionState.GIFT;
                break;
            case GIFT:
                //PastLife pl = new PastLife(name, date, animal, place, gift);
                Intent i = new Intent(this, ResultAct.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    /*
     * setAskX() functions controls the appearance of the view.
     */
    private void setAskName() {
        questionState = QuestionState.NAME;
        pb.setProgress(16);
        tvMessage.setText(R.string.askName);
        etTextRW.setVisibility(View.VISIBLE);
    }

    private void setAskDate() {
        questionState = QuestionState.DATE;
        pb.setProgress(32);
        tvMessage.setText(R.string.askDate);
        etTextRW.setVisibility(View.GONE);
        etTextR.setVisibility(View.VISIBLE);
    }

    private void setAskAnimal() {
        questionState = QuestionState.ANIMAL;
        pb.setProgress(48);
        tvMessage.setText(R.string.askAnimal);
        etTextR.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
    }

    private void setAskPlace() {
        questionState = QuestionState.PLACE;
        pb.setProgress(64);
        tvMessage.setText(R.string.askPlace);
        spinner.setVisibility(View.GONE);
        iv1.setVisibility(View.VISIBLE);
        iv2.setVisibility(View.VISIBLE);
        iv3.setVisibility(View.VISIBLE);
        iv4.setVisibility(View.VISIBLE);
    }

    private void setAskGift() {
        questionState = QuestionState.GIFT;
        pb.setProgress(80);
        tvMessage.setText(R.string.askGift);
        iv1.setVisibility(View.GONE);
        iv2.setVisibility(View.GONE);
        iv3.setVisibility(View.GONE);
        iv4.setVisibility(View.GONE);
        bR1.setVisibility(View.VISIBLE);
        bR2.setVisibility(View.VISIBLE);
        bR3.setVisibility(View.VISIBLE);
    }


    private String askName() {
        //R.id.etTextRW
        return "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bBack:
                switch (questionState) {
                    case EMPTY:
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                        break;
                    case NAME:
                        questionState = QuestionState.EMPTY;
                        break;
                    case DATE:
                        questionState = QuestionState.NAME;
                        break;
                    case ANIMAL:
                        questionState = QuestionState.DATE;
                        break;
                    case PLACE:
                        questionState = QuestionState.ANIMAL;
                        break;
                    case GIFT:
                        questionState = QuestionState.PLACE;
                        break;
                    default:
                        break;
                }
                break;
            case R.id.bContinue:

                break;
        }
    }

    private enum QuestionState {
        EMPTY, NAME, DATE, ANIMAL, PLACE, GIFT
    }
}