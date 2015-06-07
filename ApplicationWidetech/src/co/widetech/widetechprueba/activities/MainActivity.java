package co.widetech.widetechprueba.activities;

import android.app.Activity;
import android.content.Intent;
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mOperations.onActivityResult(requestCode, resultCode, data);
	}
	
	

}
