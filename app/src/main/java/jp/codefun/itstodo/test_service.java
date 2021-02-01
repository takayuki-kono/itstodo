package jp.codefun.itstodo;

import android.*;

import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

//import static jp.codefun.itstodo.MainActivity.builder;
//import static jp.codefun.itstodo.MainActivity.lists;
//import static jp.codefun.itstodo.MainActivity.task;
//import static jp.codefun.itstodo.MainActivity.timer;
//import static jp.codefun.itstodo.MainActivity.todoLists;

public class test_service extends android.app.Service{

    public static boolean tuuchiFlg;//スレッドを停止させるフラグ
    static boolean flag;//スレッドを停止させるフラグ

    public android.os.IBinder onBind(android.content.Intent intent)    {
        return null;
    }

    public void onCreate()    {
        Thread th;
        th=new Thread(new test_thread());
        th.start();
    }

    private class test_thread implements Runnable {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            flag=true;
            android.media.ToneGenerator tg;
//            tg = new android.media.ToneGenerator(android.media.AudioManager.STREAM_SYSTEM, android.media.ToneGenerator.MAX_VOLUME);

            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyApp::MyWakelockTag");
            wakeLock.acquire();

            while(flag) {
                try {
//                    tg.startTone(android.media.ToneGenerator.TONE_SUP_ERROR,50);
//


//                    MainActivity.manager = NotificationManagerCompat.from(this);

//                    NotificationChannel channel = new NotificationChannel(
//                            // アプリでユニークな ID
//                            "itstodo_0_",
//                            // ユーザが「設定」アプリで見ることになるチャンネル名
//                            "itstodo",
//                            // チャンネルの重要度（0 ~ 4）
//                            NotificationManager.IMPORTANCE_DEFAULT
//                    );
//                    // 通知時のライトの色
//                    channel.setLightColor(Color.BLUE);
//                    // ロック画面で通知を表示するかどうか
//                    channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//                    // 端末にチャンネルを登録し、「設定」で見れるようにする
//                    MainActivity.manager.createNotificationChannel(channel);

                    MainActivity.getDataList();


//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "itstodo_0_")
//                            .setSmallIcon(R.drawable.droid)
////                          .setContentTitle("itstodo")
////                          .setContentText("Much longer text that cannot fit one line...")
//                            .setStyle(new NotificationCompat.BigTextStyle()
//                                    .bigText(names));
////                          .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//
//                    // notificationId is a unique int for each notification that you must define
//                    MainActivity.manager.notify(1, builder.build());

//                  Timer timer = new Timer(); // 今回追加する処w理
//                  TimerTask task = new TimerTask() {
//                        int count = 0;
//                        public void run() {
//                            // 定期的に実行したい処理
//                            count++;
                    if(MainActivity.todonum_ == 0 && MainActivity.todonum == 0){
                        MainActivity.manager.cancelAll();
                    }else if(
//                            tuuchiFlg ||
                                MainActivity.todonum != MainActivity.todonum_bk ||
                                MainActivity.todonum_ != MainActivity.todonum_bk2){

                        StringBuilder names = new StringBuilder();
                        int count2 = 0;
//                        ArrayList<Integer> todoLists2 = new ArrayList<Integer>();
//                        todoLists2.addAll(MainActivity.todoLists);
//                        todoLists2.addAll(MainActivity.todoLists_);

//                        for(int i : todoLists2){
//
//                            if(count2 > 0){
//                                names.append("\n");
//                            }
//                            names.append(MainActivity.lists[i-1][0]);
//                            count2++;
//                        }
//
                        for(int i : MainActivity.todoLists){

                            if(count2 > 0){
                                names.append("\n");
                            }
                            names.append(MainActivity.lists[i-1][0]);
                            count2++;
                        }

                        for(int i : MainActivity.todoLists_){

                            if(count2 > 0){
                                names.append("\n");
                            }
                            names.append(MainActivity.lists[i-1][0]);
                            count2++;
                        }

                        MainActivity.builder.setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(names));

                        MainActivity.manager.cancelAll();
                        MainActivity.manager.notify(1, MainActivity.builder.build());
//                        //通知フラグオフ
//                        tuuchiFlg = false;

//                        timer.scheduleAtFixedRate(task, 0, 900000); // 今回追加する処理
//
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        Intent intent = new Intent(test_service.this, MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getApplication().startActivity(intent);

                    }
                    MainActivity.todonum_bk2 = MainActivity.todonum_;
                    MainActivity.todonum_bk = MainActivity.todonum;

//                        }
//                    };
//                    MainActivity.timer.scheduleAtFixedRate(task,60000,60000); // 今回追加する処理
                    Thread.sleep(60000);


                } catch (Exception e) {}

            }

        }
    }
}