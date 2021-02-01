package jp.codefun.itstodo;

import androidx.annotation.RequiresApi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Calendar;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/*
 * todo画面
 *
 */

public class MainActivity extends AppCompatActivity {

    static final int MAXDATA = 20;

    public static Map<String, String> data;
    public static List<Map<String, String>> dataList;
    public static ListView listView;
    public static ListViewAdapter adapter;
    public static String lists[][];
//    public static String lists[][] = new String[10][6];
    // todoリストの内部番号リスト
    public static ArrayList<Integer> todoLists;
    // 進行形tmpリスト
    public static ArrayList<Integer> todoLists_;
    // Activity用タスク内部番号リスト
    public static ArrayList<Integer> todoLists_bk;
    public static boolean[] exists = {
            false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false
    };
    public static int afterLists;
    public static int todonum;
    public static int todonum_;
    //期限切れbk
    public static int todonum_bk;
    //進行形bk
    public static int todonum_bk2;
    //todobk
    public static int todonum_bk3;
    public static Timer timer; // 今回追加する処w理
    public static NotificationManagerCompat manager;
    public static NotificationCompat.Builder builder;
    public static Date present;
    public static List<Map<String, String>> inDataList;
    public static Context context;
    public static TimerTask task;
    public static int resetnum;
    public static int resetnum_bk;
    public static boolean isNotificationed;
    public static android.content.Intent _intent;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        lists = new String[20][6];

        loadLists(this);

        todoLists_bk = new ArrayList<Integer>();

//        todoLists = new ArrayList<Integer>();

//        final Handler handler = new Handler();
//        final Runnable r = new Runnable() {
//            int count = 0;
//            @Override
//            public void run() {
//                // UIスレッド
//                count++;
//                if (count < 2) { // 5回実行したら終了
//                    return;
//                }
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                handler.postDelayed(this, 10000);
//            }
//        };
//        handler.post(r);


//        lists = new String[10][6];
//        loadLists(this);
//
//        dataList = new ArrayList<Map<String, String>>();
//
//        present = new Date();
//
//        afterLists = 0;
//        todonum = 0;
//
//        // ListViewに表示するためのDATAを作成する
//        int MAXDATA = 10;
//        List<Map<String, String>> outDataList = new ArrayList<Map<String, String>>();
//        inDataList = new ArrayList<Map<String, String>>();
//
//        todoLists = new ArrayList<Integer>();
//        todoLists_ = new ArrayList<Integer>();

//        for (int i = 0; i < MAXDATA; i++) {
//
//            //バグ用
////            if(lists[i][0] != null) {
////                continue;
////            }
//
//            if(lists[i][0] == null) {
//                continue;
//            }
//            if(lists[i] == null) {
//                continue;
//            }
//            if(!exists[i]){
//                continue;
//            }
//
//            if(lists[i][3].isEmpty()) {
//                continue;
//            }
////            if(!lists[i][3].isEmpty()) {
////                continue;
////            }
//
//            int year = Integer.parseInt(lists[i][3].substring(0,4));
//            int month = Integer.parseInt(lists[i][3].substring(7, 9));
//            int date = Integer.parseInt(lists[i][3].substring(12, 14));
//            int startTime = Integer.parseInt(lists[i][1].substring(0, 2));
//            int startMin = Integer.parseInt(lists[i][1].substring(5, 7));
//            int endTime = Integer.parseInt(lists[i][2].substring(0, 2));
//            int endMin = Integer.parseInt(lists[i][2].substring(5, 7));
//            //int endMin = 13;
//
//            Date taskStartTime = new Date(year-1900, month-1, date, startTime, startMin);
//
//            if(present.before(taskStartTime)){
//                continue;
//            }
//
//            Date taskEndTime = new Date(year-1900, month-1, date, endTime, endMin);
//
//            //0時をまたいでいる場合、日付繰り上げ
//            if(taskEndTime.before(taskStartTime)){
//                // Calendarインスタンスを取得
//                final Calendar date1  = Calendar.getInstance();
////            final Calendar date2  = Calendar.getInstance();
//
//                // 日付セット
//                date1.set(year, month, date);
////            date1.set(year, month, date, endTime, endMin);
//
//                // 翌日をセット
//                date1.add(Calendar.DAY_OF_MONTH, 1);
//
//                year = date1.get(Calendar.YEAR);
//                month = date1.get(Calendar.MONTH);
//                date = date1.get(Calendar.DAY_OF_MONTH);
//
//                taskEndTime = new Date(year-1900, month-1, date, endTime, endMin);
//            }
//
//            if(present.after(taskEndTime)){
//                data = new HashMap<String, String>();
//                data.put("text1", lists[i][0]);
//                data.put("text2", lists[i][1]);
//                data.put("text3", lists[i][2]);
//                dataList.add(data);
//                todoLists.add(i+1);
//                afterLists++;
//                todonum++;
//
//            }else{
//                data = new HashMap<String, String>();
//                data.put("text1", lists[i][0]);
//                data.put("text2", lists[i][1]);
//                data.put("text3", lists[i][2]);
//                inDataList.add(data);
//                todoLists_.add(i+1);
//                todonum++;
//            }
////            if(present.before(new Date()));
////            if(present.After(LocalDateTime.parse(lists[i][3] + "T" + lists[i][2]))){
////                data = new HashMap<String, String>();
////                data.put("text1", lists[i][0]);
////                data.put("text2", lists[i][1]);
////                data.put("text3", lists[i][2]);
////                dataList.add(data);
////            }else{
////                data = new HashMap<String, String>();
////                data.put("text1", lists[i][0]);
////                data.put("text2", lists[i][1]);
////                data.put("text3", lists[i][2]);
////                inDataList.add(data);
////            }
//        }
//
//        dataList.addAll(inDataList);
//        todoLists.addAll(todoLists_);

