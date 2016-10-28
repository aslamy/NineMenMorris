package com.benjamin.ninemenmorris;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;


/**
 * Created by Benjamin on 2015-11-27.
 */
public class GameView extends View {

    public static final int RED_MOVE = 2;
    public static final int BLUE_MOVE = 1;
    public static final int EMPTY_SPACE = 0;
    public static final int BLUE_MARKER = 4;
    public static final int RED_MARKER = 5;
    private NineMenMorrisRules gameRole;
    private Paint bgPaint;
    private Paint bgBorderPaint;
    private Paint bgCheckerPaint;
    private Paint redPaint;
    private Paint bluePaint;
    private Paint lightRedPaint;
    private Paint lightBluePaint;
    private Checker[] checkers;
    private TextView textInfo;
    private MainActivity mainActivity;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private int checkerRadius;
    private int borderWidth;
    private int displayW;
    private int displayH;
    private int turn;
    private int margin;
    private int touchedChecker;
    private int oldTouchedChecker;
    private int checkerColor;
    private boolean timeToRemoveChecker = false;
    private boolean gameOver = false;


    public GameView(Context context) {
        super(context);
        gameRole = new NineMenMorrisRules();
        bgPaint = new Paint();
        bgBorderPaint = new Paint();
        bgCheckerPaint = new Paint();
        redPaint = new Paint();
        bluePaint = new Paint();
        lightBluePaint = new Paint();
        lightRedPaint = new Paint();
        checkers = new Checker[25];
        mainActivity = (MainActivity) context;
        textInfo = (TextView) mainActivity.findViewById(R.id.textView);
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences("save_game", Context.MODE_PRIVATE);
        init();
    }

    public void init() {
        bgPaint.setColor(Color.YELLOW);
        bgCheckerPaint.setColor(Color.BLACK);
        bgBorderPaint.setColor(Color.BLACK);
        redPaint.setColor(Color.RED);
        bluePaint.setColor(Color.BLUE);
        lightBluePaint.setColor(Color.parseColor("#FF8899D2"));
        lightRedPaint.setColor(Color.parseColor("#FFF9ACAC"));
        for (int i = 1; i < checkers.length; i++) {
            checkers[i] = new Checker(checkerRadius, bgCheckerPaint);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == 1) {
            margin = canvas.getWidth() / 25;
            displayW = canvas.getWidth() - margin;
            displayH = canvas.getHeight() - margin;
        } else if (orientation == 2) {
            margin = canvas.getHeight() / 25;
            displayW = canvas.getHeight() - margin;
            displayH = canvas.getWidth() - margin;
        }

        checkerRadius = displayW / 30;
        borderWidth = displayW / 70;

        drawGameBoard(canvas);
        drawGameCheckers(canvas);
        drawGameInfo(canvas);

    }

