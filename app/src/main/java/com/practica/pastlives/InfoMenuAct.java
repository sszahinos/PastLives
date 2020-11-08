package com.practica.pastlives;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class InfoMenuAct extends AppCompatActivity implements
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        Serializable{

    private TextView tvMessage;
    private EditText etTextRW, etTextR;
    private ProgressBar pb;
    private Button bContinue, bBack;
    private Spinner spinner;
    private ImageButton ib1, ib2, ib3, ib4;
    private RadioGroup bRG;
    private RadioButton bRadio;
    private ImageView ivSeer;
    private TextView tvThinking;
    private PastLife pl;

    private int length;
    private String name = "";
    private String animal;
    private Date date = null;
    private String place;
    private String gift = "";
    private QuestionState questionState;
    private MediaPlayer mediaPlayer;
    private boolean sound;
    private int animalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_menu);


        questionState = QuestionState.NAME;
        Intent i = getIntent();
        length = (int) i.getSerializableExtra("length");
        sound = (boolean) i.getSerializableExtra("sound");
        if (sound) {
            mediaPlayer = MediaPlayer.create(this, R.raw.toocrazy);
            mediaPlayer.setLooping(true);
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }

        setViews();

        setAskName();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //Back button
            case R.id.bBack:
                switch (questionState) {
                    case NAME:
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                        break;
                    case DATE:
                        questionState = QuestionState.NAME;
                        setAskName();
                        break;
                    case ANIMAL:
                        questionState = QuestionState.DATE;
                        setAskDate();
                        break;
                    case PLACE:
                        questionState = QuestionState.ANIMAL;
                        setAskAnimal();
                        break;
                    case GIFT:
                        setAskPlace();
                        questionState = QuestionState.PLACE;
                        break;
                    default:
                        break;
                }
                break;

            //Continue button
            case R.id.bContinue:
                switch (questionState) {
                    case NAME:
                        name = etTextRW.getText().toString();

                        if (!name.equals("")) {
                            setAskDate();
                            questionState = QuestionState.DATE;
                        } else {
                            showToast(R.string.notAnswerMessage);
                        }

                        break;

                    case DATE:
                        if (date != null) {
                            setAskAnimal();
                            questionState = QuestionState.ANIMAL;
                        } else {
                            showToast(R.string.notAnswerMessage);
                        }
                        break;
                    case ANIMAL:
                        spinnerSelector();
                        setAskPlace();
                        questionState = QuestionState.PLACE;
                        break;
                    case PLACE:
                        break;
                    case GIFT:

                        int giftID = bRG.getCheckedRadioButtonId();
                        bRadio = findViewById(giftID);

                        if (bRadio.getText().equals("")) {
                            showToast(R.string.notAnswerMessage);
                            break;
                        }

                        //gift = (String) bRadio.getText();

                        setDone();
                        seerAnimation();

                        pb.setVisibility(View.GONE);
                        pl = new PastLife(name, date, animalID, place, giftID);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                endingAnimation();
                            }
                        }, 5000);

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                changeResultAct(pl);
                            }
                        }, 8000);

                        break;
                    default:
                        break;
                }
                break;
            //Other buttons
            case R.id.ibPlace1:
                place = "MOUNTAIN";
                setAskGift();
                questionState = QuestionState.GIFT;
                break;
            case R.id.ibPlace2:
                place = "RAINFOREST";
                setAskGift();
                questionState = QuestionState.GIFT;
                break;
            case R.id.ibPlace3:
                place = "BEACH";
                setAskGift();
                questionState = QuestionState.GIFT;
                break;
            case R.id.ibPlace4:
                place = "DESERT";
                setAskGift();
                questionState = QuestionState.GIFT;
                break;

            case R.id.etTextR:
                showDatePickerDialog();
                break;
            default:
                break;
        }
    }

    private void showToast(int message) {
        Toast toast = Toast.makeText(this, message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    private void spinnerSelector() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        animalID = spinner.getSelectedItemPosition();
        //animal = spinner.getSelectedItem().toString();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        etTextR.setText(String.format("%d/%d/%d", dayOfMonth, (month + 1), year));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        date = calendar.getTime();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show();
    }

    @Override
    public void onBackPressed() {
        showToast(R.string.backMessage);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void seerAnimation() {
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        ivSeer.startAnimation(animFadeIn);
        tvThinking.startAnimation(animFadeIn);
        pb.startAnimation(animFadeOut);
    }

    private void endingAnimation() {
        Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);

        ivSeer.startAnimation(animFadeOut);
        tvThinking.startAnimation(animFadeOut);
    }


    private void changeResultAct(PastLife pl) {
        Intent i = new Intent(this, ResultAct.class);
        i.putExtra("result", pl);
        i.putExtra("sound", sound);
        if (sound) {
            length = mediaPlayer.getCurrentPosition();
            i.putExtra("length", length);
        }
        startActivity(i);
    }

    private void setViews() {
        bContinue = findViewById(R.id.bContinue);
        bBack = findViewById(R.id.bBack);
        pb = findViewById(R.id.progressBar);
        tvMessage = findViewById(R.id.tvMessage);
        etTextRW = findViewById(R.id.etTextRW);
        etTextR = findViewById(R.id.etTextR);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.animals, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ib1 = findViewById(R.id.ibPlace1);
        ib2 = findViewById(R.id.ibPlace2);
        ib3 = findViewById(R.id.ibPlace3);
        ib4 = findViewById(R.id.ibPlace4);

        bRG = findViewById(R.id.bRadioGroup);

        bContinue.setOnClickListener(this);
        bBack.setOnClickListener(this);
        ib1.setOnClickListener(this);
        ib2.setOnClickListener(this);
        ib3.setOnClickListener(this);
        ib4.setOnClickListener(this);
        etTextR.setOnClickListener(this);

        ivSeer = findViewById(R.id.ivSeer);
        tvThinking = findViewById(R.id.tvThinking);
    }

    /*
     * setAskX() functions controls the appearance of the view.
     */
    private void setAskName() {
        questionState = QuestionState.NAME;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pb.setProgress(16, true);
        }
        tvMessage.setText(R.string.askName);
        etTextRW.setVisibility(View.VISIBLE);

        etTextR.setVisibility(View.GONE);
    }

    private void setAskDate() {
        questionState = QuestionState.DATE;
        pb.setProgress(32);
        tvMessage.setText(R.string.askDate);
        etTextRW.setVisibility(View.GONE);
        etTextR.setVisibility(View.VISIBLE);

        spinner.setVisibility(View.GONE);
    }

    private void setAskAnimal() {
        questionState = QuestionState.ANIMAL;
        pb.setProgress(48);
        tvMessage.setText(R.string.askAnimal);
        etTextR.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        bContinue.setVisibility(View.VISIBLE);
        ib1.setVisibility(View.GONE);
        ib2.setVisibility(View.GONE);
        ib3.setVisibility(View.GONE);
        ib4.setVisibility(View.GONE);
    }

    private void setAskPlace() {
        questionState = QuestionState.PLACE;
        pb.setProgress(64);
        tvMessage.setText(R.string.askPlace);
        spinner.setVisibility(View.GONE);
        ib1.setVisibility(View.VISIBLE);
        ib2.setVisibility(View.VISIBLE);
        ib3.setVisibility(View.VISIBLE);
        ib4.setVisibility(View.VISIBLE);
        bContinue.setVisibility(View.GONE);
        bRG.setVisibility(View.GONE);
    }

    private void setAskGift() {
        questionState = QuestionState.GIFT;
        pb.setProgress(80);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText(R.string.askGift);
        ib1.setVisibility(View.GONE);
        ib2.setVisibility(View.GONE);
        ib3.setVisibility(View.GONE);
        ib4.setVisibility(View.GONE);
        bRG.setVisibility(View.VISIBLE);
        bBack.setVisibility(View.VISIBLE);
        bContinue.setVisibility(View.VISIBLE);
        ivSeer.setVisibility(View.GONE);
        tvThinking.setVisibility(View.GONE);

    }

    private void setDone() {
        pb.setProgress(100);
        tvMessage.setVisibility(View.GONE);
        bRG.setVisibility(View.GONE);
        bBack.setVisibility(View.GONE);
        bContinue.setVisibility(View.GONE);
        tvThinking.setVisibility(View.VISIBLE);
        ivSeer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        if (sound) {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (sound) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (sound) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}