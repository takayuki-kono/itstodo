package jp.codefun.itstodo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static jp.codefun.itstodo.Main2Activity.lists;


public class Main3Activity extends AppCompatActivity {
    //部品の変数
    EditText showDate;
    EditText showOclock1;
    EditText showOclock2;
    EditText taskName;
    EditText taskRevive;
    CheckBox maitsuki;

    String taskName1;
    String taskStart1;
    String taskBy1;
    String taskDate1;
    String taskRevive1;

    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    //編集フラグ
    public static boolean editFlg;
    //編集タスクNo
    public static int editTaskNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        setCalender();
        //部品の取得
        taskName = (EditText) findViewById(R.id.editText);
        showOclock1 = (EditText) findViewById(R.id.editText4);
        showOclock2 = (EditText) findViewById(R.id.editText5);
        taskRevive = (EditText) findViewById(R.id.editText6);
        setOclock(this, showOclock1);
        setOclock(this, showOclock2);
        maitsuki = findViewById(R.id.checkBox);

        if(editFlg){
            taskName.setText(lists[editTaskNo][0]);
            showOclock1.setText(lists[editTaskNo][1]);
            showOclock2.setText(lists[editTaskNo][2]);
            showDate.setText(lists[editTaskNo][3]);
            if(lists[editTaskNo][4].equals("71144422")){
                maitsuki.setChecked(true);
            }else{
                taskRevive.setText(lists[editTaskNo][4]);
            }
            Button button = findViewById(R.id.button);
            button.setText("更新");
        }
    }

    public void insert(View view){

        taskName1 = taskName.getText().toString();
        taskStart1 = showOclock1.getText().toString();
        taskBy1 = showOclock2.getText().toString();
        taskDate1 = showDate.getText().toString();
        taskRevive1 = taskRevive.getText().toString();


        if(check()){
            // プリファレンスの準備 //
            SharedPreferences pref =
                    this.getSharedPreferences( "taskList", Context.MODE_PRIVATE );

            // プリファレンスに書き込むためのEditorオブジェクト取得 //
            SharedPreferences.Editor editor = pref.edit();
            boolean insertFlg = false;
            for(int i = 1;i <= 20;i++){
                insertFlg = pref.getBoolean( "exist" + i,false) ? false : true;
                //編集フラグが真であれば、editTaskNoで上書きする。
                if(editFlg){
                    editFlg = false;
                    insertFlg = true;
                    i = editTaskNo + 1;
                }

                if(insertFlg){
                    String taskStart1 = showOclock1.getText().toString();
                    String taskBy1 = showOclock2.getText().toString();
                    String taskDate1 = showDate.getText().toString();
                    boolean startIsEmpty = taskStart1.isEmpty();
                    boolean byIsEmpty = taskBy1.isEmpty();
                    boolean dateIsEmpty = taskDate1.isEmpty();

                    editor.putBoolean( "exist" + i, true );
                    editor.putString( "taskName" + i, taskName.getText().toString() );
                    if(!startIsEmpty){
                        editor.putString( "taskStart" + i, taskStart1);
                    }else{
                        editor.putString( "taskStart" + i, "00 : 00" );
                    }
                    if(!byIsEmpty){
                        editor.putString( "taskBy" + i, taskBy1);
                    }else{
                        editor.putString( "taskBy" + i, "23 : 59" );
                    }
                    //日付空白
                    if(taskDate1.isEmpty()){
                        //日付跨ぎ
                        if(isOverMidnight()){
                            //現在時刻が日付跨ぎ
                            if(!isPast(taskBy1)){
                                setYesterday(editor, "taskDate" + i);;
                            }else{
                                setToday(editor, "taskDate" + i);
                            }
                        }else if(isPast(taskBy1) && isPast(taskStart1)){
                            setTommorow(editor, "taskDate" + i);
                        }else{
                            setToday(editor, "taskDate" + i);
                        }
//                        if( (!byIsEmpty)
//                                && !isPast(taskBy1) ){
//                            setToday(editor, "taskDate" + i);
//                        }else if(byIsEmpty
//                                    && isPast(taskBy1)
//                                    && isPast(taskStart1) ){
//                            setTommorow(editor, "taskDate" + i);
//                        }else{
//                            setToday(editor, "taskDate" + i);
//                        }
                    }else{
                        editor.putString( "taskDate" + i, taskDate1);
                    }
                    editor.putString( "taskRevive" + i, taskRevive.getText().toString() );
                    maitsuki = findViewById(R.id.checkBox);
                    if(maitsuki.isChecked()){
                        // 月末にチェックが入ってる場合71144422で疎通
                        editor.putString( "taskRevive" + i, "71144422" );
//                        setGetsumatsu(editor, "taskDate" + i);
                    }
                    break;
                }
            }
            if(!insertFlg){
                //ダイアログ作成
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("タスクがいっぱいです。");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                    }
                });
                builder.setCancelable(false);
                builder.show();
                return;
            }
            editor.apply();
            editor.commit();
            //通知フラグオン
