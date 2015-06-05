package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import co.widetech.widetechprueba.R;
import co.widetech.widetechprueba.activities.DetailActivity;
import co.widetech.widetechprueba.to.GP;
import co.widetech.widetechprueba.to.Main;
import co.widetech.widetechprueba.to.User;
import co.widetech.widetechprueba.utils.Utils;

public class SignUpOperations extends Operations {

	WeakReference<EditText> mNameEdit;
	WeakReference<EditText> mLastNameEdit;
	WeakReference<EditText> mEmailEdit;
	WeakReference<EditText> mPasswordEdit;
	WeakReference<EditText> mPhoneEdit;
	
	private User data;
	boolean mActive = false;
	
	public SignUpOperations(Activity mActivity) {
		super(mActivity);
		initializeViewFields();
		initializeNonViewFields();
		initializeButtonStrategy();
		refreshActivity();
	}

	private void initializeButtonStrategy() {
		putButtonCommand(R.id.accept_button, new ButtonCommand() {
			
			@Override
			public void execute(View view) {
				if(!mActive){
					mActive = true;
					Main request = buildLoginRequest();
					callWebService(request);
				}
			}
		});
		
		putButtonCommand(R.id.cancel_button, new ButtonCommand() {
			
			@Override
			public void execute(View view) {
				if(!mActive){
					mActive = true;
					getActivity().setResult(Activity.RESULT_CANCELED);
					getActivity().finish();
					mActive = false;
				}
				
			}
		});
		
	}

	private void initializeViewFields() {
		getActivity().setContentView(R.layout.activity_signup);
		
		mNameEdit = new WeakReference<EditText>(
				(EditText)getActivity().findViewById(R.id.fname));
		
		mLastNameEdit = new WeakReference<EditText>(
				(EditText)getActivity().findViewById(R.id.lname));
		
		mEmailEdit = new WeakReference<EditText>(
				(EditText)getActivity().findViewById(R.id.email));
		
		mPasswordEdit = new WeakReference<EditText>(
				(EditText)getActivity().findViewById(R.id.password));
		
		mPhoneEdit = new WeakReference<EditText>(
				(EditText)getActivity().findViewById(R.id.phone));
		

	}
	
	
	private void initializeNonViewFields() {
		data = getActivity().getIntent().getParcelableExtra("USER");
    }

	@Override
	public void onConfigurationChange(Activity activity) {
		super.setActivity(activity);
		initializeViewFields();
		refreshActivity();
	}
	
	public void resetViewFields(){
		
		
	}
	
	private void refreshActivity() {
		if(data==null||data.getId()==null||data.getId().isEmpty()||data.getId().equals("0"))
		{
			getActivity().setTitle(getActivity().getResources().getString(R.string.signup));
		}else{
			getActivity().setTitle(getActivity().getResources().getString(R.string.update));
		}
		
		
	}
	
	private Main buildLoginRequest() {
		Main result = new Main();
		
		/**
		 *  {"GP":[
			{"Name":"USR","Value":"taxi"},
			{"Name":"PASS","Value":"taxi"},
			{"Name":"CLIENTEID","Value":"33047"},
			{"Name":"PHONE","Value":"3103117143"},
			{"Name":"NAME","Value":"adriana"},
			{"Name":"LASTNAME","Value":"Mendez"},
			{"Name":"MAIL","Value":"dmendez@widetech.com.co"},
			{"Name":"PASSWORD","Value":"12345"},
			{"Name":"ID","Value":"2787128"},
			{"Name":"METHOD","Value":"SINGUP"}
			]}

		 */ 
		
		List<GP> gpList = new ArrayList<>();
		gpList.add(new GP("USR","taxi"));
		gpList.add(new GP("PASS","taxi"));
		gpList.add(new GP("CLIENTEID","33047"));
		if(data==null||data.getId()==null||data.getId().isEmpty())
			gpList.add(new GP("ID","0"));
		else
			gpList.add(new GP("ID",data.getId()));
		
		gpList.add(new GP("NAME",mNameEdit.get().getText().toString()));
		gpList.add(new GP("LASTNAME",mLastNameEdit.get().getText().toString()));
		gpList.add(new GP("MAIL",mEmailEdit.get().getText().toString()));
		gpList.add(new GP("PHONE",mPhoneEdit.get().getText().toString()));
		gpList.add(new GP("PASSWORD",mPasswordEdit.get().getText().toString()));
		gpList.add(new GP("METHOD","SINGUP"));
		result.setGP(gpList);
		
		return result;
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
	
	protected void handlerResult(Main result) {
		if(null!= result)
		{
			if(result.getResponse().getCode()==0)
			{
				getActivity().setResult(Activity.RESULT_OK);
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

}
