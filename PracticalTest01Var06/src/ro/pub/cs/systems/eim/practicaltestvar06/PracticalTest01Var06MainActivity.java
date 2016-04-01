package ro.pub.cs.systems.eim.practicaltestvar06;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class PracticalTest01Var06MainActivity extends Activity {

    private Button navigate;
	private Button check;
	private EditText secondEditText;
	private Button details;
	private EditText firstEditText;
	private int serviceStatus = 1;
	private TextListener  tl = new TextListener();
	
	private IntentFilter intentFilter = new IntentFilter();
	
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
	private class MessageBroadcastReceiver extends BroadcastReceiver {
	  @Override
	  public void onReceive(Context context, Intent intent) {
	    Log.d("[Message]", intent.getStringExtra("message"));
	  }
	}
	
	final public static String[] actionTypes = {
		"actionType1",
		"actionType2"
	};
	
	private class TextListener implements TextWatcher{

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			String link = arg0.toString();
			if(link.startsWith("http")){
				check.setText("Pass");
				check.setBackground((getApplicationContext().getResources().getDrawable(R.color.green)));
				if(serviceStatus == 1){
					Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
			        intent.putExtra("link", secondEditText.getText().toString());
			        getApplicationContext().startService(intent);
			        serviceStatus = 2;
				}
			}
			else{
				check.setText("Fail");
				check.setBackground((getApplicationContext().getResources().getDrawable(R.color.red)));
			}
		}
	}
	
	private ButtonClickListener bcl = new ButtonClickListener();
	private LinearLayout container;
	
	private class ButtonClickListener implements View.OnClickListener {
	    @Override
	    public void onClick(View view) {
	      switch(view.getId()) {
	        case R.id.details:
	        	if(container.getVisibility() == View.VISIBLE){
	        		container.setVisibility(View.INVISIBLE);
	        		details.setText("More Details");
	        	}
	        	else{
	        		container.setVisibility(View.VISIBLE);
	        		details.setText("Less Details");
	        	}
	          break;
	        case R.id.navigate:
	        	Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
      	  
                intent.putExtra("first", secondEditText.getText().toString());
                intent.putExtra("second", check.getText().toString());
                startActivityForResult(intent, 1);
	        	break;
	      }
	    }
	  }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);
        
        this.firstEditText = (EditText)findViewById(R.id.firstedittext);
        this.details = (Button)findViewById(R.id.details);
        this.secondEditText = (EditText)findViewById(R.id.link);
        this.check = (Button)findViewById(R.id.checkbutton);
        this.navigate = (Button)findViewById(R.id.navigate);
        this.container = (LinearLayout)findViewById(R.id.container);
        
        this.details.setOnClickListener(bcl);
        this.secondEditText.addTextChangedListener(tl);
        this.navigate.setOnClickListener(bcl);
        
        intentFilter.addAction(actionTypes[0]);
        intentFilter.addAction(actionTypes[1]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practical_test01_var06_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
      savedInstanceState.putString("firstEditText", firstEditText.getText().toString());
      savedInstanceState.putString("secondEditText", secondEditText.getText().toString());
    }
   
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
      if (savedInstanceState.containsKey("firstEditText")) {
    	  firstEditText.setText(savedInstanceState.getString("firstEditText"));
      } else {
    	  firstEditText.setText("");
      }
      if (savedInstanceState.containsKey("secondEditText")) {
    	  secondEditText.setText(savedInstanceState.getString("secondEditText"));
      } else {
    	  secondEditText.setText("");
      }
      Toast.makeText(this, "The saved values: " + savedInstanceState.getString("firstEditText") +  savedInstanceState.getString("secondEditText"), Toast.LENGTH_LONG).show();
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
      if (requestCode == 1) {
        Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
      }
    }
    @Override
    protected void onDestroy() {
      Intent intent = new Intent(this, PracticalTest01Var06Service.class);
      stopService(intent);
      super.onDestroy();
    }
    

    @Override
    protected void onResume() {
      super.onResume();
      registerReceiver(messageBroadcastReceiver, intentFilter);
    }
   
    @Override
    protected void onPause() {
      unregisterReceiver(messageBroadcastReceiver);
      super.onPause();
    }
}
