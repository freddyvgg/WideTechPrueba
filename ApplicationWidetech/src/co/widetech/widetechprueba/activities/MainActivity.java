package co.widetech.widetechprueba.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import co.widetech.widetechprueba.operations.MainOperations;
import co.widetech.widetechprueba.operations.Operations;
import co.widetech.widetechprueba.utils.RetainedFragmentManager;

public class MainActivity extends Activity {

	private RetainedFragmentManager mRetainedFragmentManager = 
	        new RetainedFragmentManager(this.getFragmentManager(),
	                                    Operations.OPERATIONS);
	
	Operations mOperations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleConfigurationChanges();
	}

	private void handleConfigurationChanges() {
		if(mRetainedFragmentManager.firstTimeIn())
		{
			mOperations = new MainOperations(this);
			mRetainedFragmentManager.put(Operations.OPERATIONS, mOperations);
		}
		else
		{
			mOperations = mRetainedFragmentManager.get(Operations.OPERATIONS);
			
			if(mOperations==null)
			{
				mOperations = new MainOperations(this);
				mRetainedFragmentManager.put(Operations.OPERATIONS, mOperations);
			}else{
				mOperations.onConfigurationChange(this);
			}
		}
	}
	
	public void onButtonPressed(View view)
	{
		mOperations.onButtonPressed(view);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
