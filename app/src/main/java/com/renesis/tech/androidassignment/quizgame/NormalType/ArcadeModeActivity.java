package com.renesis.tech.androidassignment.quizgame.NormalType;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.renesis.tech.androidassignment.quizgame.GameOverActivity;
import com.renesis.tech.androidassignment.quizgame.Pojo.QuestionsModel;
import com.renesis.tech.androidassignment.quizgame.R;
import com.renesis.tech.androidassignment.quizgame.ScoreBoardActivity;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;
import com.renesis.tech.androidassignment.quizgame.Utils.History;
import com.renesis.tech.androidassignment.quizgame.Utils.ProgressBarAnimation;
import com.renesis.tech.androidassignment.quizgame.Utils.Server.MySingleton;
import com.renesis.tech.androidassignment.quizgame.Utils.Utilities;
import com.renesis.tech.androidassignment.quizgame.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class ArcadeModeActivity extends AppCompatActivity {
    LinearLayout answer_msg, question_a, question_b, question_c, question_d, timer_main;
    CircleImageView score_board_btn;
    TextView question_text, option_a_text, question_b_text, question_c_text, question_d_text, answer_msg_text, current_money, timer_text;
    ImageView backbtn;
    ArrayList<QuestionsModel> questionsModelArrayList;
    Animation animation_out, animation_in, animation_out_lifeline, animation_in_lifeline;
    int question_counter = 0;
    int currentMoney = 0;
    String QuestionType, game_type;
    String genius_ans;
    LinearLayout main_screen;
    ProgressBarAnimation animation_progress_bar;

    boolean check_fifty = false;
    CountDownTimer timer_counter = null;


    ProgressBar progress_bar;


    int question_difficulty = 0;
    int question_stage = 5;
    boolean singledialog = false;
    Dialog dialogexit;
    int total_question_asked = 0, total_correct_ans = 0;

    boolean check_bonus_chance = false;

    Date timestart;


    BackgroundMusic backgroundMusic;


    @Override
    protected void onPause() {
        backgroundMusic.pausemusic();
        super.onPause();
    }

    @Override
    protected void onResume() {
        backgroundMusic.playmusic();
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade_mode);
        Paper.init(this);



        total_question_asked = Integer.parseInt(Paper.book().read("total_question_asked", "0"));
        total_correct_ans = Integer.parseInt(Paper.book().read("total_correct_ans", "0"));

        init();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                QuestionType = null;
            } else {
                QuestionType = extras.getString("question_type");
                game_type = extras.getString("game_type");
            }
        } else {
            QuestionType = (String) savedInstanceState.getSerializable("question_type");
            game_type = (String) savedInstanceState.getSerializable("game_type");
        }


//          Arcade game setup
        if (QuestionType.equalsIgnoreCase("boolean")) {
            question_c.setVisibility(View.GONE);
            question_d.setVisibility(View.GONE);
            getquestions("easy", "boolean");
            question_difficulty++;

        } else {
            getquestions("easy", "multiple");
            question_c.setVisibility(View.VISIBLE);
            question_d.setVisibility(View.VISIBLE);
            question_difficulty++;
        }

//                                              set top currency
        current_money.setText("$ " + 0);


