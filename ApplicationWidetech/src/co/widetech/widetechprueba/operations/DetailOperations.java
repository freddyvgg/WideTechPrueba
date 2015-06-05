package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import co.widetech.widetechprueba.R;
import co.widetech.widetechprueba.to.User;

public class DetailOperations extends Operations {

	private WeakReference<TextView> mResultText;
	private User data;
	
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
				// TODO Auto-generated method stub
				
			}
		});
		
		putMenuCommand(R.id.logout_menu, new MenuCommand(){

			@Override
			public void execute(MenuItem item) {
				logout();
			}
		});
		
	}

	private void initializeViewFields() {
		getActivity().setContentView(R.layout.activity_detail);
		
		mResultText = new WeakReference<TextView>((TextView)getActivity()
									.findViewById(R.id.result));

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
		mResultText.get().setText(data.toString());
		
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
	
	

}
