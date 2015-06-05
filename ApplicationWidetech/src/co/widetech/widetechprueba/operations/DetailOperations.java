package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;

import android.app.Activity;
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
		mResultText.get().setText(data.toString());
		
	}

}
