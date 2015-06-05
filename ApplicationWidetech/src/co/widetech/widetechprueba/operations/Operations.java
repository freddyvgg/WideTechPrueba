package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

public abstract class Operations {
	
	public static final String OPERATIONS = "OPERATIONS";
	
	protected WeakReference<Activity> mActivity;
	
	protected SparseArray<ButtonCommand> mButtonCommands = new SparseArray<>();
	
	public Operations(Activity mActivity) {
		super();
		this.mActivity = new WeakReference<Activity>(mActivity);
	}

	public final Activity getActivity()
	{
		return mActivity.get();
	}
	
	public final void setActivity(Activity activity)
	{
		mActivity = new WeakReference<Activity>(activity);
	}
	
	public void putButtonCommand(int key, ButtonCommand value) {
		mButtonCommands.put(key, value);
	}
	
	public abstract void onConfigurationChange(Activity activity);

	public void onButtonPressed(View view) {
		mButtonCommands.get(view.getId()).execute(view);
	}
	
	public void onAttach(Activity activity)
	{
		//no-op
	}
	
	
	
	protected interface ButtonCommand {
		void execute(View view);
	}

}
