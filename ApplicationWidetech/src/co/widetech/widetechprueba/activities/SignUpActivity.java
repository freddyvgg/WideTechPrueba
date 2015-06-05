package co.widetech.widetechprueba.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import co.widetech.widetechprueba.operations.Operations;
import co.widetech.widetechprueba.operations.SignUpOperations;
import co.widetech.widetechprueba.to.User;
import co.widetech.widetechprueba.utils.RetainedFragmentManager;

public class SignUpActivity extends Activity {
	
	public static final String VIEW_SIGNUP_ACTION = "co.widetech.widetechprueba.activities.SignUpActivity";
	
	private RetainedFragmentManager mRetainedFragmentManager = 
	        new RetainedFragmentManager(this.getFragmentManager(),
	                                    Operations.OPERATIONS);
	
	Operations mOperations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("SignUpActivity", "onCreate");
		super.onCreate(savedInstanceState);
		handleConfigurationChanges();
	}

	private void handleConfigurationChanges() {
		if(mRetainedFragmentManager.firstTimeIn())
		{
			mOperations = new SignUpOperations(this);
			mRetainedFragmentManager.put(Operations.OPERATIONS, mOperations);
		}
		else
		{
			mOperations = mRetainedFragmentManager.get(Operations.OPERATIONS);
			
			if(mOperations==null)
			{
				mOperations = new SignUpOperations(this);
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

	public static Intent makeIntent(Context context, User user) {
		Intent intent = new Intent(VIEW_SIGNUP_ACTION);
		intent.putExtra("USER", user);
		return intent;
	}

}
