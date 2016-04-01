package ro.pub.cs.systems.eim.practicaltestvar06;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var06SecondaryActivity extends Activity {

	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	private Button cancel;
	private Button ok;
	private EditText secondEditText;
	private EditText firstEditText;
	  private class ButtonClickListener implements View.OnClickListener {
	    @Override
	    public void onClick(View view) {
	      switch(view.getId()) {
	        case R.id.ok:
	          setResult(RESULT_OK, null);
	          break;
	        case R.id.cancel:
	          setResult(RESULT_CANCELED, null);
	          break;
	      }
	      finish();
	    }
	  }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_var06_secondary);
		
		this.firstEditText = (EditText)findViewById(R.id.firstE);
		this.secondEditText = (EditText)findViewById(R.id.secondE);
		this.ok = (Button)findViewById(R.id.ok);
		this.cancel = (Button)findViewById(R.id.cancel);
		
		this.ok.setOnClickListener(buttonClickListener);
		this.cancel.setOnClickListener(buttonClickListener);
		Intent intent = getIntent();
	    if (intent != null && intent.getExtras().containsKey("first")) {
	      String link = intent.getStringExtra("first");
	      firstEditText.setText(link);
	    }
	      if (intent.getExtras().containsKey("second")) {
		      String test = intent.getStringExtra("second");
		      secondEditText.setText(test);
	    }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.practical_test01_var06_secondary, menu);
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
}
