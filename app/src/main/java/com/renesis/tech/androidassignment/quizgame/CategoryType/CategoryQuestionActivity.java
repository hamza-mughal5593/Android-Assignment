package com.renesis.tech.androidassignment.quizgame.CategoryType;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryLevelModel;
import com.renesis.tech.androidassignment.quizgame.Pojo.CategoryListModel;
import com.renesis.tech.androidassignment.quizgame.Pojo.QuestionsModel;
import com.renesis.tech.androidassignment.quizgame.R;
import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;
import com.renesis.tech.androidassignment.quizgame.Utils.History;
import com.renesis.tech.androidassignment.quizgame.Utils.Server.MySingleton;
import com.renesis.tech.androidassignment.quizgame.Utils.Utilities;
import com.renesis.tech.androidassignment.quizgame.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class CategoryQuestionActivity extends AppCompatActivity {
    LinearLayout answer_msg, question_a, question_b, question_c, question_d, question_number_main, cat_name_bg;
    TextView question_text, option_a_text, question_b_text, question_c_text, question_d_text, answer_msg_text, current_question, catg_name;
    ImageView backbtn;
    ArrayList<QuestionsModel> questionsModelArrayList;
    Animation animation_out, animation_in, animation_out_lifeline, animation_in_lifeline;
    int question_counter = 0;

    String cat_name;
    int cat_id;
    LinearLayout main_screen;

    boolean check_fifty = false;

    int current_question_count = 1;

    int questionAsked = 20;


    Dialog dialogexit;
    public static int total_question_asked = 0, total_correct_ans = 1;
    CategoryLevelModel categoryLevelModel;



    int cat_color = 0;
    CategoryListModel categoryListModel;





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
        setContentView(R.layout.activity_category_question);
        Paper.init(this);







        total_question_asked = Integer.parseInt(Paper.book().read("total_question_asked", "0"));

        categoryLevelModel = Paper.book().read("categoryLevelModel", null);

        String totalcorrect_ans = "total_correct_ans" + categoryLevelModel.getId() + Paper.book().read("categoryListModel_pos", 0);
        total_correct_ans = Integer.parseInt(Paper.book().read(totalcorrect_ans, "1"));

        questionAsked = questionAsked - total_correct_ans;


        init();


        categoryListModel = Paper.book().read("categoryListModel", null);
        cat_color = categoryListModel.getCat_color();
        cat_id = categoryListModel.getId();
        cat_name = categoryListModel.getCat_name();


        GradientDrawable backgroundGradient2 = (GradientDrawable) cat_name_bg.getBackground();
        backgroundGradient2.setColor(getResources().getColor(cat_color));
        cat_name_bg.setBackground(backgroundGradient2);

        catg_name.setText(cat_name);
        catg_name.setSelected(true);
//          Arcade game setup
        getquestions();


//                                              set top Question counter
        current_question_count = total_correct_ans;
        current_question.setText(String.valueOf(current_question_count));


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


                if (question_counter <= questionAsked) {
                    Integer[] arr;
                    if (questionsModelArrayList.get(question_counter).getType().equalsIgnoreCase("boolean")) {
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

                    if (questionsModelArrayList.get(question_counter).getType().equalsIgnoreCase("multiple")) {

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

                    if (questionsModelArrayList.get(question_counter).getType().equalsIgnoreCase("boolean")) {
                        question_c.setVisibility(View.GONE);
                        question_d.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(CategoryQuestionActivity.this, "Level completed", Toast.LENGTH_SHORT).show();
                    backgroundMusic.Stopsound();
                    backgroundMusic.Playsound(CategoryQuestionActivity.this, "some_money.mp3");
                    level_complete_dialog();

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
                answer_msg.setVisibility(View.GONE);
                question_number_main.setVisibility(View.VISIBLE);
                current_question.setText(String.valueOf(current_question_count));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        question_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backgroundMusic.Stopsound();
                backgroundMusic.Playsound(CategoryQuestionActivity.this, "select_option.mp3");
                backgroundMusic.PlayVibrate500(CategoryQuestionActivity.this, 120);
                question_a.setBackgroundResource(R.drawable.rounded_border_white_bold);
                question_a.setClickable(false);
                question_b.setClickable(false);
                question_c.setClickable(false);
                question_d.setClickable(false);


                new CountDownTimer(1500, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {

                        answer_msg.setVisibility(View.VISIBLE);
                        question_number_main.setVisibility(View.GONE);
                        if (question_b_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_b.setBackgroundResource(R.drawable.answer_true_bg);
                        }
                        if (!questionsModelArrayList.get(question_counter).getType().equalsIgnoreCase("boolean")) {

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
                            question_counter++;
                            current_question_count++;

                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(CategoryQuestionActivity.this, "correct_answer_select.mp3");

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


                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(CategoryQuestionActivity.this, "wrong_asnwer_select.mp3");
                            backgroundMusic.PlayVibrate500(CategoryQuestionActivity.this, 2000);
                            question_a.setBackgroundResource(R.drawable.answer_false_bg);
                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {


                                    wrong_ans_dialog();

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
                backgroundMusic.Playsound(CategoryQuestionActivity.this, "select_option.mp3");
                backgroundMusic.PlayVibrate500(CategoryQuestionActivity.this, 120);

                question_a.setClickable(false);
                question_b.setClickable(false);
                question_c.setClickable(false);
                question_d.setClickable(false);
                question_b.setBackgroundResource(R.drawable.rounded_border_white_bold);

                new CountDownTimer(1500, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {

                        if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_a.setBackgroundResource(R.drawable.answer_true_bg);
                        }
                        if (!questionsModelArrayList.get(question_counter).getType().equalsIgnoreCase("boolean")) {

                            if (question_c_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_c.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                            if (question_d_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                                question_d.setBackgroundResource(R.drawable.answer_true_bg);
                            }
                        }


                        answer_msg.setVisibility(View.VISIBLE);
                        question_number_main.setVisibility(View.GONE);
                        if (question_b_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_b.setBackgroundResource(R.drawable.answer_true_bg);
                            answer_msg_text.setText("Great! Correct Answer");
                            question_counter++;
                            total_correct_ans++;
                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(CategoryQuestionActivity.this, "correct_answer_select.mp3");
                            current_question_count++;
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
                            backgroundMusic.Playsound(CategoryQuestionActivity.this, "wrong_asnwer_select.mp3");
                            backgroundMusic.PlayVibrate500(CategoryQuestionActivity.this, 2000);

                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {

                                    wrong_ans_dialog();

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
                backgroundMusic.Stopsound();
                backgroundMusic.Playsound(CategoryQuestionActivity.this, "select_option.mp3");
                backgroundMusic.PlayVibrate500(CategoryQuestionActivity.this, 120);

                question_a.setClickable(false);
                question_b.setClickable(false);
                question_c.setClickable(false);
                question_d.setClickable(false);

                question_c.setBackgroundResource(R.drawable.rounded_border_white_bold);


                new CountDownTimer(1500, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        answer_msg.setVisibility(View.VISIBLE);
                        question_number_main.setVisibility(View.GONE);
                        if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_a.setBackgroundResource(R.drawable.answer_true_bg);
                        }
                        if (!questionsModelArrayList.get(question_counter).getType().equalsIgnoreCase("boolean")) {

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
                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(CategoryQuestionActivity.this, "correct_answer_select.mp3");
                            total_correct_ans++;
                            current_question_count++;
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
                            backgroundMusic.Playsound(CategoryQuestionActivity.this, "wrong_asnwer_select.mp3");
                            backgroundMusic.PlayVibrate500(CategoryQuestionActivity.this, 2000);

                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {

                                    wrong_ans_dialog();

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
                backgroundMusic.Stopsound();
                backgroundMusic.Playsound(CategoryQuestionActivity.this, "select_option.mp3");
                backgroundMusic.PlayVibrate500(CategoryQuestionActivity.this, 120);

                question_a.setClickable(false);
                question_b.setClickable(false);
                question_c.setClickable(false);
                question_d.setClickable(false);

                question_d.setBackgroundResource(R.drawable.rounded_border_white_bold);


                new CountDownTimer(1500, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        answer_msg.setVisibility(View.VISIBLE);
                        question_number_main.setVisibility(View.GONE);
                        if (option_a_text.getText().toString().equalsIgnoreCase(questionsModelArrayList.get(question_counter).getCorrect_answer())) {
                            question_a.setBackgroundResource(R.drawable.answer_true_bg);
                        }
                        if (!questionsModelArrayList.get(question_counter).getType().equalsIgnoreCase("boolean")) {

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
                            backgroundMusic.Playsound(CategoryQuestionActivity.this, "correct_answer_select.mp3");
                            current_question_count++;
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
                            backgroundMusic.Stopsound();
                            backgroundMusic.Playsound(CategoryQuestionActivity.this, "wrong_asnwer_select.mp3");
                            backgroundMusic.PlayVibrate500(CategoryQuestionActivity.this, 2000);

                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    wrong_ans_dialog();

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
            }
        });

        backgroundMusic = BackgroundMusic.getInstance(this);
        backgroundMusic.playmusic_background(this, "during_game.mp3");


    }




    private void level_complete_dialog() {
        dialogexit = new Dialog(this);
        dialogexit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogexit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogexit.setCancelable(false);
        dialogexit.setContentView(R.layout.level_complete_dialog);


        final TextView your_money = (TextView) dialogexit.findViewById(R.id.your_money);
        final ImageView cat_img = (ImageView) dialogexit.findViewById(R.id.cat_img);

        your_money.setText("Level " + categoryLevelModel.getId());
        cat_img.setImageResource(categoryLevelModel.getCat_icon());


        TextView okbtn = (TextView) dialogexit.findViewById(R.id.okbtn);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogexit.dismiss();
                to_resultscreen();
            }
        });


        dialogexit.show();
    }

    private void exit_dialog() {
        dialogexit = new Dialog(this);
        dialogexit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogexit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogexit.setCancelable(false);
        dialogexit.setContentView(R.layout.exit_dialog_level);

        final TextView takemoney_btn = (TextView) dialogexit.findViewById(R.id.takemoney_btn);
        final TextView your_money = (TextView) dialogexit.findViewById(R.id.your_money);

        final ImageView sound_btn = (ImageView) dialogexit.findViewById(R.id.sound_btn);
        final ImageView vibration_btn = (ImageView) dialogexit.findViewById(R.id.vibration_btn);
        final ImageView music_btn = (ImageView) dialogexit.findViewById(R.id.music_btn);
        String check_sound = Utilities.getString(CategoryQuestionActivity.this, History.check_sound, "yes");
        if (check_sound.equalsIgnoreCase("yes")) {
            sound_btn.setImageResource(R.drawable.exit_volume_on);
        } else {
            sound_btn.setImageResource(R.drawable.exit_volume_off);
        }

        String check_music = Utilities.getString(CategoryQuestionActivity.this, History.check_music, "yes");
        if (check_music.equalsIgnoreCase("yes")) {
            music_btn.setImageResource(R.drawable.exit_music_on);
        } else {
            music_btn.setImageResource(R.drawable.exit_music_off);
        }

        String checkvid = Utilities.getString(CategoryQuestionActivity.this, History.IS_VIB, "yes");
        if (checkvid.equalsIgnoreCase("yes")) {
            vibration_btn.setImageResource(R.drawable.exit_vibration_on);
        } else {
            vibration_btn.setImageResource(R.drawable.exit_vibration_off);
        }


        vibration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkvid = Utilities.getString(CategoryQuestionActivity.this, History.IS_VIB, "yes");
                if (checkvid.equalsIgnoreCase("yes")) {
                    vibration_btn.setImageResource(R.drawable.exit_vibration_off);
                    Utilities.saveString(CategoryQuestionActivity.this, History.IS_VIB, "no");
                } else {
                    vibration_btn.setImageResource(R.drawable.exit_vibration_on);
                    Utilities.saveString(CategoryQuestionActivity.this, History.IS_VIB, "yes");
                }
            }
        });


        sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_sound = Utilities.getString(CategoryQuestionActivity.this, History.check_sound, "yes");
                if (check_sound.equalsIgnoreCase("yes")) {
                    backgroundMusic.Stopsound();
                    sound_btn.setImageResource(R.drawable.exit_volume_off);
                    Utilities.saveString(CategoryQuestionActivity.this, History.check_sound, "no");
                } else {
                    sound_btn.setImageResource(R.drawable.exit_volume_on);
                    Utilities.saveString(CategoryQuestionActivity.this, History.check_sound, "yes");
                }
            }
        });

        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_sound = Utilities.getString(CategoryQuestionActivity.this, History.check_music, "yes");
                if (check_sound.equalsIgnoreCase("yes")) {
                    backgroundMusic.Stopmusic_background();
                    music_btn.setImageResource(R.drawable.exit_music_off);
                    Utilities.saveString(CategoryQuestionActivity.this, History.check_music, "no");
                } else {
                    music_btn.setImageResource(R.drawable.exit_music_on);
                    Utilities.saveString(CategoryQuestionActivity.this, History.check_music, "yes");
                    backgroundMusic.playmusic_background(CategoryQuestionActivity.this, "during_game.mp3");
                }
            }
        });


        your_money.setText(String.valueOf(current_question_count) + "/20");
        takemoney_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogexit.dismiss();


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
//        Intent intent = new Intent(CategoryQuestionActivity.this, GameOverActivity.class);
//        intent.putExtra("current_money", String.valueOf(currentMoney));
//        intent.putExtra("question_type", QuestionType);
//        intent.putExtra("game_type", game_type);
        categoryLevelModel = Paper.book().read("categoryLevelModel", null);
        String totalcorrect_ans = "total_correct_ans" + categoryLevelModel.getId() + String.valueOf(Paper.book().read("categoryListModel_pos", "0"));
        Paper.book().write(totalcorrect_ans, String.valueOf(total_correct_ans));


//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
        finish();
    }

    private void wrong_ans_dialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.wrong_ans_dialog);


        TextView okbtn = (TextView) dialog.findViewById(R.id.okbtn);



        okbtn.setText("RETURN TO LEVELS");
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                to_resultscreen();
            }
        });




        dialog.show();
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
        main_screen = findViewById(R.id.main_screen);
        current_question = findViewById(R.id.current_question);
        question_number_main = findViewById(R.id.question_number_main);
        catg_name = findViewById(R.id.catg_name);
        cat_name_bg = findViewById(R.id.cat_name_bg);

    }

    private void getquestions() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);

        final TextView cat_name = (TextView) dialog.findViewById(R.id.cat_name);
        final TextView text = (TextView) dialog.findViewById(R.id.text);
        final ImageView cat_icon = (ImageView) dialog.findViewById(R.id.cat_icon);
        cat_name.setText(categoryListModel.getCat_name());
        cat_icon.setImageResource(categoryListModel.getCat_icon());
        text.setText(String.valueOf(categoryLevelModel.getId()));

        dialog.show();
        int question = questionAsked;
        if (question != 20)
            question = question + 1;
        final StringRequest RegistrationRequest = new StringRequest(Request.Method.GET, "https://opentdb.com/api.php?amount=" + question + "&category=" + cat_id + "&encode=base64", new Response.Listener<String>() {
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

                            String type_base64 = jsonObject.getString("type");
                            byte[] datatype = Base64.decode(type_base64, Base64.DEFAULT);
                            String type = new String(datatype, StandardCharsets.UTF_8);


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
                            questionsModelArrayList.add(new QuestionsModel(question, type, correct_answer, incorrect_ansr));
                        }

//                                      Timed Game Setup

                        answer_msg.setVisibility(View.GONE);
                        question_number_main.setVisibility(View.VISIBLE);

                        Integer[] arr;
                        question_counter = 0;
                        if (questionsModelArrayList.get(0).getType().equalsIgnoreCase("boolean")) {
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

                        if (questionsModelArrayList.get(0).getType().equalsIgnoreCase("multiple")) {

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
                        if (questionsModelArrayList.get(0).getType().equalsIgnoreCase("boolean")) {
                            question_c.setVisibility(View.GONE);
                            question_d.setVisibility(View.GONE);


                        } else {
                            question_c.setVisibility(View.VISIBLE);
                            question_d.setVisibility(View.VISIBLE);

                        }


                    } else {

                        Toast.makeText(CategoryQuestionActivity.this, "No Question Available..!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CategoryQuestionActivity.this, message, Toast.LENGTH_LONG).show();


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
                if (Utils.isInternetAvailable_for_popup(CategoryQuestionActivity.this)) {
                    dialogexit.dismiss();
                } else {
                    Toast.makeText(CategoryQuestionActivity.this, "Internet not available", Toast.LENGTH_SHORT).show();
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