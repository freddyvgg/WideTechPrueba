package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import co.widetech.widetechprueba.R;
import co.widetech.widetechprueba.activities.SignUpActivity;
import co.widetech.widetechprueba.to.User;
import co.widetech.widetechprueba.utils.Utils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class DetailOperations extends Operations {

	private User data;
	private boolean mActive;
	public static final int SIGNUP_REQUEST = 0x001;
	
    private WeakReference<GoogleMap> mMap;
	
	public DetailOperations(Activity mActivity) {
		super(mActivity);
		initializeViewFields();
		initializeNonViewFields();
		initializeButtonStrategy();
		refreshActivity();
	}

	private void initializeButtonStrategy() {
		putMenuCommand(R.id.update_menu, new MenuCommand(){

			@Override
			public void execute(MenuItem item) {
				if(!mActive){
					mActive = true;
					Intent intent = SignUpActivity.makeIntent(getActivity(), data);
					getActivity().startActivityForResult(intent, SIGNUP_REQUEST);
				}
			}
		});
		
		putMenuCommand(R.id.logout_menu, new MenuCommand(){

			@Override
			public void execute(MenuItem item) {
				if(!mActive){
					mActive = true;
					logout();
					mActive = false;
				}
			}
		});
		
	}

	private void initializeViewFields() {
		getActivity().setContentView(R.layout.activity_detail);
		
		mMap = new WeakReference<GoogleMap>(((MapFragment) getActivity().getFragmentManager().findFragmentById(
				R.id.map)).getMap());

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
		getActivity().setTitle(data.getFname()+" "+data.getLname());
		mMap.get().setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.get().setMyLocationEnabled(true);
	}

	@Override
	public void onBackPressed() {
		logout();
	}
	
	public void logout()
	{
		new AlertDialog.Builder(getActivity())
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Log out")
	        .setMessage("Are you sure you want to log out?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            getActivity().finish();    
	        }
	
	    })
	    .setNegativeButton("No", null)
	    .show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getActivity().getMenuInflater();
	    inflater.inflate(R.menu.detail, menu);
	    return true;
	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==SIGNUP_REQUEST)
		{
			if(resultCode==Activity.RESULT_OK)
			{
				Utils.showToast(getActivity(), "Successed");
				this.data = (User) data.getParcelableExtra("USER");
				refreshActivity();
			}else if(requestCode==Activity.RESULT_CANCELED){
				Utils.showToast(getActivity(), "Canceled");
			}
			mActive=false;
		}
	}

}