//                                              LIFE LINES
        animation_out_lifeline = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_out);
        animation_in_lifeline = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_in_lifeline);







        animation_out = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_out);


        animation_in = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_in);


        animation_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                question_a.setVisibility(View.VISIBLE);
                question_b.setVisibility(View.VISIBLE);
                question_c.setVisibility(View.VISIBLE);
                question_d.setVisibility(View.VISIBLE);

                Integer[] arr;
                if (QuestionType.equalsIgnoreCase("boolean")) {
                    arr = new Integer[1];
                    arr = Utilities.randonnum(2);
                } else {
                    arr = new Integer[3];
                    arr = Utilities.randonnum(4);
                }
                total_question_asked++;
                if (question_counter < question_stage) {
                    question_text.setText(questionsModelArrayList.get(question_counter).getQuestion());
                    option_a_text.setText(questionsModelArrayList.get(question_counter).getIncorrectans().get(Arrays.asList(arr).indexOf(0)));
                    question_b_text.setText(questionsModelArrayList.get(question_counter).getIncorrectans().get(Arrays.asList(arr).indexOf(1)));

                    if (QuestionType.equalsIgnoreCase("multiple")) {

                        question_c_text.setText(questionsModelArrayList.get(question_counter).getIncorrectans().get(Arrays.asList(arr).indexOf(2)));
                        question_d_text.setText(questionsModelArrayList.get(question_counter).getIncorrectans().get(Arrays.asList(arr).indexOf(3)));
                    } else {
                        option_a_text.setText("True");
                        question_b_text.setText("False");

                    }

                    question_a.setBackgroundResource(R.drawable.rounded_border_blue);
                    question_b.setBackgroundResource(R.drawable.rounded_border_blue);
                    question_c.setBackgroundResource(R.drawable.rounded_border_blue);
                    question_d.setBackgroundResource(R.drawable.rounded_border_blue);
                    option_a_text.startAnimation(animation_in);
                    question_b_text.startAnimation(animation_in);
                    question_c_text.startAnimation(animation_in);
                    question_d_text.startAnimation(animation_in);
                    question_text.startAnimation(animation_in);


                    if (check_fifty) {
                        question_a.setAnimation(animation_in);
                        question_b.setAnimation(animation_in);
                        question_c.setAnimation(animation_in);
                        question_d.setAnimation(animation_in);
                        check_fifty = false;
                    } else {
                        question_a.setAnimation(null);
                        question_b.setAnimation(null);
                        question_c.setAnimation(null);
                        question_d.setAnimation(null);
                    }

                    if (QuestionType.equalsIgnoreCase("boolean")) {
                        question_c.setVisibility(View.GONE);
                        question_d.setVisibility(View.GONE);
                    }

                } else {

                    if (question_difficulty == 3) {
                        backgroundMusic.Stopmusic_background();

                        Intent intent = new Intent(ArcadeModeActivity.this, GameWinActivity.class);
                        intent.putExtra("current_money", String.valueOf(currentMoney));
                        intent.putExtra("question_type", QuestionType);
                        intent.putExtra("game_type", game_type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();


                        Toast.makeText(ArcadeModeActivity.this, "Game completed", Toast.LENGTH_SHORT).show();
                    } else {

                        rounded_completed_dialog();


                    }


                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//              Timer next Question


                question_a.setClickable(true);
                question_b.setClickable(true);
                question_c.setClickable(true);
                question_d.setClickable(true);

                if (game_type.equalsIgnoreCase("timed")) {
                    if (game_type.equalsIgnoreCase("timed")) {
                        timestart = Calendar.getInstance().getTime();
                    }
                    timer_counter.start();
                    timer_main.setVisibility(View.VISIBLE);
                    answer_msg.setVisibility(View.GONE);
                    animation_progress_bar = new ProgressBarAnimation(ArcadeModeActivity.this, progress_bar, null, 0f, 100);
                    animation_progress_bar.setDuration(16000);
                    progress_bar.setAnimation(animation_progress_bar);


                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        question_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backgroundMusic.Stopsound();
                backgroundMusic.Playsound(ArcadeModeActivity.this, "select_option.mp3");
                backgroundMusic.PlayVibrate500(ArcadeModeActivity.this, 100);
                question_a.setBackgroundResource(R.drawable.rounded_border_white_bold);
                question_a.setClickable(false);
                question_b.setClickable(false);
                question_c.setClickable(false);
                question_d.setClickable(false);

                if (game_type.equalsIgnoreCase("timed")) {
                    int prog = progress_bar.getProgress();
                    timer_counter.cancel();
                    animation_progress_bar.cancel();

                    progress_bar.setProgress(prog);

                }

                new CountDownTimer(1500, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {

                        timer_main.setVisibility(View.GONE);
                        answer_msg.setVisibility(View.VISIBLE);

                        if (question_b_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_b.setBackgroundResource(R.drawable.answer_true_bg);
                        }
                        if (!QuestionType.equalsIgnoreCase("boolean")) {

                            if (question_c_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_c.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                            if (question_d_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_d.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                        }


                        if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_a.setBackgroundResource(R.drawable.answer_true_bg);
                            answer_msg_text.setText("Great! Correct Answer");
                            total_correct_ans++;

                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(ArcadeModeActivity.this, "correct_answer_select.mp3");


                            if (currentMoney < 300) {
                                currentMoney = currentMoney + 100;
                            } else if (currentMoney == 300) {
                                currentMoney = currentMoney + 200;
                            } else if (currentMoney < 64000) {
                                currentMoney = currentMoney * 2;
                            } else if (currentMoney == 64000) {
                                currentMoney = 125000;
                            } else {
                                currentMoney = currentMoney * 2;
                            }

                            current_money.setText("$ " + NumberFormat.getNumberInstance(Locale.US).format(currentMoney));
                            question_counter++;

                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    option_a_text.startAnimation(animation_out);
                                    question_b_text.startAnimation(animation_out);
                                    question_c_text.startAnimation(animation_out);
                                    question_d_text.startAnimation(animation_out);
                                    question_text.startAnimation(animation_out);
                                }
                            }.start();


                        } else {

                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(ArcadeModeActivity.this, "wrong_asnwer_select.mp3");
                            backgroundMusic.PlayVibrate500(ArcadeModeActivity.this, 2000);

                            answer_msg_text.setText("Oops! Your answer is wrong");

                            question_a.setBackgroundResource(R.drawable.answer_false_bg);
                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {


                                            to_resultscreen();


                                }
                            }.start();

                        }

                    }

                }.start();


            }
        });
        question_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backgroundMusic.Stopsound();
                backgroundMusic.Playsound(ArcadeModeActivity.this, "select_option.mp3");
                backgroundMusic.PlayVibrate500(ArcadeModeActivity.this, 200);
                question_a.setClickable(false);
                question_b.setClickable(false);
                question_c.setClickable(false);
                question_d.setClickable(false);
                question_b.setBackgroundResource(R.drawable.rounded_border_white_bold);

                if (game_type.equalsIgnoreCase("timed")) {
                    int prog = progress_bar.getProgress();
                    timer_counter.cancel();

                    animation_progress_bar.cancel();

                    progress_bar.setProgress(prog);
                }


                new CountDownTimer(1500, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        timer_main.setVisibility(View.GONE);

                        if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_a.setBackgroundResource(R.drawable.answer_true_bg);
                        }
                        if (!QuestionType.equalsIgnoreCase("boolean")) {

                            if (question_c_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_c.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                            if (question_d_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_d.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                        }


                        answer_msg.setVisibility(View.VISIBLE);

                        if (question_b_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_b.setBackgroundResource(R.drawable.answer_true_bg);
                            answer_msg_text.setText("Great! Correct Answer");
                            question_counter++;
                            total_correct_ans++;

                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(ArcadeModeActivity.this, "correct_answer_select.mp3");

                            if (currentMoney < 300) {
                                currentMoney = currentMoney + 100;
                            } else if (currentMoney == 300) {
                                currentMoney = currentMoney + 200;
                            } else if (currentMoney < 64000) {
                                currentMoney = currentMoney * 2;
                            } else if (currentMoney == 64000) {
                                currentMoney = 125000;
                            } else {
                                currentMoney = currentMoney * 2;
                            }
                            current_money.setText("$ " + NumberFormat.getNumberInstance(Locale.US).format(currentMoney));

                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    option_a_text.startAnimation(animation_out);
                                    question_b_text.startAnimation(animation_out);
                                    question_c_text.startAnimation(animation_out);
                                    question_d_text.startAnimation(animation_out);
                                    question_text.startAnimation(animation_out);
                                }
                            }.start();

                        } else {
                            answer_msg_text.setText("Oops! Your answer is wrong");
                            question_b.setBackgroundResource(R.drawable.answer_false_bg);

                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(ArcadeModeActivity.this, "wrong_asnwer_select.mp3");
                            backgroundMusic.PlayVibrate500(ArcadeModeActivity.this, 2000);

                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {


                                            to_resultscreen();

                                }
                            }.start();
                        }
                    }

                }.start();


            }
        });
        question_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question_a.setClickable(false);
                question_b.setClickable(false);
                question_c.setClickable(false);
                question_d.setClickable(false);

                backgroundMusic.Stopsound();
                backgroundMusic.Playsound(ArcadeModeActivity.this, "select_option.mp3");
                backgroundMusic.PlayVibrate500(ArcadeModeActivity.this, 100);
                question_c.setBackgroundResource(R.drawable.rounded_border_white_bold);
                if (game_type.equalsIgnoreCase("timed")) {
                    int prog = progress_bar.getProgress();
                    timer_counter.cancel();
                    animation_progress_bar.cancel();

                    progress_bar.setProgress(prog);

                }


                new CountDownTimer(1500, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        timer_main.setVisibility(View.GONE);
                        answer_msg.setVisibility(View.VISIBLE);
                        if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_a.setBackgroundResource(R.drawable.answer_true_bg);
                        }
                        if (!QuestionType.equalsIgnoreCase("boolean")) {

                            if (question_b_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_b.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                            if (question_d_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_d.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                        }


                        if (question_c_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_c.setBackgroundResource(R.drawable.answer_true_bg);
                            answer_msg_text.setText("Great! Correct Answer");
                            question_counter++;
                            total_correct_ans++;

                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(ArcadeModeActivity.this, "correct_answer_select.mp3");

                            if (currentMoney < 300) {
                                currentMoney = currentMoney + 100;
                            } else if (currentMoney == 300) {
                                currentMoney = currentMoney + 200;
                            } else if (currentMoney < 64000) {
                                currentMoney = currentMoney * 2;
                            } else if (currentMoney == 64000) {
                                currentMoney = 125000;
                            } else {
                                currentMoney = currentMoney * 2;
                            }
                            current_money.setText("$ " + NumberFormat.getNumberInstance(Locale.US).format(currentMoney));
                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    option_a_text.startAnimation(animation_out);
                                    question_b_text.startAnimation(animation_out);
                                    question_c_text.startAnimation(animation_out);
                                    question_d_text.startAnimation(animation_out);
                                    question_text.startAnimation(animation_out);
                                }
                            }.start();
                        } else {
                            answer_msg_text.setText("Oops! Your answer is wrong");
                            question_c.setBackgroundResource(R.drawable.answer_false_bg);

                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(ArcadeModeActivity.this, "wrong_asnwer_select.mp3");
                            backgroundMusic.PlayVibrate500(ArcadeModeActivity.this, 2000);

                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                           to_resultscreen();

                                }
                            }.start();
                        }


                    }

                }.start();

            }
        });
        question_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                question_a.setClickable(false);
                question_b.setClickable(false);
                question_c.setClickable(false);
                question_d.setClickable(false);
                backgroundMusic.Stopsound();
                backgroundMusic.Playsound(ArcadeModeActivity.this, "select_option.mp3");
                backgroundMusic.PlayVibrate500(ArcadeModeActivity.this, 100);
                question_d.setBackgroundResource(R.drawable.rounded_border_white_bold);
                if (game_type.equalsIgnoreCase("timed")) {
                    int prog = progress_bar.getProgress();
                    timer_counter.cancel();

                    animation_progress_bar.cancel();

                    progress_bar.setProgress(prog);

                }

                new CountDownTimer(1500, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        timer_main.setVisibility(View.GONE);
                        answer_msg.setVisibility(View.VISIBLE);
                        if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_a.setBackgroundResource(R.drawable.answer_true_bg);
                        }
                        if (!QuestionType.equalsIgnoreCase("boolean")) {

                            if (question_b_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_b.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                            if (question_c_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_c.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                        }


                        if (question_d_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_d.setBackgroundResource(R.drawable.answer_true_bg);
                            answer_msg_text.setText("Great! Correct Answer");
                            question_counter++;
                            total_correct_ans++;
                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(ArcadeModeActivity.this, "correct_answer_select.mp3");

                            if (currentMoney < 300) {
                                currentMoney = currentMoney + 100;
                            } else if (currentMoney == 300) {
                                currentMoney = currentMoney + 200;
                            } else if (currentMoney < 64000) {
                                currentMoney = currentMoney * 2;
                            } else if (currentMoney == 64000) {
                                currentMoney = 125000;
                            } else {
                                currentMoney = currentMoney * 2;
                            }
                            current_money.setText("$ " + NumberFormat.getNumberInstance(Locale.US).format(currentMoney));
                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    option_a_text.startAnimation(animation_out);
                                    question_b_text.startAnimation(animation_out);
                                    question_c_text.startAnimation(animation_out);
                                    question_d_text.startAnimation(animation_out);
                                    question_text.startAnimation(animation_out);
                                }
                            }.start();
                        } else {
                            answer_msg_text.setText("Oops! Your answer is wrong");
                            question_d.setBackgroundResource(R.drawable.answer_false_bg);
                            backgroundMusic.PlayVibrate500(ArcadeModeActivity.this, 2000);
                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(ArcadeModeActivity.this, "wrong_asnwer_select.mp3");

                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {


                                            to_resultscreen();
                                }
                            }.start();
                        }


                    }

                }.start();

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit_dialog();
//                rounded_completed_dialog();
            }
        });
        score_board_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcadeModeActivity.this, ScoreBoardActivity.class);
                intent.putExtra("current_score", String.valueOf(currentMoney));

                startActivity(intent);
            }
        });

        backgroundMusic = BackgroundMusic.getInstance(this);
        backgroundMusic.playmusic_background(this, "during_game.mp3");


    }


    private void rounded_completed_dialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.round_complete_dialog);


        TextView okbtn = (TextView) dialog.findViewById(R.id.okbtn);
        TextView your_money = (TextView) dialog.findViewById(R.id.your_money);
        RelativeLayout next_round = (RelativeLayout) dialog.findViewById(R.id.next_round);
        ImageView round_icon = (ImageView) dialog.findViewById(R.id.round_icon);

        your_money.setText(current_money.getText().toString());
        if (question_difficulty == 1)
            round_icon.setImageResource(R.drawable.round1);
        else if (question_difficulty == 2)
            round_icon.setImageResource(R.drawable.round2);
        else if (question_difficulty == 3)
            round_icon.setImageResource(R.drawable.round3);


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                to_resultscreen();
            }
        });

        next_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    question_difficulty++;
                    question_stage = 5;
                    if (Paper.book().read("game_mode", "normal_mode").equalsIgnoreCase("normal_mode")) {
                        if (question_difficulty == 2) {
                            if (QuestionType.equalsIgnoreCase("boolean")) {
                                getquestions("medium", "boolean");

                            } else {
                                getquestions("medium", "multiple");

                            }
                        } else if (question_difficulty == 3) {
                            if (QuestionType.equalsIgnoreCase("boolean")) {
                                getquestions("hard", "boolean");

                            } else {
                                getquestions("hard", "multiple");

                            }
                        } else if (question_difficulty == 1) {
                            if (QuestionType.equalsIgnoreCase("boolean")) {
                                getquestions("easy", "boolean");

                            } else {
                                getquestions("easy", "multiple");

                            }
                        }
                    } else {
                        if (question_difficulty == 2) {
                            if (QuestionType.equalsIgnoreCase("boolean")) {
                                getquestions("easy", "boolean");

                            } else {
                                getquestions("easy", "multiple");

                            }
                        } else if (question_difficulty == 3) {
                            if (QuestionType.equalsIgnoreCase("boolean")) {
                                getquestions("medium", "boolean");

                            } else {
                                getquestions("medium", "multiple");

                            }
                        } else if (question_difficulty == 1) {
                            if (QuestionType.equalsIgnoreCase("boolean")) {
                                getquestions("easy", "boolean");

                            } else {
                                getquestions("easy", "multiple");

                            }
                        }
                    }



                dialog.dismiss();


            }
        });


        dialog.show();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStop() {


        if (!game_type.equalsIgnoreCase("timed")) {
            Paper.book().write("total_question_asked", String.valueOf(total_question_asked));
            Paper.book().write("total_correct_ans", String.valueOf(total_correct_ans));
        }
        super.onStop();
    }

    private void exit_dialog() {
        dialogexit = new Dialog(this);
        dialogexit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogexit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogexit.setCancelable(false);
        dialogexit.setContentView(R.layout.exit_dialog);

        final TextView takemoney_btn = (TextView) dialogexit.findViewById(R.id.takemoney_btn);
        final TextView your_money = (TextView) dialogexit.findViewById(R.id.your_money);
        final TextView time_running = (TextView) dialogexit.findViewById(R.id.time_running);


        final ImageView sound_btn = (ImageView) dialogexit.findViewById(R.id.sound_btn);
        final ImageView vibration_btn = (ImageView) dialogexit.findViewById(R.id.vibration_btn);
        final ImageView music_btn = (ImageView) dialogexit.findViewById(R.id.music_btn);
        String check_sound = Utilities.getString(ArcadeModeActivity.this, History.check_sound, "yes");
        if (check_sound.equalsIgnoreCase("yes")) {
            sound_btn.setImageResource(R.drawable.exit_volume_on);
        } else {
            sound_btn.setImageResource(R.drawable.exit_volume_off);
        }

        String check_music = Utilities.getString(ArcadeModeActivity.this, History.check_music, "yes");
        if (check_music.equalsIgnoreCase("yes")) {
            music_btn.setImageResource(R.drawable.exit_music_on);
        } else {
            music_btn.setImageResource(R.drawable.exit_music_off);
        }

        String checkvid = Utilities.getString(ArcadeModeActivity.this, History.IS_VIB, "yes");
        if (checkvid.equalsIgnoreCase("yes")) {
            vibration_btn.setImageResource(R.drawable.exit_vibration_on);
        } else {
            vibration_btn.setImageResource(R.drawable.exit_vibration_off);
        }


        vibration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkvid = Utilities.getString(ArcadeModeActivity.this, History.IS_VIB, "yes");
                if (checkvid.equalsIgnoreCase("yes")) {
                    vibration_btn.setImageResource(R.drawable.exit_vibration_off);
                    Utilities.saveString(ArcadeModeActivity.this, History.IS_VIB, "no");
                } else {
                    vibration_btn.setImageResource(R.drawable.exit_vibration_on);
                    Utilities.saveString(ArcadeModeActivity.this, History.IS_VIB, "yes");
                }
            }
        });


        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_sound = Utilities.getString(ArcadeModeActivity.this, History.check_sound, "yes");
                if (check_sound.equalsIgnoreCase("yes")) {
                    backgroundMusic.Stopsound();
                    sound_btn.setImageResource(R.drawable.exit_volume_off);
                    Utilities.saveString(ArcadeModeActivity.this, History.check_sound, "no");
                } else {
                    sound_btn.setImageResource(R.drawable.exit_volume_on);
                    Utilities.saveString(ArcadeModeActivity.this, History.check_sound, "yes");
                }
            }
        });

        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_sound = Utilities.getString(ArcadeModeActivity.this, History.check_music, "yes");
                if (check_sound.equalsIgnoreCase("yes")) {
                    backgroundMusic.Stopmusic_background();
                    music_btn.setImageResource(R.drawable.exit_music_off);
                    Utilities.saveString(ArcadeModeActivity.this, History.check_music, "no");
                } else {
                    music_btn.setImageResource(R.drawable.exit_music_on);
                    Utilities.saveString(ArcadeModeActivity.this, History.check_music, "yes");
                    backgroundMusic.playmusic_background(ArcadeModeActivity.this, "during_game.mp3");
                }
            }
        });

        if (game_type.equalsIgnoreCase("timed")) {
            time_running.setVisibility(View.VISIBLE);
        }
        your_money.setText(current_money.getText().toString());
        takemoney_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogexit.dismiss();

                if (timer_counter != null) {
                    timer_counter.cancel();

                }

                to_resultscreen();
            }
        });

        TextView okbtn = (TextView) dialogexit.findViewById(R.id.okbtn);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogexit.dismiss();
            }
        });


        dialogexit.show();
    }

    private void to_resultscreen() {
        backgroundMusic.Stopmusic_background();

        if (currentMoney == 0) {
            Intent intent = new Intent(ArcadeModeActivity.this, GameOutActivity.class);
            intent.putExtra("current_money", String.valueOf(currentMoney));
            intent.putExtra("question_type", QuestionType);
            intent.putExtra("game_type", game_type);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(ArcadeModeActivity.this, GameOverActivity.class);
            intent.putExtra("current_money", String.valueOf(currentMoney));
            intent.putExtra("question_type", QuestionType);
            intent.putExtra("game_type", game_type);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }


    }


    private void wrong_ans_dialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.wrong_ans_dialog);


        TextView okbtn = (TextView) dialog.findViewById(R.id.okbtn);


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                to_resultscreen();
            }
        });




        dialog.show();
    }



    void getQuestionWithDiffiulty() {
        if (question_difficulty == 2) {
            if (QuestionType.equalsIgnoreCase("boolean")) {
                getsinglequestions("medium", "boolean");

            } else {
                getsinglequestions("medium", "multiple");

            }
        } else if (question_difficulty == 3) {
            if (QuestionType.equalsIgnoreCase("boolean")) {
                getsinglequestions("hard", "boolean");

            } else {
                getsinglequestions("hard", "multiple");

            }
        } else if (question_difficulty == 1) {
            if (QuestionType.equalsIgnoreCase("boolean")) {
                getsinglequestions("easy", "boolean");

            } else {
                getsinglequestions("easy", "multiple");

            }
        }
    }

    private void askdeniusdialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.ask_genius_dialog);

        final TextView correct_ans = (TextView) dialog.findViewById(R.id.correct_ans);
        correct_ans.setText("I am sure the correct answer is: \"" + genius_ans + "\"");
        TextView okbtn = (TextView) dialog.findViewById(R.id.okbtn);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void publicpoldialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.public_pol_dialog);

        final TextView optin_a_text = (TextView) dialog.findViewById(R.id.optin_a_text);
        final TextView optin_b_text = (TextView) dialog.findViewById(R.id.optin_b_text);
        final TextView optin_c_text = (TextView) dialog.findViewById(R.id.optin_c_text);
        final TextView optin_d_text = (TextView) dialog.findViewById(R.id.optin_d_text);

        final ProgressBar progress_bar_optin_a = (ProgressBar) dialog.findViewById(R.id.progress_bar);
        final ProgressBar progress_bar_optin_b = (ProgressBar) dialog.findViewById(R.id.progress_bar_b);
        final ProgressBar progress_bar_optin_c = (ProgressBar) dialog.findViewById(R.id.progress_bar_c);
        final ProgressBar progress_bar_optin_d = (ProgressBar) dialog.findViewById(R.id.progress_bar_d);

        final LinearLayout progress_d = (LinearLayout) dialog.findViewById(R.id.progress_d);
        final LinearLayout progress_c = (LinearLayout) dialog.findViewById(R.id.progress_c);

        TextView okbtn = (TextView) dialog.findViewById(R.id.okbtn);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (QuestionType.equalsIgnoreCase("boolean")) {
            progress_c.setVisibility(View.GONE);
            progress_d.setVisibility(View.GONE);
        } else {
            progress_c.setVisibility(View.VISIBLE);
            progress_d.setVisibility(View.VISIBLE);
        }

        if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
            progressAnimation(progress_bar_optin_a, optin_a_text, 95f);
            progressAnimation(progress_bar_optin_b, optin_b_text, 70f);
            progressAnimation(progress_bar_optin_c, optin_c_text, 25f);
            progressAnimation(progress_bar_optin_d, optin_d_text, 50f);
        }

        if (question_b_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
            progressAnimation(progress_bar_optin_a, optin_a_text, 25f);
            progressAnimation(progress_bar_optin_b, optin_b_text, 90f);
            progressAnimation(progress_bar_optin_c, optin_c_text, 50f);
            progressAnimation(progress_bar_optin_d, optin_d_text, 75f);
        }
        if (question_c_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
            progressAnimation(progress_bar_optin_a, optin_a_text, 75f);
            progressAnimation(progress_bar_optin_b, optin_b_text, 25f);
            progressAnimation(progress_bar_optin_c, optin_c_text, 95f);
            progressAnimation(progress_bar_optin_d, optin_d_text, 50f);
        }
        if (question_d_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
            progressAnimation(progress_bar_optin_a, optin_a_text, 50f);
            progressAnimation(progress_bar_optin_b, optin_b_text, 70f);
            progressAnimation(progress_bar_optin_c, optin_c_text, 25f);
            progressAnimation(progress_bar_optin_d, optin_d_text, 95f);
        }


        dialog.show();
    }

    private void progressAnimation(ProgressBar progress_bar_optin, TextView optin_a_text, float range) {


        ProgressBarAnimation animation = new ProgressBarAnimation(ArcadeModeActivity.this, progress_bar_optin, optin_a_text, 0f, range);
        animation.setDuration(6000);
        progress_bar_optin.setAnimation(animation);
    }


    private void init() {


        answer_msg = findViewById(R.id.answer_msg);
        question_text = findViewById(R.id.question_text);
        option_a_text = findViewById(R.id.option_a_text);
        question_b_text = findViewById(R.id.question_b_text);
        question_c_text = findViewById(R.id.question_c_text);
        question_d_text = findViewById(R.id.question_d_text);
        question_a = findViewById(R.id.question_a);
        question_b = findViewById(R.id.question_b);
        question_c = findViewById(R.id.question_c);
        question_d = findViewById(R.id.question_d);
        backbtn = findViewById(R.id.backbtn);
        answer_msg_text = findViewById(R.id.answer_msg_text);
        current_money = findViewById(R.id.current_money);
        timer_text = findViewById(R.id.timer_text);
        timer_main = findViewById(R.id.timer_main);
        main_screen = findViewById(R.id.main_screen);
       progress_bar = findViewById(R.id.progress_bar);
        score_board_btn = findViewById(R.id.score_board_btn);
    }

    private void getquestions(String difficulty, String type) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);

        final TextView cat_name = (TextView) dialog.findViewById(R.id.cat_name);
        final TextView text = (TextView) dialog.findViewById(R.id.text);
        final TextView round_text = (TextView) dialog.findViewById(R.id.round_text);
        final ImageView cat_icon = (ImageView) dialog.findViewById(R.id.cat_icon);
        round_text.setText("ROUND");
        if (game_type.equalsIgnoreCase("timed")) {
            cat_name.setText("Timed");
            cat_icon.setImageResource(R.drawable.clock_loader);

        }
        int round = question_difficulty;
        if (round == 0)
            round = round + 1;
        text.setText(String.valueOf(round));

        dialog.show();
        final StringRequest RegistrationRequest = new StringRequest(Request.Method.GET, "https://opentdb.com/api.php?amount=5&difficulty=" + difficulty + "&type=" + type + "&encode=base64", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                try {
                    questionsModelArrayList = new ArrayList<>();

                    JSONObject object = new JSONObject(response);
                    int response_code = object.getInt("response_code");
                    if (response_code == 0) {
                        dialog.dismiss();
                        JSONArray results = object.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            ArrayList<String> incorrect_ansr = new ArrayList<>();
                            JSONObject jsonObject = results.getJSONObject(i);
                            String question_base64 = jsonObject.getString("question");
                            byte[] data = Base64.decode(question_base64, Base64.DEFAULT);
                            String question = new String(data, StandardCharsets.UTF_8);


                            String correct_answer_base = jsonObject.getString("correct_answer");
                            byte[] data2 = Base64.decode(correct_answer_base, Base64.DEFAULT);
                            String correct_answer = new String(data2, StandardCharsets.UTF_8);

                            JSONArray incorrect_ans = jsonObject.getJSONArray("incorrect_answers");
                            for (int j = 0; j < incorrect_ans.length(); j++) {
                                String a_base = incorrect_ans.getString(j);
                                byte[] data3 = Base64.decode(a_base, Base64.DEFAULT);
                                String a = new String(data3, StandardCharsets.UTF_8);
                                incorrect_ansr.add(a);
                            }
                            incorrect_ansr.add(correct_answer);
                            questionsModelArrayList.add(new QuestionsModel(question, correct_answer, incorrect_ansr));
                        }

//                                      Timed Game Setup
                        if (game_type.equalsIgnoreCase("timed")) {
                            answer_msg.setVisibility(View.GONE);
                            timer_main.setVisibility(View.VISIBLE);


                            animation_progress_bar = new ProgressBarAnimation(ArcadeModeActivity.this, progress_bar, null, 0f, 100);
                            animation_progress_bar.setDuration(16000);
                            progress_bar.setAnimation(animation_progress_bar);


                            timer_counter = new CountDownTimer(16000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    int size = String.valueOf(millisUntilFinished / 1000).length();
                                    if (size == 1) {
                                        timer_text.setText("00:0" + millisUntilFinished / 1000);

                                    } else {

                                        timer_text.setText("00:" + millisUntilFinished / 1000);
                                    }
                                }

                                public void onFinish() {


                                    if (dialogexit != null)
                                        dialogexit.dismiss();

                                    answer_msg_text.setText("Time Up..!");
                                    answer_msg.setVisibility(View.VISIBLE);
                                    timer_main.setVisibility(View.GONE);


                                    if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                        question_a.setBackgroundResource(R.drawable.answer_true_bg);
                                    }
                                    if (question_b_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                        question_b.setBackgroundResource(R.drawable.answer_true_bg);
                                    }
                                    if (!QuestionType.equalsIgnoreCase("boolean")) {

                                        if (question_c_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                            question_c.setBackgroundResource(R.drawable.answer_true_bg);
                                        }
                                        if (question_d_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                            question_d.setBackgroundResource(R.drawable.answer_true_bg);
                                        }
                                    }


                                    new CountDownTimer(3000, 1000) {
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        public void onFinish() {

                                            if (!singledialog) {
                                                singledialog = true;

                                                        to_resultscreen();                                            } else {
                                                singledialog = false;
                                            }
                                        }
                                    }.start();

                                }

                            }.start();


                        } else {
                            timer_main.setVisibility(View.GONE);
                            answer_msg.setVisibility(View.INVISIBLE);
                        }

                        Integer[] arr;
                        question_counter = 0;
                        if (QuestionType.equalsIgnoreCase("boolean")) {
                            arr = new Integer[1];
                            arr = Utilities.randonnum(2);
                        } else {
                            arr = new Integer[3];
                            arr = Utilities.randonnum(4);
                        }

                        total_question_asked++;
                        question_text.setText(questionsModelArrayList.get(question_counter).getQuestion());

                        option_a_text.setText(questionsModelArrayList.get(question_counter).getIncorrectans().get(Arrays.asList(arr).indexOf(0)));
                        question_b_text.setText(questionsModelArrayList.get(question_counter).getIncorrectans().get(Arrays.asList(arr).indexOf(1)));

                        if (QuestionType.equalsIgnoreCase("multiple")) {

                            question_c_text.setText(questionsModelArrayList.get(question_counter).getIncorrectans().get(Arrays.asList(arr).indexOf(2)));
                            question_d_text.setText(questionsModelArrayList.get(question_counter).getIncorrectans().get(Arrays.asList(arr).indexOf(3)));
                        } else {
                            option_a_text.setText("True");
                            question_b_text.setText("False");
                        }


                        question_a.setBackgroundResource(R.drawable.rounded_border_blue);
                        question_b.setBackgroundResource(R.drawable.rounded_border_blue);
                        question_c.setBackgroundResource(R.drawable.rounded_border_blue);
                        question_d.setBackgroundResource(R.drawable.rounded_border_blue);
                        option_a_text.startAnimation(animation_in);
                        question_b_text.startAnimation(animation_in);
                        question_c_text.startAnimation(animation_in);
                        question_d_text.startAnimation(animation_in);
                        question_text.startAnimation(animation_in);
                        if (QuestionType.equalsIgnoreCase("boolean")) {
                            question_c.setVisibility(View.GONE);
                            question_d.setVisibility(View.GONE);

                        } else {
                            question_c.setVisibility(View.VISIBLE);
                            question_d.setVisibility(View.VISIBLE);
                        }


                    } else {

                        Toast.makeText(ArcadeModeActivity.this, "No Question Available..!", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                String message = null;
                check_internet();
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (this != null)
                    Toast.makeText(ArcadeModeActivity.this, message, Toast.LENGTH_LONG).show();


            }
        }) {

        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(this).addToRequestQueue(RegistrationRequest);
    }