    private void drawGameCheckers(Canvas canvas) {

        int radius = checkerRadius;
        canvas.drawCircle(margin + borderWidth / 2, margin + borderWidth / 2, radius, checkers[3].paint);
        checkers[3].xPosition = margin + borderWidth / 2;
        checkers[3].yPosition = margin + borderWidth / 2;

        canvas.drawCircle(displayW / 2 + margin / 2, margin + borderWidth / 2, radius, checkers[6].paint);
        checkers[6].xPosition = displayW / 2 + margin / 2;
        checkers[6].yPosition = margin + borderWidth / 2;

        canvas.drawCircle(displayW - borderWidth / 2, margin + borderWidth / 2, radius, checkers[9].paint);
        checkers[9].xPosition = displayW - borderWidth / 2;
        checkers[9].yPosition = margin + borderWidth / 2;

        canvas.drawCircle(displayW / 6 + margin + borderWidth / 2, displayW / 6 + margin + borderWidth / 2, radius, checkers[2].paint);
        checkers[2].xPosition = displayW / 6 + margin + borderWidth / 2;
        checkers[2].yPosition = displayW / 6 + margin + borderWidth / 2;

        canvas.drawCircle(displayW / 2 + margin / 2, displayW / 6 + margin + borderWidth / 2, radius, checkers[5].paint);
        checkers[5].xPosition = displayW / 2 + margin / 2;
        checkers[5].yPosition = displayW / 6 + margin + borderWidth / 2;

        canvas.drawCircle(displayW - displayW / 6 - borderWidth / 2, displayW / 6 + margin + borderWidth / 2, radius, checkers[8].paint);
        checkers[8].xPosition = displayW - displayW / 6 - borderWidth / 2;
        checkers[8].yPosition = displayW / 6 + margin + borderWidth / 2;

        canvas.drawCircle(displayW / 3 + margin + borderWidth / 2, displayW / 3 + margin + borderWidth / 2, radius, checkers[1].paint);
        checkers[1].xPosition = displayW / 3 + margin + borderWidth / 2;
        checkers[1].yPosition = displayW / 3 + margin + borderWidth / 2;

        canvas.drawCircle(displayW / 2 + margin / 2, displayW / 3 + margin + borderWidth / 2, radius, checkers[4].paint);
        checkers[4].xPosition = displayW / 2 + margin / 2;
        checkers[4].yPosition = displayW / 3 + margin + borderWidth / 2;

        canvas.drawCircle(displayW - displayW / 3 - borderWidth / 2, displayW / 3 + margin + borderWidth / 2, radius, checkers[7].paint);
        checkers[7].xPosition = displayW - displayW / 3 - borderWidth / 2;
        checkers[7].yPosition = displayW / 3 + margin + borderWidth / 2;

        canvas.drawCircle(margin + borderWidth / 2, displayW / 2 + margin / 2, radius, checkers[24].paint);
        checkers[24].xPosition = margin + borderWidth / 2;
        checkers[24].yPosition = displayW / 2 + margin / 2;

        canvas.drawCircle(displayW / 6 + margin + borderWidth / 2, displayW / 2 + margin / 2, radius, checkers[23].paint);
        checkers[23].xPosition = displayW / 6 + margin + borderWidth / 2;
        checkers[23].yPosition = displayW / 2 + margin / 2;

        canvas.drawCircle(displayW / 3 + margin + borderWidth / 2, displayW / 2 + margin / 2, radius, checkers[22].paint);
        checkers[22].xPosition = displayW / 3 + margin + borderWidth / 2;
        checkers[22].yPosition = displayW / 2 + margin / 2;

        canvas.drawCircle(displayW - displayW / 3 - borderWidth / 2, displayW / 2 + margin / 2, radius, checkers[10].paint);
        checkers[10].xPosition = displayW - displayW / 3 - borderWidth / 2;
        checkers[10].yPosition = displayW / 2 + margin / 2;

        canvas.drawCircle(displayW - displayW / 6 - borderWidth / 2, displayW / 2 + margin / 2, radius, checkers[11].paint);
        checkers[11].xPosition = displayW - displayW / 6 - borderWidth / 2;
        checkers[11].yPosition = displayW / 2 + margin / 2;

        canvas.drawCircle(displayW - borderWidth / 2, displayW / 2 + margin / 2, radius, checkers[12].paint);
        checkers[12].xPosition = displayW - borderWidth / 2;
        checkers[12].yPosition = displayW / 2 + margin / 2;


        canvas.drawCircle(displayW / 3 + margin + borderWidth / 2, displayW - displayW / 3 - borderWidth / 2, radius, checkers[19].paint);
        checkers[19].xPosition = displayW / 3 + margin + borderWidth / 2;
        checkers[19].yPosition = displayW - displayW / 3 - borderWidth / 2;

        canvas.drawCircle(displayW / 2 + margin / 2, displayW - displayW / 3 - borderWidth / 2, radius, checkers[16].paint);
        checkers[16].xPosition = displayW / 2 + margin / 2;
        checkers[16].yPosition = displayW - displayW / 3 - borderWidth / 2;

        canvas.drawCircle(displayW - displayW / 3 - borderWidth / 2, displayW - displayW / 3 - borderWidth / 2, radius, checkers[13].paint);
        checkers[13].xPosition = displayW - displayW / 3 - borderWidth / 2;
        checkers[13].yPosition = displayW - displayW / 3 - borderWidth / 2;

        canvas.drawCircle(displayW / 6 + margin + borderWidth / 2, displayW - displayW / 6 - borderWidth / 2, radius, checkers[20].paint);
        checkers[20].xPosition = displayW / 6 + margin + borderWidth / 2;
        checkers[20].yPosition = displayW - displayW / 6 - borderWidth / 2;

        canvas.drawCircle(displayW / 2 + margin / 2, displayW - displayW / 6 - borderWidth / 2, radius, checkers[17].paint);
        checkers[17].xPosition = displayW / 2 + margin / 2;
        checkers[17].yPosition = displayW - displayW / 6 - borderWidth / 2;

        canvas.drawCircle(displayW - displayW / 6 - borderWidth / 2, displayW - displayW / 6 - borderWidth / 2, radius, checkers[14].paint);
        checkers[14].xPosition = displayW - displayW / 6 - borderWidth / 2;
        checkers[14].yPosition = displayW - displayW / 6 - borderWidth / 2;


        canvas.drawCircle(margin + borderWidth / 2, displayW - borderWidth / 2, radius, checkers[21].paint);
        checkers[21].xPosition = margin + borderWidth / 2;
        checkers[21].yPosition = displayW - borderWidth / 2;

        canvas.drawCircle(displayW / 2 + margin / 2, displayW - borderWidth / 2, radius, checkers[18].paint);
        checkers[18].xPosition = displayW / 2 + margin / 2;
        checkers[18].yPosition = displayW - borderWidth / 2;

        canvas.drawCircle(displayW - borderWidth / 2, displayW - borderWidth / 2, radius, checkers[15].paint);
        checkers[15].xPosition = displayW - borderWidth / 2;
        checkers[15].yPosition = displayW - borderWidth / 2;


    }

