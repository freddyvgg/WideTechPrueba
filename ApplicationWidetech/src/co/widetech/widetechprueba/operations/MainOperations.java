package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import co.widetech.widetechprueba.R;
import co.widetech.widetechprueba.activities.DetailActivity;
import co.widetech.widetechprueba.activities.SignUpActivity;
import co.widetech.widetechprueba.to.GP;
import co.widetech.widetechprueba.to.Main;
import co.widetech.widetechprueba.to.User;
import co.widetech.widetechprueba.utils.Utils;

public class MainOperations extends Operations {

	private final String TAG = this.getClass().getSimpleName();
	
	private WeakReference<EditText> mUserText;
	private WeakReference<EditText> mPassText;
	
	boolean mActive = false;
	
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
				if(!mActive){
					mActive = true;
					Main request = buildLoginRequest();
					callWebService(request);
				}
			}
		});
		
		putButtonCommand(R.id.singup_button, new ButtonCommand() {
			
			@Override
			public void execute(View view) {
				if(!mActive){
					mActive = true;
					Intent intent = SignUpActivity.makeIntent(getActivity(), null);
					getActivity().startActivity(intent);
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

	}
	
	
	private void initializeNonViewFields() {
		
    }

	@Override
	public void onConfigurationChange(Activity activity) {
		super.setActivity(activity);
		initializeViewFields();
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
		
		List<GP> gpList = new ArrayList<>();
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
		if(null!= result)
		{
			if(result.getResponse().getCode()==0)
			{
				Intent intent = DetailActivity.makeIntent(getActivity(), new User(result));
				getActivity().startActivity(intent);
				resetViewFields();
			}else{
				showDialogError(result.getResponse().getDesc());
			}
		}else{
			Utils.showToast(getActivity(), "Couldnt connect to the server");
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
		 new AsyncTask<Main, Void, Main>(){

				@Override
				protected Main doInBackground(Main... params) {
					return Utils.callWebServiceDummy(params[0]);
				}
				
				@Override
				protected void onPostExecute(Main result) {
					handlerResult(result);
				}
				
			}.execute(request);
	}

}
