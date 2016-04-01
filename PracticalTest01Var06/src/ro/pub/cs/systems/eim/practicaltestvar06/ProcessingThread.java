package ro.pub.cs.systems.eim.practicaltestvar06;

import java.util.Date;
import java.util.Random;
 
import android.content.Context;
import android.content.Intent;
import android.util.Log;
 
public class ProcessingThread extends Thread {
 
	final public static String[] actionTypes = {
		"actionType1",
		"actionType2"
	};
	
  private Context context = null;
  private boolean isRunning = true;
 
  private Random random = new Random();
 
  private String link;
 
  public ProcessingThread(Context context, String link) {
    this.context = context;
    this.link = link;
  }
 
  @Override
  public void run() {
    Log.d("[ProcessingThread]", "Thread has started!");
    while (isRunning) {
      sendMessage();
      sleep();
    }
    Log.d("[ProcessingThread]", "Thread has stopped!");
  }
 
  private void sendMessage() {
    Intent intent = new Intent();
    intent.setAction(actionTypes[random.nextInt(actionTypes.length)]);
    intent.putExtra("message", new Date(System.currentTimeMillis()) + link);
    context.sendBroadcast(intent);
  }
 
  private void sleep() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    }
  }
 
  public void stopThread() {
    isRunning = false;
  }
}