        present = new Date();

        getDataList();
//        MainActivity.todonum_bk = 0;

        dataList.addAll(inDataList);
        todoLists.addAll(todoLists_);
        todoLists_bk.addAll(todoLists);
        todonum_bk3 = todonum;

        // アダプターにデータを渡す
        adapter = new ListViewAdapter(
                this,
                dataList,
                R.layout.raw,
                new String[] { "text1", "text2", "task3" },
                new int[] { android.R.id.text1,
                            android.R.id.text2 ,
                            android.R.id.textAssist});

        // アダプターにDATAをSETする
        listView = (ListView) findViewById(R.id.mainlist);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(false);

        manager = NotificationManagerCompat.from(this);

        NotificationChannel channel = new NotificationChannel(
                // アプリでユニークな ID
                "itstodo_0_",
                // ユーザが「設定」アプリで見ることになるチャンネル名
               "itstodo",
                // チャンネルの重要度（0 ~ 4）
                NotificationManager.IMPORTANCE_DEFAULT
        );
        // 通知時のライトの色
        channel.setLightColor(Color.BLUE);
        // ロック画面で通知を表示するかどうか
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        // 端末にチャンネルを登録し、「設定」で見れるようにする
        manager.createNotificationChannel(channel);

//        NotificationManager manager = (NotificationManager)
//                getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new Notification.Builder(this)
//                .setContentTitle("通知タイトル")
//                .setContentText("通知コンテンツ")
//                .setSmallIcon(R.drawable.ic_notify)
//                .setChannel("channel_1")
//                .build();
//        manager.notify(1, notification);

        StringBuilder names = new StringBuilder();
        int count2 = 0;
        for(int i : todoLists){

            if(count2 > 0){
                names.append("\n");
            }
            names.append(lists[i-1][0]);
            count2++;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(this, "itstodo_0_")
                .setSmallIcon(R.drawable.droid)
//                .setContentTitle("itstodo")
//                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(names))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
//                .setOngoing(true)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//         notificationId is a unique int for each notification that you must define
//        manager.notify(1, builder.build());

//      pc用
        if(timer != null){
            timer.cancel();
            timer = null;
        }

        timer = new Timer();

        task = new TimerTask() {
            //                int count = 0;

            public void run() {
                // 定期的に実行したい処理
                //                    count++;
                //                if(count == 15){
                //                    finish();
                getDataList();

                if (todonum != todonum_bk3) {
                    todonum_bk3 = todonum;

                    //通知
                    //                        MainActivity.manager.notify(1, builder.build());
                    //todo画面更新
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    //                        MainActivity.todonum_bk = MainActivity.todonum;

                }

            }
        };
        timer.scheduleAtFixedRate(task, 900000, 900000); // 今回追加する処理


        manager.cancelAll();

        if(todonum_ != 0 || todonum != 0){
            manager.notify(1, MainActivity.builder.build());
        }
        MainActivity.todonum_bk = MainActivity.todonum;
        MainActivity.todonum_bk2 = MainActivity.todonum_;

        //通地フラグオン
//        test_service.tuuchiFlg = true;

        //            getApplicationContext().stopService(_intent);
        if(_intent == null){
            _intent=new android.content.Intent(getApplicationContext(), test_service.class);
            getApplicationContext().startService(_intent);
        }

    }

