package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import co.widetech.widetechprueba.R;
import co.widetech.widetechprueba.activities.SignUpActivity;
import co.widetech.widetechprueba.to.GP;
import co.widetech.widetechprueba.to.Main;
import co.widetech.widetechprueba.to.User;
import co.widetech.widetechprueba.utils.Utils;

public class SignUpOperations extends Operations {

	private WeakReference<EditText> mNameEdit;
	private WeakReference<EditText> mLastNameEdit;
	private WeakReference<EditText> mEmailEdit;
	private WeakReference<EditText> mPasswordEdit;
	private WeakReference<EditText> mPhoneEdit;
	private WeakReference<ProgressBar> mProgressBar;
	
	private User data;
	boolean mActive = false;
	int mLoading = ProgressBar.INVISIBLE;
	
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

	protected boolean isValidForm() {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		String ALFA_PATTEN = "^[A-Za-z]+$";
		
		String NUM_PATTEN = "^[0-9]+$";
		
		boolean result = true;
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(ALFA_PATTEN);
		matcher = pattern.matcher(mNameEdit.get().getText().toString());
		if(!matcher.matches()){
			mNameEdit.get().setError("Invalid Format");
			result = false;
		}
		
		pattern = Pattern.compile(ALFA_PATTEN);
		matcher = pattern.matcher(mLastNameEdit.get().getText().toString());
		if(!matcher.matches()){
			mLastNameEdit.get().setError("Invalid Format");
			result = false;
		}
		
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(mEmailEdit.get().getText().toString());
		if(!matcher.matches()){
			mEmailEdit.get().setError("Invalid Format");
			result = false;
		}
		
		pattern = Pattern.compile(NUM_PATTEN);
		matcher = pattern.matcher(mPhoneEdit.get().getText().toString());
		if(!matcher.matches()){
			mPhoneEdit.get().setError("Invalid Format");
			result = false;
		}
		
		if(mPasswordEdit.get().getText().toString().isEmpty()){
			mPasswordEdit.get().setError("Required Field");
			result = false;
		}
		
		return result;
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
		
		mProgressBar = new WeakReference<ProgressBar>(
				(ProgressBar)getActivity().findViewById(R.id.progress));

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
			mNameEdit.get().setText(data.getFname());
			mLastNameEdit.get().setText(data.getLname());
			mEmailEdit.get().setText(data.getEmail());
			mEmailEdit.get().setEnabled(false);
			mPasswordEdit.get().setText(data.getPassword());
			mPhoneEdit.get().setText(data.getPhone());
		}
		mProgressBar.get().setVisibility(mLoading);
		
		
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
		
		List<GP> gpList = new ArrayList<GP>();
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
		data = new User(result);
		return result;
	}
	
	public void callWebService(Main request)
	{
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
	
	protected void handlerResult(Main result) {
		if(null!= result)
		{
			if(result.getResponse().getCode()==0)
			{
				data.setId(new User(result).getId());
				getActivity().setResult(Activity.RESULT_OK,SignUpActivity.makeIntentResult(data));
				getActivity().finish();
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
}