    private void drawGameBoard(Canvas canvas) {

        canvas.drawRect(margin, margin, displayW, displayW, bgPaint);

        int rectStartLength = margin;
        int rectStopLength = displayW;
        canvas.drawRect(rectStartLength, rectStartLength, rectStopLength, rectStopLength, bgBorderPaint);
        canvas.drawRect(rectStartLength + borderWidth, rectStartLength + borderWidth, rectStopLength - borderWidth, rectStopLength - borderWidth, bgPaint);

        rectStartLength = displayW / 6 + margin;
        rectStopLength = displayW - (displayW / 6);
        canvas.drawRect(rectStartLength, rectStartLength, rectStopLength, rectStopLength, bgBorderPaint);
        canvas.drawRect(rectStartLength + borderWidth, rectStartLength + borderWidth, rectStopLength - borderWidth, rectStopLength - borderWidth, bgPaint);

        rectStartLength = displayW / 3 + margin;
        rectStopLength = displayW - (displayW / 3);
        canvas.drawRect(rectStartLength, rectStartLength, rectStopLength, rectStopLength, bgBorderPaint);
        canvas.drawRect(rectStartLength + borderWidth, rectStartLength + borderWidth, rectStopLength - borderWidth, rectStopLength - borderWidth, bgPaint);

        canvas.drawRect(margin, displayW / 2 - borderWidth / 2 + margin / 2, displayW / 3 + margin, displayW / 2 + borderWidth / 2 + margin / 2, bgBorderPaint);
        canvas.drawRect(displayW - displayW / 3, displayW / 2 - borderWidth / 2 + margin / 2, displayW, displayW / 2 + borderWidth / 2 + margin / 2, bgBorderPaint);
        canvas.drawRect(displayW / 2 - borderWidth / 2 + margin / 2, margin, displayW / 2 + borderWidth / 2 + margin / 2, displayW / 3 + margin, bgBorderPaint);
        canvas.drawRect(displayW / 2 - borderWidth / 2 + margin / 2, displayW - displayW / 3, displayW / 2 + borderWidth / 2 + margin / 2, displayW, bgBorderPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            for (int i = 1; i < checkers.length; i++) {
                boolean match = checkers[i].inside(event.getX(), event.getY(), checkerRadius);
                if (match && !gameOver) {
                    touchedChecker = i;
                    move();
                    invalidate();
                    break;
                }
            }
        }

        return false;
    }

    public void move() {

        turn = gameRole.getTurn();
        int marker = gameRole.getMarker(turn);
        checkerColor = RED_MARKER;

        if (turn == BLUE_MOVE) {
            checkerColor = BLUE_MARKER;
        }

        if (timeToRemoveChecker) {

            boolean success = gameRole.remove(touchedChecker, checkerColor);
            if (success) {
                checkers[touchedChecker].paint = bgCheckerPaint;
            }
            timeToRemoveChecker = !success;
            return;
        }


        if (marker > 0) {
            boolean success = gameRole.legalMove(touchedChecker, 99999, turn);
            if (success) {
                if (turn == RED_MOVE) {
                    checkers[touchedChecker].paint = redPaint;
                } else if (turn == BLUE_MOVE) {
                    checkers[touchedChecker].paint = bluePaint;
                }
                checkGameResult();
            }
            return;
        }

        if (oldTouchedChecker == 0) {
            int checkerColor = gameRole.board(touchedChecker);
            if (checkerColor == RED_MARKER && turn == RED_MOVE) {
                checkers[touchedChecker].paint = lightRedPaint;
                oldTouchedChecker = touchedChecker;

            } else if (checkerColor == BLUE_MARKER && turn == BLUE_MOVE) {
                checkers[touchedChecker].paint = lightBluePaint;
                oldTouchedChecker = touchedChecker;
            }

            return;
        }

        if (oldTouchedChecker == touchedChecker) {
            int checkerColor = gameRole.board(touchedChecker);
            if (checkerColor == RED_MARKER) {
                checkers[touchedChecker].paint = redPaint;

            } else if (checkerColor == BLUE_MARKER) {
                checkers[touchedChecker].paint = bluePaint;

            }
            oldTouchedChecker = 0;
            return;
        }

        boolean success = gameRole.legalMove(touchedChecker, oldTouchedChecker, turn);
        if (success) {
            if (turn == RED_MOVE) {
                checkers[oldTouchedChecker].paint = bgCheckerPaint;
                checkers[touchedChecker].paint = redPaint;
            } else if (turn == BLUE_MOVE) {
                checkers[oldTouchedChecker].paint = bgCheckerPaint;
                checkers[touchedChecker].paint = bluePaint;
            }
            checkGameResult();
            oldTouchedChecker = 0;
        }
    }