    public void shiftInsert(View view){
        Main3Activity.editFlg = false;

        startActivity(new Intent(getApplicationContext(), Main3Activity.class));

    }

    public void shiftTaskList(View view){
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
    }

    public void koushin(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void loadLists(Context context){
        // プリファレンスの準備 //
        SharedPreferences pref =
                context.getSharedPreferences( "taskList", Context.MODE_PRIVATE );

//        String tasks[] = new String[6];

        for(int i = 1;i <= 20;i++){
            if(pref.getBoolean( "exist" + i,false)){
                exists[i-1] = true;
                lists[i-1][0] = pref.getString("taskName" + i, "");
                lists[i-1][1] = pref.getString("taskStart" + i, "");
                lists[i-1][2] = pref.getString("taskBy" + i, "");
                lists[i-1][3] = pref.getString("taskDate" + i, "");
                lists[i-1][4] = pref.getString("taskRevive" + i, "");
                lists[i-1][5] = "" + i;
//                lists[i-1] = tasks;
            }
        }
    }


//    public class ListViewAdapter extends SimpleAdapter {
//
//        private LayoutInflater inflater;
//        private List<? extends Map<String, ?>> listData;
//
//        // 各行が保持するデータ保持クラス
//        public class ViewHolder {
//            TextView text1;
//            TextView text2;
//            TextView text3;
//        }
//
//        public ListViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
//            super(context, data, resource, from, to);
//            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            this.listData = data;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final ViewHolder holder;
//
//            // ビューを受け取る
//            View view = convertView;
//
//            if (view == null) {
//                // 画面起動時にViewHolderを作成する
//                view = inflater.inflate(R.layout.raw, parent, false);
//
//                holder = new ViewHolder();
//                holder.text1 = (TextView) view.findViewById(R.id.text1);
//                holder.text2 = (TextView) view.findViewById(R.id.text2);
//                holder.text3 = (TextView) view.findViewById(R.id.text3);
//
//                view.setTag(holder);
//            } else {
//                // 行選択時などは既に作成されているものを取得する
//                holder = (ViewHolder) view.getTag();
//            }
//
//            // holderにデータをセットする
//            String text1 = ((HashMap<?, ?>) listData.get(position)).get("text1").toString();
//            String text2 = ((HashMap<?, ?>) listData.get(position)).get("text2").toString();
//            String text3 = ((HashMap<?, ?>) listData.get(position)).get("text3").toString();
//            holder.text1.setText(text1);
//            holder.text2.setText(text2);
//            holder.text3.setText(text2);
//
//            // セル上にあるボタンの処理
//            Button btn = (Button) view.findViewById(R.id.radioButton);
//            btn.setTag(position);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    // 選択したセルの文字を赤色にする
//                    holder.text1.setTextColor(Color.RED);
//                }
//            });
//
//            return view;
//        }
//    }

    public void delete (View view){

//        getDataList();
//
//        if(todonum_ != todonum_bk){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            return;
//        }

        //通地フラグオン
//        test_service.tuuchiFlg = true;

        // リストビューの項目番号
        int position = (Integer)view.getTag();
        // そのタスクの内部番号
        int taskNo = todoLists_bk.get(position);
        // プリファレンスの準備 //
        SharedPreferences pref =
                this.getSharedPreferences( "taskList", Context.MODE_PRIVATE );
        // プリファレンスに書き込むためのEditorオブジェクト取得 //
        SharedPreferences.Editor editor = pref.edit();

        // タスクの繰り返し時間がない場合、そのタスクを消す。
        if(lists[taskNo-1][4].isEmpty()) {
            editor.putBoolean("exist" + taskNo, false);

        }else if(Integer.parseInt(lists[taskNo-1][4]) == 71144422){
            // 月末にチェックが入ってる場合
            Calendar cal = Calendar.getInstance();

            int year = Integer.parseInt(lists[taskNo-1][3].substring(0,4));
            int month = Integer.parseInt(lists[taskNo-1][3].substring(7, 9));
            int date = Integer.parseInt(lists[taskNo-1][3].substring(12, 14));

            cal.set(year, month-1, date);
            if(date != cal.getActualMaximum(Calendar.DATE)){
                //既に月末日でない場合、月末日に
                date = cal.getActualMaximum(Calendar.DATE);

            }else {
                //月末の場合、次の月の月末日
                if (month == 12) {
                    year++;
                    month = 1;
                } else {
                    month++;
                }
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    date = 31;
                } else if (month == 2) {
                    if (year % 4 == 0) {
                        date = 29;
                    } else {
                        date = 28;
                    }
                } else {
                    date = 30;
                }
            }
            editor.putString( "taskDate" + taskNo, year + " / " + fillZero(month) + " / " + date);

        }else{
            // 繰り返しがある場合、開始終了を繰り上げ
            int revive = Integer.parseInt(lists[taskNo-1][4]);

            int year = Integer.parseInt(lists[taskNo-1][3].substring(0, 4));
            //月は0~11の形で引き出されるので-1
            int month = Integer.parseInt(lists[taskNo-1][3].substring(7, 9)) - 1;
            int date = Integer.parseInt(lists[taskNo-1][3].substring(12, 14));
            int startTime = Integer.parseInt(lists[taskNo-1][1].substring(0, 2));
            int startMin = Integer.parseInt(lists[taskNo-1][1].substring(5, 7));
            int endTime = Integer.parseInt(lists[taskNo-1][2].substring(0, 2));
            int endMin = Integer.parseInt(lists[taskNo-1][2].substring(5, 7));

            // Calendarインスタンスを取得
            Calendar date1  = Calendar.getInstance();
            Calendar date2  = Calendar.getInstance();
            Calendar cal  = Calendar.getInstance();
//            final Calendar date2  = Calendar.getInstance();

            // 開始時刻を1にセット
            date1.set(year, month, date, startTime, startMin);
//            date1_bk.set(year, month, date, startTime, startMin);
            date2.set(year, month, date, endTime, endMin);
//            date1.set(year, month, date, endTime, endMin);
            if(date2.before(date1)){
                date2.add(Calendar.DAY_OF_MONTH, 1);
            }

            date1.add(Calendar.HOUR_OF_DAY, revive);
//            date1_bk.add(Calendar.HOUR_OF_DAY, revive);
            date2.add(Calendar.HOUR_OF_DAY, revive);


            endTime = (endTime + revive)% 24;

            boolean isPast = true;
            Date date3 = new Date();
            cal.setTime(date3);

            int count = 0;

//            date3.set(year, month, date, endTime, endMin);
            //繰り上げてなおタスク期限切れの場合、終了が未来になるまで繰り上げ
            if(date2.before(cal)){
                while(isPast){
                    // 繰り返し時間分開始時刻を繰り上げ
                    date1.add(Calendar.HOUR_OF_DAY, revive);
                    date2.add(Calendar.HOUR_OF_DAY, revive);
                    count++;
                    if(date2.after(cal)){
//                        date1 = date1_bk;
                        isPast = false;
                        endTime = (endTime + revive * count)% 24;

//                    }else{
//                        date1_bk.add(Calendar.HOUR_OF_DAY, revive);
//                        count++;
                    }

                }

            }

            // 繰り上げた分でセット
            year = date1.get(Calendar.YEAR);
            //月は0~11の形で引き出される
            month = date1.get(Calendar.MONTH) +1;
            date = date1.get(Calendar.DAY_OF_MONTH);
            startTime = date1.get(Calendar.HOUR_OF_DAY);
            startMin = date1.get(Calendar.MINUTE);

            editor.putString( "taskStart" + taskNo, fillZero(startTime) + " : " + fillZero(startMin));
            editor.putString( "taskBy" + taskNo, fillZero(endTime) + " : " + fillZero(endMin));
            editor.putString( "taskDate" + taskNo, year + " / " + fillZero(month) + " / " + fillZero(date));

        }

        editor.apply();
        editor.commit();

        // 画面再起動
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

//    public static Date dateCal(int year,int month,int date,int startTime,int startMin,int revive){
//
//
//
//        Date date1 = new Date(year-1900, month-1, date, endTime, endMin);
//
//        return date1;
//
//    }

    public static String fillZero(int num){
        String zeroNum = num < 10 ? "0" + num : "" + num;
        return zeroNum;
    }

//    public static int tranMonth(int year, int month){
//        int monthT = 0;
//        switch(month) {
//            case 12:
//                monthT += 30;
//            case 11:
//                monthT += 31;
//            case 10:
//                monthT += 30;
//            case 9:
//                monthT += 31;
//            case 8:
//                monthT += 31;
//            case 7:
//                monthT += 30;
//            case 6:
//                monthT += 31;
//            case 5:
//                monthT += 30;
//            case 4:
//                monthT += 31;
//            case 3:
//                monthT += 28;
//            case 2:
//                monthT += 31;
//        }
//        if(year % 4 == 0) {
//            monthT++;
//        }
//
//        return monthT;
//
//    }

//    public static int tranYear(int year){
//        int yearT = 0;
//        year / 4
//    }

    public static void getDataList(){

        dataList = new ArrayList<Map<String, String>>();

        afterLists = 0;
        todonum = 0;
        todonum_ = 0;

        // ListViewに表示するためのDATAを作成する
        List<Map<String, String>> outDataList = new ArrayList<Map<String, String>>();
        inDataList = new ArrayList<Map<String, String>>();

        todoLists = new ArrayList<Integer>();
        todoLists_ = new ArrayList<Integer>();


        for (int i = 0; i < MAXDATA; i++) {

            //バグ用
//            if(lists[i][0] != null) {
//                continue;
//            }

            if(lists[i][0] == null) {
                continue;
            }
            if(lists[i] == null) {
                continue;
            }
            if(!exists[i]){
                continue;
            }

            if(lists[i][3].isEmpty()) {
                continue;
            }
//            if(!lists[i][3].isEmpty()) {
//                continue;
//            }

            int year = Integer.parseInt(lists[i][3].substring(0,4));
            int month = Integer.parseInt(lists[i][3].substring(7, 9));
            int date = Integer.parseInt(lists[i][3].substring(12, 14));
            int startTime = Integer.parseInt(lists[i][1].substring(0, 2));
            int startMin = Integer.parseInt(lists[i][1].substring(5, 7));
            int endTime = Integer.parseInt(lists[i][2].substring(0, 2));
            int endMin = Integer.parseInt(lists[i][2].substring(5, 7));
            //int endMin = 13;

            Date taskStartTime = new Date(year-1900, month-1, date, startTime, startMin);

            if(present.before(taskStartTime)){
                continue;
            }

            Date taskEndTime = new Date(year-1900, month-1, date, endTime, endMin);

            //0時をまたいでいる場合、日付繰り上げ
            if(taskEndTime.before(taskStartTime)){
                // Calendarインスタンスを取得
                final Calendar date1  = Calendar.getInstance();
//            final Calendar date2  = Calendar.getInstance();

                // 日付セット
                date1.set(year, month, date);
//            date1.set(year, month, date, endTime, endMin);

                // 翌日をセット
                date1.add(Calendar.DAY_OF_MONTH, 1);

                year = date1.get(Calendar.YEAR);
                month = date1.get(Calendar.MONTH);
                date = date1.get(Calendar.DAY_OF_MONTH);

                taskEndTime = new Date(year-1900, month-1, date, endTime, endMin);
            }

            if(!present.equals(taskEndTime) && present.after(taskEndTime)){
                data = new HashMap<String, String>();
                data.put("text1", lists[i][0]);
                data.put("text2", lists[i][1]);
                data.put("text3", lists[i][2]);
                dataList.add(data);
                todoLists.add(i+1);
                afterLists++;
                todonum++;

            }else{
                data = new HashMap<String, String>();
                data.put("text1", lists[i][0]);
                data.put("text2", lists[i][1]);
                data.put("text3", lists[i][2]);
                inDataList.add(data);
                todoLists_.add(i+1);
                todonum++;
                todonum_++;
            }
//            if(present.before(new Date()));
//            if(present.After(LocalDateTime.parse(lists[i][3] + "T" + lists[i][2]))){
//                data = new HashMap<String, String>();
//                data.put("text1", lists[i][0]);
//                data.put("text2", lists[i][1]);
//                data.put("text3", lists[i][2]);
//                dataList.add(data);
//            }else{
//                data = new HashMap<String, String>();
//                data.put("text1", lists[i][0]);
//                data.put("text2", lists[i][1]);
//                data.put("text3", lists[i][2]);
//                inDataList.add(data);
//            }
        }

    }

//    public static void finishActivity() {
//        this.finish();
//        this.moveTaskToBack(true);
//    }

//    @Override
//    public void onUserLeaveHint(){
//        //ホームボタンが押された時や、他のアプリが起動した時に呼ばれる
//        //戻るボタンが押された場合には呼ばれない
//        timer.cancel();
//        timer = null;
//    }
}