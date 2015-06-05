package co.widetech.widetechprueba.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import co.widetech.widetechprueba.operations.DetailOperations;
import co.widetech.widetechprueba.operations.Operations;
import co.widetech.widetechprueba.to.User;
import co.widetech.widetechprueba.utils.RetainedFragmentManager;

public class DetailActivity extends Activity {
	
	public static final String VIEW_DETAIL_ACTION = "co.widetech.widetechprueba.activities.DetailActivity";
	
	private RetainedFragmentManager mRetainedFragmentManager = 
	        new RetainedFragmentManager(this.getFragmentManager(),
	                                    Operations.OPERATIONS);
	
	Operations mOperations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("Detail", "onCreate");
		super.onCreate(savedInstanceState);
		handleConfigurationChanges();
	}

	private void handleConfigurationChanges() {
		if(mRetainedFragmentManager.firstTimeIn())
		{
			mOperations = new DetailOperations(this);
			mRetainedFragmentManager.put(Operations.OPERATIONS, mOperations);
		}
		else
		{
			mOperations = mRetainedFragmentManager.get(Operations.OPERATIONS);
			
			if(mOperations==null)
			{
				mOperations = new DetailOperations(this);
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
		Intent intent = new Intent(VIEW_DETAIL_ACTION);
		intent.putExtra("USER", user);
		return intent;
	}

}