//            test_service.tuuchiFlg = true;
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

    }

//    public void loadLists(Context context){
//        // プリファレンスの準備 //
//        SharedPreferences pref =
//                context.getSharedPreferences( "taskList", Context.MODE_PRIVATE );
//
//        String tasks[] = new String[5];
//        for(int i = 1;i <= 10;i++){
//            if(pref.getBoolean( "exist" + i,false)){
//                tasks[0] = pref.getString("taskName" + i, "");
//                tasks[1] = pref.getString("taskStart" + i, "");
//                tasks[2] = pref.getString("taskBy" + i, "");
//                tasks[3] = pref.getString("taskDate" + i, "");
//                tasks[4] = pref.getString("taskRevive" + i, "");
//                lists[i] = tasks;
//            }
//        }
//    }


    public boolean check(){
        String taskReviveStr = taskRevive.getText().toString();

        taskBy1 = showOclock2.getText().toString();
        taskDate1 = showDate.getText().toString();

        if(taskName1.isEmpty()){
            showDialogEmpty("タスク名");
//        }else if(!taskReviveStr.isEmpty()
//                    && (taskStart1.isEmpty() || taskBy1.isEmpty())){
//            showDialogEmpty("実行可能時刻を");
        }else if(!formatCheck()){
            showDialogValid("日付・時間", "正確に");
        }else {
            return true;
        }
        return false;
    }

    public boolean isNum(String text){
        boolean result = true;
        for(int i=0;i<text.length();i++){
            //i文字めの文字についてCharacter.isDigitメソッドで判定する
            if(Character.isDigit(text.charAt(i))) {
                //数字の場合は次の文字の判定へ
                continue;
            }else {
                //数字でない場合は検証結果をfalseに上書きする
                result =false;
                break;
            }
        }
        return result;
    }

    public void showDialogEmpty(String text){
        //ダイアログ作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text + "入力してください");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
            }
        });
        builder.setCancelable(false);
        builder.show();

    }

    public void showDialogValid(String text1, String text2){
        //ダイアログ作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text1 + "は" + text2 + "入力してください");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
            }
        });
        builder.setCancelable(false);
        builder.show();

    }

    public void setOclock(final Context context, final EditText showOclock){

        //EditTextにリスナーをつける
        showOclock.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //Calendarインスタンスを取得
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        context,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                //setした日付を取得して表示
                                showOclock.setText(String.format("%02d : %02d", hourOfDay,minute));
                            }
                        },
                        hour,minute,true);
                dialog.show();
            }
        });
    }

    public void setCalender(){
        //部品の取得
        showDate = (EditText) findViewById(R.id.editText3);

        //EditTextにリスナーをつける
        showDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //Calendarインスタンスを取得
                final Calendar date = Calendar.getInstance();

                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Main3Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得して表示
                                showDate.setText(String.format("%d / %02d / %02d", year, month+1, dayOfMonth));
                            }
                        },
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DATE)
                );

                //dialogを表示
                datePickerDialog.show();

            }
        });
    }

    public boolean isPast(String timeMin){
        if(timeMin.isEmpty()){
            return false;
        }
        int time = Integer.parseInt(timeMin.substring(0,2));
        int min = Integer.parseInt(timeMin.substring(5,7));
        long time_now = date.getHours();
        if(time < time_now){
            return true;
        }
        if(time == date.getHours() && min < date.getMinutes()){
            return true;
        }
        return false;
    }

    public void setTommorow(SharedPreferences.Editor editor, String key){
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tommorow = cal.getTime();
        editor.putString( key,
                new SimpleDateFormat("yyyy / MM / dd", Locale.US).format(tommorow) );;
    }

    public void setYesterday(SharedPreferences.Editor editor, String key){
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        editor.putString( key,
                new SimpleDateFormat("yyyy / MM / dd", Locale.US).format(yesterday) );;
    }

    public void setToday(SharedPreferences.Editor editor, String key){
        editor.putString( key,
                new SimpleDateFormat("yyyy / MM / dd", Locale.US).format(date) );;
    }

    public boolean formatCheck() {

        if (taskStart1.length() != 0) {
            if (taskStart1.length() != 7) {
                return false;
            } else {
                //一文字ずつ先頭から確認する。for文は文字数分繰り返す
                for (int i = 0; i < 7; i++) {

                    if (i == 2 || i == 3 || i == 4) {
                        continue;
                    }
                    //i文字めの文字についてCharacter.isDigitメソッドで判定する
                    if (Character.isDigit(taskStart1.charAt(i))) {

                        //数字の場合は次の文字の判定へ
                        continue;

                    } else {

                        //数字でない場合は検証結果をfalseに上書きする
                        return false;
                    }
                }

            }
        }

        if (taskBy1.length() != 0) {
            if (taskBy1.length() != 7) {
                return false;
            } else {
                //一文字ずつ先頭から確認する。for文は文字数分繰り返す
                for (int i = 0; i < 7; i++) {

                    if (i == 2 || i == 3 || i == 4) {
                        continue;
                    }
                    //i文字めの文字についてCharacter.isDigitメソッドで判定する
                    if (Character.isDigit(taskBy1.charAt(i))) {

                        //数字の場合は次の文字の判定へ
                        continue;

                    } else {

                        //数字でない場合は検証結果をfalseに上書きする
                        return false;
                    }
                }

            }
        }

        if (taskDate1.length() != 0) {
            if (taskDate1.length() != 14) {
                return false;
            } else {
                //一文字ずつ先頭から確認する。for文は文字数分繰り返す
                for (int i = 0; i < 14; i++) {

                    if (i == 4 || i == 5 || i == 6 || i == 9 || i == 10 || i == 11) {
                        continue;
                    }
                    //i文字めの文字についてCharacter.isDigitメソッドで判定する
                    if (Character.isDigit(taskDate1.charAt(i))) {

                        //数字の場合は次の文字の判定へ
                        continue;

                    } else {

                        //数字でない場合は検証結果をfalseに上書きする
                        return false;
                    }
                }

            }
        }

        return true;

    }

    public boolean isOverMidnight(){
        if(taskStart1.isEmpty() || taskBy1.isEmpty()){
            return false;
        }
        int sttime = Integer.parseInt(taskStart1.substring(0,2));
        int stmin = Integer.parseInt(taskStart1.substring(5,7));
        int bytime = Integer.parseInt(taskBy1.substring(0,2));
        int bymin = Integer.parseInt(taskBy1.substring(5,7));
        if(sttime > bytime){
            return true;
        }
        if(sttime == bytime && stmin > bymin){
            return true;
        }
        return false;

    }

    public void setGetsumatsu(SharedPreferences.Editor editor, String key){
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        Date getsumatsu = cal.getTime();
        editor.putString( key,
                new SimpleDateFormat("yyyy / MM / dd", Locale.US).format(getsumatsu) );;
    }
}