//    void countdown_timer(){
//
//
//        final Date timeend = Calendar.getInstance().getTime();
//
//        long diff = timeend.getTime() - timestart.getTime();
//        long diffSeconds = diff / 1000;
//        long diffMinutes = diff / (60 * 1000);
//        long diffHours = diff / (60 * 60 * 1000);
//        int additionalSeconds = (int) (long) diffSeconds;
//        int additionalMinute = (int) (long) diffMinutes;
//        int additionalHour = (int) (long) diffHours;
//
//
//
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//        String currentDateandTime = sdf.format(new Date());
//        Date date = null;
//        try {
//            date = sdf.parse(currentDateandTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.HOUR, additionalHour);
//        calendar.add(Calendar.MINUTE, additionalMinute);
//        calendar.add(Calendar.SECOND, additionalSeconds);
//
//        System.out.println("Desired Time here "+calendar.getTime());
//        String currentDateandTime2 = sdf.format(calendar.getTime());
//
//
//    }


    private void getsinglequestions(String difficulty, String type) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);

        final TextView cat_name = (TextView) dialog.findViewById(R.id.cat_name);
        final TextView text = (TextView) dialog.findViewById(R.id.text);
        final TextView round_text = (TextView) dialog.findViewById(R.id.round_text);
        final ImageView cat_icon = (ImageView) dialog.findViewById(R.id.cat_icon);
        round_text.setText("");
        if (game_type.equalsIgnoreCase("timed")) {
            cat_name.setText("Timed");
            cat_icon.setImageResource(R.drawable.clock_loader);

        }

        text.setText("Fetching a new question");
        text.setTextSize(16);
       dialog.show();
        final StringRequest RegistrationRequest = new StringRequest(Request.Method.GET, "https://opentdb.com/api.php?amount=1&difficulty=" + difficulty + "&type=" + type + "&encode=base64", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                try {

                        dialog.dismiss();

                    JSONObject object = new JSONObject(response);
                    int response_code = object.getInt("response_code");
                    if (response_code == 0) {
                        JSONArray results = object.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            ArrayList<String> incorrect_ansr = new ArrayList<>();
                            JSONObject jsonObject = results.getJSONObject(i);
                            String question_base64 = jsonObject.getString("question");
                            byte[] data = Base64.decode(question_base64, Base64.DEFAULT);
                            String question = new String(data, StandardCharsets.UTF_8);


                            String correct_answer_base = jsonObject.getString("correct_answer");
                            byte[] data2 = Base64.decode(correct_answer_base, Base64.DEFAULT);
                            String correct_answer = new String(data2, StandardCharsets.UTF_8);

                            JSONArray incorrect_ans = jsonObject.getJSONArray("incorrect_answers");
                            for (int j = 0; j < incorrect_ans.length(); j++) {
                                String a_base = incorrect_ans.getString(j);
                                byte[] data3 = Base64.decode(a_base, Base64.DEFAULT);
                                String a = new String(data3, StandardCharsets.UTF_8);
                                incorrect_ansr.add(a);
                            }
                            incorrect_ansr.add(correct_answer);
                            questionsModelArrayList.add(new QuestionsModel(question, correct_answer, incorrect_ansr));
                            question_stage++;

                        }

                    } else {

                            dialog.dismiss();

                        Toast.makeText(ArcadeModeActivity.this, "No Question Available..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                    dialog.show();

                check_internet();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";

                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (this != null)
                    Toast.makeText(ArcadeModeActivity.this, message, Toast.LENGTH_LONG).show();


            }
        }) {
        };

        RegistrationRequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(this).addToRequestQueue(RegistrationRequest);
    }

    private void check_internet() {
        final Dialog dialogexit = new Dialog(this);
        dialogexit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogexit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogexit.setCancelable(false);
        dialogexit.setContentView(R.layout.check_internet_dialog);


        final TextView retry = (TextView) dialogexit.findViewById(R.id.retry);
        final TextView exit = (TextView) dialogexit.findViewById(R.id.exit);


        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetAvailable_for_popup(ArcadeModeActivity.this)) {
                    dialogexit.dismiss();
                } else {
                    Toast.makeText(ArcadeModeActivity.this, "Internet not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogexit.dismiss();
                finishAffinity();

            }
        });


        dialogexit.show();
    }
}