package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import co.widetech.widetechprueba.R;
import co.widetech.widetechprueba.activities.DetailActivity;
import co.widetech.widetechprueba.activities.SignUpActivity;
import co.widetech.widetechprueba.to.GP;
import co.widetech.widetechprueba.to.Main;
import co.widetech.widetechprueba.to.User;
import co.widetech.widetechprueba.utils.Utils;

public class MainOperations extends Operations {

	private final String TAG = this.getClass().getSimpleName();
	public static final int SIGNUP_REQUEST = 0x001;
	public static final int LOGIN_REQUEST = 0x002;
	
	private WeakReference<EditText> mUserText;
	private WeakReference<EditText> mPassText;
	private WeakReference<ProgressBar> mProgressBar;
	
	boolean mActive = false;
	int mLoading = ProgressBar.INVISIBLE;
	
	public MainOperations(Activity mActivity) {
		super(mActivity);
		initializeViewFields();
		initializeNonViewFields();
		initializeButtonStrategy();
	}

	private void initializeButtonStrategy() {
		putButtonCommand(R.id.login_button, new ButtonCommand() {
			
			@Override
			public void execute(View view) {
				Log.d(TAG,"login_button pressed");
				if(!mActive){
					if(isValidForm()){
						mActive = true;
						mLoading = ProgressBar.VISIBLE;
						mProgressBar.get().setVisibility(ProgressBar.VISIBLE);
						Main request = buildLoginRequest();
						callWebService(request);
					}
				}
			}
		});
		
		putButtonCommand(R.id.singup_button, new ButtonCommand() {
			
			@Override
			public void execute(View view) {
				Log.d(TAG,"singup_button pressed");
				if(!mActive){
					mActive = true;
					Intent intent = SignUpActivity.makeIntent(getActivity(), null);
					getActivity().startActivityForResult(intent, SIGNUP_REQUEST);
				}
			}
		});
		
	}

	private void initializeViewFields() {
		getActivity().setContentView(R.layout.activity_main);
		
		mUserText = new WeakReference<EditText>(
				(EditText)getActivity().findViewById(R.id.user));
		
		mPassText = new WeakReference<EditText>(
				(EditText)getActivity().findViewById(R.id.pass));
		
		mProgressBar = new WeakReference<ProgressBar>(
				(ProgressBar)getActivity().findViewById(R.id.progress));

	}
	
	
	private void initializeNonViewFields() {
		
    }

	@Override
	public void onConfigurationChange(Activity activity) {
		super.setActivity(activity);
		initializeViewFields();
		mProgressBar.get().setVisibility(mLoading);
	}

	private Main buildLoginRequest() {
		Main result = new Main();
		
		/**
		 *  {"GP":[
		 *  {"Name": "USR","Value": "taxi"},
		 *  {"Name":"PASS","Value": "taxi"},
		 *  {"Name":"CLIENTEID","Value": "33047"},
		 *  {"Name":"MAIL","Value":"jairomoreno1971@gmail.com"},
		 *  {"Name": "PASSWORD","Value": "3016053124"},
		 *  {"Name":"METHOD","Value":"GETUSERLOGIN"},
		 *  {"Name":"IMEI","Value": "353922053883384"}]}
		 */
		TelephonyManager mngr = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE); 
		
		List<GP> gpList = new ArrayList<GP>();
		gpList.add(new GP("USR","taxi"));
		gpList.add(new GP("PASS","taxi"));
		gpList.add(new GP("CLIENTEID","33047"));
		gpList.add(new GP("MAIL",mUserText.get().getText().toString()));
		gpList.add(new GP("PASSWORD",mPassText.get().getText().toString()));
		gpList.add(new GP("METHOD","GETUSERLOGIN"));
		gpList.add(new GP("IMEI",mngr.getDeviceId()));
		result.setGP(gpList);
		
		return result;
	}

	protected void handlerResult(Main result) {
		Log.d(TAG,"handlerResult() called");
		if(null!= result)
		{
			if(result.getResponse().getCode()==0){
				Intent intent = DetailActivity.makeIntent(getActivity(), new User(result));
				getActivity().startActivityForResult(intent,LOGIN_REQUEST);
				
			}else{
				showDialogError(result.getResponse().getDesc());
				mLoading = ProgressBar.INVISIBLE;
				mProgressBar.get().setVisibility(ProgressBar.INVISIBLE);
			}
		}else{
			Utils.showToast(getActivity(), "Couldnt connect to the server");
			mLoading = ProgressBar.INVISIBLE;
			mProgressBar.get().setVisibility(ProgressBar.INVISIBLE);
		}
		mActive = false;
	}
	

	private void showDialogError(String desc) {
		new AlertDialog.Builder(getActivity())
			.setMessage(desc)
			.setTitle("Error")
			.setPositiveButton("Ok", null)
			.create().show();
		
	}

	public void resetViewFields(){
		Utils.hideKeyboard(getActivity(), mUserText.get().getWindowToken());
		Utils.hideKeyboard(getActivity(), mPassText.get().getWindowToken());

		mUserText.get().setText("");
		mPassText.get().setText("");
		
		
	}
	
	
	public void callWebService(Main request)
	{
		Log.d(TAG, "callWebService called");
		 new AsyncTask<Main, Void, Main>(){

				@Override
				protected Main doInBackground(Main... params) {
					return Utils.callWebService(params[0]);
				}
				
				@Override
				protected void onPostExecute(Main result) {
					handlerResult(result);
				}
				
			}.execute(request);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==SIGNUP_REQUEST)
		{
			if(resultCode==Activity.RESULT_OK)
			{
				Utils.showToast(getActivity(), "Successed");
			}else if(requestCode==Activity.RESULT_CANCELED){
				Utils.showToast(getActivity(), "Canceled");
			}
			mActive=false;
		}else if(requestCode==LOGIN_REQUEST)
		{
			resetViewFields();
			mLoading = ProgressBar.INVISIBLE;
			mProgressBar.get().setVisibility(ProgressBar.INVISIBLE);
		}
	}
	
	protected boolean isValidForm() {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		boolean result = true;
		Pattern pattern;
		Matcher matcher;
		
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(mUserText.get().getText().toString());
		if(!matcher.matches()){
			mUserText.get().setError("Invalid Format");
			result = false;
		}
		
		if(mPassText.get().getText().toString().isEmpty()){
			mPassText.get().setError("Required Field");
			result = false;
		}
		
		return result;
	}

}