    private void checkGameResult() {

        if (timeToRemoveChecker) {
            return;
        }
        boolean remove = gameRole.remove(touchedChecker);
        if (remove) {
            timeToRemoveChecker = true;
            return;
        }
        timeToRemoveChecker = false;
    }


    private void drawGameInfo(Canvas canvas) {


        if (gameRole.win(BLUE_MARKER)) {
            textInfo.setText("Blue wins! Red has too few pieces!");
            gameOver = true;
            return;
        }
        if (gameRole.win(RED_MARKER)) {
            textInfo.setText("Red Wins! Blue has too few pieces");
            gameOver = true;
            return;
        }


        if (!gameRole.moveLeft(RED_MARKER)) {
            textInfo.setText("Blue Wins!\nRed can't move!");
            gameOver = true;
            return;
        }

        if (!gameRole.moveLeft(BLUE_MARKER)) {
            textInfo.setText("Red Wins!\nBlue can't move!");
            gameOver = true;
            return;
        }

        if (timeToRemoveChecker) {
            if (gameRole.getTurn() == BLUE_MOVE) {
                textInfo.setText("Red remove blue's piece!");

            } else if (gameRole.getTurn() == RED_MOVE) {
                textInfo.setText("Blue remove red's piece!");
            }
            return;
        }

        if (gameRole.getTurn() == BLUE_MOVE) {
            textInfo.setText("Blue player's turn!");
        } else if (gameRole.getTurn() == RED_MOVE) {
            textInfo.setText("Red player's turn!");
        }
    }


    public void saveGame() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nineMenMorrisRules", gson.toJson(gameRole));
        editor.commit();
    }

    public void loadGame() {
        String json = sharedPreferences.getString("nineMenMorrisRules", null);
        gameRole = gson.fromJson(json, NineMenMorrisRules.class);

        if (gameRole == null) {
            gameRole = new NineMenMorrisRules();
        }
        int[] gamePlan = gameRole.getGameplan();

        for (int i = 1; i < gamePlan.length; i++) {
            if (gamePlan[i] == EMPTY_SPACE) {
                checkers[i] = new Checker(checkerRadius, bgCheckerPaint);
            } else if (gamePlan[i] == BLUE_MARKER) {
                checkers[i] = new Checker(checkerRadius, bluePaint);
            } else if (gamePlan[i] == RED_MARKER) {
                checkers[i] = new Checker(checkerRadius, redPaint);
            }
        }
        invalidate();
    }

    public void resetGame() {
        turn= 0;
        touchedChecker = 0;
        oldTouchedChecker = 0;
        checkerColor = 0;
        timeToRemoveChecker = false;
        gameOver = false;
        gameRole = new NineMenMorrisRules();
        for (int i = 1; i < checkers.length; i++) {
            checkers[i] = new Checker(checkerRadius, bgCheckerPaint);
        }
        invalidate();
    }


    private class Checker  {

        private int radius;
        private Paint paint;
        private int xPosition;
        private int yPosition;

        public Checker(int radius, Paint paint) {
            this.radius = radius;
            this.paint = paint;
        }

        public boolean inside(float x, float y, int radius) {

            float xlimitP = xPosition + 2 * radius;
            float xlimitN = xPosition - 2 * radius;

            float ylimitP = yPosition + 2 * radius;
            float ylimitN = yPosition - 2 * radius;

            if (x <= xlimitP && x >= xlimitN && y <= ylimitP && y >= ylimitN) {
                return true;
            }
            return false;
        }
    }
}
